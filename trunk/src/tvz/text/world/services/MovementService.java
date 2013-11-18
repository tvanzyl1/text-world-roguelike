package tvz.text.world.services;

import java.util.HashMap;
import java.util.Random;

import tvz.text.world.objects.BulletObject;
import tvz.text.world.objects.CharacterObject;
import tvz.text.world.objects.FoodObject;
import tvz.text.world.objects.HealingObject;
import tvz.text.world.objects.MapObject;
import tvz.text.world.objects.TextWorldObject;
import tvz.text.world.objects.WORLDCONSTANTS;
import tvz.text.world.objects.WaterObject;
import tvz.text.world.objects.WeaponObject;
import tvz.text.world.objects.WorldObject;
import tvz.text.world.objects.WorldXY;

public class MovementService {

	/**
	 * Check if objects in the world can move to their next location.
	 * @param world
	 */
	public static void checkObjectMovement(TextWorldObject world) {
		//Check Characters movement.
		for(int i = 0; i < world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().size();i++){
			if(world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(i).getClass() == CharacterObject.class){
				CharacterObject objToCheck = (CharacterObject) world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(i);
				if(objToCheck.isCanMove()){
					objToCheck.setMoveObject(MapService.isPlaceable(objToCheck.getX(), objToCheck.getY(), world.getWorldObjects().get(world.getMapxmapy()), objToCheck));
				}
			}
		}
	}

	/**
	 * Move all objects in the world.
	 * If it is the mainChar we are moving. Then update the visibility.
	 * @param world
	 */
	public static void moveObjects(TextWorldObject world) {
		for(int i = 0; i < world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().size();i++){
			if(world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(i).getClass() == CharacterObject.class){
				CharacterObject objToCheck = (CharacterObject) world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(i);
				if(objToCheck.isCanMove() && objToCheck.isMoveObject()){
					objToCheck.setPrevX(objToCheck.getX());
					objToCheck.setPrevY(objToCheck.getY());
				} else
				if(objToCheck.isCanMove() && !objToCheck.isMoveObject()){
					objToCheck.setX(objToCheck.getPrevX());
					objToCheck.setY(objToCheck.getPrevY());
				}
				if(objToCheck.isMainChar()){
					MapService.updateVisibleArea(world, world.getWorldObjects().get(world.getMapxmapy()), objToCheck);
				}
			} else
			if(world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(i).getClass() == BulletObject.class){
				BulletObject objToCheck = (BulletObject) world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(i);
					objToCheck.setPrevX(objToCheck.getX());
					objToCheck.setPrevY(objToCheck.getY());
			}
		}
	}


	/**
	 * Main method to track main character's movement.
	 * @param world
	 */
	public static void checkMainCharMovement(TextWorldObject world) {
		//Check Characters movement.
		CharacterObject mainChar = (CharacterObject) world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(world.getWorldObjects().get(world.getMapxmapy()).getMainCharIndex());
		if(mainChar.isCanMove()){
			mainChar.setMoveObject(true);
			//Change map
			checkChangeMap(mainChar,world);
			//Main Character collision
			checkMainCharCollision(mainChar,world);
			//Check mainChar pickup item
			checkMainCharPickup(mainChar,world);
			//Reset action booleans
			mainChar.setActionInventoryItem(false);
			mainChar.setMovementAction(false);
		}
	}

	/**
	 * Checks if the mainChar is positioned on a health, water, food or weapon item.
	 * @param mainChar
	 * @param world
	 */
	private static void checkMainCharPickup(CharacterObject mainChar,
			TextWorldObject world) {
		MapObject map = world.getWorldObjects().get(world.getMapxmapy());
		int i = 0;
		boolean exitLoop = false;
		boolean hasFreeSlot = mainChar.hasEmptyInventorySlot();
		if(hasFreeSlot){
			while(i<map.getAreaObjectList().size() && !exitLoop && hasFreeSlot){
				if(i!=map.getMainCharIndex()){
					//Check weapons
					if(map.getAreaObjectList().get(i).getClass() == WeaponObject.class){
						WeaponObject obj = (WeaponObject)map.getAreaObjectList().get(i);
						if(mainChar.getX() == obj.getX() && mainChar.getY() == obj.getY() && obj.isAlive()){
							if(mainChar.addObjectToInventory(obj)){
								((WorldObject)map.getAreaObjectList().get(i)).destroyObject();
							}
							exitLoop = true;
						}
					}
					//Check food, water and healing objects
					if(map.getAreaObjectList().get(i).getClass() == FoodObject.class || map.getAreaObjectList().get(i).getClass() == WaterObject.class || map.getAreaObjectList().get(i).getClass() == HealingObject.class){
						WorldObject obj = (WorldObject)map.getAreaObjectList().get(i);
						if(mainChar.getX() == obj.getX() && mainChar.getY() == obj.getY() && obj.isAlive()){
							if(mainChar.addObjectToInventory(obj)){
								((WorldObject)map.getAreaObjectList().get(i)).destroyObject();
							}
							exitLoop = true;
						}
					}
				}
				i++;
			}
		}

	}

