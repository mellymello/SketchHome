package drawableObjects;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

public class FurnitureCreationContent {

	private Vector<Vector<Point>> points;
	
	private Ellipse2D tmpEllipse;
	private Vector<Ellipse2D> ellipses;
	
	private Line2D tmpLine;
	private Vector<Line2D> lines;
	
	private Rectangle2D tmpRectangle;
	private Vector<Rectangle2D> rectangles;
	
	private BufferedImage img;


	public FurnitureCreationContent() {
		ellipses = new Vector<Ellipse2D>();
		points = new Vector<Vector<Point>>();
		lines = new Vector<Line2D>();
		rectangles = new Vector<Rectangle2D>();
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
