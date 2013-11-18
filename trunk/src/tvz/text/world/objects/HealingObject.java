package tvz.text.world.objects;

import net.slashie.libjcsi.CSIColor;

/**
 * The Healing Object is a parent object for items the mainChar can pick up and if
 * used, will heal the mainChar.
 * @author tertiusv
 *
 */
public class HealingObject extends WorldObject{

	private static final long serialVersionUID = -98287145894218337L;

	/**
	 * Default constructor for {@link HealthObject} extends {@link WorldObject}
	 * @param objectName
	 * @param x
	 * @param y
	 * @param asciiChar
	 * @param objectColour
	 * @param blocking
	 * @param breakable
	 * @param health which is also used for this.value
	 */
	public HealingObject(String objectName, int x, int y, char asciiChar, CSIColor objectColour,
			boolean blocking, boolean breakable, int health) {
		super(objectName,x, y, asciiChar, objectColour, blocking, breakable, health);
		this.healingValue=health;
	}


	/**
	 * A copy constructor
	 * @param obj
	 */
	public HealingObject(HealingObject obj) {
		super(obj.getObjectName(),obj.getX(), obj.getY(), obj.getAsciiChar(), obj.getObjectColour(), obj.isBlocking(), obj.isBreakable(), obj.getHealth());
		this.healingValue = obj.healingValue;
	}


	private int healingValue;

	/**
	 * @param value the value to set
	 */
	public void setHealingValue(int value) {
		this.healingValue = value;
	}

	/**
	 * @return the value
	 */
	public int getHealingValue() {
		return healingValue;
	}
}
