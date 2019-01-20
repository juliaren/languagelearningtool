import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TriggerPowerup extends TimerTask{

	//set a timer to run the powerup scheduling which allows a powerup to be added every certain amount of time
	private Timer powerupTimer;
	
	//holds the arraylist of powerups objects
	private ArrayList<Powerup> powerup;

	//sets the timer and powerup
	public TriggerPowerup(Timer powerupTimer, ArrayList<Powerup> powerup){
		
		this.powerupTimer = powerupTimer;
		
		this.powerup = powerup;
		
	}

	//overrides the super run method from timer task and perform our own method
	@Override
	public void run() {
		
		//remove all existing powerup objects from the powerup arraylist
		powerup.removeAll(powerup);
		
		//add a new powerup to the arraylist
		powerup.add(new Powerup());
	}
}

