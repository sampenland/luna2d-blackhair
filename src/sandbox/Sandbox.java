package sandbox;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import luna2d.engine.ColorHandler;
import luna2d.engine.Log;
import luna2d.engine.Scene;
import luna2d.renderables.TextDisplay;
import sandbox.scenes.WorldPlayer;

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

		new TextDisplay(this, "[W] World Editor", 200, 200, textColor, 1);
		new TextDisplay(this, "[M] Map Editor", 200, 220, textColor, 1);
		
		new TextDisplay(this, "[1] Test Movement", 200, 280, textColor, 1);
		new TextDisplay(this, "[2] Test Collisions", 200, 300, textColor, 1);
		
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
			return;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_1))
		{
			WorldPlayer worldPlayer = (WorldPlayer)this.openScene("WorldPlayer");
			worldPlayer.loadAndStart("WORLD00");
			return;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_W))
		{
			this.openScene("WorldEditor");
			return;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_M))
		{
			this.openScene("MapEditor");
			return;
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
