package luna2d.templates;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;

import luna2d.engine.Game;
import luna2d.engine.Scene;
import luna2d.renderables.Sprite;
import luna2d.renderables.TextDisplay;
import luna2d.timers.SceneTask;
import luna2d.templates.worldMapData.MapGrounds;
import luna2d.templates.dataTypes.ObjectTypes;
import luna2d.templates.ui.SimpleMapEditorMenu;

public class SimpleMapEditor extends Scene
{	
	private int playerRow = 0;
	private int playerCol = 0;
	
	protected enum MOUSE_STATUS
	{
		IDLE,
		SELECTING,
		PLACING
	}
	
	protected MOUSE_STATUS mouseStatus;
	private boolean disableSwitching = false;
	private SceneTask resetInputTask;
	private Timer resetInputTimer;
	
	private ObjectTypes currentSelection;
	private ObjectTypes lastSelection;
	private Sprite currentSelectionSprite;
	
	private Sprite[][] mapDataSprites;
	private int[][] mapDataGrounds;
	private Sprite[][] selectionSprites;
	private boolean[][] mapDataSpritesPlaced;
	
	private TextDisplay statusLabel;
	public SimpleMapEditorMenu detailedMenu;
	
	private MapGrounds grounds;
	
	public SimpleMapEditor(String name) 
	{
		super(name);
	}
	
	public void closeMenu()
	{
		this.mouseStatus = MOUSE_STATUS.IDLE;
		
		this.currentSelectionSprite.updateImageRef("Player", true, false);
		this.currentSelectionSprite.setFixedScreenPosition(true);
		
		this.detailedMenu.close();
	}

	@Override
	public void start() 
	{		
		mouseStatus = MOUSE_STATUS.IDLE;
		this.currentSelection = ObjectTypes.Player;
		this.lastSelection = ObjectTypes.Empty;
		
		this.setMouseEnabled(true);
		
		this.mouseStatus = MOUSE_STATUS.IDLE;
		
		this.playerRow = 0;
		this.playerCol = 0;
		
		this.mapDataSprites = new Sprite[Game.ROWS][Game.COLUMNS];
		this.mapDataSpritesPlaced = new boolean[Game.ROWS][Game.COLUMNS];
		this.mapDataGrounds = new int[Game.ROWS][Game.COLUMNS];
		this.selectionSprites = new Sprite[Game.ROWS][Game.COLUMNS];
		
		grounds = new MapGrounds(this, 0, Game.GRIDY_OFFSET, 1, null);
		
		int count = 0;
		int countRow = 0;
		int countColumn = 0;
		for(int r = 0; r < Game.ROWS; r++)
		{
			for(int c = 0; c < Game.COLUMNS; c++)
			{	
				mapDataSpritesPlaced[r][c] = false;
				mapDataGrounds[r][c] = ObjectTypes.GndGrass.intValue;
				mapDataSprites[r][c] = new Sprite(this, "", c * 16, Game.GRIDY_OFFSET + r * 16, 1, Game.ENVIRONMENT_DRAW_LAYER);
				mapDataSprites[r][c].enableCulling = false;
				mapDataSprites[r][c].setObjectType(ObjectTypes.Empty.intValue);
				mapDataSprites[r][c].visible = false;
				
				if (count < ObjectTypes.values().length)
				{
					if (ObjectTypes.values()[count].showInMapEditor)
					{
						String imgName = ObjectTypes.values()[count].imgName;
						int objType = ObjectTypes.values()[count].intValue;
						
						if (imgName == "Player")
						{
							this.selectionSprites[countRow][countColumn] = new Sprite(this, imgName, countColumn * 16, Game.GRIDY_OFFSET + countRow * 16, 1, Game.TOP_DRAW_LAYER, 16, 16);
						}
						else
						{
							this.selectionSprites[countRow][countColumn] = new Sprite(this, imgName, countColumn * 16, Game.GRIDY_OFFSET + countRow * 16, 1, Game.TOP_DRAW_LAYER);	
						}
						
						this.selectionSprites[countRow][countColumn].setObjectType(objType);
						this.selectionSprites[countRow][countColumn].visible = false;
						
						countColumn++;
						
						if (countColumn >= Game.COLUMNS)
						{
							countColumn = 0;
							countRow++;
						}
						
					}
					
					count++;
					
				}
			}
		}
		
		this.grounds.updateFillTypes(mapDataGrounds);
		
		this.setInputEnabled(false);
		
		this.currentSelectionSprite = new Sprite(this, "Player", 0, 0, 1, 16, 16, Game.TOP_DRAW_LAYER);
		this.currentSelectionSprite.setFixedScreenPosition(true);
		
		mapDataSprites[this.playerRow][this.playerCol].updateImageRef("Player", true, 16, 16);
		mapDataSprites[this.playerRow][this.playerCol].setObjectType(ObjectTypes.Player.intValue);
		
		this.statusLabel = new TextDisplay(this, "Idle", Game.WIDTH / 2 - 64, Game.HEIGHT - 64, Color.WHITE, Game.TOP_DRAW_LAYER);
		
		this.detailedMenu = new SimpleMapEditorMenu(this, Game.WIDTH / 2 - 150, Game.HEIGHT / 2 - 100, 300, 200, new Color(0, 0, 0, 0.45f), 1);
	}
	
