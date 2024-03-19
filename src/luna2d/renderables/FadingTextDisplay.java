package luna2d.renderables;

import java.awt.Color;
import java.util.Timer;

import luna2d.Scene;
import luna2d.timers.FadingTextDisplayTask;

public class FadingTextDisplay extends TextDisplay 
{

	FadingTextDisplayTask fadeTask;
	Timer fadeTimer;
	
	public FadingTextDisplay(Scene inScene, String text, int x, int y, Color c, int msDisplayTime, int depth) 
	{
		super(inScene, text, x, y, c, depth);
		
		fadeTask = new FadingTextDisplayTask(this)
		{
			@Override
			public void run()
			{
				this.fadingTextDisplay.destroy();
			}
		};
		
		fadeTimer = new Timer();
		fadeTimer.schedule(fadeTask, msDisplayTime);
		
	}	
}
