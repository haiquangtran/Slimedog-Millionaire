package game;

import game.Player.Direction;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import util.ImageLibrary;

/**
 * An enemy is a character that can follow a route and when making contact with
 * a player deals damage.
 * 
 */
public class Enemy extends Characters implements Serializable {

	private static final long serialVersionUID = 1L;
	// the path the enemy will follow. should be cycle
	Direction[] path;
	// Direction facing
	Direction direction = Direction.LEFT;
	// Transition between tiles
	double transition = 0.0;
	// Sprite set
	public String[] images;
	// Position
	double x, y;
	// Bounce animation stage
	int i = 0;
	int n;
	private int j = 0;
	private boolean up;

	/**
	 * Creates a new enemy with the given sprite set, name, position, route and
	 * whether it can move.
	 * 
	 * @param image
	 *            Sprite set
	 * @param name
	 *            Enemy name
	 * @param isMoveable
	 *            Can enemy move
	 * @param x
	 *            X position
	 * @param y
	 *            Y position
	 * @param p
	 *            Route to follow
	 */
	public Enemy(String[] image, String name, boolean isMoveable, int x, int y,
			Direction[] p) {
		super(image, name, isMoveable, x, y);
		images = image;
		path = p;
		n = 1;
		this.x = x;
		this.y = y;
		if (p != null) {
			move(path[0]);
		}
	}

	@Override
	public void move(Direction dir) {
		transition = 0.1;
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
	 * update position on each clocktick
	 */
	public void clockTick() {

		if (up) {
			j += 1;
		} else {
			j -= 1;
		}
		if (j > 4) {
			up = false;
		}
		if (j < 1) {
			up = true;
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
			y -= 0.1;
			break;
		case DOWN:
			y += 0.1;
			break;
		case LEFT:
			x -= 0.1;
			break;
		case RIGHT:
			x += 0.1;
			break;
		}

		transition += 0.1;
	}

	/**
	 * Draw the enemy on the graphics with the given offset
	 * 
	 * @param g
	 *            Graphics
	 * @param startRow
	 *            Row offset from master player
	 * @param startCol
	 *            Column offset from player
	 */
	public void draw(Graphics g, int startRow, int startCol) {
		g.setColor(Color.BLUE);
		g.drawImage(ImageLibrary.get(images[i]),
				(int) ((x - startRow) * Tile.tileSize),
				(int) ((y - startCol) * Tile.tileSize) - j, Tile.tileSize,
				Tile.tileSize, null);

	}

}
