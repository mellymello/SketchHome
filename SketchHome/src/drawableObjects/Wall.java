package drawableObjects;

import java.awt.geom.Line2D;
import java.io.Serializable;

/**
 * Représente un mur traçable sur le plan.
 */
public class Wall implements Serializable{

	private static final long serialVersionUID = -7846324772480602708L;
	//les point qui définissent le début et la fin du mur
	private CtrlPoint startPoint;
	private CtrlPoint endPoint;
	//la ligne qui represent le mur
	private Line2D wallLine;

	private double ctrlPointDiameter;
	private int wallThickness;

	/**
	 * Construit un mur à partir de:
	 * @param xStartPoint : coordonné x du point de départ
	 * @param yStartPoint : coordonné y du point de départ
	 * @param ctrlPointDiameter : le diamètre du ctrlPoint
	 * @param wallThickness : l'épaisseur du mur
	 * 
	 * Note: Le mur créé possède comme point d'arrivée le point de départ (donc le mur possède une longueur de 0)
	 */
	public Wall(double xStartPoint, double yStartPoint,
			double ctrlPointDiameter, int wallThickness) {

		this.ctrlPointDiameter = ctrlPointDiameter;
		this.wallThickness = wallThickness;

		startPoint = new CtrlPoint(xStartPoint, yStartPoint, ctrlPointDiameter);
		endPoint = new CtrlPoint(xStartPoint, yStartPoint, ctrlPointDiameter);

		wallLine = new Line2D.Double(startPoint, endPoint);
	}

	/**
	 * Construit un mur à partir de:
	 * @param startPoint : le point de départ du mur
	 * @param ctrlPointDiameter : le diamètre du ctrlPoint
	 * @param wallThickness : l'épaisseur du mur
	 * 
	 * Note: Le mur créé possède comme point d'arrivée le point de départ (donc le mur possède une longueur de 0)
	 */
	public Wall(CtrlPoint startPoint, int ctrlPointDiameter, int wallThickness) {
		this.startPoint = startPoint;
		endPoint = new CtrlPoint(startPoint.getX(), startPoint.getY(),
				ctrlPointDiameter);

		this.wallThickness = wallThickness;
		wallLine = new Line2D.Double(startPoint, endPoint);
	}

	/**
	 * 
	 * @param startPoint : 	le point de départ du mur
	 * @param endPoint : 	le point d'arrivée du mur
	 * @param wallThickness : l'épaisseur du mur
	 */
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

	/**
	 * Définit le point d'arrivée du mur
	 * @param xEndPoint : coordonnée x du point
	 * @param yEndPoint : coordonnée y du point
	 */
	public void setEndPoint(double xEndPoint, double yEndPoint) {
		endPoint.setLocation(xEndPoint, yEndPoint);
		wallLine.setLine(startPoint, endPoint);
	}

	/**
	 * Définit le point de départ du mur
	 * @param xStartPoint : coordonnée x du point
	 * @param yStartPoint : coordonnée y du point
	 */
	public void setStartPoint(double xStartPoint, double yStartPoint) {
		startPoint.setLocation(xStartPoint, yStartPoint);
		wallLine.setLine(startPoint, endPoint);
	}

	/**
	 * Définit un nouveau point d'arrivée pour le mur
	 * @param newEndPoint : le nouveau point
	 */
	public void setNewEndPoint(CtrlPoint newEndPoint) {
		endPoint = newEndPoint;
		wallLine.setLine(startPoint, endPoint);

	}
	/**
	 * défini un nouveau point de départ pour le mur
	 * @param newStartPoint : le nouveau point
	 */
	public void setNewStartPoint(CtrlPoint newStartPoint) {
		startPoint = newStartPoint;
		wallLine.setLine(startPoint, endPoint);

	}

	public Line2D getWallLine() {
		return wallLine;
	}

	/**
	 * Permet d'obtenir la longueur du mur
	 * @return la longueur du mur
	 */
	public double getWallLength() {
		return Math.sqrt(Math.pow((endPoint.getX() - startPoint.getX()), 2)
				+ Math.pow((endPoint.getY() - startPoint.getY()), 2));
	}

	/**
	 * Permet d'obtenir une représentation texte du mur
	 */
	public String toString() {
		return "[" + startPoint + ":" + endPoint + "] wallLine: {"
				+ wallLine.getP1() + " : " + wallLine.getP2() + "}";
	}
}
