package game;

import java.io.Serializable;

/**
 * Abstract class representing items that can be picked up.
 * 
 * @author Jacob Duligall
 * 
 */
public abstract class Item extends GameObject implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new Item with the given Image, Name and whether it can be
	 * picked up
	 * 
	 * @param image
	 *            Item Image
	 * @param name
	 *            Item Name
	 * @param pickUp
	 *            Whether the item can be picked up
	 */
	public Item(String image, String name, boolean pickUp) {
		super(image, name, pickUp);
	}

}
