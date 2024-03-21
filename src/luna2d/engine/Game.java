package luna2d.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import luna2d.renderables.TextDisplay;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -2680723036795663013L;
	
	public static final int GRIDY_OFFSET = 5;
	public static final int ROWS = 37;
	public static final int COLUMNS = 49;
	public static final int CELL_SIZE = 16;
	public static final int WORLD_WIDTH = CELL_SIZE * COLUMNS;
	public static final int WORLD_HEIGHT = CELL_SIZE * ROWS;
	
	
	public static String DATA_DIR = System.getProperty("user.dir") + "/data/";
	public static String GAME_SAVE_DIR = System.getProperty("user.dir") + "/data/saves/";
	public static String GAME_RES_DIR = System.getProperty("user.dir") + "/data/res/";
	
	public static final int PLAYER_ID = -99;	
	public static final int RESOURCE_ACTION_DISTANCE = 50;
	
	// -------- LAYERS ----------------------
	public static final int DRAW_LAYERS = 50;
	public static final int BOTTOM_DRAW_LAYER = 0;
	public static final int TOP_DRAW_LAYER = DRAW_LAYERS - 1;
	public static final int ENVIRONMENT_DRAW_LAYER = 2; 
	//---------------------------------------
	
	private static Rectangle screenBounds;
	
	public static int CAMERA_X = 0;
	public static int CAMERA_Y = 0;
	private static int CAMERA_X_OFFSET = 0;
	private static int CAMERA_Y_OFFSET = 0;
	public static int CAMERA_SCALE = 1;
	
	public static boolean paused = false;
	public static boolean DEBUG = true;
	
	protected Window window;
	public SceneManager sceneManager;
	private InputHandler inputHandler;
	private MouseHandler mouseHandler;
	private static WeatherSystem weatherSystem;
	
	private Thread mainGameThread;
	private boolean gameRunning = false;
	
	public static int WIDTH, HEIGHT, FPS;
	public static int mouseX, mouseY, mouseWorldX, mouseWorldY;
	private String title;
	private Color bkgColor;
	
	private TextDisplay lbFramesPerSecond;
	
	private static Game instance;
	
	public static Game getInstance() { return instance; }
		
	public void init(int width, int height, String title, Color bkgColor, String resourceDir)
	{
		Game.instance = this;
		
		if (!Utilites.directoryExists(DATA_DIR))
		{
			Utilites.createDirectory(DATA_DIR);
		}
		
		if (!Utilites.directoryExists(GAME_SAVE_DIR))
		{
			Utilites.createDirectory(GAME_SAVE_DIR);
		}
		
		if (!Utilites.directoryExists(GAME_RES_DIR))
		{
			Utilites.createDirectory(GAME_RES_DIR);
		}
		
		WIDTH = width;
		HEIGHT = height;
		
		this.title = title;
		this.bkgColor = bkgColor;		
		
		sceneManager = new SceneManager(this);		
		window = new Window(WIDTH, HEIGHT, this.title, this);
		
		screenBounds = new Rectangle(0, 0, WIDTH - 200, HEIGHT - 200);
	}
	
	public static void updatePlayerPosition(int x, int y, int padding)
	{
		screenBounds.x = x - padding / 2;
		screenBounds.y = y - padding / 2;
		screenBounds.width = Game.WIDTH + padding;
		screenBounds.height = Game.HEIGHT + padding;
	}
	
	public static Rectangle getScreenBounds()
	{
		return screenBounds;
	}
	
	public Window getWindow()
	{
		return this.window;
	}
	
	public void updateScale(int scale)
	{
		CAMERA_SCALE = scale;
		
		if (this.getObjectHandler() == null)
		{
			Log.println("Object handler not initialized. Failed to update scale.");
			return;
		}		
	}
	
	public void updateCameraOffset(int x, int y)
	{
		CAMERA_X_OFFSET = x;
		CAMERA_Y_OFFSET = y;
	}
	
	public void setBkgColor(Color c)
	{
		this.bkgColor = c;
	}
	
	public SceneManager getSceneManager()
	{
		return sceneManager;
	}
	
	public MouseHandler getMouseHandler()
	{
		return this.mouseHandler;
	}
	
	public ObjectHandler getObjectHandler()
	{
		if (this.sceneManager != null)
		{
			if (this.sceneManager.getCurrentScene() != null)
			{
				return this.sceneManager.getCurrentScene().getObjectHandler();
			}
		}
		
		return null;
	}
	
	public static WeatherSystem getWeatherSystem()
	{
		return weatherSystem;
	}
	
	private void tick()
	{
		CAMERA_X = -CAMERA_X_OFFSET;
		CAMERA_Y = -CAMERA_Y_OFFSET;
		
		if (this.sceneManager == null) return;
		
		if (this.inputHandler == null)
		{
			this.inputHandler = new InputHandler(this);
			this.addKeyListener(inputHandler);			
		}
		
		if (this.mouseHandler == null || this.mouseHandler.failedToInitialized)
		{
			this.mouseHandler = new MouseHandler(this);
			this.addMouseListener(mouseHandler);
		}
		
		if (this.inputHandler == null || this.mouseHandler == null) return;
		
		if (!ResourceHandler.initialized)
		{
			ResourceHandler.init();
		}
		
		if (weatherSystem == null)
		{
			weatherSystem = new WeatherSystem();
		}
		
		if (this.lbFramesPerSecond == null)
		{
			this.lbFramesPerSecond = new TextDisplay(null, "FPS: " + Game.FPS, Game.WIDTH/2 - 20, 40, Color.white, Game.TOP_DRAW_LAYER);
		}
		
		this.sceneManager.update();
	}
	
	private void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(this.bkgColor);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		this.sceneManager.render(g);
		
		if (DEBUG && this.lbFramesPerSecond != null)
		{
			this.lbFramesPerSecond.render(g);			
		}
		
		g.dispose();
		bs.show();
	}

	public void beginSceneEngine(String name)
	{
		this.sceneManager.startEngine(name);
	}
	
	public synchronized void start()
	{
		mainGameThread = new Thread(this);
		mainGameThread.start();
		this.gameRunning = true;
	}
	
	public void endGame()
	{
		this.gameRunning = false;
	}
	
	private synchronized void stop()
	{
		System.exit(0);
	}
	
	@Override
	public void run() 
	{
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int framesPerSecond = 0;
		
		while (this.gameRunning)
		{
			mouseX = (MouseInfo.getPointerInfo().getLocation().x - this.window.getFrame().getLocationOnScreen().x) - 8;
			mouseY = (MouseInfo.getPointerInfo().getLocation().y - this.window.getFrame().getLocationOnScreen().y) - 32;
			
			mouseWorldX = -Game.CAMERA_X + mouseX;
			mouseWorldY = -Game.CAMERA_Y + mouseY;
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1)
			{
				tick();
				delta--;
			}
			
			if(this.gameRunning)
			{
				render();
			}
			
			framesPerSecond++;
				
			if(System.currentTimeMillis() - timer > 1000)
			{
				FPS = framesPerSecond;
				
				if (this.lbFramesPerSecond != null)
				{
					this.lbFramesPerSecond.updateText("FPS: " + FPS);
				}
				
				timer += 1000;
				framesPerSecond = 0;
			}
		
		}
		
		stop();
		
	}
}
