package tvz.text.world.services;

import java.util.List;
import java.util.Random;

import net.slashie.libjcsi.CSIColor;
import tvz.text.world.objects.BuildingMaterialObject;
import tvz.text.world.objects.CharacterObject;
import tvz.text.world.objects.ConfigObject;
import tvz.text.world.objects.FoodObject;
import tvz.text.world.objects.HealingObject;
import tvz.text.world.objects.MapObject;
import tvz.text.world.objects.TextWorldObject;
import tvz.text.world.objects.WORLDCONSTANTS;
import tvz.text.world.objects.WORLDCONSTANTS.WeaponTypeEnum;
import tvz.text.world.objects.WaterObject;
import tvz.text.world.objects.WeaponObject;
import tvz.text.world.objects.WorldObject;
import tvz.text.world.objects.WorldXY;

/**
 * The MapService provides various services. Includes:
 * Create world objects.
 * Get a random colour between two colours.
 * @author tertiusv
 *
 */
public class MapService {

	static String  eol = System.getProperty("line.separator");


	public MapService(){

	}

	/**
	 * Returns a new map service
	 * @return {@link MapService}
	 */
	public static MapService Initialise(){
		return new MapService();

	}

	/**
	 * Produce a random colour between two specified
	 * @param color1
	 * @param color2
	 * @return {@link CSIColor}
	 */
	private static CSIColor randomColor(CSIColor color1, CSIColor color2){
		Random rand = new Random();
		int  n = rand.nextInt(2) + 1;
		if(n==1)
			return color1;
		else
			return color2;
	}


	/**
	 * Create a tree at location
	 * @param x
	 * @param y
	 * @return {@link WorldObject}
	 */
	public static WorldObject Tree(int x, int y){
		return new WorldObject(WORLDCONSTANTS.OBJ_TYPE_TREE,x,y,WORLDCONSTANTS.CHAR_TREE.charAt(0),randomColor(CSIColor.BROWN,CSIColor.GREEN),true,true,WORLDCONSTANTS.OBJ_HEALTH_TREE);
	}

	/**
	 * Create grass
	 * @param x
	 * @param y
	 * @return {@link WorldObject}
	 */
	public static WorldObject Grass(int x, int y){
		Random rand = new Random();
		int  n = rand.nextInt(WORLDCONSTANTS.CHAR_GRASS.length);
		return new WorldObject("grass",x,y,WORLDCONSTANTS.CHAR_GRASS[n].charAt(0),randomColor(CSIColor.BROWN,CSIColor.GREEN),false,false,WORLDCONSTANTS.MAXHEALTH);
	}

	/**
	 * Create a mountain
	 * @param x
	 * @param y
	 * @return {@link WorldObject}
	 */
	public static WorldObject Mountain(int x, int y){
		return new WorldObject("mountain",x,y,'▲',CSIColor.GRAY,true,false,1000000);
	}


	public static WorldObject createRiver(int x, int y) {
		return new WorldObject(WORLDCONSTANTS.OBJ_TYPE_RIVER,x,y,'~',randomColor(CSIColor.BABY_BLUE,CSIColor.LIGHT_BLUE),true,false,WORLDCONSTANTS.MAXHEALTH);
	}


	public static WorldObject createRoad(int x, int y) {
		return new WorldObject("road",x,y,WORLDCONSTANTS.CHAR_ROAD.charAt(0),randomColor(CSIColor.BROWN,CSIColor.LIGHT_KHAKI),false,false,WORLDCONSTANTS.MAXHEALTH);
	}


	public static WorldObject BuildingWall(int x, int y){
		return new WorldObject(WORLDCONSTANTS.OBJ_TYPE_WALL,x,y,WORLDCONSTANTS.CHAR_BUILDING_WALL.charAt(0),randomColor(CSIColor.BROWN,CSIColor.BROWN),true,false,WORLDCONSTANTS.OBJ_HEALTH_WALL);
	}


