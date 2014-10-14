package game;

import game.Player.Direction;

import java.io.Serializable;

/**
 * A Character represents a Object on the world that can move
 * 
 */
public abstract class Characters implements Serializable {

	private static final long serialVersionUID = 1L;

	// Position of the character
	private int x, y;

	/**
	 * Creates a new Character with the given Sprite set, name position and
	 * whether it is movable
	 * 
	 * @param images
	 *            Sprite Set
	 * @param name
	 *            Name of Character
	 * @param isMoveable
	 *            Can be moved
	 * @param x
	 *            X position
	 * @param y
	 *            Y position
	 */
	public Characters(String[] images, String name, boolean isMoveable, int x,
			int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Move the character in the given direction
	 * 
	 * @param dir
	 *            Direction to move
	 */
	public abstract void move(Direction dir);

	/**
	 * Return the X position of the character
	 * 
	 * @return X position
	 */
	public int getX() {
		return x;
	}

	/**
	 * Return the Y position of the character
	 * 
	 * @return Y position
	 */
	public int getY() {
		return y;
	}

	/**
	 * Move right by one place
	 */
	public void moveRight() {
		if (x < World.mapSize - 1) {
			x++;
		}
	}

	/**
	 * Move left by one place
	 */
	public void moveLeft() {
		if (x > 0) {
			x--;
		}
	}

	/**
	 * Move up by one place
	 */
	public void moveUp() {
		if (y > 0) {
			y--;
		}
	}

	/**
	 * Move down by one place
	 */
	public void moveDown() {
		if (y < World.mapSize - 1) {
			y++;
		}
	}
}
