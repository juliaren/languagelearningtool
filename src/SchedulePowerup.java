import java.util.ArrayList;
import java.util.Timer;

public class SchedulePowerup {
	
	//set a timer to run the powerup scheduling which allows a powerup to be added every certain amount of time
	private Timer powerupTimer = new Timer() ; 
	
	//class to run the actual triggering and adding of powerup
	private TriggerPowerup triggerPowerup; 
	
	//first powerup is added 3 sec after game starts
	private long firstSart = 3000;  
	
	//new powerup is added every 7 sec
	private long period = 7000; 	

	public SchedulePowerup(ArrayList<Powerup> powerup){
		
		//create trigger powerup class to run powerup with timer and the given 
		//powerup object (containing the type, image, position, etc.)
		triggerPowerup =  new TriggerPowerup (powerupTimer, powerup); 
		
		//schedule the powerup timer to trigger the run 3 seconds after the game 
		//starts and every 7 seconds after that
		powerupTimer.schedule(triggerPowerup, firstSart, period);
	}
	

}
