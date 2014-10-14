package util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * Stores images when used for later use
 * @author Oliver Greenaway
 *
 */
public class ImageLibrary {

	private static Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();

	/**
	 * Attempts to retrieve the image specified, if it is yet to be loaded then it is loaded and returned.
	 * If the file is invalid then a 1x1 image is returned.
	 * @param filename The File to be retrieved
	 * @return An Image from the file
	 */
	public static BufferedImage get(String filename){
		//If the image already exists then return
		if(images.containsKey(filename)){
			return images.get(filename);
		//Else load the image and return
		}else{
			try{
				//Loads the image
				URL file = ClassLoader.getSystemResource("Assets/"+filename);
				if(file == null){
					System.out.println(filename+" failed to load");
					return new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
				}
				BufferedImage newImage = ImageIO.read(file);
				//Stores the image
				images.put(filename, newImage);
				return newImage;
			}catch(IOException e){
				//Display an error and return a 1x1 Image
				System.err.println("Error loading file \""+filename+"\"\n");
				return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
			}
		}
	}

	/**
	 * Loads the given Image to the program
	 * @param filename The File to be loaded
	 */
	public static void load(String filename){
		try{
			//Load the image
			BufferedImage newImage = ImageIO.read(ClassLoader.getSystemResource("Assets/"+filename));
			//Store the image
			images.put(filename, newImage);
		}catch(IOException e){
			//Display an error if file is invalid
			System.err.println("Error loading file \""+filename+"\"\n");
		}
	}


}
