package game;

import game.Player.Direction;
import gui.GameFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import util.ImageLibrary;

/**
 * Class that represents the world. Contains a 2d array of game objects, in
 * which could represent keys, doors etc. This class populates the world and is
 * responsible for redrawing it. This class is also responsible for the logic of
 * the game. i.e. where players can or cannot move etc.
 * 
 * 
 */
public class World implements Serializable {
	public int test = 0;

	private static final long serialVersionUID = 1L;

	// Length of side of square world
	public static final int mapSize = 70;

	// Map containing all the tiles, which represent the world
	private Tile[][] map = new Tile[mapSize][mapSize];

	// Boolean to mark objects that need drawing
	private boolean drawRoomLabel = false;
	private boolean drawSign = false;
	private boolean drawChest = false;
	private boolean canDrop = true;

	// Notice Time
	private int timer = 200;
	private int RoomLabelCount = 20;

	// Player inside a room
	private boolean inside = false;

	// Players in the game
	private Map<Integer, Player> players = new HashMap<Integer, Player>();

	// List of NPC's
	private ArrayList<NPC> npcs = new ArrayList<NPC>();

	// List of Enemies
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	// Master player for client run worlds
	private int masterPlayer = 0;

	// Player position
	private int playerRow;
	private int playerCol;

	// Shows how many tiles away from center of screen (For offset of player)
	private int middleRow = (GameFrame.screenWidth / Tile.tileSize / 2);
	private int middleCol = (GameFrame.screenHeight / Tile.tileSize / 2);

	private static Graphics graphics;

	// Item names
	public static final String grass = "grass";
	public static final String flower = "flower";
	public static final String tree = "tree";
	public static final String floor = "floor";
	public static final String door = "door";
	public static final String water = "water";
	public static final String key = "key";
	public static final String bluekey = "bluekey";
	public static final String redkey = "redkey";
	public static final String yellowkey = "yellowkey";
	public static final String wall = "wall";
	public static final String lockedchest = "lockedchest";
	public static final String boulder = "boulder";
	public static final String reviver = "reviver";
	public static final String boat = "boat";
	public static final String sign = "sign";
	public static final String mazeWall = "mazeWall";

	// Sprite sets
	String[] green = new String[] { "sprites/slimes/slime.png",
			"sprites/slimes/slime2.png", "sprites/slimes/slime3.png",
			"sprites/slimes/slime4.png" };
	String[] girl = new String[] { "sprites/slimes/slimeGirlFront.png",
			"sprites/slimes/slimeGirlLeft.png",
			"sprites/slimes/slimeGirlRight.png",
			"sprites/slimes/slimeGirlBack.png" };
	String[] blue = new String[] { "sprites/slimes/slimeBlueFront.png",
			"sprites/slimes/slimeBlueLeft.png",
			"sprites/slimes/slimeBlueRight.png",
			"sprites/slimes/slimeBlueBack.png" };
	String[] gang = new String[] { "sprites/slimes/slimeGangsterFront.png",
			"sprites/slimes/slimeGangsterLeft.png",
			"sprites/slimes/slimeGangsterRight.png",
			"sprites/slimes/slimeGangsterBack.png" };
	String[] sponge = new String[] { "sprites/enemies/spongeFront.png",
			"sprites/enemies/spongeLeft.png",
			"sprites/enemies/spongeRight.png", "sprites/enemies/spongeBack.png" };

	// Array of sprite sets for assigning to player
	String[][] playerAssignChar = new String[][] { green, girl, blue, gang };

	/**
	 * Create a new world setting up all objects and sprites
	 */
	public World() {
		setUpWorld();
	}

