package game;

import java.io.Serializable;

/**
 * Abstract class representing objects that can be contained in a tile.
 * 
 * @author Jacob Duligall
 * 
 */
public abstract class GameObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String image;
	private String name;
	private boolean pickUp;

	/**
	 * Creates a new GameObject with the given Image, name and whether it can be
	 * picked up
	 * 
	 * @param image
	 *            The name of the image
	 * @param name
	 *            The name of the item
	 * @param pickUp
	 *            whether the game object can be picked up by the player
	 */
	public GameObject(String image, String name, boolean pickUp) {
		this.image = image;
		this.name = name;
		this.pickUp = pickUp;
	}

	/**
	 * Sets the image of the game object
	 * 
	 * @param img
	 *            Image to be set
	 */
	public void setImage(String img) {
		this.image = img;
	}

	/**
	 * Returns whether or not the item can be picked up
	 * 
	 * @return True if can be picked up, false if not
	 */
	public boolean isPickUp() {
		return pickUp;
	}

	/**
	 * Return the filename of the image for the Object
	 * 
	 * @return Name of the Image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Returns the name of the Object
	 * 
	 * @return Name of the object
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
