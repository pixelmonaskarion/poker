package poker.networking;

import java.util.Random;

import javax.swing.JOptionPane;

import io.grpc.stub.StreamObserver;
import poker.networking.PokerGrpc.PokerImplBase;
import poker.networking.PokerOuterClass.Card;
import poker.networking.PokerOuterClass.Choice;
import poker.networking.PokerOuterClass.Color;
import poker.networking.PokerOuterClass.Empty;
import poker.networking.PokerOuterClass.GameData;
import poker.networking.PokerOuterClass.JoinRequest;
import poker.networking.PokerOuterClass.JoinResponse;
import poker.networking.PokerOuterClass.Player;

public class PokerService extends PokerImplBase {

	//responses:
	//0 = game full
	//1 = joined
	//2 already in game
	
	@Override
	public void playerJoin(JoinRequest request, StreamObserver<JoinResponse> responseObserver) {
		Player joinedPlayer = PokerServer.getPlayer(request.getIp());
		if (joinedPlayer != null) {
			JoinResponse response = JoinResponse.newBuilder().setPlayer(joinedPlayer).setResponse(2).build();
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		} else {
			int id = PokerServer.nextSlot();
			if (id == -1) {
				JoinResponse response = JoinResponse.newBuilder().setPlayer(Player.getDefaultInstance()).setResponse(0).build();
				responseObserver.onNext(response);
				responseObserver.onCompleted();
			} else {
				Player newPlayer = Player.newBuilder().setColor(randomColor()).setId(id).setName(request.getPlayerName()).setIp(request.getIp()).setActive(true).setMoney(1000).build();
				PokerServer.addPlayer(newPlayer);
				JoinResponse response = JoinResponse.newBuilder().setPlayer(newPlayer).setResponse(1).build();
				responseObserver.onNext(response);
				responseObserver.onCompleted();
			}
		}
		
	}
	
	@Override
	public void sendChoice(Choice choice, StreamObserver<Empty> responseObserver) {
		PokerServer.choiceLogic(choice.getChoice(), choice.getId(), choice.getNewBet());
		responseObserver.onNext(Empty.getDefaultInstance());
		responseObserver.onCompleted();
	}
	
	@Override
	public void getGameData(Empty request, StreamObserver<GameData> responseObserver) {
		GameData.Builder gd = GameData.newBuilder().setBet(PokerServer.bet).setTableMoney(PokerServer.tableMoney).setRound(PokerServer.round).setAnti(PokerServer.anti).setTurn(PokerServer.turn);
		for (Card c : PokerServer.getRiver()) {
			if (c != null) gd.addRiver(c);
		}
		for (Player p : PokerServer.getPlayers()) {
			if (p != null) gd.addPlayers(p);
		}
		responseObserver.onNext(gd.build());
		responseObserver.onCompleted();
	}
	
	public static Color randomColor() {
		Random random = new Random();
		int r = random.nextInt(256);
		int g = random.nextInt(256);
		int b = random.nextInt(256);
		return Color.newBuilder().setR(r).setG(g).setB(b).build();
	}
	
	public static java.awt.Color colorFromWeb(Color color) {
		return new java.awt.Color(
				color.getR(),
				color.getG(),
				color.getB());
	}
}