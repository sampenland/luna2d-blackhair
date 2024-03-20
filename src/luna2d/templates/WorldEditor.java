package luna2d.templates;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import luna2d.engine.Game;
import luna2d.engine.Log;
import luna2d.engine.Scene;
import luna2d.renderables.TextDisplay;
import luna2d.ui.UIButton;
import luna2d.ui.UITextInput;
import luna2d.templates.ui.WorldEditorMenu;
import luna2d.templates.worldMapData.MapGrounds;
import luna2d.templates.dataTypes.ObjectTypes;

public class WorldEditor extends Scene
{

	private MapGrounds grounds;
	private int[][] maps;
	private String[][] mapNames;

	private UITextInput mapNameInput;
	private UIButton mapNameSaveButton, mapNamesCloseButton;
	private int nameRow, nameColumn;
	
	public WorldEditorMenu detailedMenu;
	
	public WorldEditor(String name) 
	{
		super(name);
		this.setMouseEnabled(true);
		nameRow = -1;
		nameColumn = -1;
	}
	
	public void closeMenu()
	{		
		this.detailedMenu.close();
	}
	
	public void injectMapData(int[][] data, String[][] names)
	{
		clearMaps();
		clearMapNames();
		
		for(int r = 0; r < Game.ROWS; r++)
		{
			for(int c = 0; c < Game.COLUMNS; c++)
			{		
				maps[r][c] = data[r][c];
				mapNames[r][c] = names[r][c];
			}
		}
		
		grounds.updateFillTypes(maps);
		this.closeMenu();
	}
	
	private void clearMapNames()
	{
		mapNames = new String[Game.ROWS][Game.COLUMNS];
		
		for(int r = 0; r < Game.ROWS; r++)
		{
			for(int c = 0; c < Game.COLUMNS; c++)
			{		
				mapNames[r][c] = "Water";
			}
		}
		
	}
	
	private void clearMaps()
	{
		maps = new int[Game.ROWS][Game.COLUMNS];
		
		for(int r = 0; r < Game.ROWS; r++)
		{
			for(int c = 0; c < Game.COLUMNS; c++)
			{		
				maps[r][c] = ObjectTypes.GndWater.intValue;
			}
		}
		
	}
	
