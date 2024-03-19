package luna2d.templates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import luna2d.Game;
import luna2d.Log;
import luna2d.Maths;
import luna2d.ObjectTypes;
import luna2d.Scene;
import luna2d.Vector2;
import luna2d.WorldPosition;
import luna2d.playerControllers.SimplePlayer;
import luna2d.renderables.FillBar;
import luna2d.Utilites;
import luna2d.renderables.TextDisplay;
import luna2d.templates.ui.Backpack;

public class Player extends SimplePlayer
{
	private Backpack backpack;
	private boolean backpackLock, pauseLock;
	
	private ObjectTypes holdingType;
	private Object holdingObject;
	
	private float hunger, temperature;
	private float hungerDrain = 0.02f;
	
	private FillBar healthBar, hungerBar, temperatureBar;
	
	private TextDisplay timeLabel, weatherLabel;
	
	public Player(Scene inScene, String imageName, int x, int y, int scale, int cellSize, int frames,
			int msBetweenFrames) 
	{
		super(inScene, imageName, x, y, scale, cellSize, frames, msBetweenFrames);
		
		this.sprite.enableCulling = false;
		this.holdingType = ObjectTypes.Empty;
		this.backpackLock = false;
		this.pauseLock = false;	
		
		int currentY = 60;
		int statsPaddingY = 18;
		
		healthBar = new FillBar(Math.round(this.health), Game.WIDTH / 2 - cellSize * 2, Game.HEIGHT / 2 - cellSize * 2 - 12, 
				cellSize * 2, 4, 2, 1, Color.GRAY, Color.WHITE, Color.GREEN, inScene);
		healthBar.setEnableCameraScaling(false);
		healthBar.setFixedScreenPosition(true);
		
		this.hunger = 100;
		new TextDisplay(inScene, "Hunger", 10, currentY, Color.white, Game.TOP_DRAW_LAYER);
		hungerBar = new FillBar(Math.round(this.hunger), 60, currentY - 5, cellSize * 3, 4, 2, 1, Color.GRAY, Color.WHITE, Color.GREEN, inScene);
		hungerBar.setEnableCameraScaling(false);
		hungerBar.setFixedScreenPosition(true);
		
		// -----
		currentY += statsPaddingY;
		
		this.temperature = 50;
		new TextDisplay(inScene, "Temp", 10, currentY, Color.white, Game.TOP_DRAW_LAYER);
		temperatureBar = new FillBar(Math.round(this.temperature), 60, currentY - 5, cellSize * 3, 4, 2, 1, Color.GRAY, Color.WHITE, Color.GREEN, inScene);
		temperatureBar.setEnableCameraScaling(false);
		temperatureBar.setFixedScreenPosition(true);
		
		// -----

		this.setZoomingEnabled(true);
		
		this.timeLabel = new TextDisplay(inScene, inScene.getDaysAndTime(), Game.WIDTH - 160, Game.HEIGHT - 50, Color.white, Game.TOP_DRAW_LAYER);
		this.weatherLabel = new TextDisplay(inScene, inScene.getWeather(), 10, 30, Color.white, Game.TOP_DRAW_LAYER);
		
		backpack = new Backpack(inScene);

		this.inScene.setPlayer(this);
		
	}
	
	private boolean playerSave(String gameName)
	{
		String path = Game.GAME_SAVE_DIR + gameName + "/player.play";
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(this.health + "\n");
		builder.append(this.hunger + "\n");
		
		WorldPosition wp = this.getWorldPosition();		
		builder.append(wp.worldRow + "," + wp.worldColumn + "," + wp.mapRow + "," + wp.mapColumn);
		
		return Utilites.saveStringToFile(builder.toString(), path);
	}
	
	public void save(String gameName)
	{
		if (this.playerSave(gameName) && this.backpack.save(gameName))		
		{
			Log.println("Saved player and backpack.");
		}
		else
		{
			Log.println("Error saving player and backpack.");
		}
	}
	
