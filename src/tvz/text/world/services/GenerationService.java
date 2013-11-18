package tvz.text.world.services;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import tvz.text.world.objects.CharacterObject;
import tvz.text.world.objects.FoodObject;
import tvz.text.world.objects.HealingObject;
import tvz.text.world.objects.MapObject;
import tvz.text.world.objects.TextWorldObject;
import tvz.text.world.objects.WORLDCONSTANTS;
import tvz.text.world.objects.WaterObject;
import tvz.text.world.objects.WorldObject;
import tvz.text.world.objects.WorldXY;
import tvz.text.world.services.generation.BuildingGenerationService;
import tvz.text.world.services.generation.CemeteryGenerationService;
import tvz.text.world.services.generation.EmptyAreaGenerationService;
import tvz.text.world.services.generation.ForestGenerationService;
import tvz.text.world.services.generation.MountainGenerationService;
import tvz.text.world.services.generation.RiverGenerationService;
import tvz.text.world.services.generation.RoadGenerationService;

/**
 * Generates the world
 * @author tertiusv
 *
 */
public class GenerationService {

	public static void generateAreas(int mapx, int mapy, String areaToGenerate, int dangerRating, TextWorldObject world, CharacterObject mainChar) throws IOException {
		String area = areaToGenerate.substring(0,1);
		String side = areaToGenerate.substring(1,2);

		world.setMapx(mapx);
		world.setMapy(mapy);
		MapObject mapArea = new MapObject();
		mapArea.setMapXmapY(world.getMapxmapy());
		mapArea.setAreaObjectList(new ArrayList<Object>());
		//Create the ArrayList.
		List<Object> valueList = new ArrayList<Object>();
		int dangerRatingToUse = dangerRating*10;

		//Generate Object.
		if(area.equals("M")) { //Mountain
			mapArea.setAreaName("Mountain Range");
			MountainGenerationService.generateMountain(side,valueList);
			dangerRatingToUse=2;
		}else
		if(area.equals("W")) { //Water
			mapArea.setAreaName("The River-Z");
			RiverGenerationService.generateRiver(side,valueList);
			dangerRatingToUse=2;
		}else
		if(area.equals("R")) { //Road
			mapArea.setAreaName("A Lonely Road");
			RoadGenerationService.generateRoad(side,valueList);
			dangerRatingToUse=5;
		}else
		if(area.equals("B")) { //Buildings
			mapArea.setAreaName("A City");
			BuildingGenerationService.generateBuilding(side, valueList);
		}else
		if(area.equals("F")) { //Forest
			mapArea.setAreaName("Forest Area");
			ForestGenerationService.generateForest(side, valueList);
		}else
		if(area.equals("C")) { //Cemetery
			mapArea.setAreaName("Place for the dead");
			String density = side;
			CemeteryGenerationService.generateCemeteryArea(density, valueList);
		}else
		if(area.equals("S")) { //Safe Zone
			mapArea.setAreaName("Safe Zone");
			BuildingGenerationService.generateBuilding(side, valueList);
		}else
		if(area.equals("L")) { //Close to city
			mapArea.setAreaName("City Surroundings");
			BuildingGenerationService.generateBuilding(side, valueList);
			dangerRatingToUse=3;
		}else
		if(area.equals("E")) { //Extras like river end and bridges.
			ExtrasGenerationService.generateExtras(side, valueList,mapArea);
			dangerRatingToUse=3;
		}else{
			mapArea.setAreaName("Dead Zone");
			EmptyAreaGenerationService.generateEmptyArea(side, valueList);
			dangerRatingToUse=0;
		}
		//Generate enemies
		if(dangerRatingToUse > 0){
			GenerationService.generateZombies(dangerRatingToUse, valueList);
		}

		generateWeaponsWaterFoodHealing(area,valueList);

//		//TODO: remove this line.
//		valueList.add(MapService.CreateRevolver(11,11));
//		valueList.add(MapService.CreateKnife(12,11));
//		valueList.add(MapService.CreateRifle(13,11));
//		valueList.add(MapService.CreateBow(14,11));
//		valueList.add(MapService.CreateAxe(15,11));
		valueList.add(MapService.CreateFood(16,11));
//		valueList.add(MapService.CreateWater(17,11));
//		valueList.add(MapService.CreateHealth(18,11));
//
//		//TODO: remove above line

		mapArea.setAreaObjectList(valueList);
		mapArea.setVisibleArea(emptyMapInt());
		//Add to worldobjects.
		world.getWorldObjects().put(world.getMapxmapy(), mapArea);

	}


