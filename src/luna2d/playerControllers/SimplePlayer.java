package luna2d.playerControllers;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import luna2d.engine.Game;
import luna2d.engine.GameObject;
import luna2d.engine.Scene;
import luna2d.renderables.Sprite;

public class SimplePlayer extends GameObject
{
	private boolean zoomingEnabled = false;
	protected int moveSpeed = 5;
	protected float health = 100;
	protected int realWorldX, realWorldY;
	private int worldX, worldY;
	
	protected Sprite sprite;
	
	public SimplePlayer(Scene inScene, String imageName, int x, int y, int scale, int cellSize, int frames, int msBetweenFrames) 
	{
		super(0, 0, Game.PLAYER_ID, true, inScene);
		
		this.inputEnabled = true;
		this.mouseEnabled = true;
		
		this.worldX = x;
		this.worldY = y;
		
		sprite = new Sprite(inScene, imageName, 0, 0, scale, Game.TOP_DRAW_LAYER -1, cellSize, frames, msBetweenFrames);
		sprite.setFixedScreenPosition(true);
		sprite.updateScreenPosition(Game.WIDTH / 2 - sprite.getWidth(), Game.HEIGHT / 2 - sprite.getHeight());
	}
	
	public int getInternalX()
	{
		return this.worldX;
	}
	
	public int getInternalY()
	{
		return this.worldY;
	}
	
	public int getWorldX()
	{
		return this.realWorldX;
	}
	
	public int getWorldY()
	{
		return this.realWorldY;
	}
	
	public void updateWorldPosition(int x, int y)
	{
		this.worldX = x;
		this.worldY = y;
	}
	
	public int getGridX()
	{
		return this.worldX / this.sprite.getWidth();
	}
	
	public int getGridY()
	{
		return this.worldY / this.sprite.getHeight();
	}
	
	public int getWidth()
	{
		return this.sprite.getWidth();
	}
	
	public int getHeight()
	{
		return this.sprite.getHeight();
	}
	
	public void setZoomingEnabled(boolean val)
	{
		this.zoomingEnabled = val;
	}
	
	public void setMoveSpeed(int speed)
	{
		this.moveSpeed = speed;
	}

	@Override
	protected void render(Graphics g) 
	{	
	}

	@Override
	protected void update()
	{
		this.realWorldX = this.worldX + Game.WIDTH/2 - this.getWidth()/2; 
		this.realWorldY = this.worldY + Game.HEIGHT/2 - this.getHeight()/2;
		this.playerUpdate();
	}
	
	public void playerUpdate() 
	{
		if (this.isKeyPressed(KeyEvent.VK_W) || this.isKeyPressed(KeyEvent.VK_UP))
		{
			this.worldY -= this.moveSpeed;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_S) || this.isKeyPressed(KeyEvent.VK_DOWN))
		{
			this.worldY += this.moveSpeed;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_A) || this.isKeyPressed(KeyEvent.VK_LEFT))
		{
			this.worldX -= this.moveSpeed;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_D) || this.isKeyPressed(KeyEvent.VK_RIGHT))
		{
			this.worldX += this.moveSpeed;
		}
		
		this.getGame().updateCameraOffset(this.worldX, this.worldY);
		
		if (this.zoomingEnabled)
		{
			if (this.isKeyPressed(KeyEvent.VK_1)) 
			{
				this.getGame().updateScale(1);
			}
			
			if (this.isKeyPressed(KeyEvent.VK_2)) 
			{
				this.getGame().updateScale(2);
			}
			
			if (this.isKeyPressed(KeyEvent.VK_3)) 
			{
				this.getGame().updateScale(3);
			}
			
			if (this.isKeyPressed(KeyEvent.VK_4)) 
			{
				this.getGame().updateScale(4);
			}
			
			if (this.isKeyPressed(KeyEvent.VK_5)) 
			{
				this.getGame().updateScale(5);
			}	
		}
	}

	@Override
	protected void onMouseClick(MouseEvent e) 
	{
	}

	@Override
	protected void onMousePressed(MouseEvent e) 
	{
	}

	@Override
	protected void onMouseReleased(MouseEvent e) 
	{	
	}

	@Override
	public void onDestroy() 
	{
	}

	@Override
	protected void pauseTick() 
	{
	}
}
