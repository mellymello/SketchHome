package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import tools.DrawEllipseTool;
import tools.ITools;

public class FornitureCreation extends JFrame {

	private DrawingPanel dp;
	private ToolPanel toolPanel;
	private ModifPanel modifPanel;

	private ITools selectedTool;
	private DrawEllipseTool drawEllipse;

	public FornitureCreation() {
		setPreferredSize(new Dimension(400, 400));
		setTitle("Forniture Creator");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		dp = new DrawingPanel();
		toolPanel = new ToolPanel();
		modifPanel = new ModifPanel();

		add(dp, BorderLayout.CENTER);
		add(toolPanel, BorderLayout.NORTH);
		add(modifPanel, BorderLayout.WEST);

		// creation des outil de travaille
		drawEllipse = DrawEllipseTool.getInstance();

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

			ellipse.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					selectedTool = drawEllipse;

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

		public DrawingPanel() {

			setBackground(Color.WHITE);
			addMouseListener(this);
			addMouseListener(this);
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
