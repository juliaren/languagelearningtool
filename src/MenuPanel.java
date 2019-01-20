/**
 * Author: Tony Haung
 * 
 * Description: This is the first panel that the user encounters, this contains the start button for the player to advance into the selectionPanel
 * 
 * Parameters: Graphics g, Receives the graphics that the coder wants to use
 * 
 * **/


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{
	
	//panel dimensions 
	private final int WIDTH = 1024, HEIGHT = 768;
	
	//set the background
	private ImageIcon background;
	
	
	/**Constructor Method**/
	public MenuPanel(){
		
		//calls methods
		setup();
	}
	
	/**Setup Method**/
	private void setup(){
		
		//MenuPanel properties
		setLayout(null);
		setBounds(0,0,WIDTH,HEIGHT);
		setBackground(Color.BLACK);
		setFocusable(true);
		setVisible(true);
		
		//set the background image
		background = new ImageIcon("Map/background.gif");
	
	
	}
	
	/**Paint Method**/
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		//draw background image, set properties
		g.drawImage(background.getImage(), -135, -50, this);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.drawString("LE SUPER HÉROS", 270, 350);
	}

}
