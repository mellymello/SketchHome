package drawableObjects;



import java.awt.geom.Line2D;
import java.io.Serializable;

/**
 * Repr�sente un mur tracable sur le plan.
 */
public class Wall implements Serializable{

	private static final long serialVersionUID = -7846324772480602708L;
	//les point qui d�finissent le d�but et la fin du mur
	private CtrlPoint startPoint;
	private CtrlPoint endPoint;
	//la ligne qui represent le mur
	private Line2D wallLine;

	private double ctrlPointDiameter;
	private int wallThickness;

	/**
	 * Construit un mur � partir de:
	 * @param xStartPoint : coordonn� x du point de d�part
	 * @param yStartPoint : coordonn� y du point de d�part
	 * @param ctrlPointDiameter : le diam�tre du ctrlPoint
	 * @param wallThickness : l'�pesseur du mur
	 * 
	 * Note: Le mur cr�e poss�de comme point d'arriv� le point de d�part (donc le mur poss�de une longueur de 0)
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
	 * Construit un mur � partir de:
	 * @param startPoint : le point de d�part du mur
	 * @param ctrlPointDiameter : le diam�tre du ctrlPoint
	 * @param wallThickness : l'�pesseur du mur
	 * 
	 * Note: Le mur cr�e poss�de comme point d'arriv� le point de d�part (donc le mur poss�de une longueur de 0)
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
	 * @param startPoint :le point de d�part du mur
	 * @param endPoint : le point d'arriv� du mur
	 * @param wallThickness : l'�pesseur du mur
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
	 * d�finir le point d'arriv� du mur
	 * @param xEndPoint : coordonn� x du point
	 * @param yEndPoint : coordonn� y du point
	 */
	public void setEndPoint(double xEndPoint, double yEndPoint) {
		endPoint.setLocation(xEndPoint, yEndPoint);
		wallLine.setLine(startPoint, endPoint);
	}

	/**
	 * d�finir le point de d�part du mur
	 * @param xStartPoint : coordonn� x du point
	 * @param yStartPoint : coordonn� y du point
	 */
	public void setStartPoint(double xStartPoint, double yStartPoint) {
		startPoint.setLocation(xStartPoint, yStartPoint);
		wallLine.setLine(startPoint, endPoint);
	}

	/**
	 * d�fini un nouveau point d'arriv� pour le mur
	 * @param newEndPoint : le nouveau point
	 */
	public void setNewEndPoint(CtrlPoint newEndPoint) {
		endPoint = newEndPoint;
		wallLine.setLine(startPoint, endPoint);

	}
	/**
	 * d�fini un nouveau point de d�part pour le mur
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
	 * M�thode pour obtenir la longueur du mur
	 * @return la longueur du mur
	 */
	public double getWallLength() {
		return Math.sqrt(Math.pow((endPoint.getX() - startPoint.getX()), 2)
				+ Math.pow((endPoint.getY() - startPoint.getY()), 2));
	}

	/**
	 * permet d'obtenir une repr�sentation texte du mur
	 */
	public String toString() {
		return "[" + startPoint + ":" + endPoint + "] wallLine: {"
				+ wallLine.getP1() + " : " + wallLine.getP2() + "}";
	}
}
