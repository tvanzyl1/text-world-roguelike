package tvz.text.world.services.generation;

import java.util.Date;
import java.util.List;
import java.util.Random;

import tvz.text.world.objects.WORLDCONSTANTS;
import tvz.text.world.objects.WorldObject;
import tvz.text.world.services.GenerationService;
import tvz.text.world.services.MapService;

public class ForestGenerationService {

	public static void generateForest(String side, List<Object> valueList) {
		//initial stuff used in making the map
		int x = WORLDCONSTANTS.SCREENMAXX; int y = WORLDCONSTANTS.SCREENMAXY;
		int dungeon_objects = 1;
		//then we create a new forest map
		if (createForest(x, y, dungeon_objects)){
			genForest(valueList);
		}
		GenerationService.generateGrass(1000, valueList);
		GenerationService.generateTrees(200, valueList);
	}

	//max size of the map
	private static int xmax = WORLDCONSTANTS.SCREENMAXX; //75 columns
	private static int ymax = WORLDCONSTANTS.SCREENMAXY; //20 rows

	//size of the map
	private static int xsize = 0;
	private static int ysize = 0;

	//number of "objects" to generate on the map
	private static int objects = 0;

	//define the %chance to generate either a room or a corridor on the map
	//BTW, rooms are 1st priority so actually it's enough to just define the chance
	//of generating a room
	private static int chanceRoom = 85;

	//our map
	private static int[] dungeon_map = { };

	//the old seed from the RNG is saved in this one
	private static long oldseed = 0;

	//a list over tile types we're using
	final private static int tileUnused = 0;
	final private static int tileDirtWall = 1; //not in use
	final private static int tileDirtFloor = 2;
	final private static int tileStoneWall = 3;
	final private static int tileCorridor = 4;
	final private static int tileDoor = 5;
	final private static int tileUpStairs = 6;
	final private static int tileDownStairs = 7;
	final private static int tileChest = 8;

	//setting a tile's type
	private static void setCell(int x, int y, int celltype){
		dungeon_map[x + xsize * y] = celltype;
	}

	//returns the type of a tile
	private static int getCell(int x, int y){
		return dungeon_map[x + xsize * y];
	}

	//The RNG. the seed is based on seconds from the "java epoch" ( I think..)
	//perhaps it's the same date as the unix epoch
	private static int getRand(int min, int max){
		//the seed is based on current date and the old, already used seed
		Date now = new Date();
		long seed = now.getTime() + oldseed;
		oldseed = seed;

		Random randomizer = new Random(seed);
		int n = max - min + 1;
		int i = randomizer.nextInt(n);
		if (i < 0)
			i = -i;
		return min + i;
	}

	private static boolean makeCorridor(int x, int y, int lenght, int direction){
		//define the dimensions of the corridor (er.. only the width and height..)
		int len = getRand(2, lenght);
		int floor = tileCorridor;
		int dir = 0;
		if (direction > 0 && direction < 4) dir = direction;

		int xtemp = 0;
		int ytemp = 0;

		switch(dir){
		case 0:
		//north
			//check if there's enough space for the corridor
			//start with checking it's not out of the boundaries
			if (x < 0 || x > xsize) return false;
			else xtemp = x;

			//same thing here, to make sure it's not out of the boundaries
			for (ytemp = y; ytemp > (y-len); ytemp--){
				if (ytemp < 0 || ytemp > ysize) return false; //oh boho, it was!
				if (getCell(xtemp, ytemp) != tileUnused) return false;
			}

			//if we're still here, let's start Forest
			for (ytemp = y; ytemp > (y-len); ytemp--){
				setCell(xtemp, ytemp, floor);
			}
			break;
		case 1:
		//east
				if (y < 0 || y > ysize) return false;
				else ytemp = y;

				for (xtemp = x; xtemp < (x+len); xtemp++){
					if (xtemp < 0 || xtemp > xsize) return false;
					if (getCell(xtemp, ytemp) != tileUnused) return false;
				}

				for (xtemp = x; xtemp < (x+len); xtemp++){
					setCell(xtemp, ytemp, floor);
				}
			break;
		case 2:
		//south
			if (x < 0 || x > xsize) return false;
			else xtemp = x;

			for (ytemp = y; ytemp < (y+len); ytemp++){
				if (ytemp < 0 || ytemp > ysize) return false;
				if (getCell(xtemp, ytemp) != tileUnused) return false;
			}

			for (ytemp = y; ytemp < (y+len); ytemp++){
				setCell(xtemp, ytemp, floor);
			}
			break;
		case 3:
		//west
			if (ytemp < 0 || ytemp > ysize) return false;
			else ytemp = y;

			for (xtemp = x; xtemp > (x-len); xtemp--){
				if (xtemp < 0 || xtemp > xsize) return false;
				if (getCell(xtemp, ytemp) != tileUnused) return false;
			}

			for (xtemp = x; xtemp > (x-len); xtemp--){
				setCell(xtemp, ytemp, floor);
			}
			break;
		}

		//woot, we're still here! let's tell the other guys we're done!!
		return true;
	}

