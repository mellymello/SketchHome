package tools;

import java.awt.event.MouseEvent;

import drawableObjects.DrawingBoardContent;
import drawableObjects.FurnitureCreationContent;

/**
 * Repr�sente un outils capable de g�rer un clic de souris
 */
public abstract class ITools {
	
	//contenu du plan sur lequel l'outil doit agir
	protected DrawingBoardContent drawingBoardContent;
	
	//contenu de la fen�tre de cr�ation de meuble sur lequel l'outil doit agir
	protected FurnitureCreationContent furnitureCreationContent;

	/**
	 * Action r�alis�e par l'outil lors d'un �v�nement cliqu�-relach� de la souris.
	 * @param me : �v�nement souris sur lequel on r�agit
	 */
	public abstract void onMouseClicked(MouseEvent me);
	
	/**
	 * Action r�alis�e par l'outil lors d'un �v�nement cliqu� de la souris.
	 * @param me : �v�nement souris sur lequel on r�agit
	 */
	public abstract void onMousePressed(MouseEvent me);
	
	/**
	 * Action r�alis�e par l'outil lors d'un �v�nement cliqu�-gliss� de la souris.
	 * @param me : �v�nement souris sur lequel on r�agit
	 */
	public abstract void onMouseDragged(MouseEvent me);
	
	/**
	 * Action r�alis�e par l'outil lors d'un �v�nement relach� de la souris.
	 * @param me : �v�nement souris sur lequel on r�agit
	 */
	public abstract void onMouseReleased(MouseEvent me);
	
	/**
	 * Action r�alis�e par l'outil lors d'un �v�nement d�placement de la souris.
	 * @param me : �v�nement souris sur lequel on r�agit
	 */
	public abstract void onMouseMoved(MouseEvent me);
	
	/**
	 * Param�trer le plan sur lequel l'outils doit travailler
	 * @param drawingBoardContent : plan qu'on modifie avec l'outil
	 */
	public void setDrawingBoardContent(DrawingBoardContent drawingBoardContent) {
		this.drawingBoardContent = drawingBoardContent;
	}
	
	/**
	 * Param�trer la zone de cr�ation de meuble sur lequel l'outils doit travailler
	 * @param furnitureCreationContent : zone de cr�ation de meuble qu'on modifie avec l'outil
	 */
	public void setFurnitureCreationContent(FurnitureCreationContent furnitureCreationContent){
		this.furnitureCreationContent = furnitureCreationContent;
	}
}
