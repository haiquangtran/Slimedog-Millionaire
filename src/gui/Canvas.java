package gui;

import java.awt.Dimension;

import javax.swing.JPanel;


/**
 * Drawing canvas extending JPanel
 * @author Oliver Greenaway
 *
 */
public class Canvas extends JPanel {

	private static final long serialVersionUID = -2438216440815164201L;

	/**
	 * Creates a new Drawing Canvas with the given width and height
	 * @param width Width of the Canvas
	 * @param height Height of the Canvas
	 */
	public Canvas(int width, int height){
		super();
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(new Dimension(width, height));
	}

}
