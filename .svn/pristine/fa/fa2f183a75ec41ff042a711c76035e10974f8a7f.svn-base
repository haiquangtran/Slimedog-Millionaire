package networking;

import game.Player.Direction;
import game.World;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * A master connection receives events from a slave connection via a socket.
 * These events are registered with the world. The master connection is also
 * responsible for transmitting information to the slave about the current world
 * state.
 * 
 * @author Oliver Greenaway
 */
public final class Master extends Thread {
	public static World board;
	private final int broadcastClock;
	private List<Socket> sockets = new ArrayList<Socket>();
	private Object syncKey = new Object();

	/**
	 * Constructs a master on the server, takes input from slaves and changes
	 * the global world
	 * 
	 * @param broadcastClock
	 * @param board
	 */
	public Master(int broadcastClock, World game) {
		Master.board = game;
		this.broadcastClock = broadcastClock;
	}

	/**
	 * Executes the master loop. Retrieves input from slaves and changes the
	 * world state.
	 */
	public void run() {
		try {
			SaveThread saveThread = new SaveThread();
			saveThread.start();
			boolean exit = false;
			Socket disconnecting = null; // disconnecting client - press the x
											// exit in game
			Integer disUID = null;
			while (!exit) {
				try {
					synchronized (syncKey) {
						for (Socket s : sockets) {
							DataInputStream input = new DataInputStream(
									s.getInputStream());
							if (input.available() != 0) {
								// read direction event from client.
								int uid = input.readInt();
								int dir = input.readInt();
								int pos = -1;
								switch (dir) {
								case 1:
									board.move(Direction.UP, uid);
									break;
								case 2:
									board.move(Direction.DOWN, uid);
									break;
								case 3:
									board.move(Direction.RIGHT, uid);
									break;
								case 4:
									board.move(Direction.LEFT, uid);
									break;
								case 5:
									board.interact(uid);
									break;
								case 6:
									pos = input.readInt();
									board.dropItem(uid, pos);
									break;
								case -2:
									disconnecting = s;
									disUID = uid;
									break;
								}

								for (Socket out : sockets) {
									if (disconnecting != null) {
										if (out == disconnecting) {
											continue;
										}
									} // the client may already be gone
									DataOutputStream output = new DataOutputStream(
											out.getOutputStream());
									output.writeInt(uid);
									output.writeInt(dir);
									if (pos != -1) {
										output.writeInt(pos);
									}
									output.flush();

								}
							}
						}
						if (disconnecting != null) {
							sockets.remove(disconnecting);
							System.out.println("DISCONNECT CONNECTION FROM "
									+ disconnecting.getInetAddress());
							disconnecting.close();
							disconnecting = null;
							board.disconnectPlayer(disUID);
							disUID = null;
						}
					}
					Thread.sleep(broadcastClock);
				} catch (InterruptedException e) {
				}
			}
			for (Socket s : sockets) {
				s.close();
			}
		} catch (IOException e) {
		}
	}

	/**
	 * Add a socket to the list of open sockets and send the current game state
	 * to the new Client
	 * 
	 * @param socket
	 *            The socket to be added
	 */
	public void addSocket(Socket socket) {
		synchronized (syncKey) {
			sockets.add(socket);
			try {
				ObjectOutputStream output = new ObjectOutputStream(
						socket.getOutputStream());
				DataInputStream in = new DataInputStream(
						socket.getInputStream());
				int uid = in.readInt();
				// Sends the world state
				output.writeObject(board);
				output.flush();
				// Sends all players positions
				output.writeInt(board.getPlayers().size());
				for (Integer i : board.getPlayers().keySet()) {
					output.writeInt(i);
					output.writeInt(board.getPlayers().get(i).getX());
					output.writeInt(board.getPlayers().get(i).getY());
				}
				output.flush();
				// Register the player
				board.registerPlayer(uid);
				// Send new details to all existing clients
				for (Socket s : sockets) {
					try {
						if (s != socket) {
							DataOutputStream out = new DataOutputStream(
									s.getOutputStream());
							out.writeInt(uid);
							out.writeInt(-1);
							out.flush();
						}
					} catch (SocketException e) {

					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
