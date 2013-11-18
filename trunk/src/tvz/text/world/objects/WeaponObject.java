package tvz.text.world.objects;

import java.util.Random;

import net.slashie.libjcsi.CSIColor;
import tvz.text.world.objects.WORLDCONSTANTS.WeaponTypeEnum;

public class WeaponObject extends WorldObject{

	private static final long serialVersionUID = -9218675792956536993L;
	private int weaponDamage;
	private int weaponRateOfFire;
	private int weaponDistance;
	private int weaponAmmo;
	private WeaponTypeEnum weaponType;
	private boolean isSilenced = false;

	/**
	 * @param weaponDamage the weaponDamage to set
	 */
	public void setWeaponDamage(int weaponDamage) {
		this.weaponDamage = weaponDamage;
	}
	/**
	 * @return the weaponDamage
	 */
	public int getWeaponDamage() {
		return weaponDamage;
	}

	/**
	 * @param weaponRateOfFire the weaponRateOfFire to set
	 */
	public void setWeaponRateOfFire(int weaponRateOfFire) {
		this.weaponRateOfFire = weaponRateOfFire;
	}
	/**
	 * @return the weaponRateOfFire
	 */
	public int getWeaponRateOfFire() {
		return weaponRateOfFire;
	}

	/**
	 * @param weaponDistance the weaponDistance to set
	 */
	public void setWeaponDistance(int weaponDistance) {
		this.weaponDistance = weaponDistance;
	}
	/**
	 * @return the weaponDistance
	 */
	public int getWeaponDistance() {
		return weaponDistance;
	}
	/**
	 * @param weaponAmmo the weaponAmmo to set
	 */
	public void setWeaponAmmo(int weaponAmmo) {
		this.weaponAmmo = weaponAmmo;
	}
	/**
	 * @return the weaponAmmo
	 */
	public int getWeaponAmmo() {
		return weaponAmmo;
	}
	/**
	 * @param weaponType the weaponType to set
	 */
	public void setWeaponType(WeaponTypeEnum weaponType) {
		this.weaponType = weaponType;
	}
	/**
	 * @return the weaponType
	 */
	public WeaponTypeEnum getWeaponType() {
		return weaponType;
	}
	public WeaponObject(String objectName, int x, int y, char asciiChar, CSIColor objectColour,
			int weaponDamage, int weaponRateOfFire, int weaponDistance, WeaponTypeEnum weaponType, boolean isSilenced){
		super(objectName,x, y, asciiChar, objectColour, false, false, WORLDCONSTANTS.MAXHEALTH);
		this.weaponDamage = weaponDamage;
		this.weaponRateOfFire = weaponRateOfFire;
		this.weaponDistance = weaponDistance;
		this.weaponType = weaponType;
		if(this.weaponType == WeaponTypeEnum.LONG_RANGE_WEAPON)
			this.weaponAmmo = randomAmmoAmount();
		else
			this.weaponAmmo = 1;
		this.isSilenced = isSilenced;
	}

	public WeaponObject(WeaponObject obj) {
		super(obj.getObjectName(),obj.getX(), obj.getY(), obj.getAsciiChar(), obj.getObjectColour(), false, false, WORLDCONSTANTS.MAXHEALTH);
		this.weaponDamage = obj.getWeaponDamage();
		this.weaponRateOfFire = obj.getWeaponRateOfFire();
		this.weaponDistance = obj.getWeaponDistance();
		this.weaponAmmo = obj.getWeaponAmmo();
		this.weaponType = obj.getWeaponType();
		this.isSilenced = obj.isSilenced();
	}

	private int randomAmmoAmount(){
		Random rand = new Random();
		return rand.nextInt(9) + 1;
	}


	public boolean isSilenced() {
		return this.isSilenced ;
	}

	public void setIsSilenced(boolean value){
		this.isSilenced = value;
	}
}
