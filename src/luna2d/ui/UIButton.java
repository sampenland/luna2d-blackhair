package luna2d.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import luna2d.Game;
import luna2d.Scene;

public class UIButton extends UI 
{

	private Color bkgColor, textColor;
	private String text;
	
	private int width, height;
	
	public boolean mouseClicked;
	
	public UIButton(Scene inScene, String text, int x, int y, int w, int h) 
	{
		super(inScene, 1);
		
		this.screenX = x;
		this.screenY = y;
		
		this.setSize(w, h);
		this.mouseClicked = false;
		
		this.text = text;
		this.bkgColor = Color.gray;
		this.textColor = Color.black;
		
		this.mouseEnabled = true;
		
		this.inScene.getGame().getObjectHandler().addUI(this);
	}
	
	public void setSize(int w, int h)
	{
		this.width = w;
		this.height = h;
	}
	
	public void updateText(String text)
	{
		this.text = text;
	}
	
	public void setColors(Color bkg, Color text)
	{
		this.bkgColor = bkg;
		this.textColor = text;
	}

	@Override
	public void render(Graphics g) 
	{
		if (!this.visible) return;
		
		g.setColor(bkgColor);
		g.fillRect(this.screenX, this.screenY, this.width, this.height);
		
		g.setColor(textColor);
		g.drawString(this.text, screenX + 5, screenY + 15);
		
	}

	@Override
	public void clean() 
	{
		this.destroy();
	}

	@Override
	public void update() 
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
		if (!this.visible) return;
		
		Rectangle r = new Rectangle(this.screenX, this.screenY, this.width, this.height);
		this.mouseClicked = r.contains(new Point(Game.mouseX, Game.mouseY));
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
