package tvz.text.world.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.slashie.libjcsi.CSIColor;

import tvz.text.world.objects.WORLDCONSTANTS.WeaponTypeEnum;
import tvz.text.world.services.MapService;
import tvz.text.world.services.ObjectService;

public class CharacterObject extends WorldObject{

	private static final long serialVersionUID = -4206808001354391373L;

	/**
	 * Default Constructor
	 * @param x
	 * @param y
	 * @param asciiChar
	 * @param objectColour
	 * @param blocking
	 * @param breakable
	 * @param health
	 */
	public CharacterObject(String objectName, int x, int y, char asciiChar, CSIColor objectColour,
			boolean blocking, boolean breakable, int health) {
		super(objectName, x, y, asciiChar, objectColour, blocking, breakable, health);
	}


	private boolean canMove;
	private boolean moveObject = false;
	private boolean movementAction;
	private int movementSpeed;
	private int armourType;
	private int armourHealth;
	private int weaponIndex;
	private int sightDistance;
	private int hearingDistance;
	private List<Integer> ammo;
	private List<Integer> weaponType;
	private WorldXY lastSeenLocation;
	private String name;
	private int hunger; private int hungerCounter;
	private int thirst; private int thirstCounter;
	private HashMap<Integer,Object> inventory;
	private int inventoryIndex;
	private boolean actionInventoryItem; //Do we need to action on the selected inventory item?
	private int actionDirection; //Direction WORLDCONSTANTS.directions to action the inventory item.
	private boolean actionCompleted;
	private boolean shouldAttack;


	/**
	 * @return the canMove
	 */
	public boolean isCanMove() {
		return canMove;
	}


