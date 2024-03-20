package luna2d.renderables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import luna2d.engine.Game;
import luna2d.engine.Scene;

public class Rect extends Renderable
{

	private Color color;
	private boolean filled = true;
	
	public Rect(Scene inScene, int x, int y, int w, int h, boolean filled, Color c, int scale, int depth) 
	{
		super(inScene, x, y, scale, depth);
		
		this.color = c;
		
		this.screenX = x;
		this.screenY = y;
		this.setWidth(w);
		this.setHeight(h);
		this.filled = filled;
	}
	
	@Override
	public void render(Graphics g) 
	{
		if (!this.visible) return;
		
		g.setColor(this.color);
		
		if (this.filled)
		{
			g.fillRect(Game.CAMERA_X + this.screenX, Game.CAMERA_Y + this.screenY, 
					Math.round(this.getWidth() * this.scale * Game.CAMERA_SCALE), 
					Math.round(this.getHeight() * this.scale * Game.CAMERA_SCALE));
		}
		else
		{
			g.drawRect(Game.CAMERA_X + this.screenX, Game.CAMERA_Y + this.screenY, 
					Math.round(this.getWidth() * this.scale * Game.CAMERA_SCALE), 
					Math.round(this.getHeight() * this.scale * Game.CAMERA_SCALE));
		}
		
	}

	@Override
	public void update() 
	{

	}

	@Override
	public void updateScreenPosition(int x, int y) 
	{
		this.screenX = x;
		this.screenY = y;
	}
	
	public void updateSize(int w, int h)
	{
		this.setWidth(w);
		this.setHeight(h);
	}
	
	public void updateColor(Color c)
	{
		this.color = c;
	}

	@Override
	public void onMouseClick(MouseEvent e) 
	{
	}

	@Override
	public void onMousePressed(MouseEvent e) 
	{
	}

	@Override
	public void onMouseReleased(MouseEvent e) 
	{
	}

}
