package tvz.text.world.objects;

import net.slashie.libjcsi.CSIColor;
/**
 * The parent class for all building materials.
 * Contains an 'amount' field for stackable inventory.
 * @author tertiusv
 *
 */
public class BuildingMaterialObject extends WorldObject {

	private static final long serialVersionUID = 8750638647488320432L;
	private int amount;

	/**
	 * Default Constructor. Amount default to 1.
	 * @param objectName
	 * @param x
	 * @param y
	 * @param asciiChar
	 * @param objectColour
	 * @param blocking
	 * @param breakable
	 * @param health
	 */
	public BuildingMaterialObject(String objectName, int x, int y,
			char asciiChar, CSIColor objectColour, boolean blocking,
			boolean breakable, int health) {
		super(objectName, x, y, asciiChar, objectColour, blocking, breakable, health);
		this.amount = 1;
		this.setAlive(true);
	}

	/**
	 * Default Constructor plus amount
	 * @param objectName
	 * @param x
	 * @param y
	 * @param asciiChar
	 * @param objectColour
	 * @param blocking
	 * @param breakable
	 * @param health
	 * @param amount
	 */
	public BuildingMaterialObject(String objectName, int x, int y,
			char asciiChar, CSIColor objectColour, boolean blocking,
			boolean breakable, int health, int amount) {
		super(objectName, x, y, asciiChar, objectColour, blocking, breakable, health);
		this.amount = amount;
	}

	/**
	 * Copy constructor.
	 * @param obj
	 */
	public BuildingMaterialObject(BuildingMaterialObject obj){
		super(obj.getObjectName(), obj.getX(), obj.getY(), obj.getAsciiChar(),
				obj.getObjectColour(), obj.isBlocking(), obj.isBreakable(), obj.getHealth());
		this.amount = obj.getAmount();
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

}
