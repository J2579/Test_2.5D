package io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Loads an image from a file name.
 * 
 * @author J2579
 */
public class ImageLoader {

	/**
	 * 
	 * @param fileName The filename of the object.
	 * @throws InvalidImageLoadException If loading of the image failed.
	 * @return The image loaded from the file.
	 */
	public static BufferedImage loadImage(String filename) throws InvalidImageLoadException {
		
		BufferedImage loadedImage;
		
		try {
			loadedImage = ImageIO.read(new File(filename));
		}
		catch(IOException e) {
			throw new InvalidImageLoadException("Unable to load file: " + filename);
		}
		
		return loadedImage;
	}
	
}
