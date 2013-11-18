package tvz.text.world.services;

import net.slashie.libjcsi.CharKey;
import tvz.text.world.objects.WorldXY;

public class MenuService {

	public static MenuService newInstance(){
		return new MenuService();
	}

	/**
	 * Print a main menu with a selector. Also uses key presses.
	 * @param printService
	 * @return
	 */
	public static int MainMenu(PrintService printService) {
		WorldXY xy = new WorldXY(10,10);
		int intToReturn = -1;
        printService.restore();
        printService.cls();
		PrintService.printMainMenu();
		printService.printMenuSelector(xy);
		printService.refresh();
		while(intToReturn != 1 && intToReturn != 2 && intToReturn != 4){
			int key = PrintService.getCsi().inkey().code;
            switch (key){
            	case CharKey.UARROW: xy.setY(xy.getY()-1); break;
            	case CharKey.DARROW: xy.setY(xy.getY()+1); break;
            	case CharKey.ENTER : intToReturn = xy.getY()-9;
            }
            if(xy.getY()>13)xy.setY(10);
            if(xy.getY()<10)xy.setY(13);
            printService.cls();
            PrintService.printMainMenu();
            printService.printMenuSelector(xy);
            printService.refresh();
		}
		return intToReturn;
	}

	/**
	 * Print an in-game menu with a selector. Also uses key presses.
	 * @param printService
	 * @return
	 */
	public static int InGameMenu(PrintService printService) {
		WorldXY xy = new WorldXY(1,1);
		int intToReturn = -1;
        printService.restore();
        printService.cls();
		PrintService.printInGameMenu();
		printService.printMenuSelector(xy);
		printService.refresh();
		while(intToReturn != 1 && intToReturn != 2){
			int key = PrintService.getCsi().inkey().code;
            switch (key){
            	case CharKey.UARROW: xy.setY(xy.getY()-1); break;
            	case CharKey.DARROW: xy.setY(xy.getY()+1); break;
            	case CharKey.ENTER : intToReturn = xy.getY();
            }
            if(xy.getY()>2)xy.setY(1);
            if(xy.getY()<1)xy.setY(2);
            printService.cls();
            PrintService.printInGameMenu();
            printService.printMenuSelector(xy);
            printService.refresh();
		}
		return intToReturn;
	}

}
