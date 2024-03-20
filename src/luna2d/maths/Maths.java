package luna2d.maths;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.concurrent.ThreadLocalRandom;

import luna2d.engine.Game;
import luna2d.playerControllers.SimplePlayer;
import luna2d.templates.worldMapData.WorldPosition;

public class Maths 
{

	public static int clamp(int val, int max, int min)
	{
		if (val >= max) return val = max;
		if (val <= min) return val = min;
		return val;
	}
	
	public static float clamp(float val, float max, float min)
	{
		if (val >= max) return val = max;
		if (val <= min) return val = min;
		return val;
	}
	
	public static Vector2f directionBetweenTwoPoints(Point start, Point end, boolean normalized)
	{
		if(normalized)
		{
			Point p = new Point((end.x - start.x), (end.y - start.y));
			double mag = Math.sqrt(p.x * p.x + p.y * p.y);
			return new Vector2f((float)(p.x / mag), (float)(p.y / mag));
			
		}
		return new Vector2f((end.x - start.x), (end.y - start.y));
	}
	
	public static int random(int min, int max)
	{
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	public static int distanceBetweenTwoPoints(Point p1, Point p2)
	{
		return (int) Point.distance(p1.x, p1.y, p2.x, p2.y);
	}
	
	public static int distanceBetweenPlayerAndPoint(Object playerObj, Point p)
	{
		if (playerObj instanceof SimplePlayer)
		{
			SimplePlayer player = (SimplePlayer)playerObj;
			int d = (int)Point.distance(player.getWorldX(), player.getWorldY(), p.x, p.y);
			return d;
		}
		
		return -1;
	}
	
	public static Point convertWorldToScreen(int x, int y, int cellSize)
	{
		return new Point (Game.CAMERA_X + x, Game.CAMERA_Y + y);
	}
	
	public static Vector2 convertToGrid(int x, int y, int cellSize)
	{
		return convertToGrid(x, y, cellSize, 0, 0);
	}
	
	public static WorldPosition convertToWorldPosition(Vector2 worldGridPosition, int scale, int worldRows, int worldColumns)
	{
		int c = worldGridPosition.x / scale;
		int r = worldGridPosition.y / scale;
		
		int wrdCol = c / worldColumns;
		int wrdRow = r / worldRows;
		
		int mapCol = c % worldColumns;
		int mapRow = r % worldRows;
	
		return new WorldPosition(wrdRow, wrdCol, mapRow, mapCol);
	}
	
	public static Vector2 convertWorldPositionToXY(WorldPosition wp, int cellSize, int rows, int cols, int mapScale)
	{
		int worldX = (wp.worldColumn * cols * cellSize * mapScale);
		int worldY = (wp.worldRow * rows * cellSize * mapScale); 
		
		int mapX = (wp.mapColumn * Game.CELL_SIZE * mapScale);
		int mapY = (wp.mapRow * Game.CELL_SIZE) * mapScale;
		
		int x = worldX + mapX;
		int y = worldY + mapY;
		
		return new Vector2(x, y);
	}
	
	public static Vector2 convertToGrid(int x, int y, int cellSize, int startX, int startY)
	{
		Vector2 p = new Vector2(-1, -1);
		
		if (startX < 0) startX = 0;
		if (startY < 0) startY = 0;
		
		p.x = Math.round((x - startX) / cellSize) * Game.CAMERA_SCALE;
		p.y = Math.round((y - startY) / cellSize) * Game.CAMERA_SCALE;
		
		return p;
	}
	
	public static boolean characterIsAlphaNumeric(int character, boolean allowBackspace)
	{
		if (allowBackspace && character == KeyEvent.VK_BACK_SPACE) return true;
		return ((character >= (int)'0' & character <= (int)'9') || (character >= (int)'a' && character <= (int)'z') || (character >= (int)'A' && character <= (int)'Z'));
	}
}