	/**
	 * Checks the collision of the mainCharacter.
	 * @param mainChar
	 * @param world
	 */
	private static void checkMainCharCollision(CharacterObject mainChar,
			TextWorldObject world) {
	  for(int j = 0; j < world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().size();j++){
			if(j!=world.getWorldObjects().get(world.getMapxmapy()).getMainCharIndex()){
				//Collides into other objects?
				WorldObject obj = (WorldObject) world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(j);
				if(obj.getX() == mainChar.getX() && obj.getY() == mainChar.getY() &&obj.isBlocking() && obj.isAlive()){
					mainChar.setMoveObject(false);
				}
			}
		}
	}

	/**
	 * Check if the main character movement changes the map.
	 */
	private static void checkChangeMap(CharacterObject mainChar, TextWorldObject world) {
		if(mainChar.getX()<0){world.setPrevMapKey(world.getMapxmapy());world.setMapx(world.getMapx()-1); mainChar.setX(WORLDCONSTANTS.SCREENMAXX-1);} else
		if(mainChar.getX()>=WORLDCONSTANTS.SCREENMAXX){world.setPrevMapKey(world.getMapxmapy());world.setMapx(world.getMapx()+1); mainChar.setX(0);} else
		if(mainChar.getY()<0){world.setPrevMapKey(world.getMapxmapy());world.setMapy(world.getMapy()-1); mainChar.setY(WORLDCONSTANTS.SCREENMAXY-1);} else
		if(mainChar.getY()>=WORLDCONSTANTS.SCREENMAXY){world.setPrevMapKey(world.getMapxmapy());world.setMapy(world.getMapy()+1); mainChar.setY(0);}
	}

	/**
	 * Test what the zombies will do next.
	 * @param world
	 */
	public static void checkZombiesMovement(TextWorldObject world) {
		//Check Characters movement.
		for(int i = 0; i < world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().size();i++){
			if(i != world.getWorldObjects().get(world.getMapxmapy()).getMainCharIndex() && world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(i).getClass() == CharacterObject.class){
				CharacterObject zombie = (CharacterObject) world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(i);
				if(zombie.isCanMove() && zombie.isAlive()){
					zombie.setMoveObject(true);
					if(!calculateZombieMovement(zombie,world)){
						//random movement
						moveRandomDirection(zombie);
					}
					//Check moving off screen
					stopMovingOffScreen(zombie);
					//Walk into wall...
					stopMovingIntoObject(zombie,world,i);

				} else {
					zombie.setCanMove(true);
				}
			}
		}
	}


	/**
	 * Calculates a zombie's next move.
	 * @param zombie
	 * @param world
	 * @return
	 */
	private static boolean calculateZombieMovement(CharacterObject zombie,	TextWorldObject world) {
		WorldObject mainChar = (WorldObject) world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(world.getWorldObjects().get(world.getMapxmapy()).getMainCharIndex());
		int distanceToMainChar = Math.abs(zombie.getX() - mainChar.getX())+Math.abs(zombie.getY() - mainChar.getY());

		if(zombie.getSightDistance() >= distanceToMainChar){
			//If mainChar is diagonal, set the last seen location random between mainChar(x or y)
			for(int i = 0; i <= distanceToMainChar; i++){
				if((zombie.getX()+i == mainChar.getX())&& (zombie.getY()+i == mainChar.getY()) ||
				   (zombie.getX()+i == mainChar.getX())&& (zombie.getY()-i == mainChar.getY()) ||
				   (zombie.getX()-i == mainChar.getX())&& (zombie.getY()-i == mainChar.getY()) ||
				   (zombie.getX()-i == mainChar.getX())&& (zombie.getY()+i == mainChar.getY())){
					Random rand = new Random();
					int  amount = rand.nextInt(10)+1;
					if(amount <= 5) zombie.setLastSeenLocation(new WorldXY(mainChar.getX(),zombie.getY()));
					if(amount >= 6) zombie.setLastSeenLocation(new WorldXY(zombie.getX(),mainChar.getY()));
				}
			}
			//If on Axis, set last seen position
			if((zombie.getX() == mainChar.getX()) || (zombie.getY() == mainChar.getY())){
				zombie.setLastSeenLocation(new WorldXY(mainChar.getX(),mainChar.getY()));
			}
		}

		if(zombie.getLastSeenLocation().getX() > 0 && zombie.getLastSeenLocation().getY() > 0){
			return zombie.moveInDirectionOfLastSeenLocation();
		}
		zombie.setLastSeenLocation(new WorldXY());
		return false;
	}

