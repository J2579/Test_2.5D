package physics;

import util.Tickable;

/**
 * Handles Physics
 * 
 * @author J2579
 */
public class PhysicsController implements Tickable {

	//Position on the 3-D Plane
	private double x,y,z;
	
	//Movement on the 3-D Plane
	private Vector currentVector;
	
	private double xSpeed, ySpeed, zSpeed;
	private static final int DEFAULT_SPEED = 1;
	
	//Position on 2-D Plane (The screen)
	private double screenX, screenY;
	
	/** Screen Movement Constants */
	private static final double XXCONSTANT = (Math.sin(70)+0.3) * -1;
	private static final double XYCONSTANT = (Math.cos(70));
	private static final double YXCONSTANT = 0;
	private static final double YYCONSTANT = 1;
	private static final double ZXCONSTANT = (Math.cos(60));
	private static final double ZYCONSTANT = (Math.sin(60)-0.2);
	
	
	
	/**
	 * Creates a new physics object, centered upon a position.
	 * 
	 * @param x The x-position
	 * @param y The y-position
	 * @param z The z-position
	 */
	public PhysicsController(double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.zSpeed = zSpeed;
		
		currentVector = new Vector(0, 0, 0);
		updateScreenPosition(x, y, z);
	}
	
	/**
	 * Creates a PhysicsController at the origin
	 */
	public PhysicsController() {
		this(0,0,0,DEFAULT_SPEED,DEFAULT_SPEED,DEFAULT_SPEED);
	}
	
	/**
	 * Returns the movement vector for editing.
	 * 
	 * @return The movement vector of the PhysicsController.
	 */
	public Vector getCurrentVector() {
		return currentVector;
	}
	
	/**
	 * Updates the PhysicsObject on tick
	 */
	public void onTick() {
		
		move();
	}
	
	/**
	 * Moves the Physics Object
	 * 
	 * @return True if the object can be moved, false if a collision would occur.
	 */
	public boolean move() {
		
		if(checkCollideOnCurrentMap()) {
			return false;
		}
		
		double xChange = currentVector.getX() * xSpeed;
		double yChange = currentVector.getY() * ySpeed;
		double zChange = currentVector.getZ() * zSpeed;
		
		x += xChange;
		y += yChange;
		z += zChange;
		
		updateScreenPosition(xChange, yChange, zChange);
		
		return true;
	}
	
	/**
	 * Updates screenX and screenY upon movement.
	 */
	private void updateScreenPosition(double xChange, double yChange, double zChange) {
		
		
		//Set the new screen X-Coordinate.
		screenX += xChange * XXCONSTANT;
		screenX += yChange * YXCONSTANT;
		screenX += zChange * ZXCONSTANT;
		
		//Set the new screen Y-Coordinate.
		screenY += xChange * XYCONSTANT;
		screenY += yChange * YYCONSTANT;
		screenY += zChange * ZYCONSTANT;
	}

	/**
	 * Returns the draw coordinates of the Physics Object
	 * 
	 * @return The screen coordinates of the Physics Object
	 */
	public int[] get2DPosition() {
		return new int[] {(int) screenX, (int) screenY};
	}
	
	/**
	 * Returns the 3D coordinates of the Physics Object
	 * 
	 * @return The 3D coordinates of the Physics Object
	 */
	public int[] get3DPosition() {
		return new int[] {(int)x, (int)y, (int)z};
	}
	
	/**
	 * Checks collision against all objects on the current map.
	 * 
	 * @param vector The adjustment to the position to be checked
	 * @return True if a collision occurs, false if otherwise.
	 */
	private boolean checkCollideOnCurrentMap() {
		//TODO:
		return false;
	}


	/**
	 * Represents a 3-D Movement Vector, for encapsulating movement
	 * commands to a PhysicsController.
	 * 
	 * @author J2579
	 */
	public static class Vector {

		private double xMovement, yMovement, zMovement;
		
		public Vector(double xMovement, double yMovement, double zMovement) {
			this.xMovement = xMovement;
			this.yMovement = yMovement;
			this.zMovement = zMovement;
		}
		
		/**
		 * Adds a vector to the current vector (i.e. an external impulse on the physicsController)
		 * 
		 * @param other The vector to add to the current vector.
		 */
		public void addVector(Vector other) {
			setX(getX() + other.getX());
			setY(getY() + other.getY());
			setZ(getZ() + other.getZ());
		}
		
		/**
		 * Returns the X-Component of the Vector
		 * 
		 * @return The X-Component
		 */
		public double getX() {
			return xMovement;
		}
		
		/**
		 * Returns the Y-Component of the Vector
		 * 
		 * @return The Y-Component.
		 */
		public double getY() {
			return yMovement;
		}
		
		/**
		 * Returns the Z-Component of the Vector.
		 * 
		 * @return The z-component
		 */
		public double getZ() {
			return zMovement;
		}
		
		/**
		 * Sets the X-Component of the vector
		 * 
		 * @param xMovement the xMovement to set
		 */
		public void setX(double xMovement) {
			this.xMovement = xMovement;
		}

		/**
		 * Sets the Y-Component of the vector
		 * 
		 * @param yMovement the yMovement to set
		 */
		public void setY(double yMovement) {
			this.yMovement = yMovement;
		}

		/**
		 * Sets the Z-Component of the vector
		 * 
		 * @param zMovement the zMovement to set
		 */
		public void setZ(double zMovement) {
			this.zMovement = zMovement;
		}
	}
}
