package gui;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

import main.Main;

/**
 * Contains the main GUI for displaying the game
 * 
 * @author Oliver Greenaway
 * 
 */
public class GameFrame extends JFrame {

	private static final long serialVersionUID = -75532683488941715L;

	// screen dimensions
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	public static final int screenWidth = (int) tk.getScreenSize().getWidth();
	public static final int screenHeight = (int) tk.getScreenSize().getHeight();

	private final String gameName = "SLIMEDOG MILLIONAIRE";

	public MenuCanvas menuCanvas;
	public GameCanvas gameCanvas;

	/**
	 * Creates a Frame in which to display either the menu or the game canvas
	 * 
	 * @param menu
	 *            Whether to display the Menu
	 */
	public GameFrame(boolean menu) {
		super();
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);

		// Set JFrame size to screen width and height
		this.setTitle(gameName);
		this.setSize(tk.getScreenSize());
		this.setResizable(true);

		// Setup menu and game canvas objects
		menuCanvas = new MenuCanvas(this.getWidth(), this.getHeight(), this);
		gameCanvas = new GameCanvas(this.getWidth(), this.getHeight(), this);

		// Setup canvas defaults
		menuCanvas.setBackground(Color.BLACK);
		gameCanvas.setBackground(Color.WHITE);

		// Set visibility of canvas objects
		menuCanvas.setVisible(menu);
		gameCanvas.setVisible(!menu);

		// Add Canvas objects to JFrame
		this.add(menuCanvas);
		this.add(gameCanvas);

		gameCanvas.setFocusable(true);

		this.setVisible(true);
	}

	/**
	 * Changes the view to the game and joins the game as the given host with
	 * the user name passed
	 * 
	 * @param host
	 *            The host of the server to join
	 * @param username
	 *            The user name to sign in with
	 */
	public void changeToGameState(String host, String username) {
		menuCanvas.setVisible(false);
		gameCanvas.setVisible(true);

		this.dispose();
		try {
			Main.runClient(host, this, username.hashCode());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Change to the menu state
	 */
	public void changeToMenuState() {
		menuCanvas.setVisible(true);
		gameCanvas.setVisible(false);
	}
}