	/**
	 * Stops Zombie from walking into blocking objects
	 * @param zombie
	 * @param world
	 * @param objIndex
	 */
	private static void stopMovingIntoObject(CharacterObject zombie, TextWorldObject world, int objIndex) {
		if(zombie.isMoveObject() && zombie.isAlive()){
					if(!MapService.isPlaceable(zombie.getX(), zombie.getY(), world.getWorldObjects().get(world.getMapxmapy()),zombie)) zombie.setMoveObject(false);
		}
	}

	/**
	 * Prevents zombie from walking off screen.
	 * @param zombie
	 */
	private static void stopMovingOffScreen(CharacterObject zombie) {
		if(zombie.getX()<=0){zombie.setMoveObject(false);}
		if(zombie.getX()>=WORLDCONSTANTS.SCREENMAXX){zombie.setMoveObject(false);}
		if(zombie.getY()<=0){zombie.setMoveObject(false);}
		if(zombie.getY()>=WORLDCONSTANTS.SCREENMAXY){zombie.setMoveObject(false);}
	}

	/**
	 * If nothing to do, move in random direction.
	 * @param zombie
	 */
	private static void moveRandomDirection(CharacterObject zombie) {
		Random rand = new Random();
		int  n = rand.nextInt(4) + 1;
		if(n==1){
			zombie.setX(zombie.getX()-1);
		}
		else if(n==2 || n==3){
			zombie.setX(zombie.getX());
			int m = rand.nextInt(4) + 1;
			if(m==1){
				zombie.setY(zombie.getY()-1);
			}else if(m==2 || m==3){
				zombie.setY(zombie.getY());
			}else{
				zombie.setY(zombie.getY()+1);
			}
		}else{
			zombie.setX(zombie.getX()+1);
		}

	}

	/**
	 * Executes the action required. This may include shooting, eating, drinking, discarding of inventory items.
	 * @param mainChar
	 * @param world
	 */
	public static void executeMainCharAction(CharacterObject mainChar, TextWorldObject world) {
		mainChar.setActionCompleted(true);
		MapObject map = world.getWorldObjects().get(world.getMapxmapy());
		if(mainChar.getInventory() == null) mainChar.setInventory(new HashMap<Integer,Object>());
		//NO_DIRECTION :EATING, DRINKING, HEALING, Discard weapon
		if(mainChar.getActionDirection() == WORLDCONSTANTS.NO_DIRECTION){
			if(mainChar.getInventory().get(mainChar.getInventoryIndex()) != null){
				mainChar.actionInventoryNonWeapon();
				mainChar.actionInventoryWeaponDiscard(world,map);
			}
		}else{
			//DIRECTION - WEAPON : Shoots in direction.
			//If no inventory item...punch.
			if(mainChar.isNoInventoryItemSelected()){
				WorldXY xy = BulletService.placeBullet(mainChar,world);
				if(MapService.inScreenRange(xy.getX(),xy.getY()) && xy.getX() > 0 && xy.getY() > 0)
					mainChar.setActionCompleted(false);
				else
					mainChar.setActionCompleted(true);
			} else
			//If it is a weapon, shoot or hit in direction
			if(mainChar.isHoldingWeapon() && mainChar.heldWeaponHasBullets()){
				mainChar.decreaseWeaponAmmo();
				WorldXY xy = BulletService.placeBullet(mainChar,world);
				if(MapService.inScreenRange(xy.getX(),xy.getY()) && xy.getX() > 0 && xy.getY() > 0)
					mainChar.setActionCompleted(false);
				else
					mainChar.setActionCompleted(true);
			} else
			//DIRECTION - NON-WEAPON : Discard
			if(mainChar.getInventory().get(mainChar.getInventoryIndex()).getClass() != WeaponObject.class){
				MapService.placeInventoryItem(mainChar,world,map);
				mainChar.setActionCompleted(true);
			}
		}
		//Reset action booleans.
		mainChar.setActionInventoryItem(false);
		mainChar.setMovementAction(false);

	}


