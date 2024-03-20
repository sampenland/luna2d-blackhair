package luna2d.timers;

import java.util.TimerTask;

import luna2d.engine.WeatherSystem;

public class WeatherSystemTask extends TimerTask
{
	protected WeatherSystem weatherSystem;
	
	public WeatherSystemTask(WeatherSystem weatherSystem)
	{
		this.weatherSystem = weatherSystem;
	}

	@Override
	public void run() 
	{ 
		// Override this	
	}
}
