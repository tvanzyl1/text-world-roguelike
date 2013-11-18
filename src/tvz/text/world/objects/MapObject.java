package tvz.text.world.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MapObject  implements Serializable{

	private static final long serialVersionUID = -5979003301754444428L;
	private List<Object> areaObjectList;
	private String mapXmapY;
	private int mainCharIndex;
	private String areaName;
	private int[][] visibleArea = {{0}};



/**
 * @param areaObjectList the areaObjectList to set
 */
public void setAreaObjectList(List<Object> areaObjectList) {
	this.areaObjectList = areaObjectList;
}

/**
 * @return the areaObjectList
 */
public List<Object> getAreaObjectList() {
	if(areaObjectList == null) areaObjectList = new ArrayList<Object>();
	return areaObjectList;
}

/**
 * @param mapXmapY the mapXmapY to set
 */
public void setMapXmapY(String mapXmapY) {
	this.mapXmapY = mapXmapY;
}

/**
 * @return the mapXmapY
 */
public String getMapXmapY() {
	return mapXmapY;
}

/**
 * @param mainCharIndex the mainCharIndex to set
 */
public void setMainCharIndex(int mainCharIndex) {
	this.mainCharIndex = mainCharIndex;
}

/**
 * @return the mainCharIndex
 */
public int getMainCharIndex() {
	if((mainCharIndex<areaObjectList.size())&&(areaObjectList.get(mainCharIndex)!=null)&&(((WorldObject)areaObjectList.get(mainCharIndex)).getObjectName() == WORLDCONSTANTS.OBJ_TYPE_MAINCHAR))
		return mainCharIndex;

	for(int i = 0; i<areaObjectList.size();i++){
		if(((WorldObject)areaObjectList.get(i)).getObjectName() == WORLDCONSTANTS.OBJ_TYPE_MAINCHAR) {
			mainCharIndex = i;
			return mainCharIndex;
		}
	}
	return mainCharIndex;
}

/**
 * @param areaName the areaName to set
 */
public void setAreaName(String areaName) {
	this.areaName = areaName;
}

/**
 * @return the areaName
 */
public String getAreaName() {
	return areaName;
}

/**
 * @param visibleArea the visibleArea to set
 */
public void setVisibleArea(int[][] visibleArea) {
	this.visibleArea = visibleArea;
}

/**
 * @return the visibleArea
 */
public int[][] getVisibleArea() {
	return visibleArea;
}

}
