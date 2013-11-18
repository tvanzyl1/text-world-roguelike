package tvz.text.world.services;

import java.util.List;

import net.slashie.libjcsi.CSIColor;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;
import tvz.text.world.objects.CharacterObject;
import tvz.text.world.objects.ConfigObject;
import tvz.text.world.objects.TextWorldObject;
import tvz.text.world.objects.WORLDCONSTANTS;
import tvz.text.world.objects.WorldObject;
import tvz.text.world.objects.WorldXY;

public class PrintService {
	private static ConsoleSystemInterface csi;

	/**
	 * Create a new service with an interface.
	 */
	public PrintService(){
		setCsi(new WSwingConsoleInterface("Text World.",false));
	}

	/**
	 * Returns a new instance
	 * @return {@link PrintService}
	 */
	public static PrintService newInstance() {

		return new PrintService();
	}


	/**
	 * @param csi the csi to set
	 */
	public static void setCsi(ConsoleSystemInterface csi) {
		PrintService.csi = csi;
	}

	/**
	 * @return the csi
	 */
	public static ConsoleSystemInterface getCsi() {
		return csi;
	}

	public static void print(int x, int y, String str){
		getCsi().print(x,y,str);
	}

	private static void print(int x, int y, String string, CSIColor color) {
		getCsi().print(x,y,string,color);
	}

	public void print(int x, int y, char asciiChar, char asciiChar2) {
		getCsi().print(x,y,asciiChar,asciiChar2);
	}

	/**
	 * The main print method of the game.
	 * @param world
	 */
	public static void print(TextWorldObject world){
		CharacterObject mainChar = (CharacterObject) world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(world.getWorldObjects().get(world.getMapxmapy()).getMainCharIndex());

		//PrintWorld
		printWorld(world,mainChar);
		//Print HUD
		printHUD(mainChar,world);
		printInventory(mainChar,world);


	}

