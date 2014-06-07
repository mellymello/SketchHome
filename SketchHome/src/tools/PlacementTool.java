package tools;

import java.awt.event.MouseEvent;

import drawableObjects.Furniture;

/**
 * Représente un outils capable de gérer un clic de souris.
 * Cet outil doit permettre de placer des éléments en un clic et de les déplacer ensuite avec un cliqué-glissé. 
 */
public abstract class PlacementTool extends ITools {
	
	//méthodes inutiles
	public void onMouseMoved(MouseEvent me) {}

}
