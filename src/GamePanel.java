import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;

import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener, MouseListener {

	//dimensions of GamePanel
	private final int WIDTH = 1024, HEIGHT = 618;

	//instance of hero
	private Hero hero;

	//arraylist of mobs, bullet, powerup and effect
	private static ArrayList<Powerup> powerup = new ArrayList<Powerup>();
	private ArrayList<Mob> mobs = new ArrayList<Mob>();
	private ArrayList<Projectile> bullet = new ArrayList<Projectile>();
	private ArrayList<Effect> effect = new ArrayList<Effect>();

	//schedules powerup class
	private SchedulePowerup schedulePowerup;

	//change from bullet to missile
	private boolean missile = false;

	//change speed of hero
	private boolean speeding = false;

	//make hero invincible
	private boolean invincible = false;

	//instance of physics
	private Physics physics = new Physics();

	//check for game over
	private boolean gameOver = false;

	//timer for game
	Timer tmrGame = new Timer(10, this);

	//sets the background image
	private ImageIcon background = new ImageIcon("Map/gameBackground.png");


	/** Constructor Method **/
	public GamePanel(Hero hero) {

		//calls methods
		playSound("background_game");
		setHero(hero);
		setAlien();
		setup();

		//starts the timer
		tmrGame.start();

		//start the cycle of adding powerups
		schedulePowerup = new SchedulePowerup(powerup);

	}

	/**Setup Method **/
	private void setup() {

		//properties of the pane;
		setBounds(0, 0, WIDTH, HEIGHT);
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		setFocusTraversalKeysEnabled(false);
		setVisible(true);

		//allows the use of cursor to aim
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("Map/cursor.png").getImage(),
				new Point(0, 0), "custom cursor"));

	}

	/**Setters and Getters**/

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public void setAlien() {
		String answer = hero.getLevel().getCurrentQAndA()[1];
		for (int i = 0; i < answer.length(); i++)
			mobs.add(new Mob(Character.toString(answer.charAt(i)), 200, 0));
	}

	/**Paint Method**/
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// draw background image
		g.drawImage(background.getImage(), 0, 0, this);

		// draw hero
		g.drawImage(hero.getImgGame(), hero.getBounds().x, hero.getBounds().y, this);

		// draw mobs
		for (int i = 0; i < mobs.size(); i++) {
			g.drawImage(mobs.get(i).getImg(), (int) mobs.get(i).getX(), (int) mobs.get(i).getY(), this);
		}

		// draw bullets
		for (int i = 0; i < bullet.size(); i++) {
			g.drawImage(bullet.get(i).getImg(), (int) bullet.get(i).getX(), (int) bullet.get(i).getY(), this);

			// if the bullet goes out of the panel, remove the bullet
			if (bullet.get(i).getX() < 0 || bullet.get(i).getX() > 1280 || bullet.get(i).getY() < 0
					|| bullet.get(i).getY() > 800)
				bullet.remove(i);
		}

		// draw effects
		for (int i = 0; i < effect.size(); i++) {
			g.drawImage(effect.get(i).getImgEffect(), effect.get(i).getX()-90, effect.get(i).getY()-80, this);
			if (effect.get(i).getImgIndex() == 7)
				effect.remove(i);
		}

		for(int i = 0; i < powerup.size(); i++) 
			g.drawImage(powerup.get(i).getGraphic(), powerup.get(i).getX(), powerup.get(i).getY(), this);


	}

	@Override
	public void keyPressed(KeyEvent e) {

		//if the user presses W
		if (e.getKeyCode() == KeyEvent.VK_W) {

			//move up

			if(speeding == false){

				hero.setXVel(0);
				hero.setYVel(-3);

			}else{

				hero.setXVel(0);
				hero.setYVel(-5);

			}
		}

		//if the user presses S
		if (e.getKeyCode() == KeyEvent.VK_S) {

			//move down
			if(speeding == false){

				hero.setXVel(0);
				hero.setYVel(3);

			}else{

				hero.setXVel(0);
				hero.setYVel(5);

			}
		}

		//if the user presses A
		if (e.getKeyCode() == KeyEvent.VK_A) {

			//move left
			if(speeding == false){

				hero.setXVel(-3);
				hero.setYVel(0);

			}else{

				hero.setXVel(-5);
				hero.setYVel(0);

			}
		}

		//if the user presses D
		if (e.getKeyCode() == KeyEvent.VK_D) {

			//move right
			if(speeding == false){

				hero.setXVel(3);
				hero.setYVel(0);

			}else{

				hero.setXVel(5);
				hero.setYVel(0);

			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {

		//if the user left clicks
		if (e.getButton() == MouseEvent.BUTTON1) {

			//create and add new bullet
			if(missile == false)
				bullet.add(new Projectile("bullet"));
			else
				bullet.add(new Projectile("missile"));

			//fire the bullet towards the location of the cursor
			//play the sound effect
			hero.fire(bullet.get(bullet.size() - 1), MouseInfo.getPointerInfo().getLocation().getX(),
					MouseInfo.getPointerInfo().getLocation().getY(), this);
			playSound("bullet_fired");

			//if the user right clicks
		} else if (e.getButton() == MouseEvent.BUTTON2) {

			//create and add new bullet
			bullet.add(new Projectile("bullet"));

			//fire the bullet towards the location of the cursor
			//play the sound effect
			hero.fire(bullet.get(bullet.size() - 1), MouseInfo.getPointerInfo().getLocation().getX(),
					MouseInfo.getPointerInfo().getLocation().getY(), this);
			playSound("bullet_fired");

			//if the user clicks the scroll wheel
		} else if (e.getButton() == MouseEvent.BUTTON3) {

			//create and add new bullet
			bullet.add(new Projectile("bullet"));

			//fire the bullet towards the location of the cursor
			//play the sound effect
			hero.fire(bullet.get(bullet.size() - 1), MouseInfo.getPointerInfo().getLocation().getX(),
					MouseInfo.getPointerInfo().getLocation().getY(), this);
			playSound("bullet_fired");
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		//if the mobs are all gone
		if(mobs.size() == 0){

			//stop the gameTimer
			tmrGame.stop();

			//show message, that the user is wrong
			int reply1 = JOptionPane.showOptionDialog(null, 
					"Votre r�ponse est incorrecte.", 
					"Attention", 
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, 
					null, 
					new String[]{"Red�marrer","Sortie"}, 
					"default");

			//if the user clicks yes
			if (reply1 == JOptionPane.YES_OPTION) {


				//create new instances of mobs, bullets, and effects
				mobs = new ArrayList<Mob>();
				bullet = new ArrayList<Projectile>();
				effect = new ArrayList<Effect>();

				//playSound("background_game");

				//set the hero level, health and bounds
				//sets the alien
				hero.setLevel(new Level(hero.getLevel().getLevel()));
				hero.setAlive(true);
				hero.setHealth(100);
				hero.setBounds(new Rectangle(500, 500, 41, 71));
				setAlien();

				//restarts the gameTimer
				tmrGame.restart();

				//if the user chooses no_option
			} else if (reply1 == JOptionPane.NO_OPTION) {

				//exit
				System.exit(0);
			}
		}


		//for each bullet element
		for (int i = 0; i < bullet.size(); i++)

			//move the bullet
			bullet.get(i).move();

		//for each mob element
		for (int i = 0; i < mobs.size(); i++) {

			//move the mob 
			mobs.get(i).movementAI(hero.getBounds());
		}

		//if the hero is alive
		if (hero.isAlive()) {

			//move the hero
			hero.move();

			//else
		} else {

			//stop the game Timer
			tmrGame.stop();

			//show message dialog, user is dead
			int reply1 = JOptionPane.showOptionDialog(null, 
					"Tu es mort.", 
					"Attention", 
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, 
					null, 
					new String[]{"Red�marrer","Sortie"}, // this is the array
					"default");

			//if the user chooses yes
			if (reply1 == JOptionPane.YES_OPTION) {

				//new instances of mobs, bullet, effect
				mobs = new ArrayList<Mob>();
				bullet = new ArrayList<Projectile>();
				effect = new ArrayList<Effect>();

				//playSound("background_game");

				//set the hero level, health, bounds
				//create aliens
				hero.setLevel(new Level(hero.getLevel().getLevel()));
				hero.setAlive(true);
				hero.setHealth(100);
				hero.setBounds(new Rectangle(500, 500, 41, 71));
				setAlien();

				//restart the gameTimer
				tmrGame.restart();

				//else
			} else if (reply1 == JOptionPane.NO_OPTION) {

				//exit
				System.exit(0);
			}



		}

		//buffered image of HeroGraphic
		//herobounds is a rectangle object
		BufferedImage heroGraphic = hero.getImgGame();
		Rectangle heroBounds = hero.getBounds();

		//for each mob element
		for (int a = 0; a < mobs.size(); a++) {

			//get the Image for the alienGraphic 
			//alienbounds is a rectangle object
			BufferedImage alienGraphic = mobs.get(a).getImg();
			Rectangle alienBounds = mobs.get(a).getCoordinates();

			//if the alien and the hero collide
			if (physics.collision(heroGraphic, heroBounds, alienGraphic, alienBounds)) {

				//if hero is NOT invincible
				//reduce hero health by 1
				//console print hero health
				if(invincible == false){

					hero.setHealth(hero.getHealth() - 1);
					System.out.println(hero.getHealth());

				}

			}

			//for each bullet element
			for (int b = 0; b < bullet.size(); b++) {


				//get the bulletGraphic image
				//bulletbounds are rectangle objects
				//bullet properties
				BufferedImage bulletGraphic = bullet.get(b).getImg();
				Rectangle bulletBounds = new Rectangle();
				bulletBounds.setBounds((int) bullet.get(b).getX(), (int) bullet.get(b).getY(),
						(int) bullet.get(b).getWidth(), (int) bullet.get(b).getHeight());

				//if the bullet collides with an alien
				if (physics.collision(bulletGraphic, bulletBounds, alienGraphic, alienBounds)) {

					//the answer will be recorded based on the mob hit. 
					hero.getLevel().answerQuestion(mobs.get(a).getType());

					//if the level is complete
					if (hero.getLevel().isComplete) {

						//increase level by 1, load the level, create new mobs, 
						//save progress
						hero.getLevel().setLevel(hero.getLevel().getLevel() + 1);
						hero.getLevel().loadLevel();
						setAlien();
						hero.save();
					}

					//if the hero reaches level 3
					if (hero.getLevel().getLevel() == 3) {

						//show message dialog, stop timer
						JOptionPane.showMessageDialog(this, "Tu as gagn�!");
						tmrGame.stop();
					}

					//if hero's level question is complete
					if (hero.getLevel().questionComplete) {

						//create more mobs, set questionComplete false
						//continue to answer questions 
						setAlien();
						hero.getLevel().questionComplete = false;
					}

					//play the bullet hit sound
					playSound("bullet_hit");

					//add new explosion effect at the mobs location
					effect.add(new Effect("explosion", (int) mobs.get(a).getX(), (int) mobs.get(a).getY()));


					//remove the mobs
					//remove the bullets;
					mobs.remove(a);
					bullet.remove(b);

				}
			}

		}

		for(int i = 0; i < powerup.size(); i++){

			BufferedImage powerupGraphic = powerup.get(i).getGraphic();
			Rectangle powerupBounds = powerup.get(i).getBounds();

			//if hero ate a powerup
			if(physics.collision(heroGraphic, heroBounds, powerupGraphic, powerupBounds) == true){

				//if powerup was heart, increase health
				if (powerup.get(i).getType() == 0){

					if(hero.getHealth() < 100)
						hero.setHealth(hero.getHealth()+ 5);

					//if power up was wing, increase speed
				}else if (powerup.get(i).getType() == 1){

					if(speeding == false)
						speeding = true;

					//if powerup was missile, change bullet to missile
				}else if (powerup.get(i).getType() == 2){

					if(missile == false)
						missile = true;

					//if powerup was shield, make hero invincible
				}else if (powerup.get(i).getType() == 3){

					if(invincible == false)
						invincible = true;

					System.out.printf("invincible:" + invincible);

				}

				powerup.remove(i);
			}
		}

		revalidate();
		repaint();

	}

	/**Sound Method**/
	public void playSound(String type) {

		try {

			//creates new soundFile
			File soundFile = new File("Sound/" + type + ".wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

			// Get a sound clip resource.
			Clip clip = AudioSystem.getClip();

			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			clip.start();

			//if the type of music contains background
			if (type.contains("background"))

				//loop the clip
				clip.loop(1000000000);


			//exceptions
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

}
