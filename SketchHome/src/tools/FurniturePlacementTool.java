package tools;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.sun.media.sound.FFT;

import drawableObject.CtrlPoint;
import drawableObject.Furniture;
import drawableObject.Wall;

public class FurniturePlacementTool extends PlacementTool {
	
	private static FurniturePlacementTool instance;

	@Override
	public void onMouseClicked(MouseEvent me) {
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

	public static FurniturePlacementTool getInstance() {
		if(instance == null) {
			instance = new FurniturePlacementTool();
		}
		return instance;
	}

	@Override
	public void onMouseDragged(MouseEvent me) {
		if(drawingBoardContent.getSelectedFurniture() != null) {
			drawingBoardContent.getSelectedFurniture().setPosition(me.getPoint());			
		}
	}

	@Override
	public void onMousePressed(MouseEvent me) {
		drawingBoardContent.setSelectedFurniture(furnitureDetect(me.getX(),
				me.getY()));
	}
	
	public Furniture furnitureDetect(int x, int y) {
		for (Furniture f : drawingBoardContent.getFurnitures()) {
			if (f.contains(x,y)) {
				return f;
			}
		}
		return null;
	}

}
