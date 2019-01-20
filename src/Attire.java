import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Attire {

	//type and colour of the attire
	private String type; 
	private String colour; 
	
	//attire graphic for selection panel 
	//attire graphic in game 
	private BufferedImage[] imgSelection = new BufferedImage[2]; 
	private BufferedImage[] imgGame = new BufferedImage[2]; 
	
	/**Attire Class Constructor**/
	public Attire(String type, String colour){
		setType(type);
		setColour(colour);
		imgSetup();
	}
		
	/**Getters and Setters**/
	public String getType() {
		return type;
	}
		
	//changes string to lower case
	public void setType(String type) {
		this.type = type.toLowerCase(); 
	}
	
	public String getColour() {
		return colour;
	}
	
	//changes string to lower case
	public void setColour(String colour) {
		this.colour = colour.toLowerCase(); 
	}
	
	public BufferedImage[] getImgSelection() {
		return imgSelection;
	}

	public void setImgSelection(BufferedImage[] imgSelection) {
		this.imgSelection = imgSelection;
	}
	
	public BufferedImage[] getImgGame() {
		return imgGame;
	}

	public void setImgGame(BufferedImage[] imgGame) {
		this.imgGame = imgGame;
	}

	/**Graphics Setup Method**/
	public void imgSetup() {
		
		 //loop 2 times (each graphic contain 2 images for animation)
		
		for(int i = 0; i < 2; i++){
			
			//read selection attire image file
			//read game attire image file
			
			try {
				imgSelection[i] = ImageIO.read(new File("Item/Attire/" + type + "/" + colour + "/selection_" + i + ".png")); 
				imgGame[i] = ImageIO.read(new File("Item/Attire/" + type + "/" + colour + "/game_" + i + ".png")); 
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}	
	}
	
}