	public static WorldObject BuildingFloor(int x, int y){
		return new WorldObject("floor",x,y,'▓',randomColor(CSIColor.BEIGE,CSIColor.BEIGE),false,false,WORLDCONSTANTS.MAXHEALTH);
	}

	/**
	 * Create a wood pile from a crushed tree.
	 * @param x
	 * @param y
	 * @return {@link BuildingMaterialObject} at x,y
	 */
	public static BuildingMaterialObject WoodPile(int x, int y){
		return new BuildingMaterialObject(WORLDCONSTANTS.OBJ_TYPE_WOOD,x,y,'▓',randomColor(CSIColor.BROWN,CSIColor.BROWN),true,false,WORLDCONSTANTS.MAXHEALTH);
	}


	/**
	 * Creates a stone pile from a crushed wall.
	 * @param x
	 * @param y
	 * @return {@link BuildingMaterialObject} at x,y
	 */
	public static BuildingMaterialObject StonePile(int x, int y){
		return new BuildingMaterialObject(WORLDCONSTANTS.OBJ_TYPE_STONE,x,y,WORLDCONSTANTS.CHAR_STONE.charAt(0),randomColor(CSIColor.WHITE,CSIColor.BEIGE),true,false,WORLDCONSTANTS.MAXHEALTH);
	}


	/**
	 * Create a tombstone.
	 * See {@link WORLDCONSTANTS.TOMBSTONE_CHAR}
	 * @param x
	 * @param y
	 * @return
	 */
	public static WorldObject TombStone(int x, int y) {
		Random rand = new Random();
		int  n = rand.nextInt(WORLDCONSTANTS.CHAR_TOMBSTONE.length);
		return new WorldObject("tombstone",x,y,WORLDCONSTANTS.CHAR_TOMBSTONE[n].charAt(0),randomColor(CSIColor.GRAY,CSIColor.BEIGE),true,false,WORLDCONSTANTS.MAXHEALTH);
	}


	/**
	 * Go through map area objects and check each object's health.
	 * If the health is < 0. Destroy object
	 * @param mapObject
	 * @param world
	 */
	public static void checkWorldObjectHealth(TextWorldObject world, MapObject mapObject) {
		//Check health.
		for(int i = 0; i <mapObject.getAreaObjectList().size();i++){
			if(i != mapObject.getMainCharIndex()){
				WorldObject obj = (WorldObject)mapObject.getAreaObjectList().get(i);
				if(obj.getHealth() < 0 && obj.isAlive()){
					ifZombieDestroy(obj);
					ifTreeReplaceWithWood(mapObject.getAreaObjectList(), obj);
					ifWallReplaceWithStone(mapObject.getAreaObjectList(), obj);
					ifBuildingMaterialPickupRemove(mapObject.getAreaObjectList(),obj);
				}
				//Respawn dead object.
				if(!obj.isAlive()){
					if(obj.getDateDead().getTime() - System.currentTimeMillis() > WORLDCONSTANTS.RESPAWNTIME){
						obj.respawnObject();
					}
				}
			}
		}
	}


	/**
	 * Check if obj is {@link BuildingMaterialObject} and remove.
	 * @param areaObjectList
	 * @param obj
	 */
	private static void ifBuildingMaterialPickupRemove(
			List<Object> areaObjectList, WorldObject obj) {
		if(obj.getClass()==BuildingMaterialObject.class)
			areaObjectList.remove(obj);

	}

	/**
	 * Check if the obj is a tree. Remove from map and add pile of wood to map.
	 * Trees will not respawn.
	 * @param obj
	 */
	private static void ifTreeReplaceWithWood(List<Object> list, WorldObject obj) {
		if(obj.getObjectName().equalsIgnoreCase(WORLDCONSTANTS.OBJ_TYPE_TREE)){
			int x = obj.getX();
			int y = obj.getY();
			list.add(WoodPile(x, y));
			list.remove(obj);
		}
	}


