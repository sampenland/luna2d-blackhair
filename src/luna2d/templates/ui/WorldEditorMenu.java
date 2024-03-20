package luna2d.templates.ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Timer;

import luna2d.engine.Game;
import luna2d.engine.SaveLoadHandler;
import luna2d.engine.Scene;
import luna2d.renderables.FadingTextDisplay;
import luna2d.renderables.TextDisplay;
import luna2d.timers.SceneTask;
import luna2d.ui.UIButton;
import luna2d.ui.UIMenu;
import luna2d.ui.UITextInput;
import luna2d.templates.dataTypes.LoadDataType;
import luna2d.templates.WorldEditor;

public class WorldEditorMenu extends UIMenu
{

	private boolean saving;
	private boolean loading;
	
	private UITextInput worldNameInput, worldNameLoadInput;
	private UIButton saveButton, loadButton, closeButton;
	
	public WorldEditorMenu(Scene inScene, int x, int y, int width, int height, Color bkgColor, int scale) 
	{
		super(inScene, x, y, width, height, bkgColor, scale);
		
		this.saving = false;
		this.loading = false;
		
		this.visible = false;
		
		TextDisplay title = new TextDisplay(inScene, "World Editor Menu", x + 10, y + 20, Color.black, Game.TOP_DRAW_LAYER);
		title.setInMenu(this);
		this.addTextDisplay(title);	
		
		TextDisplay lbWorldName = new TextDisplay(inScene, "Save World: ", x + 20, y + 40, Color.black, Game.TOP_DRAW_LAYER);
		lbWorldName.setInMenu(this);
		this.addTextDisplay(lbWorldName);	
		
		TextDisplay lbWorldNameLoad = new TextDisplay(inScene, "Load World: ", x + 20, y + 65, Color.black, Game.TOP_DRAW_LAYER);
		lbWorldNameLoad.setInMenu(this);
		this.addTextDisplay(lbWorldNameLoad);	
	}

	@Override
	public void launch() 
	{
		worldNameInput = new UITextInput(inScene, "WORLD00", this.screenX + 100, this.screenY + 25, 100, 20, 8);
		this.focusedTextInput = worldNameInput;
		
		saveButton = new UIButton(inScene, "Save World", this.screenX + 210, this.screenY + 25, 65, 20);
		
		// ----
		
		worldNameLoadInput = new UITextInput(inScene, "WORLD00", this.screenX + 100, this.screenY + 50, 100, 20, 8);
		this.focusedTextInput = worldNameLoadInput;
		
		loadButton = new UIButton(inScene, "Load World", this.screenX + 210, this.screenY + 50, 65, 20);
		closeButton = new UIButton(inScene, "Close", this.screenX + 240, this.screenY + this.getHeight() - 30, 45, 20);
		
		this.show();
	}
	
	@Override
	public void show()
	{
		super.show();
		
		if (this.saveButton != null)
		{		
			this.saveButton.visible = true;
		}
		
		if (this.loadButton != null)
		{			
			this.loadButton.visible = true;
		}
		
		if (this.closeButton != null)
		{		
			this.closeButton.visible = true;
		}
		
		if (this.worldNameInput != null)
		{
			this.worldNameInput.show();
		}
		
		if (this.worldNameLoadInput != null)
		{
			this.worldNameLoadInput.show();
		}
	}

	@Override 
	public void hide()
	{
		super.hide();

		if (this.saveButton != null)
		{			
			this.saveButton.visible = false;
		}
		
		if (this.loadButton != null)
		{		
			this.loadButton.visible = false;
		}
		
		if (this.closeButton != null)
		{		
			this.closeButton.visible = false;
		}
		
		if (this.worldNameInput != null)
		{
			this.worldNameInput.hide();
		}
		
		if (this.worldNameLoadInput != null)
		{
			this.worldNameLoadInput.hide();
		}
	}
	
	@Override
	public void update()
	{
		super.update();	
		
		if (!this.visible) return;
		
		checkMouseClicks();
		
		if (this.focusedTextInput != null)
		{
			this.focusedTextInput.update();
			return;
		}
		
		checkKeys();
		
	}
	
	private void checkKeys()
	{
	}
	
