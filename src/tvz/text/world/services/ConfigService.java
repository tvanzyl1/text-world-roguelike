package tvz.text.world.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

public class ConfigService implements Serializable{

	private static final long serialVersionUID = -2508964455374678527L;
	Properties prop = new Properties();

	public ConfigService() throws FileNotFoundException, IOException{
		prop.load(new FileInputStream("TextWorld.properties"));
	}

	public static ConfigService newInstance() throws FileNotFoundException, IOException{
		return new ConfigService();
	}

	public String getValue(String key){
		return prop.getProperty(key);
	}



}
