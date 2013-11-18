package tvz.text.world.objects;

import java.io.Serializable;

public class Ticker implements Runnable,Serializable {

	private static final long serialVersionUID = 6318210749944411343L;
	private int time = 0;
    private boolean canExecute = false;

    public Ticker(int i)
    {
    	time = i;
    }

    public void run() {
    	while(true){
    	try {
    		time++;
    		setCanExecute(true);
    		Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	}
    }

	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param canExecute the canExecute to set
	 */
	public void setCanExecute(boolean canExecute) {
		this.canExecute = canExecute;
	}

	/**
	 * @return the canExecute
	 */
	public boolean isCanExecute() {
		return canExecute;
	}
}
