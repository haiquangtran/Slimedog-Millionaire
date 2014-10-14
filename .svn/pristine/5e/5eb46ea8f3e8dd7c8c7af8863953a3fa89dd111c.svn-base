package gui;

import game.Item;
import game.World;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import util.ImageLibrary;

/**
 * The display for the game part of the game Client
 * 
 * @author Oliver Greenaway
 * 
 */
public class GameCanvas extends Canvas {

	private static final long serialVersionUID = -2241158400287323322L;

	public World world;
	private GameFrame parent;

	private boolean inventory = false;

	/**
	 * Constructs a new GameCanvas with the given width and height
	 * 
	 * @param width
	 *            The Width of the canvas
	 * @param height
	 *            The Height of the canvas
	 * @param parent
	 *            GameFrame that contains the canvas
	 */
	public GameCanvas(int width, int height, GameFrame parent) {
		super(parent.getWidth(), parent.getHeight());
		this.setBackground(Color.BLACK);
		this.parent = parent;
	}

	/**
	 * Draws everything in the world.
	 * 
	 * @param g
	 *            Graphics
	 */
	@Override
	public void paint(Graphics g) {
		if (world != null) {
			BufferedImage img = new BufferedImage(parent.getWidth(),
					parent.getHeight(), BufferedImage.TYPE_INT_ARGB);
			world.worldToImage(img);
			Graphics ig = img.getGraphics();
			ig.setColor(Color.red);
			ig.drawImage(ImageLibrary.get("UI/inventory.png"),
					this.getWidth() - 100, 0, null);
			if (inventory) {
				ig.setColor(Color.GRAY);
				ig.fillRect(this.getWidth() - 300, 40, 300, 500);
				int x = this.getWidth() - 290;
				int y = 50;
				int pos = 0;
				for (Item i : world.masterPlayer().getInventory()) {
					ig.drawImage(ImageLibrary.get(i.getImage()), x, y, null);
					x += 50;
					if (pos >= 6) {
						x = this.getWidth() - 290;
						y += 50;
						pos = 0;
					} else {
						pos++;
					}

				}
			}
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}

	/**
	 * Toggle whether the inventory is displayed
	 */
	public void toggleInventory() {
		inventory = !inventory;
	}

	/**
	 * Returns whether or not the inventory is currently being displayed
	 * 
	 * @return True if visible, else false
	 */
	public boolean getInventory() {
		return inventory;
	}

}
