package luna2d.templates;

import java.awt.Color;

import luna2d.DayNightCycle;

public class DayNightCycleEngine extends DayNightCycle
{

	public DayNightCycleEngine(int msOfInGameMinute, int startOfDayHour, int startOfNightHour, Color dawnColor,
			Color dayColor, Color dustColor, Color nightColor) 
	{
		super(msOfInGameMinute, startOfDayHour, startOfNightHour, dawnColor, dayColor, dustColor, nightColor);
	}

	@Override
	public void update() 
	{
	}

}