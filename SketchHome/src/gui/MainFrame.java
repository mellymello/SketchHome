package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTree;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JToolBar;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Font;

import javax.swing.JSeparator;

import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.Savepoint;

import javax.swing.SwingConstants;

import drawableObject.Furniture;
import drawableObject.FurnitureLibrary;
import tools.ITools;
import tools.TextTool;

import javax.swing.JSplitPane;

public class MainFrame extends JFrame {
	
	private static final int WINDOW_HEIGHT = 600;
	private static final int WINDOW_WIDTH = 800;
	private static final int CTRL_POINT_DIAMETER = 10;
	private static final int WALL_THICKNESS = 5;
	
	private JTextField txtName;
	private JFormattedTextField txtWidth;
	private JFormattedTextField txtHeight;
	private JFormattedTextField txtRotation;
	
	private JLabel lblSelectedobjectlibrary;
	private JPanel pnlFurnitureLibrary;
	
	private DrawingBoard pnlDrawingBoard;

	private DefaultMutableTreeNode bedRoomJtreeNode = new DefaultMutableTreeNode("Bedroom");
	private DefaultMutableTreeNode livingRoomJtreeNode = new DefaultMutableTreeNode("Living room");
	private DefaultMutableTreeNode kitchenJtreeNode = new DefaultMutableTreeNode("Kitchen");
	private DefaultMutableTreeNode diningRoomJtreeNode = new DefaultMutableTreeNode("Living room");
	private DefaultMutableTreeNode bathroomLibraryJtreeNode = new DefaultMutableTreeNode("Bathroom");
	private DefaultMutableTreeNode officeJtreeNode = new DefaultMutableTreeNode("Office");
	private DefaultMutableTreeNode windowJtreeNode = new DefaultMutableTreeNode("Window");
	private DefaultMutableTreeNode doorJtreeNode = new DefaultMutableTreeNode("Door");

	private FurnitureLibrary bedRoomLibrary = new FurnitureLibrary("library/bedroom.xml", "Bedroom", bedRoomJtreeNode);
	private FurnitureLibrary livingRoomLibrary = new FurnitureLibrary("library/livingroom.xml", "Living room", livingRoomJtreeNode);
	private FurnitureLibrary kitchenLibrary = new FurnitureLibrary("library/kitchen.xml"," Kitchen", kitchenJtreeNode);
	private FurnitureLibrary diningRoomLibrary = new FurnitureLibrary("library/diningroom.xml", "Dining room", diningRoomJtreeNode);
	private FurnitureLibrary bathroomLibrary = new FurnitureLibrary("library/bathroom.xml", "Bathroom", bathroomLibraryJtreeNode);
	private FurnitureLibrary officeLibrary = new FurnitureLibrary("library/office.xml", "Office", officeJtreeNode);
	private FurnitureLibrary windowLibrary = new FurnitureLibrary("library/window.xml","Window", windowJtreeNode);
	private FurnitureLibrary doorLibrary = new FurnitureLibrary("library/door.xml","Door", doorJtreeNode);
	
	private SaveContent saveContent;
	
