package drawableObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Repr�sente un meuble.
 */
public class Furniture implements Cloneable, Serializable {
	private static final long serialVersionUID = -4108285572677120893L;
	private static int count = 0;
	
	private int id; //TODO : delete ?
	private String name;
	private String description;
	private Dimension dimension;
	private String picture;
	//degr� de rotation
	private double orientation;
	//position du coin sup�rieur-gauche
	private Point position;
	private boolean locked;
	private boolean visible;
	
	private boolean isSelected;
	
	private transient Image loadedPicture;
	
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
		
		loadPicture();

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
		//car si on fait "open" dans le fichier de sauvegarde il n'y a pas les
		//images et donc nous devons la charger depuis la librairie
		if(loadedPicture==null){
			loadPicture();
		}
		return loadedPicture;
	}
	
	public boolean contains(int x, int y) {
		return getPathPolygon().contains(new Point(x, y));
	}
	
	/**
	 * @return un polygon repr�sentant le rectangle de s�lection de l'image
	 */
	public Polygon getPathPolygon() {
		Polygon p = new Polygon();
		/* * red�finition du polygone conteneur de l'image */ 
		int furnitureCenterX = position.x + dimension.width /2; 
		int furnitureCenterY = position.y + dimension.height /2; 
		AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(orientation), furnitureCenterX, furnitureCenterY); 
		Rectangle r = new Rectangle(position.x, position.y, dimension.width, dimension.height); 
		//parcours des "boundaries" du rectangle de l'image auquel on applique la rotation et construction du polygone repr�sentant l'image trourn�e avec chaque coins des "boundaries" 
		PathIterator i = r.getPathIterator(at); 
		while (!i.isDone()) {
			double[] xy = new double[2];
			i.currentSegment(xy);
			if (!(xy[0] == 0 && xy[1] == 0)) {
				p.addPoint((int)xy[0], (int)xy[1]);
			}
			i.next(); 
		}
		return p;
	}
	
	public boolean getSelected() {
		return isSelected;
	}
	public void setSelected(boolean selected) {
		this.isSelected = selected;
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
	
	private void loadPicture(){
		try {
			loadedPicture = ImageIO.read(new File(picture));
		} catch (IOException e) {
			//si l'image n'est pas trouv�e, on g�n�re une image noire
			loadedPicture = new BufferedImage(
					dimension.width, dimension.height,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D bGr = (Graphics2D) loadedPicture.getGraphics();
			bGr.setColor(Color.BLACK);
			bGr.fillRect(0, 0, dimension.width, dimension.height);
			bGr.dispose();
		}
	}
}
