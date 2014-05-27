package drawableObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.tree.DefaultMutableTreeNode;

public class Furniture implements Cloneable, Serializable {
	private static final long serialVersionUID = -4108285572677120893L;
	private static int count = 0;
	
	private int id;
	private String name;
	private String description;
	private Dimension dimension;
	private String picture;
	//degré de rotation
	private double orientation;
	//position du coin supérieur-gauche
	private Point position;
	private boolean locked;
	private boolean visible;
	
	private transient Image loadedImage;
	
	
	private Color color;
	
	private FurnitureLibrary library;
	private DefaultMutableTreeNode jTreeNode;
	
	
	public Furniture(String name, String description, String picture, Dimension dimension, Point position, double orientation, boolean locked, boolean visible, FurnitureLibrary library, Color color) {
		this.id = count++;
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
		
		loadImage();

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
	
	public void setPosition(int x, int y) {
		this.position.x = x;
		this.position.y = y;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Image getLoadedPicture() {
		//car si on fait "open" dans le fichier de sauvegarde il y a  pas les
		//images et donc nous devons la charger depuis la "base de donnée"
		if(loadedImage==null){
			loadImage();
		}
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
	
	public void setJTreeNode(DefaultMutableTreeNode jTreeNode) {
		this.jTreeNode = jTreeNode;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	private void loadImage(){
		try {
			loadedImage = ImageIO.read(new File(picture));
		} catch (IOException e) {
			//si l'image n'est pas trouvée, on génère une image noire
			loadedImage = new BufferedImage(
					dimension.width, dimension.height,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D bGr = (Graphics2D) loadedImage.getGraphics();
			bGr.setColor(Color.BLACK);
			bGr.fillRect(0, 0, dimension.width, dimension.height);
			bGr.dispose();
		}
	}
}
