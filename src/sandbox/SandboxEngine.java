package sandbox;

import java.awt.Color;

import luna2d.engine.ColorHandler;
import luna2d.engine.Game;
import luna2d.engine.ResourceHandler;
import sandbox.scenes.MapEditor;
import sandbox.scenes.WorldEditor;
import sandbox.scenes.WorldPlayer;

public class SandboxEngine extends Game 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// -------- LAYERS ----------------------
	public static final int ENVIRONMENT_DRAW_LAYER = 2; 
	//---------------------------------------
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 640;
		
	public static void main(String[] args)
	{
		Game g = new Game();
		Game.DATA_DIR = System.getProperty("user.dir") + "/data/";
		Game.GAME_SAVE_DIR = System.getProperty("user.dir") + "/data/saves/";
		Game.GAME_RES_DIR = System.getProperty("user.dir") + "/data/res/";
		
		g.init(WIDTH, HEIGHT, "Sandbox", Color.black, "src/sandbox/res/");
		
		Sandbox sandbox = new Sandbox("Sandbox");		
		g.sceneManager.addScene(sandbox);
		
		WorldEditor worldEditor = new WorldEditor("WorldEditor");
		g.sceneManager.addScene(worldEditor);
		
		MapEditor mapEditor = new MapEditor("MapEditor");
		g.sceneManager.addScene(mapEditor);
		
		WorldPlayer worldPlayer = new WorldPlayer("WorldPlayer");
		g.sceneManager.addScene(worldPlayer);
		
		loadImages();
		createColors();
		
		g.beginSceneEngine("Sandbox");
		
	}	
	
	private static void createColors()
	{
		ColorHandler.addColor("GrassGreen", new Color(52, 122, 115));
		ColorHandler.addColor("GrassYellow", new Color(155, 161, 95));
		ColorHandler.addColor("GrassGridYellow", new Color(155, 161, 95), 0.25f);
		ColorHandler.addColor("WaterBlue", new Color(118, 135, 171));
	}
	
	private static void loadImages()
	{
		ResourceHandler.addImage("Player", "player-idle_16x16_4-frames.png");
		ResourceHandler.addImage("Bush", "bush.png");
		ResourceHandler.addImage("Tree", "tree.png");
		ResourceHandler.addImage("Water", "water.png");
		ResourceHandler.addImage("Rock", "rock.png");
		ResourceHandler.addImage("Rain", "rain.png");
	}
}

