package tvz.text.world.objects;

import java.util.Random;

import net.slashie.libjcsi.CSIColor;

public class WaterObject extends HealingObject{
	private static final long serialVersionUID = -4509212133349706162L;

	/**
	 * Default constructor for H20
	 * @param objectName
	 * @param x
	 * @param y
	 * @param asciiChar
	 * @param objectColour
	 * @param blocking
	 * @param breakable
	 * @param health which is also used for this.value
	 * this.healingValue = random({@link WORLDCONSTANTS.FOOD_WATER_HEALING_FACTOR})+1
	 */
	public WaterObject(String objectName, int x, int y, char asciiChar, CSIColor objectColour,
			boolean blocking, boolean breakable, int health) {
		super(objectName,x, y, asciiChar, objectColour, blocking, breakable, health);
		this.value=health;
		Random random = new Random();
		this.setHealingValue(random.nextInt(WORLDCONSTANTS.FOOD_WATER_HEALING_FACTOR)+1);
	}

	/**
	 * A copy constructor
	 * @param obj
	 */
	public WaterObject(WaterObject obj) {
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
