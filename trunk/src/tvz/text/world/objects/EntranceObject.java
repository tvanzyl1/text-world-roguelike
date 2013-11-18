package tvz.text.world.objects;

import net.slashie.libjcsi.CSIColor;

public class EntranceObject extends WorldObject{

	private static final long serialVersionUID = 4151172093116217032L;

	public EntranceObject(int x, int y, char asciiChar, CSIColor objectColour,
			boolean blocking, boolean breakable, int health) {
		super("entrance",x, y, asciiChar, objectColour, blocking, breakable, health);
		// TODO Auto-generated constructor stub
	}

	private String newMapKey; //The key in the world hash map to transistion to.

	/**
	 * @return the newMapKey
	 */
	public String getNewMapKey() {
		return newMapKey;
	}

	/**
	 * @param newMapKey the newMapKey to set
	 */
	public void setNewMapKey(String newMapKey) {
		this.newMapKey = newMapKey;
	}

}
