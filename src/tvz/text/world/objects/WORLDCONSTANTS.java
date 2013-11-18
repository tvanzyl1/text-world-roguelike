package tvz.text.world.objects;

import java.io.Serializable;

public class WORLDCONSTANTS  implements Serializable{
	private static final long serialVersionUID = 5216094451313192783L;
	public static final int SCREENMAXX = 74;
	public static final int SCREENMAXY = 20;
	public static final int IMAGEMAPX = 100;
	public static final int IMAGEMAPY = 100;
	public static final int NORTH = 8;
	public static final int EAST = 6;
	public static final int SOUTH = 2;
	public static final int WEST = 4;
	public static final int NO_DIRECTION = 5;
	public static final int HUNGERDELAY = 80;
	public static final int THIRSTDELAY = 80;

	public static final int DAMAGE_HAND = 60;
	public static final int DAMAGE_KNIFE = 80;
	public static final int DAMAGE_AXE = 100;
	public static final int DAMAGE_REVOLVER = 100;

	//Characters
	public static final String[] FOOD_CHAR = {"۵","ᾱ"};
	public static final String WATER_CHAR ="۝";
	public static final String HEALTH_CHAR= "Θ";

	public static final String[]CHAR_GRASS = {"ˬ","ʬ","̧","˯","͖"};
	public static final String[]CHAR_TOMBSTONE = {"†","۩"};
	public static final char 	CHAR_ZOMBIE = '☻';
	public static final String 	CHAR_KNIFE = "ψ";
	public static final String 	CHAR_AXE = "آ";
	public static final String 	CHAR_SLEDGE = "ﴽ";
	public static final String[]CHAR_ARROW = {"←","↑","→","↓"};
	public static final String 	CHAR_HAND_GUN = "⌐";
	public static final String 	CHAR_SHOTGUN="⌡";
	public static final String 	CHAR_BULLET = "•";
	public static final String 	CHAR_BOW = ")";
	public static final String 	CHAR_TREE = "▲";
	public static final String 	CHAR_WOOD = "▓";
	public static final String 	CHAR_BUILDING_WALL = "▓";
	public static final String 	CHAR_ROAD = "░";
	public static final String 	CHAR_STONE = "█";

	public static final int MAXHEALTH = 32000000;
	public static final int OBJ_HEALTH_TREE = 200;
	public static final int OBJ_HEALTH_WALL = 200;

	public static final long RESPAWNTIME = 1*60*60*1000;

	public static final String OBJ_TYPE_MAINCHAR = "mainChar";
	/**
	 * A consant in miliseconds to print a bullet flying
	 */
	public static final long ATTACK_PRINT_RATE_MS = 100;

	public static final String OBJ_TYPE_ZOMBIE = "zombie";
	public static final String OBJ_TYPE_TREE   = "tree";
	public static final String OBJ_TYPE_HAND   = "hand";
	public static final String OBJ_TYPE_AXE    = "axe";
	public static final String OBJ_TYPE_WOOD   = "wood";
	public static final String OBJ_TYPE_STONE  = "stne";
	public static final String OBJ_TYPE_SLEDGE = "sldg";
	public static final String OBJ_TYPE_WATER  = "H2O";
	public static final String OBJ_TYPE_FOOD   = "food";
	public static final String OBJ_TYPE_HEALTH = "hlth";
	public static final String OBJ_TYPE_WALL   = "wall";
	public static final String OBJ_TYPE_RIVER  = "river";

	public static final int FOOD_WATER_HEALING_FACTOR = 20;
	public static final String AREA_NAME_RIVER = "River Z";


	public static enum WeaponTypeEnum {
	    HAND_WEAPON, LONG_RANGE_WEAPON
	}


}
