package tools;

import java.awt.event.MouseEvent;

import drawableObject.CtrlPoint;
import drawableObject.Wall;

public class PolygonalWallTool extends WallTool {
	
	private static PolygonalWallTool instance;
	
	private PolygonalWallTool() {}

	@Override
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
			// Dessiner un mur après avoir cliqué sur un mur existant.
			// La reaction du programme est la suivante:
			// splitter le mur de base (selui ou on a cliqué) en 2 mur
			// et utiliser le point ou le click à été detecté comme point de
			// départ pour un nouveau mur.
		} else {
			if (drawingBoardContent.getSelectedCtrlPoint() == null) {
				// on commence à dessiner un nouveau mur
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
					// on doit fixer le point d'arrivé du mur
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
				// l'utilisateur a cliqué sur un point de contrôle. On doit
				// l'utiliser comme point de départ pour le nouvau mur
				if (drawingBoardContent.getTmpWall() == null) {
					drawingBoardContent.setTmpWall(new Wall(drawingBoardContent
							.getSelectedCtrlPoint(), drawingBoardContent
							.getCtrlPointDiameter(), drawingBoardContent
							.getWallThickness()));
				} else {
					// ici le point de contrôle selectionné doit être le point
					// de terminaison du mur
					drawingBoardContent.getTmpWall().setNewEndPoint(
							drawingBoardContent.getSelectedCtrlPoint());

					drawingBoardContent.getWalls().add(
							drawingBoardContent.getTmpWall());

					drawingBoardContent.setTmpWall(null);

				}
			}
		}

	}

	@Override
	public void onMousePressed(MouseEvent me) {

		drawingBoardContent.setSelectedCtrlPoint(ctrlPointDetect(me.getX(),
				me.getY()));
		drawingBoardContent.setSelectedWall(wallDetect(me.getX(), me.getY()));
		
		System.out.println("Pressed:"+ drawingBoardContent.getSelectedCtrlPoint());
		System.out.println("Pressed Wall:"+ drawingBoardContent.getSelectedWall());

	}

	@Override
	public void onMouseDragged(MouseEvent me) {

		// déplacer le point de contrôle
		if (drawingBoardContent.getSelectedCtrlPoint() != null) {
			drawingBoardContent.getSelectedCtrlPoint().setLocation(me.getX(),
					me.getY());
			for (Wall w : drawingBoardContent.getWalls()) {
				if (w.getCtrlPointStart() == drawingBoardContent
						.getSelectedCtrlPoint()) {
					w.setNewStartPoint(drawingBoardContent
							.getSelectedCtrlPoint());
				} else if (w.getCtrlPointEnd() == drawingBoardContent
						.getSelectedCtrlPoint()) {
					w.setNewEndPoint(drawingBoardContent.getSelectedCtrlPoint());
				}
			}
		}

	}

	@Override
	public void onMouseReleased(MouseEvent me) {
		CtrlPoint detectedReleasedPoint = ctrlPointDetect(me.getX(), me.getY());
		Wall detectedRelasedWall = wallDetect(me.getX(), me.getY());
		
		System.out.println("Relased:"+ detectedReleasedPoint);
		System.out.println("Relased Wall:"+ detectedRelasedWall);
		

		if (detectedRelasedWall != null) {
			// si on relache le click sur un point de contrôle il faut setter le
			// point de contrôle cliqué comme étant le start/end point du mur en
			// construction
			if (detectedReleasedPoint != null
					&& detectedReleasedPoint != drawingBoardContent
							.getSelectedCtrlPoint()) {

				if (drawingBoardContent.getSelectedCtrlPoint() != null) {
					for (Wall w : drawingBoardContent.getWalls()) {
						if (w.getCtrlPointStart() == detectedReleasedPoint) {
							w.setNewStartPoint(drawingBoardContent
									.getSelectedCtrlPoint());
						} else if (w.getCtrlPointEnd() == detectedReleasedPoint) {
							w.setNewEndPoint(drawingBoardContent
									.getSelectedCtrlPoint());
						}
					}

					drawingBoardContent
							.setSelectedCtrlPoint(detectedReleasedPoint);
				}
			} else if (drawingBoardContent.getSelectedWall() != detectedRelasedWall) {

				Wall newWall = new Wall(detectedRelasedWall.getCtrlPointStart(), drawingBoardContent.getSelectedCtrlPoint(),
						drawingBoardContent.getWallThickness());
				Wall newWall2 = new Wall(newWall.getCtrlPointEnd(), detectedRelasedWall.getCtrlPointEnd(), drawingBoardContent.getWallThickness());
				
				if(drawingBoardContent.getSelectedCtrlPoint() == drawingBoardContent.getSelectedWall().getCtrlPointEnd()){
					drawingBoardContent.getSelectedWall().setNewEndPoint(
							newWall.getCtrlPointEnd());
				}
				else{
					drawingBoardContent.getSelectedWall().setNewStartPoint(
							newWall.getCtrlPointEnd());
				}
				
				drawingBoardContent.getWalls().add(newWall);
				drawingBoardContent.getWalls().add(newWall2);
				drawingBoardContent.getWalls().remove(detectedRelasedWall);

			}
		}

	}

	@Override
	public void onMouseMoved(MouseEvent me) {
		if (drawingBoardContent.getTmpWall() != null) {

			drawingBoardContent.getTmpWall().setEndPoint(me.getX(), me.getY());

		}

	}

	public static PolygonalWallTool getInstance() {
		if(instance == null) {
			instance = new PolygonalWallTool();
		}
		return instance;
	}

}