	/**
	 * Sets up the World and all the components in it
	 */
	public void setUpWorld() {

		// Opens input to the file containing the map data
		InputStream file = ClassLoader
				.getSystemResourceAsStream("Assets/Data/file.txt");
		Scanner scan = new Scanner(file);

		// Iterate over each character in the map and build a tile based on the
		// character
		for (int j = 0; j < map.length; j++) {
			char[] next = scan.next().toCharArray();
			for (int i = 0; i < map[0].length; i++) {
				String img = "";
				String name = "";
				char current = next[i];
				if (current == 'x') {
					img = "sprites/tree3.png";
					name = tree;
				}
				if (current == '-') {
					img = "sprites/grass.png";
					name = grass;
				}
				if (current == 'y') {
					img = "sprites/flower.png";
					name = flower;
				}
				if (current == 'w') {
					img = "sprites/water.png";
					name = water;
				}
				if (current == 'l') {
					img = "sprites/wall.png";
					name = wall;
				}
				if (current == '=') {
					img = "sprites/wallOld.png";
					name = mazeWall;
				}
				if (current == 'k') {
					img = "sprites/keyYellow.png";
					name = key;
					map[i][j] = new Tile(new Key(img, name, true), i, j);
					continue;
				}
				if (current == 'd') {
					img = "sprites/door.png";
					name = door;
					map[i][j] = new Tile(new Door(img, name, false, key), i, j);
					continue;
				}
				if (current == 'R') {
					img = "sprites/doorRed.png";
					name = door;
					map[i][j] = new Tile(new Door(img, name, false, redkey), i,
							j);
					continue;
				}
				if (current == 'D') {
					img = "sprites/doorBlue.png";
					name = door;
					map[i][j] = new Tile(new Door(img, name, false, bluekey),
							i, j);
					continue;
				}
				if (current == 'f') {
					img = "sprites/floor.png";
					name = floor;
				}
				if (current == 'L') {
					img = "sprites/lockedchest.png";
					name = lockedchest;
					map[i][j] = new Tile(new Container(img, name, null, false),
							i, j);
					continue;
				}
				if (current == 'o') {
					img = "sprites/boulder.png";
					name = boulder;
					map[i][j] = new Tile(new Boulder(img, name, false), i, j);
					continue;
				}
				if (current == 'r') {
					img = "sprites/reviver.png";
					name = reviver;
					map[i][j] = new Tile(new EnvironmentObject(img, name), i, j);
					continue;
				}
				if (current == 'b') {
					img = "sprites/boatDown.png";
					name = boat;
					map[i][j] = new Tile(new Boat(img, name, false), i, j);
					continue;
				}
				if (current == 's') {
					img = "sprites/sign.png";
					name = sign;
					map[i][j] = new Tile(new Sign(img, name, "A room"), i, j);
					continue;
				}
				if (current == 'B') {
					img = "sprites/keyBlue.png";
					name = bluekey;
					map[i][j] = new Tile(new Key(img, name, true), i, j);
					continue;
				}
				if (current == 'K') {
					img = "sprites/keyRed.png";
					name = redkey;
					map[i][j] = new Tile(new Key(img, name, true), i, j);
					continue;
				}
				map[i][j] = new Tile(new EnvironmentObject(img, name), i, j);
			}
		}
		scan.close();

		// Add all NPC's
		npcs.add(new NPC(gang, "bob", false, 15, 25, new Direction[] {
				Direction.DOWN, Direction.RIGHT, Direction.RIGHT,
				Direction.RIGHT, Direction.RIGHT, Direction.UP, Direction.LEFT,
				Direction.LEFT, Direction.LEFT, Direction.LEFT, },
				"I'd rather be playing GTA..."));
		npcs.add(new NPC(green, "bob", false, 15, 15, new Direction[] {
				Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT,
				Direction.DOWN, Direction.RIGHT, Direction.RIGHT,
				Direction.RIGHT, Direction.RIGHT, Direction.UP },
				"Congratulations! You won!"));
		npcs.add(new NPC(blue, "bob", false, 13, 17,
				new Direction[] { Direction.LEFT, Direction.DOWN,
						Direction.RIGHT, Direction.LEFT, Direction.LEFT,
						Direction.LEFT, Direction.RIGHT, Direction.RIGHT,
						Direction.RIGHT, Direction.UP }, "We rich!!!"));
		npcs.add(new NPC(girl, "bob", false, 13, 14, new Direction[] {
				Direction.LEFT, Direction.LEFT, Direction.UP, Direction.UP,
				Direction.RIGHT, Direction.RIGHT, Direction.DOWN,
				Direction.DOWN }, "We millionaires baby!"));
		npcs.add(new NPC(blue, "bob", false, 15, 10, new Direction[] {
				Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT,
				Direction.DOWN, Direction.RIGHT, Direction.RIGHT,
				Direction.RIGHT, Direction.RIGHT, Direction.UP },
				"My wife left me..."));
		npcs.add(new NPC(blue, "bob", false, 10, 60, new Direction[] {
				Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT,
				Direction.DOWN, Direction.RIGHT, Direction.RIGHT,
				Direction.RIGHT, Direction.RIGHT, Direction.UP },
				"Ghosts can move through anything!"));
		npcs.add(new NPC(green, "bob", false, 45, 10, new Direction[] {
				Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT,
				Direction.DOWN, Direction.RIGHT, Direction.RIGHT,
				Direction.RIGHT, Direction.RIGHT, Direction.UP },
				"Sponges are dangerous!"));
		npcs.add(new NPC(gang, "bob", false, 30, 40, new Direction[] {
				Direction.DOWN, Direction.RIGHT, Direction.RIGHT,
				Direction.RIGHT, Direction.RIGHT, Direction.UP, Direction.LEFT,
				Direction.LEFT, Direction.LEFT, Direction.LEFT, },
				"Ghosts can open chests!"));

		// Add all Enemies
		enemies.add(new Enemy(sponge, "sponge", false, 50, 50, new Direction[] {
				Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT,
				Direction.DOWN, Direction.RIGHT, Direction.RIGHT,
				Direction.RIGHT, Direction.RIGHT, Direction.UP }));
		enemies.add(new Enemy(sponge, "sponge", false, 30, 25, new Direction[] {
				Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT,
				Direction.DOWN, Direction.RIGHT, Direction.RIGHT,
				Direction.RIGHT, Direction.RIGHT, Direction.UP }));
		enemies.add(new Enemy(sponge, "sponge", false, 50, 10, new Direction[] {
				Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT,
				Direction.DOWN, Direction.RIGHT, Direction.RIGHT,
				Direction.RIGHT, Direction.RIGHT, Direction.UP }));

		// Create Enemy Puzzle in room
		Direction[] rightMovement = new Direction[24];
		for (int i = 0; i < 24; i++) {
			if (i < 12) {
				rightMovement[i] = Direction.RIGHT;
			} else {
				rightMovement[i] = Direction.LEFT;
			}
		}
		Direction[] leftMovement = new Direction[24];
		for (int i = 0; i < 24; i++) {
			if (i < 12) {
				leftMovement[i] = Direction.LEFT;
			} else {
				leftMovement[i] = Direction.RIGHT;
			}
		}
		Direction[] upMovement = new Direction[12];
		for (int i = 0; i < 12; i++) {
			if (i < 6) {
				upMovement[i] = Direction.DOWN;
			} else {
				upMovement[i] = Direction.UP;
			}
		}
		for (int i = 0; i < 7; i++) {
			if (i % 2 == 0) {
				enemies.add(new Enemy(sponge, "sponge", false, 15, 44 + i,
						leftMovement));
			} else {
				enemies.add(new Enemy(sponge, "sponge", false, 3, 44 + i,
						rightMovement));
			}
		}
		enemies.add(new Enemy(sponge, "sponge", false, 3, 44, upMovement));
		enemies.add(new Enemy(sponge, "sponge", false, 15, 44, upMovement));
		enemies.add(new Enemy(sponge, "sponge", false, 9, 44, upMovement));
		enemies.add(new Enemy(sponge, "sponge", false, 6, 44, upMovement));
		enemies.add(new Enemy(sponge, "sponge", false, 12, 44, upMovement));

	}

