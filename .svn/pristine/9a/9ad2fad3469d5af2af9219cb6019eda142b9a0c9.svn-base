package game;

import game.Player.Direction;

/**
 * A Boat Object is a vehicle which allows the player to travel across water.
 * 
 * @author Quang Tran
 * 
 */
public class Boat extends GameObject {

	private static final long serialVersionUID = 2219797920756035058L;

	/**
	 * Creates a new Boat with the given Image, name and whether it can be
	 * picked up
	 * 
	 * @param image
	 *            Boat Image
	 * @param name
	 *            Boat Name
	 * @param pickUp
	 *            Whether or not the Boat can be picked up
	 */
	public Boat(String image, String name, boolean pickUp) {
		super(image, name, false);
	}

	/**
	 * Allows a player to move on the Boat in a certain direction.
	 * 
	 * @param dir
	 *            - direction player will move
	 * @param uid
	 *            - player identification
	 * @param world
	 *            - World that contains the Boat
	 */
	public void move(Direction dir, int uid, World world) {
		if (world.checkTile(dir, uid, 1).getItem().getName()
				.equals(World.water)) {
			move(dir, uid, world, 1);
			world.replaceTile(dir, uid, "sprites/water.png", World.water, 0);
		}

	}

	/**
	 * Moves the player and both in the specified Direction for the given
	 * distance
	 * 
	 * @param dir
	 *            Direction of travel
	 * @param uid
	 *            ID of player in boat
	 * @param world
	 *            World containing Boat
	 * @param distance
	 *            Distance to travel
	 */
	public void move(Direction dir, int uid, World world, int distance) {
		Player p = world.getPlayers().get(uid);
		int pRow = p.getX();
		int pCol = p.getY();
		if (dir == Direction.DOWN && pCol + distance < World.mapSize
				&& pCol + distance >= 0) {
			world.getMap()[pRow][pCol + distance].setItem(new Boat(
					"sprites/boatDown.png", World.boat, false));
		} else if (dir == Direction.UP && pCol - distance < World.mapSize
				&& pCol - distance >= 0) {
			world.getMap()[pRow][pCol - distance].setItem(new Boat(
					"sprites/boatUp.png", World.boat, false));
		} else if (dir == Direction.RIGHT && pRow + distance < World.mapSize
				&& pRow + distance >= 0) {
			world.getMap()[pRow + distance][pCol].setItem(new Boat(
					"sprites/boatRight.png", World.boat, false));
		} else if (dir == Direction.LEFT && pRow - distance < World.mapSize
				&& pRow - distance >= 0) {
			world.getMap()[pRow - distance][pCol].setItem(new Boat(
					"sprites/boatLeft.png", World.boat, false));
		}
	}
}
