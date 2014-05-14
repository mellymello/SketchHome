package tools;

import java.awt.event.MouseEvent;

import drawableObject.CtrlPoint;
import drawableObject.Furniture;
import drawableObject.Wall;

public class OnWallPlacementTool extends FurniturePlacementTool {
	
	
	private static OnWallPlacementTool instance;
	private Wall detectedWall;
	private WallTool wallTool;
	
	private OnWallPlacementTool() {
	}

	@Override
	public void onMouseClicked(MouseEvent me) {
	
		
		detectedWall = wallTool.wallDetect(me.getX(), me.getY());
		
		if(detectedWall != null){
			super.onMouseClicked(me);
		}
		
	}

	@Override
	public void onMouseReleased(MouseEvent me) {
		detectedWall = wallTool.wallDetect(me.getX(), me.getY());
		
		if(detectedWall ==null && drawingBoardContent.getSelectedFurniture()!=null){
			
			drawingBoardContent.getFurnitures().remove(drawingBoardContent.getSelectedFurniture());
		}
		
	}
	
	
	public static OnWallPlacementTool getInstance() {
		if (instance == null) {
			instance = new OnWallPlacementTool();
		}
		return instance;
	}

	public void setWallTool(WallTool wallTool) {
		this.wallTool = wallTool;
	}	
}
