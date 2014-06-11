package tools;

import java.awt.event.MouseEvent;

import drawableObjects.DrawingBoardContent;
import drawableObjects.FurnitureCreationContent;

/**
 * Représente un outils capable de gérer un clic de souris
 */
public abstract class ITools {
	
	//contenu du plan sur lequel l'outil doit agir
	protected DrawingBoardContent drawingBoardContent;
	
	//contenu de la fenêtre de création de meuble sur lequel l'outil doit agir
	protected FurnitureCreationContent furnitureCreationContent;

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
	
	/**
	 * Paramétrer le plan sur lequel l'outils doit travailler
	 * @param drawingBoardContent : plan qu'on modifie avec l'outil
	 */
	public void setDrawingBoardContent(DrawingBoardContent drawingBoardContent) {
		this.drawingBoardContent = drawingBoardContent;
	}
	
	/**
	 * Paramétrer la zone de création de meuble sur lequel l'outils doit travailler
	 * @param furnitureCreationContent : zone de création de meuble qu'on modifie avec l'outil
	 */
	public void setFurnitureCreationContent(FurnitureCreationContent furnitureCreationContent){
		this.furnitureCreationContent = furnitureCreationContent;
	}
}
