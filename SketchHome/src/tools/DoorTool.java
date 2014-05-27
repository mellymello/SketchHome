package tools;

import java.awt.event.MouseEvent;

import drawableObjects.CtrlPoint;
import drawableObjects.Furniture;
import drawableObjects.Wall;

public class DoorTool extends FurniturePlacementTool {
	
	
	private static DoorTool instance;
	private Wall detectedWall;

	
	private DoorTool() {
		
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
	
	
	public static DoorTool getInstance() {
		if (instance == null) {
			instance = new DoorTool();
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
