package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import drawableObjects.DrawingBoardContent;
import drawableObjects.Furniture;
import drawableObjects.FurnitureLibrary;
import drawableObjects.Wall;
import tools.DoorTool;
import tools.FurniturePlacementTool;
import tools.ITools;
import tools.PolygonalWallTool;
import tools.SimpleWallTool;
import tools.OnWallPlacementTool;

/**
 * JPanel représentant le plan dessiné dans SketchHome.
 */
public class DrawingBoard extends JPanel implements MouseListener,
		MouseMotionListener {

	// contenu du plan
	private DrawingBoardContent drawingBoardContent;

	// si vaut true : on affiche les cotations des éléments du plan
	private boolean showMeasurements;

	// outils utilisables pour dessiner le plan
	private SimpleWallTool simpleWallTool = SimpleWallTool.getInstance();
	private PolygonalWallTool polygonalWallTool = PolygonalWallTool
			.getInstance();
	private FurniturePlacementTool furniturePlacementTool = FurniturePlacementTool
			.getInstance();
	private OnWallPlacementTool onWallPlacementTool = OnWallPlacementTool
			.getInstance();
	// outil actuellement utilisé
	private ITools selectedTool = simpleWallTool;

	// librairie de meuble actuellement sélectionnée
	private FurnitureLibrary selectedFurnitureLibrary;

	public DrawingBoard(int width, int height, int ctrlPointDiameter,
			int wallThickness) {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(width, height));

		drawingBoardContent = new DrawingBoardContent(ctrlPointDiameter,
				wallThickness);

		this.showMeasurements = true;

		addMouseListener(this);
		addMouseMotionListener(this);

		simpleWallTool.setDrawingBoardContent(drawingBoardContent);
		polygonalWallTool.setDrawingBoardContent(drawingBoardContent);
		furniturePlacementTool.setDrawingBoardContent(drawingBoardContent);
		onWallPlacementTool.setDrawingBoardContent(drawingBoardContent);
		onWallPlacementTool.setWallTool(simpleWallTool);
	}

	public void addContentObserver(DrawingBoardContentObserver obs) {
		if (obs.getAdditionObserver() != null)
			drawingBoardContent.addAdditionObserver(obs.getAdditionObserver());
		if (obs.getDeletionObserver() != null)
			drawingBoardContent.addDeletionObserver(obs.getDeletionObserver());
		if (obs.getModificationObserver() != null)
			drawingBoardContent.addModificationObserver(obs
					.getModificationObserver());
	}

	/**
	 * Méthode réalisant l'affichage du plan
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		
		//activation de l'anti-aliasing
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setStroke(new BasicStroke(drawingBoardContent.getWallThickness()));
		// dessiner les murs qui sont déjà fixés
		for (Wall w : drawingBoardContent.getWalls()) {
			paintWall(g2, w);
		}

		// dessiner le mur en cours de tracement
		if (drawingBoardContent.getTmpWall() != null) {
			paintWall(g2, drawingBoardContent.getTmpWall());
		}

		// dessiner les meubles
		for (Furniture furniture : drawingBoardContent.getFurnitures()) {
			if (furniture.getVisible()) {
				/*
				* Affichage de l'image du meuble en y appliquant une rotation
				*/
				//copie de l'image dans un BufferedImage de travail 
				BufferedImage bimage = new BufferedImage((int)furniture.getDimension().getWidth(), (int)furniture.getDimension().getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics2D tempG2D = bimage.createGraphics();
				tempG2D.drawImage(furniture.getLoadedPicture(),
				0, 0,
				(int)furniture.getDimension().getWidth(),
				(int)furniture.getDimension().getHeight(),
				furniture.getColor(), null);
				tempG2D.dispose();

				//copie avec rotation du BufferedImage dans le Graphics d'affichage
				AffineTransform transf = new AffineTransform(); 
				transf.translate(furniture.getPosition().x,furniture.getPosition().y); 
				transf.rotate(Math.toRadians(furniture.getOrientation()), furniture.getDimension().getWidth() / 2, furniture.getDimension().getHeight() / 2);
				g2.drawImage(bimage, transf, null);
				 
				int furnitureCenterX = furniture.getPosition().x + furniture.getDimension().width /2;
				int furnitureCenterY = furniture.getPosition().y + furniture.getDimension().height /2;
				 
				System.out.println("position : " + furnitureCenterX + " " + furnitureCenterY);

				//affichage des cotations
				if (showMeasurements) {
				//largeur affichée en haut au millieu de l'image
				   Point2D initialWidthMeasurementPosition = new Point(furniture.getPosition().x + furniture.getDimension().width / 2, furniture.getPosition().y);
				   Point2D widthMeasurementPosition = new Point2D.Double();
				   //hauteur affichée à droite au millieu de l'image
				   Point2D initialHeightMeasurementPosition = new Point(furniture.getPosition().x + furniture.getDimension().width, furniture.getPosition().y + furniture.getDimension().height / 2);
				   Point2D heightMeasurementPosition = new Point2D.Double();
				    
				   //rotation des positions d'affichage des cotations
				   AffineTransform transformationMeasurementPoint = new AffineTransform(); 
				   transformationMeasurementPoint.rotate(Math.toRadians(furniture.getOrientation()), furnitureCenterX, furnitureCenterY);
				   transformationMeasurementPoint.transform ( initialWidthMeasurementPosition, widthMeasurementPosition );
				   transformationMeasurementPoint.transform ( initialHeightMeasurementPosition, heightMeasurementPosition );

				   g2.setColor(Color.BLACK);
				   g2.drawString(String.valueOf(furniture.getDimension().width),(int)widthMeasurementPosition.getX(),(int)widthMeasurementPosition.getY());
				   g2.drawString(String.valueOf(furniture.getDimension().height),(int)heightMeasurementPosition.getX(),(int)heightMeasurementPosition.getY());
				}
			}
		}
	}

	/**
	 * Dessiner un mur sur le plan.
	 * 
	 * @param g2
	 *            : espace de dessin
	 * @param w
	 *            : mur à dessiner
	 */
	private void paintWall(Graphics2D g2, Wall w) {
		// dessiner le mur
		g2.setColor(Color.black);
		g2.draw(w.getWallLine());

		// afficher les cotations
		if (showMeasurements) {
			DecimalFormat df = new DecimalFormat("#.00");
			String wallLength = df.format(w.getWallLength());
			if (w.getWallLength() > 0) {
				g2.setColor(Color.blue);
				g2.drawString(wallLength,
						(int) (((w.getCtrlPointStart().getX() + w
								.getCtrlPointEnd().getX()) / 2))
								- drawingBoardContent.getWallThickness() * 3,
						(int) ((w.getCtrlPointStart().getY() + w
								.getCtrlPointEnd().getY()) / 2)
								- drawingBoardContent.getWallThickness() * 3);
			}
		}

		// dessiner les CtrlPoints
		g2.setColor(Color.red);
		g2.draw(w.getCtrlPointStart().getCtrlPoint());
		g2.draw(w.getCtrlPointEnd().getCtrlPoint());
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
	public void mouseEntered(MouseEvent me) {
	}

	@Override
	public void mouseExited(MouseEvent me) {
	}

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

	/**
	 * Activer ou désactiver l'affichage des cotations dans le plan
	 */
	public void toggleShowMeasurements() {
		showMeasurements = !showMeasurements;
	}

	public SimpleWallTool getSimpleWallTool() {
		return simpleWallTool;
	}

	public PolygonalWallTool getPolygonalWallTool() {
		return polygonalWallTool;
	}

	public OnWallPlacementTool getonWallPlacementTool() {
		return onWallPlacementTool;
	}

	public FurniturePlacementTool getFurniturePlacementTool() {
		return furniturePlacementTool;
	}

	public void setSelectedTool(ITools tool) {
		selectedTool = tool;
	}

	public DrawingBoardContent getDrawingBoardContent() {
		return drawingBoardContent;
	}

	public void setSelectedFurnitureLibrary(
			FurnitureLibrary selectedFurnitureLibrary) {
		this.selectedFurnitureLibrary = selectedFurnitureLibrary;
	}

	public FurnitureLibrary getSelectedFurnitureLibrary() {
		return selectedFurnitureLibrary;
	}

	public void setSelectedFurniture(Furniture furniture) {
		drawingBoardContent.setSelectedFurniture(furniture);
	}

	public Furniture getSelectedFurniture() {
		return drawingBoardContent.getSelectedFurniture();
	}

	public void setSelectedModelFurniture(Furniture furniture) {
		drawingBoardContent.setSelectedFurnitureModel(furniture);

	}
}
