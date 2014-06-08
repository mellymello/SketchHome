package tools;

import java.awt.event.MouseEvent;

public class DrawMoveImgTool extends IDragDrawTool {

	private static DrawMoveImgTool instance;

	private DrawMoveImgTool() {

	}

	public void onMouseDragged(MouseEvent me) {
		
		furnitureCreationContent.setImgXpos(me.getX());
		furnitureCreationContent.setImgYpos(me.getY());

	}

	public void onMousePressed(MouseEvent me) {

		

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
