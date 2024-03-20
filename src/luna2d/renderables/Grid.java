package luna2d.renderables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import luna2d.engine.Game;
import luna2d.engine.Log;
import luna2d.engine.Scene;

public class Grid extends Renderable
{

	protected int x, y, rows, columns, cellSize;
	protected Color gridColor, bkgColor;
	public boolean hideGrid;
	
	protected int[][] fillTypes;
	public int clickedRow;
	public int clickedColumn;
	
	public Grid(Scene inScene, int x, int y, int rows, int columns, int cellSize, Color gridColor, int scale, int depth, int[][] fillTypes) 
	{
		super(inScene, x, y, scale, depth);
		
		this.x = x;
		this.y = y;
		this.rows = rows;
		this.columns = columns;
		this.cellSize = cellSize;
		this.gridColor = gridColor;
		this.scale = scale;
		this.fillTypes = fillTypes;
		this.hideGrid = false;
		
		this.mouseClicked = false;
		this.clickedRow = -1;
		this.clickedColumn = -1;
	}
	
	public void updateFillTypes(int[][] fillTypes)
	{
		this.fillTypes = fillTypes;
	}
	
	public void setColors(Color gridColor, Color bkgColor)
	{
		this.gridColor = gridColor;
		this.bkgColor = bkgColor;
	}
	
	public void renderBackground(Graphics g)
	{
		if (bkgColor != null)
		{
			g.setColor(bkgColor);
			g.fillRect(
					Game.CAMERA_X + this.x, 
					Game.CAMERA_Y + this.y, 
					cellSize * columns, cellSize * rows);			
		}
	}
	
	public void renderGrid(Graphics g)
	{
		if (!this.hideGrid)
		{
			Log.println("Rendering grid", Game.CAMERA_X + ", " + Game.CAMERA_Y);
			int drawX = Game.CAMERA_X + this.x * Game.CAMERA_SCALE;
			int drawY = Game.CAMERA_Y + this.y * Game.CAMERA_SCALE;
			
			g.setColor(gridColor);
			for (int col = 0; col <= columns; col++)
			{
				g.drawLine(drawX + (col * cellSize * Game.CAMERA_SCALE), 
						drawY, 
						drawX + (col * cellSize * Game.CAMERA_SCALE), 
						drawY + (rows * cellSize * Game.CAMERA_SCALE));
			}
			
			for (int row = 0; row <= rows; row++)
			{
				g.drawLine(drawX, 
						drawY + (row * cellSize * Game.CAMERA_SCALE), 
						drawX + (columns * cellSize * Game.CAMERA_SCALE), 
						drawY + (row * cellSize * Game.CAMERA_SCALE));
			}	
		}
	}
	
	@Override
	public void render(Graphics g) 
	{
		if (!this.visible) return;
		
		this.renderBackground(g);		
		this.renderGrid(g);
	}

	@Override
	public void update() 
	{}

	@Override
	public void updateScreenPosition(int x, int y) 
	{
	}

	@Override
	public void onMouseClick(MouseEvent e) 
	{		
		for(int row = 0; row < rows; row++)
		{
			for(int col = 0; col < columns; col++)
			{
				int cx = col * cellSize * this.scale;
				int cy = row * cellSize * this.scale;
				
				Rectangle r = new Rectangle(this.x + cx, this.y + cy, Math.round(cellSize * this.scale), Math.round(cellSize * this.scale));
				if (r.contains(new Point(Game.mouseX,Game.mouseY)))
				{
					this.mouseClicked = true;
					this.mouseClickEvent = e;
					this.clickedRow = row;
					this.clickedColumn = col;
					break;
				}
			}
			
			if(this.mouseClicked) break;
		}	
	}

	@Override
	public void onMousePressed(MouseEvent e) 
	{		
	}

	@Override
	public void onMouseReleased(MouseEvent e) 
	{
		this.mouseClicked = false;
		this.mouseClickEvent = null;
		this.clickedRow = -1;
		this.clickedColumn = -1;
	}

}
