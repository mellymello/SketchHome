package tools;

import java.awt.event.MouseEvent;
import java.util.LinkedList;

import drawableObject.CtrlPoint;
import drawableObject.Wall;

public abstract class WallTool extends IDrawingTool {
	

	public CtrlPoint ctrlPointDetect(int x, int y) {
		for (Wall w : drawingBoardContent.getWalls()) {
			if (w.getCtrlPointStart().getCtrlPoint().contains(x, y)) {
				return w.getCtrlPointStart();
			} else if (w.getCtrlPointEnd().getCtrlPoint().contains(x, y)) {
				return w.getCtrlPointEnd();
			}
		}
		return null;
	}

	public Wall wallDetect(int x, int y) {
		for (Wall w : drawingBoardContent.getWalls()) {
			if (w.getWallLine().ptSegDist(x, y) < drawingBoardContent.getWallThickness()) {
				return w;
			}
		}
		return null;
	}
	
}
