package drawableObject;

import java.awt.Rectangle;
import java.awt.geom.Line2D;

public class Wall {

	private CtrlPoint startPoint;
	private CtrlPoint endPoint;
	private Line2D wallLine;

	private double ctrlPointDiameter;
	private int wallThickness;

	public Wall(double xStartPoint, double yStartPoint,double ctrlPointDiameter, int wallThickness) {
		
		this.ctrlPointDiameter = ctrlPointDiameter;
		this.wallThickness = wallThickness;

		startPoint = new CtrlPoint(xStartPoint, yStartPoint, ctrlPointDiameter);
		endPoint = new CtrlPoint(xStartPoint, yStartPoint, ctrlPointDiameter);

		wallLine = new Line2D.Double(startPoint, endPoint);

	}

	public Wall(CtrlPoint startPoint, int ctrlPointDiameter, int wallThickness) {
		this.startPoint = startPoint;
		endPoint = new CtrlPoint(startPoint.getX(), startPoint.getY(), ctrlPointDiameter);
		
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
	
	
	public double getWallLength(){
		return Math.sqrt ( ( startPoint.getX() * endPoint.getX() ) - ( startPoint.getY() *  endPoint.getY()))  ;
	}
	

	public String toString() {
		return "[" + startPoint + ":" + endPoint + "] wallLine: {"+ wallLine.getP1()+" : "+wallLine.getP2()+"}";
	}
}
