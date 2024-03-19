package luna2d.physics;

import java.awt.Rectangle;

import luna2d.Maths;

public class BoxCollisionShape extends CollisionShape
{
	
	public Rectangle rect;
	
	public BoxCollisionShape(int x, int y, int w, int h)
	{
		this.rect.x = x;
		this.rect.y = y;
		this.rect.width = w;
		this.rect.height = h;
		
		this.shape = TYPES.Box;
	}
	
	@Override
	protected void update(int x, int y)
	{
		this.rect.x = x;
		this.rect.y = y;
	}
	
	protected void resize(int w, int h)
	{
		this.rect.width = w;
		this.rect.height = h;
	}

	@Override
	protected boolean collidingWith(CollisionShape other) 
	{		
		switch(other.shape)
		{
		
			// Collision between this box and another box
			case Box:				
				
				BoxCollisionShape oBox = (BoxCollisionShape)other;
				return this.rect.intersects(oBox.rect.getBounds());
				
			// Collision between this box and a circle
			case Circle:
				
				CircleCollisionShape oCircle = (CircleCollisionShape)other;
				
				float closestX = Maths.clamp(oCircle.x, this.rect.x, this.rect.x + this.rect.width);
			    float closestY = Maths.clamp(oCircle.y, this.rect.y - this.rect.height, this.rect.y);
			 
			    float distanceX = oCircle.x - closestX;
			    float distanceY = oCircle.y - closestY;
			 
			    return Math.pow(distanceX, 2) + Math.pow(distanceY, 2) < Math.pow(oCircle.r, 2);
		}
		
		return false;
		
	}
	
}
