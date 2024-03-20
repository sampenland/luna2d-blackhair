package luna2d.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import luna2d.engine.Game;
import luna2d.engine.Scene;
import luna2d.maths.Maths;

public class UITextInput extends UI 
{	
	private Rectangle drawRect;
	private Color bkgColor;
	private Color textColor;
	private Color highlightColor;
	
	private String currentText;
	private int max;
	private int paddingX;
	private int paddingY;
	
	private boolean focused;
	public boolean mouseClicked;
	
	public UITextInput(Scene inScene, String placeHolderText, int x, int y, int w, int h, int max) 
	{
		super(inScene, 1);
	    this.destroyNow = false;
	    this.max = max;
	    
	    this.bkgColor = Color.white;
	    this.textColor = Color.black;
	    this.highlightColor = Color.yellow;
	    
	    this.focused = false;
	    this.mouseClicked = false;
	    
	    this.drawRect = new Rectangle( x, y, w, h);
	    this.currentText = placeHolderText;
	    this.paddingX = 6;
	    this.paddingY = h / 2 + 6;
	    
	    this.inputEnabled = true;
	    this.mouseEnabled = true;
	    this.visible = false;
	}
	
	@Override
	public void updateScreenPosition(int x, int y)
	{
		super.updateScreenPosition(x, y);
		this.drawRect.x = x;
		this.drawRect.y = y;
	}
	
	public void updateText(String text)
	{
		this.currentText = text;
	}
	
	public void toggleVisible()
	{
		if (this.visible)
		{
			this.hide();
		}
		else
		{
			this.show();
		}
	}
	
	public void hide()
	{
		this.inputEnabled = false;
		this.visible = false;
	}
	
	public void show()
	{
		this.inputEnabled = true;
		this.visible = true;
	}
	
	public void setFocus(boolean val)
	{
		this.focused = val;
	}
	
	public String getText()
	{
		return this.currentText;
	}
	
	public void setColor(Color bkg, Color text, Color highlightColor)
	{
		this.bkgColor = bkg;
		this.textColor = text;
		this.highlightColor = highlightColor;
	}

	@Override
	public void render(Graphics g) 
	{
		if(!this.visible) return;
		
		if (this.focused)
		{
			g.setColor(highlightColor);
		}
		else
		{
			g.setColor(bkgColor);
		}
		
		g.fillRect(this.drawRect.x, this.drawRect.y, this.drawRect.width, this.drawRect.height);
		g.setColor(textColor);
		g.drawString(this.currentText, this.drawRect.x + this.paddingX, this.drawRect.y + this.paddingY);
	}
	
	@Override
	public void update() 
	{
		if (this.mouseClicked)
		{
			this.setFocus(true);
		}
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
		if (!this.focused) return;
		
		if (keycode == -1 || !Maths.characterIsAlphaNumeric(keycode, true)) return;

		if (this.currentText.length() >= this.max && keycode != KeyEvent.VK_BACK_SPACE) return;
		
		if (keycode == KeyEvent.VK_BACK_SPACE && this.currentText.length() > 0)
		{
			this.currentText = this.currentText.substring(0, this.currentText.length() - 1);
			return;
		}
		else if (keycode == KeyEvent.VK_BACK_SPACE)
		{
			return;
		}
		
		this.currentText += (char)keycode;
	}

	@Override
	public void onMouseClick(MouseEvent e) 
	{
		if (!this.visible) return;
		this.mouseClicked = this.drawRect.contains(new Point(Game.mouseX, Game.mouseY));
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
