package drawableObject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

public class Furniture implements Cloneable {
	private String name;
	private String description;
	private Dimension dimension;
	private String picture;
	//rotation degree
	private double orientation;
	//the upper-left corner position
	private Point position;
	private boolean locked;
	private boolean visible;
	
	private Image loadedImage;
	
	private Color color;
	
	private FurnitureLibrary library;
	private DefaultMutableTreeNode jTreeNode;
	
	
	public Furniture(String name, String description, String picture, Dimension dimension, Point position, double orientation, boolean locked, boolean visible, FurnitureLibrary library, Color color) {
		this.name = name;
		this.description = description;
		this.dimension = dimension;
		this.picture = picture;
		this.orientation = orientation;
		this.position = position;
		this.locked = locked;
		this.visible = visible;
		this.library = library;
		this.color = color;
		
		this.jTreeNode = new DefaultMutableTreeNode(name);
		
		try {
			loadedImage = ImageIO.read(new File(picture));
		} catch (IOException e) {
			loadedImage = new BufferedImage(
					dimension.width, dimension.height,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D bGr = (Graphics2D) loadedImage.getGraphics();
			bGr.setColor(Color.BLACK);
			bGr.fillRect(0, 0, dimension.width, dimension.height);
			bGr.dispose();
		}
	}

	public Furniture(String name, String description, String picture, Dimension dimension, Point position, FurnitureLibrary library, Color color) {
		this(name, description, picture, dimension, position, 0, false, true, library, color);
	}
	
	public Furniture clone() { 
		Furniture f = null; 
		try { 
			f = (Furniture) super.clone(); 
		} 
		catch (CloneNotSupportedException e) { } 
		return f; 
	}

	public Dimension getDimension() {
		return dimension;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	public boolean getLocked() {
		return locked;
	}
	
	public double getOrientation() {
		return orientation;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Image getLoadedPicture() {
		return loadedImage;
	}
	
	public boolean contains(int x, int y) {
		return x >= position.x && x <= position.x + dimension.width && y >= position.y && y <= position.y + dimension.height;
	}

	public FurnitureLibrary getLibrary() {
		return library;
	}

	public DefaultMutableTreeNode getJtreeNode() {
		return jTreeNode;
	}

	public Color getColor() {
		return color;
	}
}
