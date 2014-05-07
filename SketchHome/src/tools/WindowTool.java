package tools;

import java.awt.event.MouseEvent;

public class WindowTool extends WallTool {
	
	private static WindowTool instance;

	private WindowTool() {
	}

	@Override
	public void onMouseClicked(MouseEvent me) {
		// TODO Auto-generated method stub
		
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
	
	
	public static WindowTool getInstance() {
		if (instance == null) {
			instance = new WindowTool();
		}
		return instance;
	}

}
