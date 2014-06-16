package drawableObjects;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * Représente un point de contrôle sur un mur.
 */
public class CtrlPoint extends Point2D implements Serializable {

	private static final long serialVersionUID = 3100241430946391451L;
	// position
	private double x;
	private double y;
	// représentation graphique
	private Ellipse2D ctrlPoint;
	private double diameter;

	public CtrlPoint(double x, double y, double diameter) {
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		ctrlPoint = new Ellipse2D.Double(x - diameter / 2, y - diameter / 2,
				diameter, diameter);
	}

	/**
	 * @return un Ellipse2D représentant le point de contrôle
	 */
	public Ellipse2D getCtrlPoint() {
		return ctrlPoint;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	/**
	 * Modifie la position du CtrPoint.
	 * 
	 * @param x : nouvelle position x du point
	 * @param y : nouvelleve position y du point 
	 */
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
		
		ctrlPoint.setFrame(x - diameter / 2, y - diameter / 2, diameter,
				diameter);
	}
}
