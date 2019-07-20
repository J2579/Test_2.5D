package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

import game.Game;

/**
* This class acts as a driver for the 2D Game
*
* @author J2579
* @version 1.1.0
* @since 2019-06-23
*/
@SuppressWarnings("serial") //No need to create Serial UID
public class GUI extends JFrame implements ActionListener, KeyListener {
	
	public static boolean debug;
	
	private GameWindow gameWindow;
	private Timer tick;
	private Game game;
	
	private JButton quit;

	public static final int CANVAS_HEIGHT = 600;
	public static final int CANVAS_WIDTH = 600;
	
	/**
	 * Initializes the GUI
	 * 
	 * @param args Unused
	 */
	public static void main(String[] args) {
		GUI gui = new GUI();
		
		if(args.length != 0 && args[0].equals("DEBUG")) 
			debug = true;
		else
			debug = false;
		
		gui.initialize();
	}
	
	/**
	* This method acts to initialize all of the relevant components
	* of the Game Window.
	*/
	public void initialize() {
		
		//Set up the window
		setTitle("2.5D Game Test");
		setSize(CANVAS_WIDTH, CANVAS_HEIGHT + 100);
		setLocation(50, 50);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setFocusable(true);
		setAlwaysOnTop(true);
		addKeyListener(this);
		
		//Create the GFX window.
		gameWindow = new GameWindow(CANVAS_WIDTH,CANVAS_HEIGHT); 
		gameWindow.setSize(CANVAS_WIDTH,CANVAS_HEIGHT);
		gameWindow.setVisible(true);
		
		//Create the quit button
		quit = new JButton("Quit");
		quit.addActionListener(this);

		//Add the components to the frame
		Container frame = getContentPane();
		frame.add(gameWindow, BorderLayout.CENTER);
		frame.add(quit, BorderLayout.SOUTH);
		
		

		//Init Game Components
		game = Game.getInstance();
		
		//Make the frame visible.
		setVisible(true);
				
		//Create the GFX buffer
		gameWindow.createAndSetBuffer();
		
		//Create and start the game clock (0.017s timer = race condition)
		
		tick = new Timer(17,this); //1 Tick / 0.017 Seconds ~ 60 Ticks / 1 Second 
		tick.setRepeats(true);
		tick.start();
		
		
	}
	
	/**
	* Call whenever an action is performed on a component
	*
	* @param event An event that occurs within the GUI
	*/
		
	public void actionPerformed(ActionEvent ae) {

		//On every 'tick' of the internal timer:
		if(ae.getSource() == tick) {	
			game.onTick();
			gameWindow.update();
			
			if(GUI.debug) {
				System.out.println(game.getPlayerDrawPosition()[0] + "," + game.getPlayerDrawPosition()[1]);
				System.out.println(game.getPlayerPosition()[0] + "," + game.getPlayerPosition()[1] + "," + game.getPlayerPosition()[2]);
		
			}
		}
		else if(ae.getSource() == quit) {
			System.exit(0);
		}
		
		requestFocus();
	}	
		
	
	/**
	* Activates when a key is pressed while
	* the window is focused.
	*
	* @param KeyEvent e Keyboard key pressed.
	*/
	public void keyPressed(KeyEvent e) {			
		game.handleKeyPressed(e.getKeyCode());
	}
		
	/**
	* Activates when a key is released while
	* the window is focused.
	*
	* @param KeyEvent e Keyboard key released.
	*/
	public void keyReleased(KeyEvent e) {
		game.handleKeyReleased(e.getKeyCode());
	}
	
	/**
	* Unused (Required by Interface)
	* 
	* @param e Unused.
	*/
		
	public void keyTyped(KeyEvent e) {}
	
	/**
	* Simple implementation of double-buffered canvas. Edit 
	* contents with GameWindow.draw;
	*
	* @author J2579
	* @version 1.0.0
	* @since 2019-04-17
	*/
	private class GameWindow extends DoubleBufferedCanvas {
		
		/**
		* Constructs the window, creating it from a width 
		* and height through the parent constructor.
		*
		* @param width This is the width of the Canvas.
		* @param height This is the height of the Canvas.
		*/
		public GameWindow(int width, int height) {
			super(width,height);
		}
		
		/**
		* This method draws the Graphics to the Canvas.
		* By iterating through every object on the screen, 
		*
		* @param g The double-buffer's graphics to draw on.
		*/
		public void draw(Graphics g) {
			
			g.setColor(new Color(255, 255, 255));
			g.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
			
			g.drawImage(game.grid, 0, 0, null);
			g.drawImage(game.ball, game.getPlayerDrawPosition()[0], game.getPlayerDrawPosition()[1], null);
			//Next line goes here...
		}
	}
}
