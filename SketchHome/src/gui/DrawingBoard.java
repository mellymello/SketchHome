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
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import drawableObject.DrawingBoardContent;
import drawableObject.Furniture;
import drawableObject.FurnitureLibrary;
import drawableObject.Wall;
import tools.DoorTool;
import tools.FurniturePlacementTool;
import tools.ITools;
import tools.PolygonalWallTool;
import tools.SimpleWallTool;
import tools.WindowTool;

public class DrawingBoard extends JPanel implements MouseListener,
		MouseMotionListener {

	private ITools selectedTool;

	private DrawingBoardContent drawingBoardContent;

	private boolean showMeasurements;

	private SimpleWallTool simpleWallTool = SimpleWallTool.getInstance();
	private PolygonalWallTool polygonalWallTool = PolygonalWallTool
			.getInstance();
	private FurniturePlacementTool furniturePlacementTool = FurniturePlacementTool
			.getInstance();
	
	private WindowTool windowTool = WindowTool.getInstance();
	
	private DoorTool doorTool = DoorTool.getInstance();

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

		// simpleWallTool.initWalls(walls, ctrlPointDiameter, wallThickness,
		// drawingBoardContent.getTmpWall());
		simpleWallTool.setDrawingBoardContent(drawingBoardContent);
		polygonalWallTool.setDrawingBoardContent(drawingBoardContent);
		furniturePlacementTool.setDrawingBoardContent(drawingBoardContent);
		windowTool.setDrawingBoardContent(drawingBoardContent);
	}

	public void addFurniture(Furniture f) {
		drawingBoardContent.addFurniture(f);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(drawingBoardContent.getWallThickness()));

		// painting the already "fixed" walls
		// dessiner les mur qui sont "d�j�" definitifs
		for (Wall w : drawingBoardContent.getWalls()) {

			g2.setColor(Color.black);

			g2.draw(w.getWallLine());

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

		// dessiner le tmp wall
		if (drawingBoardContent.getTmpWall() != null) {
			g2.setColor(Color.black);
			g2.draw(drawingBoardContent.getTmpWall().getWallLine());

			if (showMeasurements) {
				String wallLength = String.valueOf(drawingBoardContent
						.getTmpWall().getWallLength());

				if (wallLength.length() > wallLength.indexOf(".") + 3) {
					g2.setColor(Color.blue);
					g2.drawString(
							wallLength.substring(0,
									(wallLength.indexOf(".") + 3)),
							(int) (((drawingBoardContent.getTmpWall()
									.getCtrlPointStart().getX() + drawingBoardContent
									.getTmpWall().getCtrlPointEnd().getX()) / 2) - drawingBoardContent
									.getWallThickness() * 3),
							(int) ((drawingBoardContent.getTmpWall()
									.getCtrlPointStart().getY() + drawingBoardContent
									.getTmpWall().getCtrlPointEnd().getY()) / 2)
									- drawingBoardContent.getWallThickness()
									* 3);

				}
			}

			g2.setColor(Color.red);
			g2.draw(drawingBoardContent.getTmpWall().getCtrlPointStart()
					.getCtrlPoint());
			g2.draw(drawingBoardContent.getTmpWall().getCtrlPointEnd()
					.getCtrlPoint());
		}

		// dessiner les furnitures
		java.util.ListIterator<Furniture> it = drawingBoardContent
				.getFurnitures().listIterator();
		while (it.hasNext()) {
			Furniture furniture = it.next();
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
						Color.BLUE, null);

				if (showMeasurements) {
					g2.setColor(Color.BLACK);

					// largeur affich�e en bas de l'image au milleu
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

					// hauteur affich�e � droite de l'image au millieu
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
	
	public DoorTool getDoorTool(){
		return doorTool;
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
}
