package tools;

import java.awt.event.MouseEvent;

public class PolygonalWallTool extends WallTool {
	
	private static PolygonalWallTool instance;
	
	private PolygonalWallTool() {}

	@Override
	public void onMouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public static PolygonalWallTool getInstance() {
		if(instance == null) {
			instance = new PolygonalWallTool();
		}
		return instance;
	}

}
