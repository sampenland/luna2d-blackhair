package luna2d;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener
{

	private Game game;
	private ObjectHandler objHandler;
	private SceneManager sceneManager;
	
	//public boolean mouseClicked;
	public boolean failedToInitialized;
	
	public MouseHandler(Game g)
	{
		this.game = g;
		if (this.game == null || this.game.getObjectHandler() == null || this.game.getSceneManager() == null) 
		{
			Log.println("Could not initialize mouse handler");
			this.failedToInitialized = true;
			return;
		}
		
		this.failedToInitialized = false;
		this.objHandler = this.game.getObjectHandler();
		this.sceneManager = this.game.getSceneManager();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if (this.objHandler != null) this.objHandler.onMouseClick(e);
		if (this.sceneManager != null) this.sceneManager.onMouseClick(e);
		//this.mouseClicked = true;
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		if (this.objHandler != null) this.objHandler.onMousePressed(e);
		if (this.sceneManager != null) this.sceneManager.onMousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if (this.objHandler != null) this.objHandler.onMouseReleased(e);
		if (this.sceneManager != null) this.sceneManager.onMouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		
	}

}