	/**
	 * Check if the obj is a tree. Remove from map and add pile of wood to map.
	 * Trees will not respawn.
	 * @param obj
	 */
	private static void ifWallReplaceWithStone(List<Object> list, WorldObject obj) {
		if(ObjectService.isWall(obj)){
			int x = obj.getX();
			int y = obj.getY();
			list.add(StonePile(x, y));
			list.remove(obj);
		}
	}

	/**
	 * Check if the obj is a zombie, then destroy.
	 * @param list
	 * @param obj
	 */
	private static void ifZombieDestroy(WorldObject obj) {
		if(obj.getObjectName().equalsIgnoreCase(WORLDCONSTANTS.OBJ_TYPE_ZOMBIE))
			obj.destroyObject();
	}

	public static WeaponObject CreateHand(int x, int y){
		return new WeaponObject("hand",x,y,'-',CSIColor.WHITE,5,5,1,WeaponTypeEnum.HAND_WEAPON,true);
	}


	public static WeaponObject CreateRevolver(int x, int y){
		return new WeaponObject("hgun",x,y,WORLDCONSTANTS.CHAR_HAND_GUN.charAt(0),CSIColor.WHITE,50,1,40,WeaponTypeEnum.LONG_RANGE_WEAPON,false);
	}


	public static WeaponObject CreateKnife(int x, int y) {
		return new WeaponObject("knfe",x,y,WORLDCONSTANTS.CHAR_KNIFE.charAt(0),CSIColor.WHITE,90,1,1,WeaponTypeEnum.HAND_WEAPON,true);
	}


	public static WeaponObject CreateRifle(int x, int y) {
		return new WeaponObject("rifl",x,y,WORLDCONSTANTS.CHAR_SHOTGUN.charAt(0),CSIColor.WHITE,70,1,70,WeaponTypeEnum.LONG_RANGE_WEAPON,false);
	}


	public static WeaponObject CreateBow(int x, int y) {
		return new WeaponObject("bow",x,y,WORLDCONSTANTS.CHAR_BOW.charAt(0),CSIColor.WHITE,80,1,60,WeaponTypeEnum.LONG_RANGE_WEAPON,true);
	}

	/**
	 * Creates an axe at x,y
	 * @param x
	 * @param y
	 * @return {@link WeaponObject}
	 */
	public static WeaponObject CreateAxe(int x, int y) {
		return new WeaponObject(WORLDCONSTANTS.OBJ_TYPE_AXE,x,y,WORLDCONSTANTS.CHAR_AXE.charAt(0),CSIColor.WHITE,100,1,1,WeaponTypeEnum.HAND_WEAPON,true);
	}


	/**
	 * Creates a sledge hammer at x,y
	 * @param x
	 * @param y
	 * @return {@link WeaponObject}
	 */
	public static WeaponObject CreateSledge(int x, int y) {
		return new WeaponObject(WORLDCONSTANTS.OBJ_TYPE_SLEDGE,x,y,WORLDCONSTANTS.CHAR_SLEDGE.charAt(0),CSIColor.WHITE,100,1,1,WeaponTypeEnum.HAND_WEAPON,true);
	}

	/**
	 * Creates {@link FoodObject}
	 * @param x
	 * @param y
	 * @return
	 */
	public static FoodObject CreateFood(int x, int y) {
		Random rand = new Random();
		int  n = rand.nextInt(WORLDCONSTANTS.FOOD_CHAR.length);
		return new FoodObject(WORLDCONSTANTS.OBJ_TYPE_FOOD,x,y,WORLDCONSTANTS.FOOD_CHAR[n].charAt(0),CSIColor.WHITE,false,false,40);
	}

	/**
	 * Creates a {@link FoodObject} with a specific food value.
	 * @param x
	 * @param y
	 * @param foodValue
	 * @return
	 */
	public static FoodObject CreateFood(int x, int y, int foodValue) {
		FoodObject obj = CreateFood(x,y);
		obj.setValue(foodValue);
		return obj;
	}

