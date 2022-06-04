package poker;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Keys extends KeyAdapter {
	public ArrayList<String> keys = new ArrayList<String>();
	public ArrayList<Integer> codes = new ArrayList<Integer>();
	public ArrayList<Consumer<KeyEvent>> typedCallbacks = new ArrayList<>();
	public ArrayList<Consumer<KeyEvent>> pressedCallbacks = new ArrayList<>();
	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
//		System.out.println("code: " + e.getKeyCode());
//		System.out.println("char: " + e.getKeyChar());
		if (!keys.contains(String.valueOf(e.getKeyChar()))){
			keys.add(String.valueOf(e.getKeyChar()));
		}
		if (!codes.contains(e.getKeyCode())){
			codes.add(e.getKeyCode());
		}
		
		for (Consumer<KeyEvent> callback : pressedCallbacks) {
			callback.accept(e);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		super.keyPressed(e);
		if (keys.contains(String.valueOf(e.getKeyChar()))){
			keys.remove(String.valueOf(e.getKeyChar()));
		}
		if (codes.contains((Integer)e.getKeyCode())){
			codes.remove((Integer)e.getKeyCode());
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		super.keyTyped(e);
		for (Consumer<KeyEvent> callback : typedCallbacks) {
			callback.accept(e);
		}
	}
}