	private static boolean makeRoom(int x, int y, int xlength, int ylength, int direction){
		//define the dimensions of the room, it should be at least 4x4 tiles (2x2 for walking on, the rest is walls)
		int xlen = getRand(4, xlength);
		int ylen = getRand(4, ylength);
		//the tile type it's going to be filled with
		int floor = tileDirtFloor; //jordgolv..
		int wall = tileDirtWall; //jordv????gg
		//choose the way it's pointing at
		int dir = 0;
		if (direction > 0 && direction < 4) dir = direction;

		switch(dir){
		case 0:
		//north
			//Check if there's enough space left for it
			for (int ytemp = y; ytemp > (y-ylen); ytemp--){
				if (ytemp < 0 || ytemp > ysize) return false;
				for (int xtemp = (x-xlen/2); xtemp < (x+(xlen+1)/2); xtemp++){
					if (xtemp < 0 || xtemp > xsize) return false;
					if (getCell(xtemp, ytemp) != tileUnused) return false; //no space left...
				}
			}

			//we're still here, build
			for (int ytemp = y; ytemp > (y-ylen); ytemp--){
				for (int xtemp = (x-xlen/2); xtemp < (x+(xlen+1)/2); xtemp++){
					//start with the walls
					if (xtemp == (x-xlen/2)) setCell(xtemp, ytemp, wall);
					else if (xtemp == (x+(xlen-1)/2)) setCell(xtemp, ytemp, wall);
					else if (ytemp == y) setCell(xtemp, ytemp, wall);
					else if (ytemp == (y-ylen+1)) setCell(xtemp, ytemp, wall);
					//and then fill with the floor
					else setCell(xtemp, ytemp, floor);
				}
			}
			break;
		case 1:
		//east
			for (int ytemp = (y-ylen/2); ytemp < (y+(ylen+1)/2); ytemp++){
				if (ytemp < 0 || ytemp > ysize) return false;
				for (int xtemp = x; xtemp < (x+xlen); xtemp++){
					if (xtemp < 0 || xtemp > xsize) return false;
					if (getCell(xtemp, ytemp) != tileUnused) return false;
				}
			}

			for (int ytemp = (y-ylen/2); ytemp < (y+(ylen+1)/2); ytemp++){
				for (int xtemp = x; xtemp < (x+xlen); xtemp++){

					if (xtemp == x) setCell(xtemp, ytemp, wall);
					else if (xtemp == (x+xlen-1)) setCell(xtemp, ytemp, wall);
					else if (ytemp == (y-ylen/2)) setCell(xtemp, ytemp, wall);
					else if (ytemp == (y+(ylen-1)/2)) setCell(xtemp, ytemp, wall);

					else setCell(xtemp, ytemp, floor);
				}
			}
			break;
		case 2:
		//south
			for (int ytemp = y; ytemp < (y+ylen); ytemp++){
				if (ytemp < 0 || ytemp > ysize) return false;
				for (int xtemp = (x-xlen/2); xtemp < (x+(xlen+1)/2); xtemp++){
					if (xtemp < 0 || xtemp > xsize) return false;
					if (getCell(xtemp, ytemp) != tileUnused) return false;
				}
			}

			for (int ytemp = y; ytemp < (y+ylen); ytemp++){
				for (int xtemp = (x-xlen/2); xtemp < (x+(xlen+1)/2); xtemp++){

					if (xtemp == (x-xlen/2)) setCell(xtemp, ytemp, wall);
					else if (xtemp == (x+(xlen-1)/2)) setCell(xtemp, ytemp, wall);
					else if (ytemp == y) setCell(xtemp, ytemp, wall);
					else if (ytemp == (y+ylen-1)) setCell(xtemp, ytemp, wall);

					else setCell(xtemp, ytemp, floor);
				}
			}
			break;
		case 3:
		//west
			for (int ytemp = (y-ylen/2); ytemp < (y+(ylen+1)/2); ytemp++){
				if (ytemp < 0 || ytemp > ysize) return false;
				for (int xtemp = x; xtemp > (x-xlen); xtemp--){
					if (xtemp < 0 || xtemp > xsize) return false;
					if (getCell(xtemp, ytemp) != tileUnused) return false;
				}
			}

			for (int ytemp = (y-ylen/2); ytemp < (y+(ylen+1)/2); ytemp++){
				for (int xtemp = x; xtemp > (x-xlen); xtemp--){

					if (xtemp == x) setCell(xtemp, ytemp, wall);
					else if (xtemp == (x-xlen+1)) setCell(xtemp, ytemp, wall);
					else if (ytemp == (y-ylen/2)) setCell(xtemp, ytemp, wall);
					else if (ytemp == (y+(ylen-1)/2)) setCell(xtemp, ytemp, wall);

					else setCell(xtemp, ytemp, floor);
				}
			}
			break;
		}

		//yay, all done
		return true;
	}