	/**
	 * Creates {@link WaterObject}
	 * @param x
	 * @param y
	 * @return
	 */
	public static WaterObject CreateWater(int x, int y) {
		return new WaterObject(WORLDCONSTANTS.OBJ_TYPE_WATER,x,y,WORLDCONSTANTS.WATER_CHAR.charAt(0),CSIColor.WHITE,false,false,40);
	}

	/**
	 * Creates a {@link WaterObject} with a specific food value.
	 * @param x
	 * @param y
	 * @param waterValue
	 * @return
	 */
	public static WaterObject CreateWater(int x, int y, int waterValue) {
		WaterObject obj = CreateWater(x,y);
		obj.setValue(waterValue);
		return obj;
	}

	/**
	 * Creates {@link HealingObject}
	 * @param x
	 * @param y
	 * @return
	 */
	public static HealingObject CreateHealth(int x, int y){
		return new HealingObject(WORLDCONSTANTS.OBJ_TYPE_HEALTH,x,y,WORLDCONSTANTS.HEALTH_CHAR.charAt(0),CSIColor.WHITE,false,false,40);
	}

	/**
	 * Creates a {@link HealingObject} with a specific health value.
	 * @param x
	 * @param y
	 * @param waterValue
	 * @return
	 */
	public static HealingObject CreateHealth(int x, int y, int healingValue) {
		HealingObject obj = CreateHealth(x,y);
		obj.setHealingValue(healingValue);
		return obj;
	}

