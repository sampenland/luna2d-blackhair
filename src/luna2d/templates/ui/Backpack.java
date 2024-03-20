package luna2d.templates.ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import luna2d.renderables.FadingTextDisplay;
import luna2d.renderables.TextDisplay;
import luna2d.ui.UIMenu;
import luna2d.templates.InventoryItem;
import luna2d.templates.dataTypes.ObjectTypes;
import luna2d.engine.Game;
import luna2d.engine.Scene;

public class Backpack extends UIMenu
{

	private final static int WIDTH = 300;
	private final static int HEIGHT = 450;
	
	private BackpackItems backpackItems;
	private LinkedList<InventoryItem> items;
	public static final int BACKPACK_ROWS = 11;
	public static final int BACKPACK_COLUMNS = 9;
	private static final int TOTAL_ITEMS = BACKPACK_ROWS * BACKPACK_COLUMNS;
	
	private int x, y;
	
	private TextDisplay title;
	
	public Backpack(Scene inScene) 
	{
		super(inScene, Game.WIDTH/2 - (WIDTH/2) - 225, Game.HEIGHT/2 - (HEIGHT/2) + 30, WIDTH, HEIGHT, new Color(0.2f, 0.2f, 0.2f, 0.4f), 1);
		
		items = new LinkedList<InventoryItem>();
		
		this.x = Game.WIDTH/2 - (WIDTH/2) - 225;
		this.y = Game.HEIGHT/2 - (HEIGHT/2) + 30;
		
		title = new TextDisplay(inScene, "Backpack", this.x + 5, this.y + 15, Color.white, Game.TOP_DRAW_LAYER);
		this.addTextDisplay(title);
		
		backpackItems = new BackpackItems(this, inScene, this.x + 5, this.y + 25, BACKPACK_ROWS, BACKPACK_COLUMNS, 
				32, new Color(0.f, 0.f, 0.f, 0.5f), new Color(1.f, 1.f, 1.f, 0.25f), 1, null);
		backpackItems.enableCulling = false;
		this.addRenderable(backpackItems);
		
	}
	
	public int itemQty(ObjectTypes type)
	{
		return this.backpackItems.itemQty(type);
	}
	
	public boolean isFull()
	{
		return this.backpackItems.totalItems() == TOTAL_ITEMS;
	}
	
	public void showLog(String text, Color color)
	{
		FadingTextDisplay logDisplay = new FadingTextDisplay(inScene, text, 20, Game.HEIGHT - 55, color, 2000, Game.TOP_DRAW_LAYER);
		logDisplay.setInMenu(this);
		this.addTextDisplay(logDisplay);
	}
	
	public boolean addToBackpack(InventoryItem item)
	{
		if (items.size() >= TOTAL_ITEMS) return false;
		
		ObjectTypes t = ObjectTypes.values()[item.TYPE.intValue];
		switch(t)
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
			this.showLog("Added " + item.AMOUNT + " berries", Color.green);
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
			this.showLog("Added " + item.AMOUNT + " rocks", Color.green);
			break;
		case Rock:
			break;
		default:
			break;
		
		}
		
		items.push(item);
		compact();
		return true;
	}
	
	public boolean removeNextTypeFromBackpack(ObjectTypes type)
	{
		int index = -1;
		int cnt = 0;
		for (InventoryItem item : items)
		{
			if (item.TYPE == type)
			{
				index = cnt;
				break;
			}
			cnt++;
		}
		
		if (index != -1)
		{
			items.remove(index);
			compact();
		}
		
		return false;
	}
	
	public boolean removeFromBackpack(InventoryItem item)
	{
		if (items.removeFirstOccurrence(item))
		{
			ObjectTypes t = ObjectTypes.values()[item.TYPE.intValue];
			switch(t)
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
				this.showLog("Removed " + item.AMOUNT + " berries", Color.red);
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
				this.showLog("Removed " + item.AMOUNT + " rocks", Color.red);
				break;
			case Rock:
				break;
			case FenceHorz:
				break;
			case FenceVert:
				break;
			case Fire:
				break;
			case InvFence:
				break;
			case ThrownRock:
				break;
			default:
				break;
			
			}
		}
		compact();
		return true;
	}
	
	private void compact()
	{
		// Compact all items
		
		// Update grid with images
		this.backpackItems.updateItems(items);
		
	}
	
	public boolean save(String gameName)
	{
		return this.backpackItems.save(gameName);
	}
	
	public boolean load(String gameName)
	{
		this.backpackItems.load(gameName);
		this.items = this.backpackItems.getItems();
		this.compact();
		return true;
	}

	@Override
	public void launch() 
	{		
		this.show();
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