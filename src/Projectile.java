/**
 * Author: Tony Huang
 * 
 * Description: The projectile that the hero shoots
 * 
 * Parameters: String type, the type of the projectile that is shot
 * 
 * Return
 * 
 */

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Projectile implements ActionListener {

	//type of bullet
	private String type; 

	//boundaries and velocity of the bullet
	private double width;
	private double height;
	private double x;
	private double y;
	private double xVel;
	private double yVel;
	
	//bullet graphic
	private BufferedImage[] img; 
	
	//index of the img
	private int imgIndex = 0;
	
	//new imgTimer
	private Timer imgTimer = new Timer(100, this);

	/** Bullet Class Constructor **/
	public Projectile(String type) {

		//calls methods
		setType(type);
		loadImg();
		imgTimer.start();
	}

	/** Getters and Setters **/
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getxVel() {
		return xVel;
	}

	public void setxVel(double xVel) {
		this.xVel = xVel;
	}

	public double getyVel() {
		return yVel;
	}

	public void setyVel(double yVel) {
		this.yVel = yVel;
	}

	public BufferedImage getImg() {
		return img[imgIndex];
	}

	public void setImg(BufferedImage img) {
		this.img[imgIndex] = img;
	}
	
	/**Load Img Method**/
	public void loadImg() {
		
		//instance of frame
		int frame = 0;
		
		
		try {
			
			//read projectile info
			Scanner inputFile = new Scanner(new File("Data/projectileInfo.txt")).useDelimiter(",");
			
			//next line
			inputFile.nextLine();
			
			//while it has next token
			while (inputFile.hasNext()) {
				
				//if type equals the next token
				if (type.equals(inputFile.next()))
					
					//frame = next int value
					frame = inputFile.nextInt();
				
				else
					
					//next line
					inputFile.nextLine();
			}
			
			//catch filenotfound
		} catch (FileNotFoundException e) {
		}

		//img array with length frame
		img = new BufferedImage[frame];

		//for each element in img array
		for (int i = 0; i < img.length; i++) {
			
			try {
				
				//read pictures from Item folder
				img[i] = ImageIO.read(new File("Item/Projectile/" + type + "/" + i + ".png")); 
				
				//catch IOexception
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// set the width of the image file as the
		// height of the bullet
		width = img[0].getWidth(); 
		
		// set the height of the image file as the
		// height of the bullet
		height = img[0].getHeight(); 

	}

	/** Bullet Movement Method **/
	public void move() {
		
		//increase x and y coordinate by xVel, yVel
		x += xVel; 
		y += yVel; 
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		//if imgindex = 1 less than img array
		if (imgIndex == img.length - 1)
			
			//set imgindex = 0
			imgIndex = 0;
		else
			
			//increment
			imgIndex++;

	}

}