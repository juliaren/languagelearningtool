import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class Powerup {
	
	//variable to hold the type of powerup
	private int type;
	
	////variable to hold the image for the current powerup
	private BufferedImage graphic;
	
	//variable to hold the position of the image of the powerup
	private Rectangle bounds = new Rectangle();
	
	//variable to hold the x and y (coordinates) of the image of the powerup
	private int x, y;
	
	//to generate a random number for the x and y (coordinate) of the image of the powerup
	private Random rand = new Random();
	
	public Powerup(){

		//generate x coordinate value that is less than the width of the game screen
		x = rand.nextInt(750) + 1;
		
		//generate y coordinate value that is less than the height of the game screen
		y = rand.nextInt(400) + 1;
		
		//print the random generate x and y coordinates
		System.out.println(x);
		System.out.println(y);
		
		//set the position of the image of the powerup to the randomly generated coordinate
		setBounds(new Rectangle(x, y, 40, 40));
		
		//set up the type of powerup
		setType();
		
		//set up the image of the powerup according to the type
		setGraphic();
	}
	
	/**getters and setters**/
	
	public int getType() {
		return type;
	}
	
	//different types of powerup is generated according to different probabilities
	//because one powerup may be more favourable than another
	
	//0 - heart : increase health - 49%
	//1 - wings : increase speed - 8%
	//2 - missile : better bullet - 34%
	//3 - shield : invincible - 9%
	public void setType() {
		
		//generate random number for probability
		int p = rand.nextInt(100);
		
		//set type of powerup to heart
		if (p < 50)
			type = 0;
		
		//set type of powerup to wing
		else if (p >= 50 && p <= 57)
			type = 1;
		
		//set type of powerup to missile
		else if (p >= 58 && p <= 91)
			type = 2;
		
		//set type of powerup to shield
		else 
			type = 3;
		

	}
	public BufferedImage getGraphic() {
		return graphic;
	}
	
	public void setGraphic() {
		
		//read in image files from folder according to the type
		try{
			System.out.println("type:" + type);
			graphic = ImageIO.read(new File("Powerup/" + type + ".png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public int getX() {
		return bounds.x;
	}

	public void setX(int x) {
		bounds.x = x;
	}

	public int getY() {
		return bounds.y;
	}

	public void setY(int y) {
		bounds.y = y;
	}
}
