package tvz.text.world.objects;

import java.io.Serializable;
import java.util.TimerTask;

public class TextWorldTask extends TimerTask  implements Serializable{
	private static final long serialVersionUID = -7765442529019701411L;
	//times member represent calling times.
    private int counter;

    public TextWorldTask() {
    	setCounter(0);
	}


	public void run() {
		counter++;
		if(counter>=8000)
			counter = 0;

        //Stop Timer.
        //this.cancel();
    }


	/**
	 * @param counter the counter to set
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}


	/**
	 * @return the counter
	 */
	public int getCounter() {
		return counter;
	}
}