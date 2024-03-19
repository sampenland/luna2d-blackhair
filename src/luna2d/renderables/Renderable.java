package luna2d.renderables;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import luna2d.Game;
import luna2d.Scene;
import luna2d.Vector2;
import luna2d.ui.UIMenu;
import luna2d.WorldPosition;

public abstract class Renderable 
{
	private Scene inScene;
	
	protected WorldPosition worldPosition;
	
	private int depth;
	public int worldX, worldY, screenX, screenY; 
	private int width, height;
	protected int scale;
	protected boolean canScale;
	public boolean visible;
	protected boolean customRender; // Means it will not be drawn from automatically but manually
	private boolean destroyNow;
	public UIMenu inMenu;
	
	public boolean enableCulling;
	public boolean inputEnabled;
	public boolean mouseEnabled;
	public boolean mouseClicked;
	public MouseEvent mouseClickEvent;
	
	public Renderable(Scene inScene, int x, int y, int scale, int depth)
	{
		this.depth = depth;
		this.inScene = inScene;
		this.canScale = true;
		
		if (this.inScene != null)
		{
			this.inScene.getObjectHandler().addRenderable(this);			
		}
		
		this.scale = scale;
		
		this.worldX = x;
		this.worldX = y;
		
		this.visible = true;
		this.customRender = false;
		this.destroyNow = false;
		this.enableCulling = true;
		this.inputEnabled = false;
		this.mouseEnabled = false;
		this.mouseClicked = false;
	}
	
	public WorldPosition getWorldPosition()
	{
		return this.worldPosition;
	}
	
	public void updateWorldPosition(WorldPosition wp)
	{
		this.worldPosition = wp;
	}
	
	public void setDepth(int depth)
	{
		if (depth < 0) depth = 0;
		if (depth > Game.DRAW_LAYERS) depth = Game.DRAW_LAYERS;
		
		this.depth = depth;
	}
	
	public int getDepth()
	{
		return this.depth;
	}
	
	public void setSize(int w, int h)
	{
		this.width = w;
		this.height = h;
	}
	
	public void setWidth(int w)
	{
		this.width = w;
	}
	
	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}
	
	public void setHeight(int h)
	{
		this.height = h;
	}
	
	public Scene getScene()
	{
		return this.inScene;
	}
	
	public void setInMenu(UIMenu menu)
	{
		this.inMenu = menu;
	}
	
	public void destroy()
	{
		this.destroyNow = true;
	}
	
	public boolean getDestroyNow()
	{
		return this.destroyNow;
	}
	
	public void enableScaling()
	{
		this.canScale = true;
	}
	
	public void disableScaling()
	{
		this.canScale = false;
	}
	
	public void setCustomRender(boolean val)
	{
		this.customRender = val;
	}
	
	public void updateWorldPosition(int x, int y)
	{
		this.worldX = x;
		this.worldY = y;
	}
	
	public void updateWorldPosition(Vector2 worldPos, Vector2 mapPos)
	{
		this.worldPosition = new WorldPosition(worldPos, mapPos);
	}
	
	public int getWorldX()
	{
		return this.worldX;
	}
	
	public int getWorldY()
	{
		return this.worldY;
	}
	
	public void updateScreenPosition(int x, int y)
	{
		this.screenX = x;
		this.screenY = y;
	}
	
	public abstract void render(Graphics g);
	
	public void customRender(Graphics g)
	{
		if (!this.visible) return;
		
		boolean cRender = this.customRender;
		this.customRender = false;
		this.render(g);
		this.customRender = cRender;
	}
	
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
	public abstract void onMouseClick(MouseEvent e);
	public abstract void onMousePressed(MouseEvent e);
	public abstract void onMouseReleased(MouseEvent e);
}
