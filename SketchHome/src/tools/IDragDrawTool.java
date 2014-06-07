package tools;

import java.awt.event.MouseEvent;

/**
 * Représente un outils capable de gérer un clic de souris.
 * Cet outil doit permettre de placer des éléments avec un cliqué glissé.
 * Tant que le clic n'est pas relaché, l'élément est redimensionné et affiché à l'écran.
 */
public abstract class IDragDrawTool extends ITools{
	@Override
	public void onMouseClicked(MouseEvent me) {}
	
	@Override
	public void onMouseMoved(MouseEvent me) {}
}
