package drawableObject;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.text.Position;

public class Furniture implements Cloneable {
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
}