	private boolean playerLoad(String gameName)
	{
		String path = Game.GAME_SAVE_DIR + gameName + "/player.play";
		
		BufferedReader reader;
		try 
		{
			reader = new BufferedReader(new FileReader(path));
			
			String line = "";
			int i = 0;
			while((line = reader.readLine()) != null)
			{
			   	if (i == 0)
			   	{
			   		// health
			   		this.health = Float.parseFloat(line);
			   	}
			   	else if (i == 1)
			   	{
			   		// hunger
			   		this.hunger = Float.parseFloat(line);
			   	}
			   	
				i++;
			}
			
			reader.close();
			
			return true;
			
		} 
		catch (IOException | NumberFormatException e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void load(String gameName)
	{
		if (this.playerLoad(gameName) && this.backpack.load(gameName))
		{
			Log.println("Player and backpack loaded.");
		}
		else
		{
			Log.println("Failed to load player and backpack.");
		}
	}
	
	@Override 
	public void updateWorldPosition(WorldPosition p)
	{
		super.updateWorldPosition(p);
		this.sprite.updateWorldPosition(p);
	}
	
	public WorldPosition getWorldPosition()
	{
		return this.worldPosition;
	}
	
	public void setCustomRender(boolean val)
	{
		this.sprite.setCustomRender(val);
	}
	
	public boolean addToBackpack(InventoryItem item)
	{
		return this.backpack.addToBackpack(item);
	}
	
	public boolean backpackFull()
	{
		return this.backpack.isFull();
	}
	
	public void useItem(ObjectTypes type)
	{
		switch(type)
		{
		case Bush:
			break;
		case Empty:
			break;
		case GndDirt:
			break;
		case GndGrass:
			break;
		case GndRock:
			break;
		case GndWater:
			break;
		case InvBerries:
			break;
		case Player:
			break;
		case Tree:
			break;
		case Water:
			break;
		case Wolf:
			break;
		case FenceHorz:
			break;
		case FenceVert:
			break;
		case Fire:
			break;
		case InvFence:
			break;
		case InvRock:
			break;
		case Rock:
			break;
		case ThrownRock:
			break;
		default:
			break;
		
		}
	}
	
	public void readyHoldItem(ObjectTypes type)
	{
		this.holdingType = type;
		
		switch(type)
		{
		case Bush:
			break;
		case Empty:
			break;
		case FenceHorz:
			break;
		case FenceVert:
			break;
		case Fire:
			break;
		case GndDirt:
			break;
		case GndGrass:
			break;
		case GndRock:
			break;
		case GndWater:
			break;
		case InvBerries:
			break;
		case InvFence:
			break;
		case InvFire:
			break;
		case InvTorch:
		case InvRock:
			break;
		case Player:
			break;
		case Rock:
			break;
		case ThrownRock:
			break;
		case Tree:
			break;
		case Water:
			break;
		case Wolf:
			break;
		default:
			break;
		
		}
	}
	
	public void eat(int hunger)
	{
		this.hunger += hunger;
		this.hunger = Maths.clamp(this.hunger, 100, 0);
	}
	
	public void pickup(ObjectTypes type, int amount)
	{
		InventoryItem item = null;
		
		switch(type)
		{
		case Bush:
			break;
		case Empty:
			break;
		case GndDirt:
			break;
		case GndGrass:
			break;
		case GndRock:
			break;
		case GndWater:
			break;
		case InvBerries:
			break;
		case Player:
			break;
		case Tree:
			break;
		case Water:
			break;
		case Wolf:
			break;
		case InvRock:
			break;
		case Rock:
			break;
		default:
			break;
		}
		
		if (item == null) return;
		
		backpack.addToBackpack(item);
	}

	@Override
	protected void render(Graphics g) 
	{			
	}
	
	private void checkKeys()
	{
		if (!this.backpackLock && this.isKeyPressed(KeyEvent.VK_TAB))
		{
			this.backpackLock = true;
			this.backpack.toggleVisible();
		}
		else if (!this.isKeyPressed(KeyEvent.VK_TAB))
		{
			this.backpackLock = false;
		}
	}
	
	private void handlePause()
	{
		if (!this.pauseLock && this.getScene().isKeyPressed(KeyEvent.VK_P))
		{
			this.pauseLock = true;
			Game.paused = !Game.paused;
			return;
		}
		else if (!this.getScene().isKeyPressed(KeyEvent.VK_P)) 
		{
			this.pauseLock = false;
		}
	}
	
	@Override 
	public void pauseTick()
	{
		this.handlePause();
	}

	@Override
	protected void update() 
	{
		super.update();
		this.handlePause();
		
		checkKeys();
		Game.updatePlayerPosition(this.getInternalX(), this.getInternalY(), 200);
		
		this.timeLabel.updateText(this.inScene.getDaysAndTime());
		this.weatherLabel.updateText(this.inScene.getWeather());
		
		healthBar.setValue(Math.round(this.health));
		
		handleHunger();
		
		this.handleWorldPosition();
	}
	
	private void handleWorldPosition()
	{
		Vector2 gPos = Maths.convertToGrid(this.getWorldX(), this.getWorldY(), Game.CELL_SIZE * Game.CAMERA_SCALE);
		WorldPosition pWP = Maths.convertToWorldPosition(gPos, Game.CAMERA_SCALE, Game.ROWS, Game.COLUMNS);

		this.updateWorldPosition(pWP);
	}
	
	private void handleHunger()
	{
		float drain = this.hungerDrain; // also with conditions
		
		this.hunger -= drain;
		this.hunger = Maths.clamp(this.hunger, 100, 0);
		
		hungerBar.setValue(Math.round(this.hunger));
		
		if (this.hunger < 1)
		{
			this.health -= 0.05f;
		}
		else if(this.hunger > 75)
		{
			this.health += 0.05f;
			this.health = Maths.clamp(this.health, 100, 0);
		}
	}

	@Override
	protected void onMouseClick(MouseEvent e) 
	{
			
	}

	@Override
	protected void onMousePressed(MouseEvent e) 
	{
	}

	@Override
	protected void onMouseReleased(MouseEvent e) 
	{	
	}

}