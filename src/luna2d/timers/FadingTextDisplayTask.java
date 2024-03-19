package luna2d.timers;

import java.util.TimerTask;
import luna2d.renderables.FadingTextDisplay;

public class FadingTextDisplayTask extends TimerTask
{
	protected FadingTextDisplay fadingTextDisplay;
	
	public FadingTextDisplayTask(FadingTextDisplay fadingTextDisplay)
	{
		this.fadingTextDisplay = fadingTextDisplay;
	}

	@Override
	public void run() 
	{ 
		// Override this	
	}
}
