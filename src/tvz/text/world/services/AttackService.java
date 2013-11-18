package tvz.text.world.services;

import tvz.text.world.objects.CharacterObject;
import tvz.text.world.objects.TextWorldObject;

public class AttackService {

	/**
	 * Check if the zombie is next to the mainChar and if it should attach.
	 * @param world
	 * @param mainChar
	 */
	public static void zombieAttack(TextWorldObject world, CharacterObject mainChar) {
		for(int i = 0; i<world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().size();i++)
			if(world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(i).getClass() == CharacterObject.class){
				if(i!=world.getWorldObjects().get(world.getMapxmapy()).getMainCharIndex()){
					CharacterObject zombie = (CharacterObject)world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(i);
					if(zombieNextToMainChar(zombie,mainChar)&&zombie.isAlive()){
						zombie.setCanMove(false);
						zombie.setShouldAttack(!zombie.getShouldAttack());
						if(zombie.getShouldAttack()){
							mainChar.takeDamage(zombie.getSelectedWeaponDamage());
						}
					}
				}
			}
	}

	/**
	 * Check if zombie is N,E,S,W from mainChar
	 * @param zombie
	 * @param mainChar
	 * @return
	 */
	private static boolean zombieNextToMainChar(CharacterObject zombie,
			CharacterObject mainChar) {

		return (zombie.getX()==mainChar.getX() && zombie.getY()==mainChar.getY()-1) ||
		       (zombie.getX()==mainChar.getX() && zombie.getY()==mainChar.getY()+1) ||
		       (zombie.getY()==mainChar.getY() && zombie.getX()==mainChar.getX()+1) ||
		       (zombie.getY()==mainChar.getY() && zombie.getX()==mainChar.getX()-1);
	}

}
