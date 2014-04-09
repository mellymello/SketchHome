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
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JToolBar;


public class SketchHome extends JFrame {
	private JTextField textName;
	private JTextField txtWidth;
	private JTextField txtHeight;
	private JTextField txtRotation;

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
		GridBagLayout gbl_pnlDescription = new GridBagLayout();
		gbl_pnlDescription.columnWidths = new int[] {27, 86, 30};
		gbl_pnlDescription.rowHeights = new int[] {20, 0, 0, 0};
		gbl_pnlDescription.columnWeights = new double[]{0.0, 1.0, 0.0};
		gbl_pnlDescription.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlDescription.setLayout(gbl_pnlDescription);
		
		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		pnlDescription.add(lblName, gbc_lblName);
		
		textName = new JTextField();
		GridBagConstraints gbc_textName = new GridBagConstraints();
		gbc_textName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textName.anchor = GridBagConstraints.NORTH;
		gbc_textName.insets = new Insets(0, 0, 5, 5);
		gbc_textName.gridx = 1;
		gbc_textName.gridy = 0;
		pnlDescription.add(textName, gbc_textName);
		textName.setColumns(10);
		
		JLabel lblWidth = new JLabel("Width");
		GridBagConstraints gbc_lblWidth = new GridBagConstraints();
		gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblWidth.anchor = GridBagConstraints.WEST;
		gbc_lblWidth.gridx = 0;
		gbc_lblWidth.gridy = 1;
		pnlDescription.add(lblWidth, gbc_lblWidth);
		
		txtWidth = new JTextField();
		GridBagConstraints gbc_txtWidth = new GridBagConstraints();
		gbc_txtWidth.anchor = GridBagConstraints.NORTH;
		gbc_txtWidth.insets = new Insets(0, 0, 5, 5);
		gbc_txtWidth.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtWidth.gridx = 1;
		gbc_txtWidth.gridy = 1;
		pnlDescription.add(txtWidth, gbc_txtWidth);
		txtWidth.setColumns(10);
		
		JLabel lblHeight = new JLabel("Height");
		GridBagConstraints gbc_lblHeight = new GridBagConstraints();
		gbc_lblHeight.anchor = GridBagConstraints.WEST;
		gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeight.gridx = 0;
		gbc_lblHeight.gridy = 2;
		pnlDescription.add(lblHeight, gbc_lblHeight);
		
		txtHeight = new JTextField();
		GridBagConstraints gbc_txtHeight = new GridBagConstraints();
		gbc_txtHeight.anchor = GridBagConstraints.NORTH;
		gbc_txtHeight.insets = new Insets(0, 0, 5, 5);
		gbc_txtHeight.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtHeight.gridx = 1;
		gbc_txtHeight.gridy = 2;
		pnlDescription.add(txtHeight, gbc_txtHeight);
		txtHeight.setColumns(10);
		
		JLabel lblRotation = new JLabel("Rotation angle");
		GridBagConstraints gbc_lblRotation = new GridBagConstraints();
		gbc_lblRotation.anchor = GridBagConstraints.WEST;
		gbc_lblRotation.insets = new Insets(0, 0, 0, 5);
		gbc_lblRotation.gridx = 0;
		gbc_lblRotation.gridy = 3;
		pnlDescription.add(lblRotation, gbc_lblRotation);
		
		txtRotation = new JTextField();
		GridBagConstraints gbc_txtRotation = new GridBagConstraints();
		gbc_txtRotation.anchor = GridBagConstraints.NORTH;
		gbc_txtRotation.insets = new Insets(0, 0, 0, 5);
		gbc_txtRotation.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRotation.gridx = 1;
		gbc_txtRotation.gridy = 3;
		pnlDescription.add(txtRotation, gbc_txtRotation);
		txtRotation.setColumns(10);
	}

}
