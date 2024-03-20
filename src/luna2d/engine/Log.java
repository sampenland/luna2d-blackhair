package luna2d.engine;

import java.awt.Point;

import luna2d.maths.Vector2;
import luna2d.templates.worldMapData.WorldPosition;

public class Log 
{

	public static void print(String text)
	{
		System.out.print(text);
	}
	
	public static void println(WorldPosition pos)
	{
		Log.println("World Position: Wrd[" + pos.getWorldPos().x +
				", " + pos.getWorldPos().y + "]  Map[" + pos.getMapPos().x + ", " +
				pos.getMapPos().y + "]");
	}
	
	public static void println(String text, WorldPosition wp)
	{
		System.out.print(text + "  ");
		Log.println(wp);
	}
	
	public static void println(String text)
	{
		System.out.println(text);
	}
	
	public static void println(Scene scene)
	{
		System.out.println(scene.name);
	}
	
	public static void println(Vector2 text)
	{
		System.out.println(text.x + ", " + text.y);
	}
	
	public static void println(Point text)
	{
		System.out.println(text.x + ", " + text.y);
	}
	
	public static void println(boolean text)
	{
		System.out.println(text);
	}
	
	public static void println(float text)
	{
		System.out.println(text);
	}
	
	public static void println(boolean text, boolean text2)
	{
		System.out.println(text + " " + text2);
	}
	
	public static void println(float text, float text2)
	{
		System.out.println(text + " " + text2);
	}
	
	public static void println(boolean text, boolean text2, boolean text3)
	{
		System.out.println(text + " " + text2 + " " + text3);
	}
	
	
	public static void println(boolean text, boolean text2, boolean text3, boolean text4)
	{
		System.out.println(text + " " + text2 + " " + text3 + " " + text4);
	}
	
	public static void println(String text, String text2)
	{
		System.out.println(text + " " + text2);
	}
	
	public static void println(String text,  String text2, String text3)
	{
		System.out.println(text + " " + text2 + " " + text3);
	}
	
	public static void print(int text)
	{
		System.out.print(text);
	}
	
	public static void println(int text)
	{
		System.out.println(text);
	}
	
	public static void println(int text, int text2)
	{
		System.out.println(text + " " + text2);
	}
	
	public static void println(int text,  int text2, int text3)
	{
		System.out.println(text + " " + text2 + " " + text3);
	}
	
	public static void println(int text,  int text2, int text3, int text4)
	{
		System.out.println(text + " " + text2 + " " + text3 + " " + text4);
	}
	
}