	/**
	 * uses the location of the objToCheck and change what the player can see onscreen.
	 * See {@link ConfigObject.ViewTypeEnum}
	 * @param world
	 * @param map
	 * @param objToCheck
	 */
	public static void updateVisibleArea(TextWorldObject world, MapObject map, CharacterObject objToCheck) {

	if(world.getConfig().getViewType() == ConfigObject.ViewTypeEnum.LIMITED_VIEW)
		map.setVisibleArea(GenerationService.emptyMapInt());

		int x;
		int y;
		boolean breakx=false;
		boolean breaky=false;
		//Check vertical line
		y=objToCheck.getY(); breaky = false;
		while(y < objToCheck.getY() + objToCheck.getSightDistance() &&!breaky){
			y++;
			if(inScreenRange(objToCheck.getX(),y)){
				if(map.getVisibleArea()[objToCheck.getX()][y]==0) map.getVisibleArea()[objToCheck.getX()][y]=1;
				if(inScreenRange(objToCheck.getX()-1,y) && map.getVisibleArea()[objToCheck.getX()-1][y]==0) map.getVisibleArea()[objToCheck.getX()-1][y]=1;
				if(inScreenRange(objToCheck.getX()+1,y) && map.getVisibleArea()[objToCheck.getX()+1][y]==0) map.getVisibleArea()[objToCheck.getX()+1][y]=1;
				WorldObject wobj = getWorldObjectAtXY(map,objToCheck.getX(),y);
				if(wobj != null && ObjectService.isVisionBlocking(wobj)){
					breaky=true;
				}
			}
		}
		y=objToCheck.getY(); breaky = false;
		while(y > objToCheck.getY() - objToCheck.getSightDistance() &&!breaky){
			y--;
			if(inScreenRange(objToCheck.getX(),y)){
				if(map.getVisibleArea()[objToCheck.getX()][y]==0) map.getVisibleArea()[objToCheck.getX()][y]=1;
				if(inScreenRange(objToCheck.getX()-1,y) && map.getVisibleArea()[objToCheck.getX()-1][y]==0) map.getVisibleArea()[objToCheck.getX()-1][y]=1;
				if(inScreenRange(objToCheck.getX()+1,y) && map.getVisibleArea()[objToCheck.getX()+1][y]==0) map.getVisibleArea()[objToCheck.getX()+1][y]=1;
				WorldObject wobj = getWorldObjectAtXY(map,objToCheck.getX(),y);
				if((wobj != null) && ObjectService.isVisionBlocking(wobj)){
					breaky=true;
				}
			}
		}
		//Check horizontal line.
		x = objToCheck.getX(); breakx=false;
		while(x < objToCheck.getX() + objToCheck.getSightDistance()&&!breakx){
			x++;
			if(x>=0 && x<=WORLDCONSTANTS.SCREENMAXX){
				if(map.getVisibleArea()[x][objToCheck.getY()]==0) map.getVisibleArea()[x][objToCheck.getY()]=1;
				if(inScreenRange(x,objToCheck.getY()-1)&&map.getVisibleArea()[x][objToCheck.getY()-1]==0) map.getVisibleArea()[x][objToCheck.getY()-1]=1;
				if(inScreenRange(x,objToCheck.getY()+1)&&map.getVisibleArea()[x][objToCheck.getY()+1]==0) map.getVisibleArea()[x][objToCheck.getY()+1]=1;
				WorldObject wobj = getWorldObjectAtXY(map,x,objToCheck.getY());
				if((wobj != null) && ObjectService.isVisionBlocking(wobj)){
					breakx=true;
				}
			}
		}
		x = objToCheck.getX(); breakx=false;
		while(x > objToCheck.getX() - objToCheck.getSightDistance()&&!breakx){
			x--;
			if(x>=0 && x<=WORLDCONSTANTS.SCREENMAXX){
				if(map.getVisibleArea()[x][objToCheck.getY()]==0) map.getVisibleArea()[x][objToCheck.getY()]=1;
				if(inScreenRange(x,objToCheck.getY()-1)&&map.getVisibleArea()[x][objToCheck.getY()-1]==0) map.getVisibleArea()[x][objToCheck.getY()-1]=1;
				if(inScreenRange(x,objToCheck.getY()+1)&&map.getVisibleArea()[x][objToCheck.getY()+1]==0) map.getVisibleArea()[x][objToCheck.getY()+1]=1;
				WorldObject wobj = getWorldObjectAtXY(map,x,objToCheck.getY());
				if((wobj != null) && ObjectService.isVisionBlocking(wobj)){
					breakx=true;
				}
			}
		}
		//Check NE Quadrant
		x = objToCheck.getX(); breakx=false;
		while(x < objToCheck.getX() + objToCheck.getSightDistance() && !breakx){
			y = objToCheck.getY(); breaky = false;
			while(y > objToCheck.getY() - objToCheck.getSightDistance() && !breaky){
				if(inScreenRange(x,y) && inScreenRange(x,y-1) && inScreenRange(x+1,y)){
					WorldObject wobj = getWorldObjectAtXY(map,x,y);
					if((wobj != null) && inScreenRange(x,y) && (map.getVisibleArea()[x][y]==0)){
						map.getVisibleArea()[x][y]=1;
					}
					WorldObject wobj2 = getWorldObjectAtXY(map,x,y-1);
					if((wobj2 != null) && inScreenRange(x,y-1) && (map.getVisibleArea()[x][y-1]==0) ){
						map.getVisibleArea()[x][y-1]=1;
					}
					WorldObject wobj3 = getWorldObjectAtXY(map,x+1,y);
					if((wobj3 != null) && inScreenRange(x+1,y) && (map.getVisibleArea()[x+1][y]==0)){
						map.getVisibleArea()[x+1][y]=1;
					}
					if((wobj != null) && (ObjectService.isVisionBlocking(wobj)))breaky=true;
					if((wobj2 != null) && (ObjectService.isVisionBlocking(wobj2))) breaky=true;
					if((wobj3 != null) && (ObjectService.isVisionBlocking(wobj3))) breakx=true;
				}
				y--;
			}
			x++;
		}
		//Check NW Quadrant
		x = objToCheck.getX(); breakx=false;
		while(x >= objToCheck.getX()- objToCheck.getSightDistance() && !breakx){
			y = objToCheck.getY(); breaky = false;
			while(y > objToCheck.getY() - objToCheck.getSightDistance() && !breaky){
				if(inScreenRange(x,y) && inScreenRange(x,y-1) && inScreenRange(x-1,y)){
					WorldObject wobj = getWorldObjectAtXY(map,x,y);
					if((wobj != null) && inScreenRange(x,y) && (map.getVisibleArea()[x][y]==0)){
						map.getVisibleArea()[x][y]=1;
					}
					WorldObject wobj2 = getWorldObjectAtXY(map,x,y-1);
					if((wobj2 != null) && inScreenRange(x,y-1) && (map.getVisibleArea()[x][y-1]==0) ){
						map.getVisibleArea()[x][y-1]=1;
					}
					WorldObject wobj3 = getWorldObjectAtXY(map,x-1,y);
					if((wobj3 != null) && inScreenRange(x-1,y) && (map.getVisibleArea()[x-1][y]==0)){
						map.getVisibleArea()[x-1][y]=1;
					}
					if((wobj != null) && (ObjectService.isVisionBlocking(wobj)))breaky=true;
					if((wobj2 != null) && (ObjectService.isVisionBlocking(wobj2))) breaky=true;
					if((wobj3 != null) && (ObjectService.isVisionBlocking(wobj3))) breakx=true;
				}
				y--;
			}
			x--;
		}
		//Check SE Quadrant
		x = objToCheck.getX(); breakx=false;
		while(x < objToCheck.getX() + objToCheck.getSightDistance() && !breakx){
			y = objToCheck.getY(); breaky = false;
			while(y < objToCheck.getY() + objToCheck.getSightDistance() && !breaky){
				if(inScreenRange(x,y) && inScreenRange(x,y+1) && inScreenRange(x+1,y)){
					WorldObject wobj = getWorldObjectAtXY(map,x,y);
					if((wobj != null) && inScreenRange(x,y) && (map.getVisibleArea()[x][y]==0)){
						map.getVisibleArea()[x][y]=1;
					}
					WorldObject wobj2 = getWorldObjectAtXY(map,x,y+1);
					if((wobj2 != null) && inScreenRange(x,y+1) && (map.getVisibleArea()[x][y+1]==0) ){
						map.getVisibleArea()[x][y+1]=1;
					}
					WorldObject wobj3 = getWorldObjectAtXY(map,x+1,y);
					if((wobj3 != null) && inScreenRange(x+1,y) && (map.getVisibleArea()[x+1][y]==0)){
						map.getVisibleArea()[x+1][y]=1;
					}
					if((wobj != null) && (ObjectService.isVisionBlocking(wobj)))breaky=true;
					if((wobj2 != null) && (ObjectService.isVisionBlocking(wobj2))) breaky=true;
					if((wobj3 != null) && (ObjectService.isVisionBlocking(wobj3))) breakx=true;
				}
				y++;
			}
			x++;
		}
		//Check SW Quadrant
		x = objToCheck.getX(); breakx=false;
		while(x >= objToCheck.getX()- objToCheck.getSightDistance() && !breakx){
			y = objToCheck.getY(); breaky = false;
			while(y < objToCheck.getY() + objToCheck.getSightDistance() && !breaky){
				if(inScreenRange(x,y) && inScreenRange(x,y-1) && inScreenRange(x-1,y)){
					WorldObject wobj = getWorldObjectAtXY(map,x,y);
					if((wobj != null) && inScreenRange(x,y) && (map.getVisibleArea()[x][y]==0)){
						map.getVisibleArea()[x][y]=1;
					}
					WorldObject wobj2 = getWorldObjectAtXY(map,x,y+1);
					if((wobj2 != null)  && inScreenRange(x,y+1) && (map.getVisibleArea()[x][y+1]==0) ){
						map.getVisibleArea()[x][y+1]=1;
					}
					WorldObject wobj3 = getWorldObjectAtXY(map,x-1,y);
					if((wobj3 != null) && inScreenRange(x-1,y) && (map.getVisibleArea()[x-1][y]==0)){
						map.getVisibleArea()[x-1][y]=1;
					}
					if((wobj != null) && (ObjectService.isVisionBlocking(wobj)))breaky=true;
					if((wobj2 != null) && (ObjectService.isVisionBlocking(wobj2))) breaky=true;
					if((wobj3 != null) && (ObjectService.isVisionBlocking(wobj3))) breakx=true;
				}
				y++;
			}
			x--;
		}
	}

