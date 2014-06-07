package tools;

import gui.FurnitureCreationFrame;

import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class DrawEllipseTool extends IDragDrawTool {

	private static DrawEllipseTool instance;

	private DrawEllipseTool() {

	}

	@Override
	public void onMousePressed(MouseEvent me) {

		if (furnitureCreationContent.getTmpEllipse() == null) {
			furnitureCreationContent.setTmpEllipse(new Ellipse2D.Double(me
					.getX(), me.getY(), 0, 0));
		}

	}

	@Override
	public void onMouseDragged(MouseEvent me) {

		furnitureCreationContent.getTmpEllipse().setFrame(
				furnitureCreationContent.getTmpEllipse().getX(),
				furnitureCreationContent.getTmpEllipse().getY(),
				me.getX() - furnitureCreationContent.getTmpEllipse().getX(),
				me.getY() - furnitureCreationContent.getTmpEllipse().getY());

	}

	@Override
	public void onMouseReleased(MouseEvent me) {
		furnitureCreationContent.addEllipse();

	}

	public static DrawEllipseTool getInstance() {
		if (instance == null) {
			instance = new DrawEllipseTool();
		}
		return instance;
	}
}
