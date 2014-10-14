package game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An environment object that can hold items inside somewhat like the players
 * inventory.
 * 
 * @author Celdamar
 */
public class Container extends EnvironmentObject implements Serializable {

	private static final long serialVersionUID = 1L;

	// Container settings and gold contained
	private boolean open = false;
	private boolean locked;
	private int goldInside = (int) (1000000 + Math.random() * 500);

	// List of items contained in container
	private ArrayList<Item> items;

	/**
	 * Creates a new Container with the given Image, Name, list of items and
	 * whether it is locked
	 * 
	 * @param image
	 *            Container Image
	 * @param name
	 *            Container name
	 * @param i
	 *            Item List
	 * @param locked
	 *            Is Container Locked
	 */
	public Container(String image, String name, ArrayList<Item> i,
			boolean locked) {
		super(image, name);
		if (i == null) {
			items = new ArrayList<Item>();
		} else {
			items = i;
		}
		this.locked = locked;
	}

	/**
	 * Adds an item to the container
	 * 
	 * @param item
	 *            Item to be added
	 */
	public void additem(Item item) {
		this.items.add(item);
	}

	/**
	 * Removes and returns all the items from the container
	 * 
	 * @return items All items that were contained in the Container
	 */
	public ArrayList<Item> removeAll() {
		ArrayList<Item> temp = items;
		items.removeAll(temp);
		return temp;
	}

	/**
	 * Returns all the items in the container
	 * 
	 * @return items All items in container
	 */
	public ArrayList<Item> getItems() {
		return items;
	}

	/**
	 * Returns the total amount of gold in the container
	 * 
	 * @return Total amount of Gold
	 */
	public int getGold() {
		return this.goldInside;
	}

	/**
	 * Returns if the container is open
	 * 
	 * @return True if open, else false
	 */
	public boolean ifOpen() {
		return open;
	}

	/**
	 * Interact with the container, Opening if closed and unlocked, closing if
	 * open
	 */
	public void interact() {
		if (!open) {
			if (!locked) {
				// Open chest if closed and not locked
				super.setImage("sprites/openchest.png");
				open = true;
				goldInside = 0;
			}

		} else {
			// Close chest
			super.setImage("sprites/closedchest.png");
			open = false;
		}

	}

	/**
	 * Attempts to open the chest with an item from the given inventory
	 * 
	 * @param inventory
	 *            Inventory to be checked for Item
	 * @return True if can be opened, false otherwise
	 */
	public boolean tryOpen(ArrayList<Item> inventory) {
		for (Item i : inventory) {
			if (i.getName().equals(World.bluekey)) {
				return true;
			}
		}
		return false;
	}

}
