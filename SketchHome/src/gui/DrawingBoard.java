package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import tools.FurniturePlacementTool;
import tools.ITools;
import tools.PolygonalWallTool;
import tools.SimpleWallTool;
import drawableObjects.DrawingBoardContent;
import drawableObjects.Furniture;
import drawableObjects.FurnitureLibrary;
import drawableObjects.Wall;

/**
 * JPanel représentant le plan dessiné dans SketchHome.
 */
public class DrawingBoard extends JPanel implements MouseListener,
		MouseMotionListener, KeyListener {

	// contenu du plan
	private DrawingBoardContent drawingBoardContent;

	// s'il vaut true, on affiche les cotations des éléments du plan
	private boolean showMeasurements;

	// outils utilisables pour dessiner le plan
	private SimpleWallTool simpleWallTool = SimpleWallTool.getInstance();
	private PolygonalWallTool polygonalWallTool = PolygonalWallTool
			.getInstance();
	private FurniturePlacementTool furniturePlacementTool = FurniturePlacementTool
			.getInstance();
	// outil actuellement utilisé
	private ITools selectedTool = simpleWallTool;

	// librairie de meuble actuellement sélectionnée
	private FurnitureLibrary selectedFurnitureLibrary;

	/**
	 * Crée un nouveau plan.
	 * 
	 * @param width: largeur du plan
	 * @param height : hauteur du plan
	 * @param ctrlPointDiameter : largeur des points de contrôle de mur du plan
	 * @param wallThickness : largeur des murs du plan
	 */
	public DrawingBoard(int width, int height, int ctrlPointDiameter,
			int wallThickness) {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(width, height));

		drawingBoardContent = new DrawingBoardContent(ctrlPointDiameter,
				wallThickness);

		this.showMeasurements = true;

		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);

		simpleWallTool.setDrawingBoardContent(drawingBoardContent);
		polygonalWallTool.setDrawingBoardContent(drawingBoardContent);
		furniturePlacementTool.setDrawingBoardContent(drawingBoardContent);
		furniturePlacementTool.setWallTool(simpleWallTool);
	}

	/**
	 * Abonne un observateur aux modification apportées au plan. Notifications
	 * possibles : ajout, modification ou suppression de meuble.
	 * 
	 * @param obs : observateur à abonner
	 */
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

		// activation de l'anti-aliasing
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setStroke(new BasicStroke(drawingBoardContent.getWallThickness()));
		// dessiner les murs qui sont déjà fixés
		for (Wall w : drawingBoardContent.getPlacedWalls()) {
			paintWall(g2, w);
		}

		// dessiner le mur en cours de tracement
		if (drawingBoardContent.getTmpWall() != null) {
			paintWall(g2, drawingBoardContent.getTmpWall());
		}

		// dessiner les meubles
		for (Furniture furniture : drawingBoardContent.getPlacedFurnitures()) {
			if (furniture.getVisible()) {
				/*
				 * Affichage de l'image du meuble en y appliquant une rotation.
				 */
				// copie de l'image dans un BufferedImage de travail
				BufferedImage bimage = new BufferedImage((int) furniture
						.getDimension().getWidth(), (int) furniture
						.getDimension().getHeight(),
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D tempG2D = bimage.createGraphics();
				tempG2D.drawImage(furniture.getLoadedPicture(), 0, 0,
						(int) furniture.getDimension().getWidth(),
						(int) furniture.getDimension().getHeight(),
						furniture.getColor(), null);
				tempG2D.dispose();

				// Copie avec rotation du BufferedImage dans le Graphics
				// d'affichage.
				AffineTransform transf = new AffineTransform();
				transf.translate(furniture.getPosition().x,
						furniture.getPosition().y);
				transf.rotate(Math.toRadians(furniture.getOrientation()),
						furniture.getDimension().getWidth() / 2, furniture
								.getDimension().getHeight() / 2);
				g2.drawImage(bimage, transf, null);

				// Point central du meuble.
				int furnitureCenterX = furniture.getPosition().x
						+ furniture.getDimension().width / 2;
				int furnitureCenterY = furniture.getPosition().y
						+ furniture.getDimension().height / 2;

				// Affichage de la bordure bleue sur le meuble sélectionné.
				if (furniture.getSelected()) {
					g2.setColor(Color.BLUE);
					g2.drawPolygon(furniture.getPathPolygon());
				}

				// Affichage des cotations.
				if (showMeasurements) {
					// largeur affichée en haut au millieu de l'image
					Point2D initialWidthMeasurementPosition = new Point(
							furniture.getPosition().x
									+ furniture.getDimension().width / 2,
							furniture.getPosition().y);
					Point2D widthMeasurementPosition = new Point2D.Double();
					
					// hauteur affichée à droite au millieu de l'image
					Point2D initialHeightMeasurementPosition = new Point(
							furniture.getPosition().x
									+ furniture.getDimension().width,
							furniture.getPosition().y
									+ furniture.getDimension().height / 2);
					Point2D heightMeasurementPosition = new Point2D.Double();

					// rotation des positions d'affichage des cotations
					AffineTransform transformationMeasurementPoint = new AffineTransform();
					transformationMeasurementPoint.rotate(
							Math.toRadians(furniture.getOrientation()),
							furnitureCenterX, furnitureCenterY);
					transformationMeasurementPoint.transform(
							initialWidthMeasurementPosition,
							widthMeasurementPosition);
					transformationMeasurementPoint.transform(
							initialHeightMeasurementPosition,
							heightMeasurementPosition);

					// afficher les cotations
					g2.setColor(Color.BLACK);
					g2.drawString(
							String.valueOf(furniture.getDimension().width),
							(int) widthMeasurementPosition.getX(),
							(int) widthMeasurementPosition.getY());
					g2.drawString(
							String.valueOf(furniture.getDimension().height),
							(int) heightMeasurementPosition.getX(),
							(int) heightMeasurementPosition.getY());
				}
			}
		}
	}

	/**
	 * Dessiner un mur sur le plan.
	 * 
	 * @param g2 : espace de dessin
	 * @param w : mur à dessiner
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
	/**
	 * Evènement du cliquer-glisser de la souris.
	 * Application de la méthode de l'outil sélectionné.
	 */
	public void mouseDragged(MouseEvent me) {
		selectedTool.onMouseDragged(me);

		repaint();
	}

	@Override
	/**
	 * Evènement du déplacement de la souris.
	 * Application de la méthode de l'outil sélectionné.
	 */
	public void mouseMoved(MouseEvent me) {
		selectedTool.onMouseMoved(me);

		repaint();
	}

	@Override
	/**
	 * Evènement du cliquer-relacher la souris.
	 * Application de la méthode de l'outil sélectionné.
	 */
	public void mouseClicked(MouseEvent me) {
		selectedTool.onMouseClicked(me);

		// besoin du focus pour détecter les touches claviers
		requestFocus();

		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent me) {
	}

	@Override
	public void mouseExited(MouseEvent me) {
	}

	@Override
	/**
	 * Evènement du clic de la souris.
	 * Application de la méthode de l'outil sélectionné
	 */
	public void mousePressed(MouseEvent me) {
		selectedTool.onMousePressed(me);

		// besoin du focus pour détecter les touches claviers
		requestFocus();

		repaint();
	}

	@Override
	/**
	 * Evènement du relacher de la souris.
	 * Application de la méthode de l'outil sélectionné.
	 */
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

	public void setSelectedModelFurniture(Furniture furniture) {
		drawingBoardContent.setSelectedFurnitureModel(furniture);

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	/**
	 * Evènement de l'appui sur une touche de clavier
	 */
	public void keyPressed(KeyEvent e) {
		// supprimer le meuble avec la touche delete
		if (e.getKeyCode() == KeyEvent.VK_DELETE) {
			if (drawingBoardContent.getSelectedFurniture() != null) {
				drawingBoardContent.deleteFurniture(drawingBoardContent
						.getSelectedFurniture());
				repaint();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * Envoyer une notification de modification de meuble aux
	 * ModificationObserver du plan
	 * 
	 * @param f: meuble modifié
	 */
	public void modifyFurniture(Furniture f) {
		drawingBoardContent.getModificationObservable().sendNotify(f);
	}
}
