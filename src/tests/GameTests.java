package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import game.Boat;
import game.Boulder;
import game.Door;
import game.Enemy;
import game.EnvironmentObject;
import game.Item;
import game.Key;
import game.Player;
import game.Player.Direction;
import game.Tile;
import game.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;

import datastorage.StoreData;

/**
 * Tests written for the game.
 * 
 * @author Hai Tran, Marcel Blokker
 * 
 */
public class GameTests {

	/**
	 * Test for the movement of enemies
	 */
	@Test
	public void testEnemy() {
		String[] sponge = new String[] { "sprites/enemies/spongeFront.png",
				"sprites/enemies/spongeLeft.png",
				"sprites/enemies/spongeRight.png",
				"sprites/enemies/spongeBack.png" };
		Enemy enemy = new Enemy(sponge, "", true, 50, 50, new Direction[] {
				Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT,
				Direction.DOWN, Direction.RIGHT, Direction.RIGHT,
				Direction.RIGHT, Direction.RIGHT, Direction.UP });
		
		//testing up
		int prevY = enemy.getY();
		enemy.move(Direction.UP);
		int newY = enemy.getY();
		assertTrue("failed @ testEnemy testing move up", newY == (prevY-1));
	}

	/**
	 * Tests the door being unlocked by specific keys
	 */
	@Test
	public void DoorUnlock() {
		Door door = new Door("", "normal", false, World.key);
		assertFalse("the door should be locked ", door.getOpen());
		Key keyValid = new Key("", World.key, true);
		Key keyInvalid = new Key("", World.bluekey, true);
		Key keyInvalid2 = new Key("", World.redkey, true);
		ArrayList<Item> keys = new ArrayList<Item>();
		door.tryOpen(keys);
		assertFalse("The door should stay locked when no keys are given",
				door.getOpen());
		keys.add(keyInvalid);
		door.tryOpen(keys);
		assertFalse("The door should not be opened by the wrong key",
				door.getOpen());
		keys.add(keyInvalid2);
		door.tryOpen(keys);
		assertFalse("The door should not be opened by the wrong key",
				door.getOpen());
		keys.add(keyValid);
		door.tryOpen(keys);
		assertTrue("The door should be opened", door.getOpen());
	}

	/**
	 * Tests the players movements
	 */
	@Test
	public void PlayerMove() {
		Player player = new Player(null, "bob", false, 5, 5);
		assertTrue("player should be at x = 5", player.getX() == 5);
		assertTrue("player should be at y = 5", player.getY() == 5);
		player.move(Direction.DOWN);
		assertTrue("player should be at y=6", player.getY() == 6);
		player.move(Direction.RIGHT);
		assertTrue("player should be at x=6", player.getX() == 6);
		player.move(Direction.LEFT);
		assertTrue("player should be at X=5", player.getX() == 5);
		player.move(Direction.UP);
		assertTrue("player should be at y=5", player.getY() == 5);
	}

	/**
	 * Tests the adding of items to a players inventory
	 */
	@Test
	public void playerAddItem() {
		Player p = new Player(null, "", false, 5, 5);
		p.addItem(new Key("", "", true));
		assertTrue("Players inventory should not be empty", !p.getInventory()
				.isEmpty());
	}

	/**
	 * Tests the world, testing the adding of multiple players and the dropping
	 * and picking up of items
	 */
	@Test
	public void World() {
		World w = new World();
		assertTrue("world should have no players", w.getPlayers().isEmpty());
		w.registerPlayer(5);
		assertTrue("World should contain one player uid = 5", w.getPlayers()
				.containsKey(5));
		w.registerPlayer(2, 5, 20);
		Player p = w.getPlayers().get(2);
		assertTrue("Player uid = 2 should be at x = 5", p.getX() == 5);
		assertTrue("Player uid = 2 should be at y = 20", p.getY() == 20);
		Item i = new Key("", "key", true);
		w.replaceTile(Direction.DOWN, 2, i, 1);
		w.move(Direction.DOWN, 2);
		assertTrue("Player should have an item in their inventory", !w
				.getPlayers().get(2).getInventory().isEmpty());
		assertTrue(
				"player should have a key in inventory",
				w.getPlayers().get(2).getInventory().get(0).getName()
						.equals("key"));
		w.dropItem(2, 0);
		assertTrue("players inventory should be empty from drop", w
				.getPlayers().get(2).getInventory().isEmpty());
	}

