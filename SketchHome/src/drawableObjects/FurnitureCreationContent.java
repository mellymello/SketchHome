package drawableObjects;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * Représente le contenu d'un plan dans la fenêtre de création d'objet personnalisé
 */
public class FurnitureCreationContent {

	// liste de points nécessaires pour l'outil crayon (dessiner à main libre)
	private Vector<Vector<Point>> points;
	
	private Ellipse2D tmpEllipse;
	private Vector<Ellipse2D> ellipses;
	
	private Line2D tmpLine;
	private Vector<Line2D> lines;
	
	private Rectangle2D tmpRectangle;
	private Vector<Rectangle2D> rectangles;
	
	//image utilisée pour permettre à l'utilisateur d'importer une image (format PNG)
	private BufferedImage img;
	//les coordonnées de l'image
	private int imgXpos;
	private int imgYpos;

	public FurnitureCreationContent() {
		ellipses = new Vector<Ellipse2D>();
		points = new Vector<Vector<Point>>();
		lines = new Vector<Line2D>();
		rectangles = new Vector<Rectangle2D>();
		
		imgXpos=0;
		imgYpos=0;
	}

	public Ellipse2D getTmpEllipse() {
		return tmpEllipse;
	}

	public void setTmpEllipse(Ellipse2D tmpEllipse) {
		this.tmpEllipse = tmpEllipse;
	}
	
	public Line2D getTmpLine() {
		return tmpLine;
	}

	public void setTmpLine(Line2D tmpLine) {
		this.tmpLine = tmpLine;
	}

	public void setTmpRectangle(Rectangle2D tmpRectangle) {
		this.tmpRectangle = tmpRectangle;
	}

	public Rectangle2D getTmpRectangle() {
		return tmpRectangle;
	}

	public Vector<Ellipse2D> getEllipses() {
		return ellipses;
	}

	public void addEllipse(Ellipse2D finalEllipse) {
		ellipses.add(finalEllipse);
	}

	public Vector<Vector<Point>> getPoints() {
		return points;
	}

	public void addPoint(Point finalPoint) {

		points.get(points.size()-1).add(finalPoint);
	}
	public void addNewPoints(Point newPoint){
		
		points.add(new Vector<Point>());
		points.get(points.size()-1).add(newPoint);
	}

	public Vector<Line2D> getLines() {
		return lines;
	}

	public void addLine() {
		if (tmpLine != null) {
			lines.add(tmpLine);
			tmpLine = null;
		}
	}
	
	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
	

	public int getImgXpos() {
		return imgXpos;
	}

	public void setImgXpos(int imgXpos) {
		this.imgXpos = imgXpos;
	}

	public int getImgYpos() {
		return imgYpos;
	}

	public void setImgYpos(int imgYpos) {
		this.imgYpos = imgYpos;
	}

	public void addEllipse() {
		if (tmpEllipse != null) {
			ellipses.add(tmpEllipse);
			tmpEllipse = null;
		}
	}
	
	public Vector<Rectangle2D> getRectangles() {
		return rectangles;
	}

	public void addRectangle() {
		if (tmpRectangle != null) {
			rectangles.add(tmpRectangle);
			tmpRectangle = null;
		}
	}

	/**
	 * Méthode utile pour effacer le contenu du panel de création d'objets  
	 */
	public void clearContent() {
		ellipses.clear();
		points.clear();
		lines.clear();
		rectangles.clear();
		tmpLine = null;
		tmpRectangle = null;
		img=null;
	}
}
