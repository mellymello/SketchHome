package tools;

import java.awt.event.MouseEvent;

import drawableObject.Furniture;

public abstract class PlacementTool implements ITools {
	
	private Furniture selectedFurniture;
	
	public void onMousePressed(MouseEvent e) {}
	public void onMouseDragged(MouseEvent e) {}
	public void onMouseReleased(MouseEvent e) {}
	public void onMouseMoved(MouseEvent e) {}

}