	//used to print the map on the screen
	public void showDungeon(){
		for (int y = 0; y < ysize; y++){
			for (int x = 0; x < xsize; x++){
				switch(getCell(x, y)){
				case tileUnused:
					System.out.print("0");
					break;
				case tileDirtWall:
					System.out.print("#");
					break;
				case tileDirtFloor:
					System.out.print(".");
					break;
				case tileStoneWall:
					System.out.print("0");
					break;
				case tileCorridor:
					System.out.print("0");
					break;
				case tileDoor:
					System.out.print(".");
					break;
				case tileUpStairs:
					System.out.print(".");
					break;
				case tileDownStairs:
					System.out.print(".");
					break;
				case tileChest:
					System.out.print(".");
					break;
				};
			}
			if (xsize <= xmax) System.out.println();
		}
	}

	//used to print the map on the screen
	public static void genForest(List<Object> valueList){
		WorldObject obj = null;
		for (int y = 0; y < ysize; y++){
			for (int x = 0; x < xsize; x++){
				obj = null;
				switch(getCell(x, y)){
				case tileUnused:
					break;
				case tileDirtWall:
					obj = MapService.Tree(x, y);
					break;
				case tileDirtFloor:
					obj = MapService.Tree(x, y);
					break;
				case tileStoneWall:
					break;
				case tileCorridor:
					break;
				case tileDoor:
					obj = MapService.Tree(x, y);
					break;
				case tileUpStairs:
					obj = MapService.Tree(x, y);
					break;
				case tileDownStairs:
					obj = MapService.Tree(x, y);
					break;
				case tileChest:
					obj = MapService.Tree(x, y);
					break;
				};
				if(obj != null)
				  valueList.add(obj);
			}
		}
	}

