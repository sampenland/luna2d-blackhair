package sandbox;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import luna2d.ColorHandler;
import luna2d.Log;
import luna2d.Scene;
import luna2d.renderables.TextDisplay;

public class Sandbox extends Scene
{
	public Sandbox(String name)
	{
		super(name);
	}
	
	public void start()
	{
		Log.println("Sandbox started.");
		
		Color textColor = ColorHandler.getColor("GrassGreen");
		new TextDisplay(this, "Sandbox for testing the luna2d engine", 200, 150, textColor, 1);
		
		new TextDisplay(this, "[C]ollisions", 200, 220, textColor, 1);
		new TextDisplay(this, "[M]ovement", 200, 240, textColor, 1);
		
		new TextDisplay(this, "[Q]uit", 200, 400, textColor, 1);
		
		
	}

	public void end() 
	{
		Log.println("Sandbox ended.");
	}

	public void update() 
	{
		if (this.isKeyPressed(KeyEvent.VK_Q))
		{
			this.endGame();
		}
	}

	@Override
	protected void onMouseClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
