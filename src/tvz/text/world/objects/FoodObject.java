package tvz.text.world.objects;

import java.util.Random;

import net.slashie.libjcsi.CSIColor;

public class FoodObject extends HealingObject{
	private static final long serialVersionUID = 1812618961893458278L;

	/**
	 * Default Constructor
	 * @param objectName
	 * @param x
	 * @param y
	 * @param asciiChar
	 * @param objectColour
	 * @param blocking
	 * @param breakable
	 * @param health which is also used for this.value
	 */
	public FoodObject(String objectName, int x, int y, char asciiChar, CSIColor objectColour,
			boolean blocking, boolean breakable, int health) {
		super(objectName,x, y, asciiChar, objectColour, blocking, breakable, health);
		this.value=health;
		Random random = new Random();
		this.setHealingValue(random.nextInt(WORLDCONSTANTS.FOOD_WATER_HEALING_FACTOR)+1);
	}

	/**
	 * Default constructor to copy an object.
	 * @param obj
	 */
	public FoodObject(FoodObject obj) {
		super(obj.getObjectName(),obj.getX(), obj.getY(), obj.getAsciiChar(), obj.getObjectColour(), obj.isBlocking(), obj.isBreakable(), obj.getHealth());
		this.value = obj.value;
		this.setHealingValue(obj.getHealingValue());
	}

	private int value;

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
}
