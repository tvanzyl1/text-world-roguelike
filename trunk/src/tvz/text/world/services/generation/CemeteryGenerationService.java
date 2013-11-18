package tvz.text.world.services.generation;

import java.util.List;
import java.util.Random;

import tvz.text.world.objects.WORLDCONSTANTS;
import tvz.text.world.objects.WorldObject;
import tvz.text.world.services.GenerationService;
import tvz.text.world.services.MapService;

public class CemeteryGenerationService {

	public static void generateCemeteryArea(String side, List<Object> valueList) {

		Random rand = new Random();
		if(side.equalsIgnoreCase("0")) side = "1";
		int  amount = rand.nextInt(Integer.valueOf(side)*20) + 1;
		boolean canPlace = true;
		for(int j = 0; j <= amount ; j++){
			int x = rand.nextInt(WORLDCONSTANTS.SCREENMAXX-2)+2;
			int y = rand.nextInt(WORLDCONSTANTS.SCREENMAXY-2)+2;
			for(int i = 0; i<valueList.size();i++){
				WorldObject obj = (WorldObject) valueList.get(i);
			  if(obj!=null)
				if(x==obj.getX() && y==obj.getY()){
				  canPlace = false;
			  }
			}
			if(canPlace)
				valueList.add(MapService.TombStone(x, y));
		}

		GenerationService.generateGrass(1000, valueList);
		GenerationService.generateTrees(10, valueList);


	}

}
