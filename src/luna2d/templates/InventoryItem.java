package luna2d.templates;

import luna2d.ObjectTypes;
import luna2d.Scene;

public abstract class InventoryItem 
{
	public static int PICKUP_MULTIPLIER = 1;
	public ObjectTypes TYPE;
	public int AMOUNT;
	private Scene inScene;
	
	public InventoryItem(Scene inScene, ObjectTypes type, int amount)
	{
		this.inScene = inScene;
		this.TYPE = type;
		this.AMOUNT = amount;
	}
	
	public Scene getScene()
	{
		return this.inScene;
	}
	
	public abstract void use(int flag);
	public abstract void repair();
	public abstract void craft();	
	
}