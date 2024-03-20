package sandbox.scenes;

import java.awt.event.KeyEvent;

import luna2d.templates.WorldEditor;

public class Editor extends WorldEditor
{

	public Editor(String name) 
	{
		super(name);
	}
	
	@Override protected void checkKeys()
	{
		if (this.detailedMenu != null && this.detailedMenu.visible)
		{
			return;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_ESCAPE))
		{
			this.openScene("Sandbox");
			return;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_M)) 
		{			
			this.detailedMenu.toggleVisible();
		}
	}

}
