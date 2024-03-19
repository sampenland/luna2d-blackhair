package luna2d.lights;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import luna2d.Scene;
import luna2d.WorldPosition;

public class GlowLight extends Light
{	
	private final Color color[] = new Color[5];
	private final float fraction[] = new float[5];
		
	public GlowLight(Scene inScene, int worldX, int worldY, int radius, WorldPosition wp) 
	{
		super(inScene, worldX, worldY, wp);
		this.radius = radius;
		
		color[4] = new Color(0, 0, 0, 0.f);
		color[3] = new Color(0, 0, 0, 0.f);
		color[2] = new Color(0, 0, 0, 0.1f);
		color[1] = new Color(0, 0, 0, 0.2f);
		color[0] = new Color(0, 0, 0, 0.75f);
		
		fraction[0] = 0.0f;
		fraction[1] = 0.2f;
		fraction[2] = 0.23f;
		fraction[3] = 0.5f;
		fraction[4] = 1f;
		
		this.buildLightImage();
		
	}	
	
	@Override
	public void buildLightImage()
	{
		this.img = new BufferedImage(radius, radius, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D)this.img.getGraphics();
		
		Shape circleShape = new Ellipse2D.Double(0, 0, radius, radius);					
		RadialGradientPaint rad = new RadialGradientPaint(radius/2, radius/2, radius, fraction, color); 
		g2d.setPaint(rad);
		g2d.fill(circleShape);
		g2d.dispose();
	}
	
	public int getRadius()
	{
		return this.radius;
	}
	
	public void setRadius(int r)
	{
		this.radius = r;
	}

	@Override
	public void update() 
	{
	}

	@Override
	public void render(Graphics g) 
	{	
	}
	
}
