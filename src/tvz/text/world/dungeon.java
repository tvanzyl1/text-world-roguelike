package tvz.text.world;

import java.util.Random;

import tvz.text.world.objects.WORLDCONSTANTS;

public class dungeon{


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args){
		Random rand = new Random();
		int x = rand.nextInt(WORLDCONSTANTS.SCREENMAXX-2)+2;
		int y = rand.nextInt(WORLDCONSTANTS.SCREENMAXY-2)+2;

		System.err.println("X :"+WORLDCONSTANTS.SCREENMAXX+" Y :"+WORLDCONSTANTS.SCREENMAXY);
		for(int j = 0; j < 4000; j++){
			if(x==WORLDCONSTANTS.SCREENMAXX || y==WORLDCONSTANTS.SCREENMAXY || x==0 || y==0)
				System.err.println("X :"+x+" Y :"+y);
//			else
//				System.out.println("X :"+x+" Y :"+y);
			 x = rand.nextInt(WORLDCONSTANTS.SCREENMAXX-2)+2;
			 y = rand.nextInt(WORLDCONSTANTS.SCREENMAXY-2)+2;

		}

	}
}