package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import util.ImageLibrary;

/**
 * The display for the menu of the game Client
 * 
 * @author Oliver Greenaway
 * 
 */
public class MenuCanvas extends Canvas {

	private static final long serialVersionUID = 2567796968944997800L;

	private JLabel titleLabel;
	private JLabel backDrop;
	private JLabel hostnameLabel;
	private JLabel usernameLabel;
	private JLabel hostnameErrorLabel;
	private JLabel usernameErrorLabel;
	private JTextField hostnameTextBox;
	private JTextField usernameTextBox;
	private JButton joinButton;
	private JButton quitButton;

	private GameFrame parent;

	/**
	 * Constructs a new Menu with the given width and height
	 * 
	 * @param width
	 *            The Width of the canvas
	 * @param height
	 *            The Height of the canvas
	 * @param parent
	 *            GameFrame containing the canvas
	 */
	public MenuCanvas(int width, int height, GameFrame parent) {
		super(width, height);
		this.parent = parent;
		// Initialises labels, textFields and buttons
		titleLabel = new JLabel();
		backDrop = new JLabel();
		hostnameLabel = new JLabel();
		usernameLabel = new JLabel();
		hostnameErrorLabel = new JLabel();
		usernameErrorLabel = new JLabel();
		hostnameTextBox = new JTextField(10);
		usernameTextBox = new JTextField(10);
		joinButton = new JButton();
		quitButton = new JButton();

		// Initialise image on backDrop, title and background
		backDrop.setIcon(new ImageIcon(ImageLibrary.get("Menu/menuBacking.gif")));
		titleLabel
				.setIcon(new ImageIcon(ImageLibrary.get("Menu/GameTitle.gif")));

		// Assigns the text labels the image text needed
		hostnameLabel.setIcon(new ImageIcon(ImageLibrary
				.get("Menu/HostnameLabel.png")));
		usernameLabel.setIcon(new ImageIcon(ImageLibrary
				.get("Menu/UsernameLabel.png")));

		// Changes the font of the text boxes and error labels
		Font textFont = new Font("Verdana", Font.BOLD, 20);
		Font errorTextFont = new Font("Verdana", Font.ITALIC, 10);
		hostnameTextBox.setFont(textFont);
		usernameTextBox.setFont(textFont);
		hostnameErrorLabel.setFont(errorTextFont);
		usernameErrorLabel.setFont(errorTextFont);
		hostnameErrorLabel.setForeground(Color.RED);
		usernameErrorLabel.setForeground(Color.RED);
		// Changes the borders on the textFields
		hostnameTextBox.setBorder(new BevelBorder(BevelBorder.RAISED));
		usernameTextBox.setBorder(new BevelBorder(BevelBorder.RAISED));

		// Changes the look of each of the buttons
		joinButton.setIcon(new ImageIcon(ImageLibrary
				.get("Menu/JoinButton.png"), ""));
		joinButton.setPressedIcon(new ImageIcon(ImageLibrary
				.get("Menu/JoinButtonP.png")));
		joinButton.setBorder(null);
		joinButton.setOpaque(false);
		joinButton.setContentAreaFilled(false);
		joinButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		quitButton.setIcon(new ImageIcon(ImageLibrary
				.get("Menu/QuitButton.png"), ""));
		quitButton.setPressedIcon(new ImageIcon(ImageLibrary
				.get("Menu/QuitButtonP.png")));
		quitButton.setBorder(null);
		quitButton.setOpaque(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Assigns the joinButton to join a game when clicked
		joinButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				joinGame();
			}
		});

		// Assigns the quitButton to quit the game when clicked
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});

		// Set the position of each of the components on the screen
		this.setLayout(null);
		int prevHeight = 100;
		Dimension size = titleLabel.getPreferredSize();
		titleLabel.setBounds(this.getWidth() / 2 - size.width / 2,
				prevHeight - 20, size.width, size.height);
		prevHeight += size.height;
		size = backDrop.getPreferredSize();
		backDrop.setBounds(this.getWidth() / 2 - size.width / 2,
				prevHeight - 20, size.width, size.height);
		size = hostnameLabel.getPreferredSize();
		hostnameLabel.setBounds(this.getWidth() / 2 - size.width / 2,
				prevHeight, size.width, size.height);
		prevHeight += size.height;
		size = hostnameTextBox.getPreferredSize();
		hostnameTextBox.setBounds(this.getWidth() / 2 - size.width / 2,
				prevHeight, size.width, size.height);
		hostnameErrorLabel.setBounds(this.getWidth() / 2 - size.width / 2,
				prevHeight + size.height, size.width, size.height / 2);
		prevHeight += size.height + 20;
		size = usernameLabel.getPreferredSize();
		usernameLabel.setBounds(this.getWidth() / 2 - size.width / 2,
				prevHeight, size.width, size.height);
		prevHeight += size.height;
		size = usernameTextBox.getPreferredSize();
		usernameTextBox.setBounds(this.getWidth() / 2 - size.width / 2,
				prevHeight, size.width, size.height);
		usernameErrorLabel.setBounds(this.getWidth() / 2 - size.width / 2,
				prevHeight + size.height, size.width, size.height / 2);
		prevHeight += size.height + 20;
		size = joinButton.getPreferredSize();
		joinButton.setBounds(this.getWidth() / 2 - size.width, prevHeight,
				size.width, size.height);
		size = quitButton.getPreferredSize();
		quitButton.setBounds(this.getWidth() / 2, prevHeight, size.width,
				size.height);

		// Add all components to the canvas
		this.add(titleLabel);
		this.add(hostnameLabel);
		this.add(hostnameTextBox);
		this.add(hostnameErrorLabel);
		this.add(usernameLabel);
		this.add(usernameTextBox);
		this.add(usernameErrorLabel);
		this.add(joinButton);
		this.add(quitButton);
		this.add(backDrop);

		// Display the canvas
		this.repaint();
	}

	/**
	 * Ensures the inputs on the menu exist and requests that the graphics
	 * change to the game on the server given
	 */
	private void joinGame() {
		String host = hostnameTextBox.getText();
		String username = usernameTextBox.getText();
		hostnameErrorLabel.setText("");
		usernameErrorLabel.setText("");
		boolean error = false;
		if (host.length() == 0) {
			hostnameErrorLabel.setText("Hostname required");
			error = true;
		}
		if (username.length() == 0) {
			usernameErrorLabel.setText("Username required");
			error = true;
		}
		if (error) {
			this.repaint();
			return;
		} else {
			parent.changeToGameState(host, username);
			parent.repaint();
		}
	}

}
