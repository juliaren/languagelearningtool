import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class HUDPanel extends JPanel implements ActionListener {
	
	//dimensions of panel
	private final int WIDTH = 1024, HEIGHT = 150;
	
	//new timer
	Timer timer = new Timer(5, this);

	//instance of hero and graphic
	private Hero hero;
	private BufferedImage graphic;

	
	/**Constructor Method**/
	public HUDPanel(Hero hero) {
		
		//calls methods
		setHero(hero);
		setGraphic(hero.getImgSelection());
		setup();
	}

	
	/**Setters and Getters**/
	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public void setGraphic(BufferedImage graphic) {
		this.graphic = graphic;
	}

	/**Setup Method**/
	private void setup() {
		
		//properties of panel
		setLayout(null);
		setBounds(0, 619, 1280, 200);
		setBackground(Color.BLACK);
		setVisible(true);
		
		//start timer
		timer.start();
	}

	/**Paint Method**/
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		
		//draw the graphic
		g.drawImage(graphic, 10, 5, this);

		//set color to white
		g.setColor(Color.WHITE);

		//display hero name and level
		g.drawString(hero.getName(), 70, 30);
		g.drawString("Niveau: " + Integer.toString(hero.getLevel().getLevel()), 70, 45);

		//draw rectangle
		//set color to black, and fill 
		g.drawRect(9, 59, 201, 21);
		g.setColor(Color.BLACK);
		g.fillRect(10, 60, 200, 20);
		
		//set color to red
		//fill the health bar red 
		g.setColor(Color.RED);
		g.fillRect(10, 60, hero.getHealth() * 2, 20);
		
		//set color white
		//display hero health
		g.setColor(Color.WHITE);
		g.drawString(Integer.toString(hero.getHealth()), 100, 115);
		
		//set font for question, display the questions
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("La Question: " + hero.getLevel().getCurrentQAndA()[0], 300,40 );
		g.drawString("La Solution: " + hero.getLevel().getPlayerAnswer(), 300, 70);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

}
