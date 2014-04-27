package tools;

import java.awt.event.MouseEvent;

public interface ITools {

	public void onMouseClicked(MouseEvent e);
	public void onMousePressed(MouseEvent e);
	public void onMouseDragged(MouseEvent e);
	public void onMouseReleased(MouseEvent e);
	public void onMouseMoved(MouseEvent e);
	
	public ITools getInstance();
}
