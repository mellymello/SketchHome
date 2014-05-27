package tools;

import gui.FurnitureCreationFrame;

import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class DrawEllipseTool extends IDragDrawTool {

	private static DrawEllipseTool instance;
	private Ellipse2D tmpEllipse;

	private DrawEllipseTool() {

	}

	@Override
	public void onMouseClicked(MouseEvent me) {
		System.out.println("Ellipse gogogo");

	}

	@Override
	public void onMousePressed(MouseEvent me) {
		if (tmpEllipse == null) {
			tmpEllipse = new Ellipse2D.Double(me.getX(), me.getY(), 0, 0);
			furnitureCreationContent.setTmpEllipse(tmpEllipse);
		}
	}

	@Override
	public void onMouseDragged(MouseEvent me) {
//		if(tmpEllipse != null){
//			tmpEllipse.setFrame(tmpEllipse.getX(), tmpEllipse.getY(), w, h);
//		}

	}

	@Override
	public void onMouseReleased(MouseEvent me) {
		if(tmpEllipse != null){
			tmpEllipse.setFrame(tmpEllipse.getX(), tmpEllipse.getY(), me.getX(), me.getY());
			furnitureCreationContent.setTmpEllipse(tmpEllipse);
			furnitureCreationContent.addEllipse(tmpEllipse);
		}

	}

	@Override
	public void onMouseMoved(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	public static DrawEllipseTool getInstance() {
		if (instance == null) {
			instance = new DrawEllipseTool();
		}
		return instance;
	}
}
