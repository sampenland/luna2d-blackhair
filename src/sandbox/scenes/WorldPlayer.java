package sandbox.scenes;

import java.awt.event.KeyEvent;

import luna2d.templates.SimpleWorldPlayer;

public class WorldPlayer extends SimpleWorldPlayer
{

	public WorldPlayer(String name) 
	{
		super(name);
	}
	
	@Override protected void checkKeys()
	{
		if (this.isKeyPressed(KeyEvent.VK_ESCAPE)) 
		{
			this.openScene("Sandbox");
		}
	}

}
