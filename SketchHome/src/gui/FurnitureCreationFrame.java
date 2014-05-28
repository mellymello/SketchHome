package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import drawableObjects.FurnitureCreationContent;
import drawableObjects.Wall;
import sun.java2d.loops.DrawLine;
import sun.security.util.PendingException;
import tools.DrawEllipseTool;
import tools.DrawLineTool;
import tools.DrawPencilTool;
import tools.DrawRectangleTool;
import tools.DrawTriangleTool;
import tools.ITools;

public class FurnitureCreationFrame extends JFrame {

	private DrawingPanel dp;
	private ToolPanel toolPanel;
	private ModifPanel modifPanel;

	private ITools selectedTool;
	private DrawEllipseTool drawEllipse;
	private DrawLineTool drawLine;
	private DrawPencilTool drawPencil;
	private DrawRectangleTool drawRectangle;
	private DrawTriangleTool drawTrinagle;

	public FurnitureCreationFrame() {
		setPreferredSize(new Dimension(500, 400));
		setTitle("Furniture Creator");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		// creation des outil de travail
		drawEllipse = DrawEllipseTool.getInstance();
		drawLine = DrawLineTool.getInstance();
		drawPencil = DrawPencilTool.getInstance();
		drawRectangle = DrawRectangleTool.getInstance();
		drawTrinagle = DrawTriangleTool.getInstance();

		dp = new DrawingPanel();
		toolPanel = new ToolPanel();
		modifPanel = new ModifPanel();

		add(dp, BorderLayout.CENTER);
		add(toolPanel, BorderLayout.NORTH);
		add(modifPanel, BorderLayout.WEST);

		pack();
		setVisible(true);

	}

	class ToolPanel extends JPanel {

		private JButton line;
		private JButton rectangle;
		private JButton ellipse;
		private JButton triangle;
		private JButton pencil;
		private JButton erase;

		public ToolPanel() {

			setBackground(Color.DARK_GRAY);
			setLayout(new FlowLayout());

			makeButtons();

			add(line);
			add(rectangle);
			add(ellipse);
			add(triangle);
			add(pencil);
			add(erase);			

		}

		private void makeButtons() {
			line = new JButton(
					new ImageIcon(
							MainFrame.class
									.getResource("/gui/img/furnitureCreationIcon/line.png")));
			rectangle = new JButton(
					new ImageIcon(
							MainFrame.class
									.getResource("/gui/img/furnitureCreationIcon/rectangle.png")));
			ellipse = new JButton(
					new ImageIcon(
							MainFrame.class
									.getResource("/gui/img/furnitureCreationIcon/ellipse.png")));
			triangle = new JButton(
					new ImageIcon(
							MainFrame.class
									.getResource("/gui/img/furnitureCreationIcon/triangle.png")));
			pencil = new JButton(
					new ImageIcon(
							MainFrame.class
									.getResource("/gui/img/furnitureCreationIcon/pencil.png")));
			
			erase = new JButton(
					new ImageIcon(
							MainFrame.class
									.getResource("/gui/img/furnitureCreationIcon/colors.png")));
		

			line.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					selectedTool = drawLine;

				}
			});

			rectangle.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					selectedTool = drawRectangle;

				}
			});

			ellipse.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					selectedTool = drawEllipse;

				}
			});

			triangle.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					selectedTool = drawTrinagle;

				}
			});

			pencil.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					selectedTool = drawPencil;

				}
			});
			
			erase.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					dp.clearContent();

				}
			});
			

	
		}
	}

	class ModifPanel extends JPanel {

		public ModifPanel() {
			setBackground(Color.GRAY);
		}
	}

	class DrawingPanel extends JPanel implements MouseMotionListener,
			MouseListener {

		BufferedImage bImage = new BufferedImage(500, 400,
				BufferedImage.TYPE_INT_RGB);

		private FurnitureCreationContent furnitureCreationContent;

		public DrawingPanel() {

			Graphics g2d = bImage.getGraphics();
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, 500, 400);

			furnitureCreationContent = new FurnitureCreationContent();

			drawEllipse.setFurnitureCreationContent(furnitureCreationContent);
			drawPencil.setFurnitureCreationContent(furnitureCreationContent);

			// polygonalWallTool.setDrawingBoardContent(drawingBoardContent);
			// furniturePlacementTool.setDrawingBoardContent(drawingBoardContent);
			// onWallPlacementTool.setDrawingBoardContent(drawingBoardContent);
			// onWallPlacementTool.setWallTool(simpleWallTool);

			setBackground(Color.WHITE);

			addMouseListener(this);
			addMouseMotionListener(this);
			g2d.dispose();
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			
			
			drawLines(g2d);
			
			drawImage();
			g2d.drawImage(bImage, 0, 0, null);
			
			g2d.dispose();

		}

		public void drawImage() {
		
			Graphics g = bImage.getGraphics();
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_SPEED);
			g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_BEVEL));
			g2d.setColor(Color.BLACK);
			
			// dessiner les epllipses:			
			for (Ellipse2D e : furnitureCreationContent.getEllipses()) {
				g2d.draw(e);
			}

			drawLines(g2d);
			g2d.dispose();
		}

		public void drawLines(Graphics2D g2d ) {
			
			

			if (furnitureCreationContent.getPoints() != null
					&& furnitureCreationContent.getPoints().size() > 1) {

				
				for (int i = 0; i < furnitureCreationContent.getPoints().size() - 1; i++) {
					int x1 = furnitureCreationContent.getPoints().get(i).x;
					int y1 = furnitureCreationContent.getPoints().get(i).y;
					int x2 = furnitureCreationContent.getPoints().get(i + 1).x;
					int y2 = furnitureCreationContent.getPoints().get(i + 1).y;
					g2d.drawLine(x1, y1, x2, y2);
				}
			}
		}
		
		public void clearContent()
		{
	        furnitureCreationContent.clearContent();
	        Graphics g = bImage.getGraphics();
	        g.setColor(Color.WHITE);
	        g.fillRect(0, 0, 500, 400);
	        g.dispose();
	        repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (selectedTool != null) {
				selectedTool.onMouseDragged(e);

				repaint();
			} else {
				System.out.println("ToolNotSelected");
			}

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (selectedTool != null) {
				selectedTool.onMouseMoved(e);

				repaint();
			} else {
				System.out.println("ToolNotSelected");
			}

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (selectedTool != null) {
				selectedTool.onMouseClicked(e);

				repaint();
				
			} else {
				System.out.println("ToolNotSelected");
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (selectedTool != null) {
				selectedTool.onMousePressed(e);

				repaint();
			} else {
				System.out.println("ToolNotSelected");
			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (selectedTool != null) {
				selectedTool.onMouseReleased(e);

				repaint();
			} else {
				System.out.println("ToolNotSelected");
			}

		}
	}
}
