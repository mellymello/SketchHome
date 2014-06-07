package tools;

import java.awt.event.MouseEvent;

/**
 * Repr�sente un outils capable de g�rer un clic de souris.
 * Cet outil doit permettre de placer des �l�ments avec un cliqu� gliss�.
 * Tant que le clic n'est pas relach�, l'�l�ment est redimensionn� et affich� � l'�cran.
 */
public abstract class IDragDrawTool extends ITools{
	@Override
	public void onMouseClicked(MouseEvent me) {}
	
	@Override
	public void onMouseMoved(MouseEvent me) {}
}
