package luna2d.templates.worldMapData;

import luna2d.engine.ColorHandler;
import luna2d.engine.Game;
import luna2d.engine.SaveLoadHandler;
import luna2d.engine.Scene;
import luna2d.maths.Maths;
import luna2d.maths.Vector2;
import luna2d.templates.dataTypes.LoadDataType;
import luna2d.templates.dataTypes.ObjectTypes;

public class MapStruct 
{
	public static final int MAP_SCALE = 5;
	private int[][] mapData;
	private int[][] mapDataGrounds;
	private String mapName;
	
	private WorldPosition worldPosition;
	
	MapGrounds grounds;
	Scene inScene;
	
	public MapStruct(String mapName, Scene inScene, int r, int c, boolean gameLoad) // r, c is row and col of world
	{
		this.inScene = inScene;
		
		if (gameLoad)
		{
			this.worldPosition = new WorldPosition(new Vector2(c, r), new Vector2(0, 0));
			return;
		}
		
		this.mapName = mapName;
		mapName = "m_" + mapName;
		
		this.worldPosition = new WorldPosition(new Vector2(c, r), new Vector2(0, 0));
		
		this.mapData = SaveLoadHandler.loadCSVints(mapName, LoadDataType.MAP);
		this.mapDataGrounds = SaveLoadHandler.loadCSVints(mapName, LoadDataType.GROUNDS);
	}
	
	public String getMapName()
	{
		return this.mapName;
	}
	
	public WorldPosition getWorldPosition()
	{
		return this.worldPosition;
	}
	
	public void clearPlayer()
	{
		for (int r = 0; r < Game.ROWS; r++)
		{
			for (int c = 0; c < Game.COLUMNS; c++)
			{
				if (this.mapData[r][c] == ObjectTypes.Player.intValue)
				{
					this.mapData[r][c] = ObjectTypes.Empty.intValue;
					return;
				}
			}
		}
	}
	
	public void load()
	{
		grounds = new MapGrounds(this.inScene, this.worldPosition.worldColumn * (Game.CELL_SIZE * Game.COLUMNS), 
				this.worldPosition.worldRow * (Game.CELL_SIZE * Game.ROWS), MAP_SCALE, this.mapDataGrounds);
		
		grounds.setWorldRender(true);
		grounds.updateWorldPosition(this.worldPosition);
		grounds.setColors(ColorHandler.getColor("GrassGridYellow"), ColorHandler.getColor("GrassGreen"));
		grounds.enableCulling = true;
		
		// Populate objects
		Vector2 wp = this.worldPosition.getWorldPos();
		for (int r = 0; r < Game.ROWS; r++)
		{
			for (int c = 0; c < Game.COLUMNS; c++)
			{
				WorldPosition thisWorldPosition = new WorldPosition(wp, new Vector2(c, r));
				int x = c * Game.CELL_SIZE * MAP_SCALE;
				int y = (r * Game.CELL_SIZE) * MAP_SCALE;
						
				x += wp.x * Game.CELL_SIZE * Game.COLUMNS * MAP_SCALE;
				y += wp.y * Game.CELL_SIZE * Game.ROWS * MAP_SCALE;
				
				ObjectTypes objectType = ObjectTypes.values()[mapData[r][c]];
				
				switch(objectType)
				{
				case Empty:
					break;
					
				case Bush:
					break;
				case GrowingBerryBush:
					break;
				case GndDirt:
					break;
					
				case GndGrass:
					break;
					
				case GndRock:
					break;
					
				case GndWater:
					break;
					
				case Player:
//					Player p = new Player(this.inScene, "Player", 0, 0, 1, 16, 4, 250);
//					x = x - Game.WIDTH / 2 + (Game.CELL_SIZE * Game.CAMERA_SCALE) + (Game.CELL_SIZE * MAP_SCALE / 2);
//					y = y - Game.HEIGHT / 2 + (Game.CELL_SIZE * Game.CAMERA_SCALE) + (Game.CELL_SIZE * MAP_SCALE / 2);
//					p.updateWorldPosition(x, y);
//					p.updateWorldPosition(thisWorldPosition);
					break;
					
				case Tree:
					break;
					
				case Water:
					break;
					
				case Wolf:
					break;
				case InvBerries:
					break;
				case InvRock:
					break;
				case Rock:
					break;
				case Fire:
					break;
				case Torch:
					break;
				default:
					break;
				}
			}
		}
	}
	
	public void update()
	{
		
	}
	
	public Vector2 getPlayerMapPosition()
	{
		for(int r = 0; r < Game.ROWS; r++)
		{
			for (int c = 0; c < Game.COLUMNS; c++)
			{
				if (this.mapData[r][c] == ObjectTypes.Player.intValue)
				{
					return new Vector2(c, r);
				}
			}
		}
		
		return new Vector2(-1, -1);
	}
	
	public boolean hasPlayer()
	{
		for(int r = 0; r < Game.ROWS; r++)
		{
			for (int c = 0; c < Game.COLUMNS; c++)
			{
				if (this.mapData[r][c] == ObjectTypes.Player.intValue)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void addObjectToWorld(ObjectTypes type, int r, int c)
	{
		if (this.mapData != null)
		{
			this.mapData[r][c] = type.intValue;
		}
	}
	
	public void createPlayer(WorldPosition wp)
	{
//		Vector2 xyPos = Maths.convertWorldPositionToXY(wp, Game.CELL_SIZE, Game.ROWS, Game.COLUMNS, MAP_SCALE);
//		
//		int x = xyPos.x;
//		int y = xyPos.y;
//		
//		Player p = new Player(this.inScene, "Player", 0, 0, 1, 16, 4, 250);
//		x = x - Game.WIDTH / 2 + (Game.CELL_SIZE * Game.CAMERA_SCALE) + (Game.CELL_SIZE * MAP_SCALE / 2);
//		y = y - Game.HEIGHT / 2 + (Game.CELL_SIZE * Game.CAMERA_SCALE) + (Game.CELL_SIZE * MAP_SCALE / 2);
//		p.updateWorldPosition(x, y);
//		p.updateWorldPosition(wp);
	}
	
	public int[][] getMapData()
	{
		return this.mapData;
	}
	
	public int[][] getMapGroundsData()
	{
		return this.mapDataGrounds;
	}
	
	public void injectData(String mapName, int[][] mapData, int[][] groundData)
	{
		this.mapName = mapName;
		
		this.mapData = new int[Game.ROWS][Game.COLUMNS];
		this.mapData = mapData;
		
		this.mapDataGrounds = new int[Game.ROWS][Game.COLUMNS];
		this.mapDataGrounds = groundData;
		
		this.load();
	}
	
}