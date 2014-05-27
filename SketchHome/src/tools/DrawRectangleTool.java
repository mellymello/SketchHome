package tools;

import gui.FornitureCreation;

import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class DrawRectangleTool extends IDragDrawTool {

	private static DrawRectangleTool instance;
	private Rectangle2D tmpRect;

	private DrawRectangleTool() {

	}

	@Override
	public void onMouseClicked(MouseEvent me) {
		System.out.println("rectangle gogogo");

	}

	@Override
	public void onMousePressed(MouseEvent me) {

	}

	@Override
	public void onMouseDragged(MouseEvent me) {


	}

	@Override
	public void onMouseReleased(MouseEvent me) {


	}

	@Override
	public void onMouseMoved(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	public static DrawRectangleTool getInstance() {
		if (instance == null) {
			instance = new DrawRectangleTool();
		}
		return instance;
	}
}
