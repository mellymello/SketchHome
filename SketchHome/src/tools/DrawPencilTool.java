package tools;

import java.awt.Point;
import java.awt.event.MouseEvent;


public class DrawPencilTool extends IDragDrawTool {

	private static DrawPencilTool instance;

	private DrawPencilTool() {

	}

	public void onMouseDragged(MouseEvent me) {

		furnitureCreationContent.getPoints().addElement((new Point(me.getPoint())));
		

	}

	public void onMousePressed(MouseEvent me) {

		furnitureCreationContent.getPoints().clear();
		furnitureCreationContent.getPoints().addElement((new Point(me.getPoint())));
		

	}

	public void onMouseReleased(MouseEvent me) {
//
//		furnitureCreationContent.getPoints().addElement((new Point(me.getPoint())));
//		furnitureCreationContent.getPoints().clear();


	}

	public void onMouseMoved(MouseEvent me) {
	}

	public void onMouseClicked(MouseEvent me) {
	}

	public void onMouseEntered(MouseEvent me) {
	}

	public void onMouseExited(MouseEvent me) {
	}

	public static DrawPencilTool getInstance() {
		if (instance == null) {
			instance = new DrawPencilTool();
		}
		return instance;
	}
}
