package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JPanel;

import sun.org.mozilla.javascript.internal.ObjToIntMap.Iterator;
import drawableObject.*;

public class DrawingBoard extends JPanel implements MouseListener,
		MouseMotionListener {

	private LinkedList<Wall> walls;
	private Wall tmpWall;
	private CtrlPoint selectedCtrlPoint;
	private int ctrlPointDiameter;
	private int wallThickness;

	private Wall selectedWall;
	private boolean showMeasure;

	// precision is uesd to detect if a ctrlPoint is selected or not
	public DrawingBoard(int width, int height, int ctrlPointDiameter,
			int wallThickness) {

		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(width, height));

		walls = new LinkedList<Wall>();
		tmpWall = null;
		selectedCtrlPoint = null;
		this.ctrlPointDiameter = ctrlPointDiameter;
		this.wallThickness = wallThickness;
		this.showMeasure = true;

		this.selectedWall = null;

		addMouseListener(this);
		addMouseMotionListener(this);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(wallThickness));

		// painting the already "fixed" walls
		for (Wall w : walls) {
			g2.setColor(Color.black);
			// g2.drawLine(w.getCtrlPointStart().getX(), w.getCtrlPointStart()
			// .getY(), w.getCtrlPointEnd().getX(), w.getCtrlPointEnd()
			// .getY());

			g2.draw(w.getWallLine());
			// g2.draw(w.getWallRect());
			if (showMeasure) {
				g2.drawString(String.valueOf(w.getWallLength()), (int) (((w
						.getCtrlPointStart().getX() + w.getCtrlPointEnd()
						.getX()) / 2)),
						(int) ((w.getCtrlPointStart().getY() + w
								.getCtrlPointEnd().getY()) / 2));

			}

			// painting CtrlPoints
			g2.setColor(Color.red);
			g2.draw(w.getCtrlPointStart().getCtrlPoint());
			g2.draw(w.getCtrlPointEnd().getCtrlPoint());

		}

		// painting the tmp wall:
		if (tmpWall != null) {
			g2.setColor(Color.black);
			// g2.drawLine(tmpWall.getCtrlPointStart().getX(), tmpWall
			// .getCtrlPointStart().getY(), tmpWall.getCtrlPointEnd()
			// .getX(), tmpWall.getCtrlPointEnd().getY());
			g2.draw(tmpWall.getWallLine());

			g2.setColor(Color.red);
			g2.draw(tmpWall.getCtrlPointStart().getCtrlPoint());
			g2.draw(tmpWall.getCtrlPointEnd().getCtrlPoint());
		}

	}

	@Override
	public void mouseDragged(MouseEvent me) {
		if (selectedCtrlPoint != null) {
			selectedCtrlPoint.setLocation(me.getX(), me.getY());
			for (Wall w : walls) {
				if (w.getCtrlPointStart() == selectedCtrlPoint) {
					w.setNewStartPoint(selectedCtrlPoint);
				} else if (w.getCtrlPointEnd() == selectedCtrlPoint) {
					w.setNewEndPoint(selectedCtrlPoint);
				}
			}

		}

		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		if (tmpWall != null) {

			tmpWall.setEndPoint(me.getX(), me.getY());

			repaint();
		}

	}

	@Override
	public void mouseClicked(MouseEvent me) {
		if (selectedWall == null) {
			if (selectedCtrlPoint == null) {
				if (tmpWall == null) {
					tmpWall = new Wall(me.getX(), me.getY(), ctrlPointDiameter,
							wallThickness);
				} else {
					tmpWall.setEndPoint(me.getX(), me.getY());
					walls.add(tmpWall);
					tmpWall = null;
				}
			} else {
				if (tmpWall == null) {
					tmpWall = new Wall(selectedCtrlPoint, ctrlPointDiameter,
							wallThickness);
				} else {
					tmpWall.setNewEndPoint(selectedCtrlPoint);
					walls.add(tmpWall);
					tmpWall = null;
				}
			}
			//managing the action of connecting a wall with an existing wall on the "middle" (not over a ctrlPoint)
			//The reaction of the program is to split the "base" wall into 2 walls and let the user continue to draw the third wall
		} else { 
			if (selectedCtrlPoint == null) {
				if (tmpWall == null) {
					tmpWall = new Wall(me.getX(), me.getY(), ctrlPointDiameter,
							wallThickness);
					walls.add(new Wall(selectedWall.getCtrlPointStart(), tmpWall.getCtrlPointStart(),wallThickness));
					walls.add(new Wall(tmpWall.getCtrlPointStart(),selectedWall.getCtrlPointEnd(),wallThickness));
					walls.remove(selectedWall);
					
				} else {
					tmpWall.setEndPoint(me.getX(), me.getY());
					walls.add(new Wall(selectedWall.getCtrlPointStart(), tmpWall.getCtrlPointEnd(),wallThickness));
					walls.add(new Wall(tmpWall.getCtrlPointEnd(),selectedWall.getCtrlPointEnd(),wallThickness));
					walls.remove(selectedWall);
					walls.add(tmpWall);
					tmpWall = null;
				}
			} else {
				if (tmpWall == null) {
					tmpWall = new Wall(selectedCtrlPoint, ctrlPointDiameter,
							wallThickness);
				} else {
					tmpWall.setNewEndPoint(selectedCtrlPoint);
					walls.add(tmpWall);
					tmpWall = null;
				}
			}
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent me) {
		// TODO Auto-generated method stub
		System.out.println("---- WALLS ----");
		for (Wall w : walls) {
			System.out.println(w + " length:" + w.getWallLength());
		}
	}

	@Override
	public void mousePressed(MouseEvent me) {
		selectedCtrlPoint = ctrlPointDetected(me.getX(), me.getY());
		selectedWall = wallDetect(me.getX(), me.getY());
		System.out.println(selectedWall);
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		CtrlPoint detectedReleasedPoint = ctrlPointDetected(me.getX(),me.getY());
//		Wall detectedRelasedWall = wallDetect(me.getX(), me.getY());

		if (detectedReleasedPoint != null
				&& detectedReleasedPoint != selectedCtrlPoint) {
			if (selectedCtrlPoint != null) {
				for (Wall w : walls) {
					if (w.getCtrlPointStart() == detectedReleasedPoint) {
						w.setNewStartPoint(selectedCtrlPoint);
					} else if (w.getCtrlPointEnd() == detectedReleasedPoint) {
						w.setNewEndPoint(selectedCtrlPoint);
					}
				}
				selectedCtrlPoint = detectedReleasedPoint;
			}
		}
	
		repaint();
	}

	private CtrlPoint ctrlPointDetected(int x, int y) {
		for (Wall w : walls) {
			if (w.getCtrlPointStart().getCtrlPoint().contains(x, y)) {
				return w.getCtrlPointStart();
			} else if (w.getCtrlPointEnd().getCtrlPoint().contains(x, y)) {
				return w.getCtrlPointEnd();
			}
		}
		return null;
	}

	public Wall wallDetect(int x, int y) {
		for (Wall w : walls) {
			if (w.getWallLine().ptSegDist(x, y) < wallThickness) {
				return w;
			}
		}
		return null;
	}

	public void setShowMeasure(boolean show) {
		showMeasure = show;
	}
}
