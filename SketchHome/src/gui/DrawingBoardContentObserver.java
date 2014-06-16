package gui;

import java.util.Observer;

/**
 * Interface repr�sentant un observateur de plan SketchHome capable de fournir 
 * des observateur pour l'addition, la modification et la sssuppression d'�l�ment.
 */
public interface DrawingBoardContentObserver {
	public Observer getAdditionObserver();
	public Observer getDeletionObserver();
	public Observer getModificationObserver();
}
