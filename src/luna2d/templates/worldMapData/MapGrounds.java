package luna2d.templates.worldMapData;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import luna2d.renderables.Grid;
import luna2d.templates.dataTypes.ObjectTypes;
import luna2d.engine.ColorHandler;
import luna2d.engine.Game;
import luna2d.engine.Scene;

public class MapGrounds extends Grid
{	
	private boolean worldRendering;
	public MapGrounds(Scene inScene, int x, int y,int scale, int[][] fillTypes) 
	{
		super(inScene, x, y, Game.ROWS, Game.COLUMNS, Game.CELL_SIZE, 
				ColorHandler.getColor("Green"), scale, Game.BOTTOM_DRAW_LAYER, fillTypes);
		
		this.setColors(ColorHandler.getColor("GrassGridYellow"), ColorHandler.getColor("GrassGreen"));
		this.mouseEnabled = true;
		this.enableCulling = false;
		this.worldRendering = false;
	}
	
	public void setWorldRender(boolean val)
	{
		this.worldRendering = val;
	}
	
	public boolean getWorldRendering()
	{
		return this.worldRendering;
	}
	
	@Override
	public void renderBackground(Graphics g)
	{
		if (bkgColor != null)
		{
			g.setColor(bkgColor);
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);			
		}
	}
	
	@Override 
	public void renderGrid(Graphics g)
	{
		if (!this.hideGrid)
		{
			int drawX = Game.CAMERA_X + this.x * Game.CAMERA_SCALE;
			int drawY = Game.CAMERA_Y + this.y * Game.CAMERA_SCALE;
			
			g.setColor(gridColor);
			
			for (int col = 0; col <= columns; col++)
			{
				int thisDrawX = drawX + (col * cellSize * Game.CAMERA_SCALE);
				int thisDrawY = drawY;
				
				if (!(thisDrawX > 0 && thisDrawX < Game.WIDTH)) continue; // cull
				
				g.drawLine(thisDrawX, thisDrawY, 
						drawX + (col * cellSize * Game.CAMERA_SCALE), 
						drawY + (rows * cellSize * Game.CAMERA_SCALE));
			}

			for (int row = 0; row <= rows; row++)
			{
				int thisDrawX = drawX;
				int thisDrawY = drawY + (row * cellSize * Game.CAMERA_SCALE);
				
				if (!(thisDrawY > 0 && thisDrawY < Game.HEIGHT)) continue; // cull
				
				g.drawLine(thisDrawX, thisDrawY, 
						drawX + (columns * cellSize * Game.CAMERA_SCALE), 
						drawY + (row * cellSize * Game.CAMERA_SCALE));
			}	
			
		}
	}
	
	private void renderWorldGrid(Graphics g, WorldPosition playerWP)
	{
		if (!this.hideGrid)
		{
			int drawX = Game.CAMERA_X + this.x * Game.CAMERA_SCALE;
			int drawY = Game.CAMERA_Y + this.y * Game.CAMERA_SCALE;
						
			g.setColor(gridColor);
			
			for (int col = 0; col <= columns; col++)
			{
				int thisDrawX = drawX + (col * cellSize * Game.CAMERA_SCALE);
				int thisDrawY = drawY;
				
				if (!(thisDrawX > 0 && thisDrawX < Game.WIDTH)) continue; // cull
				
				g.drawLine(thisDrawX, thisDrawY, 
						drawX + (col * cellSize * Game.CAMERA_SCALE), 
						drawY + (rows * cellSize * Game.CAMERA_SCALE));
			}

			for (int row = 0; row <= rows; row++)
			{
				int thisDrawX = drawX;
				int thisDrawY = drawY + (row * cellSize * Game.CAMERA_SCALE);
				
				if (!(thisDrawY > 0 && thisDrawY < Game.HEIGHT)) continue; // cull
				
				g.drawLine(thisDrawX, thisDrawY, 
						drawX + (columns * cellSize * Game.CAMERA_SCALE), 
						drawY + (row * cellSize * Game.CAMERA_SCALE));
			}	
			
		}
	}
	
	public void worldRender(Graphics g, WorldPosition playerWP)
	{
		this.renderBackground(g);
		this.renderWorldGrid(g, playerWP);
	}
	
	@Override
	public void render(Graphics g)
	{		
		if (!this.visible) return;
		
		if (this.fillTypes == null)
		{
			return;
		}
		
		if (this.worldRendering)
		{
			return;
		}
		
		this.renderBackground(g);
		
		for(int row = 0; row < rows; row++)
		{
			for(int col = 0; col < columns; col++)
			{
				if (this.fillTypes[row][col] == ObjectTypes.Empty.intValue) continue;
				
				int cx = col * cellSize * this.scale;
				int cy = row * cellSize * this.scale;
				
				int drawX = Game.CAMERA_X + this.x + cx;
				int drawY = Game.CAMERA_Y + this.y + cy;
				
				// Culling				
				if (this.enableCulling && !Game.getScreenBounds().contains(
						new Point(drawX - Game.CAMERA_X + (cellSize), drawY - Game.CAMERA_Y + (cellSize))))
				{
					continue;
				}
					
				Color groundColor = Color.black;
				
				ObjectTypes gndType = ObjectTypes.values()[this.fillTypes[row][col]];
				
				switch(gndType)
				{
				case Bush:
					break;
				case Empty:
					break;
				case GndDirt:
					break;
				case GndGrass:
					groundColor = ColorHandler.getColor("GrassGreen");
					break;
				case GndRock:
					break;
				case GndWater:
					groundColor = ColorHandler.getColor("WaterBlue");
					break;
				case Player:
					break;
				case Tree:
					break;
				case Water:
					break;
				case Wolf:
					break;
				default:
					break;
				
				}
				
				g.setColor(groundColor);
				
				g.fillRect(drawX, drawY, 
						Math.round(cellSize * this.scale), 
						Math.round(cellSize * this.scale));
			}
		}
		
		this.renderGrid(g);
	}

	@Override
	public void update() 
	{
	}

	@Override
	public void updateScreenPosition(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
}