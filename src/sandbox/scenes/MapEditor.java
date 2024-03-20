package sandbox.scenes;

import java.awt.event.KeyEvent;

import luna2d.templates.SimpleMapEditor;

public class MapEditor extends SimpleMapEditor
{

	public MapEditor(String name) 
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
		
		if (this.isKeyPressed(KeyEvent.VK_Q))
		{
			this.userSwitching();
		}
		
		if (this.isKeyPressed(KeyEvent.VK_M)) 
		{			
			if (this.mouseStatus != MOUSE_STATUS.IDLE)
			{
				this.mouseStatus = MOUSE_STATUS.PLACING;
				userSwitching();
				this.displaySelection(false);
			}
			
			this.detailedMenu.toggleVisible();
		}
		
	}

}
