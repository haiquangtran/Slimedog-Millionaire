package game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An object that the player can move through when it is open. Otherwise they
 * cannot move through until they have the right key.
 * 
 * @author Marcel Blokker
 * 
 */
public class Door extends EnvironmentObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean open;
	private String key;

	/**
	 * Creates a new Door with the given Image, Name, link to a key and whether
	 * it is open
	 * 
	 * @param i
	 *            Door Image
	 * @param n
	 *            Door Name
	 * @param open
	 *            Whether Door is open
	 * @param key
	 *            Key linked to Door
	 */
	public Door(String i, String n, boolean open, String key) {
		super(i, n);
		this.open = open;
		this.key = key;
	}

	/**
	 * returns whether the door is open or not
	 * 
	 * @return boolean for door being open.
	 */
	public boolean getOpen() {
		return open;
	}

	/**
	 * set whether the door is open or not
	 * 
	 * @param op
	 *            what to set open to.
	 */
	public void setOpen(boolean op) {
		open = op;
	}

	/**
	 * Player can try to open a door. The door will open if the player has the
	 * correct Key in their inventory.
	 * 
	 * @param inventory
	 *            - the players inventory
	 * @return whether the door was opened or not.
	 */
	public boolean tryOpen(ArrayList<Item> inventory) {
		for (Item i : inventory) {
			if (i.getName().equals(key)) {
				open = true;
				return true;
			}
		}
		return false;
	}

	/**
	 * return the name of the key
	 * 
	 * @return name of key
	 */
	public String getKey() {
		return key;
	}

}
