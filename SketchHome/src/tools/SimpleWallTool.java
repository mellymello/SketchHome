package tools;

import java.awt.event.MouseEvent;

import drawableObject.CtrlPoint;
import drawableObject.DrawingBoardContent;
import drawableObject.Wall;

public class SimpleWallTool extends WallTool {
	
	private static SimpleWallTool instance;
	
	private SimpleWallTool() {}

	@Override
	public void onMouseClicked(MouseEvent me) {
		if (selectedWall == null) {
			if (selectedCtrlPoint == null) {
				if (tmpWall == null) {
					tmpWall = new Wall(me.getX(), me.getY(), ctrlPointDiameter,
							wallThickness);
				} else {
					tmpWall.setEndPoint(me.getX(), me.getY());
					walls.add(tmpWall);
					tmpWall = null;
				}
			} else {
				if (tmpWall == null) {
					tmpWall = new Wall(selectedCtrlPoint, ctrlPointDiameter,
							wallThickness);
				} else {
					tmpWall.setNewEndPoint(selectedCtrlPoint);
					walls.add(tmpWall);
					tmpWall = null;
				}
			}
			//managing the action of connecting a wall with an existing wall on the "middle" (not over a ctrlPoint)
			//The reaction of the program is to split the "base" wall into 2 walls and let the user continue to draw the third wall
		} else { 
			if (selectedCtrlPoint == null) {
				if (tmpWall == null) {
					tmpWall = new Wall(me.getX(), me.getY(), ctrlPointDiameter,
							wallThickness);
					walls.add(new Wall(selectedWall.getCtrlPointStart(), tmpWall.getCtrlPointStart(),wallThickness));
					walls.add(new Wall(tmpWall.getCtrlPointStart(),selectedWall.getCtrlPointEnd(),wallThickness));
					walls.remove(selectedWall);
					
				} else {
					tmpWall.setEndPoint(me.getX(), me.getY());
					walls.add(new Wall(selectedWall.getCtrlPointStart(), tmpWall.getCtrlPointEnd(),wallThickness));
					walls.add(new Wall(tmpWall.getCtrlPointEnd(),selectedWall.getCtrlPointEnd(),wallThickness));
					walls.remove(selectedWall);
					walls.add(tmpWall);
					tmpWall = null;
				}
			} else {
				if (tmpWall == null) {
					tmpWall = new Wall(selectedCtrlPoint, ctrlPointDiameter,
							wallThickness);
				} else {
					tmpWall.setNewEndPoint(selectedCtrlPoint);
					walls.add(tmpWall);
					tmpWall = null;
				}
			}
		}

	}

	@Override
	public void onMousePressed(MouseEvent me) {
		selectedCtrlPoint = ctrlPointDetect(me.getX(), me.getY());
		selectedWall = wallDetect(me.getX(), me.getY());
		System.out.println(selectedWall);
	}

	@Override
	public void onMouseDragged(MouseEvent me) {
		if (selectedCtrlPoint != null) {
			selectedCtrlPoint.setLocation(me.getX(), me.getY());
			for (Wall w : walls) {
				if (w.getCtrlPointStart() == selectedCtrlPoint) {
					w.setNewStartPoint(selectedCtrlPoint);
				} else if (w.getCtrlPointEnd() == selectedCtrlPoint) {
					w.setNewEndPoint(selectedCtrlPoint);
				}
			}

		}

	}

	@Override
	public void onMouseReleased(MouseEvent me) {
		CtrlPoint detectedReleasedPoint = ctrlPointDetect(me.getX(),me.getY());
//		Wall detectedRelasedWall = wallDetect(me.getX(), me.getY());

		if (detectedReleasedPoint != null
				&& detectedReleasedPoint != selectedCtrlPoint) {
			if (selectedCtrlPoint != null) {
				for (Wall w : walls) {
					if (w.getCtrlPointStart() == detectedReleasedPoint) {
						w.setNewStartPoint(selectedCtrlPoint);
					} else if (w.getCtrlPointEnd() == detectedReleasedPoint) {
						w.setNewEndPoint(selectedCtrlPoint);
					}
				}
				selectedCtrlPoint = detectedReleasedPoint;
			}
		}

	}

	@Override
	public void onMouseMoved(MouseEvent me) {
		if (tmpWall != null) {

			tmpWall.setEndPoint(me.getX(), me.getY());

			
		}

	}

	public static SimpleWallTool getInstance() {
		if(instance == null) {
			instance = new SimpleWallTool();
		}
		return instance;
	}

}
