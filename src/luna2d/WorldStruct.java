package luna2d;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import luna2d.Log;
import luna2d.Scene;
import luna2d.Utilites;
import luna2d.Vector2;
import luna2d.templates.Player;

public class WorldStruct 
{
	private MapStruct[][] worldMaps;
	private WorldPosition playerWorldPosition;
	
	private String worldName;
	private Scene inScene;
	
	private boolean active;
	
	public WorldStruct(String worldName, Scene inScene, boolean gameLoad)
	{
		this.inScene = inScene;
		this.worldMaps = new MapStruct[Game.ROWS][Game.COLUMNS];
		
		if (gameLoad) return;
		
		this.worldName = worldName;
		worldName = "w_" + worldName;		
		
		String[][] mapsInWorld = Game.loadCSVstrings(worldName, LoadDataType.WORLD_NAMES);
		
		for (int r = 0; r < Game.ROWS; r++)
		{
			for (int c = 0; c < Game.COLUMNS; c++)
			{
				String mapName = mapsInWorld[r][c];
				this.worldMaps[r][c] = new MapStruct(mapName, this.inScene, r, c, false);
				this.worldMaps[r][c].load();
				
				if (this.worldMaps[r][c].hasPlayer())
				{
					playerWorldPosition = new WorldPosition(new Vector2(c, r), this.worldMaps[r][c].getPlayerMapPosition());
				}
			}
		}
		
		Log.println("Loading Complete: Player @ ", this.playerWorldPosition);
	}
	
	public void setActive(boolean val)
	{
		this.active = val;
	}
	
	public boolean isActive()
	{
		return this.active;
	}
	
	public MapStruct[][] getWorldMaps()
	{
		return this.worldMaps;
	}
	
	public void updateMap(int r, int c)
	{
		this.worldMaps[r][c].update();
	}
	
	public String getWorldName()
	{
		return this.worldName;
	}
	
	public void setPlayerWorldPosition(WorldPosition wp)
	{
		this.playerWorldPosition = wp;
	}
	
	public void updatePlayerWorldPosition(WorldPosition wp)
	{
		this.addObjectToWorld(ObjectTypes.Player, wp);
	}
	
	public WorldPosition getPlayerWorldPosition()
	{
		return this.playerWorldPosition;
		
	}
	
	public void addObjectToWorld(ObjectTypes type, WorldPosition wp)
	{
		if (this.worldMaps == null) return;
		if (!this.isActive()) return;
		
		this.worldMaps[wp.worldRow][wp.worldColumn].addObjectToWorld(type, wp.mapRow, wp.mapColumn);
	}
	
	/*
	 * Returns Vector2 (COLUMN, ROW)    NOT r, c
	 */
	public Vector2 getPlayerOnMapRC()
	{
		return this.playerWorldPosition.getMapPos();
	}
	
	// --------------------------------------------------------------------------------------------------
	//                                  SAVING AND LOADING
	// --------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------
	//                 LOADING
	// --------------------------------------------------------------------
	public void injectData(MapStruct[][] data)
	{
		this.worldMaps = new MapStruct[Game.ROWS][Game.COLUMNS];
		this.worldMaps = data;
	}
	
	// --------------------------------------------------------------------
	//                 SAVING
	// --------------------------------------------------------------------
	public static void saveEntireWorld(String gameName, MapStruct[][] worldMaps, Player p)
	{
		saveGameFile(gameName, p);
		
		// Save world
		Log.println("Saving world: " + gameName);
		for(int r = 0; r < Game.ROWS; r++)//for each row
		{
		   for(int c = 0; c < Game.COLUMNS; c++)//for each column
		   {
			   saveWorldMap(gameName, worldMaps[r][c]);
		   }
		}
		Log.println(gameName + " World Saved");
	}
	
	private static void saveGameFile(String gameName, Player p)
	{
		String path = Game.GAME_SAVE_DIR + "/" + gameName + "/" + gameName + ".thdat";
		
		StringBuilder builder = new StringBuilder();
		
		// player position
		WorldPosition wp = p.getWorldPosition();
		builder.append(wp.worldRow + "," + wp.worldColumn + "," + wp.mapRow + "," + wp.mapColumn);
		
		Utilites.saveStringToFile(builder.toString(), path);
		
		// Backpack and player stats
		p.save(gameName);		
		
	}
	
	public static void saveWorldMap(String gameName, MapStruct map)
	{
		String path = Game.GAME_SAVE_DIR + "/" + gameName + "/";
		
		Utilites.createDirectory(path);
		path = path + "s_" + gameName + "_" + map.getWorldPosition().worldRow + "-" +  map.getWorldPosition().worldColumn + ".ths";
		
		StringBuilder builder = new StringBuilder();
		
		int[][] mapData = map.getMapData();
		
		for(int r = 0; r < Game.ROWS; r++)//for each row
		{
		   for(int c = 0; c < Game.COLUMNS; c++)//for each column
		   {
			   int oType = mapData[r][c];
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
			
			saveWorldGround(gameName, map);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void saveWorldGround(String gameName, MapStruct map)
	{
		
		String path = Game.GAME_SAVE_DIR + "/" + gameName + "/";
		Utilites.createDirectory(path);
		path = path + "s_" + gameName + "_" + map.getWorldPosition().worldRow + "-" +  map.getWorldPosition().worldColumn + ".thsg";
		
		int[][] mapGrounds = map.getMapGroundsData();
		
		StringBuilder builder = new StringBuilder();
		
		for(int r = 0; r < Game.ROWS; r++)
		{
		   for(int c = 0; c < Game.COLUMNS; c++)
		   {
			   int oType = mapGrounds[r][c];
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