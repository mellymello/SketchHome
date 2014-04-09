import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.List;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JTree;


public class SketchHome extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SketchHome frame = new SketchHome();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SketchHome() {
		setTitle("SketchHome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 546);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save as");
		mnFile.add(mntmSaveAs);
		
		JMenuItem mntmExport = new JMenuItem("Export");
		mnFile.add(mntmExport);
		
		JMenuItem mntmPrint = new JMenuItem("Print");
		mnFile.add(mntmPrint);
		
		JMenu mnObject = new JMenu("Object");
		menuBar.add(mnObject);
		
		JMenuItem mntmCreateNew = new JMenuItem("Create new");
		mnObject.add(mntmCreateNew);
		
		JMenuItem mntmHide = new JMenuItem("Hide");
		mnObject.add(mntmHide);
		
		JMenuItem mntmCopy = new JMenuItem("Copy");
		mnObject.add(mntmCopy);
		
		JMenuItem mntmDelete = new JMenuItem("Delete");
		mnObject.add(mntmDelete);
		
		JMenu mnDisplay = new JMenu("Display");
		menuBar.add(mnDisplay);
		
		JMenuItem mntmMeasurements = new JMenuItem("Measurements");
		mnDisplay.add(mntmMeasurements);
		
		JMenuItem mntmObjectTree = new JMenuItem("Object tree");
		mnDisplay.add(mntmObjectTree);
		
		JMenuItem mntmDescription = new JMenuItem("Description");
		mnDisplay.add(mntmDescription);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAboutSketchhome = new JMenuItem("About SketchHome");
		mnHelp.add(mntmAboutSketchhome);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel pnlDrawingBoard = new JPanel();
		getContentPane().add(pnlDrawingBoard, BorderLayout.CENTER);
		
		JPanel pnlTools = new JPanel();
		getContentPane().add(pnlTools, BorderLayout.WEST);
		pnlTools.setLayout(new GridLayout(4, 1, 0, 0));
		
		JPanel pnlToolsBtn = new JPanel();
		pnlTools.add(pnlToolsBtn);
		
		JPanel pnlObjects = new JPanel();
		pnlTools.add(pnlObjects);
		
		JTree treeObjects = new JTree();
		pnlTools.add(treeObjects);
		
		JPanel pnlDescription = new JPanel();
		pnlTools.add(pnlDescription);
	}

}
