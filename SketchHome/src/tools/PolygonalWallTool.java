package tools;

import java.awt.event.MouseEvent;

public class PolygonalWallTool extends WallTool {
	
	private static PolygonalWallTool instance;
	
	private PolygonalWallTool() {}

	@Override
<<<<<<< HEAD
	public void onMouseClicked(MouseEvent me) {
		
		if (drawingBoardContent.getSelectedWall() == null) {
			if (drawingBoardContent.getSelectedCtrlPoint() == null) {
				// on doit creer un nouveau mur
				if (drawingBoardContent.getTmpWall() == null) {
					drawingBoardContent.setTmpWall(new Wall(me.getX(), me
							.getY(),
							drawingBoardContent.getCtrlPointDiameter(),
							drawingBoardContent.getWallThickness()));
				} else {
					// on peut fixer le tmpWall et l'ajouter dans la liste des
					// mur
					drawingBoardContent.getTmpWall().setEndPoint(me.getX(),
							me.getY());

					drawingBoardContent.getWalls().add(
							drawingBoardContent.getTmpWall());

					drawingBoardContent.setTmpWall(new Wall(drawingBoardContent.getTmpWall().getCtrlPointEnd(), 
							drawingBoardContent.getCtrlPointDiameter(), drawingBoardContent.getWallThickness()));
				}
			}
			// Dessiner un mur apr�s avoir cliqu� sur un mur existant.
			// La reaction du programme est la suivante:
			// splitter le mur de base (selui ou on a cliqu�) en 2 mur
			// et utiliser le point ou le click � �t� detect� comme point de
			// d�part pour un nouveau mur.
		} else {
			if (drawingBoardContent.getSelectedCtrlPoint() == null) {
				// on commence � dessiner un nouveau mur
				if (drawingBoardContent.getTmpWall() == null) {
					drawingBoardContent.setTmpWall(new Wall(me.getX(), me
							.getY(),
							drawingBoardContent.getCtrlPointDiameter(),
							drawingBoardContent.getWallThickness()));

					drawingBoardContent.getWalls().add(
							new Wall(drawingBoardContent.getSelectedWall()
									.getCtrlPointStart(), drawingBoardContent
									.getTmpWall().getCtrlPointStart(),
									drawingBoardContent.getWallThickness()));

					drawingBoardContent.getWalls().add(
							new Wall(drawingBoardContent.getTmpWall()
									.getCtrlPointStart(), drawingBoardContent
									.getSelectedWall().getCtrlPointEnd(),
									drawingBoardContent.getWallThickness()));

					drawingBoardContent.getWalls().remove(
							drawingBoardContent.getSelectedWall());

				} else {
					// on doit fixer le point d'arriv� du mur
					drawingBoardContent.getTmpWall().setEndPoint(me.getX(),
							me.getY());

					drawingBoardContent.getWalls().add(
							new Wall(drawingBoardContent.getSelectedWall()
									.getCtrlPointStart(), drawingBoardContent
									.getTmpWall().getCtrlPointEnd(),
									drawingBoardContent.getWallThickness()));

					drawingBoardContent.getWalls().add(
							new Wall(drawingBoardContent.getTmpWall()
									.getCtrlPointEnd(), drawingBoardContent
									.getSelectedWall().getCtrlPointEnd(),
									drawingBoardContent.getWallThickness()));

					drawingBoardContent.getWalls().remove(
							drawingBoardContent.getSelectedWall());

					drawingBoardContent.getWalls().add(
							drawingBoardContent.getTmpWall());

					drawingBoardContent.setTmpWall(null);
				}
			} else {
				// l'utilisateur a cliqu� sur un point de contr�le. On doit
				// l'utiliser comme point de d�part pour le nouvau mur
				if (drawingBoardContent.getTmpWall() == null) {
					drawingBoardContent.setTmpWall(new Wall(drawingBoardContent
							.getSelectedCtrlPoint(), drawingBoardContent
							.getCtrlPointDiameter(), drawingBoardContent
							.getWallThickness()));
				} else {
					// ici le point de contr�le selectionn� doit �tre le point
					// de terminaison du mur
					drawingBoardContent.getTmpWall().setNewEndPoint(
							drawingBoardContent.getSelectedCtrlPoint());

					drawingBoardContent.getWalls().add(
							drawingBoardContent.getTmpWall());

					drawingBoardContent.setTmpWall(null);

				}
			}
		}

=======
	public void onMouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
>>>>>>> 1be01dc58d57787b7bb6b48bdbbf2cd3d1325a95
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