	/**
	 * Prints a world.area and all objects that are visible.
	 * Use mainChar.sightDistance variable and some time to print visibility.
	 * @param csi
	 * @param world
	 * @param mainChar
	 */
	private static void printWorld(TextWorldObject world, CharacterObject mainChar) {
		List<Object> objectList = world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList();
		for(int x = 0; x<objectList.size();x++){
			try{

				if(((WorldObject) objectList.get(x)).isAlive()){
					if(MapService.inScreenRange(((WorldObject) objectList.get(x)).getX(), ((WorldObject) objectList.get(x)).getY()) && world.getConfig().getViewType() == ConfigObject.ViewTypeEnum.SEE_ALL){
						getCsi().print(((WorldObject)objectList.get(x)).getX(), ((WorldObject)objectList.get(x)).getY(),((WorldObject)objectList.get(x)).getAsciiChar(),((WorldObject)objectList.get(x)).getObjectColour());
					} else if(MapService.inScreenRange(((WorldObject) objectList.get(x)).getX(), ((WorldObject) objectList.get(x)).getY()) && world.getWorldObjects().get(world.getMapxmapy()).getVisibleArea()[((WorldObject)objectList.get(x)).getX()][((WorldObject)objectList.get(x)).getY()]==1){
						getCsi().print(((WorldObject)objectList.get(x)).getX(), ((WorldObject)objectList.get(x)).getY(),((WorldObject)objectList.get(x)).getAsciiChar(),((WorldObject)objectList.get(x)).getObjectColour());
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				System.err.println("Tried to print : "+((WorldObject)objectList.get(x)).getAsciiChar()+" at X :"+((WorldObject)objectList.get(x)).getX()+", Y :"+((WorldObject)objectList.get(x)).getY());
				System.exit(0);
			}
		}
	}


	/**
	 * Print the health, hunger and thirst
	 * @param mainChar
	 * @param world
	 */
	private static void printHUD(CharacterObject mainChar, TextWorldObject world) {
		getCsi().print(76, 1, "Name");
		getCsi().print(76, 2, "♥"+mainChar.getHealth(),CSIColor.RED);
		getCsi().print(76, 3, "H"+mainChar.getHunger(),CSIColor.BROWN);
		getCsi().print(76, 4, "≈"+mainChar.getThirst(),CSIColor.LIGHT_BLUE);
		getCsi().print(40, 21, world.getWorldObjects().get(world.getMapxmapy()).getAreaName());
	}

	/**
	 * Print the inventory
	 * @param mainChar
	 * @param world
	 */
	private static void printInventory(CharacterObject mainChar, TextWorldObject world){
		if(mainChar.getInventory() == null)
		 getCsi().print(1,22,"free free free free free free free free free hand");
		else{
			getCsi().print(1,22,mainChar.getInventoryItemNameAt(1),selectedColor(1,mainChar.getInventoryIndex()));getCsi().print(1,23,mainChar.getInventoryItemAmountAt(1));
			getCsi().print(6,22,mainChar.getInventoryItemNameAt(2),selectedColor(2,mainChar.getInventoryIndex()));getCsi().print(6,23,mainChar.getInventoryItemAmountAt(2));
			getCsi().print(11,22,mainChar.getInventoryItemNameAt(3),selectedColor(3,mainChar.getInventoryIndex()));getCsi().print(11,23,mainChar.getInventoryItemAmountAt(3));
			getCsi().print(16,22,mainChar.getInventoryItemNameAt(4),selectedColor(4,mainChar.getInventoryIndex()));getCsi().print(16,23,mainChar.getInventoryItemAmountAt(4));
			getCsi().print(21,22,mainChar.getInventoryItemNameAt(5),selectedColor(5,mainChar.getInventoryIndex()));getCsi().print(21,23,mainChar.getInventoryItemAmountAt(5));
			getCsi().print(26,22,mainChar.getInventoryItemNameAt(6),selectedColor(6,mainChar.getInventoryIndex()));getCsi().print(26,23,mainChar.getInventoryItemAmountAt(6));
			getCsi().print(31,22,mainChar.getInventoryItemNameAt(7),selectedColor(7,mainChar.getInventoryIndex()));getCsi().print(31,23,mainChar.getInventoryItemAmountAt(7));
			getCsi().print(36,22,mainChar.getInventoryItemNameAt(8),selectedColor(8,mainChar.getInventoryIndex()));getCsi().print(36,23,mainChar.getInventoryItemAmountAt(8));
			getCsi().print(41,22,mainChar.getInventoryItemNameAt(9),selectedColor(9,mainChar.getInventoryIndex()));getCsi().print(41,23,mainChar.getInventoryItemAmountAt(9));
			getCsi().print(46,22,"hand",selectedColor(0,mainChar.getInventoryIndex()));
		}
	}

	/**
	 * Change the inventory item's colour if selected.
	 * @param i
	 * @param inventoryIndex
	 * @return
	 */
	private static CSIColor selectedColor(int i, int inventoryIndex) {
		if(i==inventoryIndex) return CSIColor.YELLOW;
		return CSIColor.WHITE;
	}

	/**
	 * The main menu
	 */
	public static void printMainMenu() {
		print(11,10,"New Game",CSIColor.AQUAMARINE);
		print(11,11,"Continue",CSIColor.AQUAMARINE);
		print(11,12,"Options",CSIColor.AQUAMARINE);
		print(11,13,"Exit",CSIColor.AQUAMARINE);
	}

	public void cls() {
		getCsi().cls();

	}

	public void saveBuffer() {
		getCsi().saveBuffer();

	}

	public void restore() {
		getCsi().restore();

	}

	public void refresh() {
		getCsi().refresh();

	}

	/**
	 * Menu selector
	 * @param xy
	 */
	public void printMenuSelector(WorldXY xy) {
		print(xy.getX(),xy.getY(),"@",CSIColor.AQUAMARINE);
	}

	/**
	 * The in-game menu
	 */
	public static void printInGameMenu() {
		print(2,1,"Continue",CSIColor.AQUAMARINE);
		print(2,2,"Save and Exit",CSIColor.AQUAMARINE);
	}

	/**
	 * The help screen
	 */
	public void printHelp() {
		cls();
		refresh();
		print(1,1,"Arrows to move.");
		print(1,2,"WASD to action a weapon or placeable block in a direction.");
		print(1,3,"E to action a non weapon.");
		print(1,4,"F to forfeit a turn.");
		print(1,6, "Zombie   : "+WORLDCONSTANTS.CHAR_ZOMBIE);
		print(1,7, "Tree     : "+WORLDCONSTANTS.CHAR_TREE);
		print(1,8, "Wood     : "+WORLDCONSTANTS.CHAR_WOOD);
		print(1,9, "Water    : "+WORLDCONSTANTS.WATER_CHAR);
		print(1,10,"Food     : ");for(int i=0;i<WORLDCONSTANTS.FOOD_CHAR.length;i++) print(12+i,10,WORLDCONSTANTS.FOOD_CHAR[i]);
		print(1,11,"Knife    : "+WORLDCONSTANTS.CHAR_KNIFE);
		print(1,12,"Axe      : "+WORLDCONSTANTS.CHAR_AXE);
		print(1,13,"Sledge   : "+WORLDCONSTANTS.CHAR_SLEDGE);
		print(1,14,"Bow      : "+WORLDCONSTANTS.CHAR_BOW);
		print(1,15,"Hand gun : "+WORLDCONSTANTS.CHAR_HAND_GUN);
		print(1,16,"Rifle    : "+WORLDCONSTANTS.CHAR_SHOTGUN);
		print(1,17,"Tombstone: ");for(int i=0;i<WORLDCONSTANTS.CHAR_TOMBSTONE.length;i++) print(17+i,17,WORLDCONSTANTS.CHAR_TOMBSTONE[i]);
		print(1,18,"Grass    : ");for(int i=0;i<WORLDCONSTANTS.CHAR_GRASS.length;i++) print(17+i,18,WORLDCONSTANTS.CHAR_GRASS[i]);
		print(1,19,"Wall     : "+WORLDCONSTANTS.CHAR_BUILDING_WALL);
		print(1,20,"Road     : "+WORLDCONSTANTS.CHAR_ROAD);
		restore();
		getCsi().inkey();

	}





}