	private void checkMouseClicks()
	{
		if (this.closeButton.mouseClicked)
		{
			WorldEditor me = (WorldEditor)this.inScene;
			me.closeMenu();
			return;
		}
		else if (this.saveButton.mouseClicked && this.saving == false)
		{
			this.saving = true;
			
			WorldEditor me = (WorldEditor)this.inScene;
			me.saveMap("w_" + this.worldNameInput.getText());
			
			FadingTextDisplay saveStatusDisplay = new FadingTextDisplay(inScene, "World Saved", this.screenX + 10, this.screenY + this.getHeight() - 15, Color.GREEN, 2000, Game.TOP_DRAW_LAYER);
			saveStatusDisplay.setInMenu(this);
			this.addTextDisplay(saveStatusDisplay);
			
			SceneTask task = new SceneTask(this.getScene())
			{
				@Override
				public void run()
				{
					WorldEditor mapEditor = (WorldEditor)this.scene;
					mapEditor.detailedMenu.focusedTextInput.setFocus(false);
					mapEditor.detailedMenu.focusedTextInput = null;
					mapEditor.detailedMenu.saving = false;
				}
			};
			
			Timer t = new Timer();
			t.schedule(task, 2100);
			
			this.saveButton.mouseClicked = false;
		}
		else if (this.loadButton.mouseClicked && this.loading == false)
		{
			this.loading = true;
			
			WorldEditor me = (WorldEditor)this.getScene();
			int[][] maps = SaveLoadHandler.loadCSVints(("w_" + this.worldNameLoadInput.getText()), LoadDataType.WORLD);
			String[][] mapNames = SaveLoadHandler.loadCSVstrings(("w_" + this.worldNameLoadInput.getText()), LoadDataType.WORLD_NAMES);
			
			me.injectMapData(maps, mapNames);
			
			FadingTextDisplay loadStatusDisplay = new FadingTextDisplay(inScene, "Loading...", this.screenX + 10, this.screenY + this.getHeight() - 15,
					Color.yellow, 2000, Game.TOP_DRAW_LAYER);
			
			loadStatusDisplay.setInMenu(this);
			this.addTextDisplay(loadStatusDisplay);
			
			SceneTask task = new SceneTask(this.inScene)
			{
				@Override
				public void run()
				{
					WorldEditor worldEditor = (WorldEditor)this.scene;
					worldEditor.detailedMenu.focusedTextInput.setFocus(false);
					worldEditor.detailedMenu.focusedTextInput = null;
					worldEditor.detailedMenu.loading = false;
					
					FadingTextDisplay loadStatusDisplay = new FadingTextDisplay(inScene, "Map Loaded", worldEditor.detailedMenu.screenX + 10, 
							worldEditor.detailedMenu.screenY + worldEditor.detailedMenu.getHeight() - 15, 
							Color.yellow, 2000, Game.TOP_DRAW_LAYER);
					
					loadStatusDisplay.setInMenu(worldEditor.detailedMenu);
					worldEditor.detailedMenu.addTextDisplay(loadStatusDisplay);
					
				}
			};
			
			Timer t = new Timer();
			t.schedule(task, 2100);
			
			this.loadButton.mouseClicked = false;
		}
		else if (this.worldNameInput.mouseClicked)
		{
			WorldEditor worldEditor = (WorldEditor)this.inScene;
			
			worldEditor.detailedMenu.focusedTextInput.setFocus(false);
			worldEditor.detailedMenu.focusedTextInput = this.worldNameInput;
			worldEditor.detailedMenu.focusedTextInput.setFocus(true);
			
			this.worldNameInput.mouseClicked = false;
		}
		else if (this.worldNameLoadInput.mouseClicked)
		{
			WorldEditor mapEditor = (WorldEditor)this.inScene;
			
			mapEditor.detailedMenu.focusedTextInput.setFocus(false);
			mapEditor.detailedMenu.focusedTextInput = this.worldNameLoadInput;
			mapEditor.detailedMenu.focusedTextInput.setFocus(true);
			
			this.worldNameLoadInput.mouseClicked = false;
		}
	}
	
	@Override
	public void close() 
	{
		this.hide();
	}

	@Override
	public void onMouseClick(MouseEvent e) 
	{
	}

	@Override
	public void onMousePressed(MouseEvent e) 
	{
	}

	@Override
	public void onMouseReleased(MouseEvent e) 
	{
	}

}