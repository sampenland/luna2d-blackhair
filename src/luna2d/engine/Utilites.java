package luna2d.engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utilites
{

	public static boolean createDirectory(String fullpath)
	{
		try 
		{
		
			Path path = Paths.get(fullpath);
			
			//java.nio.file.Files;
			Files.createDirectories(path);
			
			return true;
		
		} 
		catch (IOException e) 
		{
		
		    System.err.println("Failed to create directory: " + e.getMessage());
		    return false;		
		}
	}
	
	public static boolean saveStringToFile(String data, String filepath)
	{
		BufferedWriter writer;
		try
		{
			writer = new BufferedWriter(new FileWriter(filepath));
			writer.write(data);
			writer.close();
			
			return true;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean fileExists(String fullPath)
	{
		File f = new File(fullPath);
		return (f.exists() && !f.isDirectory());
	}
	
	public static boolean directoryExists(String fullPath)
	{
		File f = new File(fullPath);
		return (f.exists() && f.isDirectory());
	}
	
}