	/**
	 * Tests the replacing of a tile
	 */
	@Test
	public void world2() {
		World w = new World();
		w.registerPlayer(1, 5, 5);
		Item i = new Key("", "key", true);
		w.replaceTile(Direction.DOWN, 1, i, 1);
		Tile t = w.checkTile(Direction.DOWN, 1, 1);
		assertTrue("Tile should contain the replaced item", t.getItem() == i);
		w.registerPlayer(2);
		assertTrue("Player should be in the world", w.getPlayers().containsKey(2));
		w.disconnectPlayer(2);
		assertTrue("Player should be removed from disconnect", !w.getPlayers().containsKey(2));
	}

	/**
	 * Tests the loading of a game
	 */
	@Test
	public void testLoad() {
		World world = new World();
		world.test = 500;
		StoreData.saveGameState(world);
		try {
			assertTrue("failed world load",
					world.test == StoreData.loadGameState(new FileInputStream(
							new File("file.xml"))).test);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Testing boat and movements
	 */
	@Test
	public void testBoat() {
		World world = new World();
		world.registerPlayer(1, 30, 60); // on water (can only move on water)
		Boat boat = new Boat(null, "boat", false);
		boat.move(Direction.UP, 1, world);
		boat.move(Direction.LEFT, 1, world);
		boat.move(Direction.RIGHT, 1, world);
		boat.move(Direction.DOWN, 1, world);
		world.registerPlayer(2, 25, 25); // Not on water (boat can only move on
											// water)
		Boat boat2 = new Boat(null, "boat", false);
		boat2.move(Direction.DOWN, 2, world);
		Tile t = world.checkTile(Direction.DOWN, 2, 1);
		assertTrue("Boat can't move on grass, only on water.", t.getItem()
				.getName() != "boat");
	}

	/**
	 * Testing boulder and movements
	 */
	@Test
	public void testBoulders() {
		World world = new World();
		world.registerPlayer(1, 29, 48);
		Boulder boulder = new Boulder(null, "", false); // Able to move 2 steps
		boulder.move(Direction.UP, 1, world);
		boulder.move(Direction.LEFT, 1, world);
		boulder.move(Direction.RIGHT, 1, world); // Move 1 step
		boulder.move(Direction.RIGHT, 1, world);
		boulder.move(Direction.RIGHT, 1, world);
		boulder.move(Direction.DOWN, 1, world);
		world.registerPlayer(2, 58, 48); // On Water
		Boulder boulder2 = new Boulder(null, "boulder", false);// Unable to move
		Tile t = world.checkTile(Direction.DOWN, 2, 1);
		boulder.move(Direction.DOWN, 1, world);
		assertTrue("Boulder can't be pushed on water.",
				t.getItem().getName() != "boulder");
	}

	/**
	 * Tests container objects
	 */
	@Test
	public void testContainer() {
		ArrayList<Item> items = new ArrayList<Item>();
		game.Container contain = new game.Container(null, "container", items,
				false);
		contain.additem(new Key(null, null, true));
		assertTrue("Container should have 1 item.",
				contain.getItems().size() == 1);
		contain.removeAll();
		assertTrue("Container should have no items.", contain.getItems()
				.isEmpty());
		assertTrue("container should contain more than 999,999 gold",
				contain.getGold() > 999999);
		assertTrue("container should not be open.", !contain.ifOpen());
		assertFalse("No items in container.", contain.tryOpen(items));
		items.add(new Key(null, World.bluekey, true));
		assertTrue(contain.tryOpen(items));
		contain.interact();
		assertTrue("container should be open.", contain.ifOpen());
		contain.interact();
		assertTrue("container should be closed.", !contain.ifOpen());
		ArrayList<Item> items2 = new ArrayList<Item>();
		Key key = new Key(null, game.World.bluekey, false);
		items2.add(key);
		game.Container container = new game.Container(null, "container",
				items2, false);
	}

	/**
	 * Tests door objects
	 */
	@Test
	public void testDoor() {
		Door door = new Door(null, "door", true, World.bluekey);
		door.setOpen(false);
		assertTrue("door should be closed.", !door.getOpen());
		assertTrue("door should only be opened by bluekey.", door.getKey()
				.toString() == World.bluekey);
	}

	/**
	 * Test Game Objects
	 */
	@Test
	public void testGameObjects() {
		Key key = new Key(null, World.bluekey, true);
		assertTrue("Should be true", key.isPickUp());
		assertTrue("Should be null", key.getImage() == null);
		assertTrue("Should be bluekey", key.toString().equals(World.bluekey));
	}

	/**
	 * Test Environment Objects
	 */
	@Test
	public void testEnvironmentObject() {
		EnvironmentObject environ = new EnvironmentObject(null, "tree");
		environ.interact(); // shouldnt do anything.
	}

	/**
	 * Test the game world itself and the methods inside of it.
	 */
	@Test
	public void testWorld1() {
		World world = new World();
		world.registerPlayer(1, 0, 0);
		assertTrue("no floor tiles should be near.", !world.nearFloor(5, 5));
		assertTrue(
				"floor tiles should be near.",
				world.nearFloor(51, 19) && world.nearFloor(57, 31)
						&& world.nearFloor(59, 31) && world.nearFloor(53, 31));
		assertFalse("Should return false because on door.",
				world.nearFloor(59, 32));
		assertTrue("No tiles there, should return null",
				world.checkTile(Direction.UP, 1, 2) == null);
	}

	@Test
	public void testWorld2() { // dropping keys in incorrect places
		World world = new World();
		world.registerPlayer(1, 12, 53);
		world.getPlayers().get(1).addItem(new Key(null, "key", true));
		world.getPlayers().get(1).move(Direction.UP);
		world.dropItem(1, 0);
		assertTrue("Player cant drop key on wall.", !world.getPlayers().get(1)
				.getInventory().isEmpty());
	}

	@Test
	public void testWorld3() {
		World world = new World();
		world.registerPlayer(1, 18, 61);
		assertTrue("height of map should be 70", world.height() == 70);
		assertTrue("width of map should be 70", world.width() == 70);
	}

	@Test
	public void testWorld4() {
		World world = new World();
		assertTrue("There should be no master player.",
				world.masterPlayer() == null);
		world.registerPlayer(1, 18, 61);
		world.interact(1);
		world.setMasterPlayer(1);
		assertTrue("Master player should be player 1.",
				world.masterPlayer() == world.getPlayers().get(1));
		world.disconnectPlayer(1);
		world.clockTick();
	}

	@Test
	public void testWorld5() { // Open chest
		World world = new World();
		world.registerPlayer(1, 34, 13);
		world.getPlayers().get(1).getInventory()
				.add(new Key(null, World.bluekey, true));
		world.interact(1);
		assertTrue(
				"Player should have received over 1 mil gold for opening chest.",
				world.getPlayers().get(1).getGold() > 999999);
		world.interact(1);
	}

	@Test
	public void testWorld6() { // Read sign
		World world = new World();
		world.registerPlayer(1, 9, 22);
		world.interact(1);
		world.registerPlayer(2, 34, 13);
		world.interact(2);			//Open locked chest without key
		assertFalse("Player cannot open chest."
				,world.getPlayers().get(1).getGold() > 999999);
		world.registerPlayer(3, 25, 25);
		world.interact(3);		
		assertTrue("should return true, cannot check something that isnt a " +
				"chest.",world.checkChest(1, world.getMap()[34][16].getItem()));
	}
	
	@Test
	public void testWorld7(){	//Open a locked door
		World world = new World();
		world.registerPlayer(1, 58, 33);
		assertFalse("Door should be locked. ",world.checkDoor(1, world.getMap()[58][32].getItem()));
		world.getPlayers().get(1).getInventory().add(new Key(null, World.bluekey, false));
		world.getPlayers().get(1).move(Direction.UP);
		assertTrue("Door should no longer be locked.", world.checkDoor(1, world.getMap()[58][32].getItem()));
		world.registerPlayer(2, 58, 33);
		world.getPlayers().get(1).move(Direction.UP);
		assertTrue("Door should no longer be locked.", world.checkDoor(1, world.getMap()[58][32].getItem()));
	}
	@Test
	public void testWorld8(){	//set positions
		World world = new World();
		world.registerPlayer(1, 1, 1);
		world.setMasterPlayer(1);
		world.setPlayerPositionFields();
		assertTrue("player position should be 1,1.", 
				world.getPlayers().get(1).getX() == 1 && world.getPlayers().get(1).getY() ==1);
	}
	@Test
	public void testWorld9(){	//Testing replace tile
		World world = new World();
		world.registerPlayer(1,2,2);
		world.replaceTile(Direction.DOWN, 1, new Key(null, World.bluekey, true), 1);
		assertTrue("Should be blue key",world.getMap()[2][3].getItem().getName().equals(World.bluekey));	
		world.replaceTile(Direction.LEFT, 1, new Key(null, World.bluekey, true), 1);
		assertTrue("Should be blue key",world.getMap()[1][2].getItem().getName().equals(World.bluekey));	
		world.replaceTile(Direction.RIGHT, 1, new Key(null, World.bluekey, true), 1);
		assertTrue("Should be blue key",world.getMap()[3][2].getItem().getName().equals(World.bluekey));	
		world.replaceTile(Direction.UP, 1, new Key(null, World.bluekey, true), 1);
		assertTrue("Should be blue key",world.getMap()[2][1].getItem().getName().equals(World.bluekey));	
	}
	
	@Test
	public void testLogic1(){	//Testing player in reviver
		World world = new World();
		world.registerPlayer(1,63,2);	//on reviver
		world.getPlayers().get(1).move(Direction.DOWN);
		assertTrue("Player should be out of reviver.",
				world.player(1).getX() == 63 && world.player(1).getY() == 3);
		world.getPlayers().get(1).move(Direction.UP);
	}
	@Test
	public void testLogic2(){	//Testing player moving boulder
		World world = new World();
		world.registerPlayer(1,33,11);	//just before a boulder
		world.move(Direction.UP, 1);
		assertTrue("Player shouldn't move. ",
				world.player(1).getX() == 33 && world.player(1).getY() == 11);
		assertTrue("Boulder Should have moved", world.checkTile(Direction.UP, 1, 2).
				getItem().getName().equals(World.boulder));
	}
	@Test
	public void testLogic3(){	//Testing player moving Boat
		World world = new World();
		world.registerPlayer(1,17,61);	//On Boat
		world.move(Direction.RIGHT, 1);
		assertTrue("Player should have moved. ",
				world.player(1).getX() == 18 && world.player(1).getY() == 61);
		assertTrue("Boat should have moved with player.", world.checkTile(Direction.RIGHT, 1, 0).
				getItem().getName().equals(World.boat));
	}
	
	@Test
	public void testLogic4(){	//Testing player getting revived
		World world = new World();
		world.registerPlayer(1,63,3);	//Infront of Reviver
		world.player(1).decreaseHealth(Player.maxHp);
		assertTrue("Health should be 0.",world.player(1).getHealth() == 0);
		world.move(Direction.UP, 1);	//go into reviver
		assertTrue("Health should be full.", world.player(1).getHealth() == Player.maxHp);
	}

}

