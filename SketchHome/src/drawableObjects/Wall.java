package drawableObjects;



import java.awt.geom.Line2D;
import java.io.Serializable;

/**
 * Représente un mur tracable sur le plan.
 */
public class Wall implements Serializable{

	private static final long serialVersionUID = -7846324772480602708L;
	private CtrlPoint startPoint;
	private CtrlPoint endPoint;
	private Line2D wallLine;

	private double ctrlPointDiameter;
	private int wallThickness;

	public Wall(double xStartPoint, double yStartPoint,
			double ctrlPointDiameter, int wallThickness) {

		this.ctrlPointDiameter = ctrlPointDiameter;
		this.wallThickness = wallThickness;

		startPoint = new CtrlPoint(xStartPoint, yStartPoint, ctrlPointDiameter);
		endPoint = new CtrlPoint(xStartPoint, yStartPoint, ctrlPointDiameter);

		wallLine = new Line2D.Double(startPoint, endPoint);

	}

	public Wall(CtrlPoint startPoint, int ctrlPointDiameter, int wallThickness) {
		this.startPoint = startPoint;
		endPoint = new CtrlPoint(startPoint.getX(), startPoint.getY(),
				ctrlPointDiameter);

		this.wallThickness = wallThickness;
		wallLine = new Line2D.Double(startPoint, endPoint);

	}

	public Wall(CtrlPoint startPoint, CtrlPoint endPoint, int wallThickness) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.wallThickness = wallThickness;

		wallLine = new Line2D.Double(startPoint, endPoint);

	}

	public CtrlPoint getCtrlPointStart() {
		return startPoint;
	}

	public CtrlPoint getCtrlPointEnd() {
		return endPoint;
	}

	public void setEndPoint(double xEndPoint, double yEndPoint) {
		endPoint.setLocation(xEndPoint, yEndPoint);
		wallLine.setLine(startPoint, endPoint);
	}

	public void setStartPoint(double xStartPoint, double yStartPoint) {
		startPoint.setLocation(xStartPoint, yStartPoint);
		wallLine.setLine(startPoint, endPoint);
	}

	public void setNewEndPoint(CtrlPoint newEndPoint) {
		endPoint = newEndPoint;
		wallLine.setLine(startPoint, endPoint);

	}

	public void setNewStartPoint(CtrlPoint newStartPoint) {
		startPoint = newStartPoint;
		wallLine.setLine(startPoint, endPoint);

	}

	public Line2D getWallLine() {
		return wallLine;
	}

	public double getWallLength() {
		return Math.sqrt(Math.pow((endPoint.getX() - startPoint.getX()), 2)
				+ Math.pow((endPoint.getY() - startPoint.getY()), 2));
	}

	public String toString() {
		return "[" + startPoint + ":" + endPoint + "] wallLine: {"
				+ wallLine.getP1() + " : " + wallLine.getP2() + "}";
	}
}
