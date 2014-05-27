package tools;

import gui.FornitureCreation;

import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class DrawTriangleTool extends IDragDrawTool {

	private static DrawTriangleTool instance;


	private DrawTriangleTool() {

	}

	@Override
	public void onMouseClicked(MouseEvent me) {
		System.out.println("triangle gogogo");

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

	public static DrawTriangleTool getInstance() {
		if (instance == null) {
			instance = new DrawTriangleTool();
		}
		return instance;
	}
}
