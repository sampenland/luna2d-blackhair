package sandbox;

import java.awt.Color;

import luna2d.engine.ColorHandler;
import luna2d.engine.Game;
import sandbox.scenes.Editor;
import sandbox.scenes.Movement;

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
		g.DATA_DIR = System.getProperty("user.dir") + "/data/";
		g.GAME_SAVE_DIR = System.getProperty("user.dir") + "/data/saves/";
		
		g.init(WIDTH, HEIGHT, "Sandbox", Color.black, "src/sandbox/res/");
		
		Sandbox sandbox = new Sandbox("Sandbox");		
		g.sceneManager.addScene(sandbox);
		
		Editor worldEditor = new Editor("Editor");
		g.sceneManager.addScene(worldEditor);
		
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
}

