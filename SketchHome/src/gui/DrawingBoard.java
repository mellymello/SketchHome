package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
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

	//contenu du plan
	private DrawingBoardContent drawingBoardContent;

	//si vaut true : on affiche les cotations des éléments du plan
	private boolean showMeasurements;

	//outils utilisables pour dessiner le plan
	private SimpleWallTool simpleWallTool = SimpleWallTool.getInstance();
	private PolygonalWallTool polygonalWallTool = PolygonalWallTool.getInstance();
	private FurniturePlacementTool furniturePlacementTool = FurniturePlacementTool.getInstance();
	private OnWallPlacementTool onWallPlacementTool = OnWallPlacementTool.getInstance();
	//outil actuellement utilisé
	private ITools selectedTool = simpleWallTool;

	//librairie de meuble actuellement sélectionnée
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
		if(obs.getAdditionObserver() != null)
			drawingBoardContent.addAdditionObserver(obs.getAdditionObserver());
		if(obs.getDeletionObserver() != null)
			drawingBoardContent.addDeletionObserver(obs.getDeletionObserver());
		if(obs.getModificationObserver() != null)
			drawingBoardContent.addModificationObserver(obs.getModificationObserver());
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
				//version sans rotation
//				g.drawImage(furniture.getLoadedPicture(),
//				 furniture.getPosition().x, furniture.getPosition().y,
//				 (int)furniture.getDimension().getWidth(),
//				 (int)furniture.getDimension().getHeight(),
//				 furniture.getColor(), null);

				Image img;
				img = furniture.getLoadedPicture();

				// int diagonaleImg =
				// (int)Math.sqrt((int)furniture.getDimension().getWidth() *
				// (int)furniture.getDimension().getWidth() +
				// (int)furniture.getDimension().getHeight() *
				// (int)furniture.getDimension().getHeight());

				// Create a buffered image with transparency
//				BufferedImage bimage = new BufferedImage(
//						img.getWidth(null), img.getHeight(null),
//						BufferedImage.TYPE_INT_ARGB);
				// BufferedImage bimage = new BufferedImage(diagonaleImg,
				// diagonaleImg, BufferedImage.TYPE_INT_ARGB);

				// Draw the image on to the buffered image
//				Graphics2D bGr = bimage.createGraphics();
//				bGr.drawImage(img, 0, 0, null);
//				bGr.dispose();

				// Drawing the rotated image at the required drawing
				// locations
//				AffineTransform tx = AffineTransform.getRotateInstance(Math
//						.toRadians(furniture.getOrientation()), furniture
//						.getDimension().getWidth() / 2, furniture
//						.getDimension().getHeight() / 2);
//				AffineTransformOp op = new AffineTransformOp(tx,
//						AffineTransformOp.TYPE_BILINEAR);
				// g.drawImage(op.filter(bimage, null),
				// furniture.getPosition().x, furniture.getPosition().y,
				// diagonaleImg, diagonaleImg, Color.BLUE, null);
//				g.drawImage(op.filter(bimage, null), furniture
//						.getPosition().x, furniture.getPosition().y,
//						(int) furniture.getDimension().getWidth(),
//						(int) furniture.getDimension().getHeight(),
//						furniture.getColor(), null);
				 
				//TODO : rotation ok mais dimension non
