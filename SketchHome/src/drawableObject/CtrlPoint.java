package drawableObject;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class CtrlPoint extends Point2D {

	private double x;
	private double y;
	private Ellipse2D ctrlPoint;
	private double diameter;

	public CtrlPoint(double x, double y, double diameter) {
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		ctrlPoint = new Ellipse2D.Double(x - diameter / 2, y - diameter / 2,
				diameter, diameter);

	}

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
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
		
		ctrlPoint.setFrame(x - diameter / 2, y - diameter / 2, diameter,
				diameter);
	}
}