	public void saveMap(String name)
	{
		String path = Game.DATA_DIR + "/" + name + ".thw";
		
		StringBuilder builder = new StringBuilder();
		
		for(int r = 0; r < Game.ROWS; r++)//for each row
		{
		   for(int c = 0; c < Game.COLUMNS; c++)//for each column
		   {
			   int oType = this.maps[r][c];
			   builder.append(oType + "");

			   if(c < Game.COLUMNS - 1)
			   {
				   builder.append(",");
			   }
		   }
		   
		   builder.append("\n");
		
		}
		
		BufferedWriter writer;
		try
		{
			writer = new BufferedWriter(new FileWriter(path));
			writer.write(builder.toString());
			writer.close();	
			
			this.saveNames(name);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	protected void checkKeys()
	{
		// Override this function
	}

	private void saveNames(String name)
	{
		String path = Game.DATA_DIR + "/" + name + ".thwn";
		
		StringBuilder builder = new StringBuilder();
		
		for(int r = 0; r < Game.ROWS; r++)
		{
		   for(int c = 0; c < Game.COLUMNS; c++)
		   {
			   String mapName = this.mapNames[r][c];
			   builder.append(mapName);

			   if(c < Game.COLUMNS - 1)
			   {
				   builder.append(",");
			   }
		   }
		   
		   builder.append("\n");
		
		}
		
		BufferedWriter writer;
		try
		{
			writer = new BufferedWriter(new FileWriter(path));
			writer.write(builder.toString());
			writer.close();			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void start() 
	{
		Log.println("World Editor started.");
		
		grounds = new MapGrounds(this, 0, Game.GRIDY_OFFSET, 1, null);
		
		clearMaps();
		clearMapNames();
		
		grounds.updateFillTypes(maps);
		
		new TextDisplay(this, "Left Click to place map. Right click to name it.", Game.WIDTH / 2 - 150, 
				Game.HEIGHT - 60, Color.white, Game.TOP_DRAW_LAYER);
		
		mapNameInput = new UITextInput(this, "MAP00", 0, 0, 100, 20, 8);
		mapNameInput.inputEnabled = true;
		mapNameInput.setColor(Color.white, Color.black, Color.yellow);
		mapNameInput.visible = false;
		
		mapNameSaveButton = new UIButton(this, "Save", 0, 0, 40, 20);
		mapNameSaveButton.setColors(Color.black, Color.white);
		mapNameSaveButton.visible = false;
		
		mapNamesCloseButton = new UIButton(this, "Close", 0, 0, 40, 20);
		mapNamesCloseButton.setColors(Color.white, Color.black);
		mapNamesCloseButton.visible = false;
		
		this.detailedMenu = new WorldEditorMenu(this, Game.WIDTH / 2 - 150, Game.HEIGHT / 2 - 100, 300, 200, new Color(1, 1, 1, 0.45f), 1);
		
	}

	@Override
	public void end() 
	{
		Log.println("World Editor ended.");
	}
	
	private void mapNamesInputHide()
	{
		this.mapNameInput.hide();
		this.mapNameInput.setFocus(false);
		this.mapNameSaveButton.visible = this.mapNameInput.visible;
		this.mapNamesCloseButton.visible = this.mapNameInput.visible;
		this.nameRow = -1;
		this.nameColumn = -1;
	}

	@Override
	public void update() 
	{
		if (this.mapNameInput.visible) 
		{
			if (this.mapNameSaveButton != null && this.mapNameSaveButton.mouseClicked)
			{
				this.mapNameSaveButton.mouseClicked = false;
				this.mapNames[this.nameRow][this.nameColumn] = this.mapNameInput.getText();
				mapNamesInputHide();
			}
			else if (this.mapNamesCloseButton != null && this.mapNamesCloseButton.mouseClicked)
			{
				this.mapNamesCloseButton.mouseClicked = false;
				mapNamesInputHide();
			}
			
			return;
		}
		
		this.checkKeys();
	}

	@Override
	protected void onMouseClick(MouseEvent e) 
	{
		if (this.detailedMenu != null && this.detailedMenu.visible) return;
		if (this.mapNameInput.visible) return;
		
		if (this.grounds != null && this.grounds.mouseClicked)
		{
			if (this.grounds.mouseClickEvent != null && this.grounds.mouseClickEvent.getButton() == 1)
			{
				// left click - place map
				if (this.maps[this.grounds.clickedRow][this.grounds.clickedColumn] == ObjectTypes.GndGrass.intValue)
				{
					this.maps[this.grounds.clickedRow][this.grounds.clickedColumn] = ObjectTypes.GndWater.intValue;
					this.mapNames[this.grounds.clickedRow][this.grounds.clickedColumn] = "Water";
				}
				else
				{
					this.maps[this.grounds.clickedRow][this.grounds.clickedColumn] = ObjectTypes.GndGrass.intValue;
				}				
			}
			else if (this.grounds.mouseClickEvent != null && this.grounds.mouseClickEvent.getButton() == 3)
			{
				if (this.maps[this.grounds.clickedRow][this.grounds.clickedColumn] == ObjectTypes.GndGrass.intValue)
				{
					// Right click - name map
					int x = this.grounds.clickedColumn * Game.CELL_SIZE * Game.CAMERA_SCALE;
					int y = this.grounds.clickedRow * Game.CELL_SIZE * Game.CAMERA_SCALE;
					
					if (this.grounds.clickedColumn > Game.COLUMNS - 6)
					{
						x -= 170;
					}
					
					y -= 20;
					
					if (this.grounds.clickedRow < 5)
					{
						y += 50;
					}
					
					this.mapNameInput.updateScreenPosition(x, y);
					this.mapNameInput.updateText(this.mapNames[this.grounds.clickedRow][this.grounds.clickedColumn]);
					this.mapNameSaveButton.updateScreenPosition(x + 100, y);
					this.mapNamesCloseButton.updateScreenPosition(x + 135, y);
					this.mapNameInput.toggleVisible();
					this.mapNameSaveButton.visible = this.mapNameInput.visible;
					this.mapNamesCloseButton.visible = this.mapNameInput.visible;
					
					if (this.mapNameInput.visible)
					{
						this.nameRow = this.grounds.clickedRow;
						this.nameColumn = this.grounds.clickedColumn;
					}
					else
					{
						this.nameRow = -1;
						this.nameColumn = -1;
					}	
				}				
			}
			
			
			grounds.updateFillTypes(maps);
		}
	}

	@Override
	protected void onMousePressed(MouseEvent e) 
	{
	}

	@Override
	protected void onMouseReleased(MouseEvent e) 
	{
	}

}