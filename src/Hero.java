import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Hero implements ActionListener {

	//hero number, name, level
	private int number = 0; 
	private String name;
	private Level level;

	//whether the hero is alive, health of hero
	private boolean isAlive = true; 
	private int health = 100; 

	//hero's position, velocity
	private Rectangle bounds = new Rectangle(); 
	private int xVel = 0, yVel = 0; 

	//hero's clothing, graphic, selectionGraphic
	private Attire[] attire = new Attire[5]; 
	private BufferedImage[] imgGame = new BufferedImage[2]; 
	private BufferedImage[] imgSelection = new BufferedImage[2]; 

	//index of image 
	//imgTimer
	private int imgIndex = 0;
	private Timer imgTimer = new Timer(300, this);


	/**Constructor Method**/
	public Hero() {

		//start timer, setBounds of hero
		imgTimer.start();
		setBounds(new Rectangle(512, 500, 41, 71));
	}

	/**Getters and Setters**/
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if (isAlive && health >= 0)
			this.health = health;
		else
			isAlive = false;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public int getXVel() {
		return xVel;
	}

	public void setXVel(int xVel) {
		this.xVel = xVel;
	}

	public int getYVel() {
		return yVel;
	}

	public void setYVel(int yVel) {
		this.yVel = yVel;
	}

	public Attire[] getAttire() {
		return attire;
	}

	public void setAttire(Attire[] attire) {
		this.attire = attire;
	}

	public BufferedImage getImgGame() {
		return imgGame[imgIndex];
	}

	public void setImgGame(BufferedImage imgGame) {
		this.imgGame[imgIndex] = imgGame;
	}

	public BufferedImage getImgSelection() {
		return imgSelection[imgIndex];
	}

	public void setImgSelection(BufferedImage imgSelection) {
		this.imgSelection[imgIndex] = imgSelection;
	}

	/**Adding Clothing to Hero**/
	public boolean addAttire(String type, String colour) {

		try {

			//reads attireInfo.txt
			Scanner inputFile = new Scanner(new File("Data/attireInfo.txt")).useDelimiter(",");

			//while the input has another line
			while (inputFile.hasNext()) {

				//if the type equals the next attire
				if (type.equals(inputFile.next())) {

					//create new attire
					attire[Integer.valueOf(inputFile.next())] = new Attire(type, colour);
					return true;

					//else
				} else {

					//read nextline
					inputFile.nextLine();
				}
			}

			//catch filenotfound
		} catch (FileNotFoundException error) {

			//print out error 
			System.err.println("File not found - check the file name");
		}
		return false;
	}

	/**Load The Hero from txt File**/
	public void loadHero() {

		try {

			//read hero.txt file
			Scanner inputFile = new Scanner(new File("Data/hero.txt")).useDelimiter(",");

			//writes number, name and level
			number = inputFile.nextInt();
			name = inputFile.next();
			level = new Level(inputFile.nextInt());

			//catch filenotfound
		} catch (FileNotFoundException e) {

			//print out error
			System.err.println("File not found - check the file name");

		}

		//reads hero.txt
		try {

			//reads the in game images
			imgGame[0] = ImageIO.read(new File("Character/" + number + "game_" + 0 + ".png"));
			imgGame[1] = ImageIO.read(new File("Character/" + number + "game_" + 1 + ".png"));

			//reads the selection panel images
			imgSelection[0] = ImageIO.read(new File("Character/" + number + "selection_" + 0 + ".png"));
			imgSelection[1] = ImageIO.read(new File("Character/" + number + "selection_" + 1 + ".png"));

			//catch IOexception
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**Create the Hero**/
	public void createHero() {

		try {

			//read heroInfo.txt
			String inputFile = new Scanner(new File("Data/heroInfo.txt")).next();

			//set number as object value at 0 - 1
			number = Integer.valueOf(inputFile.substring(0, 1));

			//output file will concatenate the int value
			String outputFile = String.valueOf(number + 1).concat(inputFile.substring(1)
					.concat("," + Integer.toString(number) + "," + name + "," + Integer.toString(level.getLevel())));

			//print level, name
			System.out.println(level.getLevel());
			System.out.println(name);
			BufferedWriter output = null;


			try {

				//write and update the heroInfo.txt
				output = new BufferedWriter(new FileWriter("Data/heroInfo.txt"));
				output.write(outputFile);

				//catch IOexception
			} catch (IOException error) {

				//error print
				System.err.println("File not found - check the file name");

				//finally
			} finally {
				try {

					// Close the writer regardless of what happens...
					output.close();
				} catch (Exception error) {
				}
			}

			//catch filenotfound
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//bufferedimages
		BufferedImage imgSelection;
		BufferedImage imgGame;

		//2 imgs for animation
		for (int i = 0; i < 2; i++) {

			//sets the selection graphic
			imgSelection = new BufferedImage(45, 70, BufferedImage.TYPE_INT_ARGB);
			Graphics gSelection = imgSelection.getGraphics();

			//sets the game graphic
			imgGame = new BufferedImage(41, 71, BufferedImage.TYPE_INT_ARGB);
			Graphics gGame = imgGame.getGraphics();

			//for the amount of attire
			for (int a = 0; a < attire.length; a++) {

				//if there is attire
				if (attire[a] != null) {

					//draw the attire onto hero
					gSelection.drawImage(attire[a].getImgSelection()[i], 0, 0, null);
					gGame.drawImage(attire[a].getImgGame()[i], 0, 0, null);
				}
			}

			try {

				//write the game image, selection image
				ImageIO.write(imgGame, "PNG", new File("Character/" + number + "game_" + i + ".png"));
				ImageIO.write(imgSelection, "PNG", new File("Character/" + number + "selection_" + i + ".png"));

				//catch IOexcetion
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	/**Save Hero Information into txt File**/
	public void save() {

		try {

			//save into heroInfo.txt
			Scanner inputFile = new Scanner(new File("Data/heroInfo.txt")).useDelimiter(",");

			//input ArrayList
			ArrayList<String> input = new ArrayList<String>();


			while(inputFile.hasNext())
				input.add(inputFile.next());

			System.out.println(input.toString());

			String before = "";

			for(int n = 1; n <= input.indexOf(name) - 2; n++)
				before = before + "," + input.get(n);

			String after = "";
			for(int n = input.indexOf(name) + 2; n < input.size(); n++)
				after = after + "," + input.get(n);


			String outputFile = input.get(0) + before + "," + Integer.toString(number) + "," + name + "," + Integer.toString(level.getLevel()) + after;

			BufferedWriter output = null;

			try {
				output = new BufferedWriter(new FileWriter("Data/heroInfo.txt"));
				output.write(outputFile);
			} catch (IOException error) {
				System.err.println("File not found - check the file name");
			} finally {
				try {

					// Close the writer regardless of what happens...
					output.close();
				} catch (Exception error) {
				}
			}

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**Move the Hero**/
	public void move() {

		//if x is < 0 or > 960
		if (bounds.x < 0 || bounds.x > 960)

			//change direction
			xVel *= -1;

		//if y < 0 or > 550
		else if (bounds.y < 0 || bounds.y > 550)

			//change direction
			yVel *= -1;

		//increase x by xVel
		//increase y by yVel
		bounds.x += xVel;
		bounds.y += yVel;
	}

	/**Fire a Bullet**/
	public void fire(Projectile bullet, double mouseX, double mouseY, ImageObserver o) {

		//get the image of the bullet
		BufferedImage bufferedImage = bullet.getImg();

		//get the image properties
		BufferedImage image = new BufferedImage(bullet.getImg().getHeight(), bullet.getImg().getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		//get the graphic
		Graphics2D g2D = (Graphics2D) image.getGraphics();

		//rotate the image based on where the mouse is
		g2D.rotate(-1 * Math.atan2(mouseX - bounds.getX(), mouseY - bounds.getY()), bufferedImage.getWidth() / 2,
				bufferedImage.getHeight() / 2);

		//draw the bullet
		g2D.drawImage(bufferedImage, 0, 0, o);

		//set the image and location
		bullet.setImg(image);
		bullet.setX(bounds.x);
		bullet.setY(bounds.y);

		//set the bullet speed
		double bulletVelocity = 3.0; 


		// mouseX/Y = current x/y location of the mouse
		// originX/Y = x/y location of where the bullet is being shot from
		double angle = Math.atan2(mouseX - bounds.getX(), mouseY - bounds.getY());
		double xVelocity = (bulletVelocity) * Math.sin(angle);
		double yVelocity = (bulletVelocity) * Math.cos(angle);

		//set the xVel and yVel
		bullet.setxVel(xVelocity);
		bullet.setyVel(yVelocity);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		//if imgIndex = in game img length - 1
		if (imgIndex == imgGame.length - 1)

			//set img index = 0
			imgIndex = 0;
		else
			//imgindex increase by 1
			imgIndex++;

	}

}