	/**
	 * Check bullet collision and execute accordingly
	 * @param world
	 * @param mainChar
	 */
	public static void checkBulletOrPlacedObjectCollision(CharacterObject mainChar, TextWorldObject world) {
		int bulletDistance = 1;
		int i = world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().size()-1;

		while(i >=0 ){
			if(world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(i).getClass() == BulletObject.class){
				BulletObject obj = (BulletObject)world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(i);
				//Calculate bullet distance
				bulletDistance = Math.abs(mainChar.getX()-obj.getX()) + Math.abs(mainChar.getY()-obj.getY());
				int j = world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().size()-1;
				boolean objNotDestroyed = true;

				while(j>=0 && objNotDestroyed){
					//Check bullet out of range.
					if((bulletDistance > obj.getDistance())|| !MapService.inScreenRange(obj.getX(),obj.getY())){
						objNotDestroyed = false;
						world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().remove(i);
						mainChar.setActionCompleted(true);
					}
					//check the bullet collision with other objects. if no collision, move on. If there is. act on damage and set the mainChar.isActionCompleted.
					if(i!=j){
						WorldObject objToCheck = (WorldObject)world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(j);

						if(objToCheck.getX() == obj.getX() && obj.getY() == objToCheck.getY() && objToCheck.isBlocking()){ //Collision occurred.
							bulletOrCollisionActOnZombie(objToCheck,obj);
							bulletOrCollisionAxeOnTree(objToCheck,obj,mainChar);
							bulletOrCollisionSledgeOnWall(objToCheck, obj, mainChar);
							bulletOrCollisionHandOnBuildingMaterial(objToCheck,obj,mainChar); //Not that kind of wood. Also the bullet here is a hand that will pick up.
							objNotDestroyed = false;
							world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().remove(i);
							mainChar.setActionCompleted(true);
						}
					}
					j--;
				}
				moveObject(obj, mainChar.getActionDirection());
			}
			i--;
		}


	}


	/**
	 * Checks if the objToCheck is of type {@link BuildingMaterialObject} if it is, place in inventory.
	 * @param objToCheck
	 * @param obj
	 * @param mainChar
	 */
	private static void bulletOrCollisionHandOnBuildingMaterial(WorldObject objToCheck,
			BulletObject obj, CharacterObject mainChar) {
//		if(objToCheck.getObjectName().equalsIgnoreCase(WORLDCONSTANTS.OBJ_TYPE_WOOD) &&
		if(ObjectService.isBuildingMaterial(objToCheck) &&
				mainChar.getSelectedWeaponTypeName().equalsIgnoreCase(WORLDCONSTANTS.OBJ_TYPE_HAND))
		{
			//place new one in inventory or stack if already exists.
			mainChar.addObjectToInventory(objToCheck);
			//kill the pile on ground.
			objToCheck.setHealth(-1);
		}
	}

	/**
	 * Check if the mainCharacter is chopping a tree
	 * @param objToCheck
	 * @param obj
	 * @param mainChar
	 */
	private static void bulletOrCollisionAxeOnTree(WorldObject objToCheck,
			BulletObject obj, CharacterObject mainChar) {
		if(objToCheck.getObjectName().equalsIgnoreCase(WORLDCONSTANTS.OBJ_TYPE_TREE) &&
				mainChar.getSelectedWeaponTypeName().equalsIgnoreCase(WORLDCONSTANTS.OBJ_TYPE_AXE))
			objToCheck.setHealth(objToCheck.getHealth()-obj.getDamage());

	}


	/**
	 * Check if the mainCharacter is breaking down a wall
	 * @param objToCheck
	 * @param obj
	 * @param mainChar
	 */
	private static void bulletOrCollisionSledgeOnWall(WorldObject objToCheck,
			BulletObject obj, CharacterObject mainChar) {
		if(ObjectService.isBuildingMaterial(objToCheck) && ObjectService.isWall(objToCheck) &&
				mainChar.getSelectedWeaponTypeName().equalsIgnoreCase(WORLDCONSTANTS.OBJ_TYPE_SLEDGE))
			objToCheck.setHealth(objToCheck.getHealth()-obj.getDamage());
	}

	/**
	 * Check if the object collision is with a zombie. If it is. decrease the object health.
	 * @param objToCheck
	 * @param obj
	 */
	private static void bulletOrCollisionActOnZombie(WorldObject objToCheck,
			BulletObject obj) {
		if(objToCheck.getObjectName().equalsIgnoreCase(WORLDCONSTANTS.OBJ_TYPE_ZOMBIE))
			objToCheck.setHealth(objToCheck.getHealth()-obj.getDamage());
	}

	/**
	 * Move an object in a direction.
	 * @param obj
	 * @param actionDirection
	 */
	private static void moveObject(BulletObject obj, int actionDirection) {
		switch(actionDirection){
		case WORLDCONSTANTS.NORTH : obj.setPrevY(obj.getY());obj.setY(obj.getY()-1); break;
		case WORLDCONSTANTS.EAST  : obj.setPrevX(obj.getX());obj.setX(obj.getX()+1); break;
		case WORLDCONSTANTS.SOUTH : obj.setPrevY(obj.getY());obj.setY(obj.getY()+1); break;
		case WORLDCONSTANTS.WEST  : obj.setPrevX(obj.getX());obj.setX(obj.getX()-1); break;
	}

	}

}
