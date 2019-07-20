package player;

import java.awt.event.KeyEvent;

import physics.PhysicsController;
import physics.PhysicsController.Vector;
import util.Helper;
import util.Tickable;

/**
 * The player
 * 
 * @author J2579
 */
@SuppressWarnings("unused")
public class Player implements Tickable {

	/** Physics */
	private PhysicsController physicsController;
	
	/** Speed Constants */
	private static final double DEFAULT_X_SPEED = 5;
	private static final double DEFAULT_Y_SPEED = 5;
	private static final double DEFAULT_Z_SPEED = 5;
	
	/** Keyboard */
	
	private boolean[] keyboardState = {false, false, false, false, false, false};
	private int[] keyBindings = {-1, -1, -1, -1, -1, -1};
	
	private static final int LEFT_KEY = 0;
	private static final int RIGHT_KEY = 1;
	private static final int FW_KEY = 2;
	private static final int BW_KEY = 3;
	

	private static final int UP_KEY = 4;
	private static final int DOWN_KEY = 5;
	
	private boolean keyboardInputOccurred = false;
	
	/** DEBUG */
	private void setBindings() {
		keyBindings[LEFT_KEY] = KeyEvent.VK_A;
		keyBindings[RIGHT_KEY] = KeyEvent.VK_D;
		keyBindings[FW_KEY] = KeyEvent.VK_W;
		keyBindings[BW_KEY] = KeyEvent.VK_S;
	}
	
	/**
	 * Instantiates player.
	 */
	public Player() {
		//Instantiate here.
		setBindings();
		physicsController = new PhysicsController(55,0,-255,DEFAULT_X_SPEED,DEFAULT_Y_SPEED,DEFAULT_Z_SPEED);
	}

	/**
	 * Handles player tick.
	 */
	public void onTick() {
		
		physicsController.onTick();
		
		if(keyboardInputOccurred)
			changeDirectionOnKeyboardInput();
	
		keyboardInputOccurred = false;
	}

	/**
	 * Sets the movement direction of the physics controller when a change in the keyboard occurs.
	 */
	private void changeDirectionOnKeyboardInput() {
		Vector vector = physicsController.getCurrentVector();

		//Handle X-Axis movement
		//NOTE: X Vector is inverted
		
		//If both left and right, or neither left and right are pressed
		if((keyboardState[LEFT_KEY] && keyboardState[RIGHT_KEY]) || (!keyboardState[LEFT_KEY] && !keyboardState[RIGHT_KEY])) 
			vector.setX(0);
		else if(keyboardState[LEFT_KEY]) //If left is pressed (and therefore, right is not) 
			vector.setX(1);
		else if(keyboardState[RIGHT_KEY]) //If right is pressed (and therefore, left is not)
			vector.setX(-1);
		
		
		//Handle Z-Axis movement
		
		if((keyboardState[FW_KEY] && keyboardState[BW_KEY]) || (!keyboardState[FW_KEY] && !keyboardState[BW_KEY])) 
			vector.setZ(0);
		else if(keyboardState[FW_KEY]) 
			vector.setZ(1);
		else if(keyboardState[BW_KEY])
			vector.setZ(-1);
	}

	
	/**
	 * Handles when a key is pressed in the UI
	 * 
	 * @param keyCode The key value of the key pressed in the UI.
	 */
	public void handleKeyReleased(int keyCode) {
		
		if(keyCode == keyBindings[LEFT_KEY]) {
			keyboardState[LEFT_KEY] = false;
		}
		else if(keyCode == keyBindings[RIGHT_KEY]) {
			keyboardState[RIGHT_KEY] = false;
		}
		else if(keyCode == keyBindings[FW_KEY]) {
			keyboardState[FW_KEY] = false;
		}
		else if(keyCode == keyBindings[BW_KEY]) {
			keyboardState[BW_KEY] = false;
		}
		
		keyboardInputOccurred = true;
	}

	/**
	 * Handles when a key is released in the UI
	 * 
	 * @param keyCode The key value of the key released in the UI.
	 */
	public void handleKeyPressed(int keyCode) {
		
		if(keyCode == keyBindings[LEFT_KEY]) {
			keyboardState[LEFT_KEY] = true;
		}
		else if(keyCode == keyBindings[RIGHT_KEY]) {
			keyboardState[RIGHT_KEY] = true;
		}
		else if(keyCode == keyBindings[FW_KEY]) {
			keyboardState[FW_KEY] = true;
		}
		else if(keyCode == keyBindings[BW_KEY]) {
			keyboardState[BW_KEY] = true;
		}
		
		keyboardInputOccurred = true;
	}

	/**
	 * Returns the screen coordinates to draw the player.
	 * 
	 * @return The screen coordinates to draw the player
	 */
	public int[] get2DScreenPosition() {
		return physicsController.get2DPosition();
	}
	
	/**
	 * Returns the coordinates of the player
	 * 
	 * @return The coordinates of the player
	 */
	public int[] get3DScreenPosition() {
		return physicsController.get3DPosition();
	}
	
	/**
	 * Prints the keyboard state
	 */
	@Deprecated
	private void printKBState() {
		
		String s = "{";
		
		for(int idx = 0; idx < keyboardState.length; ++idx) {
					
			s += keyboardState[idx];
					
			if(idx == keyboardState.length - 1) 
				 s += "}"; 
			else 
				 s += ",";
		}	
				
		System.out.println(s);
	}
}