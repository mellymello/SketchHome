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
	//position relative de la souris par rapport au coin haut-gauche de l'objet
	//utilisé pour déplacer les meubles
	private int relX;
	private int relY;

	@Override
	public void onMouseClicked(MouseEvent me) {
		//suppression d'un meuble avec le clic droit
		if(me.getButton() == MouseEvent.BUTTON3) {
			Furniture f = furnitureDetect(me.getX(),me.getY());
			if(f != null && !f.getLocked()) {
				drawingBoardContent.deleteFurniture(f);
			}
		}
		//placement d'un meuble, on ne peut pas les superposer
		else if(drawingBoardContent.getSelectedFurnitureModel() != null && furnitureDetect(me.getX(), me.getY()) == null) {
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
			//Mise de l'objet sélectionné au premier plan
			drawingBoardContent.getFurnitures().remove(drawingBoardContent.getSelectedFurniture());
			drawingBoardContent.getFurnitures().addLast(drawingBoardContent.getSelectedFurniture());
			
			//Calcul de la position de la souris par rapport au coin haut-gauche de l'objet
			relX = me.getX() - drawingBoardContent.getSelectedFurniture().getPosition().x;
			relY = me.getY() - drawingBoardContent.getSelectedFurniture().getPosition().y;
		}
	}
	
	public Furniture furnitureDetect(int x, int y) {
		for (int i = drawingBoardContent.getFurnitures().size()-1; i >= 0; i--) {
			if (drawingBoardContent.getFurnitures().get(i).contains(x,y)) {
				return drawingBoardContent.getFurnitures().get(i);
			}
		}
		return null;
	}

}
