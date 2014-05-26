package tools;

import gui.FornitureCreation;

import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class DrawLineTool extends IDragDrawTool {

	private static DrawLineTool instance;


	private DrawLineTool() {

	}

	@Override
	public void onMouseClicked(MouseEvent me) {
		System.out.println("line gogogo");

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

	public static DrawLineTool getInstance() {
		if (instance == null) {
			instance = new DrawLineTool();
		}
		return instance;
	}
}
