package tvz.text.world.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tvz.text.world.objects.CharacterObject;
import tvz.text.world.objects.ConfigObject;
import tvz.text.world.objects.MapObject;
import tvz.text.world.objects.TextWorldObject;

public class NewGame {

	public NewGame(){

	}

	public static TextWorldObject initialise(PrintService printService) throws IOException{
		TextWorldObject world = new TextWorldObject();

		List<List<String>>	worldMap;
		world.setMapx(1);
		world.setMapy(1);

		//Read the map file.
		PrintService.print(1,1,"Reading map file...");System.out.println("Reading map file...");
		if(Boolean.valueOf(ConfigService.newInstance().getValue("useimagemap")))
			worldMap = GenerationService.ReadMapImage(printService);
		else
			worldMap = GenerationService.ReadMapFile();
		printService.cls();
		PrintService.print(1,1,"Create new character");
		//Always add mainChar last.
		CharacterObject mainChar = CharacterService.NewMainCharacter(10,10);

		printService.cls();
		PrintService.print(1,1,"Generate map");
		GenerationService.GenerateMap(printService,worldMap,world,mainChar);


		//add the first map objects
		MapObject area = world.getWorldObjects().get(world.getMapxmapy());
		if(area==null) area = new MapObject();

		List<Object> worldObjectList = area.getAreaObjectList();
//
		if(worldObjectList == null) worldObjectList = new ArrayList<Object>();
//		//Add other stuff.

		world.getWorldObjects().put(world.getMapxmapy(), area);

		//config
		world.setConfig(new ConfigObject());


		world.setMapx(Integer.valueOf(ConfigService.newInstance().getValue("mapStartX")!=null?ConfigService.newInstance().getValue("mapStartX"):"0"));
		world.setMapy(Integer.valueOf(ConfigService.newInstance().getValue("mapStartY")!=null?ConfigService.newInstance().getValue("mapStartY"):"0"));

		return world;

	}

}
