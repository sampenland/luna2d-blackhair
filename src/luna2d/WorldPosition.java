package luna2d;

public class WorldPosition 
{
	public int worldRow, worldColumn, mapRow, mapColumn;
	
	public static WorldPosition zero()
	{
		return new WorldPosition(0, 0, 0, 0);
	}
	
	public WorldPosition(int worldRow, int worldCol, int mapRow, int mapCol)
	{
		this.worldRow = worldRow;
		this.worldColumn = worldCol;
		this.mapRow = mapRow;
		this.mapColumn = mapCol;
	}
	
	public WorldPosition(Vector2 worldPos, Vector2 mapPos)
	{
		this.worldRow = worldPos.y;
		this.worldColumn = worldPos.x;
		this.mapRow = mapPos.y;
		this.mapColumn = mapPos.x;
	}
	
	public static Vector2 distanceFromWPs(WorldPosition p1, WorldPosition p2)
	{
		int oRow = p1.getWorldPos().y;
		int oCol = p1.getWorldPos().x;
		
		int pRow = p2.getWorldPos().y;
		int pCol = p2.getWorldPos().x;
		
		int rDist = Math.abs(pRow - oRow);
		int cDist = Math.abs(pCol - oCol);
		
		return new Vector2(cDist, rDist);
	}
	
	public Vector2 getMapPos()
	{
		return new Vector2(this.mapColumn, this.mapRow);
	}
	
	public Vector2 getWorldPos()
	{
		return new Vector2(this.worldColumn, this.worldRow);
	}
}