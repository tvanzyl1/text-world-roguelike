package tvz.text.world;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tvz.text.world.objects.TextWorldObject;
import tvz.text.world.services.GenerationService;
import tvz.text.world.services.PrintService;

public class TextWorldGenerateMap {
	private PrintService printService = PrintService.newInstance();
	private TextWorldObject world;
	private List<List<String>> worldMap;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		new TextWorldGenerateMap().run();
	}

	public void run () throws IOException {
		world = new TextWorldObject();
		worldMap = ReadMapFile();
		GenerateMap(worldMap,world);

		GenerationService.testPrint(printService,world, 1,1);
	}

	private List<List<String>> ReadMapFile(){
		List<List<String>> worldMapFile = new ArrayList<List<String>>();
		String csvFile = "C:/PersonalSourceCode/TestRPG/3x3.csv";
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

	private void GenerateMap(List<List<String>> worldMapToUse, TextWorldObject worldToUse) throws IOException{
		String area = "";
		int dangerRating = 0;
		for(int mapy = 0; mapy < worldMapToUse.size(); mapy ++){
			for(int mapx = 0; mapx < worldMapToUse.get(mapy).size();mapx++){
				System.out.print(worldMapToUse.get(mapy).get(mapx));
				//Divide in Area and Danger Zone.
				area = worldMapToUse.get(mapy).get(mapx).substring(0, 2);
				dangerRating = Integer.valueOf(worldMapToUse.get(mapy).get(mapx).substring(3));
				//Generate borders, area
				GenerationService.generateAreas(mapx,mapy,area,dangerRating,worldToUse,null);
				//Generate plants/Buildings
				//Generate enemies
				if(dangerRating > 0){
					//TODO: Generate enemies
				}

			}
			System.out.println();
		}
	}
}