	/**
	 * Check if the given x and y are in the game dimensions
	 * @param x
	 * @param y
	 * @return
	 */
	static boolean inScreenRange(int x, int y) {
		return x>=0 && x<=WORLDCONSTANTS.SCREENMAXX && y>=0 && y<=WORLDCONSTANTS.SCREENMAXY;
	}

	/**
	 * Returns the {@link WorldObject} in the current map at x and y
	 * @param map
	 * @param x
	 * @param y
	 * @return {@link WorldObject}
	 */
	public static WorldObject getWorldObjectAtXY(MapObject map, int x, int y) {
		for(int i = 0; i<map.getAreaObjectList().size();i++){
			if(((WorldObject)map.getAreaObjectList().get(i)).getX() == x && ((WorldObject)map.getAreaObjectList().get(i)).getY() == y) {
				return (WorldObject)map.getAreaObjectList().get(i);
			}
		}
		return null;
	}

	public static WorldXY getPlaceableXY(List<Object> valueList) {
		WorldXY xy = new WorldXY();
		Random random = new Random();
		boolean placeable = false;
		while(!placeable){
			xy.setX(random.nextInt(WORLDCONSTANTS.SCREENMAXX));
			xy.setY(random.nextInt(WORLDCONSTANTS.SCREENMAXY));
			if(inScreenRange(xy.getX(),xy.getY())){
				for(Object obj : valueList){
					if((((WorldObject)obj).getX() != xy.getX()) && (((WorldObject)obj).getY() != xy.getY())){
						placeable = true;
						break;
					}
				}
			}

		}
		return xy;
	}

