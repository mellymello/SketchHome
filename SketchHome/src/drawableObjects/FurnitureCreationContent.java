package drawableObjects;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Vector;

public class FurnitureCreationContent {


	
	private Vector<Ellipse2D> ellipses;
	protected Vector<Point> points = new Vector<Point>();

	public FurnitureCreationContent() {
		ellipses = new Vector<Ellipse2D>();
	
	}

	public Vector<Ellipse2D> getEllipses() {
		return ellipses;
	}

	public void addEllipse(Ellipse2D finalEllipse) {
		ellipses.add(finalEllipse);
	}

	public Vector<Point> getPoints() {
		return points;
	}
	
	
 public void clearContent(){
	 ellipses.clear();
	 points.clear();
 }
}
