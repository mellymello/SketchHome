package tools;

import java.awt.event.MouseEvent;

import drawableObject.CtrlPoint;
import drawableObject.Furniture;
import drawableObject.Wall;

public class WindowTool extends FurniturePlacementTool {
	
	
	private static WindowTool instance;
	private Wall detectedWall;

	
	private WindowTool() {
		
	}

	@Override
	public void onMouseClicked(MouseEvent me) {
	
		
		detectedWall = wallDetect(me.getX(), me.getY());
		
		if(detectedWall!=null){
			super.onMouseClicked(me);
		}
		
	}

	@Override
	public void onMouseReleased(MouseEvent me) {
		detectedWall = wallDetect(me.getX(), me.getY());
		
		if(detectedWall ==null && drawingBoardContent.getSelectedFurniture()!=null){
			
			drawingBoardContent.getFurnitures().remove(drawingBoardContent.getSelectedFurniture());
		}
		
	}

	@Override
	public void onMouseMoved(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}
	
	
	public static WindowTool getInstance() {
		if (instance == null) {
			instance = new WindowTool();
		}
		return instance;
	}

	
	
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
