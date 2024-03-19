package luna2d.timers;

import java.util.TimerTask;

import luna2d.Scene;

public class SceneTask extends TimerTask
{
	protected Scene scene;
	
	public SceneTask(Scene scene)
	{
		this.scene = scene;
	}

	@Override
	public void run() 
	{ 
		// Override this	
	}
}