//				AffineTransform transf = new AffineTransform(); 
//				transf.translate(furniture.getPosition().x,furniture.getPosition().y); 
//				transf.rotate(Math.toRadians(furniture.getOrientation()), furniture.getDimension().getWidth() / 2, furniture.getDimension().getHeight() / 2);
//				g2.drawImage(img, transf, null);
				
				
				//TODO : VERSION QUI FONTIONNE ENFIN !!!!!!!!!!!!!!!!!!!!!!!!!!!!! T-T
				/*
				 * Affichage de l'image du meuble en y appliquant une rotation
				 */
				BufferedImage bimage = new BufferedImage((int)furniture.getDimension().getWidth(), (int)furniture.getDimension().getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics2D tempG2D = bimage.createGraphics();
				tempG2D.drawImage(img,
						 0, 0,
						 (int)furniture.getDimension().getWidth(),
						 (int)furniture.getDimension().getHeight(),
						 furniture.getColor(), null);
				tempG2D.dispose();

				AffineTransform transf = new AffineTransform(); 
				transf.translate(furniture.getPosition().x,furniture.getPosition().y); 
				transf.rotate(Math.toRadians(furniture.getOrientation()), furniture.getDimension().getWidth() / 2, furniture.getDimension().getHeight() / 2);
				g2.drawImage(bimage, transf, null);
				
//				BufferedImage bimage = new BufferedImage((int)furniture.getDimension().getWidth(), (int)furniture.getDimension().getHeight(), BufferedImage.TYPE_INT_ARGB);
//				Graphics2D g6 = bimage.createGraphics();
//				g6.drawImage(img, transf, null);
//				g6.dispose();
//				
//				//g2.drawImage(bimage, null, furniture.getPosition().x, furniture.getPosition().y);
//				g2.drawImage(bimage, furniture
//				.getPosition().x, furniture.getPosition().y,
//				(int) furniture.getDimension().getWidth(),
//				(int) furniture.getDimension().getHeight(),
//				furniture.getColor(), null);

				
				//http://flyingdogz.wordpress.com/2008/02/11/image-rotate-in-java-2-easier-to-use/
				
//			    double sin = Math.abs(Math.sin(Math.toRadians(furniture.getOrientation()))), cos = Math.abs(Math.cos(Math.toRadians(furniture.getOrientation())));
//			    int w = img.getWidth(null), h = img.getHeight(null);
//			    int neww = (int)Math.floor(w*cos+h*sin), newh = (int)Math.floor(h*cos+w*sin);
//			    BufferedImage result = new BufferedImage(neww, newh, Transparency.TRANSLUCENT);
//			    Graphics2D g4 = result.createGraphics();
//			    g4.translate((neww-w)/2, (newh-h)/2);
//			    g4.rotate(Math.toRadians(furniture.getOrientation()), w/2, h/2);
//			    g4.drawRenderedImage((BufferedImage)img, null);
//			    g4.dispose();
//
//			    g2.drawImage(result, furniture.getPosition().x, furniture.getPosition().y, neww, newh, furniture.getColor(), null);
				
				//TODO : retour au point de départ -_-
//				BufferedImage rotatedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
//				Graphics2D g3 = (Graphics2D) rotatedImage.getGraphics();
//				AffineTransform transf = new AffineTransform(); 
//				transf.rotate(Math.toRadians(furniture.getOrientation()), furniture.getDimension().getWidth() / 2, furniture.getDimension().getHeight() / 2);
//				g3.drawImage(img, transf, null);
//				
//				g2.drawImage(rotatedImage, furniture.getPosition().x, furniture.getPosition().y, furniture.getDimension().width, furniture.getDimension().height, null);
				
				

				//affichage des cotations
				if (showMeasurements) {
					g2.setColor(Color.BLACK);

					// largeur affichée en bas de l'image au milleu
					g2.drawString(
							String.valueOf(furniture.getDimension().width),
							(int) (furniture.getPosition().x + (furniture.getDimension().width - g2.getFontMetrics().stringWidth(String.valueOf(furniture.getDimension().width))) / 2
									),
							(int) (furniture.getPosition().y
									+ furniture.getDimension().height + g2
									.getFontMetrics().getHeight()));

					//TODO : version trafiquée qui ne fonctionne pas
					// hauteur affichée à droite de l'image au millieu
					g2.drawString(
							String.valueOf(furniture.getDimension().height),
							(int) (furniture.getPosition().x + Math.cos(Math.toRadians(furniture.getOrientation())) * furniture.getDimension().width),
							(int) (furniture.getPosition().y + Math.sin(Math.toRadians(furniture.getOrientation())) * furniture.getDimension().height) / 2);
				}
			}
		}

	}
	
	/**
	 * Dessiner un mur sur le plan.
	 * @param g2 : espace de dessin
	 * @param w : mur à dessiner
	 */
	private void paintWall(Graphics2D g2, Wall w) {
		//dessiner le mur
		g2.setColor(Color.black);
		g2.draw(w.getWallLine());

		//afficher les cotations
		if (showMeasurements) {
			DecimalFormat df = new DecimalFormat("#.00");
			String wallLength = df.format(w.getWallLength());
			if (w.getWallLength() > 0) {
				g2.setColor(Color.blue);
				g2.drawString(wallLength, (int) (((w
						.getCtrlPointStart().getX() + w.getCtrlPointEnd()
						.getX()) / 2))
						- drawingBoardContent.getWallThickness() * 3,
						(int) ((w.getCtrlPointStart().getY() + w
								.getCtrlPointEnd().getY()) / 2)
								- drawingBoardContent.getWallThickness()
								* 3);
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
	
	public OnWallPlacementTool getonWallPlacementTool(){
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
