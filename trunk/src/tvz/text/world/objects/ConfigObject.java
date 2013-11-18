/**
 *
 */
package tvz.text.world.objects;

import java.io.Serializable;

/**
 * The object that contains all game configuration.
 * @author tertiusv
 *
 */
public class ConfigObject  implements Serializable{

	private static final long serialVersionUID = 8048747211930347156L;

	/**
	 * SEE_ALL : No FOW, prints all.
	 * FOW_VIEW : normal FOW that goes away.
	 * LIMITED_VIEW : forget what you saw earlier.
	 * @author tertiusv
	 */
	public static enum ViewTypeEnum {
	    SEE_ALL, FOW_VIEW, LIMITED_VIEW;
	}

	/**
	 * Default constructor
	 */
	public ConfigObject(){
		viewType = ViewTypeEnum.FOW_VIEW;
	}

	private ViewTypeEnum viewType;

	/**
	 * @param viewType the viewType to set
	 */
	public void setViewType(ViewTypeEnum viewType) {
		this.viewType = viewType;
	}

	/**
	 * @return the viewType
	 */
	public ViewTypeEnum getViewType() {
		return viewType;
	}



}
