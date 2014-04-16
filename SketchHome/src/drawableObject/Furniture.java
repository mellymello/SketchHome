package drawableObject;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.text.Position;

public class Furniture {
	private String name;
	private String description;
	private Dimension dimension;
	private String picture;
	//rotation degree
	private double orientation;
	//the upper-left corner position
	private Point position;
	boolean locked;
	boolean visible;
	
	
	public Furniture(String name, String description, String picture, Dimension dimension, Point position, double orientation, boolean locked, boolean visible) {
		this.name = name;
		this.description = description;
		this.dimension = dimension;
		this.picture = picture;
		this.orientation = orientation;
		this.position = position;
		this.locked = locked;
		this.visible = visible;
	}

	public Furniture(String name, String description, String picture, Dimension dimension, Point position) {
		this(name, description, picture, dimension, position, 0, false, true);
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
}
