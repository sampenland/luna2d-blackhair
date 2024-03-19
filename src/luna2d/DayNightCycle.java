package luna2d;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Timer;

import luna2d.timers.DayNightCycleTask;

public abstract class DayNightCycle 
{
	private int inGameMinutes, inGameHours;
	private long inGameDays;
	
	private DayNightCycleTask cycleTask;
	private Timer cycleTimer;
	
	private int startOfDayHour, startOfNightHour;
	private Color dawnColor, dayColor, dustColor, nightColor, currentColor;
	
	private int nightHours;
	private float alpha;
	private int msOfInGameMinute;
	
	public DayNightCycle(int msOfInGameMinute, int startOfDayHour, int startOfNightHour, Color dawnColor, Color dayColor, Color dustColor, Color nightColor)
	{
		if (this.startOfNightHour - this.startOfDayHour >= 24) 
		{
			Log.println("Too many hours in cycle");
			return; 
		}
		
		this.msOfInGameMinute = msOfInGameMinute;
		this.nightHours = this.startOfNightHour - this.startOfDayHour;
		
		this.startOfDayHour = startOfDayHour;
		this.startOfNightHour = startOfNightHour;
		
		this.dawnColor = dawnColor;
		this.dayColor = dayColor;
		this.dustColor = dustColor;
		this.nightColor = nightColor;
		
		this.currentColor = dayColor;
		
		this.cycleTask = new DayNightCycleTask(this)
		{
			@Override
			public void run()
			{
				this.cycle.minuteTick();
			}
		};
		
		this.cycleTimer = new Timer("DayNightTimer");
		this.cycleTimer.scheduleAtFixedRate(cycleTask, msOfInGameMinute, msOfInGameMinute);
	}
	
	public int getMilliSecondsOfInGameMinute()
	{
		return this.msOfInGameMinute;
	}
	
	public long getTimeInMinutes()
	{
		return (this.inGameHours * 60) + (this.inGameDays * 60 * 24) + this.inGameMinutes;
	}
	
	public long getTimeSinceMinute(long markerMinute)
	{
		return getTimeInMinutes() - markerMinute;
	}
	
	public boolean isDayTime()
	{
		return this.inGameHours >= this.startOfDayHour && this.inGameHours < this.startOfNightHour;
	}
	
	public Color getCurrentColor()
	{
		return new Color(this.currentColor.getRed() / 255,
				this.currentColor.getGreen() / 255, this.currentColor.getBlue() / 255, 
				this.alpha);
	}
	
	public void setTime(int hours, int minutes, long days)
	{
		this.inGameHours = hours;
		this.inGameMinutes = minutes;
		this.inGameDays = days;
	}
	
	public void setTime(DayNightCycleTime time)
	{
		this.inGameHours = time.hours;
		this.inGameMinutes = time.minutes;
		this.inGameDays = time.days;
	}
	
	private void minuteTick()
	{
		this.inGameMinutes++;
		
		if(this.inGameMinutes > 60)
		{
			this.inGameMinutes = 0;
			this.inGameHours++;
			
			if (this.inGameHours > 24)
			{
				this.inGameHours = 0;
				this.inGameDays++;
			}
		}
		
		this.gameUpdate();
		this.update();
	}
	
	public String getTime()
	{
		String mins = this.inGameMinutes + "";
		if (mins.length() == 1) mins = "0" + mins;
		return this.inGameHours + ":" + mins;
	}
	
	public String getDaysAndTime()
	{
		String mins = this.inGameMinutes + "";
		if (mins.length() == 1) mins = "0" + mins;
		return "Day " + this.inGameDays + "    Time: " + this.inGameHours + ":" + mins;
	}
	
	public int getMinutes()
	{
		return this.inGameMinutes;
	}
	
	public int getHours()
	{
		return this.inGameHours;
	}
	
	public long getDays()
	{
		return this.inGameDays;
	}
	
	public float getAlpha()
	{
		return this.alpha;
	}
	
	public void render(Graphics g)
	{
	}
	
	public void gameUpdate()
	{
		this.alpha = 0.2f;
		
		// DAWN
		if (this.inGameHours >= this.startOfDayHour - 1 && this.inGameHours < this.startOfDayHour)
		{
			this.currentColor = this.dawnColor;
		}
		// DAY
		else if (this.inGameHours >= this.startOfDayHour && this.inGameHours < this.startOfNightHour - 1)
		{
			this.currentColor = this.dayColor;
			this.alpha = 0.1f;
		}
		else if (this.inGameHours >= this.startOfNightHour - 1 && this.inGameHours < this.startOfNightHour)
		{
			this.currentColor = this.dustColor;
		}
		else
		{
			this.currentColor = this.nightColor;
			this.alpha = (float)this.inGameHours / this.nightHours;
			if (this.alpha > 0.8) this.alpha = 0.8f;
			if (this.inGameHours == 0) this.alpha = 0.8f;
		}
	}
	
	public abstract void update();
}
