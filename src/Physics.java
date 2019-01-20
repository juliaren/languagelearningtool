/**
 * Author: Tony Huang
 * 
 * Description: This class contains all the collisions for the different objects in the game
 * 
 * Parameters: object 1, the first object, object1Bounds,position and size of the first object 
 * object2, the second object, object2Bounds, position and size of the second object
 * 
 * Returns: Boolean collision: true or false depending on if the objects intersect
 * 
 */

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public class Physics {

	/**Constructor Method**/
	public Physics() {
	}

	/**Collision Class**/
	public boolean collision(BufferedImage object1, Rectangle object1Bounds, BufferedImage object2,
			Rectangle object2Bounds) {

		//if the 2 objects exist
		if(object1 != null && object2 != null){
			
			//if the 1st object intersects the second object
			if (object1Bounds.intersects(object2Bounds)) {
				
				// get area of intersection
				Area a1 = new Area(object1Bounds);
				Area a2 = new Area(object2Bounds);
				a1.intersect(a2);
				Rectangle bounds = a1.getBounds();				
				
				// if there is an area of intersection
				if (!bounds.isEmpty()) {
					
					//for x bounds
					for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
						
						//for y bounds
						for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
							
							//create instances of object1Pixel and object2Pixel
							int object1Pixel = object1.getRGB(x - object1Bounds.x, y - object1Bounds.y);
							int object2Pixel = object2.getRGB(x - object2Bounds.x, y - object2Bounds.y);
							
							//get the RGB code for the object
							if ((object1Pixel >> 24) != 0x00 && (object2Pixel >> 24) != 0x00){
								
								//intersection is true
								return true;
							}

						}
					}
				}
			}
		}
		
		//intersection is false
		return false;
	}

}