	/**
	 * @param canMove the canMove to set
	 */
	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}


	/**
	 * @param moveObject the moveObject to set
	 */
	public void setMoveObject(boolean moveObject) {
		this.moveObject = moveObject;
	}


	/**
	 * @return the moveObject
	 */
	public boolean isMoveObject() {
		return moveObject;
	}


	/**
	 * @return the movementSpeed
	 */
	public int getMovementSpeed() {
		return movementSpeed;
	}


	/**
	 * @param movementSpeed the movementSpeed to set
	 */
	public void setMovementSpeed(int movementSpeed) {
		this.movementSpeed = movementSpeed;
	}


	/**
	 * @return the armourType
	 */
	public int getArmourType() {
		return armourType;
	}


	/**
	 * @param armourType the armourType to set
	 */
	public void setArmourType(int armourType) {
		this.armourType = armourType;
	}


	/**
	 * @param armourHealth the armourHealth to set
	 */
	public void setArmourHealth(int armourHealth) {
		this.armourHealth = armourHealth;
	}


	/**
	 * @return the armourHealth
	 */
	public int getArmourHealth() {
		return armourHealth;
	}


	/**
	 * @param weaponIndex the weaponIndex to set
	 */
	public void setWeaponIndex(int weaponIndex) {
		this.weaponIndex = weaponIndex;
	}


	/**
	 * @return the weaponIndex
	 */
	public int getWeaponIndex() {
		return weaponIndex;
	}


	/**
	 * @return the sightDistance
	 */
	public int getSightDistance() {
		return sightDistance;
	}


	/**
	 * @param sightDistance the sightDistance to set
	 */
	public void setSightDistance(int sightDistance) {
		this.sightDistance = sightDistance;
	}


	/**
	 * @return the hearingDistance
	 */
	public int getHearingDistance() {
		return hearingDistance;
	}


	/**
	 * @param hearingDistance the hearingDistance to set
	 */
	public void setHearingDistance(int hearingDistance) {
		this.hearingDistance = hearingDistance;
	}


	/**
	 * @param ammo the ammo to set
	 */
	public void setAmmo(List<Integer> ammo) {
		this.ammo = ammo;
	}


	/**
	 * @return the ammo
	 */
	public List<Integer> getAmmo() {
		if(ammo == null){
			ammo = new ArrayList<Integer>();
		}
		return ammo;
	}


	/**
	 * @param weaponType the weaponType to set
	 */
	public void setWeaponType(List<Integer> weaponType) {
		this.weaponType = weaponType;
	}


	/**
	 * @return the weaponType
	 */
	public List<Integer> getWeaponType() {
		return weaponType;
	}


	/**
	 * @param lastSeenLocation the lastSeenLocation to set
	 */
	public void setLastSeenLocation(WorldXY lastSeenLocation) {
		this.lastSeenLocation = lastSeenLocation;
	}


	/**
	 * @return the lastSeenLocation
	 */
	public WorldXY getLastSeenLocation() {
		if(lastSeenLocation == null) lastSeenLocation = new WorldXY();
		return lastSeenLocation;
	}


	public String getName(){
		return name;
	}

	public void setName(String name) {
		this.name = name;

	}


	/**
	 * @return the hunger
	 */
	public int getHunger() {
		return hunger;
	}


	/**
	 * @param hunger the hunger to set
	 */
	public void setHunger(int hunger) {
		this.hunger = hunger;
	}


	/**
	 * @return the thurst
	 */
	public int getThirst() {
		return thirst;
	}


	/**
	 * @param thurst the thurst to set
	 */
	public void setThurst(int thurst) {
		this.thirst = thurst;
	}


	/**
	 * @param inventory the inventory to set
	 */
	public void setInventory(HashMap<Integer,Object> inventory) {
		this.inventory = inventory;
	}


	/**
	 * @return the inventory
	 */
	public HashMap<Integer,Object> getInventory() {
//		if(inventory == null) return new HashMap<Integer,Object>();
		return inventory;
	}


	/**
	 * @param inventoryIndex the inventoryIndex to set
	 */
	public void setInventoryIndex(int inventoryIndex) {
		this.inventoryIndex = inventoryIndex;
	}


	/**
	 * @return the inventoryIndex
	 */
	public int getInventoryIndex() {
		return inventoryIndex;
	}


	/**
	 * @return the actionInventoryItem
	 */
	public boolean isActionInventoryItem() {
		return actionInventoryItem;
	}


	/**
	 * @param actionInventoryItem the actionInventoryItem to set
	 */
	public void setActionInventoryItem(boolean actionInventoryItem) {
		this.actionInventoryItem = actionInventoryItem;
	}


	/**
	 * @return the actionDirection
	 */
	public int getActionDirection() {
		return actionDirection;
	}


	/**
	 * @param actionDirection the actionDirection to set
	 */
	public void setActionDirection(int actionDirection) {
		this.actionDirection = actionDirection;
	}


	/**
	 * Check if the action performed was a movement action
	 * @param movementAction
	 */
	public void setMovementAction(boolean movementAction) {
		this.movementAction = movementAction;
	}

	/**
	 * Get the movement action.
	 * @return
	 */
	public boolean isMovementAction(){
		return movementAction;
	}


	//Executes the inventory item at the index that is not a weapon.
	public void actionInventoryNonWeapon() {
	//Check class and action
		if(inventory.get(inventoryIndex).getClass() == WaterObject.class){
			WaterObject obj = (WaterObject)inventory.get(inventoryIndex);
			this.setThurst(this.getThirst()- obj.getValue()); if(this.getThirst()<0)this.setThurst(0);
			this.setHealth(this.getHealth()+ obj.getHealingValue()); if(this.getHealth()>100)this.setHealth(100);
		} else
		if(inventory.get(inventoryIndex).getClass() == FoodObject.class){
			FoodObject obj = (FoodObject)inventory.get(inventoryIndex);
			this.setHunger(this.getHunger()- obj.getValue()); if(this.getHunger()<0)this.setHunger(0);
			this.setHealth(this.getHealth()+ obj.getHealingValue()); if(this.getHealth()>100)this.setHealth(100);
		} else
		if(inventory.get(inventoryIndex).getClass() == HealingObject.class){
			HealingObject obj = (HealingObject)inventory.get(inventoryIndex);
			this.setHealth(this.getHealth()+ obj.getHealingValue()); if(this.getHealth()>100)this.setHealth(100);
		}

	//Remove from inventory list
		if(ObjectService.isWaterFoodHealingObject(inventory.get(inventoryIndex)))
			inventory.remove(inventoryIndex);
	}


	/**
	 * After x amount of moves, the hunger and thirst will increase.
	 * If hunger and thirst is max (100) then health will decrease.
	 */
	public void checkHealth() {
		hungerCounter++;
		thirstCounter++;
		if(hungerCounter >=WORLDCONSTANTS.HUNGERDELAY){
			hungerCounter = 0;
			if(hunger<100)
				hunger++;
		}
		if(thirstCounter >=WORLDCONSTANTS.THIRSTDELAY){
			thirstCounter = 0;
			if(thirst<100)
				thirst++;
		}
		if(thirst == 100){
			this.setHealth(this.getHealth()-1);
		}
		if(hunger == 100){
			this.setHealth(this.getHealth()-1);
		}

	}


	/**
	 * selected weapon damage. WORLDCONSTANTS.DAMAGE_HAND if no weapon.
	 * @return
	 */
	public int getSelectedWeaponDamage() {
		int damageToReturn = WORLDCONSTANTS.DAMAGE_HAND;
		if(inventory != null && inventory.get(getInventoryIndex()) != null)
			if(inventory.get(getInventoryIndex()).getClass() == WeaponObject.class){
				WeaponObject obj = (WeaponObject)inventory.get(getInventoryIndex());
				damageToReturn = obj.getWeaponDamage();
			}
		return damageToReturn;
	}

	/**
	 * Check if character is holding a weapon
	 */
	public boolean isHoldingWeapon(){
		return (inventory != null && inventory.get(getInventoryIndex()) != null)&&(inventory.get(getInventoryIndex()).getClass() == WeaponObject.class);
	}

	/**
	 * Check if an inventory item is selected.
	 * @return
	 */
	public boolean isNoInventoryItemSelected(){
		return inventory == null || inventory.get(getInventoryIndex())==null;
	}


	/**
	 * Reduce health by selected weapon damage.
	 */
	public void takeDamage(int selectedWeaponDamage) {
		this.setHealth(this.getHealth()-selectedWeaponDamage);

	}


	/**
	 * Get the weapon distance.
	 * @return
	 */
	public int getSelectedWeaponDistance() {
		int distanceToReturn = 1;
		if(inventory != null && inventory.get(getInventoryIndex()) != null)
			if(inventory.get(getInventoryIndex()).getClass() == WeaponObject.class){
				WeaponObject obj = (WeaponObject)inventory.get(getInventoryIndex());
				distanceToReturn = obj.getWeaponDistance();
			}
		return distanceToReturn;
	}


	public void setActionCompleted(boolean b) {
		this.actionCompleted = b;
	}

	public boolean isActionCompleted(){
		return actionCompleted;
	}


	public void setShouldAttack(boolean b) {
		this.shouldAttack = b;

	}


	public boolean getShouldAttack() {
		return shouldAttack;
	}


	/**
	 * Get the name of the inventory item at index
	 * @param i
	 * @return
	 */
	public String getInventoryItemNameAt(int i) {
		if(inventory==null) return "free";
		if(inventory.get(i)==null) return "free";
		return ((WorldObject) inventory.get(i)).getObjectName();
	}


	/**
	 * Get the ammo amount of the inventory item at index i
	 * @param i
	 * @return
	 */
	public String getInventoryItemAmountAt(int i) {
		if(inventory==null) return "";
		if(inventory.get(i)==null) return "";
		if(inventory.get(i).getClass() == WeaponObject.class)
		  return String.valueOf(((WeaponObject) inventory.get(i)).getWeaponAmmo());
		if(inventory.get(i).getClass() == BuildingMaterialObject.class)
			  return String.valueOf(((BuildingMaterialObject) inventory.get(i)).getAmount());
		if(inventory.get(i).getClass() == FoodObject.class)
			 return String.valueOf(((FoodObject) inventory.get(i)).getValue());
		if(inventory.get(i).getClass() == WaterObject.class)
			 return String.valueOf(((WaterObject) inventory.get(i)).getValue());
		if(inventory.get(i).getClass() == HealingObject.class)
			 return String.valueOf(((HealingObject) inventory.get(i)).getHealingValue());
		return "";

	}


	/**
	 * Check if the mainChar has an empty inventory slot.
	 * @return true if a slot is null
	 */
	public boolean hasEmptyInventorySlot() {
		return inventory == null ||
			inventory.get(1) == null || inventory.get(2) == null || inventory.get(3) == null ||
			inventory.get(4) == null || inventory.get(5) == null || inventory.get(6) == null ||
			inventory.get(7) == null || inventory.get(8) == null || inventory.get(9) == null;
	}


	/**
	 * Get the first free inventory slot.
	 * @return
	 */
	public int getFreeInventorySlotIndex() {
		int i = 1;
		if(getInventory()==null) return 1;
		while(i<=9){
			if(!getInventory().containsKey(i)) return i;
			i++;
		}
		return 0;
	}


	/**
	 * Adds the obj to the mainChar's inventory. If it is a weapon, add ammo to existing amount.
	 * @param obj
	 */
	public boolean addObjectToInventory(Object obj) {
		//Add a weapon
		if(obj.getClass() == WeaponObject.class){
			WeaponObject weapon = new WeaponObject((WeaponObject)obj);
			int key = getExistingInventoryIndexOrFreeSlotIndex(obj);
			if((this.getInventory().get(key) != null) &&
			  ((WeaponObject)this.getInventory().get(key)).getWeaponType()==WeaponTypeEnum.HAND_WEAPON)
				return false;
			int amount = getInventoryIndexObjectAmount(key);
			if(amount>-1)
				weapon.setWeaponAmmo(weapon.getWeaponAmmo() + amount);
			this.getInventory().put(key, weapon);
			return true;
		}
		//Add a building material.
		if(obj.getClass() == BuildingMaterialObject.class){
			BuildingMaterialObject buildingMaterial = new BuildingMaterialObject((BuildingMaterialObject)obj);
			int key = getExistingInventoryIndexOrFreeSlotIndex(obj);
			if((this.getInventory().get(key) != null) && (this.getInventory().get(key).getClass() == WeaponObject.class) &&
					  ((WeaponObject)this.getInventory().get(key)).getWeaponType()==WeaponTypeEnum.HAND_WEAPON)
						return false;
			int amount = getInventoryIndexObjectAmount(key);
			if(amount>-1)
				buildingMaterial.setAmount(buildingMaterial.getAmount() + amount);
			this.getInventory().put(key, buildingMaterial);
			return true;
		}
		//Add a food, water and health.
		if(obj.getClass() == FoodObject.class || obj.getClass() == WaterObject.class || obj.getClass() == HealingObject.class){
			int key = getExistingInventoryIndexOrFreeSlotIndex(obj);
			if((this.getInventory().get(key) != null) && (this.getInventory().get(key).getClass() == WeaponObject.class) &&
					  ((WeaponObject)this.getInventory().get(key)).getWeaponType()==WeaponTypeEnum.HAND_WEAPON)
						return false;
			if(obj.getClass() == FoodObject.class) this.getInventory().put(key, new FoodObject((FoodObject)obj));
			if(obj.getClass() == WaterObject.class) this.getInventory().put(key, new WaterObject((WaterObject)obj));
			if(obj.getClass() == HealingObject.class) this.getInventory().put(key, new HealingObject((HealingObject)obj));
			return true;
		}
		return false;
	}

	/**
	 * Gets the inventory item's index number of the same type. If it isn't in the inventory,
	 * return a free slot index.
	 * @param obj
	 * @return
	 */
	private int getExistingInventoryIndexOrFreeSlotIndex(Object obj) {
		for(int i = 0; i <= 9; i++){
			if(this.getInventory().get(i)!= null){
				if(!isFoodWaterOrHealing(((WorldObject)this.getInventory().get(i))) && ((WorldObject)this.getInventory().get(i)).getObjectName().equalsIgnoreCase(((WorldObject)obj).getObjectName())){
					return i;
				}
			}
		}
		return this.getFreeInventorySlotIndex();
	}

	/**
	 * Check if the {@link WorldObject} is any of following
	 * {@link WORLDCONSTANTS.OBJ_FOOD_TYPE}
	 * {@link WORLDCONSTANTS.OBJ_WATER_TYPE}
	 * {@link WORLDCONSTANTS.OBJ_HEALTH_TYPE}
	 * @param worldObject
	 * @return
	 */
	private boolean isFoodWaterOrHealing(WorldObject worldObject) {
		return worldObject.getObjectName().equalsIgnoreCase(WORLDCONSTANTS.OBJ_TYPE_FOOD) ||
		worldObject.getObjectName().equalsIgnoreCase(WORLDCONSTANTS.OBJ_TYPE_WATER) ||
		worldObject.getObjectName().equalsIgnoreCase(WORLDCONSTANTS.OBJ_TYPE_HEALTH);
	}


	/**
	 * Get the ammo amount or building material amount at a specific index.
	 * @param index
	 * @return amount or -1 if it doesn't exist.
	 */
	private int getInventoryIndexObjectAmount(int index){
		if(this.getInventory().get(index)!=null){
			if(getInventory().get(index).getClass()==WeaponObject.class) return ((WeaponObject)getInventory().get(index)).getWeaponAmmo();
			if(getInventory().get(index).getClass()==BuildingMaterialObject.class) return ((BuildingMaterialObject)getInventory().get(index)).getAmount();
		}
		return -1;
	}

	/**
	 * Check if the held weapon has ammo > 0 or if the held weapon is a hand weapon
	 * @return true if ammo > 0 or if the held weapon is a hand weapon
	 */
	public boolean heldWeaponHasBullets() {
		return (inventory != null && inventory.get(getInventoryIndex()) != null)&&
		       (inventory.get(getInventoryIndex()).getClass() == WeaponObject.class) &&
		       ((((WeaponObject)inventory.get(getInventoryIndex())).getWeaponAmmo() > 0)||
		        (((WeaponObject)inventory.get(getInventoryIndex())).getWeaponType()==WeaponTypeEnum.HAND_WEAPON));
	}


	/**
	 * Decrease the ammo count with 1.
	 */
	public void decreaseWeaponAmmo() {
		if((((WeaponObject)inventory.get(getInventoryIndex())).getWeaponType()==WeaponTypeEnum.LONG_RANGE_WEAPON))
		  ((WeaponObject)inventory.get(getInventoryIndex())).setWeaponAmmo(((WeaponObject)inventory.get(getInventoryIndex())).getWeaponAmmo()-1);
	}


	/**
	 * Best effort to move zombie in direction of last seen location
	 */
	public boolean moveInDirectionOfLastSeenLocation() {

		int distanceToLastSeenLoc = Math.abs(this.getX() - this.lastSeenLocation.getX())+Math.abs(this.getY() - this.lastSeenLocation.getY());
			//If mainChar is diagonal, set the last seen location random between mainChar(x or y)
			for(int i = 0; i <= distanceToLastSeenLoc; i++){
				if((this.getX()+i == this.lastSeenLocation.getX())&& (this.getY()+i == this.lastSeenLocation.getY()) ||
				   (this.getX()+i == this.lastSeenLocation.getX())&& (this.getY()-i == this.lastSeenLocation.getY()) ||
				   (this.getX()-i == this.lastSeenLocation.getX())&& (this.getY()-i == this.lastSeenLocation.getY()) ||
				   (this.getX()-i == this.lastSeenLocation.getX())&& (this.getY()+i == this.lastSeenLocation.getY())){
					Random rand = new Random();
					int  amount = rand.nextInt(10)+1;
					if(amount <= 5) this.setLastSeenLocation(new WorldXY(this.lastSeenLocation.getX(),this.getY()));
					if(amount >= 6) this.setLastSeenLocation(new WorldXY(this.getX(),this.lastSeenLocation.getY()));
				}
			}

		//Check if mainChar is on Y axis
		if(this.getX()==this.getLastSeenLocation().getX()){
			this.setY(this.getY()>this.getLastSeenLocation().getY()?this.getY()-1:this.getY()+1);
			return true;
		}else
			//Check if mainChar is on X axis
		if(this.getY()==this.getLastSeenLocation().getY()){
			this.setX(this.getX()>this.getLastSeenLocation().getX()?this.getX()-1:this.getX()+1);
			return true;
		}




		return false;
	}


	public boolean isMainChar() {
		return this.getObjectName().equalsIgnoreCase(WORLDCONSTANTS.OBJ_TYPE_MAINCHAR);
	}

	/**
	 * Get the weapon type name.
	 * @return
	 */
	public String getSelectedWeaponTypeName() {
		String weaponType = WORLDCONSTANTS.OBJ_TYPE_HAND;
		if(inventory != null && inventory.get(getInventoryIndex()) != null){
			if(inventory.get(getInventoryIndex()).getClass() == WeaponObject.class){
				WeaponObject obj = (WeaponObject)inventory.get(getInventoryIndex());
				weaponType = obj.getObjectName();

			}
		}
		return weaponType;
	}


	public void actionInventoryWeaponDiscard(TextWorldObject world, MapObject map) {
		MapService.placeInventoryItem(this,world,map);
		this.setActionCompleted(true);

	}


}
