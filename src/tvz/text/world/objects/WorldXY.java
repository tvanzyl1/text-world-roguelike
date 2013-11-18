package tvz.text.world.objects;

import java.io.Serializable;

public class WorldXY  implements Serializable{
	private static final long serialVersionUID = 3563843932159852597L;
	private int x;
	private int y;

	public WorldXY(int x, int y){
		this.x = x;
		this.y = y;
	}

	public WorldXY(){
		this.x = -1;
		this.y = -1;
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

}