	/**
	 * Sets the Fields needed for players position according to where the player
	 * is in the array
	 */
	public void setPlayerPositionFields() {
		if (players.size() > 0) {
			playerRow = players.get(masterPlayer).getX();
			playerCol = players.get(masterPlayer).getY();
		}
	}

	/**
	 * Redraw method - Draws the whole world, including the players, npc,
	 * enemies and the map. Draws the world so that it is relative to the
	 * player, player is always in the middle.
	 * 
	 * @param g
	 *            - Graphics canvas
	 * @param width
	 *            - width of screen
	 * @param height
	 *            - height of screen
	 */
	public void redraw(Graphics g, int width, int height) {
		World.graphics = g;
		// the size of the "game window"
		int screenWidth = mapSize;
		int screenHeight = mapSize;

		// reset middle position
		middleRow = (width / Tile.tileSize / 2);
		middleCol = (height / Tile.tileSize / 2);

		// Get players position in array (PLAYER 1)
		setPlayerPositionFields();
		int startRow = playerRow - middleRow;
		int startCol = playerCol - middleCol;

		// Draws background tiles
		inside = nearFloor(playerRow, playerCol);
		BufferedImage image = ImageLibrary.get("sprites/grass.png");
		BufferedImage image2 = ImageLibrary.get("sprites/water.png");
		BufferedImage image3 = ImageLibrary.get("sprites/floor.png");
		for (int i = 0; i < screenWidth; i++) {
			for (int j = 0; j < screenHeight; j++) {
				Tile tile = map[i][j];
				// draw water under boat
				if (tile.getItem().getName().equals(boat)) {
					g.drawImage(image2, tile.getPoint().x
							- (startRow * Tile.tileSize), tile.getPoint().y
							- (startCol * Tile.tileSize), image.getWidth(),
							image.getHeight(), null);
				} else if (inside) { // draw floor tile under room
					g.drawImage(image3, tile.getPoint().x
							- (startRow * Tile.tileSize), tile.getPoint().y
							- (startCol * Tile.tileSize), image.getWidth(),
							image.getHeight(), null);
				} else { // draw grass
					g.drawImage(image, tile.getPoint().x
							- (startRow * Tile.tileSize), tile.getPoint().y
							- (startCol * Tile.tileSize), image.getWidth(),
							image.getHeight(), null);
				}
			}
		}

		// Draws the map relative to the player (Player in center)
		for (int j = 0; j < screenHeight; j++) {
			for (int i = 0; i < screenWidth; i++) {
				Tile tile = map[i][j];
				image = ImageLibrary.get(tile.getItem().getImage());
				int offsetY = image.getHeight() - Tile.tileSize;
				int offsetX = image.getWidth() - Tile.tileSize;
				// Translates it onto screen and draws so that drawing is
				// Relative to player (player is in middle)

				if (!tile.getItem().getName().equals(reviver)) {
					g.drawImage(image, tile.getPoint().x
							- (startRow * Tile.tileSize) - offsetX,
							tile.getPoint().y - offsetY
									- (startCol * Tile.tileSize),
							image.getWidth(), image.getHeight(), null);
				}

				for (Player p : players.values()) {
					// Draw players
					if (p.getX() == i && p.getY() == j) {
						p.draw(g, tile.getPoint().x
								- (startRow * Tile.tileSize), tile.getPoint().y
								- (startCol * Tile.tileSize));
					}
				}
				// Draw reviver after the player has been drawn
				if (tile.getItem().getName().equals(reviver)) {
					g.drawImage(image, tile.getPoint().x
							- (startRow * Tile.tileSize) - offsetX,
							tile.getPoint().y - offsetY
									- (startCol * Tile.tileSize),
							image.getWidth(), image.getHeight(), null);
				}

			}

		}
		// Draw npcs
		for (NPC npc : npcs) {
			npc.draw(g, startRow, startCol);
		}

		// Draw Enemies
		for (Enemy enemy : enemies) {
			enemy.draw(g, startRow, startCol);
		}
		// If hit by enemy - flash red
		if (player(masterPlayer).isHit() && !player(masterPlayer).isDead()) {
			g.setColor(new Color(154, 18, 18, 80));
			g.fillRect(0, 0, GameFrame.screenWidth, GameFrame.screenHeight);
			players.get(masterPlayer).setHit(false);
		}

		g.setColor(Color.black);
		// If player is in a room, only shows the room that they're in and
		// Darken everything else.
		for (int i = 0; i < screenWidth; i++) {
			for (int j = 0; j < screenHeight; j++) {
				Tile tile = map[i][j];
				String type = tile.getItem().getName();
				if ((type.equals("floor") && !inside || type.equals(mazeWall)
						&& !inside || (!inside && nearFloor(i, j)))) {
					BufferedImage img = ImageLibrary.get("sprites/roof.png");
					g.drawImage(img, tile.getPoint().x
							- (startRow * Tile.tileSize), tile.getPoint().y
							- (startCol * Tile.tileSize) - 30, img.getWidth(),
							img.getHeight(), null);
				} else if (!type.equals("floor") && inside
						&& !type.equals("door") && !type.equals("wall")
						&& !type.equals(mazeWall) && !nearFloor(i, j)) {
					BufferedImage img = ImageLibrary.get(tile.getItem()
							.getImage());
					int offset = Tile.tileSize - img.getHeight();
					g.fillRect(tile.getPoint().x - (startRow * Tile.tileSize),
							tile.getPoint().y - (startCol * Tile.tileSize)
									+ offset, img.getWidth(), img.getHeight());
				}
			}
		}

		// draw players score
		player(masterPlayer).drawScore(g);

		// draw the room label
		if (drawRoomLabel && RoomLabelCount != 0) {
			Player p = player(masterPlayer);
			if (p.getY() - 1 < mapSize && p.getY() - 1 >= 0
					&& p.getY() + 1 >= 0 && p.getY() + 1 < mapSize) {
				Tile tAbove = map[p.getX()][p.getY() - 1];
				Tile tBelow = map[p.getX()][p.getY() + 1];
				if (tBelow.getItem().getName().equals(door)
						&& ((Door) tBelow.getItem()).getKey().equals(bluekey)) {
					drawTextBox("Maze Room");
				} else if (tBelow.getItem().getName().equals(door)
						&& ((Door) tBelow.getItem()).getKey().equals(redkey)) {
					drawTextBox("Millionare's Retreat");
				} else if (tBelow.getItem().getName().equals(door)
						&& ((Door) tBelow.getItem()).getKey().equals(key)) {
					drawTextBox("Sponge Mania");
				} else if (tAbove.getItem().getName().equals(door)) {
					drawTextBox("Slimy Slime Village.");
				}
				// Cannot go through locked door
				if (tAbove.getItem().getName().equals(door)
						&& !checkDoor(masterPlayer, tAbove.getItem())) {
					drawTextBox("The door seems to be locked...");
				}

				RoomLabelCount--;
			}
		}
		if (RoomLabelCount == 0) {
			drawRoomLabel = false;
			RoomLabelCount = 200;
		}
		// pop up sign info
		if (drawSign) {
			if (timer > 0) {
				Player p = player(masterPlayer);
				Tile tAbove = map[p.getX()][p.getY() - 1];
				if (tAbove.getItem().getName().equals(sign)) {
					drawTextBox("A room, containing a puzzle.");
				}
				timer--;
				if (timer <= 0) {
					drawSign = false;
				}
			}
		}

		// pop up chest is locked
		if (drawChest) {
			if (timer > 0) {
				Player p = player(masterPlayer);
				Tile tAbove = map[p.getX()][p.getY() - 1];
				if (tAbove.getItem().getName().equals(lockedchest)) {
					drawTextBox("There seems to be a blue lock. Hmmmm, I might need a key...");
				}
				timer--;
				if (timer <= 0) {
					drawChest = false;
				}
			}
		}

		// Darken screen if player is dead
		if (player(masterPlayer).isDead()) {
			g.setColor(new Color(0, 0, 0, 80));
			g.fillRect(0, 0, GameFrame.screenWidth, GameFrame.screenHeight);
		}

		// Dialogue for places you can't drop items
		if (!canDrop) {
			if (timer > 0 && !player(masterPlayer).getInventory().isEmpty()) {
				drawTextBox("You can't drop an item there.");
				timer--;
			}
			if (timer <= 0) {
				canDrop = true;
				timer = 200;
			}
		}

	}

