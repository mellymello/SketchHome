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
		System.out.println("Clicked on WindowTOOL");
		
		//detecter le mur et creer la fenetre tmp si elle n'existe pas / fixer la fenetre si elle existe
		detectedWall = wallDetect(me.getX(), me.getY());
		
		if(detectedWall!=null){
			//delete furniture with right clic
			if(me.getButton() == MouseEvent.BUTTON3) {
				drawingBoardContent.getFurnitures().remove(furnitureDetect(me.getX(),
						me.getY()));
			}
			//placement of furniture
			else if(drawingBoardContent.getSelectedModelFurniture() != null) {
				Furniture placedFurniture = drawingBoardContent.getSelectedModelFurniture().clone();
				placedFurniture.setPosition(me.getPoint());
				drawingBoardContent.addFurniture(placedFurniture);
			}
		}
		
	}

	@Override
	public void onMousePressed(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseDragged(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub
		
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
