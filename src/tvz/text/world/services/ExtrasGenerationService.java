package tvz.text.world.services;

import java.io.IOException;
import java.util.List;

import tvz.text.world.objects.MapObject;
import tvz.text.world.objects.WORLDCONSTANTS;
import tvz.text.world.objects.WorldObject;

public class ExtrasGenerationService {

	public static void generateExtras(String side, List<Object> valueList,
			MapObject mapArea) throws IOException {
		String[][] str = GenerationService.emptyMap();
		//TOP
		if(side.equalsIgnoreCase("8")){
			List<List<String>> strList = GenerationService.ReadResourceImage(".\\resources\\water_end_8.bmp");
			for(int y=0;y<strList.size();y++){
				for(int x=0;x<strList.get(y).size();x++){
					if(strList.get(y).get(x).equalsIgnoreCase("E")){
						valueList.add(MapService.createRiver(x,y));
					}
				}
			}
			mapArea.setAreaName(WORLDCONSTANTS.AREA_NAME_RIVER);
		} else //BOTTOM
		if(side.equalsIgnoreCase("2")){
//			str = generateRoad2();
		} else //LEFT
		if(side.equalsIgnoreCase("4")){
			List<List<String>> strList = GenerationService.ReadResourceImage(".\\resources\\water_end_4.bmp");
			for(int y=0;y<strList.size();y++){
				for(int x=0;x<strList.get(y).size();x++){
					if(strList.get(y).get(x).equalsIgnoreCase("E")){
						valueList.add(MapService.createRiver(x,y));
					}
				}
			}
			mapArea.setAreaName(WORLDCONSTANTS.AREA_NAME_RIVER);
		} else //RIGHT
		if(side.equalsIgnoreCase("6")){
			List<List<String>> strList = GenerationService.ReadResourceImage(".\\resources\\water_end_6.bmp");
			for(int y=0;y<strList.size();y++){
				for(int x=0;x<strList.get(y).size();x++){
					if(strList.get(y).get(x).equalsIgnoreCase("E")){
						valueList.add(MapService.createRiver(x,y));
					}
				}
			}
			mapArea.setAreaName(WORLDCONSTANTS.AREA_NAME_RIVER);
		} else //TOP-LEFT
		if(side.equalsIgnoreCase("7")){
//			str = generateRoad7();
		} else //TOP RIGHT
		if(side.equalsIgnoreCase("9")){
//			str = generateRoad9();
		} else //BOTTOM LEFT
		if(side.equalsIgnoreCase("1")){
//			str = generateRoad1();
		} else // BOTTOM RIGHT
		if(side.equalsIgnoreCase("3")){
//			str = generateRoad3();
		} else
		if(side.equalsIgnoreCase("5")){
//			str = generateRoad5();
		}
		if(side.equalsIgnoreCase("0")){
			List<List<String>> strList = GenerationService.ReadResourceImage(".\\resources\\bridge_0_1.bmp");
			for(int y=0;y<strList.size();y++){
				for(int x=0;x<strList.get(y).size();x++){
					if(strList.get(y).get(x).equalsIgnoreCase("W")){
						valueList.add(MapService.createRiver(x,y));
					}
					if(strList.get(y).get(x).equalsIgnoreCase("R")){
						valueList.add(MapService.createRoad(x,y));
					}
				}
			}
			mapArea.setAreaName(WORLDCONSTANTS.AREA_NAME_RIVER);
		}
		if(side.equalsIgnoreCase("R")){
			List<List<String>> strList = GenerationService.ReadResourceImage(".\\resources\\bridge_R_1.bmp");
			for(int y=0;y<strList.size();y++){
				for(int x=0;x<strList.get(y).size();x++){
					if(strList.get(y).get(x).equalsIgnoreCase("W")){
						valueList.add(MapService.createRiver(x,y));
					}
					if(strList.get(y).get(x).equalsIgnoreCase("R")){
						valueList.add(MapService.createRoad(x,y));
					}
				}
			}
			mapArea.setAreaName(WORLDCONSTANTS.AREA_NAME_RIVER);
		}

		GenerationService.generateGrass(100, valueList);
		GenerationService.generateTrees(100, valueList);
	}

}
