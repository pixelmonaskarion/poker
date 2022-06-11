package poker.networking;

import java.awt.Color;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import poker.game.Card;
import poker.game.Game;
import poker.game.Rank;
import poker.game.Suit;
import poker.networking.PokerGrpc.PokerStub;
import poker.networking.PokerOuterClass.Choice;
import poker.networking.PokerOuterClass.Empty;
import poker.networking.PokerOuterClass.GameData;
import poker.networking.PokerOuterClass.JoinRequest;
import poker.networking.PokerOuterClass.JoinResponse;
import poker.networking.PokerOuterClass.Player;

public class PokerClient {
	static ManagedChannel channel;
	static PokerStub stub;
	static Player player;
	static String ip;
	static String name;
	
	public static void main(String[] args) throws UnknownHostException {
		String host = (String)JOptionPane.showInputDialog(
                new JFrame(),
                "Enter ip and port number (ip:25565)",
                "Host",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "0.0.0.0:25565");
		if (host == null) exit();
		String hostIP = null;
		int port = 0;
		try {
			hostIP = host.substring(0, host.indexOf(':'));
			port = Integer.valueOf(host.substring(host.indexOf(':')+1));
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			exit();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			exit();
		}
		if (hostIP == null || port > 65536 || port < 1) exit();
		System.out.println("joining host " + hostIP + ":" + port);
		channel = ManagedChannelBuilder.forAddress(hostIP, port).usePlaintext().build();
		System.out.println("client started");
		//sync
		//PokerBlockingStub stub = PokerGrpc.newBlockingStub(channel);
		//async
		stub = PokerGrpc.newStub(channel);
		
		name = (String)JOptionPane.showInputDialog(
                new JFrame(),
                "Enter your name",
                "Name",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "name");
		if (name == null) exit();
		ip = InetAddress.getLocalHost().getHostAddress();
		ip = name;
		System.out.println("ip is " + ip);
		
		JoinRequest request = JoinRequest.newBuilder().setPlayerName(name).setIp(ip).build();
		
		stub.playerJoin(request, new StreamObserver<PokerOuterClass.JoinResponse>() {
			
			@Override
			public void onNext(JoinResponse value) {
				System.out.println("got response: " + value.getResponse());
				player = value.getPlayer();
			}
			
			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				exit();
			}
			
			@Override
			public void onCompleted() {
				System.out.println("joined");
				Game.startOnline(player.getId());
				getGameData();
			}
		});
		
		while (true) {}
	}
	
	public static void sendChoice(int choice, int newBet) {
		stub.sendChoice(Choice.newBuilder().setId(player.getId()).setChoice(choice).setNewBet(newBet).build(), new StreamObserver<PokerOuterClass.Empty>() {

			@Override
			public void onNext(Empty value) {
			}

			@Override
			public void onError(Throwable t) {
			}

			@Override
			public void onCompleted() {
			}
		});
	}
	
	public static void getGameData() {
		stub.getGameData(Empty.getDefaultInstance(), new StreamObserver<PokerOuterClass.GameData>() {

			GameData data;
			
			@Override
			public void onCompleted() {
				Game.main.setRoundState(data.getRound());
				Game.main.setBet(data.getBet());
				Game.main.setTableMoney(data.getTableMoney());
				
				Game.main.river = new Card[5];
				//System.out.println(data.getRiverCount());
				for (int i = 0; i < data.getRiverCount(); i++) {
					Suit suit = Suit.values()[data.getRiver(i).getSuit()];
					Rank rank = Rank.values()[data.getRiver(i).getRank()];
					Card card = new Card(suit, rank);
					//System.out.println(card);
					Game.main.river[i] = card;
				}
				Game.main.setAnti(data.getAnti());
				Game.main.turn = data.getTurn();
				Game.main.players = new poker.game.Player[10];
				for (int i = 0; i < data.getPlayersCount(); i++) {
					poker.game.Player player = new poker.game.Player();
					player.money = data.getPlayers(i).getMoney();
					player.active = data.getPlayers(i).getActive();
					player.color = new Color(data.getPlayers(i).getColor().getR(), data.getPlayers(i).getColor().getG(), data.getPlayers(i).getColor().getB());
					player.name = data.getPlayers(i).getName();
					player.majorScore = data.getPlayers(i).getMajorScore();
					player.minorScore = data.getPlayers(i).getMinorScore();
					poker.game.Hand hand = new poker.game.Hand();
					for (int c = 0; c < data.getPlayers(i).getHand().getCardsCount(); c++) {
						Suit suit = Suit.values()[data.getPlayers(i).getHand().getCards(c).getSuit()];
						Rank rank = Rank.values()[data.getPlayers(i).getHand().getCards(c).getRank()];
						Card card = new Card(suit, rank);
						hand.setCard(c, card);
					}
					player.hand = hand;
					Game.main.players[i] = player;
				}
			}

			@Override
			public void onError(Throwable arg0) {
				System.out.println("there was an error");
				arg0.printStackTrace();
				exit();
			}

			@Override
			public void onNext(GameData arg0) {
				data = arg0;
			}
		});
	}
	
	public static void exit() {
		if (channel != null) channel.shutdown();
		System.exit(0);
	}
}