	/**
	 * Uses probability to generate objects in the area.
	 * @param area
	 * @param valueList
	 */
	private static void generateWeaponsWaterFoodHealing(String area,List<Object> valueList) {
		//City   : weapons(med), food(small),water(small),healing(small)
		int weaponFactor = 0;
		int foodFactor = 0;
		int waterFactor = 0;
		int healingFactor = 0;
		if(area.equals("B")){
			weaponFactor = 2; foodFactor = 1; waterFactor = 1; healingFactor = 1;
		}
		//Forest : food(med), water(small), healing(small)
		if(area.equals("F")){
			foodFactor = 1; waterFactor = 1; healingFactor = 1;
		}
		//River : water(med)
		if(area.equals("W")){
			waterFactor = 1;
		}
		//Road : weapon(small)
		if(area.equals("R")){
			weaponFactor = 1;
		}
		generateWeapons(weaponFactor,valueList);
		generateFood(foodFactor,valueList);
		generateWater(waterFactor,valueList);
		generateHealing(healingFactor,valueList);

	}

	/**
	 * Create random amount of {@link FoodObject} at random location based on factor
	 * @param factor
	 * @param valueList
	 */
	private static void generateFood(int factor, List<Object> valueList) {
		if(factor > 0){
			Random random = new Random();
			int n = random.nextInt(factor)+1;
			if(n>0){
				for(int i = 0; i <= n; i++){
					WorldXY xy = MapService.getPlaceableXY(valueList);
					valueList.add(MapService.CreateFood(xy.getX(), xy.getY()));
				}
			}
		}
	}


	/**
	 * Create random amount of {@link WaterObject} at random location based on factor
	 * @param factor
	 * @param valueList
	 */
	private static void generateWater(int factor, List<Object> valueList) {
		if(factor > 0){
			Random random = new Random();
			int n = random.nextInt(factor)+1;
			if(n>0){
				for(int i = 0; i <= n; i++){
					WorldXY xy = MapService.getPlaceableXY(valueList);
					valueList.add(MapService.CreateWater(xy.getX(), xy.getY()));
				}
			}
		}

	}


	/**
	 * Create random amount of {@link HealingObject} at random location based on factor
	 * @param factor
	 * @param valueList
	 */
	private static void generateHealing(int factor, List<Object> valueList) {
		if(factor > 0){
			Random random = new Random();
			int n = random.nextInt(factor)+1;
			if(n>0){
				for(int i = 0; i <= n; i++){
					WorldXY xy = MapService.getPlaceableXY(valueList);
					valueList.add(MapService.CreateHealth(xy.getX(), xy.getY()));
				}
			}
		}
	}

	/**
	 * GEnerate a random amount of weapons based on the factor in random locations.
	 * @param factor
	 * @param valueList
	 */
	private static void generateWeapons(int factor, List<Object> valueList) {
		if(factor > 0){
			Random random = new Random();
			int n = random.nextInt(factor)+1;
			if(n>0){
				for(int i = 0; i <= n; i++){
					WorldXY xy = MapService.getPlaceableXY(valueList);
					n = random.nextInt(6)+1;
					switch(n){
						case 1: valueList.add(MapService.CreateKnife(xy.getX(),xy.getY())); break;
						case 2: valueList.add(MapService.CreateAxe(xy.getX(),xy.getY())); break;
						case 3: valueList.add(MapService.CreateBow(xy.getX(),xy.getY())); break;
						case 4: valueList.add(MapService.CreateRevolver(xy.getX(),xy.getY())); break;
						case 5: valueList.add(MapService.CreateRifle(xy.getX(),xy.getY())); break;
						case 6: valueList.add(MapService.CreateSledge(xy.getX(),xy.getY())); break;
					}
				}
			}
		}
	}


	public static List<List<String>> ReadMapFile() throws FileNotFoundException, IOException{
		List<List<String>> worldMapFile = new ArrayList<List<String>>();
		String csvFile = ConfigService.newInstance().getValue("mapfile");
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
		        // use comma as separator
				String[] aLine = line.split(cvsSplitBy);
				List<String> strings = Arrays.asList(aLine);
				worldMapFile.add(strings);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");

		return worldMapFile;
	}

