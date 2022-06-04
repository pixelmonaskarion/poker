package poker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main extends JPanel implements ActionListener {
	private Deck deck;
	private static Main main;
	private static JFrame frame;	
	public static Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

	Player[] players = new Player[2];
	public static void main(String[] args) {
		frame = new JFrame("poker");
		main = new Main();
		frame.add(main);
		frame.addKeyListener(main.keys);
		frame.addMouseListener(main.mouse);
		frame.setVisible(true);
		frame.setSize(screenSize.width, screenSize.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Timer t = new Timer(1, main);
		t.start();
	}
	
	public Keys keys = new Keys();
	public Mouse mouse = new Mouse();
	
	private Card[] river;
	private int anti = 100;
	int bet = 0;
	int tableMoney = 0;
	int roundState = 0;
	boolean choseThisRound = false;
	int choice = 0;
	public static int mouseClicked = 0;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (keys.codes.contains(27)) System.exit(1);
		
		if (roundState == 0) {
			bet = 10;
			river = new Card[5];
			
			for (int p = 0; p < players.length; p++) {
				players[p].money -= anti;
				tableMoney += anti;
				players[p].hand = new Hand();
				for (int i = 0; i < 2; i++) {
					players[p].hand.setCard(i, deck.pop());
				}
			}
			for (int i = 0; i < 3; i++) {
				river[i] = deck.pop();
			}
			roundState = 1;
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
				players[0].hand.getCard(i).draw(g, screenSize.width/2+(i*(Card.CARD_WIDTH+50)-river.length*((Card.CARD_WIDTH+50)/4)), screenSize.height-Card.CARD_HEIGHT+50);
			}
			g.drawString("Your money: " + players[0].money, screenSize.width-g.getFontMetrics().stringWidth("Your money: " + String.valueOf(players[0].money))-50, screenSize.height-300);
			if (!choseThisRound) {
				if (drawButton(g, "Fold", screenSize.width/2-150, screenSize.height-Card.CARD_HEIGHT-50, 5, 100, 70)) choseOption(0);
				if (drawButton(g, "Raise", screenSize.width/2, screenSize.height-Card.CARD_HEIGHT-50, 5, 100, 70)) choseOption(1);
				if (drawButton(g, "Check", screenSize.width/2+150, screenSize.height-Card.CARD_HEIGHT-50, 5, 100, 70)) choseOption(2);
			} else {
				String optionText = "Error";
				if (choice == 0) optionText = "Fold";
				if (choice == 1) optionText = "Raise";
				if (choice == 2) optionText = "Check";
				g.setColor(Color.black);
				g.drawString("Your choice: " + optionText, screenSize.width/2-g.getFontMetrics().stringWidth("Your choice: " + optionText)/2, screenSize.height-Card.CARD_HEIGHT);
			}
		}
		mouseClicked = 0;
	}
	
	public void choiceLogic(int choice) {
		if (choice == 0) {
			players[0].active = false;
		}
		if (choice == 1) {
			String s = null;
			while (true) {
				s = (String)JOptionPane.showInputDialog(frame, "What is the new bet?", "Raise", JOptionPane.PLAIN_MESSAGE, null, null, "");
				int newbet = -1;
				try {
					newbet = Integer.parseInt(s);
				} catch (NumberFormatException e) {
					continue;
				}
				if (newbet > bet) {
					bet = newbet;
					break;
				}
			}
			players[0].money -= bet;
		}
		if (choice == 2) {
			players[0].money -= bet;
		}
	}
	
	public boolean drawButton(Graphics g, String buttonText, int sx, int sy, int margin, int width, int height) {
		g.setColor(Color.black);
		g.fillRect(sx, sy, width, height);
		g.setColor(Color.white);
		g.fillRect(sx+margin, sy+margin, width-margin*2, height-margin*2);
		g.setColor(Color.black);
		g.drawString(buttonText, sx-g.getFontMetrics().stringWidth(buttonText)/2+width/2, sy+height/2+15);
		if (mouse.buttons.contains((Integer)1)) {
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
	
	public Main() {
		deck = new Deck();
		players[0] = new Player();
		players[1] = new Player();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
}