	/**
	 * Checks if any of the adjacent tiles are of type floor
	 * 
	 * @param x
	 *            X position of Tile
	 * @param y
	 *            Y position of Tile
	 * @return True if adjacent to Floor Tile, false otherwise
	 */
	public boolean nearFloor(int x, int y) {
		// Wals and door are not registered as neer floor
		if (map[x][y].getItem().getName().equals("wall")
				|| map[x][y].getItem().getName().equals("door")) {
			return false;
		}
		// Checks if adjacent to a floor tile
		if (x > 0 && map[x - 1][y].getItem().getName().equals("floor")) {
			return true;
		} else if (x < mapSize - 1
				&& map[x + 1][y].getItem().getName().equals("floor")) {
			return true;
		} else if (y > 0 && map[x][y - 1].getItem().getName().equals("floor")) {
			return true;
		} else if (y < mapSize - 1
				&& map[x][y + 1].getItem().getName().equals("floor")) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a Map of players bound by ID number keys
	 * 
	 * @return Map of Players
	 */
	public Map<Integer, Player> getPlayers() {
		return players;
	}

	/**
	 * Takes an image and displays the world on it
	 * 
	 * @param bi
	 *            Image to have world drawn on
	 */
	public void worldToImage(BufferedImage bi) {
		// Get graphics of image
		Graphics g = bi.getGraphics();
		// Print black background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		// Draw World
		redraw(g, bi.getWidth(), bi.getHeight());
	}

	/**
	 * Returns the map of tiles
	 * */
	public Tile[][] getMap() {
		return this.map;
	}

	/**
	 * Moves the player, according to what keys were pressed and checks if the
	 * move is valid or not. If the move is valid it moves the player, if not it
	 * doesn't
	 * 
	 * @param dir
	 *            the direction the player is being moved
	 */
	public void move(Direction dir, int uid) {
		Player p = players.get(uid);
		p.setDirection(dir);
		Tile tile = checkTile(dir, uid, 1);

		// Ghost
		if (p.isDead()) {
			// Move player
			p.move(dir);
		}

		if (tile != null) {
			GameObject tileItem = tile.getItem();
			String tileName = tileItem.getName();
			// If ghost and at a reviver, turns the ghost back into a slime.
			if (tileName.equals(reviver)) {
				p.setAlive();
			}
			// Player has to be alive...
			if (!p.isDead()) {
				// Move Boulders
				if (tileName.equals(boulder)) {
					((Boulder) tileItem).move(dir, uid, this); // Move boulder
					return;
				}
				// Player is in the boat
				if (checkTile(dir, uid, 0).getItem().getName().equals(boat)) {
					// Move player and boat
					((Boat) checkTile(dir, uid, 0).getItem()).move(dir, uid,
							this);
					if (!tileName.equals(tree) && !tileName.equals(wall)
							&& !tileName.equals(lockedchest)
							&& !tileName.equals(boulder)
							&& !tileName.equals(sign)) {
						p.move(dir);
					}
					return;
				}
				// Cannot move through tree, water or wall
				if (!tileName.equals(tree) && !tileName.equals(water)
						&& !tileName.equals(wall)
						&& !tileName.equals(lockedchest)
						&& checkDoor(uid, tileItem)
						&& !tileName.equals(boulder) && !tileName.equals(sign)
						&& !tileName.equals(mazeWall)) {

					// Pick up key
					if (tileName.equals(key) || tileName.equals(bluekey)
							|| tileName.equals(redkey)) {
						p.addItem((Item) tileItem);
						// Replace key with grass
						if (inside) {
							replaceTile(dir, uid, "sprites/floor.png", floor, 1);
						} else {
							replaceTile(dir, uid, "sprites/grass.png", grass, 1);
						}
					}

					// Move player
					p.move(dir);
				}
			}
		}
	}

	/**
	 * Checks if a door is open or not. (unlocked or locked) Returns true if the
	 * door is open, returns false if not.
	 * 
	 * @param uid
	 *            - player number.
	 * @param tile
	 *            - Item that is contained within the tile you are checking.
	 * @return true if unlocked, false if locked.
	 */
	public boolean checkDoor(int uid, GameObject tile) {
		if (tile.getName().equals(door)) {
			if (((Door) tile).getOpen()) {
				return true;
			} else if (((Door) tile).tryOpen(players.get(uid).getInventory())) {
				return true;
			}
			return false;

		} else {
			return true;
		}
	}

	/**
	 * Checks what the surrounding tile is given the direction, and returns it.
	 * Checks the tiles distance spaces away from the player.
	 * 
	 * Returns null if there are no tiles in the specified direction
	 * 
	 * @param dir
	 *            - Direction that the player will move
	 * @return Tile that is next to the player, given the direction, returns
	 *         null if there are no tiles in that direction
	 */
	public Tile checkTile(Direction dir, int uid, int distance) {
		// Find out where the player is in array
		Player p = players.get(uid);
		int pRow = p.getX();
		int pCol = p.getY();

		if (dir == Direction.DOWN && pCol + distance < mapSize
				&& pCol + distance >= 0) {
			Tile tile = map[pRow][pCol + distance];
			return tile;
		} else if (dir == Direction.UP && pCol - distance < mapSize
				&& pCol - distance >= 0) {
			Tile tile = map[pRow][pCol - distance];
			return tile;

		} else if (dir == Direction.RIGHT && pRow + distance < mapSize
				&& pRow + distance >= 0) {
			Tile tile = map[pRow + distance][pCol];
			return tile;

		} else if (dir == Direction.LEFT && pRow - distance < mapSize
				&& pRow - distance >= 0) {
			Tile tile = map[pRow - distance][pCol];
			return tile;
		}

		return null;
	}

	/**
	 * 
	 * Replaces the tile in the map where the player will move to with a grass
	 * tile. (used for picking up items)
	 * 
	 * @param dir
	 *            - Direction that the player will move to
	 * @param uid
	 *            - player identification
	 * @param image
	 *            - string of image location
	 * @param name
	 *            - name of image.
	 */
	public void replaceTile(Direction dir, int uid, String image, String name,
			int distance) {

		Player p = players.get(uid);
		int pRow = p.getX();
		int pCol = p.getY();

		if (dir == Direction.DOWN && pCol + distance < mapSize
				&& pCol + distance >= 0) {
			map[pRow][pCol + distance].setItem(new EnvironmentObject(image,
					name));
		} else if (dir == Direction.UP && pCol - distance < mapSize
				&& pCol - 1 >= 0) {
			map[pRow][pCol - distance].setItem(new EnvironmentObject(image,
					name));
		} else if (dir == Direction.RIGHT && pRow + distance < mapSize
				&& pRow + distance >= 0) {
			map[pRow + distance][pCol].setItem(new EnvironmentObject(image,
					name));
		} else if (dir == Direction.LEFT && pRow - distance < mapSize
				&& playerRow - distance >= 0) {
			map[pRow - distance][pCol].setItem(new EnvironmentObject(image,
					name));
		}

	}

	/**
	 * Adds a player to the game and the id assigned
	 * 
	 * @return ID number
	 */
	public int registerPlayer(int uid) {
		// Assigns sprite set based on ID
		String[] spriteSet = playerAssignChar[Math.abs(uid
				% playerAssignChar.length)];
		players.put(uid, new Player(spriteSet, "test", false, 4, 4));

		return uid;
	}

	/**
	 * Adds a player to the world in the given position with the ID given,
	 * returning the ID on successful add
	 * 
	 * @param uid
	 *            ID of player
	 * @param x
	 *            X position
	 * @param y
	 *            Y position
	 * @return ID of player
	 */
	public int registerPlayer(int uid, int x, int y) {
		// Assigns sprite set based on ID
		String[] spriteSet = playerAssignChar[Math.abs(uid
				% playerAssignChar.length)];
		players.put(uid, new Player(spriteSet, "test", false, x, y));

		return uid;
	}

	/**
	 * Called at each game iteration
	 */
	public void clockTick() {

		// Call clocktick on each player
		for (Player p : players.values()) {
			p.clockTick();

		}
		// Call clocktick on each NPC
		for (NPC npc : npcs) {
			npc.clockTick();
		}
		// Call clock tick on each enemy and check if in contact with player. If
		// in contact deal damage
		for (Enemy enemy : enemies) {
			enemy.clockTick();
			// Touching enemy
			for (Player player : players.values()) {
				if (player.getX() == enemy.getX()
						&& player.getY() == enemy.getY()) {
					player.decreaseHealth(5);
					player.setHit(true);
				}
			}
		}
		Player p = player(masterPlayer);
		if (p != null) {
			if (p.getY() - 1 < mapSize && p.getY() < mapSize
					&& p.getY() - 1 >= 0 && p.getY() >= 0) {
				Tile t = map[p.getX()][p.getY()];
				Tile tAbove = map[p.getX()][p.getY() - 1];

				if (t.getItem().getName().equals(door)
						|| tAbove.getItem().getName().equals(door)) {
					drawRoomLabel = true;
				}
			}
		}

	}

	/**
	 * Returns the width of the world
	 * 
	 * @return Width of the world
	 */
	public int width() {
		return mapSize;
	}

	/**
	 * Returns the height of the world
	 * 
	 * @return Height of the world
	 */
	public int height() {
		return mapSize;
	}

	/**
	 * Returns the player with the matching id
	 * 
	 * @param uid
	 *            ID to be checked for
	 * @return Player matching the ID, null otherwise
	 */
	public Player player(int uid) {
		return players.get(uid);
	}

	/**
	 * Returns the master player for this world
	 * 
	 * @return Player matching the masterID
	 */
	public Player masterPlayer() {
		return players.get(masterPlayer);
	}

	/**
	 * Remove the player with the corresponding id from the game
	 * 
	 * @param uid
	 *            ID to remove
	 */
	public void disconnectPlayer(int uid) {
		players.remove(uid);
	}

	/**
	 * Sets which ID corresponds to the master player on this world
	 * 
	 * @param uid
	 */
	public void setMasterPlayer(int uid) {
		masterPlayer = uid;
	}

	/**
	 * Check if a chest can be opened
	 * 
	 * @param uid
	 *            ID of player trying to open chest
	 * @param tile
	 *            Tile containing the chest
	 * @return True if can open, false otherwise
	 */
	public boolean checkChest(int uid, GameObject tile) {
		if (tile.getName().equals(lockedchest)) {
			if (((Container) tile).ifOpen()) {
				return true;
			} else if (((Container) tile).tryOpen(players.get(uid)
					.getInventory())) {
				return true;
			}
			return false;

		} else {
			return true;
		}
	}

	/**
	 * Method that interacts the player with the environment
	 * 
	 * @param uid
	 *            ID of player interacting
	 */
	public void interact(int uid) {
		Tile tile = checkTile(Direction.UP, uid, 1);
		// If tile does not exist then return
		if (tile == null) {
			return;
		}
		// If a container attempt to open
		if (tile.getItem() instanceof Container) {
			Container container = (Container) tile.getItem();
			if (container.getName().equals(lockedchest)) {
				if (checkChest(uid, tile.getItem())) {
					// Recieve gold for opening
					this.players.get(uid).addGold(container.getGold());
					container.interact();
				} else {
					drawChest = true;
					timer = 200;
				}
			}
		}

		// Read a sign
		else if (tile.getItem() instanceof Sign) {
			drawSign = true;
			timer = 200;
		}

		// Talk to any NPC's that are adjacent to the interaction
		for (NPC npc : npcs) {
			if ((npc.getX() == player(uid).getX() && npc.getY() == player(uid)
					.getY() - 1)
					|| (npc.getX() == player(uid).getX() && npc.getY() == player(
							uid).getY() + 1)
					|| (npc.getX() == player(uid).getX() + 1 && npc.getY() == player(
							uid).getY())
					|| (npc.getX() == player(uid).getX() - 1 && npc.getY() == player(
							uid).getY())) {
				npc.talk();
			}
		}
	}

	/**
	 * Drop the selected Item from an inventory
	 * 
	 * @param uid
	 *            ID of the player dropping an item
	 * @param pos
	 *            Position of the item in the inventory
	 */
	public void dropItem(int uid, int pos) {
		// Get tile in front of player
		Player p = players.get(uid);
		Tile infront = checkTile(p.direction, uid, 1);
		String current = infront.getItem().getName();

		// If grass or flowers then may drop the item
		if (current.equals(grass) || current.equals(flower)) {
			if (!p.getInventory().isEmpty() && p.getInventory().size() > pos) {
				Item i = p.getInventory().remove(pos);
				replaceTile(p.direction, uid, i, 1);
			}
		} else {
			canDrop = false;
		}
	}

	/**
	 * Replaces the tile in the map where the player will move to with item from
	 * inventory (used for picking up items)
	 * 
	 * @param dir
	 *            - Direction that the player will move to
	 * @param uid
	 *            - player identification
	 * @param item
	 *            - Item to be replaced
	 * @param distance
	 *            distance from player of tile replacement
	 */
	public void replaceTile(Direction dir, int uid, Item item, int distance) {
		Player p = players.get(uid);
		int pRow = p.getX();
		int pCol = p.getY();

		if (dir == Direction.DOWN && pCol + distance < mapSize
				&& pCol + distance >= 0) {
			map[pRow][pCol + distance].setItem(item);
		} else if (dir == Direction.UP && pCol - distance < mapSize
				&& pCol - distance >= 0) {
			map[pRow][pCol - distance].setItem(item);
		} else if (dir == Direction.RIGHT && pRow + distance < mapSize
				&& pRow + distance >= 0) {
			map[pRow + distance][pCol].setItem(item);
		} else if (dir == Direction.LEFT && pRow - distance < mapSize
				&& pRow - distance >= 0) {
			map[pRow - distance][pCol].setItem(item);
		}
	}

	/**
	 * Draws a bordered box containing text inside.
	 * 
	 * @param text
	 *            - Text to be displayed in text box
	 * @param g
	 *            - graphics canvas
	 */
	public void drawTextBox(String text) {
		graphics.setFont(new Font("default", Font.BOLD, 14));
		graphics.setColor(Color.black);
		graphics.fillRect(10, 10, 500, 120);
		graphics.setColor(Color.lightGray);
		graphics.fillRect(30, 30, 460, 80);
		graphics.setColor(Color.black);
		graphics.drawString(text, 60, 60);
	}
}
