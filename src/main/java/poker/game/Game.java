package poker.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import poker.networking.PokerClient;

@SuppressWarnings("serial")
public class Game extends JPanel implements ActionListener {
	private Deck deck;
	public static Game main;
	private static JFrame frame;	
	public static Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	
	public Player[] players = new Player[2];
	public static void main(String[] args) {
		frame = new JFrame("poker");
		main = new Game(false);
		frame.add(main);
		frame.addKeyListener(main.keys);
		frame.addMouseListener(main.mouse);
		frame.setVisible(true);
		frame.setSize(screenSize.width, screenSize.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Timer t = new Timer(5000, main);
		t.start();
	}
	
	public static void startOnline(int playerID) {
		frame = new JFrame("poker");
		main = new Game(true);
		System.out.println("ID is " + playerID);
		main.playerID = playerID;
		frame.add(main);
		frame.addKeyListener(main.keys);
		frame.addMouseListener(main.mouse);
		frame.setVisible(true);
		frame.setSize(screenSize.width, screenSize.height);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        PokerClient.exit();
		    }
		});
		Timer t = new Timer(5000, main);
		t.start();
		java.util.Timer dataTimer = new java.util.Timer();
		dataTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				PokerClient.getGameData();
			}
		}, 100, 100);
	}
	
	public Keys keys = new Keys();
	public Mouse mouse = new Mouse();
	private boolean online;
	public int playerID = 0;
	
	public Card[] river;
	private int anti = 100;
	int bet = 0;
	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	public int getTableMoney() {
		return tableMoney;
	}

	public void setTableMoney(int tableMoney) {
		this.tableMoney = tableMoney;
	}

	public int getRoundState() {
		return roundState;
	}

	public void setRoundState(int roundState) {
		this.roundState = roundState;
		this.choice = 0;
		this.choseThisRound = false;
	}

	int tableMoney = 0;
	int roundState = 0;
	public int turn = 0;
	boolean choseThisRound = false;
	int choice = 0;
	public static int mouseClicked = 0;
	
	public int getAnti() {
		return anti;
	}
	
	public void setAnti(int value) {
		anti = value;
	}
	
	@Override
	protected synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (keys.codes.contains(27)) {
			if (online) {
				PokerClient.exit();
			} else {
				System.exit(1);
			}
		}
		
		if (roundState == 0) {
			g.setFont(getFont().deriveFont(30.0f));
			for (int i = 0; i < players.length; i++) {
				if (players[i] == null) continue;
				Random random = new Random(players[i].name.hashCode());
				g.setColor(players[i].color);
				g.drawString(players[i].name, random.nextInt(Game.screenSize.width), random.nextInt(Game.screenSize.height));
			}
		} else if (roundState == 1) {
			for (int i = 0; i < river.length; i++) {
				if (river[i] == null) continue;
				river[i].draw(g, screenSize.width/2+(i*(Card.CARD_WIDTH+50)-river.length*((Card.CARD_WIDTH+50)/4)), screenSize.height/2-Card.CARD_HEIGHT/2);
			}
			g.setColor(Color.yellow.darker());
			g.drawString("Money on table: " + tableMoney, 50, 50);
			g.setColor(Color.black);
			g.drawString("Bet: " + bet, 50, 100);
			for (int i = 0; i < 2; i++) {
				try {
					players[playerID].hand.getCard(i).draw(g, screenSize.width/2+(i*(Card.CARD_WIDTH+50)-river.length*((Card.CARD_WIDTH+50)/4)), screenSize.height-Card.CARD_HEIGHT+Card.CARD_HEIGHT/10);
				} catch (NullPointerException e) {
					//e.printStackTrace();
					System.out.println("length: " + players.length + " this player: " + players[playerID].name + " id: " + playerID);
				}
			}
			g.drawString("Your money: " + players[playerID].money, screenSize.width-g.getFontMetrics().stringWidth("Your money: " + String.valueOf(players[playerID].money))-50, screenSize.height-300);
			if (!choseThisRound && turn == playerID && players[playerID].active) {
				if (drawButton(g, "Fold", screenSize.width/2-150, screenSize.height-Card.CARD_HEIGHT-100, 5, 100, 70)) choseOption(0);
				if (drawButton(g, "Raise", screenSize.width/2, screenSize.height-Card.CARD_HEIGHT-100, 5, 100, 70)) choseOption(1);
				if (drawButton(g, "Check", screenSize.width/2+150, screenSize.height-Card.CARD_HEIGHT-100, 5, 100, 70)) choseOption(2);
			} else if (!players[playerID].active) {
				g.setColor(Color.black);
				g.drawString("You Folded", screenSize.width/2-g.getFontMetrics().stringWidth("You Folded")/2, screenSize.height-Card.CARD_HEIGHT);
			} else if (playerID != turn) {
				g.setColor(Color.black);
				g.drawString("It is " + players[turn].name + "'s turn", screenSize.width/2-g.getFontMetrics().stringWidth("It is " + players[turn].name + "'s turn")/2, screenSize.height-Card.CARD_HEIGHT);
			} else {
				String optionText = "Error";
				if (choice == 0) optionText = "Fold";
				if (choice == 1) optionText = "Raise";
				if (choice == 2) optionText = "Check";
				g.setColor(Color.black);
				g.drawString("Your choice: " + optionText, screenSize.width/2-g.getFontMetrics().stringWidth("Your choice: " + optionText)/2, screenSize.height-Card.CARD_HEIGHT);
			}
		} else if (roundState == 2) {
			//river[3] = deck.pop();
			//roundState = 3;
			choseThisRound = false;
			choice = 0;
		} else if (roundState == 3) {
			for (int i = 0; i < river.length; i++) {
				if (river[i] == null) continue;
				river[i].draw(g, screenSize.width/2+(i*(Card.CARD_WIDTH+50)-river.length*((Card.CARD_WIDTH+50)/4)), screenSize.height/2-Card.CARD_HEIGHT/2);
			}
			g.setColor(Color.yellow.darker());
			g.drawString("Money on table: " + tableMoney, 50, 50);
			g.setColor(Color.black);
			g.drawString("Bet: " + bet, 50, 100);
			for (int i = 0; i < 2; i++) {
				players[playerID].hand.getCard(i).draw(g, screenSize.width/2+(i*(Card.CARD_WIDTH+50)-river.length*((Card.CARD_WIDTH+50)/4)), screenSize.height-Card.CARD_HEIGHT+Card.CARD_HEIGHT/10);
			}
			g.drawString("Your money: " + players[playerID].money, screenSize.width-g.getFontMetrics().stringWidth("Your money: " + String.valueOf(players[playerID].money))-50, screenSize.height-300);
			if (!choseThisRound && turn == playerID && players[playerID].active) {
				if (drawButton(g, "Fold", screenSize.width/2-150, screenSize.height-Card.CARD_HEIGHT-100, 5, 100, 70)) choseOption(0);
				if (drawButton(g, "Raise", screenSize.width/2, screenSize.height-Card.CARD_HEIGHT-100, 5, 100, 70)) choseOption(1);
				if (drawButton(g, "Check", screenSize.width/2+150, screenSize.height-Card.CARD_HEIGHT-100, 5, 100, 70)) choseOption(2);
			} else if (!players[playerID].active) {
				g.setColor(Color.black);
				g.drawString("You Folded", screenSize.width/2-g.getFontMetrics().stringWidth("You Folded")/2, screenSize.height-Card.CARD_HEIGHT);
			} else if (playerID != turn) {
				g.setColor(Color.black);
				g.drawString("It is " + players[turn].name + "'s turn", screenSize.width/2-g.getFontMetrics().stringWidth("It is " + players[turn].name + "'s turn")/2, screenSize.height-Card.CARD_HEIGHT);
			} else {
				String optionText = "Error";
				if (choice == 0) optionText = "Fold";
				if (choice == 1) optionText = "Raise";
				if (choice == 2) optionText = "Check";
				g.setColor(Color.black);
				g.drawString("Your choice: " + optionText, screenSize.width/2-g.getFontMetrics().stringWidth("Your choice: " + optionText)/2, screenSize.height-Card.CARD_HEIGHT);
			}
		} else if (roundState == 4) {
			//river[4] = deck.pop();
			//roundState = 5;
			choseThisRound = false;
			choice = 0;
		} else if (roundState == 5) {
			for (int i = 0; i < river.length; i++) {
				if (river[i] == null) continue;
				river[i].draw(g, screenSize.width/2+(i*(Card.CARD_WIDTH+50)-river.length*((Card.CARD_WIDTH+50)/4)), screenSize.height/2-Card.CARD_HEIGHT/2);
			}
			g.setColor(Color.yellow.darker());
			g.drawString("Money on table: " + tableMoney, 50, 50);
			g.setColor(Color.black);
			g.drawString("Bet: " + bet, 50, 100);
			for (int i = 0; i < 2; i++) {
				players[playerID].hand.getCard(i).draw(g, screenSize.width/2+(i*(Card.CARD_WIDTH+50)-river.length*((Card.CARD_WIDTH+50)/4)), screenSize.height-Card.CARD_HEIGHT+Card.CARD_HEIGHT/10);
			}
			g.drawString("Your money: " + players[playerID].money, screenSize.width-g.getFontMetrics().stringWidth("Your money: " + String.valueOf(players[playerID].money))-50, screenSize.height-300);
			if (!choseThisRound && turn == playerID && players[playerID].active) {
				if (drawButton(g, "Fold", screenSize.width/2-150, screenSize.height-Card.CARD_HEIGHT-100, 5, 100, 70)) choseOption(0);
				if (drawButton(g, "Raise", screenSize.width/2, screenSize.height-Card.CARD_HEIGHT-100, 5, 100, 70)) choseOption(1);
				if (drawButton(g, "Check", screenSize.width/2+150, screenSize.height-Card.CARD_HEIGHT-100, 5, 100, 70)) choseOption(2);
			} else if (!players[playerID].active) {
				g.setColor(Color.black);
				g.drawString("You Folded", screenSize.width/2-g.getFontMetrics().stringWidth("You Folded")/2, screenSize.height-Card.CARD_HEIGHT);
			} else if (playerID != turn) {
				g.setColor(Color.black);
				g.drawString("It is " + players[turn].name + "'s turn", screenSize.width/2-g.getFontMetrics().stringWidth("It is " + players[turn].name + "'s turn")/2, screenSize.height-Card.CARD_HEIGHT);
			} else {
				String optionText = "Error";
				if (choice == 0) optionText = "Fold";
				if (choice == 1) optionText = "Raise";
				if (choice == 2) optionText = "Check";
				g.setColor(Color.black);
				g.drawString("Your choice: " + optionText, screenSize.width/2-g.getFontMetrics().stringWidth("Your choice: " + optionText)/2, screenSize.height-Card.CARD_HEIGHT);
			}
		} else if (roundState == 6) {
			g.drawString("End of round", screenSize.width/2-g.getFontMetrics().stringWidth("End of round")/2, 30);
			//RuleScore score = Rouxls.check(river, players[playerID].hand);
			int winningPlayer = 0;
			for (int i = 0; i < players.length; i++) {
				if (players[i] == null) continue;
				if (players[i].majorScore > players[winningPlayer].majorScore) {
					winningPlayer = i;
				} else if (players[i].majorScore == players[winningPlayer].majorScore) {
					if (players[i].minorScore > players[winningPlayer].minorScore) {
						winningPlayer = i;
					}
				}
			}
			for (int i = 0; i < players.length; i++) {
				if (players[i] == null) continue;
				if (i == winningPlayer) {
					g.setColor(Color.green);
				} else {
					g.setColor(Color.red);
				}
				g.drawString(players[i].name, screenSize.width/2-g.getFontMetrics().stringWidth(players[i].name)/2, 60*i+60);
				String scoreString = "Major: " + players[i].majorScore + " Minor: " + players[i].minorScore;
				g.drawString(scoreString, screenSize.width/2-g.getFontMetrics().stringWidth(scoreString)/2, 60*i+80);
				g.drawString("Money on table: " + tableMoney, 50, 50);
			}
		}
		mouseClicked = 0;
	}
	
	public void choiceLogic(int choice) {
		int newbet = -1;
		if (choice == 1) {
			String s = null;
			s = (String)JOptionPane.showInputDialog(frame, "What is the new bet?", "Raise", JOptionPane.PLAIN_MESSAGE, null, null, "");
			if (s == null) {
				return;
			}
			try {
				newbet = Integer.valueOf(s);
			} catch (NumberFormatException e){
				return;
			}
			if (newbet > bet) PokerClient.sendChoice(choice, newbet);
		} else {
			PokerClient.sendChoice(choice, newbet);
		}
	}
	
	public boolean drawButton(Graphics g, String buttonText, int sx, int sy, int margin, int width, int height) {
		g.setColor(Color.black);
		g.fillRect(sx, sy, width, height);
		g.setColor(Color.white);
		g.fillRect(sx+margin, sy+margin, width-margin*2, height-margin*2);
		g.setColor(Color.black);
		g.drawString(buttonText, sx-g.getFontMetrics().stringWidth(buttonText)/2+width/2, sy+height/2+15);
		if (mouseClicked == 1) {
			int mx = getMousePosition().x;
			int my = getMousePosition().y;
			if (mx > sx && mx < sx+width && my > sy && my < sy+height) return true;
		}
		return false;
	}
	
	public void choseOption(int option) {
		choseThisRound = true;
		choice = option;
		choiceLogic(option);
	}
	
	public Point getMousePosition() {
		int mouseX = MouseInfo.getPointerInfo().getLocation().x - this.getLocationOnScreen().x;
		int mouseY = MouseInfo.getPointerInfo().getLocation().y - this.getLocationOnScreen().y;
		return new Point(mouseX, mouseY);
	}
	
	public Game(boolean online) {
		this.online = online;
		if (!online) {
			deck = new Deck();
			players[0] = new Player();
			players[1] = new Player();
			Rouxls.init();
		} else {
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
	@Override
	public void repaint() {
		super.repaint();
		try {
			throw new RuntimeException();
		} catch (RuntimeException e) {
			//e.printStackTrace();
			//System.out.println(System.currentTimeMillis());
		}
	}
}
