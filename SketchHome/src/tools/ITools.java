package tools;

import java.awt.event.MouseEvent;

public interface ITools {

	public void onMouseClicked(MouseEvent me);
	public void onMousePressed(MouseEvent me);
	public void onMouseDragged(MouseEvent me);
	public void onMouseReleased(MouseEvent me);
	public void onMouseMoved(MouseEvent me);
}
