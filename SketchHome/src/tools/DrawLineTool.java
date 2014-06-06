package tools;

import gui.FurnitureCreationFrame;

import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class DrawLineTool extends IDragDrawTool {

	private static DrawLineTool instance;

	private DrawLineTool() {

	}

	@Override
	public void onMouseClicked(MouseEvent me) {

	}

	@Override
	public void onMousePressed(MouseEvent me) {
		if (furnitureCreationContent.getTmpLine() == null) {
			furnitureCreationContent.setTmpLine(new Line2D.Double(me.getX(), me
					.getY(), me.getX(), me.getY()));
		}
	}

	@Override
	public void onMouseDragged(MouseEvent me) {
		if (furnitureCreationContent.getTmpLine() != null) {
			if(me.isShiftDown()){
			furnitureCreationContent.getTmpLine().setLine(
					furnitureCreationContent.getTmpLine().getP1(),
					new Point2D.Double(me.getX(), furnitureCreationContent.getTmpLine().getP1().getY()));
			}
			else if(me.isAltDown()){
				furnitureCreationContent.getTmpLine().setLine(
						furnitureCreationContent.getTmpLine().getP1(),
						new Point2D.Double(furnitureCreationContent.getTmpLine().getP1().getX(),me.getY() ));
			}
			else{
				furnitureCreationContent.getTmpLine().setLine(
						furnitureCreationContent.getTmpLine().getP1(),
						new Point2D.Double(me.getX(),me.getY() ));
			}
		}
	}

	@Override
	public void onMouseReleased(MouseEvent me) {

		furnitureCreationContent.addLine();

	}

	@Override
	public void onMouseMoved(MouseEvent me) {

	}

	public static DrawLineTool getInstance() {
		if (instance == null) {
			instance = new DrawLineTool();
		}
		return instance;
	}
}
