package luna2d.engine;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import luna2d.lights.GlowLight;
import luna2d.lights.Light;
import luna2d.maths.Maths;
import luna2d.templates.Player;
import luna2d.templates.worldMapData.WorldPosition;

public class LightRenderer extends Thread
{
	private boolean running;
	private Scene inScene;
	
	public LightRenderer(Scene scene)
	{
		this.inScene = scene;
		this.running = false;
	}
	
	private Scene getScene()
	{
		return this.inScene;
	}
	
	public void setRunning(boolean val)
	{
		this.running = val;
	}
	
	@Override 
	public void run()
	{
		this.running = true;
		while (running)
		{			
			if (this.getScene() == null || this.getScene().getDayNightCycle() == null) 
			{
				try 
				{
					Thread.sleep(10);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				continue;
			}
			
			// Render lights with over-top Day/Night cycle background
			LinkedList<Light> lights = this.inScene.objHandler.getLights();
			
			BufferedImage img = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_ARGB);
			Graphics graphics = img.getGraphics();
			
			graphics.setColor(this.inScene.getDayNightCycle().getCurrentColor());
			graphics.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			
			if (WeatherSystem.isRaining)
			{
				graphics.setColor(WeatherSystem.cloudColor);
				graphics.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			}
			
			graphics.dispose();
			
			Graphics2D g2d = img.createGraphics();
			g2d.setComposite(AlphaComposite.DstOut);
			
			try
			{
				WorldPosition playerWP = ((Player)this.getScene().getPlayer()).getWorldPosition();
				
				for (Light light : lights)
				{
					if (!light.visible) continue;
					
					// CULL world maps
					WorldPosition lightWP = light.getWorldPosition();
					if (playerWP.worldColumn == lightWP.worldColumn && playerWP.worldRow == lightWP.worldRow)
					{
						int x = light.getWorldX();
						int y = light.getWorldY();
						
						if (x < -Game.CAMERA_X - Light.CullDistance || x > -Game.CAMERA_X + Game.WIDTH + Light.CullDistance ||
							y < -Game.CAMERA_Y - Light.CullDistance || y > -Game.CAMERA_Y + Game.HEIGHT + Light.CullDistance)
						{
							continue;
						}
						
						Point p = Maths.convertWorldToScreen(x, y, 16);
						
						if (light instanceof GlowLight)
						{
							GlowLight gl = (GlowLight)light;
							g2d.drawImage(gl.getImage(), p.x - gl.getRadius() / 2, 
									p.y - gl.getRadius() / 2, 
									gl.getRadius(), 
									gl.getRadius(), null);					
						}
					}
				}
			}
			catch (Exception e)
			{
				g2d.dispose();
				continue;
			}			
			
			g2d.dispose();
			
			this.inScene.updateLightImage(img);	
		}
	}
}
