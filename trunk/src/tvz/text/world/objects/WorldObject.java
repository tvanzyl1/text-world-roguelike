package tvz.text.world.objects;

import java.io.Serializable;
import java.util.Date;


import net.slashie.libjcsi.CSIColor;

/**
 * The backbone object of all objects in TextWorld.
 * @author tertiusv
 *
 */
public class WorldObject implements Serializable{
	private static final long serialVersionUID = -3393020301252233187L;
	private int x,y;
	private int prevX, prevY;
	private char asciiChar;
	private CSIColor objectColour;
	private CSIColor backgroundColour = CSIColor.GREEN;
	private boolean blocking;
	private boolean breakable;
	private int health = WORLDCONSTANTS.MAXHEALTH;
	private Date dateDead;
	private boolean isAlive = true;
	private int originalX;
	private int originalY;
	private int orignalHealth;
	private String objectName;

	/**
	 * Default Constructor.
	 * @param objectName
	 * @param x
	 * @param y
	 * @param asciiChar
	 * @param objectColour
	 * @param blocking
	 * @param breakable
	 * @param health
	 */
	public WorldObject(String objectName, int x, int y, char asciiChar, CSIColor objectColour, boolean blocking, boolean breakable, int health){
		this.objectName = objectName;
		this.x = x;
		this.y = y;
		this.originalX = x;
		this.originalY = y;
		this.prevX = x;
		this.prevY = y;
		this.asciiChar = asciiChar;
		this.objectColour = objectColour;
		this.blocking = blocking;
		this.breakable = breakable;
		this.health = health;
		this.orignalHealth = health;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}


	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}


	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}


	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}


	/**
	 * @return the prevX
	 */
	public int getPrevX() {
		return prevX;
	}


	/**
	 * @param prevX the prevX to set
	 */
	public void setPrevX(int prevX) {
		this.prevX = prevX;
	}


	/**
	 * @return the prevY
	 */
	public int getPrevY() {
		return prevY;
	}


	/**
	 * @param prevY the prevY to set
	 */
	public void setPrevY(int prevY) {
		this.prevY = prevY;
	}


	/**
	 * @param asciiChar the asciiChar to set
	 */
	public void setAsciiChar(char asciiChar) {
		this.asciiChar = asciiChar;
	}


	/**
	 * @return the asciiChar
	 */
	public char getAsciiChar() {
		return asciiChar;
	}


	/**
	 * @param objectColour the objectColour to set
	 */
	public void setObjectColour(CSIColor objectColour) {
		this.objectColour = objectColour;
	}


	/**
	 * @return the objectColour
	 */
	public CSIColor getObjectColour() {
		return objectColour;
	}


	/**
	 * @return the blocking
	 */
	public boolean isBlocking() {
		return blocking;
	}


	/**
	 * @param blocking the blocking to set
	 */
	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
	}


	/**
	 * @return the breakable
	 */
	public boolean isBreakable() {
		return breakable;
	}


	/**
	 * @param breakable the breakable to set
	 */
	public void setBreakable(boolean breakable) {
		this.breakable = breakable;
	}


	/**
	 * @param backgroundColour the backgroundColour to set
	 */
	public void setBackgroundColour(CSIColor backgroundColour) {
		this.backgroundColour = backgroundColour;
	}


	/**
	 * @return the backgroundColour
	 */
	public CSIColor getBackgroundColour() {
		return backgroundColour;
	}


	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}


	/**
	 * @param health the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}


	/**
	 * @return the dateDead
	 */
	public Date getDateDead() {
		return dateDead;
	}


	/**
	 * @param dateDead the dateDead to set
	 */
	public void setDateDead(Date dateDead) {
		this.dateDead = dateDead;
	}


	/**
	 * @return the isAlive
	 */
	public boolean isAlive() {
		return isAlive;
	}


	/**
	 * @param isAlive the isAlive to set
	 */
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}


	/**
	 * @return the originalX
	 */
	public int getOriginalX() {
		return originalX;
	}


	/**
	 * @param originalX the originalX to set
	 */
	public void setOriginalX(int originalX) {
		this.originalX = originalX;
	}


	/**
	 * @return the originalY
	 */
	public int getOriginalY() {
		return originalY;
	}


	/**
	 * @param originalY the originalY to set
	 */
	public void setOriginalY(int originalY) {
		this.originalY = originalY;
	}


	/**
	 * @param orignalHealth the orignalHealth to set
	 */
	public void setOrignalHealth(int orignalHealth) {
		this.orignalHealth = orignalHealth;
	}

	/**
	 * @return the orignalHealth
	 */
	public int getOrignalHealth() {
		return orignalHealth;
	}

	/**
	 * Kills off an object.
	 * Sets the date the object was destroyed.
	 * Sets the isAlive to false;
	 */
	public void destroyObject() {
		this.dateDead = new Date();
		this.isAlive = false;
	}

	/**
	 * Sets the isAlive to true.
	 * Sets the original x and y and health.
	 */
	public void respawnObject(){
		this.isAlive = true;
		this.x = this.originalX;
		this.y = this.originalY;
		this.health = this.orignalHealth;
	}

	/**
	 * @param objectName the objectName to set
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	/**
	 * @return the objectName
	 */
	public String getObjectName() {
		return objectName;
	}

}