	public void injectMapData(int[][] data, int[][] grounds)
	{
		if (this.playerRow != -1 && this.playerCol != -1)
		{
			mapDataSprites[this.playerRow][this.playerCol].updateImageRef("", false, true);
			mapDataSprites[this.playerRow][this.playerCol].setObjectType(ObjectTypes.Empty.intValue);
		}
		
		for(int r = 0; r < Game.ROWS; r++)
		{
			for(int c = 0; c < Game.COLUMNS; c++)
			{	
				mapDataSpritesPlaced[r][c] = false;
			}
		}
		
		for(int r = 0; r < Game.ROWS; r++)
		{
			for(int c = 0; c < Game.COLUMNS; c++)
			{		
				ObjectTypes objectType = ObjectTypes.values()[data[r][c]];
				
				this.mapDataSprites[r][c].visible = false;
				this.mapDataSpritesPlaced[r][c] = false;
				
				switch(objectType)
				{
				case Empty:
					this.mapDataSprites[r][c].updateImageRef("", true, true);
					break;
					
				case Player:
					this.playerRow = r;
					this.playerCol = c;
					this.mapDataSprites[r][c].updateImageRef("Player", true, 16, 16);
					this.mapDataSpritesPlaced[r][c] = true;
					break;
					
				case Tree:
					this.mapDataSprites[r][c].updateImageRef("Tree", true, true);
					this.mapDataSpritesPlaced[r][c] = true;
					break;
					
				case Water:
					this.mapDataSprites[r][c].updateImageRef("Water", true, true);
					this.mapDataSpritesPlaced[r][c] = true;
					break;
					
				default:
					break;
				}
				
				mapDataSprites[r][c].setObjectType(objectType.intValue);
			}
		}
		
		this.closeMenu();
	}

	@Override
	public void end() 
	{
		
	}

	@Override
	public void update() 
	{
		checkKeys();
		populateGrid();
	}
	
	private void populateGrid()
	{
		switch(this.mouseStatus)
		{
		
			case IDLE:
				
				if( this.currentSelectionSprite != null)
				{
					this.currentSelectionSprite.visible = false;	
				}
				
				break;
				
			case SELECTING:
				
				if( this.currentSelectionSprite != null)
				{
					this.currentSelectionSprite.visible = false;	
				}
				
				break;
				
			case PLACING:
				
				if( this.currentSelectionSprite != null)
				{
					this.currentSelectionSprite.visible = true;	
				}
				
				placeSelection();
				break;
				
		}
	}
	
	protected void displaySelection(boolean isVisible)
	{
		if (this.currentSelectionSprite == null) return;
		
		this.currentSelectionSprite.visible = !isVisible;
		
		// Show possible selections
		for(int r = 0; r < Game.ROWS; r++)
		{
			for(int c = 0; c < Game.COLUMNS; c++)
			{
				if (this.selectionSprites[r][c] == null) continue;
				this.selectionSprites[r][c].visible = isVisible;
			}	
		}
	}
	
	private void placeSelection()
	{
		if (this.currentSelectionSprite == null) return;
		
		this.currentSelectionSprite.updateScreenPosition(Game.mouseX, Game.mouseY);
		
		if (this.currentSelection != this.lastSelection)
		{
			this.currentSelectionSprite.updateImageRef(getSelectionName(), true, false);
			this.lastSelection = this.currentSelection;
		}
	}
	
	private String getSelectionName()
	{
		switch(this.currentSelection)
		{
		case Empty:
			return "";
		case Player:
			return "Player";
		case Tree:
			return "Tree";
		case Water:
			return "Water";
		default:
			return "n/a";
		}
	}
	
	protected void checkKeys()
	{
		// Override this function
	}
	
	protected void userSwitching()
	{
		if (!this.disableSwitching)
		{
			switch(this.mouseStatus)
			{
			
				case IDLE:
					this.mouseStatus = MOUSE_STATUS.SELECTING;
					this.statusLabel.updateText("Selecting");
					this.setMapSpritesVisibility(false);
					this.displaySelection(true);
					break;
					
				case SELECTING:
					this.mouseStatus = MOUSE_STATUS.PLACING;
					this.statusLabel.updateText("Placing");
					this.setMapSpritesVisibility(true);
					this.displaySelection(false);
					break;
					
				case PLACING:
					this.mouseStatus = MOUSE_STATUS.IDLE;
					this.statusLabel.updateText("Idle");
					this.setMapSpritesVisibility(true);
					break;
					
			}
			
			// Wait 500 before you can tab again
			this.disableSwitching = true;

			resetInputTask = new SceneTask(this)
			{
				@Override
				public void run() 
				{
					SimpleMapEditor s = (SimpleMapEditor)this.scene;
					s.disableSwitching = false;
				}
			};
			resetInputTimer = new Timer("ResetInputTimer");				
			this.resetInputTimer.schedule(resetInputTask, 500);
		}
	}
	