	public MainFrame() {
		setTitle("SketchHome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 546);
		setVisible(true);
		
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
		mntmSaveAs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fs = new JFileChooser();
				
				int action = fs.showSaveDialog(null);
				fs.setMultiSelectionEnabled(false);
				if (action == JFileChooser.APPROVE_OPTION) {
					File f =fs.getSelectedFile();
					saveContent.
					
					
				}
				
				
			}
		});
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
		mntmMeasurements.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/cotations.png")));
		mntmMeasurements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnlDrawingBoard.toggleShowMeasurements();
				pnlDrawingBoard.repaint();
			}
		});
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
		
		pnlDrawingBoard = new DrawingBoard(WINDOW_WIDTH, WINDOW_HEIGHT, CTRL_POINT_DIAMETER,WALL_THICKNESS);
		pnlDrawingBoard.setBackground(Color.WHITE);
		getContentPane().add(pnlDrawingBoard, BorderLayout.CENTER);

		saveContent= new SaveContent(pnlDrawingBoard.getDrawingBoardContent());
		
		JPanel pnlTools = new JPanel();
		pnlTools.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(pnlTools, BorderLayout.WEST);
		pnlTools.setLayout(new GridLayout(4, 1, 0, 0));
		
		JPanel pnlToolsBtn = new JPanel();
		pnlTools.add(pnlToolsBtn);
		GridBagLayout gbl_pnlToolsBtn = new GridBagLayout();
		gbl_pnlToolsBtn.columnWidths = new int[] {0, 0, 0, 0, 0, 0};
		gbl_pnlToolsBtn.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_pnlToolsBtn.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_pnlToolsBtn.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlToolsBtn.setLayout(gbl_pnlToolsBtn);
		
		JButton btnSelectionTool = new JButton("");
		btnSelectionTool.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/sourisB.png")));
		btnSelectionTool.setToolTipText("Select");
		btnSelectionTool.setBorderPainted(false);
		btnSelectionTool.setMargin(new Insets(0, 0, 0, 0));
		btnSelectionTool.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/souris.png")));
		GridBagConstraints gbc_btnSelectionTool = new GridBagConstraints();
		gbc_btnSelectionTool.fill = GridBagConstraints.VERTICAL;
		gbc_btnSelectionTool.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelectionTool.gridx = 0;
		gbc_btnSelectionTool.gridy = 0;
		pnlToolsBtn.add(btnSelectionTool, gbc_btnSelectionTool);
		
		JButton btnFurniturecreation = new JButton("");
		btnFurniturecreation.setToolTipText("Furniture creation");
		btnFurniturecreation.setBorderPainted(false);
		btnFurniturecreation.setMargin(new Insets(0, 0, 0, 0));
		btnFurniturecreation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnFurniturecreation.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/obje_persoB.png")));
		btnFurniturecreation.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/obje_perso.png")));
		GridBagConstraints gbc_btnFurniturecreation = new GridBagConstraints();
		gbc_btnFurniturecreation.fill = GridBagConstraints.VERTICAL;
		gbc_btnFurniturecreation.insets = new Insets(0, 0, 5, 5);
		gbc_btnFurniturecreation.gridx = 1;
		gbc_btnFurniturecreation.gridy = 0;
		pnlToolsBtn.add(btnFurniturecreation, gbc_btnFurniturecreation);
		
		JButton btnTextTool = new JButton("");
		btnTextTool.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/texteB.png")));
		btnTextTool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//selectedTool = TextTool.getInstance();
			}
		});
		btnTextTool.setToolTipText("Text");
		btnTextTool.setBorderPainted(false);
		btnTextTool.setMargin(new Insets(0, 0, 0, 0));
		btnTextTool.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/texte.png")));
		GridBagConstraints gbc_btnTextTool = new GridBagConstraints();
		gbc_btnTextTool.fill = GridBagConstraints.VERTICAL;
		gbc_btnTextTool.insets = new Insets(0, 0, 5, 5);
		gbc_btnTextTool.gridx = 2;
		gbc_btnTextTool.gridy = 0;
		pnlToolsBtn.add(btnTextTool, gbc_btnTextTool);
		
		JButton btnImageTool = new JButton("");
		btnImageTool.setToolTipText("Image");
		btnImageTool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnImageTool.setBorderPainted(false);
		btnImageTool.setMargin(new Insets(0, 0, 0, 0));
		btnImageTool.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/outil_imageB.png")));
		btnImageTool.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/outil_image.png")));
		GridBagConstraints gbc_btnImageTool = new GridBagConstraints();
		gbc_btnImageTool.insets = new Insets(0, 0, 5, 5);
		gbc_btnImageTool.gridx = 3;
		gbc_btnImageTool.gridy = 0;
		pnlToolsBtn.add(btnImageTool, gbc_btnImageTool);
		
		JButton btnZoomplus = new JButton("ZoomPlus");
		GridBagConstraints gbc_btnZoomplus = new GridBagConstraints();
		gbc_btnZoomplus.fill = GridBagConstraints.VERTICAL;
		gbc_btnZoomplus.insets = new Insets(0, 0, 5, 5);
		gbc_btnZoomplus.gridx = 4;
		gbc_btnZoomplus.gridy = 0;
		pnlToolsBtn.add(btnZoomplus, gbc_btnZoomplus);
		
		JButton btnZoomminus = new JButton("ZoomMinus");
		GridBagConstraints gbc_btnZoomminus = new GridBagConstraints();
		gbc_btnZoomminus.fill = GridBagConstraints.VERTICAL;
		gbc_btnZoomminus.insets = new Insets(0, 0, 5, 0);
		gbc_btnZoomminus.gridx = 5;
		gbc_btnZoomminus.gridy = 0;
		pnlToolsBtn.add(btnZoomminus, gbc_btnZoomminus);
		
		JButton btnConstructionMode = new JButton("");
		btnConstructionMode.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/constructionB.png")));
		btnConstructionMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConstructionMode.setToolTipText("Construction Mode");
		btnConstructionMode.setBorderPainted(false);
		btnConstructionMode.setMargin(new Insets(0, 0, 0, 0));
		btnConstructionMode.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/construction.png")));
		GridBagConstraints gbc_btnConstructionMode = new GridBagConstraints();
		gbc_btnConstructionMode.fill = GridBagConstraints.BOTH;
		gbc_btnConstructionMode.gridwidth = 3;
		gbc_btnConstructionMode.insets = new Insets(0, 0, 5, 5);
		gbc_btnConstructionMode.gridx = 0;
		gbc_btnConstructionMode.gridy = 1;
		pnlToolsBtn.add(btnConstructionMode, gbc_btnConstructionMode);
		
		JButton btnFurnitureMode = new JButton("");
		btnFurnitureMode.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/ameublementB.png")));
		btnFurnitureMode.setToolTipText("Furniture Mode");
		btnFurnitureMode.setBorderPainted(false);
		btnFurnitureMode.setMargin(new Insets(0, 0, 0, 0));
		btnFurnitureMode.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/ameublement.png")));
		GridBagConstraints gbc_btnFurnitureMode = new GridBagConstraints();
		gbc_btnFurnitureMode.insets = new Insets(0, 0, 5, 0);
		gbc_btnFurnitureMode.fill = GridBagConstraints.BOTH;
		gbc_btnFurnitureMode.gridwidth = 3;
		gbc_btnFurnitureMode.gridx = 3;
		gbc_btnFurnitureMode.gridy = 1;
		pnlToolsBtn.add(btnFurnitureMode, gbc_btnFurnitureMode);
		
		JButton btnBedroom = new JButton("");
		btnBedroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectLibrary(bedRoomLibrary);
			}
		});
		btnBedroom.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/chambreB.png")));
		btnBedroom.setToolTipText("BedRoom");
		btnBedroom.setBorderPainted(false);
		btnBedroom.setMargin(new Insets(0, 0, 0, 0));
		btnBedroom.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/chambre.png")));
		GridBagConstraints gbc_btnBedroom = new GridBagConstraints();
		gbc_btnBedroom.fill = GridBagConstraints.VERTICAL;
		gbc_btnBedroom.insets = new Insets(0, 0, 5, 5);
		gbc_btnBedroom.gridx = 0;
		gbc_btnBedroom.gridy = 2;
		pnlToolsBtn.add(btnBedroom, gbc_btnBedroom);
		
		JButton btnOffice = new JButton("");
		btnOffice.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/bureauB.png")));
		btnOffice.setToolTipText("Office");
		btnOffice.setBorderPainted(false);
		btnOffice.setMargin(new Insets(0, 0, 0, 0));
		btnOffice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectLibrary(officeLibrary);				
			}
		});
		btnOffice.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/bureau.png")));
		GridBagConstraints gbc_btnOffice = new GridBagConstraints();
		gbc_btnOffice.fill = GridBagConstraints.VERTICAL;
		gbc_btnOffice.insets = new Insets(0, 0, 5, 5);
		gbc_btnOffice.gridx = 1;
		gbc_btnOffice.gridy = 2;
		pnlToolsBtn.add(btnOffice, gbc_btnOffice);
		
		JButton btnKitchen = new JButton("");
		btnKitchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectLibrary(kitchenLibrary);				
			}
		});
		btnKitchen.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/cuisineB.png")));
		btnKitchen.setToolTipText("Kitchen");
		btnKitchen.setBorderPainted(false);
		btnKitchen.setMargin(new Insets(0, 0, 0, 0));
		btnKitchen.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/cuisine.png")));
		GridBagConstraints gbc_btnKitchen = new GridBagConstraints();
		gbc_btnKitchen.fill = GridBagConstraints.VERTICAL;
		gbc_btnKitchen.insets = new Insets(0, 0, 5, 5);
		gbc_btnKitchen.gridx = 2;
		gbc_btnKitchen.gridy = 2;
		pnlToolsBtn.add(btnKitchen, gbc_btnKitchen);
		
		JButton btnLivingroom = new JButton("");
		btnLivingroom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectLibrary(livingRoomLibrary);
			}
		});
		btnLivingroom.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/salonB.png")));
		btnLivingroom.setToolTipText("Living Room");
		btnLivingroom.setBorderPainted(false);
		btnLivingroom.setMargin(new Insets(0, 0, 0, 0));
		btnLivingroom.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/salon.png")));
		GridBagConstraints gbc_btnLivingroom = new GridBagConstraints();
		gbc_btnLivingroom.fill = GridBagConstraints.VERTICAL;
		gbc_btnLivingroom.insets = new Insets(0, 0, 5, 5);
		gbc_btnLivingroom.gridx = 3;
		gbc_btnLivingroom.gridy = 2;
		pnlToolsBtn.add(btnLivingroom, gbc_btnLivingroom);
		
		JButton btnBathroom = new JButton("");
		btnBathroom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectLibrary(bathroomLibrary);				
			}
		});
		btnBathroom.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/salle_de_bainB.png")));
		btnBathroom.setToolTipText("Bath Room");
		btnBathroom.setBorderPainted(false);
		btnBathroom.setMargin(new Insets(0, 0, 0, 0));
		btnBathroom.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/salle_de_bain.png")));
		GridBagConstraints gbc_btnBathroom = new GridBagConstraints();
		gbc_btnBathroom.fill = GridBagConstraints.VERTICAL;
		gbc_btnBathroom.insets = new Insets(0, 0, 5, 5);
		gbc_btnBathroom.gridx = 4;
		gbc_btnBathroom.gridy = 2;
		pnlToolsBtn.add(btnBathroom, gbc_btnBathroom);
		
		JButton btnDiningRoom = new JButton("");
		btnDiningRoom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectLibrary(diningRoomLibrary);
			}
		});
		btnDiningRoom.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/salle_a_mangerB.png")));
		btnDiningRoom.setToolTipText("Dining Room");
		btnDiningRoom.setBorderPainted(false);
		btnDiningRoom.setMargin(new Insets(0, 0, 0, 0));
		btnDiningRoom.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/salle_a_manger.png")));
		GridBagConstraints gbc_btnDiningRoom = new GridBagConstraints();
		gbc_btnDiningRoom.fill = GridBagConstraints.VERTICAL;
		gbc_btnDiningRoom.insets = new Insets(0, 0, 5, 0);
		gbc_btnDiningRoom.gridx = 5;
		gbc_btnDiningRoom.gridy = 2;
		pnlToolsBtn.add(btnDiningRoom, gbc_btnDiningRoom);
		
		JButton btnCustomfurniture = new JButton("");
		btnCustomfurniture.setToolTipText("Furniture creation");
		btnCustomfurniture.setBorderPainted(false);
		btnCustomfurniture.setMargin(new Insets(0, 0, 0, 0));
		btnCustomfurniture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCustomfurniture.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/obje_persoB.png")));
		btnCustomfurniture.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/obje_perso.png")));
		GridBagConstraints gbc_btnCustomfurniture = new GridBagConstraints();
		gbc_btnCustomfurniture.fill = GridBagConstraints.VERTICAL;
		gbc_btnCustomfurniture.insets = new Insets(0, 0, 5, 5);
		gbc_btnCustomfurniture.gridx = 0;
		gbc_btnCustomfurniture.gridy = 3;
		pnlToolsBtn.add(btnCustomfurniture, gbc_btnCustomfurniture);
		
		JButton btnWall = new JButton("");
		btnWall.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/mur_simpleB.png")));
		btnWall.setToolTipText("Simple Wall");
		btnWall.setBorderPainted(false);
		btnWall.setMargin(new Insets(0, 0, 0, 0));
		btnWall.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/mur_simple.png")));
		btnWall.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pnlDrawingBoard.setSelectedTool(pnlDrawingBoard.getSimpleWallTool());
				
			}
		});
		GridBagConstraints gbc_btnWall = new GridBagConstraints();
		gbc_btnWall.fill = GridBagConstraints.VERTICAL;
		gbc_btnWall.insets = new Insets(0, 0, 0, 5);
		gbc_btnWall.gridx = 0;
		gbc_btnWall.gridy = 4;
		pnlToolsBtn.add(btnWall, gbc_btnWall);
		
		JButton btnWall2 = new JButton("");
		btnWall2.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/mur_polygonalB.png")));
		btnWall2.setToolTipText("Contiguous Wall");
		btnWall2.setBorderPainted(false);
		btnWall2.setMargin(new Insets(0, 0, 0, 0));
		btnWall2.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/mur_polygonal.png")));
		btnWall2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pnlDrawingBoard.setSelectedTool(pnlDrawingBoard.getPolygonalWallTool());
				
			}
		});
		GridBagConstraints gbc_btnWall2 = new GridBagConstraints();
		gbc_btnWall2.fill = GridBagConstraints.VERTICAL;
		gbc_btnWall2.insets = new Insets(0, 0, 0, 5);
		gbc_btnWall2.gridx = 1;
		gbc_btnWall2.gridy = 4;
		pnlToolsBtn.add(btnWall2, gbc_btnWall2);
		
		JButton btnWindow = new JButton("");
		btnWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectLibrary(windowLibrary);
			}
		});
		btnWindow.setToolTipText("Windows");
		btnWindow.setBorderPainted(false);
		btnWindow.setMargin(new Insets(0, 0, 0, 0));
		btnWindow.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/fenetreB.png")));
		btnWindow.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/fenetre.png")));
		GridBagConstraints gbc_btnWindow = new GridBagConstraints();
		gbc_btnWindow.fill = GridBagConstraints.VERTICAL;
		gbc_btnWindow.insets = new Insets(0, 0, 0, 5);
		gbc_btnWindow.gridx = 2;
		gbc_btnWindow.gridy = 4;
		pnlToolsBtn.add(btnWindow, gbc_btnWindow);
		
		JButton btnDoor = new JButton("");
		btnDoor.setToolTipText("Doors");
		btnDoor.setBorderPainted(false);
		btnDoor.setMargin(new Insets(0, 0, 0, 0));
		btnDoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectLibrary(doorLibrary);
			}
		});
		btnDoor.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/porteB.png")));
		btnDoor.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/porte.png")));
		GridBagConstraints gbc_btnDoor = new GridBagConstraints();
		gbc_btnDoor.fill = GridBagConstraints.VERTICAL;
		gbc_btnDoor.insets = new Insets(0, 0, 0, 5);
		gbc_btnDoor.gridx = 3;
		gbc_btnDoor.gridy = 4;
		pnlToolsBtn.add(btnDoor, gbc_btnDoor);
		
		JButton btnStair = new JButton("");
		btnStair.setBorderPainted(false);
		btnStair.setMargin(new Insets(0, 0, 0, 0));
		btnStair.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/escaliersB.png")));
		btnStair.setToolTipText("Stairs");
		btnStair.setBorderPainted(false);
		btnStair.setMargin(new Insets(0, 0, 0, 0));
		btnStair.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/escaliers.png")));
		GridBagConstraints gbc_btnStair = new GridBagConstraints();
		gbc_btnStair.fill = GridBagConstraints.VERTICAL;
		gbc_btnStair.insets = new Insets(0, 0, 0, 5);
		gbc_btnStair.gridx = 4;
		gbc_btnStair.gridy = 4;
		pnlToolsBtn.add(btnStair, gbc_btnStair);
		
		JPanel pnlObjects = new JPanel();
		pnlTools.add(pnlObjects);
		GridBagLayout gbl_pnlObjects = new GridBagLayout();
		gbl_pnlObjects.columnWidths = new int[]{633, 0};
		gbl_pnlObjects.rowHeights = new int[]{14, 0, 107, 0};
		gbl_pnlObjects.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnlObjects.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		pnlObjects.setLayout(gbl_pnlObjects);
		
		lblSelectedobjectlibrary = new JLabel("Selected Object Library :");
		lblSelectedobjectlibrary.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblSelectedobjectlibrary = new GridBagConstraints();
		gbc_lblSelectedobjectlibrary.anchor = GridBagConstraints.NORTH;
		gbc_lblSelectedobjectlibrary.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSelectedobjectlibrary.insets = new Insets(0, 0, 5, 0);
		gbc_lblSelectedobjectlibrary.gridx = 0;
		gbc_lblSelectedobjectlibrary.gridy = 0;
		pnlObjects.add(lblSelectedobjectlibrary, gbc_lblSelectedobjectlibrary);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.BLACK);
		separator_2.setBackground(Color.BLACK);
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_2.insets = new Insets(0, 0, 5, 0);
		gbc_separator_2.gridx = 0;
		gbc_separator_2.gridy = 1;
		pnlObjects.add(separator_2, gbc_separator_2);
		
		pnlFurnitureLibrary = new JPanel();
		GridBagConstraints gbc_pnlObjectLibrary = new GridBagConstraints();
		gbc_pnlObjectLibrary.fill = GridBagConstraints.BOTH;
		gbc_pnlObjectLibrary.gridx = 0;
		gbc_pnlObjectLibrary.gridy = 2;
		pnlObjects.add(pnlFurnitureLibrary, gbc_pnlObjectLibrary);
		pnlFurnitureLibrary.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel pnlObjectTree = new JPanel();
		pnlTools.add(pnlObjectTree);
		GridBagLayout gbl_pnlObjectTree = new GridBagLayout();
		gbl_pnlObjectTree.columnWidths = new int[]{633, 0};
		gbl_pnlObjectTree.rowHeights = new int[]{14, 0, 107, 0};
		gbl_pnlObjectTree.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_pnlObjectTree.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlObjectTree.setLayout(gbl_pnlObjectTree);
		
		JLabel lblObjecttree = new JLabel("Used objects");
		lblObjecttree.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblObjecttree = new GridBagConstraints();
		gbc_lblObjecttree.anchor = GridBagConstraints.NORTH;
		gbc_lblObjecttree.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblObjecttree.insets = new Insets(0, 0, 5, 0);
		gbc_lblObjecttree.gridx = 0;
		gbc_lblObjecttree.gridy = 0;
		pnlObjectTree.add(lblObjecttree, gbc_lblObjecttree);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(Color.BLACK);
		separator_1.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1.gridx = 0;
		gbc_separator_1.gridy = 1;
		pnlObjectTree.add(separator_1, gbc_separator_1);
		
		
		DefaultMutableTreeNode rootJtreeNode = new DefaultMutableTreeNode("Placed furnitures");
		rootJtreeNode.add(bedRoomJtreeNode);
		rootJtreeNode.add(livingRoomJtreeNode);
		
		JTree treeUsedObject = new JTree(rootJtreeNode); //TODO : observeur de drawingBoardContent ?
		GridBagConstraints gbc_treeUsedObject = new GridBagConstraints();
		gbc_treeUsedObject.fill = GridBagConstraints.BOTH;
		gbc_treeUsedObject.gridx = 0;
		gbc_treeUsedObject.gridy = 2;
		pnlObjectTree.add(treeUsedObject, gbc_treeUsedObject);
		
		JPanel pnlDescription = new JPanel();
		pnlTools.add(pnlDescription);
		GridBagLayout gbl_pnlDescription = new GridBagLayout();
		gbl_pnlDescription.columnWidths = new int[] {27, 86};
		gbl_pnlDescription.rowHeights = new int[] {0, 0, 20, 0, 0, 0};
		gbl_pnlDescription.columnWeights = new double[]{0.0, 1.0};
		gbl_pnlDescription.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		pnlDescription.setLayout(gbl_pnlDescription);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.gridwidth = 2;
		gbc_lblDescription.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 0);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 0;
		pnlDescription.add(lblDescription, gbc_lblDescription);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.BLACK);
		separator.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridwidth = 2;
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 1;
		pnlDescription.add(separator, gbc_separator);
		
		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 2;
		pnlDescription.add(lblName, gbc_lblName);
		
		txtName = new JTextField();
		lblName.setLabelFor(txtName);
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.anchor = GridBagConstraints.NORTH;
		gbc_txtName.insets = new Insets(0, 0, 5, 0);
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 2;
		pnlDescription.add(txtName, gbc_txtName);
		txtName.setColumns(10);
		
		JLabel lblWidth = new JLabel("Width");
		GridBagConstraints gbc_lblWidth = new GridBagConstraints();
		gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblWidth.anchor = GridBagConstraints.WEST;
		gbc_lblWidth.gridx = 0;
		gbc_lblWidth.gridy = 3;
		pnlDescription.add(lblWidth, gbc_lblWidth);
		
		txtWidth = new JFormattedTextField();
		lblWidth.setLabelFor(txtWidth);
		GridBagConstraints gbc_txtWidth = new GridBagConstraints();
		gbc_txtWidth.anchor = GridBagConstraints.NORTH;
		gbc_txtWidth.insets = new Insets(0, 0, 5, 0);
		gbc_txtWidth.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtWidth.gridx = 1;
		gbc_txtWidth.gridy = 3;
		pnlDescription.add(txtWidth, gbc_txtWidth);
		txtWidth.setColumns(10);
		
		JLabel lblHeight = new JLabel("Height");
		GridBagConstraints gbc_lblHeight = new GridBagConstraints();
		gbc_lblHeight.anchor = GridBagConstraints.WEST;
		gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeight.gridx = 0;
		gbc_lblHeight.gridy = 4;
		pnlDescription.add(lblHeight, gbc_lblHeight);
		
		txtHeight = new JFormattedTextField();
		lblHeight.setLabelFor(txtHeight);
		GridBagConstraints gbc_txtHeight = new GridBagConstraints();
		gbc_txtHeight.anchor = GridBagConstraints.NORTH;
		gbc_txtHeight.insets = new Insets(0, 0, 5, 0);
		gbc_txtHeight.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtHeight.gridx = 1;
		gbc_txtHeight.gridy = 4;
		pnlDescription.add(txtHeight, gbc_txtHeight);
		txtHeight.setColumns(10);
		
		JLabel lblRotation = new JLabel("Rotation angle");
		GridBagConstraints gbc_lblRotation = new GridBagConstraints();
		gbc_lblRotation.anchor = GridBagConstraints.WEST;
		gbc_lblRotation.insets = new Insets(0, 0, 0, 5);
		gbc_lblRotation.gridx = 0;
		gbc_lblRotation.gridy = 5;
		pnlDescription.add(lblRotation, gbc_lblRotation);
		
		txtRotation = new JFormattedTextField();
		lblRotation.setLabelFor(txtRotation);
		GridBagConstraints gbc_txtRotation = new GridBagConstraints();
		gbc_txtRotation.anchor = GridBagConstraints.NORTH;
		gbc_txtRotation.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRotation.gridx = 1;
		gbc_txtRotation.gridy = 5;
		pnlDescription.add(txtRotation, gbc_txtRotation);
		txtRotation.setColumns(10);
		
		pack();
	}
	
	/**
	 * Sélection d'une librairie de meuble
	 * @param furnitureLibrary : librairie de meuble à afficher
	 */
	public void selectLibrary(FurnitureLibrary furnitureLibrary) {
		lblSelectedobjectlibrary.setText("Selected Object Library : "+ furnitureLibrary.getName());
		furnitureLibrary.loadLibraryContent();
		showContentOfLibrary(furnitureLibrary);
		
		pnlDrawingBoard.setSelectedFurnitureLibrary(furnitureLibrary);
		pnlDrawingBoard.setSelectedTool(pnlDrawingBoard.getFurniturePlacementTool());
		pnlDrawingBoard.setSelectedModelFurniture(null);
	}
	
	/**
	 * Remplissage de l'interface graphique avec le contenu d'une librairie de meuble pour permettre sa visualisation.
	 * @param furnitureLibrary : librairie de meuble à afficher
	 */
	public void showContentOfLibrary(FurnitureLibrary furnitureLibrary) {
		pnlFurnitureLibrary.removeAll();
		
		for (Furniture f : furnitureLibrary.getFurnitures()) {
			pnlFurnitureLibrary.add(new FurnitureMiniature(f));
		}
		
		pnlFurnitureLibrary.validate();
		pnlFurnitureLibrary.repaint();
	}
	
	/**
	 * Classe servant à représenter graphiquement le contenu d'une librairie.
	 * Contient une miniature de l'image d'un meuble et son nom.
	 * Permet la sélection du meuble représenté pour l'utiliser dans le plan. 
	 * @author Jollien Dominique
	 */
	class FurnitureMiniature extends JPanel implements MouseListener {
		private Furniture furniture;
		private JLabel lblName;
		private JComponent miniature;
		private Dimension miniatureDimension;
		public FurnitureMiniature(Furniture furniture) {
			this.furniture = furniture;	
			
			addMouseListener(this);
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			lblName = new JLabel(furniture.getName());
			add(lblName);
			
			//gestion de l'image miniaturisée
			int reducedWidth = furniture.getDimension().width/3;
			int reducedHeight = furniture.getDimension().height/3;
			miniatureDimension = new Dimension(reducedWidth < 60 ? 60 : reducedWidth, reducedHeight < 100 ? 100 : reducedHeight);
			
			miniature = new JComponent() {
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(FurnitureMiniature.this.furniture.getLoadedPicture(), 0, 0, miniatureDimension.width, miniatureDimension.height, FurnitureMiniature.this.furniture.getColor(), null);
				}
			};
			miniature.setPreferredSize(miniatureDimension);
			add(miniature);
		}
		
		public Dimension getPrefferedSize() {
			return new Dimension(300, 300);
		}
		
		/**
		 * Sur le clic de souris, on sélectionne le meuble pour pouvoir le placer dans le plan.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			pnlDrawingBoard.setSelectedModelFurniture(furniture);
		}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
	}
		
}
