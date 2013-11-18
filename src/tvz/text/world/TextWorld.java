package tvz.text.world;

import java.io.IOException;

import net.slashie.libjcsi.CharKey;
import tvz.text.world.objects.CharacterObject;
import tvz.text.world.objects.TextWorldObject;
import tvz.text.world.objects.WORLDCONSTANTS;
import tvz.text.world.services.AttackService;
import tvz.text.world.services.GameFileService;
import tvz.text.world.services.KeyPressService;
import tvz.text.world.services.MapService;
import tvz.text.world.services.MenuService;
import tvz.text.world.services.MovementService;
import tvz.text.world.services.NewGame;
import tvz.text.world.services.PrintService;

public class TextWorld {

    private PrintService printService = PrintService.newInstance();
    private TextWorldObject world;

    public static void main(String[] p) throws IOException, InterruptedException {
        new TextWorld().run();
    }

    public void init() throws IOException{
    	PrintService.print(1,1,"Starting new game..."); System.out.println("Starting new game");
    	world = NewGame.initialise(printService);
//    	GameFileService.newInstance().saveGame(world);
    	PrintService.print(1,1,"World Initialised.");System.out.println("World Initialised.");
//    	world = GameFileService.newInstance().loadGame(csi);

    }

    public void run () throws IOException, InterruptedException {
    	int menuSelection = MenuService.MainMenu(printService);
    	boolean exit = false;

    	switch(menuSelection){
    		case 1 : world = NewGame.initialise(printService); break;
    		case 2 : world = GameFileService.newInstance().loadGame(printService); break;
    		case 4 : exit = true;
    	}

//    	init();
    	System.out.println("Game Starts");
    	printService.cls();
    	printService.saveBuffer();

        while (!exit){

        	printService.restore();
        	printService.cls();
        	CharacterObject mainChar = (CharacterObject) world.getWorldObjects().get(world.getMapxmapy()).getAreaObjectList().get(world.getWorldObjects().get(world.getMapxmapy()).getMainCharIndex());
        	printService.print(mainChar.getX(), mainChar.getY(), mainChar.getAsciiChar(),mainChar.getAsciiChar());
        	PrintService.print(world);
        	printService.refresh();

//            Check keys, movement and collisions
            int key = PrintService.getCsi().inkey().code;
            int inGameMenuKey = 0;
            KeyPressService.getKeys(mainChar,key);
            switch (key){
            	case CharKey.ESC : inGameMenuKey = MenuService.InGameMenu(printService);
            	case CharKey.F1  : printService.printHelp();
            }
            switch(inGameMenuKey){
            	case 1: inGameMenuKey = 0; System.out.println("Menu key pressed : " + inGameMenuKey);break;
            	case 2: exit = true; System.out.println("Menu key pressed : " + inGameMenuKey);break;
            }
            if(!exit){
	            //Move all the characters.
	            if(mainChar.isMovementAction()){
	              MovementService.checkMainCharMovement(world);
	              //Check if zombie should attack or move?
	              AttackService.zombieAttack(world,mainChar);
	              MovementService.checkZombiesMovement(world);
	              MovementService.moveObjects(world);
	            }
	            //Shoots or action inventory items
	            if(mainChar.isActionInventoryItem()){
	            	MovementService.executeMainCharAction(mainChar,world);
	            	while(!mainChar.isActionCompleted()){
	            		MovementService.checkBulletOrPlacedObjectCollision(mainChar,world);
	            		MovementService.moveObjects(world);
	            		try{
	            			Thread.sleep(WORLDCONSTANTS.ATTACK_PRINT_RATE_MS);
	            		}catch(InterruptedException e){

	            		}
	                	//csi.cls();
	            		PrintService.print(world);
	            		printService.refresh();
	            	}
	            }
	            mainChar.checkHealth();
	            MapService.checkWorldObjectHealth(world,world.getWorldObjects().get(world.getMapxmapy()));
            }
        }
        PrintService.print(1, 20, "Press space to exit");
        printService.refresh();
        PrintService.getCsi().waitKey(CharKey.SPACE);
        System.exit(0);
    }

	/**
	 * @param printService the printService to set
	 */
	public void setPrintService(PrintService printService) {
		this.printService = printService;
	}

	/**
	 * @return the printService
	 */
	public PrintService getPrintService() {
		return printService;
	}
}