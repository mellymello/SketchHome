package tools;

import java.awt.event.MouseEvent;

import drawableObjects.CtrlPoint;
import drawableObjects.Furniture;
import drawableObjects.Wall;

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
	public void onMouseReleased(MouseEvent me) { //TODO : problème : on peut déplacer des meubles non-fenêtres et ducoup ils disparaissent si on les laches sur un mur
		detectedWall = wallTool.wallDetect(me.getX(), me.getY());
		
		if(detectedWall == null && drawingBoardContent.getSelectedFurniture() != null){
			
			drawingBoardContent.deleteFurniture(drawingBoardContent.getSelectedFurniture());
		}
		
	}

	public void setWallTool(WallTool wallTool) {
		this.wallTool = wallTool;
	}
	
	public static OnWallPlacementTool getInstance() {
		if (instance == null) {
			instance = new OnWallPlacementTool();
		}
		return instance;
	}
}