	/**
	 * Place an inventory item in the direction of the mainChar's action. But only if it is not blocked.
	 * @param mainChar
	 * @param world
	 * @param map
	 */
	public static void placeInventoryItem(CharacterObject mainChar,	TextWorldObject world, MapObject map) {
		int x = mainChar.getX();
		int y = mainChar.getY();
		switch(mainChar.getActionDirection()){
			case WORLDCONSTANTS.NORTH : y-=1; break;
			case WORLDCONSTANTS.EAST  : x+=1; break;
			case WORLDCONSTANTS.SOUTH : y+=1; break;
			case WORLDCONSTANTS.WEST  : x-=1; break;
		}

		if(inScreenRange(x, y) && isPlaceable(x,y,map) && (mainChar.getInventory().get(mainChar.getInventoryIndex()) != null)){
			//Building Material
			if(ObjectService.isBuildingMaterial(mainChar.getInventory().get(mainChar.getInventoryIndex()))){
				BuildingMaterialObject bobj = new BuildingMaterialObject((BuildingMaterialObject)mainChar.getInventory().get(mainChar.getInventoryIndex()));
				bobj.setX(x);bobj.setY(y);
				bobj.setAmount(1);
				map.getAreaObjectList().add(bobj);
				((BuildingMaterialObject)mainChar.getInventory().get(mainChar.getInventoryIndex())).setAmount(((BuildingMaterialObject)mainChar.getInventory().get(mainChar.getInventoryIndex())).getAmount() -1);
				if(((BuildingMaterialObject)mainChar.getInventory().get(mainChar.getInventoryIndex())).getAmount() <= 0){
					mainChar.getInventory().remove(mainChar.getInventoryIndex());
				}
			} else
			//Weapon Discard random direction
			if(mainChar.getInventory().get(mainChar.getInventoryIndex()).getClass() == WeaponObject.class){
				WeaponObject bobj = new WeaponObject((WeaponObject)mainChar.getInventory().get(mainChar.getInventoryIndex()));
				WorldXY xy = MapService.getPlaceableAroundXY(map,x,y);
				bobj.setX(xy.getX());bobj.setY(xy.getY());
				if(inScreenRange(xy.getX(),xy.getY())){
					map.getAreaObjectList().add(bobj);
					mainChar.getInventory().remove(mainChar.getInventoryIndex());
				}
			}
			else
			//H2O
			if(mainChar.getInventory().get(mainChar.getInventoryIndex()).getClass() == WaterObject.class){
				WaterObject bobj = new WaterObject((WaterObject)mainChar.getInventory().get(mainChar.getInventoryIndex()));
				bobj.setX(x);bobj.setY(y);
				map.getAreaObjectList().add(bobj);
				mainChar.getInventory().remove(mainChar.getInventoryIndex());
			} else
			//Food
			if(mainChar.getInventory().get(mainChar.getInventoryIndex()).getClass() == FoodObject.class){
				FoodObject bobj = new FoodObject((FoodObject)mainChar.getInventory().get(mainChar.getInventoryIndex()));
				bobj.setX(x);bobj.setY(y);
				map.getAreaObjectList().add(bobj);
				mainChar.getInventory().remove(mainChar.getInventoryIndex());
			} else
			//Healing
			if(mainChar.getInventory().get(mainChar.getInventoryIndex()).getClass() == HealingObject.class){
				HealingObject bobj = new HealingObject((HealingObject)mainChar.getInventory().get(mainChar.getInventoryIndex()));
				bobj.setX(x);bobj.setY(y);
				map.getAreaObjectList().add(bobj);
				mainChar.getInventory().remove(mainChar.getInventoryIndex());
			}
		}
	}

