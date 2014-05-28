package tools;

import gui.FurnitureCreationFrame;

import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class DrawEllipseTool extends IDragDrawTool {

	private static DrawEllipseTool instance;

	private DrawEllipseTool() {

	}

	@Override
	public void onMouseClicked(MouseEvent me) {
		
	}

	@Override
	public void onMousePressed(MouseEvent me) {
		furnitureCreationContent.getEllipses().add(new Ellipse2D.Double(me.getX(), me.getY(), 0, 0));
			
		
	}

	@Override
	public void onMouseDragged(MouseEvent me) {
		if(furnitureCreationContent.getEllipses() != null){
			Ellipse2D tmp = furnitureCreationContent.getEllipses().get(furnitureCreationContent.getEllipses().size()-1);
			furnitureCreationContent.getEllipses().get(furnitureCreationContent.getEllipses().size()-1).setFrame(tmp.getX(), tmp.getY(), me.getX(), me.getY());
		}

	}

	@Override
	public void onMouseReleased(MouseEvent me) {
		if(furnitureCreationContent.getEllipses() != null){
			Ellipse2D tmp = furnitureCreationContent.getEllipses().get(furnitureCreationContent.getEllipses().size()-1);
			furnitureCreationContent.getEllipses().get(furnitureCreationContent.getEllipses().size()-1).setFrame(tmp.getX(), tmp.getY(), me.getX(), me.getY());
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