	//and here's the one generating the whole map
	public static boolean createForest(int inx, int iny, int inobj){
		if (inobj < 1) objects = 10;
		else objects = inobj;

		//adjust the size of the map, if it's smaller or bigger than the limits
		if (inx < 3) xsize = 3;
		else if (inx > xmax) xsize = xmax;
		else xsize = inx;

		if (iny < 3) ysize = 3;
		else if (iny > ymax) ysize = ymax;
		else ysize = iny;

		//redefine the map var, so it's adjusted to our new map size
		dungeon_map = new int[xsize * ysize];

		//start with making the "standard stuff" on the map
		for (int y = 0; y < ysize; y++){
			for (int x = 0; x < xsize; x++){
				//ie, making the borders of unwalkable walls
				if (y == 0) setCell(x, y, tileStoneWall);
				else if (y == ysize-1) setCell(x, y, tileStoneWall);
				else if (x == 0) setCell(x, y, tileStoneWall);
				else if (x == xsize-1) setCell(x, y, tileStoneWall);

				//and fill the rest with dirt
				else setCell(x, y, tileUnused);
			}
		}

		/*******************************************************************************
		And now the code of the random-map-generation-algorithm begins!
		*******************************************************************************/

		//start with making a room in the middle, which we can start Forest upon
		makeRoom(xsize/2, ysize/2, 8, 6, getRand(0,3)); //getrand saken f????r att slumpa fram riktning p?? rummet
		//keep count of the number of "objects" we've made
		int currentFeatures = 1; //+1 for the first room we just made

		//then we sart the main loop
		for (int countingTries = 0; countingTries < 1000; countingTries++){
			//check if we've reached our quota
			if (currentFeatures == objects){
				break;
			}

			//start with a random wall
			int newx = 0;
			int xmod = 0;
			int newy = 0;
			int ymod = 0;
			int validTile = -1;
			//1000 chances to find a suitable object (room or corridor)..
			//(yea, i know it's kinda ugly with a for-loop... -_-')
			for (int testing = 0; testing < 1000; testing++){

				newx = getRand(1, xsize-1);
				newy = getRand(1, ysize-1);
				validTile = -1;
				//System.out.println("tempx: " + newx + "\ttempy: " + newy);
				if (getCell(newx, newy) == tileDirtWall || getCell(newx, newy) == tileCorridor){
					//check if we can reach the place
					if (getCell(newx, newy+1) == tileDirtFloor || getCell(newx, newy+1) == tileCorridor){
						validTile = 0; //
						xmod = 0;
						ymod = -1;
					}
					else if (getCell(newx-1, newy) == tileDirtFloor || getCell(newx-1, newy) == tileCorridor){
						validTile = 1; //
						xmod = +1;
						ymod = 0;
					}
					else if (getCell(newx, newy-1) == tileDirtFloor || getCell(newx, newy-1) == tileCorridor){
						validTile = 2; //
						xmod = 0;
						ymod = +1;
					}
					else if (getCell(newx+1, newy) == tileDirtFloor || getCell(newx+1, newy) == tileCorridor){
						validTile = 3; //
						xmod = -1;
						ymod = 0;
					}

					//check that we haven't got another door nearby, so we won't get alot of openings besides
					//each other
					if (validTile > -1){
						if (getCell(newx, newy+1) == tileDoor) //north
							validTile = -1;
						else if (getCell(newx-1, newy) == tileDoor)//east
							validTile = -1;
						else if (getCell(newx, newy-1) == tileDoor)//south
							validTile = -1;
						else if (getCell(newx+1, newy) == tileDoor)//west
							validTile = -1;
					}

					//if we can, jump out of the loop and continue with the rest
					if (validTile > -1) break;
				}
			}
			if (validTile > -1){
				//choose what to build now at our newly found place, and at what direction
				int feature = getRand(0, 100);
				if (feature <= chanceRoom){ //a new room
					if (makeRoom((newx+xmod), (newy+ymod), 8, 6, validTile)){
						currentFeatures++; //add to our quota

						//then we mark the wall opening with a door
						setCell(newx, newy, tileDoor);

						//clean up infront of the door so we can reach it
						setCell((newx+xmod), (newy+ymod), tileDirtFloor);
					}
				}
				else if (feature >= chanceRoom){ //new corridor
					if (makeCorridor((newx+xmod), (newy+ymod), 6, validTile)){
						//same thing here, add to the quota and a door
						currentFeatures++;

						setCell(newx, newy, tileDoor);
					}
				}
			}
		}


		/*******************************************************************************
		All done with the Forest, let's finish this one off
		*******************************************************************************/

		return true;
	}


}
