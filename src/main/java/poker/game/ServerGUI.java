package poker.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import poker.networking.PokerServer;
import poker.networking.PokerService;

public class ServerGUI extends JPanel implements ActionListener {

	public static JFrame frame;
	public static ServerGUI main;
	
	public static void openWindow() {
		frame = new JFrame("server");
		main = new ServerGUI();
		frame.add(main);
		frame.addKeyListener(main.keys);
		frame.addMouseListener(main.mouse);
		frame.setVisible(true);
		frame.setSize(Game.screenSize.width, Game.screenSize.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Timer t = new Timer(1, main);
		t.start();
	}
	
	String ip = null;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		PokerServer.gameUpdate();
		g.setFont(getFont().deriveFont(30.0f));
		if (ip == null) {
			try {
				ip = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		g.drawString("Server open on " + ip + ":" + PokerServer.port, Game.screenSize.width/2-g.getFontMetrics().stringWidth("Server open on port: " + PokerServer.port)/2, 30);
		if (PokerServer.round == 0) {
			poker.networking.PokerOuterClass.Player[] players = PokerServer.getPlayers();
			for (int i = 0; i < players.length; i++) {
				if (players[i] == null) continue;
				Random random = new Random(players[i].getName().hashCode());
				g.setColor(PokerService.colorFromWeb(players[i].getColor()));
				g.drawString(players[i].getName(), random.nextInt(Game.screenSize.width), random.nextInt(Game.screenSize.height));
			}
			if (PokerServer.nextSlot() > 1) {
				if (drawButton(g, "start game", Game.screenSize.width/2-100, (int)(Game.screenSize.height*0.75-50), 10, 200, 100)) PokerServer.startGame();
			}
		} else if (PokerServer.round == 6) {
			if (drawButton(g, "next round", Game.screenSize.width/2-100, (int)(Game.screenSize.height*0.75-50), 10, 200, 100)) PokerServer.roundEnd();
		} else {
			g.drawString("round is: " + PokerServer.round, Game.screenSize.width/2, Game.screenSize.height/2);
		}
		Game.mouseClicked = 0;
	}
	
	public boolean drawButton(Graphics g, String buttonText, int sx, int sy, int margin, int width, int height) {
		g.setColor(Color.black);
		g.fillRect(sx, sy, width, height);
		g.setColor(Color.white);
		g.fillRect(sx+margin, sy+margin, width-margin*2, height-margin*2);
		g.setColor(Color.black);
		g.drawString(buttonText, sx-g.getFontMetrics().stringWidth(buttonText)/2+width/2, sy+height/2+15);
		if (Game.mouseClicked == 1) {
			int mx = getMousePosition().x;
			int my = getMousePosition().y;
			if (mx > sx && mx < sx+width && my > sy && my < sy+height) return true;
		}
		return false;
	}
	
	public Point getMousePosition() {
		int mouseX = MouseInfo.getPointerInfo().getLocation().x - this.getLocationOnScreen().x;
		int mouseY = MouseInfo.getPointerInfo().getLocation().y - this.getLocationOnScreen().y;
		return new Point(mouseX, mouseY);
	}
	
	public Keys keys = new Keys();
	public Mouse mouse = new Mouse();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

}
