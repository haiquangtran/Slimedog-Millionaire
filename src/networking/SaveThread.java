package networking;

import datastorage.StoreData;

/**
 * A Thread that saves a World state every 10 seconds
 * 
 */
public class SaveThread extends Thread {

	/**
	 * Saves the Master world every 10 seconds
	 */
	public void run() {
		long time = System.currentTimeMillis();
		while (true) {
			if ((System.currentTimeMillis() - time) > 10000) {
				time = System.currentTimeMillis();
				StoreData.saveGameState(Master.board);
			}
		}
	}
}
