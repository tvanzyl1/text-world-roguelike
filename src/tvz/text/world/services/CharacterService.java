package tvz.text.world.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.slashie.libjcsi.CSIColor;
import tvz.text.world.objects.CharacterObject;
import tvz.text.world.objects.WORLDCONSTANTS;

public class CharacterService {



	public static CharacterObject NewMainCharacter(int i, int j, String name) {
		CharacterObject character = NewMainCharacter(i, j);
		character.setName(name);
		return character;
	}

	public static CharacterObject NewMainCharacter(int i, int j) {
		CharacterObject charToCreate = new CharacterObject(WORLDCONSTANTS.OBJ_TYPE_MAINCHAR,i,j,'☺',CSIColor.ATOMIC_TANGERINE,false,true,100);
		charToCreate.setAlive(true);
		charToCreate.setAmmo(new ArrayList<Integer>());
		charToCreate.setArmourHealth(0);
		charToCreate.setCanMove(true);
		charToCreate.setHearingDistance(15);
		charToCreate.setMovementSpeed(1);
		charToCreate.setSightDistance(20);
		charToCreate.setWeaponIndex(0);
		charToCreate.setWeaponType(new ArrayList<Integer>());
		charToCreate.setHunger(0);
		charToCreate.setThurst(0);
		charToCreate.setInventory(new HashMap<Integer,Object>());
		return charToCreate;
	}

	public static CharacterObject NewZombieCrawler(int i, int j) {
		Random rand = new Random();
		int  health = rand.nextInt(100) + 1;
		CharacterObject charToCreate = new CharacterObject(WORLDCONSTANTS.OBJ_TYPE_ZOMBIE,i,j,WORLDCONSTANTS.CHAR_ZOMBIE,CSIColor.YELLOW,true,true,health);
		charToCreate.setAlive(true);
		charToCreate.setAmmo(new ArrayList<Integer>());
		charToCreate.setArmourHealth(0);
		charToCreate.setAsciiChar('☻');
		charToCreate.setCanMove(true);
		charToCreate.setHearingDistance(5);
		charToCreate.setMovementSpeed(1);
		charToCreate.setSightDistance(10);
		charToCreate.setWeaponIndex(0);
		charToCreate.setWeaponType(new ArrayList<Integer>());
		return charToCreate;
	}

	public static CharacterObject NewZombieWalker(int i, int j) {
		Random rand = new Random();
		int  health = rand.nextInt(100) + 1;
		CharacterObject charToCreate = new CharacterObject(WORLDCONSTANTS.OBJ_TYPE_ZOMBIE,i,j,WORLDCONSTANTS.CHAR_ZOMBIE,CSIColor.YELLOW,true,true,health);
		charToCreate.setAlive(true);
		charToCreate.setAmmo(new ArrayList<Integer>());
		charToCreate.setArmourHealth(0);
		charToCreate.setAsciiChar('☻');
		charToCreate.setCanMove(true);
		charToCreate.setHearingDistance(5);
		charToCreate.setMovementSpeed(1);
		charToCreate.setSightDistance(10);
		charToCreate.setWeaponIndex(0);
		charToCreate.setWeaponType(new ArrayList<Integer>());
		return charToCreate;
	}

}
