import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class GameFrame extends JFrame implements ActionListener{
	
	//frame dimensions
	private final int WIDTH = 1024, HEIGHT = 768;
	
	/**Initialize Panels**/
	//create the panels that will be used on the frame
	private MenuPanel pnlMenu = new MenuPanel(); 							
	private SelectionPanel pnlSelection = new SelectionPanel(); 			
	private CreationPanel pnlCreation;				
	private GamePanel pnlGame; 												
	private HUDPanel pnlHUD;
	private GameOverPanel pnlGameOver = new GameOverPanel();		
	
	//pnlMenu objects
	private JButton btnMenuPlay = new JButton("Jouer");						
	
	//pnlSelection objects
	private JButton btnSelectionCreate = new JButton("Cr�er un nouveau h�ros"); 	
	private JButton btnSelectionStart = new JButton("Commencer"); 				
	
	//textFields
	private JTextField txtCreationName = new JTextField();					
	private JTextField[] txtCreationAttire = new JTextField[2]; 			
														
	//buttons for creationPanel
	private JButton btnCreationWear = new JButton("Porter");				
	private JButton btnCreationCreate = new JButton("Cr�er");				
	
	//creates the hero
	private Hero hero;						
	
	/**Game Frame Class Constructor**/
	//calls methods
	public GameFrame(){
		pnlMenuSetup();
		pnlSelectionSetup();
		pnlCreationSetup();
		setup();
	}
	
	/**Menu Panel Setup Method**/
	public void pnlMenuSetup(){
		
		//menu play button setup
		//button properties setup
		btnMenuPlay.setBackground(Color.WHITE);
		btnMenuPlay.setFont(new Font("Arial", Font.BOLD, 15));
		btnMenuPlay.setBounds(WIDTH/2-50,600,100,50); 
		btnMenuPlay.addActionListener(this); 
		btnMenuPlay.setVisible(true); 
		pnlMenu.add(btnMenuPlay); 
	
		//pnlMenu setup
		//adds pnlMenu to the frame
		getContentPane().add(pnlMenu); 
		
	}
	
	/**Selection Panel Setup Method**/
	public void pnlSelectionSetup(){
		
		//selection start button setup
		
		//selectionStart button properties
		btnSelectionStart.setBackground(Color.WHITE);
		btnSelectionStart.setFont(new Font("Arial", Font.BOLD, 25));
		btnSelectionStart.setBounds(55,600,400,100); 
		btnSelectionStart.addActionListener(this); 
		btnSelectionStart.setVisible(true); 
		pnlSelection.add(btnSelectionStart); 
		
		//selection create button setup
		
		//selectionCreate button properties
		btnSelectionCreate.setBackground(Color.WHITE);
		btnSelectionCreate.setFont(new Font("Arial", Font.BOLD, 25));
		btnSelectionCreate.setBounds(545,600,400,100); 
		btnSelectionCreate.addActionListener(this); 
		btnSelectionCreate.setVisible(true); 
		pnlSelection.add(btnSelectionCreate); 
			
	}
	
	/**Creation Panel Setup Method**/
	public void pnlCreationSetup(){
		
		//creates new Hero on creationPanel
		pnlCreation = new CreationPanel(hero); 
		
		//creation name text fields setup
		//textField properties
		txtCreationName = new JTextField(); 
		txtCreationName.setOpaque(false) ;
		txtCreationName.setBorder(null);
		txtCreationName.setBounds(75,96,175,25);
		txtCreationName.setVisible(true); 
		pnlCreation.add(txtCreationName);
		
		//creation attire text fields setup
		
		//for loop to go through the array elements of txtCreationAttire
		for(int i = 0; i < txtCreationAttire.length; i++){ 
		
			//for each element set the properties
			txtCreationAttire[i] = new JTextField(); 
			txtCreationAttire[i].setOpaque(false) ;
			txtCreationAttire[i].setBorder(null);
			txtCreationAttire[i].setBounds(75 + i * 100,170,75,25); 
			txtCreationAttire[i].setVisible(true); 
			pnlCreation.add(txtCreationAttire[i]); 
		}
		
		//creation wear button properties
		btnCreationWear.setBounds(118,212,100,50); 
		btnCreationWear.setBackground(Color.WHITE);
		btnCreationWear.addActionListener(this); 
		btnCreationWear.setVisible(true); 
		pnlCreation.add(btnCreationWear);
		
		//creation create button properties
		btnCreationCreate.setBackground(Color.WHITE);
		btnCreationCreate.setFont(new Font("Arial", Font.BOLD, 25));
		btnCreationCreate.setBounds(835,600,150,100); 
		btnCreationCreate.addActionListener(this); 
		btnCreationCreate.setVisible(true); 
		pnlCreation.add(btnCreationCreate);
		
	}
	
	/**Game Frame Setup Method**/
	public void setup(){
		
		//Used to ensure that the program was running properly
//		System.out.println("test");
		
		//Properties of the frame that we are using
		setTitle("Le Super H�ros");
		setLayout(null); 
		setVisible(true); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setSize(WIDTH,HEIGHT); 
		
	}
	
	/**Action Performed Method**/
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//if menu play button is clicked
		if(e.getSource() == btnMenuPlay){
			
			//create a new hero
			//add selection panel and remove the menu panel
			hero = new Hero(); 
			getContentPane().add(pnlSelection); 
			getContentPane().remove(pnlMenu); 
			
			
		//if selection start button is clicked
		}else if(e.getSource() == btnSelectionStart){
			
			//load the hero from files
			//create new gamePanel and HUD panel with parameters hero
			//add the gamePanel and HUD panel, remove the selection panel
			hero.loadHero(); 
			pnlGame = new GamePanel(hero); 
			pnlHUD = new HUDPanel(hero); 
			getContentPane().add(pnlGame); 
			getContentPane().add(pnlHUD); 
			getContentPane().remove(pnlSelection);
			
			
		//if selection create button is clicked
		}else if(e.getSource() == btnSelectionCreate){
			
			//add the creationPanel, remove the selectionPanel
			getContentPane().add(pnlCreation);
			getContentPane().remove(pnlSelection);
			
		//if creation wear button is clicked
		}else if(e.getSource() == btnCreationWear){
			
			//add attire to the hero
			//if the attire is wrong then display a message
			if(!hero.addAttire(txtCreationAttire[0].getText(), txtCreationAttire[1].getText())) 
				JOptionPane.showOptionDialog(null, 
						"Il y a une erreur d'orthographe! (" + txtCreationAttire[0].getText() + ")", 
				        "Attention", 
				        JOptionPane.OK_CANCEL_OPTION, 
				        JOptionPane.INFORMATION_MESSAGE, 
				        null, 
				        new String[]{"D'accord"}, 
				        "default");
			
			//else add the attire to the hero
			else
				pnlCreation.draw(txtCreationAttire[0].getText(),txtCreationAttire[1].getText());
		
			
		//if creation start button is clicked
		}else if(e.getSource() == btnCreationCreate){
			
			//if the textField is blank display message
			if(txtCreationName.getText().equals("")){
				JOptionPane.showOptionDialog(null, 
				        "Entrez votre nom.", 
				        "Attention", 
				        JOptionPane.OK_CANCEL_OPTION, 
				        JOptionPane.INFORMATION_MESSAGE, 
				        null, 
				        new String[]{"D'accord"}, 
				        "default");
				
				
			//else, set hero name, level and create hero.
			//load the hero and load the button with properties
			//add selectionPanel, remove creationPanel
			}else{
				
				hero.setName(txtCreationName.getText()); 
				hero.setLevel(new Level(1)); 
				hero.createHero(); 
				pnlSelection.loadHero(); 
				pnlSelection.btnSetup(); 
				getContentPane().add(pnlSelection); 
				getContentPane().remove(pnlCreation); 
			}
		}
		
		revalidate(); 
		repaint(); 
		
	}
	
	public void checkGameOver(){
		
		if(hero.getHealth() <= 0){
			pnlGameOver = new GameOverPanel();
			getContentPane().remove(pnlGame);
			getContentPane().remove(pnlHUD);
			getContentPane().add(pnlGameOver);
			
			pnlGameOver.getGameOver().setVisible(true);
			
		}else if (hero.getLevel().getLevel() == 3){
			getContentPane().remove(pnlGame);
			getContentPane().remove(pnlHUD);
			getContentPane().add(pnlGameOver);
			
			pnlGameOver.getCongrats().setVisible(true);
		}
	}
	
}