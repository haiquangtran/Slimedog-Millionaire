package networking;

import game.Player.Direction;
import game.World;
import gui.GameFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * A slave connection receives information about the current state of the world
 * and relays that into the local copy of the world. The slave connection also
 * notifies the master connection of key presses by the player.
 */
public final class Slave extends Thread implements KeyListener, MouseListener {
	private final Socket socket;
	private DataOutputStream output;
	private InputStream input;
	private int uid;
	private GameFrame display;

	/**
	 * Construct a slave connection from a socket. A slave connection does no
	 * local computation, other than to display the current state of the world;
	 * instead, logic is controlled entirely by the server, and the slave
	 * display is only refreshed when data is received from the master
	 * connection.
	 * 
	 * @param socket
	 * @param display
	 * @param uid
	 */
	public Slave(Socket socket, int uid) {
		this.socket = socket;
		this.display = new GameFrame(false);
		this.display.gameCanvas.addKeyListener(this);
		this.display.gameCanvas.addMouseListener(this);
		this.uid = uid;
	}

	/**
	 * Runs the slave updating the players view of the game on server state
	 * change
	 */
	public void run() {
		try {
			output = new DataOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());

			System.out.println("CLIENT UID: " + uid);
			System.out.println("CLIENT WORLD DIMENSIONS: 70 x 70");
			boolean exit = false;

			try {
				display.gameCanvas.world = (World) ((ObjectInputStream) input)
						.readObject();
				int players = ((ObjectInputStream) input).readInt();
				for (int i = 0; i < players; i++) {
					int uid = ((ObjectInputStream) input).readInt();
					int x = ((ObjectInputStream) input).readInt();
					int y = ((ObjectInputStream) input).readInt();
					display.gameCanvas.world.registerPlayer(uid, x, y);
				}
				GameClock clk = new GameClock(20, display.gameCanvas.world,
						null);
				clk.start();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			display.gameCanvas.world.registerPlayer(uid);
			display.gameCanvas.world.setMasterPlayer(uid);
			display.repaint();

			input = new DataInputStream(socket.getInputStream());

			while (!exit) {
				// read event
				if (!display.isShowing()) {
					output.writeInt(uid);
					output.writeInt(-2);
					output.flush();
					System.exit(0);
				}

				if (input.available() != 0) {
					int uid = ((DataInputStream) input).readInt();
					int command = ((DataInputStream) input).readInt();
					switch (command) {
					case 1:

						display.gameCanvas.world.move(Direction.UP, uid);
						break;
					case 2:
						display.gameCanvas.world.move(Direction.DOWN, uid);
						break;
					case 3:
						display.gameCanvas.world.move(Direction.RIGHT, uid);
						break;
					case 4:
						display.gameCanvas.world.move(Direction.LEFT, uid);
						break;
					case 5:
						display.gameCanvas.world.interact(uid);
						break;
					case 6:
						int pos = ((DataInputStream) input).readInt();
						display.gameCanvas.world.dropItem(uid, pos);
						break;
					case -1:
						display.gameCanvas.world.registerPlayer(uid);
						break;
					case -2:
						display.gameCanvas.world.disconnectPlayer(uid);
					}
				}

				display.repaint();
			}
			socket.close(); // release socket
		} catch (IOException e) {
			System.err.println("I/O Error: " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			int code = e.getKeyCode();
			if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {
				output.writeInt(uid);
				output.writeInt(3);
			} else if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
				output.writeInt(uid);
				output.writeInt(4);
			} else if (code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP) {
				output.writeInt(uid);
				output.writeInt(1);
			} else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_KP_DOWN) {
				output.writeInt(uid);
				output.writeInt(2);
			} else if (code == KeyEvent.VK_I) {
				display.gameCanvas.toggleInventory();
			} else if (code == KeyEvent.VK_SPACE) {
				output.writeInt(uid);
				output.writeInt(5);
			}
			output.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			// something went wrong trying to communicate the key press to the
			// server. So, we just ignore it.
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getX() >= display.getWidth() - 100 && e.getY() <= 40) {
			display.gameCanvas.toggleInventory();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!display.gameCanvas.getInventory())
			return;
		int x = e.getX();
		int y = e.getY();
		if (x >= display.gameCanvas.getWidth() - 300 && y >= 50 && y <= 550) { // on
																				// the
																				// inventory
																				// area.
			int col = (x - (display.gameCanvas.getWidth() - 300)) / 50;
			int row = (y - 50) / 50;
			int pos = col + row * 6;
			try {
				output.writeInt(uid);
				output.writeInt(6);
				output.writeInt(pos);
				output.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
