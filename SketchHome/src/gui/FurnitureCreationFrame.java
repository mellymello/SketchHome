package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;

import drawableObjects.FurnitureCreationContent;
import drawableObjects.Wall;
import fileFeatures.ContentExporter;
import sun.java2d.loops.DrawLine;
import sun.security.util.PendingException;
import tools.DrawEllipseTool;
import tools.DrawLineTool;
import tools.DrawPencilTool;
import tools.DrawRectangleTool;
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
		private JButton pencil;
		private JButton erase;
		private JComboBox<Float> strokeSizeChoser;

		public ToolPanel() {

			setBackground(Color.DARK_GRAY);
			setLayout(new FlowLayout());

			makeButtons();
			strokeSizeChoser = new JComboBox<>(new Float[] { 1.0f, 2.0f, 3.0f,
					4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f });
			strokeSizeChoser.setEditable(true);
			strokeSizeChoser.setSelectedIndex(1);
			strokeSizeChoser.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						dp.strokeSize = (Float) strokeSizeChoser
								.getSelectedItem();

					} catch (ClassCastException e) {
						strokeSizeChoser.setSelectedIndex(1);
					}

				}
			});

			add(line);
			add(rectangle);
			add(ellipse);
			add(pencil);
			add(erase);
			add(strokeSizeChoser);
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

		private JButton importImage;
		private JButton save;
		private FileNameExtensionFilter extensionFilterPng ;
		
		private ContentExporter contentExport;

		public ModifPanel() {
			setBackground(Color.GRAY);
			setLayout(new GridLayout(2, 0));
			extensionFilterPng = new FileNameExtensionFilter(
					"Portable Network Graphics", "png");
			
			makeModifButtons();
			contentExport = new ContentExporter(dp);

			add(importImage);
			add(save);
		}

		private void makeModifButtons() {
			importImage = new JButton("Import");
			
			importImage.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser fc = new JFileChooser();
					fc.addChoosableFileFilter(extensionFilterPng);
					fc.setFileFilter(extensionFilterPng);
					
					int action = fc.showOpenDialog(null);
					fc.setMultiSelectionEnabled(false);

					if (action == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fc.getSelectedFile();
						try {
					
							
							dp.furnitureCreationContent.setImg(	ImageIO.read(selectedFile));
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null,
									"Problem loading the image!", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}

				}
			});

			save = new JButton("Save");

			save.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					
					new FurnitureCreationSave(contentExport);


				}
			});
		}
	}

	class DrawingPanel extends JPanel implements MouseMotionListener,
			MouseListener {

		private FurnitureCreationContent furnitureCreationContent;
		private Float strokeSize;
		
		public DrawingPanel() {

			furnitureCreationContent = new FurnitureCreationContent();
			strokeSize = 2.0f;

			drawEllipse.setFurnitureCreationContent(furnitureCreationContent);
			drawPencil.setFurnitureCreationContent(furnitureCreationContent);
			drawLine.setFurnitureCreationContent(furnitureCreationContent);
			drawRectangle.setFurnitureCreationContent(furnitureCreationContent);
			
			
		

			setBackground(Color.WHITE);

			addMouseListener(this);
			addMouseMotionListener(this);

		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_SPEED);
			g2d.setStroke(new BasicStroke(strokeSize, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_BEVEL));
			g2d.setColor(Color.BLACK);

			drawLines(g2d);
			drawImage(g2d);
			g2d.dispose();

		}
		
		public void drawImage(Graphics2D g2d){
			if(furnitureCreationContent.getImg()!=null){
				g2d.drawImage(furnitureCreationContent.getImg(), null, 0, 0);
				repaint();
			}
			
		}

		public void drawLines(Graphics2D g2d) {

			if (furnitureCreationContent.getPoints() != null) {
				for (int i = 0; i < furnitureCreationContent.getPoints().size(); i++) {

					for (int j = 0; j < furnitureCreationContent.getPoints()
							.get(i).size() - 2; j++) {
						int x1 = (int) furnitureCreationContent.getPoints()
								.get(i).get(j).getX();
						int y1 = (int) furnitureCreationContent.getPoints()
								.get(i).get(j).getY();
						int x2 = (int) furnitureCreationContent.getPoints()
								.get(i).get(j + 1).getX();
						int y2 = (int) furnitureCreationContent.getPoints()
								.get(i).get(j + 1).getY();
						g2d.drawLine(x1, y1, x2, y2);
					}
				}

			}
			if (furnitureCreationContent.getTmpLine() != null) {
				g2d.draw(furnitureCreationContent.getTmpLine());
			}
			for (Line2D l : furnitureCreationContent.getLines()) {
				g2d.draw(l);
			}

			if (furnitureCreationContent.getTmpRectangle() != null) {
				g2d.draw(furnitureCreationContent.getTmpRectangle());
			}
			for (Rectangle2D r : furnitureCreationContent.getRectangles()) {
				g2d.draw(r);
			}

			if (furnitureCreationContent.getTmpEllipse() != null) {
				g2d.draw(furnitureCreationContent.getTmpEllipse());
			}
			for (Ellipse2D e : furnitureCreationContent.getEllipses()) {
				g2d.draw(e);
			}
		}

		public void clearContent() {
			furnitureCreationContent.clearContent();
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
