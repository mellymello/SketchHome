package tools;

import java.awt.event.MouseEvent;

import drawableObjects.Furniture;
import drawableObjects.Wall;

public class FurniturePlacementTool extends PlacementTool {
	
	private static FurniturePlacementTool instance;
	//position relative de la souris par rapport au coin haut-gauche de l'objet
	//utilis� pour d�placer les meubles
	private int relX;
	private int relY;
	
	//objets utilis�s pour le placement de meubles muraux (qui doivent �tre sur un mur)
	private Wall detectedWall;
	private WallTool wallTool;

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
			
			//v�rification pour les meubles muraux
			if(drawingBoardContent.getSelectedFurnitureModel().getMustBePlacedOnWall()) {
				detectedWall = wallTool.wallDetect(me.getX(), me.getY());
				
				if(detectedWall == null){
					return;
				}
			}
			
			//placement du meuble
			Furniture placedFurniture = drawingBoardContent.getSelectedFurnitureModel().clone();
			placedFurniture.setPosition(me.getPoint());
			drawingBoardContent.addFurniture(placedFurniture);
		}
	}

	@Override
	public void onMouseDragged(MouseEvent me) {
		//d�placement d'un meuble
		if(drawingBoardContent.getSelectedFurniture() != null && !drawingBoardContent.getSelectedFurniture().getLocked()) {
			//v�rification pour les meubles muraux
			if(drawingBoardContent.getSelectedFurniture().getMustBePlacedOnWall()) {
				detectedWall = wallTool.wallDetect(me.getX(), me.getY());
				
				if(detectedWall == null){
					return;
				}
			}
				
			//d�placement du meuble
			drawingBoardContent.getSelectedFurniture().setPosition(
					me.getPoint().x - relX,
					me.getPoint().y - relY);
		}
	}

	@Override
	public void onMouseReleased(MouseEvent me) {
		//v�rification pour les meubles muraux
		if(drawingBoardContent.getSelectedFurniture() != null) {
			if(drawingBoardContent.getSelectedFurniture().getMustBePlacedOnWall()) {
				detectedWall = wallTool.wallDetect(me.getX(), me.getY());
				
				//suppression du meuble si on le place ailleurs que sur un mur
				if(detectedWall == null){
					drawingBoardContent.deleteFurniture(drawingBoardContent.getSelectedFurniture());
				}
			}
		}
	}

	@Override
	public void onMousePressed(MouseEvent me) {
		//s�lection ou d�s�lection d'un meuble
		drawingBoardContent.setSelectedFurniture(furnitureDetect(me.getX(),
				me.getY()));
		if(drawingBoardContent.getSelectedFurniture() != null) {
			//Placement de l'objet s�lectionn� au premier plan
			drawingBoardContent.getPlacedFurnitures().remove(drawingBoardContent.getSelectedFurniture());
			drawingBoardContent.getPlacedFurnitures().addLast(drawingBoardContent.getSelectedFurniture());
			
			//Calcul de la position de la souris par rapport au coin haut-gauche de l'objet
			relX = me.getX() - drawingBoardContent.getSelectedFurniture().getPosition().x;
			relY = me.getY() - drawingBoardContent.getSelectedFurniture().getPosition().y;
		}
	}
	
	/**
	 * D�tecte le point est contenu par un meuble du plan
	 * @param x : coordonn�e X du point
	 * @param y : coordonn�e Y du point
	 * @return le meuble contenan le point dans le plan, null sinon
	 */
	public Furniture furnitureDetect(int x, int y) {
		for (int i = drawingBoardContent.getPlacedFurnitures().size()-1; i >= 0; i--) {
			if (drawingBoardContent.getPlacedFurnitures().get(i).contains(x,y)) {
				return drawingBoardContent.getPlacedFurnitures().get(i);
			}
		}
		return null;
	}
	
	/**
	 * Param�trer l'outils qui servira � d�tecter les murs.
	 * @param wallTool : outil permettant de d�tecter les murs
	 */
	public void setWallTool(WallTool wallTool) {
		this.wallTool = wallTool;
	}

	public static FurniturePlacementTool getInstance() {
		if(instance == null) {
			instance = new FurniturePlacementTool();
		}
		return instance;
	}
}
