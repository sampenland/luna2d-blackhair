package luna2d.templates.dataTypes;

public enum ObjectTypes 
{	
	Empty(0, "NONE", false),
	GndGrass(1, "", false),
	GndDirt(2, "", false),
	GndRock(3, "", false),
	GndWater(4, "", false),
	Player(5, "Player", true),
	Tree(6, "Tree", true),
	Bush(7, "Bush", true),
	Water(8, "Water", true),
	Rock(9, "Rock", true);
	
	public final int intValue;
	public final String imgName;
	public final boolean showInMapEditor;
	private ObjectTypes(int v, String imgName, boolean showInMapEditor)
	{
		this.intValue = v;
		this.imgName = imgName;
		this.showInMapEditor = showInMapEditor;
	}
}