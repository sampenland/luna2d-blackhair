package luna2d.physics;

import luna2d.Maths;

public class CircleCollisionShape extends CollisionShape
{

	int x, y, r;
	
	public CircleCollisionShape(int x, int y, int r)
	{
		this.x = x;
		this.y = y;
		this.r = r;
		
		this.shape = TYPES.Circle;
	}
	
	@Override
	protected void update(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	protected void resize(int r)
	{
		this.r = r;
	}

	@Override
	protected boolean collidingWith(CollisionShape other) 
	{		
		switch(other.shape)
		{
		
			// Collision between this circle and another circle
			case Circle:
				
				CircleCollisionShape oCircle = (CircleCollisionShape)other;
				return Math.pow(this.x - oCircle.x, 2) + Math.pow(this.y - oCircle.y, 2) <= Math.pow(this.r + oCircle.r, 2);
	
			// Collision between this circle and a box
			case Box:
				
				BoxCollisionShape oBox = (BoxCollisionShape)other;
				
				float closestX = Maths.clamp(this.x, oBox.rect.x, oBox.rect.x + oBox.rect.width);
			    float closestY = Maths.clamp(this.y, oBox.rect.y - oBox.rect.height, oBox.rect.y);
			 
			    float distanceX = this.x - closestX;
			    float distanceY = this.y - closestY;
			 
			    return Math.pow(distanceX, 2) + Math.pow(distanceY, 2) < Math.pow(this.r, 2);
		}
		
		return false;
		
	}
	
}
