package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import util.ImageLibrary;

/**
 * A player is controlled by a client to interact with and move around the game
 * world
 * 
 */
public class Player extends Characters implements Serializable {

	private static final long serialVersionUID = 1L;
	// Player inventory
	private ArrayList<Item> inventory = new ArrayList<Item>();
	// Direction facing
	public Direction direction;
	// Sprite set
	public String[] images;
	// Bounce animation stage
	private int i = 0;
	private int j = 0;
	private boolean up = true;
	// Health
	private int health = maxHp;
	public static final int maxHp = 30;
	public static final int minHp = 0;
	// Dead
	public boolean dead = false;
	private boolean hit = false;
	// Score
	private int gold = 0;

	// Ghost sprite set
	public String[] deathImages = new String[] {
			"sprites/slimes/slimeGhostFront.png",
			"sprites/slimes/slimeGhostLeft.png",
			"sprites/slimes/slimeGhostRight.png",
			"sprites/slimes/slimeGhostBack.png" };

	/**
	 * Enum for representing the direction of the player
	 * 
	 */
	public static enum Direction {
		LEFT, RIGHT, UP, DOWN;
	}

	/**
	 * Creates a new Player with the given Sprite set, name, position and if it
	 * is movable
	 * 
	 * @param images
	 *            Sprite set
	 * @param name
	 *            Name of Player
	 * @param isMoveable
	 *            Is player movable
	 * @param x
	 *            X position
	 * @param y
	 *            Y position
	 */
	public Player(String[] images, String name, boolean isMoveable, int x, int y) {
		super(images, name, isMoveable, x, y);
		direction = Direction.LEFT;
		this.images = images;
	}

	/**
	 * returns the inventory
	 * 
	 * @return the inventory
	 */
	public ArrayList<Item> getInventory() {
		return inventory;
	}

	/**
	 * add an item to the inventory
	 * 
	 * @param i
	 *            the item to be added to the inventory
	 */
	public void addItem(Item i) {
		inventory.add(i);
	}

	/**
	 * Change the way the player is facing This means the player will look the
	 * way the key is pressed and may or may not move that way.
	 * 
	 * @param dir
	 *            direction to face
	 */
	public void setDirection(Direction dir) {
		direction = dir;
		switch (dir) {
		case UP:
			i = 3;
			break;
		case DOWN:
			i = 0;
			break;
		case LEFT:
			i = 1;
			break;
		case RIGHT:
			i = 2;
			break;
		}
	}

	/**
	 * Player will move a tile in the given direction
	 * 
	 * @param dir
	 *            the Direction the player is wanting to move in
	 */
	@Override
	public void move(Direction dir) {
		switch (dir) {
		case UP:
			moveUp();
			break;
		case DOWN:
			moveDown();
			break;
		case LEFT:
			moveLeft();
			break;
		case RIGHT:
			moveRight();
			break;
		}
	}

	/**
	 * Updates the players current bounce.
	 */
	public void clockTick() {
		if (up) {
			j += 1;
		} else {
			j -= 1;
		}
		if (j > 5) {
			up = false;
		}
		if (j < 1) {
			up = true;
		}
	}

	/**
	 * Draw the player at the given row and column
	 * 
	 * @param g
	 *            the graphics object to draw to
	 * @param Row
	 *            the row the player is in
	 * @param Col
	 *            the column the player is in
	 */
	public void draw(Graphics g, int Row, int Col) {

		if (images == null) {
			// Draw a green circle if no sprites exist
			g.setColor(Color.GREEN);
			g.fillOval(Row, Col - (int) j, Tile.tileSize, Tile.tileSize);
		} else {
			if (dead) {
				// Draw ghost if dead
				BufferedImage img = ImageLibrary.get(deathImages[i]);
				g.drawImage(img, Row, Col - (int) j, null);
			} else {
				// Draw sprite
				BufferedImage img = ImageLibrary.get(images[i]);
				g.drawImage(img, Row, Col - (int) j, null);
				// draw health bar
				drawHealth(g, Row, Col);
			}
		}

	}

	/**
	 * Draw the players score in the top left corner
	 * 
	 * @param g
	 *            the graphics object to be drawn to
	 */
	public void drawScore(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("TimesRoman", Font.BOLD, 36));
		g.drawString("Gold: " + this.gold, 50, 50);
	}

	/**
	 * Draws the players health bar above the player.
	 * 
	 * @param g
	 *            - Graphics g
	 * @param Row
	 *            - Row the player is at
	 * @param Col
	 *            - Column the player is at
	 */
	public void drawHealth(Graphics g, int Row, int Col) {
		if (dead) {
			return;
		}
		int offset = 15;
		int hpHeight = 5;
		g.setColor(Color.white);
		g.fillRect(Row, Col - offset, Player.maxHp, hpHeight);
		g.setColor(Color.green);
		g.fillRect(Row, Col - offset, health, hpHeight);
		g.setColor(Color.black);
		g.drawRect(Row, Col - offset, Player.maxHp - 1, hpHeight);
	}

	/**
	 * Returns the players health
	 * 
	 * @return - Total remaining health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Decreases the players health according to the amount entered into
	 * parameter. health = health - amount;
	 * 
	 * @param amount
	 *            - amount to be decreased from health
	 */
	public void decreaseHealth(int amount) {
		if (health <= minHp) {
			dead = true;
			return;
		}
		this.health -= amount;
	}

	/**
	 * returns if you're dead or alive.
	 * 
	 * @return - true if dead, false if alive
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * When player is dead, sets the player to be alive again, with full health
	 * as well.
	 */
	public void setAlive() {
		health = maxHp;
		dead = false;
		hit = false;
	}

	/**
	 * Determines if the player has hit an enemy or not.
	 * 
	 * @return - true if hit, false if not.
	 */
	public boolean isHit() {
		return hit;
	}

	/**
	 * Sets the hit field which determines whether a player has been hit by a
	 * player or not.
	 * 
	 * @param trueOrFalse
	 *            - true sets hit to true, false sets hit to false.
	 */
	public void setHit(boolean trueOrFalse) {
		hit = trueOrFalse;
	}

	/**
	 * Adds a number onto the score
	 * 
	 * @param score
	 *            Amount of Gold to add to total
	 */
	public void addGold(int gold) {
		this.gold += gold;
	}

	/**
	 * Returns the score of this player
	 * 
	 * @return Total amount of gold owned
	 */
	public int getGold() {
		return this.gold;
	}

}
