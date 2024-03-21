package luna2d.templates;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import luna2d.engine.DayNightCycleTime;
import luna2d.engine.Game;
import luna2d.engine.Log;
import luna2d.engine.ResourceHandler;
import luna2d.engine.SaveLoadHandler;
import luna2d.engine.Scene;
import luna2d.engine.Utilites;
import luna2d.maths.Vector2;
import luna2d.templates.dataTypes.LoadDataType;
import luna2d.templates.dataTypes.ObjectTypes;
import luna2d.templates.worldMapData.MapStruct;
import luna2d.templates.worldMapData.WorldPosition;
import luna2d.templates.worldMapData.WorldStruct;

public abstract class SimpleWorldPlayer extends Scene
{
	public static final int WORLD_RENDER_DISTANCE = 1;
	public static final int WORLD_UPDATE_DISTANCE = 2;
	
	private boolean savingLock;
	
	public SimpleWorldPlayer(String name) 
	{
		super(name);
	}

	@Override
	public void start() 
	{
		Log.println("World Player started.");		
	}
	
	public void loadGame(String gameName)
	{
		this.savingLock = false;
		
		Log.println("Loading save data: " + gameName);
		this.worldData = new WorldStruct(gameName, this, true);
		
		// Load game file
		loadGameFile(gameName);
		
		WorldPosition pWP = this.worldData.getPlayerWorldPosition();
		
		String pathBase = Game.GAME_SAVE_DIR + "/" + gameName + "/";
		
		if(Utilites.directoryExists(pathBase))
		{
			MapStruct[][] worldMaps = new MapStruct[Game.ROWS][Game.COLUMNS];
			for (int r = 0; r < Game.ROWS; r++)
			{
				for (int c = 0; c < Game.COLUMNS; c++)
				{
					worldMaps[r][c] = this.loadMap(gameName, r, c);
					
					if (r == pWP.worldRow && c == pWP.worldColumn)
					{
						worldMaps[r][c].createPlayer(pWP);
					}
				}
			}
			
			this.worldData.injectData(worldMaps);
			
			configGame();
			
			// Below load backpack and player
			Player player = (Player)this.getPlayer();
			player.load(gameName);
			
			this.worldData.setActive(true);
			Log.println(gameName + " game loaded. Starting...");
		}		
	}
	
	private boolean loadGameFile(String gameName)
	{
		String path = Game.GAME_SAVE_DIR + "/" + gameName + "/" + gameName + ".thdat";
				
		BufferedReader reader;
		try 
		{
			reader = new BufferedReader(new FileReader(path));
			
			String line = "";
			int i = 0;
			while((line = reader.readLine()) != null)
			{
			   	if(i == 0)
			   	{
			   		String[] wps = line.split(",");
			   		int[] wpi = new int[4];
			   		for (int j = 0; j < 4; j++)
			   		{
			   			wpi[j] = Integer.parseInt(wps[j]);
			   		}
			   		
			   		WorldPosition wp = new WorldPosition(wpi[0], wpi[1], wpi[2], wpi[3]);
			   		this.worldData.setPlayerWorldPosition(wp);
			   	}
			   	
				i++;
			}
			
			reader.close();
			
			return true;
			
		} 
		catch (IOException | NumberFormatException e) 
		{
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	private MapStruct loadMap(String gameName, int worldRow, int worldColumn)
	{
		String mapName = gameName + "_" + worldRow + "-" +  worldColumn;
		
		String pathBase = Game.GAME_SAVE_DIR + "/" + gameName + "/";
		String mapPath = pathBase + "s_" + gameName + "_" + worldRow + "-" +  worldColumn + ".ths";
		String groundPath = pathBase + "s_" + gameName + "_" + worldRow + "-" +  worldColumn + ".thsg";

		int[][] mapData = SaveLoadHandler.loadCSVints(mapPath, LoadDataType.GAME_MAP);
		int[][] mapGrounds = SaveLoadHandler.loadCSVints(groundPath, LoadDataType.GAME_GROUNDS);
		
		for (int r = 0; r < Game.ROWS; r++)
		{
			for( int c = 0; c < Game.COLUMNS; c++)
			{
				if (mapData[r][c] == ObjectTypes.Player.intValue)
				{
					mapData[r][c] = ObjectTypes.Empty.intValue;
				}
			}
		}
		
		MapStruct mapStruct = new MapStruct(mapName, this, worldRow, worldColumn, true);
		mapStruct.injectData(mapName, mapData, mapGrounds);
		
		return mapStruct;
		
	}
	
	public void loadAndStart(String worldName)
	{
		this.savingLock = false;
		
		Log.println("Loading world...");
		this.worldData = new WorldStruct(worldName, this, false);
		
		configGame();
		
		Player p = (Player)this.getPlayer();
		
		if (p == null)
		{
			Log.println("Player is null...");
			Log.printStack();
			return;
		}
		
		Log.println(worldName + " finished. Starting...");
	}

	public void configGame()
	{
		// Zoom in
		this.getGame().updateScale(MapStruct.MAP_SCALE);
		
		// Startup day and night
		this.setDayNightCycle(new DayNightCycleEngine(80, 8, 20, Color.orange, Color.white, Color.orange, 
				new Color(0, 0, 0, 0.8f)), new DayNightCycleTime(8, 0, 0));
		
//		ResourceHandler.addRain("Rain", "RainComing", 800, 10, 50, 
//				4 * 60, 3 * 24 * 60, // 4 hrs - 3 days between rain
//				60, 2 * 24 * 60, // 1 hours - 2 days of rain
//				this.getDayNightEngine().getMilliSecondsOfInGameMinute() / 2
//				);
	}
	
	@Override
	public void end() 
	{
		if (this.getPlayer() != null) 
		{
			Player p = (Player)this.getPlayer();
			p.destroy();			
		}
		
		Game.getWeatherSystem().disableRain();
		
		Log.println("World Player ended.");	
	}
	
	private void updateWorld()
	{		
		Vector2 playerMap = this.worldData.getPlayerOnMapRC();
		
		if (playerMap == null)
		{
			return;
		}
		
		int playerRow = playerMap.y;
		int playerColumn = playerMap.x;
		
		for (int r = 0; r < Game.ROWS; r++)
		{
			for (int c = 0; c < Game.COLUMNS; c++)
			{
				if (playerColumn - WORLD_UPDATE_DISTANCE >= 0 && 
						playerColumn + WORLD_UPDATE_DISTANCE < Game.COLUMNS &&
						playerRow - WORLD_UPDATE_DISTANCE >= 0 &&
						playerRow + WORLD_UPDATE_DISTANCE < Game.ROWS)
					{
						this.worldData.updateMap(r, c);
					}
			}
		}
	}

	@Override
	public void update() 
	{
		this.updateWorld();
		this.checkKeys();
	}

	protected abstract void checkKeys();
	
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