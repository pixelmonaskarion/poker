package poker.networking;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import poker.game.Card;
import poker.game.Deck;
import poker.game.Rank;
import poker.game.Rouxls;
import poker.game.RuleScore;
import poker.game.ServerGUI;
import poker.game.Suit;
import poker.networking.PokerOuterClass.Hand;
import poker.networking.PokerOuterClass.Player;

public class PokerServer {
	private static Player[] players = new Player[10];
	private static boolean[] activePlayers = new boolean[10];
	private static ArrayList<Boolean> readyPlayers = new ArrayList<>();
	public static Deck deck;
	public static int anti = 100;
	public static int bet = 10;
	public static int tableMoney = 0;
	public static int round = 0;
	public static int turn = 0;
	public static Card[] river = new Card[5];
	public static Hand[] hands = new Hand[10];
	public static int port = 9090;
	
	public static void main(String[] args) {
		Server server = ServerBuilder.forPort(port).addService(new PokerService()).build();
		
		try {
			server.start();
			
			System.out.println("Server started at port: " + server.getPort());
			ServerGUI.openWindow();
			
			server.awaitTermination();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void startGame() {
		Rouxls.init();
		bet = 10;
		river = new Card[5];
		deck = new Deck();
		
		for (int p = 0; p < players.length; p++) {
			if (players[p] == null) continue;
			players[p] = players[p].toBuilder().setMoney(players[p].getMoney()-anti).build();
			tableMoney += anti;
			Hand.Builder hand = Hand.newBuilder();
			for (int i = 0; i < 2; i++) {
				Card card = deck.pop();
				hand.addCards(poker.networking.PokerOuterClass.Card.newBuilder().setRank(card.getRank().ordinal()).setSuit(card.getSuit().ordinal()));
			}
			players[p] = players[p].toBuilder().setHand(hand.build()).build();
		}
		for (int i = 0; i < 3; i++) {
			river[i] = deck.pop();
		}
		readyPlayers.clear();
		for (int i = 0; i < players.length; i++) {
			if (players[i] == null) continue;
			readyPlayers.add(false);
		}
		turn = 0;
		round = 1;
	}
	
	public static void gameUpdate() {
		if (round == 1) {
			if (!readyPlayers.contains(false)) {
				round = 2;
			}
		} else if (round == 2) {
			for (int i = 0; i < players.length; i++) {
				if (players[i] == null) continue;
				readyPlayers.set(i, !(players[i].getActive()));
			}
			river[3] = deck.pop();
			turn = 0;
			while (true) {
				if (players[turn] == null) {
					break;
				}
				if (players[turn].getActive()) {
					break;
				}
				turn++;
			}
			round = 3;
		} else if (round == 3) {
			if (!readyPlayers.contains(false)) {
				round = 4;
			}
		} else if (round == 4) {
			for (int i = 0; i < players.length; i++) {
				if (players[i] == null) continue;
				readyPlayers.set(i, !(players[i].getActive()));
			}
			river[4] = deck.pop();
			turn = 0;
			while (true) {
				if (players[turn] == null) {
					break;
				}
				if (players[turn].getActive()) {
					break;
				}
				turn++;
			}
			round = 5;
		} else if (round == 5) {
			if (!readyPlayers.contains(false)) {
				round = 6;
			}
		} else if (round == 6) {
			for (int i = 0; i < players.length; i++) {
				if (players[i] == null) continue;
				if (!players[i].getActive()) {
					players[i] = players[i].toBuilder().setMajorScore(0).setMinorScore(0).build();
					continue;
				}
				poker.game.Hand hand = new poker.game.Hand();
				for (int c = 0; c < players[i].getHand().getCardsCount(); c++) {
					Suit suit = Suit.values()[players[i].getHand().getCards(c).getSuit()];
					Rank rank = Rank.values()[players[i].getHand().getCards(c).getRank()];
					Card card = new Card(suit, rank);
					hand.setCard(c, card);
				}
				RuleScore score = Rouxls.check(river, hand);
				players[i] = players[i].toBuilder().setMajorScore(score.getRuleScore()).setMinorScore(score.getScore()).build();
			}
		}
	}
	
	public static int addPlayer(Player player) {
		for (int i = 0; i < players.length; i++) {
			if (players[i] == null) {
				players[i] = player;
				activePlayers[i] = false;
				return i;
			}
		}
		return -1;
	}
	
	public static Player[] getPlayers() {
		return players;
	}
	
	public static boolean setActive(String ip) {
		for (int i = 0; i < players.length; i++) {
			if (players[i] != null) {
				if (players[i].getIp().equals(ip)) {
					if (!activePlayers[i]) {
						activePlayers[i] = true;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static Player getPlayer(String ip) {
		for (int i = 0; i < players.length; i++) {
			if (players[i] != null) {
				if (players[i].getIp().equals(ip)) {
					return players[i];
				}
			}
		}
		return null;
	}
	
	public static int nextSlot() {
		for (int i = 0; i < players.length; i++) {
			if (players[i] == null) {
				return i;
			}
		}
		return -1;
	}
	
	public static PokerOuterClass.Card[] getRiver() {
		PokerOuterClass.Card[] cards = new PokerOuterClass.Card[river.length];
		//System.out.println("there are " + river.length);
		for (int i = 0; i < river.length; i++) {
			if (river[i] == null) continue;
			cards[i] = PokerOuterClass.Card.newBuilder().setRank(river[i].getRank().ordinal()).setSuit(river[i].getSuit().ordinal()).build();
		}
		return cards;
	}
	
	public static void choiceLogic(int choice, int player, int newBet) {
		if (player != turn) return;
		if (choice == 0) {
			players[player] = players[player].toBuilder().setActive(false).build();
		}
		if (choice == 1) {
			bet = newBet;
			players[player] = players[player].toBuilder().setMoney(players[player].getMoney()-bet).build();
			tableMoney += bet;
		}
		if (choice == 2) {
			players[player] = players[player].toBuilder().setMoney(players[player].getMoney()-bet).build();
			tableMoney += bet;
		}
		readyPlayers.set(player, true);
		while (true) {
			turn++;
			if (players[turn] == null) {
				break;
			}
			if (players[turn].getActive()) {
				break;
			}
		}
	}

	public static void roundEnd() {
		int winningPlayer = 0;
		for (int i = 0; i < players.length; i++) {
			if (players[i] == null) continue;
			players[i] = players[i].toBuilder().setActive(true).build();
			if (players[i].getMajorScore() > players[winningPlayer].getMajorScore()) {
				winningPlayer = i;
			} else if (players[i].getMajorScore() == players[winningPlayer].getMajorScore()) {
				if (players[i].getMinorScore() > players[winningPlayer].getMinorScore()) {
					winningPlayer = i;
				}
			}
		}
		players[winningPlayer] = players[winningPlayer].toBuilder().setMoney(players[winningPlayer].getMoney()+tableMoney).build();
		tableMoney = 0;
		startGame();
	}

}
