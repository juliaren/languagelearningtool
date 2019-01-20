/**
 * Author: Tony Huang
 * 
 * Positioning and movement AI component author: Julia Ren
 * 
 * Description: This class contains the graphic, coordinates, speed, and the movement of the mobs/alien
 * 
 * Parameter: String Type, the type of the alien, int x, x is a random number, int y, y is also a random number, heroBounds, gets the position and size of the hero
 * 
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.Timer;

import org.omg.CORBA.Bounds;

public class Mob implements ActionListener {

	//type of mob
	private String type;

	//bounds of mobs
	private Rectangle bounds = new Rectangle();

	//different imgs for different mobs
	private BufferedImage[] img;


	//index of img, img timer
	private int imgIndex = 0;
	private Timer imgTimer = new Timer(100, this);

	//set xVel, yVel
	private double xVel = 1, yVel = 1;

	//new Random class
	private Random rand = new Random();

	//direction
	private int direction;

	/**Constructor Method**/
	public Mob(String type, int x, int y) {

		//start timer, set the type of mob, set the img
		imgTimer.start();
		setType(type);
		setImg();

		//random numbers
		x = rand.nextInt(1000) + 1;
		y = rand.nextInt(250) + 1;

		//if randoms next = 0
		if(rand.nextInt(2) == 0)
			direction = 1;

		//else
		else
			direction = 0;

		//set coordinates of mobs
		setCoordinates(x, y);
	}

	/**Setters and Getters**/
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Rectangle getCoordinates() {
		return bounds;
	}

	public void setCoordinates(int x, int y) {

		bounds.x = x;
		bounds.y = y;

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

	public BufferedImage getImg() {
		return img[imgIndex];
	}

	/**SetImg Method**/
	public void setImg() {

		//frame for animation
		int frame = 0;

		//read mobInfo
		try {
			Scanner inputFile = new Scanner(new File("Data/mobInfo.txt")).useDelimiter(",");

			//nextLine
			inputFile.nextLine();

			//while inputFile has token
			while (inputFile.hasNext()) {

				//if type equals token
				if (type.equals(inputFile.next()))

					//frame equals next int
					frame = inputFile.nextInt();
				else

					//next line
					inputFile.nextLine();
			}

			//catch filenotfound
		} catch (FileNotFoundException e) {
		}

		//img is new bufferedimage index of frame
		img = new BufferedImage[frame];

		//for length of img array
		for (int i = 0; i < img.length; i++) {

			//try
			try {

				//read from mob folder for pictures
				img[i] = ImageIO.read(new File("Mob/" + type + "/stand." + i + ".png"));

				//catch IOexception
			} catch (IOException e) {
				e.printStackTrace();
			}

			//assign string to graphic
			img[i].getGraphics().drawString(type, 1, 20);

		}

		//print type
		System.out.println(type);

		//set the width and height
		bounds.width = img[0].getWidth();
		bounds.height = img[0].getHeight();

	}

	/**Move Method**/
	public void movementAI(Rectangle heroBounds) {

		//if x < 0 or > 1024 - width of rectangle
		if (bounds.x < 0 || bounds.x > 1024 - bounds.getWidth())

			//change direction
			xVel *= -1;

		//if y < 0 or > 768 - height of rectangle
		else if (bounds.y < 0 || bounds.y > 768 - bounds.getHeight())

			//change direction
			yVel *= -1;

		//if direction = 0 
		if(direction == 0){

			//if hero x bounds > alien X
			if (heroBounds.x > this.getX()) {

				//move right
				xVel = 1;

				//if the hero x bounds < alien x
			} else if (heroBounds.x < this.getX()) {

				//move left
				xVel = -1;

			}

			//if the hero y bounds > alien y
			if (heroBounds.y > this.getY())

				//move down
				yVel = 1;

			//if the hero y bounds < alien y
			else if (heroBounds.y < this.getY())

				//move up
				yVel = -1;
		}

		//move the alien
		bounds.x += xVel;
		bounds.y += yVel;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		//if the imgIndex = 1 less than array length
		if (imgIndex == img.length - 1)

			//set imgindex = 0
			imgIndex = 0;
		else

			//increment
			imgIndex++;

	}

}
