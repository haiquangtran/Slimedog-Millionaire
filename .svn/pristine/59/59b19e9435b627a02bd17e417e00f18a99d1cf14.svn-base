package game;

import game.Player.Direction;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import util.ImageLibrary;

/**
 * An NPC is a character that can interact with a player by talking to them.
 * NPC's can be static or follow a given route
 * 
 */
public class NPC extends Characters implements Serializable {

	private static final long serialVersionUID = 1L;

	// the path the npc will follow. should be cycle
	Direction[] path;
	// Direction being faced
	Direction direction = Direction.LEFT;
	// transition between tiles
	double transition = 0.0;
	// Sprite set
	public String[] images;
	// x,y position
	double x, y;
	// Bounce animation stage
	int i = 0;
	int n;
	private int j = 0;
	private boolean up;

	// NPC speech when talking to
	String speech;
	private boolean talk;
	private int talkCount = 0;

	/**
	 * Creates a new NPC with the given Sprite Set, name, position, route,
	 * speech and whether it is movable
	 * 
	 * @param image
	 *            Sprite Set
	 * @param name
	 *            NPC name
	 * @param isMoveable
	 *            Is NPC movable
	 * @param x
	 *            X position
	 * @param y
	 *            Y position
	 * @param p
	 *            Path NPC will follow
	 * @param speech
	 *            String said when NPC speaks
	 */
	public NPC(String[] image, String name, boolean isMoveable, int x, int y,
			Direction[] p, String speech) {
		super(image, name, isMoveable, x, y);
		images = image;
		path = p;
		n = 1;
		this.x = x;
		this.y = y;
		this.speech = speech;
		talk = false;
		if (p != null) {
			move(path[0]);
		}
	}

	@Override
	public void move(Direction dir) {
		transition = 0.05;
		direction = dir;
		switch (dir) {
		case UP:
			i = 3;
			moveUp();
			break;
		case DOWN:
			i = 0;
			moveDown();
			break;
		case LEFT:
			i = 1;
			moveLeft();
			break;
		case RIGHT:
			i = 2;
			moveRight();
			break;
		}
	}

	/**
	 * Start talking
	 */
	public void talk() {
		talk = true;
	}

	/**
	 * Stop talking
	 */
	public void shutUp() {
		talk = false;
	}

	/**
	 * updates the bounce of the npc and the position. also a timer for the
	 * talking.
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

		if (talk) {
			talkCount++;
			if (talkCount == 50) {
				talk = false;
				talkCount = 0;
			}
		}

		if (transition == 0.0)
			return;
		if (transition >= 1.0) {
			transition = 0.0;
			if (path != null) {
				move(path[n]);
				n++;
				if (n >= path.length) {
					n = 0;
				}
			}
			return;
		}
		switch (direction) {
		case UP:
			y -= 0.05;
			break;
		case DOWN:
			y += 0.05;
			break;
		case LEFT:
			x -= 0.05;
			break;
		case RIGHT:
			x += 0.05;
			break;
		}

		transition += 0.05;
	}

	/**
	 * Draws the npc with the image for the direction and where they currently
	 * are.
	 * 
	 * @param g
	 *            - graphics to draw the npc to
	 * @param startRow
	 *            row to draw from relative to the player
	 * @param startCol
	 *            column to draw from relative to the player
	 */
	public void draw(Graphics g, int startRow, int startCol) {
		g.setColor(Color.BLUE);
		g.drawImage(ImageLibrary.get(images[i]),
				(int) ((x - startRow) * Tile.tileSize),
				(int) ((y - startCol) * Tile.tileSize) - j, Tile.tileSize,
				Tile.tileSize, null);
		if (talk) {
			if (speech == null) {
				g.setColor(new Color(200, 200, 200));
				g.fillRect((int) ((x - startRow) * Tile.tileSize),
						(int) ((y - startCol) * Tile.tileSize) - (j + 25), 30,
						20);
				g.setColor(new Color(0, 0, 0));
				g.drawString("Hi", (int) ((x - startRow) * Tile.tileSize) + 1,
						(int) ((y - startCol) * Tile.tileSize) - (j + 15));
			} else {
				g.setColor(new Color(200, 200, 200));
				g.fillRect((int) ((x - startRow) * Tile.tileSize),
						(int) ((y - startCol) * Tile.tileSize) - (j + 25),
						speech.length() * 7, 20);
				g.setColor(new Color(0, 0, 0));
				g.drawString(speech,
						(int) ((x - startRow) * Tile.tileSize) + 1,
						(int) ((y - startCol) * Tile.tileSize) - (j + 15));
			}
		}

	}

}
