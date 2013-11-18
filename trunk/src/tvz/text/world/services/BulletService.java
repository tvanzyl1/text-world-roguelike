package tvz.text.world.services;

import tvz.text.world.objects.BulletObject;
import tvz.text.world.objects.CharacterObject;
import tvz.text.world.objects.TextWorldObject;
import tvz.text.world.objects.WORLDCONSTANTS;
import tvz.text.world.objects.WorldXY;

public class BulletService {

	public static WorldXY placeBullet(CharacterObject mainChar, TextWorldObject world) {
		int x = mainChar.getX();
		int y = mainChar.getY();
		WorldXY xy = new WorldXY(x,y);
		switch(mainChar.getActionDirection()){
			case WORLDCONSTANTS.NORTH : y-=1; break;
			case WORLDCONSTANTS.EAST  : x+=1; break;
			case WORLDCONSTANTS.SOUTH : y+=1; break;
			case WORLDCONSTANTS.WEST  : x-=1; break;
			//TODO: shotgun placement
//			case WORLDCONSTANTS.NORTH_EAST  : y-=1;x+=1; break;
//			case WORLDCONSTANTS.SOUTH_EAST  : x+=1;y+=1; break;
//			case WORLDCONSTANTS.SOUTH_WEST  : y+=1;x-=1; break;
//			case WORLDCONSTANTS.NORTH_WEST  : x-=1;y-=1; break;

		}
		if(MapService.inScreenRange(x, y))
			world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().add(new BulletObject(x,y,mainChar.getSelectedWeaponDamage(), mainChar.getSelectedWeaponDistance()));
		return xy;
	}

}
