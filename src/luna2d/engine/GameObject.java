package luna2d.engine;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import luna2d.renderables.Renderable;
import luna2d.templates.worldMapData.WorldPosition;

public abstract class GameObject 
{	
	protected WorldPosition worldPosition;
	
	public boolean inputEnabled = false;
	public boolean mouseEnabled = false;
	private int worldX, worldY;
	protected int screenX, screenY, objectType; 
	protected float velocityX, velocityY;
	private float friction;
	protected HashMap<Integer, Boolean> keys;
	private boolean destroyNow;

	public Scene inScene;
	public boolean mouseClicked;
	
	public GameObject(int x, int y, int objectType, boolean inputEnabled, Scene inScene)
	{
		this.inScene = inScene;
		this.keys = new HashMap<Integer, Boolean>();
		
		this.worldX = x;
		this.worldY = y;
		this.objectType = objectType;
		this.inputEnabled = inputEnabled;
		this.mouseClicked = false;
		
		this.inScene.getObjectHandler().addObject(this);
	}
	
	public void updateWorldPosition(WorldPosition wp)
	{
		this.worldPosition = wp;
	}
	
	public int getWorldX()
	{
		return this.worldX;
	}
	
	public int getWorldY()
	{
		return this.worldY;
	}
	
	public void updateWorldPosition(int x, int y)
	{
		this.worldX = x;
		this.worldY = y;
	}
	
	public abstract void onDestroy();
	
	public void destroy()
	{
		this.onDestroy();
		this.destroyNow = true;
	}
	
	public boolean getDestroyNow()
	{
		return this.destroyNow;
	}
	
	public Scene getScene()
	{
		return this.inScene;
	}
	
	public void setVelocity(float x, float y, float friction)
	{
		this.velocityX = x;
		this.velocityY = y;
		this.friction = friction;
	}
	
	protected Game getGame()
	{
		return this.inScene.getGame();
	}
	
	protected void addObjectToHandler(GameObject o) 
	{
		this.inScene.getObjectHandler().addObject(o);
	}
	
	protected void addRenderableToHandler(Renderable r)
	{
		this.inScene.getObjectHandler().addRenderable(r);
	}
	
	public void gameUpdate()
	{
		this.worldX += this.velocityX;
		this.worldY += this.velocityY;
		
		if (this.velocityX > 0)
		{
			this.velocityX -= this.friction;
		}
		else
		{
			this.velocityX += this.friction;
		}
		
		if (this.velocityY > 0)
		{
			this.velocityY -= this.friction;	
		}
		else
		{
			this.velocityY += this.friction;
		}
		
		this.update();
	}
	
	protected abstract void render(Graphics g);
	protected abstract void update();
	
	protected void keyPressed(int keycode)
	{
		this.keys.put(keycode, true);
	}
	
	protected void keyReleased(int keycode)
	{
		this.keys.put(keycode, false);
	}
	
	public boolean isKeyPressed(int keycode)
	{
		if (this.keys.get(keycode) == null) return false;
		return this.keys.get(keycode);
	}
	
	protected abstract void onMouseClick(MouseEvent e);
	protected abstract void onMousePressed(MouseEvent e);
	protected abstract void onMouseReleased(MouseEvent e);
	protected abstract void pauseTick();
	
}
