package poker.game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Mouse implements MouseListener {
	public ArrayList<Integer> buttons = new ArrayList<Integer>();
	@Override
	public void mouseClicked(MouseEvent e) {
		Game.mouseClicked = (Integer)e.getButton();
		if (Game.main != null) Game.main.repaint();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//System.out.println(e.getButton());
		buttons.remove((Integer)e.getButton());
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if (!buttons.contains(e.getButton())) {
			buttons.add(e.getButton());
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}