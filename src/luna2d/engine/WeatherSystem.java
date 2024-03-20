package luna2d.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Timer;

import luna2d.maths.Maths;
import luna2d.renderables.Sprite;
import luna2d.timers.WeatherSystemTask;

public class WeatherSystem 
{	
	private Sprite rainSprite, comingRainSprite;
	private int minMinutesBetweenRain, maxMinutesBetweenRain, minMinutesOfRain, maxMinutesOfRain, msOfInGameMinute;
	public static Color cloudColor;
	private boolean canRain;
	public static boolean isRaining = false;
	public static boolean rainComing = false;
	
	private boolean waitingForUnPause = false;
	
	private WeatherSystemTask weatherSystemTask, rainComingTask;
	private Timer weatherSystemTimer, rainComingTimer;
	
	public WeatherSystem()
	{
		this.canRain = false;
		cloudColor = new Color(0, 0, 0, 0.75f);;
	}
	
	public void disableRain()
	{
		this.stopRaining();
		this.canRain = false;
	}
	
	public void setupRain(String rainImg, String comingRainImgName, int frameWidth, int frames, int speedOfRainMS, int minMinutesBetweenRain, int maxMinutesBetweenRain, int minMinutesOfRain,
			int maxMinutesOfRain, int msOfInGameMinute)
	{
		this.canRain = true;
		
		this.minMinutesBetweenRain = minMinutesBetweenRain;
		this.maxMinutesBetweenRain = maxMinutesBetweenRain;
		this.minMinutesOfRain = minMinutesOfRain;
		this.maxMinutesOfRain = maxMinutesOfRain;
		this.msOfInGameMinute = msOfInGameMinute;
		
		this.rainSprite = new Sprite(null, rainImg, Game.WIDTH / 2, Game.HEIGHT / 2, 1, Game.TOP_DRAW_LAYER, frameWidth, frames, speedOfRainMS);
		this.rainSprite.setFixedScreenPosition(true);
		this.rainSprite.disableScaling();
		this.rainSprite.setCustomRender(true);
		this.rainSprite.stopAnimation();
		this.rainSprite.visible = false;
		
		this.comingRainSprite = new Sprite(null, comingRainImgName, Game.WIDTH - 64, 20, 2, Game.TOP_DRAW_LAYER);
		this.comingRainSprite.setFixedScreenPosition(true);
		this.comingRainSprite.disableScaling();
		this.comingRainSprite.setCustomRender(true);
		this.comingRainSprite.visible = false;
		
		this.resetRainTimers();
		
	}
	
	public void resetRainTimers()
	{
		if (!this.canRain) return;
		
		this.weatherSystemTask = new WeatherSystemTask(this)
		{
			@Override
			public void run()
			{
				if (Game.paused)
				{
					this.weatherSystem.waitingForUnPause = true;
					return;
				}
				
				if(WeatherSystem.isRaining)
				{
					this.weatherSystem.stopRaining();
				}
				else
				{
					this.weatherSystem.startRaining();
				}
				
				this.weatherSystem.resetRainTimers();
			}
		};
		
		this.rainComingTask = new WeatherSystemTask(this)
		{
			@Override
			public void run()
			{
				if (Game.paused)
				{
					this.weatherSystem.waitingForUnPause = true;
					return;
				}
				
				this.weatherSystem.showRainComing();
			}
		};
		
		this.weatherSystemTimer = new Timer();
		this.rainComingTimer = new Timer();
		
		int mins;
		if (isRaining)
		{
			mins = Maths.random(this.minMinutesOfRain, this.maxMinutesOfRain);
		}
		else
		{
			mins = Maths.random(this.minMinutesBetweenRain, this.maxMinutesBetweenRain);
		}
		
		mins *= this.msOfInGameMinute;
		
		this.weatherSystemTimer.schedule(weatherSystemTask, mins);
		
		int rainComingMins = mins / 2;
		if (rainComingMins <= 0) rainComingMins = 1;
		this.rainComingTimer.schedule(rainComingTask, rainComingMins);
	}
	
	public void showRainComing()
	{
		this.comingRainSprite.visible = true;
		rainComing = true;
	}
	
	public void startRaining()
	{
		if (this.canRain)
		{
			rainComing = false;
			isRaining = true;
			this.rainSprite.visible = true;
			this.rainSprite.playAnimation(true);			
		}
	}
	
	public void stopRaining()
	{
		if (this.canRain)
		{			
			isRaining = false;
			this.rainSprite.visible = false;
			this.comingRainSprite.visible = false;
			this.rainSprite.stopAnimation();
		}
	}
	
	public void update()
	{
		if (this.waitingForUnPause && !Game.paused)
		{
			this.resetRainTimers();
			this.waitingForUnPause = false;
		}
	}
	
	public void renderTopLayer(Graphics g)
	{
		if (this.canRain)
		{		
			this.comingRainSprite.customRender(g);
		}
	}
	
	public void render(Graphics g)
	{
		if (this.canRain)
		{			
			this.rainSprite.customRender(g);
		}
	}
	
}
