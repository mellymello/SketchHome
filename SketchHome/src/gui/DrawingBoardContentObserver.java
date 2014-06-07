package gui;

import java.util.Observer;

/**
 * Interface représentant un observateur de plan SketchHome capable de fournir des observateur pour l'addition, modification et suppression d'élément.
 */
public interface DrawingBoardContentObserver {
	public Observer getAdditionObserver();
	public Observer getDeletionObserver();
	public Observer getModificationObserver();
}
