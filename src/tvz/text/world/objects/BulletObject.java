package tvz.text.world.objects;

import net.slashie.libjcsi.CSIColor;

public class BulletObject extends WorldObject{
	private static final long serialVersionUID = 9114404607087925219L;
	private int damage;
	private int distance;

	/**
	 * Takes x,y coordinates and the associated weapon damage.
	 * @param x
	 * @param y
	 * @param weaponDamage
	 */
	public BulletObject(int x, int y, int weaponDamage, int distanceCanTravel) {
		super("bullet",x, y, WORLDCONSTANTS.CHAR_BULLET.charAt(0), CSIColor.WHITE, true, false, 1000000);
		this.damage = weaponDamage;
		this.setDistance(distanceCanTravel);
	}

	/**
	 * @param damage the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}

}
