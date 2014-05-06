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

import tools.ITools;
import tools.PolygonalWallTool;
import tools.SimpleWallTool;
import tools.WallTool;
import drawableObject.*;

public class DrawingBoard extends JPanel implements MouseListener,
		MouseMotionListener {
	
	private ITools selectedTool;
	
	private DrawingBoardContent drawingBoardContent;
	
	private boolean showMeasurements;	
	
	SimpleWallTool simpleWallTool = SimpleWallTool.getInstance();
	PolygonalWallTool polygonalWallTool = PolygonalWallTool.getInstance();
	
	
	public DrawingBoard(int width, int height, int ctrlPointDiameter,
			int wallThickness) {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(width, height));

		drawingBoardContent = new DrawingBoardContent(ctrlPointDiameter, wallThickness);
		
		//this.ctrlPointDiameter = ctrlPointDiameter;
		//this.wallThickness = wallThickness;
		this.showMeasurements = true;

		addMouseListener(this);
		addMouseMotionListener(this);
		
		//simpleWallTool.initWalls(walls, ctrlPointDiameter, wallThickness, drawingBoardContent.getTmpWall());
		simpleWallTool.setDrawingBoardContent(drawingBoardContent);
	}
	
	public void addFurniture(Furniture f) {
		drawingBoardContent.addFurniture(f);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(drawingBoardContent.getWallThickness()));

		// painting the already "fixed" walls
		//dessiner les mur qui sont "déjà" definitifs
		for (Wall w : drawingBoardContent.getWalls()) {
			
			g2.setColor(Color.black);

			g2.draw(w.getWallLine());

			if (showMeasurements) {
				g2.drawString(String.valueOf(w.getWallLength()),
						(int) (((w.getCtrlPointStart().getX() + w.getCtrlPointEnd().getX()) / 2)),
						(int) ((w.getCtrlPointStart().getY() + w.getCtrlPointEnd().getY()) / 2));

			}

			// dessiner les CtrlPoints
			g2.setColor(Color.red);
			g2.draw(w.getCtrlPointStart().getCtrlPoint());
			g2.draw(w.getCtrlPointEnd().getCtrlPoint());

		}

		// dessiner le tmp wall
		if (drawingBoardContent.getTmpWall() != null) {
			g2.setColor(Color.black);
			g2.draw(drawingBoardContent.getTmpWall().getWallLine());
			
			if (showMeasurements) {
				g2.drawString(String.valueOf(drawingBoardContent.getTmpWall().getWallLength()),
						(int) (((drawingBoardContent.getTmpWall().getCtrlPointStart().getX() + drawingBoardContent.getTmpWall().getCtrlPointEnd().getX()) / 2)),
						(int) ((drawingBoardContent.getTmpWall().getCtrlPointStart().getY() + drawingBoardContent.getTmpWall().getCtrlPointEnd().getY()) / 2));

			}

			g2.setColor(Color.red);
			g2.draw(drawingBoardContent.getTmpWall().getCtrlPointStart().getCtrlPoint());
			g2.draw(drawingBoardContent.getTmpWall().getCtrlPointEnd().getCtrlPoint());
		}
		
		//dessiner les furnitures
		java.util.ListIterator<Furniture> it = drawingBoardContent.getFurnitures().listIterator();
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
		selectedTool.onMouseDragged(me);

		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		selectedTool.onMouseMoved(me);
		
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		selectedTool.onMouseClicked(me);
		
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent me) {}

	@Override
	public void mouseExited(MouseEvent me) {}

	@Override
	public void mousePressed(MouseEvent me) {
		selectedTool.onMousePressed(me);
		
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		selectedTool.onMouseReleased(me);
		
		repaint();
	}

	public void toggleShowMeasurements() {
		showMeasurements = !showMeasurements;
	}
	
	public SimpleWallTool getSimpleWallTool(){
		return simpleWallTool;
	}
	
	public void setSelectedTool(ITools tool){
		selectedTool = tool;
	}
	
	public DrawingBoardContent getDrawingBoardContent() {
		return drawingBoardContent;
	}
}
