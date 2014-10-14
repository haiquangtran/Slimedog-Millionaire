package datastorage;

import game.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * A class the has a set of static methods to be used to store/load/print the
 * xml data of the game state
 */
public class StoreData {
	/**
	 * Prints the current game state
	 * 
	 * @param world
	 *            World to be printed
	 * 
	 */
	public static void printGameState(World world) {
		XStream xStream = new XStream(new DomDriver());
		System.out.println(xStream.toXML(world));
	}

	/**
	 * Saves the current game state
	 * 
	 * @param world
	 *            World to be saved
	 * */
	public static void saveGameState(World world) {
		XStream xStream = new XStream(new DomDriver());
		try {
			FileOutputStream saveFile = new FileOutputStream(new File(
					"file.xml"));
			xStream.toXML(world, saveFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the current game state
	 * 
	 * @param fileIn
	 *            File that contains a saved World state
	 * @return Generated World from file
	 * */
	public static World loadGameState(FileInputStream fileIn) {
		XStream xs = new XStream(new DomDriver());
		World world = (World) xs.fromXML(fileIn);
		return world;
	}

}
