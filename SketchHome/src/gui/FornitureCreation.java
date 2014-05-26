package gui;

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
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import drawableObject.FurnitureCreationContent;
import drawableObject.Wall;
import sun.java2d.loops.DrawLine;
import tools.DrawEllipseTool;
import tools.DrawLineTool;
import tools.DrawPencilTool;
import tools.DrawRectangleTool;
import tools.DrawTriangleTool;
import tools.ITools;

public class FornitureCreation extends JFrame {

	private DrawingPanel dp;
	private ToolPanel toolPanel;
	private ModifPanel modifPanel;

	private ITools selectedTool;
	private DrawEllipseTool drawEllipse;
	private DrawLineTool drawLine;
	private DrawPencilTool drawPencil;
	private DrawRectangleTool drawRectangle;
	private DrawTriangleTool drawTrinagle;
	

	public FornitureCreation() {
		setPreferredSize(new Dimension(500, 400));
		setTitle("Forniture Creator");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());
		
		// creation des outil de travaille
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
		private JButton colors;

		public ToolPanel() {

			setBackground(Color.DARK_GRAY);
			setLayout(new FlowLayout());

			makeButtons();

			add(line);
			add(rectangle);
			add(ellipse);
			add(triangle);
			add(pencil);
			add(colors);

		}

		private void makeButtons() {
			line = new JButton(
					new ImageIcon(
							MainFrame.class
									.getResource("/gui/img/fornitureCreationIcon/line.png")));
			rectangle = new JButton(
					new ImageIcon(
							MainFrame.class
									.getResource("/gui/img/fornitureCreationIcon/rectangle.png")));
			ellipse = new JButton(
					new ImageIcon(
							MainFrame.class
									.getResource("/gui/img/fornitureCreationIcon/ellipse.png")));
			triangle = new JButton(
					new ImageIcon(
							MainFrame.class
									.getResource("/gui/img/fornitureCreationIcon/triangle.png")));
			pencil = new JButton(
					new ImageIcon(
							MainFrame.class
									.getResource("/gui/img/fornitureCreationIcon/pencil.png")));
			colors = new JButton(
					new ImageIcon(
							MainFrame.class
									.getResource("/gui/img/fornitureCreationIcon/colors.png")));

			
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
			
			colors.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("TO DO COLOR CHOOSER HERE !");

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

		private FurnitureCreationContent furnitureCreationContent;
		
		public DrawingPanel() {

			furnitureCreationContent = new FurnitureCreationContent();
		
			
			drawEllipse.setFurnitureCreationContent(furnitureCreationContent);
//			polygonalWallTool.setDrawingBoardContent(drawingBoardContent);
//			furniturePlacementTool.setDrawingBoardContent(drawingBoardContent);
//			onWallPlacementTool.setDrawingBoardContent(drawingBoardContent);
//			onWallPlacementTool.setWallTool(simpleWallTool);
			
			setBackground(Color.WHITE);
			addMouseListener(this);
			addMouseListener(this);
		}
		
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;
			
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			
			
			//dessiner les epllipses:
			for (Ellipse2D e : furnitureCreationContent.getEllipses()) {
				g2.draw(e);
			}
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
