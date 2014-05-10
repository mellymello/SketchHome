package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import drawableObject.DrawingBoardContent;
import drawableObject.Furniture;
import drawableObject.FurnitureLibrary;
import drawableObject.Wall;
import tools.FurniturePlacementTool;
import tools.ITools;
import tools.PolygonalWallTool;
import tools.SimpleWallTool;
import tools.WindowTool;

/**
 * JPanel représentant le plan dessiné dans SketchHome.
 * @author Jollien Dominique
 */
public class DrawingBoard extends JPanel implements MouseListener,
		MouseMotionListener {

	private DrawingBoardContent drawingBoardContent;

	//si vaut true : on affiche les cotations des éléments du plan
	private boolean showMeasurements;

	//outils utilisables pour dessiner le plan
	private SimpleWallTool simpleWallTool = SimpleWallTool.getInstance();
	private PolygonalWallTool polygonalWallTool = PolygonalWallTool.getInstance();
	private FurniturePlacementTool furniturePlacementTool = FurniturePlacementTool.getInstance();
	private WindowTool windowTool = WindowTool.getInstance();
	//outil actuellement utilisé
	//TODO : initialiser à une valeur sinon crée nullPointerException
	private ITools selectedTool;

	//librairie de meuble actuellement sélectionnée
	private FurnitureLibrary selectedFurnitureLibrary;

	public DrawingBoard(int width, int height, int ctrlPointDiameter,
			int wallThickness) {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(width, height));

		drawingBoardContent = new DrawingBoardContent(ctrlPointDiameter,
				wallThickness);
		// this.ctrlPointDiameter = ctrlPointDiameter;
		// this.wallThickness = wallThickness;
		
		this.showMeasurements = true;

		addMouseListener(this);
		addMouseMotionListener(this);

		simpleWallTool.setDrawingBoardContent(drawingBoardContent);
		polygonalWallTool.setDrawingBoardContent(drawingBoardContent);
		furniturePlacementTool.setDrawingBoardContent(drawingBoardContent);
		windowTool.setDrawingBoardContent(drawingBoardContent);
	}

//	public void addFurniture(Furniture f) {
//		drawingBoardContent.addFurniture(f);
//		//TODO : ne fonctionne pas, utiliser DynamicTree.java
//		f.getLibrary().getJTreeNode().add(f.getJtreeNode());
//		repaint();
//	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		g2.setStroke(new BasicStroke(drawingBoardContent.getWallThickness()));
		// dessiner les mur qui sont déjà fixés
		for (Wall w : drawingBoardContent.getWalls()) {
			paintWall(g2, w);
		}

		// dessiner le tmp wall
		if (drawingBoardContent.getTmpWall() != null) {
			paintWall(g2, drawingBoardContent.getTmpWall());
		}

		// dessiner les meubles
		for (Furniture furniture : drawingBoardContent.getFurnitures()) {
			if (furniture.getVisible()) {
				// g.drawImage(Toolkit.getDefaultToolkit().getImage(furniture.getPicture()),
				// furniture.getPosition().x, furniture.getPosition().y,
				// (int)furniture.getDimension().getWidth(),
				// (int)furniture.getDimension().getHeight(), null);

				Image img;
				//img = ImageIO.read(new File(furniture.getPicture()));
				img = furniture.getLoadedPicture();

				// int diagonaleImg =
				// (int)Math.sqrt((int)furniture.getDimension().getWidth() *
				// (int)furniture.getDimension().getWidth() +
				// (int)furniture.getDimension().getHeight() *
				// (int)furniture.getDimension().getHeight());

				// Create a buffered image with transparency
				BufferedImage bimage = new BufferedImage(
						img.getWidth(null), img.getHeight(null),
						BufferedImage.TYPE_INT_ARGB);
				// BufferedImage bimage = new BufferedImage(diagonaleImg,
				// diagonaleImg, BufferedImage.TYPE_INT_ARGB);

				// Draw the image on to the buffered image
				Graphics2D bGr = bimage.createGraphics();
				bGr.drawImage(img, 0, 0, null);
				bGr.dispose();

				// Drawing the rotated image at the required drawing
				// locations
				AffineTransform tx = AffineTransform.getRotateInstance(Math
						.toRadians(furniture.getOrientation()), furniture
						.getDimension().getWidth() / 2, furniture
						.getDimension().getHeight() / 2);
				AffineTransformOp op = new AffineTransformOp(tx,
						AffineTransformOp.TYPE_BILINEAR);
				// g.drawImage(op.filter(bimage, null),
				// furniture.getPosition().x, furniture.getPosition().y,
				// diagonaleImg, diagonaleImg, Color.BLUE, null);
				g.drawImage(op.filter(bimage, null), furniture
						.getPosition().x, furniture.getPosition().y,
						(int) furniture.getDimension().getWidth(),
						(int) furniture.getDimension().getHeight(),
						furniture.getColor(), null);

				//affichage des cotations
				if (showMeasurements) {
					g2.setColor(Color.BLACK);

					// largeur affichée en bas de l'image au milleu
					g2.drawString(
							String.valueOf(furniture.getDimension().width),
							(int) (furniture.getPosition().x + (furniture
									.getDimension().width - g2
									.getFontMetrics().stringWidth(
											String.valueOf(furniture
													.getDimension().width))) / 2),
							(int) (furniture.getPosition().y
									+ furniture.getDimension().height + g2
									.getFontMetrics().getHeight()));

					// hauteur affichée à droite de l'image au millieu
					g2.drawString(String
							.valueOf(furniture.getDimension().height),
							(int) (furniture.getPosition().x + furniture
									.getDimension().width),
							(int) (furniture.getPosition().y + furniture
									.getDimension().height / 2));
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
		g2.setColor(Color.black);
		g2.draw(w.getWallLine());

		//afficher les cotations
		if (showMeasurements) {
			String wallLength = String.valueOf(w.getWallLength());
			if (wallLength.length() > wallLength.indexOf(".") + 3) {
				g2.setColor(Color.blue);
				g2.drawString(wallLength.substring(0,
						(wallLength.indexOf(".") + 3)), (int) (((w
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

	public void toggleShowMeasurements() {
		showMeasurements = !showMeasurements;
	}

	public SimpleWallTool getSimpleWallTool() {
		return simpleWallTool;
	}

	public PolygonalWallTool getPolygonalWallTool() {
		return polygonalWallTool;
	}
	
	public WindowTool getWindowTool(){
		return windowTool;
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
		drawingBoardContent.setSelectedModelFurniture(furniture);

	}
	
	/**
	 * Crée un fichier image png du plan dessiné 
	 */
	public void createPng() {
		BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = img.createGraphics();
		this.paint(g2);
		try{
			ImageIO.write(img, "png", new File("test export.png"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