	/**
	 * Check if there is a free spot N,S,E,W of x,y in the area.
	 * @param map
	 * @param x
	 * @param y
	 * @return
	 */
	private static WorldXY getPlaceableAroundXY(MapObject map, int x, int y) {
		WorldXY xy = new WorldXY(x,y);
		xy.setX(x+1);
		xy.setY(y);
		if(isPlaceable(xy.getX(), xy.getY(), map)) return xy;
		xy.setX(x);
		xy.setY(y+1);
		if(isPlaceable(xy.getX(), xy.getY(), map)) return xy;
		xy.setX(x-1);
		xy.setY(y);
		if(isPlaceable(xy.getX(), xy.getY(), map)) return xy;
		xy.setX(x);
		xy.setY(y-1);
		if(isPlaceable(xy.getX(), xy.getY(), map)) return xy;

		return new WorldXY();
	}


	/**
	 * Check if the coordinates at x,y are available to place something on.
	 * @param x
	 * @param y
	 * @param map
	 * @return
	 */
	static boolean isPlaceable(int x, int y, MapObject map) {
		boolean placeable = true;
		int i = 0;
		if(!inScreenRange(x, y)) return false;
		while(placeable && i < map.getAreaObjectList().size()){
			WorldObject obj = (WorldObject)map.getAreaObjectList().get(i);
			if((obj.getX() == x && obj.getY() == y && obj.isBlocking())) return false;
			i++;
		}
		return placeable;
	}

	static boolean isPlaceable(int x, int y, MapObject map, WorldObject objToCheck) {
		boolean placeable = true;
		int i = 0;
		if(!inScreenRange(x, y)) return false;
		while(placeable && i < map.getAreaObjectList().size()){
			WorldObject obj = (WorldObject)map.getAreaObjectList().get(i);
			if((!objToCheck.equals(obj))&&((obj.getX() == x && obj.getY() == y && obj.isBlocking()))) return false;
			i++;
		}
		return placeable;
	}


}
