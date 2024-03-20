package luna2d.engine;

import java.awt.Color;
import java.util.HashMap;

public class ColorHandler 
{
	private static HashMap<String, Color> colors = new HashMap<String, Color>();
	
	public static void addColor(String name, Color c, float alpha)
	{
		c = new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)(alpha * 255));
		colors.put(name, c);
	}
	
	public static void addColor(String name, Color c, int alpha)
	{
		Color nc = new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
		colors.put(name, nc);
	}
	
	public static void addColor(String name, Color c)
	{
		colors.put(name, c);
	}
	
	public static Color getColor(String name)
	{
		return colors.get(name);
	}
	
}