	private void setMapSpritesVisibility(boolean v)
	{
		for (int r = 0; r < Game.ROWS; r++)
		{
			for (int c = 0; c < Game.COLUMNS; c++)
			{
				this.mapDataSprites[r][c].visible = v;
			}
		}
	}

	@Override
	public void onMouseClick(MouseEvent e) 
	{		
		if (this.grounds.clickedColumn == -1 || this.grounds.clickedRow == -1) return;
		
		Point gPos = new Point(this.grounds.clickedColumn, this.grounds.clickedRow);
		
		switch(this.mouseStatus)
		{
		
			case IDLE:				
				break;
				
			case SELECTING:
				
				if (this.selectionSprites.length > gPos.y && this.selectionSprites[gPos.y].length > gPos.x)
				{
				
					if (this.selectionSprites[gPos.y][gPos.x] != null)
					{		
						int objType = this.selectionSprites[gPos.y][gPos.x].getObjectType();
						this.currentSelection = ObjectTypes.values()[objType];
						String img = ObjectTypes.values()[this.selectionSprites[gPos.y][gPos.x].getObjectType()].imgName;
						
						if (objType == ObjectTypes.Player.intValue)
						{
							this.currentSelectionSprite.updateImageRef(img, false, 16, 16);
						}
						else if (objType == ObjectTypes.Bush.intValue)
						{
							this.currentSelectionSprite.updateImageRef(img, false, 16, 16);
						}
						else
						{
							this.currentSelectionSprite.updateImageRef(img, false, true);	
						}
						
						this.currentSelectionSprite.setObjectType(objType);
					}
					
				}	
				
				break;
				
			case PLACING:
				
				if (this.currentSelection == ObjectTypes.Player)
				{
					this.mapDataSprites[this.playerRow][this.playerCol].updateImageRef("", false, false);
					this.mapDataSprites[this.playerRow][this.playerCol].setObjectType(ObjectTypes.Empty.intValue);
					
					this.mapDataSpritesPlaced[this.playerRow][this.playerCol] = false;
					this.mapDataSprites[this.playerRow][this.playerCol].visible = false;

					this.playerCol = -1;
					this.playerRow = -1;
					
					if (!(gPos.x == this.playerCol && gPos.y == this.playerRow))
					{
						this.mapDataSprites[gPos.y][gPos.x].updateImageRef("Player", true, 16, 16);
						this.mapDataSprites[gPos.y][gPos.x].setObjectType(ObjectTypes.Player.intValue);
						this.mapDataSpritesPlaced[gPos.y][gPos.x] = true;
						
						if (this.playerRow != -1 && this.playerCol == -1)
						{
							this.mapDataSprites[this.playerRow][this.playerCol].updateImageRef("", false, false);
							this.mapDataSprites[this.playerRow][this.playerCol].setObjectType(ObjectTypes.Empty.intValue);
							this.mapDataSpritesPlaced[this.playerRow][this.playerCol] = false;
						}
						
						this.playerCol = gPos.x;
						this.playerRow = gPos.y;	
					}
				}
				else
				{
					if (!(gPos.x == this.playerCol && gPos.y == this.playerRow))
					{
						if (this.mapDataSpritesPlaced[gPos.y][gPos.x])
						{
							String imgName = "NONE";
							int objType = ObjectTypes.Empty.intValue;
							this.mapDataSprites[gPos.y][gPos.x].updateImageRef(imgName, true, 16, 16);
							this.mapDataSprites[gPos.y][gPos.x].setObjectType(objType);	
							this.mapDataSprites[gPos.y][gPos.x].visible = false;
							this.mapDataSpritesPlaced[gPos.y][gPos.x] = false;
						}
						else
						{
							String imgName = this.currentSelectionSprite.getImageName();
							int objType = this.currentSelectionSprite.getObjectType();
							this.mapDataSprites[gPos.y][gPos.x].updateImageRef(imgName, true, 16, 16);
							this.mapDataSprites[gPos.y][gPos.x].setObjectType(objType);	
							this.mapDataSprites[gPos.y][gPos.x].visible = true;
							this.mapDataSpritesPlaced[gPos.y][gPos.x] = true;
						}
					}
				}
				
				break;
				
		}
	}

	@Override
	public void onMousePressed(MouseEvent e) 
	{
	}

	@Override
	public void onMouseReleased(MouseEvent e) 
	{
	}
	
	public void saveMap(String name)
	{
		String path = Game.DATA_DIR + "/" + name + ".thm";
		
		StringBuilder builder = new StringBuilder();
		
		for(int r = 0; r < Game.ROWS; r++)//for each row
		{
		   for(int c = 0; c < Game.COLUMNS; c++)//for each column
		   {
			   int oType = this.mapDataSprites[r][c].getObjectType();
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
			
			this.saveGrounds(name);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void saveGrounds(String name)
	{
		String path = Game.DATA_DIR + "/" + name + ".thmg";
		
		StringBuilder builder = new StringBuilder();
		
		for(int r = 0; r < Game.ROWS; r++)
		{
		   for(int c = 0; c < Game.COLUMNS; c++)
		   {
			   int oType = this.mapDataGrounds[r][c];
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
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
}