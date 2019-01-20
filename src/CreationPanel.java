import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class CreationPanel extends JPanel{
	
	//dimensions of panel
	private final int WIDTH = 1024, HEIGHT = 768;
	
	//creates hero
	private Hero hero;
	
	//arraylist of attire and colour
	private ArrayList<String> attire = new ArrayList();
	private ArrayList<String> colour = new ArrayList();
	
	//images of background, graphic, and window
	private ImageIcon background = new ImageIcon("Map/creationBackground.jpg");
	private ImageIcon graphic = new ImageIcon("Map/creationGraphic.png");
	private ImageIcon window = new ImageIcon("Map/window.png");
	
	/**Creation Panel Class Constructor**/
	public CreationPanel(Hero hero){
		this.hero = hero;
		setup();
	}
	
	/**Creation Panel Setup Method**/
	private void setup(){
		
		//panel properties
		setBounds(0,0,WIDTH,HEIGHT);
		setLayout(null); 
		setFocusable(true); 
		setVisible(true); 
	}
	
	
	/** Draw Method **/
	public void draw(String attire, String colour){
		
		//adds attire and colour
		this.attire.add(attire);
		this.colour.add(colour);
		repaint();
	}
	
	/** Paint Method**/
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		
		// draw background image
		g.drawImage(background.getImage(), 0, 0, this);
		g.drawImage(graphic.getImage(), 20, 350, this);
		g.drawImage(window.getImage(), 50, 50, this);
		
		ImageIcon graphic;
		
		//for each element in attire array
		for(int n = 0; n < attire.size(); n++){
			graphic = new ImageIcon("Item/Attire/" + attire.get(n) + "/" + colour.get(n) + "/selection_0.png");
			g.drawImage(graphic.getImage(), 450, 280, this);
			repaint();
		}

	}
}