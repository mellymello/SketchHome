package tools;

import gui.FurnitureCreationFrame;

import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class DrawRectangleTool extends IDragDrawTool {

	private static DrawRectangleTool instance;

	private DrawRectangleTool() {

	}

	@Override
	public void onMouseClicked(MouseEvent me) {


	}

	@Override
	public void onMousePressed(MouseEvent me) {
		if (furnitureCreationContent.getTmpRectangle() == null) {
			furnitureCreationContent.setTmpRectangle(new Rectangle2D.Double(me
					.getX(), me.getY(), 0, 0));
		} 
	}

	@Override
	public void onMouseDragged(MouseEvent me) {
		
			furnitureCreationContent.getTmpRectangle()
					.setRect(
							furnitureCreationContent.getTmpRectangle().getX(),
							furnitureCreationContent.getTmpRectangle().getY(),
							me.getX()
									- furnitureCreationContent
											.getTmpRectangle().getX(),
							me.getY()
									- furnitureCreationContent
											.getTmpRectangle().getY());
		
	}

	@Override
	public void onMouseReleased(MouseEvent me) {
		furnitureCreationContent.addRectangle();
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
