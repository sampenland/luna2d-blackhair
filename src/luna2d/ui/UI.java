package luna2d.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import luna2d.Scene;

public abstract class UI 
{
	protected Scene inScene;
	
	public int worldX, worldY, screenX, screenY;
	protected int scale;
	public boolean visible;
	
	public boolean inputEnabled, mouseEnabled;
	protected boolean destroyNow;
	public boolean mouseClicked;
	
	public UI(Scene inScene, int scale)
	{
		this.inScene = inScene;
		this.inScene.getObjectHandler().addUI(this);
		this.scale = scale;
		this.worldX = this.worldY = this.screenX = this.screenY = 0;
		this.visible = true;
		this.inputEnabled = false;
		this.mouseEnabled = false;
		this.mouseClicked = false;
	}
	
	public boolean isInputEnabled()
	{
		return this.inputEnabled;
	}
	
	public void destroy()
	{
		this.destroyNow = true;
	}
	
	public boolean getDestroyNow()
	{
		return this.destroyNow;
	}
	
	public void updateWorldPosition(int x, int y)
	{
		this.worldX = x;
		this.worldY = y;
	}
	
	public int getWorldX()
	{
		return this.worldX;
	}
	
	public int getWorldY()
	{
		return this.worldY;
	}
	
	public int getScreenX()
	{
		return this.screenX;
	}
	
	public int getScreenY()
	{
		return this.screenY;
	}
	
	public void updateScreenPosition(int x, int y)
	{
		this.screenX = x;
		this.screenY = y;
	}
	
	public abstract void render(Graphics g);
	public abstract void clean();
	
	public void show()
	{
		this.visible = true;
	}
	
	public void hide()
	{
		this.visible = false;
	}
	
	public void gameUpdate()
	{
		update();
	}
	
	public abstract void update();
	public abstract void keyPressed(int keycode);
	public abstract void keyReleased(int keycode);
	public abstract void onMouseClick(MouseEvent e);
	public abstract void onMousePressed(MouseEvent e);
	public abstract void onMouseReleased(MouseEvent e);
}
