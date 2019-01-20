import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Effect implements ActionListener{
	
	//type of effect
	private String type;
	
	//x,y coordinates
	private int x, y;
	
	//Array of images for effect, 8 frames
	private BufferedImage[] imgEffect = new BufferedImage[8];
	
	//index of image
	private int imgIndex = 0;
	
	//timer for the animation
	private Timer tmrAnimation = new Timer(50,this);
	
	
	/**Constructor Method**/
	public Effect(String type, int x, int y){
		
		//calls methods
		setX(x);
		setY(y);
		setType(type);
		loadEffect();
	}

	/**Setters and Getters**/
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getImgIndex() {
		return imgIndex;
	}

	public void setImgIndex(int imgIndex) {
		this.imgIndex = imgIndex;
	}

	public BufferedImage getImgEffect() {
		tmrAnimation.start();
		return imgEffect[imgIndex];
	}

	public void setImgEffect(BufferedImage[] imgEffect) {
		this.imgEffect = imgEffect;
	}
	
	
	/**Load Effect Method**/
	private void loadEffect(){
		
		//for each frame for the animation
		for(int i = 0; i<imgEffect.length;i++){
			
			//read the image file
			try{
				imgEffect[i] = ImageIO.read(new File("Item/Projectile/effect/" + i +".png"));
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	/**ActionPerformed**/
	@Override
	public void actionPerformed(ActionEvent arg0) {
	
		//if the imgindex does not equal 1 less than length of imgEffect array
		if(imgIndex != imgEffect.length-1)
			
			//increase by 1
			imgIndex++;

	}
	
}
