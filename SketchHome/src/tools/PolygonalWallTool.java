package tools;

import java.awt.event.MouseEvent;

public class PolygonalWallTool extends WallTool {
	
	private static PolygonalWallTool instance;
	
	private PolygonalWallTool() {}

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

	public static PolygonalWallTool getInstance() {
		if(instance == null) {
			instance = new PolygonalWallTool();
		}
		return instance;
	}

}
