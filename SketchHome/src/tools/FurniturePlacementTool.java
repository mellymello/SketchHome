package tools;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;

import drawableObject.Furniture;

public class FurniturePlacementTool extends PlacementTool {
	
	private static FurniturePlacementTool instance;

	@Override
	public void onMouseClicked(MouseEvent me) {
		Furniture placedFurniture = drawingBoardContent.getSelectedFurniture().clone();
		placedFurniture.setPosition(me.getPoint());
		drawingBoardContent.addFurniture(placedFurniture);
	}

	public static FurniturePlacementTool getInstance() {
		if(instance == null) {
			instance = new FurniturePlacementTool();
		}
		return instance;
	}

}
