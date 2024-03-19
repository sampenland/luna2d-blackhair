package luna2d.renderables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import luna2d.Game;
import luna2d.Scene;
import luna2d.ui.UI;

public class FillBar extends UI
{

	private int x, y, w, h;
	protected boolean fixedScreenPosition = false;
	
	private Rectangle bkgRect;
	private Color bkgColor;
	
	private Rectangle outlineRect;
	private Color outlineColor;
	
	private Rectangle valueRect;
	private Color valueColor;
	
	private int outlineSize;
	private int value;
	private int max;
	
	protected boolean enableCameraScaling = false;
	
	public FillBar(int value, int x, int y, int w, int h, int outlineSize, int scale,
			Color bkgColor, Color outlineColor, Color valueColor, Scene inScene) 
	{
		super(inScene, scale);
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.max = 100;
		
		this.bkgColor = bkgColor;
		this.outlineColor = outlineColor;
		this.valueColor = valueColor;
		
		this.value = value;
		this.outlineSize = outlineSize;
		
		this.bkgRect = new Rectangle();
		this.outlineRect = new Rectangle();
		this.valueRect = new Rectangle();
		
		this.update();		
		
	}
	
	public void setEnableCameraScaling(boolean val)
	{
		this.enableCameraScaling = val;
	}
	
	public boolean getCameraScaling()
	{
		return this.enableCameraScaling;
	}
	
	public void setFixedScreenPosition(boolean val)
	{
		this.fixedScreenPosition = val;
	}
	
	public int getWidth()
	{
		if (this.enableCameraScaling)
		{
			return this.bkgRect.width * Game.CAMERA_SCALE; 
		}
		
		return this.bkgRect.width;
	}
	
	protected void setMax(int max)
	{
		this.max = max;
	}

	@Override
	public void render(Graphics g) 
	{
		int drawX, drawY;
		int camScale = Game.CAMERA_SCALE;
		if (!this.enableCameraScaling)
		{
			camScale = 1;
		}
		
		if(this.fixedScreenPosition)
		{
			drawX = 0;
			drawY = 0;
		}
		else
		{
			drawX = Game.CAMERA_X * camScale;
			drawY = Game.CAMERA_Y * camScale;
		}
		
		g.setColor(outlineColor);
		g.fillRect(drawX + this.outlineRect.x, drawY + this.outlineRect.y, 
				Math.round(this.outlineRect.width * this.scale * camScale), 
				Math.round(this.outlineRect.height * this.scale * camScale));
		
		g.setColor(bkgColor);
		g.fillRect(drawX + this.bkgRect.x, drawY + this.bkgRect.y, 
				this.bkgRect.width, this.bkgRect.height);
		
		g.setColor(valueColor);
		g.fillRect(drawX + this.valueRect.x, drawY + this.valueRect.y, 
				Math.round(this.valueRect.width * this.scale * camScale) + this.outlineSize, 
				Math.round(this.outlineRect.height * this.scale * camScale) - this.outlineSize * 2);
		}

	@Override
	public void update() 
	{
		this.bkgRect.x = this.outlineRect.x = this.valueRect.x = this.x;
		this.bkgRect.y = this.outlineRect.y = this.valueRect.y = this.y;
		this.bkgRect.width = this.outlineRect.width = this.w;
		this.bkgRect.height = this.outlineRect.height = this.valueRect.height = this.h;
		
		this.outlineRect.x -= this.outlineSize;
		this.outlineRect.width += 2 * this.outlineSize;
		this.outlineRect.y -= this.outlineSize;
		this.outlineRect.height += 2 * this.outlineSize;
			
		this.valueRect.width = Math.round(this.w * (float)((float)this.value / (float)this.max));
		
	}

	@Override
	public void updateScreenPosition(int x, int y) 
	{
		this.x = x;
		this.y = y;		
	}
	
	protected void updateSize(int w, int h, int outlineSize)
	{
		this.w = w;
		this.h = h;
		this.outlineSize = outlineSize;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}

	@Override
	public void clean() 
	{
	}

	@Override
	public void keyPressed(int keycode) 
	{
	}

	@Override
	public void keyReleased(int keycode) 
	{
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
