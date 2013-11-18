package tvz.text.world.services;

import net.slashie.libjcsi.CharKey;
import tvz.text.world.objects.CharacterObject;
import tvz.text.world.objects.WORLDCONSTANTS;

public class KeyPressService {

	public static void getKeys(CharacterObject mainChar, int key) {
    	mainChar.setPrevY(mainChar.getY());
    	mainChar.setPrevX(mainChar.getX());
    	checkMovementKeys(mainChar,key);
    	checkActionKeys(mainChar,key);
    	checkInventoryKeys(mainChar,key);

	}

	/**
	 * Keys X and Z to cycle through inventory index
	 * @param mainChar
	 * @param key
	 */
	private static void checkInventoryKeys(CharacterObject mainChar, int key) {
		switch (key){
        case CharKey.x: case CharKey.X:
        	mainChar.setInventoryIndex(mainChar.getInventoryIndex()+1);
        	break;
        case CharKey.z: case CharKey.Z:
        	mainChar.setInventoryIndex(mainChar.getInventoryIndex()-1);
        	break;
        }
		if(mainChar.getInventoryIndex()<0) mainChar.setInventoryIndex(9);
		if(mainChar.getInventoryIndex()>9) mainChar.setInventoryIndex(0);
	}

	/**
	 * Check action keys. Shoot or hit in a direction, or discard item
	 * @param mainChar
	 * @param key
	 */
	private static void checkActionKeys(CharacterObject mainChar, int key) {
		switch (key){
        case CharKey.W: case CharKey.w:
        	mainChar.setMovementAction(false);
        	mainChar.setActionInventoryItem(true);
        	mainChar.setActionDirection(WORLDCONSTANTS.NORTH);
        	break;
        case CharKey.D: case CharKey.d:
        	mainChar.setMovementAction(false);
        	mainChar.setActionInventoryItem(true);
        	mainChar.setActionDirection(WORLDCONSTANTS.EAST);
        	break;
        case CharKey.S: case CharKey.s:
        	mainChar.setMovementAction(false);
        	mainChar.setActionInventoryItem(true);
        	mainChar.setActionDirection(WORLDCONSTANTS.SOUTH);
        	break;
        case CharKey.A: case CharKey.a:
        	mainChar.setMovementAction(false);
        	mainChar.setActionInventoryItem(true);
        	mainChar.setActionDirection(WORLDCONSTANTS.WEST);
        	break;
        case CharKey.E: case CharKey.e:
        	mainChar.setMovementAction(false);
        	mainChar.setActionInventoryItem(true);
        	mainChar.setActionDirection(WORLDCONSTANTS.NO_DIRECTION);
        	break;
        case CharKey.F: case CharKey.f: //forfeit turn
        	mainChar.setMovementAction(true);
        	mainChar.setActionInventoryItem(false);
        	break;
        }
	}

	/**
	 * Check movement keys.
	 * @param mainChar
	 * @param key
	 */
	private static void checkMovementKeys(CharacterObject mainChar, int key) {
		switch (key){
        case CharKey.UARROW:
        	mainChar.setY(mainChar.getY()-1);
        	mainChar.setMovementAction(true);
        	mainChar.setActionInventoryItem(false);
        	break;
        case CharKey.DARROW:
        	mainChar.setY(mainChar.getY()+1);
        	mainChar.setMovementAction(true);
        	mainChar.setActionInventoryItem(false);
        	break;
        case CharKey.LARROW:
        	mainChar.setX(mainChar.getX()-1);
        	mainChar.setMovementAction(true);
        	mainChar.setActionInventoryItem(false);
        	break;
        case CharKey.RARROW:
        	mainChar.setX(mainChar.getX()+1);
        	mainChar.setMovementAction(true);
        	mainChar.setActionInventoryItem(false);
        	break;
         }
	}


}
