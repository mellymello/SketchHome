package tools;

import gui.FurnitureCreationFrame;

import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class DrawPencilTool extends IDragDrawTool {

	private static DrawPencilTool instance;


	private DrawPencilTool() {

	}

	@Override
	public void onMouseClicked(MouseEvent me) {
		System.out.println("pencil gogogo");

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

	public static DrawPencilTool getInstance() {
		if (instance == null) {
			instance = new DrawPencilTool();
		}
		return instance;
	}
}
