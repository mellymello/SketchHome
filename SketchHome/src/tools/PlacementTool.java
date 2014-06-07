package tools;

import java.awt.event.MouseEvent;

import drawableObjects.Furniture;

/**
 * Repr�sente un outils capable de g�rer un clic de souris.
 * Cet outil doit permettre de placer des �l�ments en un clic et de les d�placer ensuite avec un cliqu�-gliss�. 
 */
public abstract class PlacementTool extends ITools {
	
	//m�thodes inutiles
	public void onMouseMoved(MouseEvent me) {}

}
