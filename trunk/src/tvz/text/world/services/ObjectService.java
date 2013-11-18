package tvz.text.world.services;

import tvz.text.world.objects.BuildingMaterialObject;
import tvz.text.world.objects.FoodObject;
import tvz.text.world.objects.HealingObject;
import tvz.text.world.objects.WORLDCONSTANTS;
import tvz.text.world.objects.WaterObject;
import tvz.text.world.objects.WorldObject;

public class ObjectService {

	public ObjectService(){

	}

	public static ObjectService newInstance(){
		return new ObjectService();
	}

	public static boolean isWaterFoodHealingObject(Object obj){
		return obj.getClass() == HealingObject.class || obj.getClass() == FoodObject.class || obj.getClass() == WaterObject.class;
	}

	public static boolean isBuildingMaterial(Object obj) {
		return obj.getClass() == BuildingMaterialObject.class;
	}

	public static boolean isWall(WorldObject objToCheck) {
		return objToCheck.getObjectName().equalsIgnoreCase(WORLDCONSTANTS.OBJ_TYPE_WALL);
	}

	public static boolean isVisionBlocking(WorldObject worldObject) {
		if(worldObject == null) return true;
		return worldObject.isBlocking() && !(isWater(worldObject));
	}

	public static boolean isWater(WorldObject worldObject) {
		return worldObject.getObjectName().equalsIgnoreCase(WORLDCONSTANTS.OBJ_TYPE_RIVER);
	}
}
