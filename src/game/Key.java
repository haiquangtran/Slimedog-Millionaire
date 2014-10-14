package game;

import java.io.Serializable;

/**
 * Represents a Key object to open a door. The door and key must match for the door to open.
 * @author duligajaco
 *
 */
public class Key extends Item implements Serializable {

	private static final long serialVersionUID = -6559431825243535378L;

	/**
	 * Creates a new Key with the given Image, name and whether it can be picked up
	 * @param image Key Image
	 * @param name Key Name
	 * @param pickUp Can be picked up
	 */
	public Key(String image, String name, boolean pickUp) {
		super(image, name, pickUp);
	}


}
