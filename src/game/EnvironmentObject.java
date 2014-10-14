package game;

import java.io.Serializable;

/**
 * Immovable scenery objects.
 * 
 * @author Marcel Blokker
 * 
 */
public class EnvironmentObject extends GameObject implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new Enviroment Object with the given Image and name
	 * @param image Object Image
	 * @param name Object Name
	 */
	public EnvironmentObject(String image, String name) {
		super(image, name, false);
	}

	/**
	 * Attempt to interact with Object
	 */
	public void interact() {
		return;
	}

}
