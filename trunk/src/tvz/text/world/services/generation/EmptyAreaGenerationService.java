package tvz.text.world.services.generation;

import java.util.List;

import tvz.text.world.services.GenerationService;

public class EmptyAreaGenerationService {

	public static void generateEmptyArea(String side, List<Object> valueList) {


		GenerationService.generateGrass(1000, valueList);
		GenerationService.generateTrees(100, valueList);


	}

}
