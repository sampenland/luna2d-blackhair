package luna2d.engine;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas 
{

	private static final long serialVersionUID = -5254340815259048727L;
	
	protected JFrame frame;
	
	public Window(int width, int height, String title, Game game)
	{
		frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.setVisible(true);
		game.start();
		game.setFocusTraversalKeysEnabled(false);
	}
	
	public JFrame getFrame() { return this.frame; }
	
}
