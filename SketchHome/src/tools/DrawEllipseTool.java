package tools;

import java.awt.event.MouseEvent;

public class DrawEllipseTool extends IDragDrawTool  {

	
	private static DrawEllipseTool instance;

	private DrawEllipseTool() {
	}

	
	@Override
	public void onMouseClicked(MouseEvent me) {
		System.out.println("Ellipse gogogo");
		
	}

	@Override
	public void onMousePressed(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseDragged(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseMoved(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	public static DrawEllipseTool getInstance() {
		if (instance == null) {
			instance = new DrawEllipseTool();
		}
		return instance;
	}
}
