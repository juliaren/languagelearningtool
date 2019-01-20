/**
 * Author: Tony Huang
 * 
 * Description: The panel which the user is able to choose a hero that he or she has previously made
 * 
 * Parameters: Graphics g, receives the graphics
 * 
 * Return:
 * 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class SelectionPanel extends JPanel implements ActionListener{
	
	//frame dimensions 
	private final int WIDTH = 1024, HEIGHT = 768;
	
	//instance of background image
	private ImageIcon backgroundImage;
	
	//array list of selection buttons
	private ArrayList<JButton> btnSelect = new ArrayList<JButton>();
	
	//array list of hero info
	private ArrayList<String[]> strHeroInfo = new ArrayList<String[]>();
	
	//array list of graphics
	public ArrayList<ImageIcon> graphics = new ArrayList<ImageIcon>();
	
	
	/**Constructor Method**/
	public SelectionPanel(){
		
		//calls classes
		loadHero();
		btnSetup();
		setup();
	}
		
	/**Setup Method**/
	private void setup(){
	
		//SelectionPanel properties
		setBackground(new Color(145,109,131));
		setLayout(null);
		setBounds(0,0,WIDTH,HEIGHT);
		setFocusable(true);
		setVisible(true);
	
		//set new background image
		backgroundImage = new ImageIcon("Map/selection_background.gif");
		
	}
	
//	/**Button Setup Method**/
	public void btnSetup(){
		
		//clear the graphics, and button
		graphics.clear();
		btnSelect.clear();
		
		//for each element in hero info
		for(int p = 0; p < strHeroInfo.size(); p++){
			
			//add graphic from character folder
			graphics.add(new ImageIcon("Character/" + p + "selection_"+ 0 + ".png"));
			
			//add new Jbutton
			btnSelect.add(new JButton());
		
			//button properties
			btnSelect.get(p).addActionListener(this);
			btnSelect.get(p).setVisible(true);
			btnSelect.get(p).setIcon(graphics.get(p));
			btnSelect.get(p).setOpaque(false);
			btnSelect.get(p).setContentAreaFilled(false);
			btnSelect.get(p).setBorder(null);;
			add(btnSelect.get(p));
			
			//if p = 0
			if(p == 0)
				
				//set button locations
				btnSelect.get(p).setBounds(100,326,45,70);
			else if(p == 1)
				btnSelect.get(p).setBounds(150,326,45,70);
			else if(p == 2)
				btnSelect.get(p).setBounds(200,326,45,70);
			else if(p == 3)
				btnSelect.get(p).setBounds(250,326,45,70);
			else if(p == 4)
				btnSelect.get(p).setBounds(300,326,45,70);
		}
	}
	
	/**Load Hero Method**/
	public void loadHero(){
		
		//clear the hero info
		strHeroInfo.clear();
		
		try {
	
			//read heroInfo.txt
			Scanner inputFile = new Scanner(new File("Data/heroInfo.txt")).useDelimiter(",");
			
			//set numhero = int value of next token
			int numHero = Integer.valueOf(inputFile.next());
			
			
			//while it has token
			while(inputFile.hasNext()){
				
				//for each numhero
				for(int p = 0; p < numHero; p++){
					
					//add new heroInfo max 3
					strHeroInfo.add(new String[3]);
					
					//for each heroinfo
					for(int n = 0; n < 3; n++)
						
						//next token
						strHeroInfo.get(p)[n] = inputFile.next();
				}
			}
					
			//catch filenotfound
		} catch (FileNotFoundException error) {

			//print error
			System.err.println("File not found - check the file name");

		}
		
		
	}

	/**Paint Method**/
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		//draw background image
		g.drawImage(backgroundImage.getImage(), 0, 0, this);
		
	}

	/**ActionPerformed**/
	@Override
	public void actionPerformed(ActionEvent e) {
	
		//concatenate heroinfo
		String exportData = strHeroInfo.get(0)[0].concat("," + strHeroInfo.get(0)[1] + "," + strHeroInfo.get(0)[2]);
	
		//for heroinfo size
		for(int p = 0; p < strHeroInfo.size(); p++){
			
			//if action from btnselect
			if(e.getSource() == btnSelect.get(p)){
				
				//for btnSelect size
				for(int n = 0 ; n < btnSelect.size(); n++)
					
					//set border null
					btnSelect.get(n).setBorder(null);
				
				//set new border, blue
				btnSelect.get(p).setBorder(new LineBorder(Color.BLUE));
				
				//concatenate heroinfo
				exportData = strHeroInfo.get(p)[0].concat("," + strHeroInfo.get(p)[1] + "," + strHeroInfo.get(p)[2]);
				
				//print exportData
				System.out.println(exportData);
			}
		}
		
		//print exportData
		System.out.println(exportData);
		
		//bufferedWriter
		BufferedWriter output= null;
		
		try {
			
			//write into hero.txt
		    output = new BufferedWriter(new FileWriter("Data/hero.txt"));
		    
		    //write exportData
		    output.write(exportData);  
		    
		    //catch IOException
		}catch (IOException error){
			
			//print error 
			System.err.println("File not found - check the file name");
		} finally {
            try {
                // Close the writer regardless of what happens...
                output.close();
                
                //catch error
            } catch (Exception error) {}
        }
		
		
	}

}
