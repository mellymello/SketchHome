package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import sun.org.mozilla.javascript.internal.ObjToIntMap.Iterator;
import tools.ITools;
import drawableObject.*;

public class DrawingBoard extends JPanel implements MouseListener,
		MouseMotionListener {

	private LinkedList<Wall> walls = new LinkedList<Wall>();
	private Wall tmpWall;
	private CtrlPoint selectedCtrlPoint;
	private int ctrlPointDiameter;
	private int wallThickness;

	private Wall selectedWall;
	private boolean showMeasurements;
	
	private LinkedList<Furniture> furnitures = new LinkedList<Furniture>();

	// precision is used to detect if a ctrlPoint is selected or not
	
	
	public DrawingBoard(int width, int height, int ctrlPointDiameter,
			int wallThickness) {

		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(width, height));

		this.ctrlPointDiameter = ctrlPointDiameter;
		this.wallThickness = wallThickness;
		this.showMeasurements = true;

		addMouseListener(this);
		addMouseMotionListener(this);

	}
	
	public void addFurniture(Furniture f) {
		furnitures.add(f);
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
			if (showMeasurements) {
				g2.drawString(String.valueOf(w.getWallLength()),
						(int) (((w.getCtrlPointStart().getX() + w.getCtrlPointEnd().getX()) / 2)),
						(int) ((w.getCtrlPointStart().getY() + w.getCtrlPointEnd().getY()) / 2));

			}

			// painting CtrlPoints
			g2.setColor(Color.red);
			g2.draw(w.getCtrlPointStart().getCtrlPoint());
			g2.draw(w.getCtrlPointEnd().getCtrlPoint());

		}

		// painting the tmp wall
		if (tmpWall != null) {
			g2.setColor(Color.black);
			// g2.drawLine(tmpWall.getCtrlPointStart().getX(), tmpWall
			// .getCtrlPointStart().getY(), tmpWall.getCtrlPointEnd()
			// .getX(), tmpWall.getCtrlPointEnd().getY());
			g2.draw(tmpWall.getWallLine());
			
			if (showMeasurements) {
				g2.drawString(String.valueOf(tmpWall.getWallLength()),
						(int) (((tmpWall.getCtrlPointStart().getX() + tmpWall.getCtrlPointEnd().getX()) / 2)),
						(int) ((tmpWall.getCtrlPointStart().getY() + tmpWall.getCtrlPointEnd().getY()) / 2));

			}

			g2.setColor(Color.red);
			g2.draw(tmpWall.getCtrlPointStart().getCtrlPoint());
			g2.draw(tmpWall.getCtrlPointEnd().getCtrlPoint());
		}
		
		//painting the furnitures
		java.util.ListIterator<Furniture> it = furnitures.listIterator();
		while(it.hasNext()) {
				Furniture furniture = it.next();
				if(furniture.getVisible()) {
					//g.drawImage(Toolkit.getDefaultToolkit().getImage(furniture.getPicture()), furniture.getPosition().x, furniture.getPosition().y, (int)furniture.getDimension().getWidth(), (int)furniture.getDimension().getHeight(), null);
					
					
					Image img;
					try {
						
						/*
						 * TODO : problème avec la rotation : la forme dépasse du carré
						 * idée : faire un carré de longueur = à diagonale de de la forme
						 */
						
						img = ImageIO.read(new File(furniture.getPicture()));
	
					    //int diagonaleImg = (int)Math.sqrt((int)furniture.getDimension().getWidth() * (int)furniture.getDimension().getWidth() + (int)furniture.getDimension().getHeight() * (int)furniture.getDimension().getHeight());
					    
						// Create a buffered image with transparency
						BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
						//BufferedImage bimage = new BufferedImage(diagonaleImg, diagonaleImg, BufferedImage.TYPE_INT_ARGB);
	
					    // Draw the image on to the buffered image
					    Graphics2D bGr = bimage.createGraphics();
					    bGr.drawImage(img, 0, 0, null);
					    bGr.dispose();
					    
						
						// Drawing the rotated image at the required drawing locations
						AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(furniture.getOrientation()), furniture.getDimension().getWidth()/2, furniture.getDimension().getHeight()/2);
						AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
						//g.drawImage(op.filter(bimage, null), furniture.getPosition().x, furniture.getPosition().y, diagonaleImg, diagonaleImg, Color.BLUE, null);
						g.drawImage(op.filter(bimage, null), furniture.getPosition().x, furniture.getPosition().y, (int)furniture.getDimension().getWidth(), (int)furniture.getDimension().getHeight(), Color.BLUE, null);
						
						
						if (showMeasurements) {
							g2.setColor(Color.BLACK);
							
							//largeur affichée en bas de l'image au milleu
							g2.drawString(String.valueOf(furniture.getDimension().width),
									(int) (furniture.getPosition().x + (furniture.getDimension().width - g2.getFontMetrics().stringWidth(String.valueOf(furniture.getDimension().width))) / 2),
									(int) (furniture.getPosition().y + furniture.getDimension().height + g2.getFontMetrics().getHeight()));
							
							//hauteur affichée à droite de l'image au millieu
							g2.drawString(String.valueOf(furniture.getDimension().height),
									(int) (furniture.getPosition().x + furniture.getDimension().width),
									(int) (furniture.getPosition().y + furniture.getDimension().height / 2));
						}

					
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
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
		selectedCtrlPoint = ctrlPointDetect(me.getX(), me.getY());
		selectedWall = wallDetect(me.getX(), me.getY());
		System.out.println(selectedWall);
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		CtrlPoint detectedReleasedPoint = ctrlPointDetect(me.getX(),me.getY());
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

	private CtrlPoint ctrlPointDetect(int x, int y) {
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

	public void toggleShowMeasurements() {
		showMeasurements = !showMeasurements;
	}
}
