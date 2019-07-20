package game;

import java.awt.image.BufferedImage;

import io.ImageLoader;
import io.InvalidImageLoadException;
import player.Player;
import util.Tickable;

/**
 * Handles the Game object.
 * 
 * @author J2579
 */
public class Game implements Tickable {

	/** The player object. */
	private Player player;
	
	/** The only instance of the Game. */
	public static Game instance;
	
	/** The images the game needs. TODO: Put these in a data structure */
	public BufferedImage grid; //TODO: MAKE. THIS. PRIVATE
	public BufferedImage ball;
	
	
	
	/** 
	 * Returns the instance of Game
	 * 
	 * @return The instance.
	 */
	public static Game getInstance() {
		
		if(instance == null) {
			instance = new Game();
		}
		return instance;
	}
	
	/**
	 * Loads the images of the game
	 * 
	 * @throws RuntimeException If loading the images fails.
	 */
	public void loadImages() {
		
		try {
			grid = ImageLoader.loadImage("assets/grid.png");
			ball = ImageLoader.loadImage("assets/sphere.png");
		}
		catch(InvalidImageLoadException iile) {
			throw new RuntimeException(iile.getMessage());
		}
		
	}
	
	/**
	 * Handle the game tick
	 */
	public void onTick() {
		player.onTick();
		//TODO:
	}
	
	/**
	 * Handle key pressed in the UI
	 * 
	 * @param keyCode The key code of the key press
	 */
	public void handleKeyPressed(int keyCode) {
		player.handleKeyPressed(keyCode);
		//TODO:
	}
	
	/**
	 * Handle key released in the UI
	 * 
	 * @param keyCode The key code of the key release
	 */
	public void handleKeyReleased(int keyCode) {
		player.handleKeyReleased(keyCode);
		//TODO:
	}
	
	/**
	 * Returns the coordinates of the player to draw on the screen
	 */
	public int[] getPlayerDrawPosition() {
		return player.get2DScreenPosition();
	}
	
	/**
	 * Returns the coordinates of the player on the 3D Plane
	 */
	public int[] getPlayerPosition() {
		return player.get3DScreenPosition();
	}
	
	/**
	 * Instantiates Game
	 */
	private Game() {
		player = new Player();
		loadImages();
	}
}
