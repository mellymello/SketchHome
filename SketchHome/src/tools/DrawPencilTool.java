package tools;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class DrawPencilTool extends IDragDrawTool {

	private static DrawPencilTool instance;

	private DrawPencilTool() {

	}

	public void onMouseDragged(MouseEvent me) {

		furnitureCreationContent.addPoint(me.getPoint());

	}

	public void onMousePressed(MouseEvent me) {

		
		furnitureCreationContent.addNewPoints(new Point(me.getPoint()));

	}

	public void onMouseReleased(MouseEvent me) {
	
		furnitureCreationContent.addPoint(me.getPoint());

	}

	public static DrawPencilTool getInstance() {
		if (instance == null) {
			instance = new DrawPencilTool();
		}
		return instance;
	}
}
