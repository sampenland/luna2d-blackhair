package sandbox.scenes;

import java.awt.event.KeyEvent;

import luna2d.templates.SimpleWorldEditor;

public class WorldEditor extends SimpleWorldEditor
{

	public WorldEditor(String name) 
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
