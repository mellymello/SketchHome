package tools;

import java.awt.event.MouseEvent;

import drawableObject.CtrlPoint;
import drawableObject.Wall;

public abstract class WallTool implements ITools {

	private CtrlPoint ctrlPointDetect(int x, int y) {
		for (Wall w : walls) {
			if (w.getCtrlPointStart().getCtrlPoint().contains(x, y)) {
				return w.getCtrlPointStart();
			} else if (w.getCtrlPointEnd().getCtrlPoint().contains(x, y)) {
				return w.getCtrlPointEnd();
			}
		}
		return null;
	}

	public Wall wallDetect(int x, int y) {
		for (Wall w : walls) {
			if (w.getWallLine().ptSegDist(x, y) < wallThickness) {
				return w;
			}
		}
		return null;
	}

}
