package tvz.text.world.objects;

import java.io.Serializable;
import java.util.HashMap;



/**
 * The world of Text
 * @author tertiusv
 *
 */
public class TextWorldObject implements Serializable{
 
 private static final long serialVersionUID = 692988861911871738L;
 
 private int mapx, mapy;
 private String mapType;
 private HashMap<String, MapObject> worldObjects; //key (mapxmapy), value (List of objects)
 private String prevMapKey;
 private int ticker;
 private ConfigObject config;

 /**
 * @return the mapx
 */
public int getMapx() {
	return mapx;
}
/**
 * @param mapx the mapx to set
 */
public void setMapx(int mapx) {
	this.mapx = mapx;
}
/**
 * @return the mapy
 */
public int getMapy() {
	return mapy;
}
/**
 * @param mapy the mapy to set
 */
public void setMapy(int mapy) {
	this.mapy = mapy;
}
/**
 * @return the worldObjects
 */
public HashMap<String, MapObject> getWorldObjects() {
	if(worldObjects == null){
		worldObjects = new HashMap<String, MapObject>();
	}
	return worldObjects;
}
/**
 * @param worldObjects the worldObjects to set
 */
public void setWorldObjects(HashMap<String, MapObject> worldObjects) {
	this.worldObjects = worldObjects;
}


/**
 * @return the mapxmapy
 */
public String getMapxmapy() {
	String x = String.valueOf(mapx);
	String y = String.valueOf(mapy);
	return x+y;
}
/**
 * @param mapType the mapType to set
 */
public void setMapType(String mapType) {
	this.mapType = mapType;
}
/**
 * @return the mapType
 */
public String getMapType() {
	return mapType;
}
/**
 * @param prevMapKey the prevMapKey to set
 */
public void setPrevMapKey(String prevMapKey) {
	this.prevMapKey = prevMapKey;
}
/**
 * @return the prevMapKey
 */
public String getPrevMapKey() {
	return prevMapKey;
}
/**
 * @param ticker the ticker to set
 */
public void setTicker(int ticker) {
	this.ticker = ticker;
}
/**
 * @return the ticker
 */
public int getTicker() {
	return ticker;
}
/**
 * @param config the config to set
 */
public void setConfig(ConfigObject config) {
	this.config = config;
}
/**
 * @return the config
 */
public ConfigObject getConfig() {
	return config;
}

}
