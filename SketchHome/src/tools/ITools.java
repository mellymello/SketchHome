package tools;

import java.awt.event.MouseEvent;

import drawableObjects.DrawingBoardContent;
import drawableObjects.FurnitureCreationContent;

/**
 * Représente un outils capable de gérer un clic de souris
 */
public abstract class ITools {
	
	//contenu du plan sur lequel l'outil doit agir
	DrawingBoardContent drawingBoardContent;
	
	//contenu de la fenêtre de création de meuble sur lequel l'outil doit agir
	FurnitureCreationContent furnitureCreationContent;

	/**
	 * Action réalisée par l'outil lors d'un évènement cliqué-relaché de la souris.
	 * @param me : évènement souris sur lequel on réagit
	 */
	public abstract void onMouseClicked(MouseEvent me);
	
	/**
	 * Action réalisée par l'outil lors d'un évènement cliqué de la souris.
	 * @param me : évènement souris sur lequel on réagit
	 */
	public abstract void onMousePressed(MouseEvent me);
	
	/**
	 * Action réalisée par l'outil lors d'un évènement cliqué-glissé de la souris.
	 * @param me : évènement souris sur lequel on réagit
	 */
	public abstract void onMouseDragged(MouseEvent me);
	
	/**
	 * Action réalisée par l'outil lors d'un évènement relaché de la souris.
	 * @param me : évènement souris sur lequel on réagit
	 */
	public abstract void onMouseReleased(MouseEvent me);
	
	/**
	 * Action réalisée par l'outil lors d'un évènement déplacement de la souris.
	 * @param me : évènement souris sur lequel on réagit
	 */
	public abstract void onMouseMoved(MouseEvent me);
	
	public void setDrawingBoardContent(DrawingBoardContent drawingBoardContent) {
		this.drawingBoardContent = drawingBoardContent;
	}
	
	public void setFurnitureCreationContent(FurnitureCreationContent furnitureCreationContent){
		this.furnitureCreationContent = furnitureCreationContent;
	}
}
