package tools;

import java.awt.event.MouseEvent;

import drawableObject.DrawingBoardContent;

public abstract class ITools {
	
	DrawingBoardContent drawingBoardContent;

	public abstract void onMouseClicked(MouseEvent me);
	public abstract void onMousePressed(MouseEvent me);
	public abstract void onMouseDragged(MouseEvent me);
	public abstract void onMouseReleased(MouseEvent me);
	public abstract void onMouseMoved(MouseEvent me);
	
	public void setDrawingBoardContent(DrawingBoardContent drawingBoardContent) {
		this.drawingBoardContent = drawingBoardContent;
	}
}