	public static List<List<String>> ReadMapImage(PrintService printService) throws IOException{
		File file = new File(ConfigService.newInstance().getValue("mapfile"));
		BufferedImage image = ImageIO.read(file);
		List<List<String>> arrToReturn = new ArrayList<List<String>>();
		//Mountain area = 64/64/0 = M
		//Water  		= 0/0/200 = W
		//Road			= 200/100/0 = R
		//Building		= 255/0/0 = B
		//Forest		= 0/255/0 = F
		//Cemetery		= 0/0/0 = C
		//Compound		= 255/255/0
		//City Surroundings = 10/10/0
		//Empty World
		for(int y=0; y<image.getHeight();y++){
			arrToReturn.add(new ArrayList<String>());
			for(int x = 0; x < image.getWidth();x++){
				int colour = image.getRGB(x,y);
				int r = colour >> 16 & 0xFF; 	//R
				int g = colour >> 8 & 0xFF;  	//G
				int b = colour & 0xFF; 		//B
				//Mountain area = 64/64/0
				if(r==64 && g ==64){
					arrToReturn.get(y).add("M"+String.valueOf(b)+"D"+"1");
				} else
				//Water  		= 0/0/200
				if(r==0 && g ==0 && b>200){
					if(b-200<10)
					 arrToReturn.get(y).add("W"+String.valueOf(b-200)+"D"+"1");
					else if(b-200 == 10){
						arrToReturn.get(y).add("W"+"R"+"D"+"1");
					}
				} else
				//Road			= 200/100/0 = R
				if(r==200 && g ==100){
					if(b<10)
						arrToReturn.get(y).add("R"+String.valueOf(b)+"D"+"2");
					else if(b==10){
						arrToReturn.get(y).add("R"+"R"+"D"+"2");
					}
				} else
				//Building		= 255/0/0 = B
				if(r==255 && g ==0){
					arrToReturn.get(y).add("B"+String.valueOf(b)+"D"+"2");
				} else
				//Forest		= 0/255/0 = F
				if(r==0 && g ==255){
					arrToReturn.get(y).add("F"+String.valueOf(b)+"D"+"2");
				} else
				//Cemetery		= 0/0/0 = C
				if(r==0 && g ==0 && b < 200){
					arrToReturn.get(y).add("C"+String.valueOf(b)+"D"+"3");
				} else
				//Compound		= 255/255/0
				if(r==255 && g ==255 && b < 255){
					arrToReturn.get(y).add("S"+String.valueOf(b)+"D"+"0");
				} else
				//City surroundings
				if(r==255 && g ==160 && b ==70){
					arrToReturn.get(y).add("L"+String.valueOf(b)+"D"+"1");
				} else
				//Extras
				if(r==127 && g ==0 && b >=110){
					arrToReturn.get(y).add("E"+String.valueOf(b-110)+"D"+"1");
				} else
				//Empty World
				{
					arrToReturn.get(y).add("0"+"0"+"D"+"0");
				}
				System.out.println("Reading map file : ["+x+","+y+"]");
				}
			}

		return arrToReturn;
	}
	static void GenerateMap(PrintService printService, List<List<String>> worldMapToUse, TextWorldObject worldToUse, CharacterObject mainChar) throws IOException{
		String area = "";
		int dangerRating = 0;
		for(int mapy = 0; mapy < worldMapToUse.size(); mapy ++){
			for(int mapx = 0; mapx < worldMapToUse.get(mapy).size();mapx++){
				//Divide in Area and Danger Zone.
				System.out.println("Generate Map areas and objects : ["+mapx+","+mapy+"]");
				area = worldMapToUse.get(mapy).get(mapx).substring(0, 2);
				dangerRating = Integer.valueOf(worldMapToUse.get(mapy).get(mapx).substring(3));
				//Generate borders, area
				GenerationService.generateAreas(mapx,mapy,area,dangerRating,worldToUse, mainChar);

				//lastly add the mainChar
				  worldToUse.getWorldObjects().get(worldToUse.getMapxmapy()).getAreaObjectList().add(mainChar);
				  worldToUse.getWorldObjects().get(worldToUse.getMapxmapy()).setMainCharIndex(worldToUse.getWorldObjects().get(worldToUse.getMapxmapy()).getAreaObjectList().size()-1);
			}

		}
	}


	public static void testPrint(PrintService printService, TextWorldObject world, int mapx, int mapy){
		printService.cls();
		world.setMapx(mapx);
		world.setMapy(mapy);
		PrintService.print(world);
	}


