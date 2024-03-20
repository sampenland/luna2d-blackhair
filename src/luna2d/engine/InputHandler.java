package luna2d.engine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import luna2d.ui.UI;

public class InputHandler extends KeyAdapter 
{	
	private Game game;
	
	public InputHandler(Game g)
	{
		this.game = g;
	}
	
	public void keyPressed(KeyEvent e)
	{
		if (this.game == null) 
		{
			Log.println("Input Handler has null Game object.");
			return;
		}
		
		ObjectHandler objHandler = this.game.getObjectHandler();
		
		if (objHandler == null)
		{
			Log.println("Input Handler has null Object Handler");
			return;
		}
		
		int key = e.getKeyCode();
		
		for (int i = 0; i < objHandler.getObjects().size(); i++) 
		{
			GameObject temp = objHandler.getObjects().get(i);
			if(temp.inputEnabled == false) continue;
			
			temp.keyPressed(key);
		}
		
		for (int i = 0; i < objHandler.getUIs().size(); i++) 
		{
			UI temp = objHandler.getUIs().get(i);
			if(temp.inputEnabled == false) continue;
			
			temp.keyPressed(key);
		}
		
		Scene currentScene = this.game.sceneManager.getCurrentScene();
		if (currentScene != null)
		{
			currentScene.keyPressed(key);
		}
		
	}
	
	public void keyReleased(KeyEvent e)
	{
		if (this.game == null) 
		{
			Log.println("Input Handler has null Game object.");
			return;
		}
		
		ObjectHandler objHandler = this.game.getObjectHandler();
		
		if (objHandler == null)
		{
			Log.println("Input Handler has null Object Handler");
			return;
		}
		
		int key = e.getKeyCode();
		
		for (int i = 0; i < objHandler.getObjects().size(); i++) 
		{
			GameObject temp = objHandler.getObjects().get(i);
			if(temp.inputEnabled == false) continue;
			
			temp.keyReleased(key);
		}
		
		for (int i = 0; i < objHandler.getUIs().size(); i++) 
		{
			UI temp = objHandler.getUIs().get(i);
			if(temp.inputEnabled == false) continue;
			
			temp.keyReleased(key);
		}
		
		Scene currentScene = this.game.sceneManager.getCurrentScene();
		if (currentScene != null)
		{
			currentScene.keyReleased(key);
		}
		
	}

}
