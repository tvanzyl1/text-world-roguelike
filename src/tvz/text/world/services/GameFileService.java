package tvz.text.world.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import tvz.text.world.objects.TextWorldObject;


public class GameFileService {


	public GameFileService(){
	}


	public static GameFileService newInstance(){
		return new GameFileService();

	}


	/**
	 * Save a {@link TextWorldObject} game.
	 * @param worldToSave
	 */
	public void saveGame(TextWorldObject worldToSave){
		try{
			FileOutputStream fout = new FileOutputStream(ConfigService.newInstance().getValue("saveGameFile"));
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(worldToSave);
			oos.close();
			System.out.println("Done");
		 }catch(Exception ex){
				   ex.printStackTrace();
		 }
	}


	/**
	 * Loads a game.
	 * @param printService
	 * @return {@link TextWorldObject}
	 */
	public TextWorldObject loadGame(PrintService printService){
		try{
			TextWorldObject worldToLoad;
			FileInputStream fin = new FileInputStream(ConfigService.newInstance().getValue("saveGameFile"));
			ObjectInputStream ois = new ObjectInputStream(fin);
			worldToLoad = (TextWorldObject) ois.readObject();
			ois.close();
			return worldToLoad;

		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
}
