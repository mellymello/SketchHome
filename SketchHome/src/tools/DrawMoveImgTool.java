package tools;

import java.awt.event.MouseEvent;

public class DrawMoveImgTool extends IDragDrawTool {

	private static DrawMoveImgTool instance;
	
	//position relative de la souris par rapport au coin haut-gauche de l'objet
	private int relX;
	private int relY;

	private DrawMoveImgTool() {

	}

	public void onMouseDragged(MouseEvent me) {
		//déplacement de l'image
		furnitureCreationContent.setImgXpos(me.getX() - relX);
		furnitureCreationContent.setImgYpos(me.getY() - relY);
	}

	public void onMousePressed(MouseEvent me) {
		//Calcul de la position de la souris par rapport au coin haut-gauche de l'objet
		relX = me.getX() - furnitureCreationContent.getImgXpos();
		relY = me.getY() - furnitureCreationContent.getImgYpos();
	}

	public void onMouseReleased(MouseEvent me) {
	}

	public static DrawMoveImgTool getInstance() {
		if (instance == null) {
			instance = new DrawMoveImgTool();
		}
		return instance;
	}
}
