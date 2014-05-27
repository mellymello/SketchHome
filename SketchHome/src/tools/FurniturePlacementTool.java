package tools;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.sun.media.sound.FFT;

import drawableObjects.CtrlPoint;
import drawableObjects.Furniture;
import drawableObjects.Wall;

public class FurniturePlacementTool extends PlacementTool {
	
	private static FurniturePlacementTool instance;
	private int relX;
	private int relY;

	@Override
	public void onMouseClicked(MouseEvent me) {
		//delete furniture with right clic
		if(me.getButton() == MouseEvent.BUTTON3) {
			Furniture f = furnitureDetect(me.getX(),me.getY());
			if(f != null) {
				drawingBoardContent.deleteFurniture(f);
			}
//			drawingBoardContent.getFurnitures().remove(furnitureDetect(me.getX(),
//					me.getY()));
		}
		//placement of furniture
		else if(drawingBoardContent.getSelectedFurnitureModel() != null) {
			Furniture placedFurniture = drawingBoardContent.getSelectedFurnitureModel().clone();
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
		if(drawingBoardContent.getSelectedFurniture() != null && !drawingBoardContent.getSelectedFurniture().getLocked()) {
			drawingBoardContent.getSelectedFurniture().setPosition(
					me.getPoint().x - relX,
					me.getPoint().y - relY);
		}
	}

	@Override
	public void onMousePressed(MouseEvent me) {
		drawingBoardContent.setSelectedFurniture(furnitureDetect(me.getX(),
				me.getY()));
		if(drawingBoardContent.getSelectedFurniture() != null) {
			relX = me.getX() - drawingBoardContent.getSelectedFurniture().getPosition().x;
			relY = me.getY() - drawingBoardContent.getSelectedFurniture().getPosition().y;
		}
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
