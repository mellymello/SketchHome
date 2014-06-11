package tools;

import drawableObjects.CtrlPoint;
import drawableObjects.Wall;

public abstract class WallTool extends DrawingTool {
	

	public CtrlPoint ctrlPointDetect(int x, int y) {
		for (Wall w : drawingBoardContent.getPlacedWalls()) {
			if (w.getCtrlPointStart().getCtrlPoint().contains(x, y)) {
				return w.getCtrlPointStart();
			} else if (w.getCtrlPointEnd().getCtrlPoint().contains(x, y)) {
				return w.getCtrlPointEnd();
			}
		}
		return null;
	}

	public Wall wallDetect(int x, int y) {
		for (Wall w : drawingBoardContent.getPlacedWalls()) {
			if (w.getWallLine().ptSegDist(x, y) < drawingBoardContent.getWallThickness()) {
				return w;
			}
		}
		return null;
	}
	
}
