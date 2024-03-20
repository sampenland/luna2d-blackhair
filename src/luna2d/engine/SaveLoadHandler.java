package luna2d.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import luna2d.templates.dataTypes.LoadDataType;

public class SaveLoadHandler 
{
	
	public static int[][] loadCSVints(String name, LoadDataType dataType)
	{
		String path = Game.DATA_DIR + "/" + name;
		
		switch(dataType)
		{
		case GROUNDS:
			path += ".thmg";
			break;
		case MAP:
			path += ".thm";
			break;
		case WORLD:
			path += ".thw";
			break;
		case GAME_MAP:
			path = name;
			break;
		case GAME_GROUNDS:
			path = name;
			break;
		case BACKPACK:
			path = name;
			break;
		default:
			Log.println(name + " not a game save, world, map, or grounds name.");
			return null;	
		}
		
		int[][] data = new int[Game.ROWS][Game.COLUMNS];
		
		BufferedReader reader;
		try 
		{
			reader = new BufferedReader(new FileReader(path));
			
			String line = "";
			int row = 0;
			while((line = reader.readLine()) != null)
			{
			   String[] cols = line.split(","); 
			   int col = 0;
			   for(String  c : cols)
			   {
				   int t = Integer.parseInt(c);				   
				   data[row][col] = t;
				   col++;
			   }
			   
			   row++;
			   
			}
			
			reader.close();
			
			return data;
			
		} 
		catch (IOException | NumberFormatException e) 
		{
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static String[][] loadCSVstrings(String name, LoadDataType dataType)
	{
		String path = Game.DATA_DIR + "/" + name;
		
		switch(dataType)
		{
		case WORLD_NAMES:
			path += ".thwn";
			break;
		default:
			Log.println(name + " not a world names file.");
			return null;
		}
		
		String[][] data = new String[Game.ROWS][Game.COLUMNS];
		
		BufferedReader reader;
		try 
		{
			reader = new BufferedReader(new FileReader(path));
			
			String line = "";
			int row = 0;
			while((line = reader.readLine()) != null)
			{
			   String[] cols = line.split(","); 
			   int col = 0;
			   for(String  c : cols)
			   {
				   data[row][col] = c;
				   col++;
			   }
			   
			   row++;
			   
			}
			
			reader.close();
			
			return data;
			
		} 
		catch (IOException | NumberFormatException e) 
		{
			((Throwable) e).printStackTrace();
		}
		
		return data;
	}

}