	public static void generateGrass(int factor, List<Object> valueList) {
		Random rand = new Random();
		int  amount = rand.nextInt(factor) + 1;
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
				valueList.add(MapService.Grass(x, y));
		}
	}


	public static void generateTrees(int factor, List<Object> valueList) {
		Random rand = new Random();
		int  amount = rand.nextInt(factor) + 1;
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
				valueList.add(MapService.Tree(x, y));
		}
	}


	private static void generateZombies(int dangerRating, List<Object> valueList) {
		Random rand = new Random();
		int  amount = rand.nextInt(dangerRating) + 1;
		boolean canPlace = true;
		for(int j = 0; j <= amount ; j++){
			int x = rand.nextInt(WORLDCONSTANTS.SCREENMAXX-2)+2;
			int y = rand.nextInt(WORLDCONSTANTS.SCREENMAXY-2)+2;
			for(int i = 0; i<valueList.size();i++){
				WorldObject obj = (WorldObject) valueList.get(i);
			  if(obj!=null)
				if(x==obj.getX() && y==obj.getY()){
				  canPlace = !obj.isBlocking();
			  }
			}
			if(canPlace){
				int  n = rand.nextInt(2) + 1;
				if(n==1)
					valueList.add(CharacterService.NewZombieWalker(x, y));
				else
					valueList.add(CharacterService.NewZombieCrawler(x, y));
			}
		}
	}


	public static String[][] emptyMap(){
		String[][] str =
	    {{"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"},
		 {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"}};
	return str;	}

	public static int[][] emptyMapInt(){
		int[][] map =
			   {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
	return map;	}



public static List<List<String>> ReadResourceImage(String mapFile) throws IOException{
	File file = new File(mapFile);
	BufferedImage image = ImageIO.read(file);
	List<List<String>> arrToReturn = new ArrayList<List<String>>();

	for(int y=0; y<image.getHeight();y++){
		arrToReturn.add(new ArrayList<String>());
		for(int x = 0; x < image.getWidth();x++){
			int colour = image.getRGB(x,y);
			int r = colour >> 16 & 0xFF; 	//R
			int g = colour >> 8 & 0xFF;  	//G
			int b = colour & 0xFF; 		//B
			if(r==255 && g == 255 && b==255){
				arrToReturn.get(y).add(" ");
			}else if(r==0 && g == 0 && b==255){
				arrToReturn.get(y).add("W");
			}else if(r==127 && g == 51 && b==0){
				arrToReturn.get(y).add("R");
			}else{
				arrToReturn.get(y).add("E");
			}
		}
	}
	return arrToReturn;
}


public static List<List<String>> ReadResourceImage(String mapFile, String charToUse) throws IOException{
	File file = new File(mapFile);
	BufferedImage image = ImageIO.read(file);
	List<List<String>> arrToReturn = new ArrayList<List<String>>();

	for(int y=0; y<image.getHeight();y++){
		arrToReturn.add(new ArrayList<String>());
		for(int x = 0; x < image.getWidth();x++){
			int colour = image.getRGB(x,y);
			int r = colour >> 16 & 0xFF; 	//R
			int g = colour >> 8 & 0xFF;  	//G
			int b = colour & 0xFF; 		//B
			if(r==255 && g == 255 && b==255){
				arrToReturn.get(y).add(" ");
			}else{
				arrToReturn.get(y).add(charToUse);
			}
		}
	}
	return arrToReturn;
}


public static void generateRoadArea(String side, int n, List<Object> valueList) throws IOException {
	Random random = new Random();
	int m = random.nextInt(n)+1;
	List<List<String>> strList = GenerationService.ReadResourceImage(".\\resources\\road_"+side+"_"+m+".bmp");
	for(int y=0;y<strList.size();y++){
		for(int x=0;x<strList.get(y).size();x++){
			if(strList.get(y).get(x).equalsIgnoreCase("R")){
				valueList.add(MapService.createRoad(x, y));
			}
		}
	}
}


public static void generateRiverArea(String side, int n, List<Object> valueList) throws IOException {
	Random random = new Random();
	int m = random.nextInt(n)+1;
	List<List<String>> strList = GenerationService.ReadResourceImage(".\\resources\\river_"+side+"_"+m+".bmp");
	for(int y=0;y<strList.size();y++){
		for(int x=0;x<strList.get(y).size();x++){
			if(strList.get(y).get(x).equalsIgnoreCase("W")){
				valueList.add(MapService.createRiver(x, y));
			}
		}
	}
}

}