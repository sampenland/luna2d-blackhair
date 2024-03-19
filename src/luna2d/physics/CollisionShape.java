package luna2d.physics;

public abstract class CollisionShape 
{
	protected enum TYPES 
	{
		Box,
		Circle
	}
	
	protected TYPES shape;
	
	protected abstract boolean collidingWith(CollisionShape other);
	protected abstract void update (int x, int y);
	
}
