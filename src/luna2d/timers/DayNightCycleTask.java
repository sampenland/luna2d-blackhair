package luna2d.timers;

import java.util.TimerTask;

import luna2d.engine.DayNightCycle;

public class DayNightCycleTask extends TimerTask
{
	protected DayNightCycle cycle;
	
	public DayNightCycleTask(DayNightCycle dayNightCycle)
	{
		this.cycle = dayNightCycle;
	}

	@Override
	public void run() 
	{ 
		// Override this	
	}
}
