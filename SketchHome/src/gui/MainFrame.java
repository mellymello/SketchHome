package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import drawableObjects.Furniture;
import drawableObjects.FurnitureLibrary;
import fileFeatures.ContentExporter;
import fileFeatures.ContentRestorer;
import fileFeatures.ContentSaver;
import fileFeatures.Printer;
import java.awt.Component;
import javax.swing.SwingConstants;

/**
 * Interface graphique de l'application SketchHome
 */
public class MainFrame extends JFrame implements  DrawingBoardContentObserver {

	private static final int WINDOW_HEIGHT = 600;
	private static final int WINDOW_WIDTH = 800;
	private static final int CTRL_POINT_DIAMETER = 10;
	private static final int WALL_THICKNESS = 5;

	// private DefaultMutableTreeNode bedRoomJtreeNode;
	// private DefaultMutableTreeNode livingRoomJtreeNode;
	// private DefaultMutableTreeNode kitchenJtreeNode;
	// private DefaultMutableTreeNode diningRoomJtreeNode;
	// private DefaultMutableTreeNode bathroomLibraryJtreeNode;
	// private DefaultMutableTreeNode officeJtreeNode;
	// private DefaultMutableTreeNode windowJtreeNode;
	// private DefaultMutableTreeNode doorJtreeNode;

	// private FurnitureLibrary bedRoomLibrary;
	// private FurnitureLibrary livingRoomLibrary;
	// private FurnitureLibrary kitchenLibrary;
	// private FurnitureLibrary diningRoomLibrary;
	// private FurnitureLibrary bathroomLibrary;
	// private FurnitureLibrary officeLibrary;
	// private FurnitureLibrary windowLibrary;
	// private FurnitureLibrary doorLibrary;

	private FurnitureLibrary bedRoomLibrary = new FurnitureLibrary(
			"library/bedroom.xml", "Bedroom");
	private FurnitureLibrary livingRoomLibrary = new FurnitureLibrary(
			"library/livingroom.xml", "Living room");
	private FurnitureLibrary kitchenLibrary = new FurnitureLibrary(
			"library/kitchen.xml", " Kitchen");
	private FurnitureLibrary diningRoomLibrary = new FurnitureLibrary(
			"library/diningroom.xml", "Dining room");
	private FurnitureLibrary bathroomLibrary = new FurnitureLibrary(
			"library/bathroom.xml", "Bathroom");
	private FurnitureLibrary officeLibrary = new FurnitureLibrary(
			"library/office.xml", "Office");
	private FurnitureLibrary windowLibrary = new FurnitureLibrary(
			"library/window.xml", "Window");
	private FurnitureLibrary doorLibrary = new FurnitureLibrary(
			"library/door.xml", "Door");
	private FurnitureLibrary customLibrary = new FurnitureLibrary(
			"library/custom.xml", "Custom furnitures");

	private FurnitureLibrary[] furnitureLibraries = { bedRoomLibrary,
			livingRoomLibrary, kitchenLibrary, diningRoomLibrary,
			bathroomLibrary, officeLibrary, windowLibrary, doorLibrary, customLibrary };

	private FileNameExtensionFilter extensionFilterSkt = new FileNameExtensionFilter("Sketch Home File", "skt");
	private FileNameExtensionFilter extensionFilterPng = new FileNameExtensionFilter("Portable Network Graphics", "png");;
	private ContentSaver contentSaver;
	private ContentRestorer contentRestorer;
	private ContentExporter contentExport;
	
	private ModificationListener modificationListener = new ModificationListener();
	private DrawingBoard pnlDrawingBoard;
	
	private JPanel pnlTools;
	
	private JLabel lblSelectedobjectlibrary;
	private JPanel pnlFurnitureLibrary;
	
	private JPanel pnlObjectTree;
	
	private JPanel pnlFurnitureProperties;
	private JTextField txtName;
	private JFormattedTextField txtWidth;
	private JFormattedTextField txtHeight;
	private JFormattedTextField txtRotation;
	private JTextField txtDescription;
	private JLabel lblColor;
	private JCheckBox checkBoxLocked;

	public MainFrame() {

		// bedRoomLibrary = new FurnitureLibrary("library/bedroom.xml",
		// "Bedroom");
		// livingRoomLibrary = new FurnitureLibrary("library/livingroom.xml",
		// "Living room");
		// kitchenLibrary = new
		// FurnitureLibrary("library/kitchen.xml"," Kitchen");
		// diningRoomLibrary = new FurnitureLibrary("library/diningroom.xml",
		// "Dining room");
		// bathroomLibrary = new FurnitureLibrary("library/bathroom.xml",
		// "Bathroom");
		// officeLibrary = new FurnitureLibrary("library/office.xml", "Office");
		// windowLibrary = new FurnitureLibrary("library/window.xml","Window");
		// doorLibrary = new FurnitureLibrary("library/door.xml","Door");

		setTitle("SketchHome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1124, 679);
		setVisible(true);

		/*
		 * Définition des barres de menu
		 */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		/*
		 * Menu d'opération sur les fichiers
		 */
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		//controle pour la création d'un nouveau plan
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pnlDrawingBoard.getDrawingBoardContent().clearContent();
				contentSaver.setOpenedFile(null);
				repaint();
			}
		});
		mnFile.add(mntmNew);

		//controle pour l'ouverture d'un fichier sauvegardé
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();

				fc.addChoosableFileFilter(extensionFilterSkt);
				fc.setFileFilter(extensionFilterSkt);

				int action = fc.showOpenDialog(null);
				fc.setMultiSelectionEnabled(false);
				if (action == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					contentRestorer.restore(f);
					contentSaver.setOpenedFile(f);
					setTitle("SketchHome - " + f.getName());
					repaint();
				}
			}
		});

		mnFile.add(mntmOpen);

		//controle pour la sauvegarde du plan en cours dans un fichier
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				contentSaver.setDrawingBoardContent(pnlDrawingBoard
//						.getDrawingBoardContent());

				try {
					contentSaver.save();
				} catch (FileNotFoundException e1) {
					JFileChooser fc = new JFileChooser();

					fc.addChoosableFileFilter(extensionFilterSkt);
					fc.setFileFilter(extensionFilterSkt);
					int action = fc.showSaveDialog(null);
					fc.setMultiSelectionEnabled(false);
					if (action == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fc.getSelectedFile();
						String filePath = selectedFile.getAbsolutePath();
						//le fichier selectionné est bien un fichier .skt
						if(filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length()).equals(extensionFilterSkt.getExtensions()[0])){
							contentSaver.saveAs(selectedFile);
							setTitle("SketchHome - " + selectedFile.getName());
						}
						else {
							File f = new File(filePath.concat(".").concat(extensionFilterSkt.getExtensions()[0]));
							contentSaver.saveAs(f);
							setTitle("SketchHome - " + f.getName());
						}
					}
				}

			}
		});
		mnFile.add(mntmSave);

		//controle pour la sauvegarde du plan en cours dans un fichier différent
		JMenuItem mntmSaveAs = new JMenuItem("Save as");
		mntmSaveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				contentSaver.setDrawingBoardContent(pnlDrawingBoard
//						.getDrawingBoardContent());
				JFileChooser fc = new JFileChooser();

				fc.addChoosableFileFilter(extensionFilterSkt);
				fc.setFileFilter(extensionFilterSkt);
				int action = fc.showSaveDialog(null);
				fc.setMultiSelectionEnabled(false);
				if (action == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fc.getSelectedFile();
					String filePath = selectedFile.getAbsolutePath();
					//le fichier selectionné est bien un fichier .skt
					if(filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length()).equals(extensionFilterSkt.getExtensions()[0])){
						contentSaver.saveAs(selectedFile);
						setTitle("SketchHome - " + selectedFile.getName());
					}
					else{
						File f = new File(filePath.concat(".").concat(extensionFilterSkt.getExtensions()[0]));
						contentSaver.saveAs(f);
						setTitle("SketchHome - " + f.getName());
					}
				}

			}
		});
		mnFile.add(mntmSaveAs);

		//controle pour l'exportation du plan dans un fichier image
		JMenuItem mntmExport = new JMenuItem("Export");
		mntmExport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser fc = new JFileChooser();

				fc.addChoosableFileFilter(extensionFilterPng);
				fc.setFileFilter(extensionFilterPng);
				int action = fc.showSaveDialog(null);
				fc.setMultiSelectionEnabled(false);
				if (action == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fc.getSelectedFile();
					String filePath = selectedFile.getAbsolutePath();
					//le fichier selectionné est bien un fichier .png
					if(filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length()).equals(extensionFilterPng.getExtensions()[0])){
						contentExport.createPng(selectedFile);
					}
					else{
						File f = new File(filePath.concat(".").concat(extensionFilterPng.getExtensions()[0]));
						contentExport.createPng(f);
					}
				}
				
			}
		});
		mnFile.add(mntmExport);
		

		//controle pour l'impression du plan
		JMenuItem mntmPrint = new JMenuItem("Print");
		mnFile.add(mntmPrint);
		mntmPrint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PrinterJob printJob = PrinterJob.getPrinterJob();
				printJob.setPrintable(new Printer(pnlDrawingBoard));
				boolean ok = printJob.printDialog();
				if (ok) {
					try {
					    printJob.print();
					} catch (PrinterException e1) {             
					    e1.printStackTrace();
					}
				}
				
			}
		});

		/*
		 * Menu d'opération sur l'objet actuellement sélectionné 
		 */
		JMenu mnObject = new JMenu("Object");
		menuBar.add(mnObject);

		//controle pour la création d'un objet personnalisé
		JMenuItem mntmCreateNew = new JMenuItem("Create new");
		mnObject.add(mntmCreateNew);
		mntmCreateNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new FurnitureCreationFrame();				
			}
		});
		mnObject.add(mntmCreateNew);

		//controle pour cacher un objet
		JMenuItem mntmHide = new JMenuItem("Hide");
		mnObject.add(mntmHide);

		//controle pour dupliquer un objet
		JMenuItem mntmCopy = new JMenuItem("Copy");
		mnObject.add(mntmCopy);

		//controle pour supprimer un objet
		JMenuItem mntmDelete = new JMenuItem("Delete");
		mnObject.add(mntmDelete);

		/*
		 * Menu d'opération sur l'interface du programme
		 */
		JMenu mnDisplay = new JMenu("Display");
		menuBar.add(mnDisplay);

		//controle pour afficher les mensurations des objets du plan
		JMenuItem mntmShowMeasurements = new JMenuItem("Measurements");
		mntmShowMeasurements.setIcon(new ImageIcon(MainFrame.class.getResource("/gui/img/cotations.png")));
		mntmShowMeasurements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnlDrawingBoard.toggleShowMeasurements();
				pnlDrawingBoard.repaint();
			}
		});
		mnDisplay.add(mntmShowMeasurements);

		//controle pour afficher la liste d'objet utilisés dans le plan
		JMenuItem mntmShowObjectTree = new JMenuItem("Object tree");
		mntmShowObjectTree.addActionListener(new ActionListener() {
			boolean objectTreeVisible = true;
			public void actionPerformed(ActionEvent e) {
				if(objectTreeVisible) {
					pnlTools.remove(pnlObjectTree);
				} else {
					pnlTools.add(pnlObjectTree);
				}
				objectTreeVisible = !objectTreeVisible;
				pack();
				repaint();
			}
		});
		mnDisplay.add(mntmShowObjectTree);

		
		//controle pour afficher les propriétés de l'objet sélectionné
		JMenuItem mntmShowProperties = new JMenuItem("Properties");
		mntmShowProperties.addActionListener(new ActionListener() {
			boolean propertiesVisible = true;
			public void actionPerformed(ActionEvent e) {
				if(propertiesVisible) {
					pnlTools.remove(pnlFurnitureProperties);
				} else {
					pnlTools.add(pnlFurnitureProperties);
				}
				propertiesVisible = !propertiesVisible;
				pack();
				repaint();
			}
		});
		mnDisplay.add(mntmShowProperties);

		/*
		 * Menu d'aide
		 */
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		//controle pour afficher des informations
		JMenuItem mntmAboutSketchhome = new JMenuItem("About SketchHome");
		mntmAboutSketchhome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this,"SketchHome\nAuthors : Hug Auriana, Jollien Dominique, Melly Calixte, Righitto Simone, Saam Frédéric\nVersion : 1.0");
			}
		});
		mnHelp.add(mntmAboutSketchhome);
		getContentPane().setLayout(new BorderLayout(0, 0));

		
		//affichage du plan
		pnlDrawingBoard = new DrawingBoard(WINDOW_WIDTH, WINDOW_HEIGHT,
				CTRL_POINT_DIAMETER, WALL_THICKNESS);
		pnlDrawingBoard.setBackground(Color.WHITE);
		getContentPane().add(pnlDrawingBoard, BorderLayout.CENTER);

		/*
		 * outils pour sauvegarder/ouvrir/exporter des fichiers représentant le plan
		 */
		contentSaver = new ContentSaver(pnlDrawingBoard.getDrawingBoardContent());
		contentRestorer = new ContentRestorer(pnlDrawingBoard.getDrawingBoardContent());
		contentExport = new ContentExporter(pnlDrawingBoard);
		pnlDrawingBoard.setLayout(null);
				
		/*
		 * Controles pour utiliser les outils
		 */
		pnlTools = new JPanel();
		pnlTools.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(pnlTools, BorderLayout.WEST);

		/*
		 * Boutons pour utiliser les outils
		 */
		pnlTools.setLayout(new BoxLayout(pnlTools, BoxLayout.Y_AXIS));
		JPanel pnlToolsBtn = new JPanel();
		pnlToolsBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
		pnlTools.add(pnlToolsBtn);
		GridBagLayout gbl_pnlToolsBtn = new GridBagLayout();
		gbl_pnlToolsBtn.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_pnlToolsBtn.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_pnlToolsBtn.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0 };
		gbl_pnlToolsBtn.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		pnlToolsBtn.setLayout(gbl_pnlToolsBtn);

		//TODO : supprimer ?
		JButton btnSelectionTool = new JButton("");
		btnSelectionTool.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/sourisB.png")));
		btnSelectionTool.setToolTipText("Select");
		btnSelectionTool.setBorderPainted(false);
		btnSelectionTool.setMargin(new Insets(0, 0, 0, 0));
		btnSelectionTool.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/souris.png")));
		GridBagConstraints gbc_btnSelectionTool = new GridBagConstraints();
		gbc_btnSelectionTool.fill = GridBagConstraints.VERTICAL;
		gbc_btnSelectionTool.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelectionTool.gridx = 0;
		gbc_btnSelectionTool.gridy = 0;
		pnlToolsBtn.add(btnSelectionTool, gbc_btnSelectionTool);

		//Outils pour la création d'objets personnalisés
		JButton btnFurniturecreation = new JButton("");
		btnFurniturecreation.setToolTipText("Furniture creation");
		btnFurniturecreation.setBorderPainted(false);
		btnFurniturecreation.setMargin(new Insets(0, 0, 0, 0));
		btnFurniturecreation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FurnitureCreationFrame();
			}
		});
		btnFurniturecreation.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/obje_persoB.png")));
		btnFurniturecreation.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/obje_perso.png")));
		GridBagConstraints gbc_btnFurniturecreation = new GridBagConstraints();
		gbc_btnFurniturecreation.fill = GridBagConstraints.VERTICAL;
		gbc_btnFurniturecreation.insets = new Insets(0, 0, 5, 5);
		gbc_btnFurniturecreation.gridx = 1;
		gbc_btnFurniturecreation.gridy = 0;
		pnlToolsBtn.add(btnFurniturecreation, gbc_btnFurniturecreation);

		//outils pour le placement de texte
		JButton btnTextTool = new JButton("");
		btnTextTool.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/texteB.png")));
		btnTextTool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// selectedTool = TextTool.getInstance();
			}
		});
		btnTextTool.setToolTipText("Text");
		btnTextTool.setBorderPainted(false);
		btnTextTool.setMargin(new Insets(0, 0, 0, 0));
		btnTextTool.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/texte.png")));
		GridBagConstraints gbc_btnTextTool = new GridBagConstraints();
		gbc_btnTextTool.fill = GridBagConstraints.VERTICAL;
		gbc_btnTextTool.insets = new Insets(0, 0, 5, 5);
		gbc_btnTextTool.gridx = 2;
		gbc_btnTextTool.gridy = 0;
		pnlToolsBtn.add(btnTextTool, gbc_btnTextTool);

		//outils pour le placement d'images
		//TODO : déplacer dans creationFurnitureFrame ?
		JButton btnImageTool = new JButton("");
		btnImageTool.setToolTipText("Image");
		btnImageTool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnImageTool.setBorderPainted(false);
		btnImageTool.setMargin(new Insets(0, 0, 0, 0));
		btnImageTool.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/outil_imageB.png")));
		btnImageTool.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/outil_image.png")));
		GridBagConstraints gbc_btnImageTool = new GridBagConstraints();
		gbc_btnImageTool.insets = new Insets(0, 0, 5, 5);
		gbc_btnImageTool.gridx = 3;
		gbc_btnImageTool.gridy = 0;
		pnlToolsBtn.add(btnImageTool, gbc_btnImageTool);

		//outils pour zomme avant dans l'affichage du plan 
		//TODO : supprimer ?
		JButton btnZoomplus = new JButton("ZoomPlus");
		GridBagConstraints gbc_btnZoomplus = new GridBagConstraints();
		gbc_btnZoomplus.fill = GridBagConstraints.VERTICAL;
		gbc_btnZoomplus.insets = new Insets(0, 0, 5, 5);
		gbc_btnZoomplus.gridx = 4;
		gbc_btnZoomplus.gridy = 0;
		pnlToolsBtn.add(btnZoomplus, gbc_btnZoomplus);

		//outils pour zomme arrière dans l'affichage du plan
		//TODO : supprimer ?
		JButton btnZoomminus = new JButton("ZoomMinus");
		GridBagConstraints gbc_btnZoomminus = new GridBagConstraints();
		gbc_btnZoomminus.fill = GridBagConstraints.VERTICAL;
		gbc_btnZoomminus.insets = new Insets(0, 0, 5, 0);
		gbc_btnZoomminus.gridx = 5;
		gbc_btnZoomminus.gridy = 0;
		pnlToolsBtn.add(btnZoomminus, gbc_btnZoomminus);

		//TODO : supprimer ?
		JButton btnConstructionMode = new JButton("");
		btnConstructionMode.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/constructionB.png")));
		btnConstructionMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConstructionMode.setToolTipText("Construction Mode");
		btnConstructionMode.setBorderPainted(false);
		btnConstructionMode.setMargin(new Insets(0, 0, 0, 0));
		btnConstructionMode.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/construction.png")));
		GridBagConstraints gbc_btnConstructionMode = new GridBagConstraints();
		gbc_btnConstructionMode.fill = GridBagConstraints.BOTH;
		gbc_btnConstructionMode.gridwidth = 3;
		gbc_btnConstructionMode.insets = new Insets(0, 0, 5, 5);
		gbc_btnConstructionMode.gridx = 0;
		gbc_btnConstructionMode.gridy = 1;
		pnlToolsBtn.add(btnConstructionMode, gbc_btnConstructionMode);

		//TODO : supprimer ?
		JButton btnFurnitureMode = new JButton("");
		btnFurnitureMode.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/ameublementB.png")));
		btnFurnitureMode.setToolTipText("Furniture Mode");
		btnFurnitureMode.setBorderPainted(false);
		btnFurnitureMode.setMargin(new Insets(0, 0, 0, 0));
		btnFurnitureMode.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/ameublement.png")));
		GridBagConstraints gbc_btnFurnitureMode = new GridBagConstraints();
		gbc_btnFurnitureMode.insets = new Insets(0, 0, 5, 0);
		gbc_btnFurnitureMode.fill = GridBagConstraints.BOTH;
		gbc_btnFurnitureMode.gridwidth = 3;
		gbc_btnFurnitureMode.gridx = 3;
		gbc_btnFurnitureMode.gridy = 1;
		pnlToolsBtn.add(btnFurnitureMode, gbc_btnFurnitureMode);

		//sélection de la librairie Bedroom
		JButton btnBedroom = new JButton("");
		btnBedroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectLibrary(bedRoomLibrary);
				pnlDrawingBoard.setSelectedTool(pnlDrawingBoard
						.getFurniturePlacementTool());
			}
		});
		btnBedroom.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/chambreB.png")));
		btnBedroom.setToolTipText("BedRoom");
		btnBedroom.setBorderPainted(false);
		btnBedroom.setMargin(new Insets(0, 0, 0, 0));
		btnBedroom.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/chambre.png")));
		GridBagConstraints gbc_btnBedroom = new GridBagConstraints();
		gbc_btnBedroom.fill = GridBagConstraints.VERTICAL;
		gbc_btnBedroom.insets = new Insets(0, 0, 5, 5);
		gbc_btnBedroom.gridx = 0;
		gbc_btnBedroom.gridy = 2;
		pnlToolsBtn.add(btnBedroom, gbc_btnBedroom);

		//sélection de la librairie Office
		JButton btnOffice = new JButton("");
		btnOffice.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/bureauB.png")));
		btnOffice.setToolTipText("Office");
		btnOffice.setBorderPainted(false);
		btnOffice.setMargin(new Insets(0, 0, 0, 0));
		btnOffice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectLibrary(officeLibrary);
				pnlDrawingBoard.setSelectedTool(pnlDrawingBoard
						.getFurniturePlacementTool());
			}
		});
		btnOffice.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/bureau.png")));
		GridBagConstraints gbc_btnOffice = new GridBagConstraints();
		gbc_btnOffice.fill = GridBagConstraints.VERTICAL;
		gbc_btnOffice.insets = new Insets(0, 0, 5, 5);
		gbc_btnOffice.gridx = 1;
		gbc_btnOffice.gridy = 2;
		pnlToolsBtn.add(btnOffice, gbc_btnOffice);

		//sélection de la librairie Kitchen
		JButton btnKitchen = new JButton("");
		btnKitchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectLibrary(kitchenLibrary);
				pnlDrawingBoard.setSelectedTool(pnlDrawingBoard
						.getFurniturePlacementTool());
			}
		});
		btnKitchen.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/cuisineB.png")));
		btnKitchen.setToolTipText("Kitchen");
		btnKitchen.setBorderPainted(false);
		btnKitchen.setMargin(new Insets(0, 0, 0, 0));
		btnKitchen.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/cuisine.png")));
		GridBagConstraints gbc_btnKitchen = new GridBagConstraints();
		gbc_btnKitchen.fill = GridBagConstraints.VERTICAL;
		gbc_btnKitchen.insets = new Insets(0, 0, 5, 5);
		gbc_btnKitchen.gridx = 2;
		gbc_btnKitchen.gridy = 2;
		pnlToolsBtn.add(btnKitchen, gbc_btnKitchen);

		//sélection de la librairie Living Room
		JButton btnLivingroom = new JButton("");
		btnLivingroom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectLibrary(livingRoomLibrary);
				pnlDrawingBoard.setSelectedTool(pnlDrawingBoard
						.getFurniturePlacementTool());
			}
		});
		btnLivingroom.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/salonB.png")));
		btnLivingroom.setToolTipText("Living Room");
		btnLivingroom.setBorderPainted(false);
		btnLivingroom.setMargin(new Insets(0, 0, 0, 0));
		btnLivingroom.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/salon.png")));
		GridBagConstraints gbc_btnLivingroom = new GridBagConstraints();
		gbc_btnLivingroom.fill = GridBagConstraints.VERTICAL;
		gbc_btnLivingroom.insets = new Insets(0, 0, 5, 5);
		gbc_btnLivingroom.gridx = 3;
		gbc_btnLivingroom.gridy = 2;
		pnlToolsBtn.add(btnLivingroom, gbc_btnLivingroom);

		//sélection de la librairie Bathroom
		JButton btnBathroom = new JButton("");
		btnBathroom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectLibrary(bathroomLibrary);
				pnlDrawingBoard.setSelectedTool(pnlDrawingBoard
						.getFurniturePlacementTool());
			}
		});
		btnBathroom.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/salle_de_bainB.png")));
		btnBathroom.setToolTipText("Bath Room");
		btnBathroom.setBorderPainted(false);
		btnBathroom.setMargin(new Insets(0, 0, 0, 0));
		btnBathroom.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/salle_de_bain.png")));
		GridBagConstraints gbc_btnBathroom = new GridBagConstraints();
		gbc_btnBathroom.fill = GridBagConstraints.BOTH;
		gbc_btnBathroom.insets = new Insets(0, 0, 5, 5);
		gbc_btnBathroom.gridx = 4;
		gbc_btnBathroom.gridy = 2;
		pnlToolsBtn.add(btnBathroom, gbc_btnBathroom);

		//sélection de la librairie Dinig room
		JButton btnDiningRoom = new JButton("");
		btnDiningRoom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectLibrary(diningRoomLibrary);
				pnlDrawingBoard.setSelectedTool(pnlDrawingBoard
						.getFurniturePlacementTool());
			}
		});
		btnDiningRoom.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/salle_a_mangerB.png")));
		btnDiningRoom.setToolTipText("Dining Room");
		btnDiningRoom.setBorderPainted(false);
		btnDiningRoom.setMargin(new Insets(0, 0, 0, 0));
		btnDiningRoom.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/salle_a_manger.png")));
		GridBagConstraints gbc_btnDiningRoom = new GridBagConstraints();
		gbc_btnDiningRoom.fill = GridBagConstraints.BOTH;
		gbc_btnDiningRoom.insets = new Insets(0, 0, 5, 0);
		gbc_btnDiningRoom.gridx = 5;
		gbc_btnDiningRoom.gridy = 2;
		pnlToolsBtn.add(btnDiningRoom, gbc_btnDiningRoom);

		//sélection de la librairie objets personnalisés
		JButton btnCustomfurniture = new JButton("");
		btnCustomfurniture.setToolTipText("Custom furnitures");
		btnCustomfurniture.setBorderPainted(false);
		btnCustomfurniture.setMargin(new Insets(0, 0, 0, 0));
		btnCustomfurniture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectLibrary(customLibrary);
				pnlDrawingBoard.setSelectedTool(pnlDrawingBoard
						.getFurniturePlacementTool());
			}
		});
		btnCustomfurniture.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/obje_persoB.png")));
		btnCustomfurniture.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/obje_perso.png")));
		GridBagConstraints gbc_btnCustomfurniture = new GridBagConstraints();
		gbc_btnCustomfurniture.fill = GridBagConstraints.VERTICAL;
		gbc_btnCustomfurniture.insets = new Insets(0, 0, 5, 5);
		gbc_btnCustomfurniture.gridx = 0;
		gbc_btnCustomfurniture.gridy = 3;
		pnlToolsBtn.add(btnCustomfurniture, gbc_btnCustomfurniture);

		//outils pour tracer des murs simples
		JButton btnWall = new JButton("");
		btnWall.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/mur_simpleB.png")));
		btnWall.setToolTipText("Simple Wall");
		btnWall.setBorderPainted(false);
		btnWall.setMargin(new Insets(0, 0, 0, 0));
		btnWall.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/mur_simple.png")));
		btnWall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pnlDrawingBoard.setSelectedTool(pnlDrawingBoard
						.getSimpleWallTool());
				showContentOfLibrary(null);
			}
		});
		GridBagConstraints gbc_btnWall = new GridBagConstraints();
		gbc_btnWall.fill = GridBagConstraints.VERTICAL;
		gbc_btnWall.insets = new Insets(0, 0, 0, 5);
		gbc_btnWall.gridx = 0;
		gbc_btnWall.gridy = 4;
		pnlToolsBtn.add(btnWall, gbc_btnWall);

		//pour tracer des murs continus
		JButton btnWall2 = new JButton("");
		btnWall2.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/mur_polygonalB.png")));
		btnWall2.setToolTipText("Contiguous Wall");
		btnWall2.setBorderPainted(false);
		btnWall2.setMargin(new Insets(0, 0, 0, 0));
		btnWall2.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/mur_polygonal.png")));
		btnWall2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pnlDrawingBoard.setSelectedTool(pnlDrawingBoard
						.getPolygonalWallTool());
				showContentOfLibrary(null);
			}
		});
		GridBagConstraints gbc_btnWall2 = new GridBagConstraints();
		gbc_btnWall2.fill = GridBagConstraints.VERTICAL;
		gbc_btnWall2.insets = new Insets(0, 0, 0, 5);
		gbc_btnWall2.gridx = 1;
		gbc_btnWall2.gridy = 4;
		pnlToolsBtn.add(btnWall2, gbc_btnWall2);

		//sélection de la librairie fenêtre
		JButton btnWindow = new JButton("");
		btnWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectLibrary(windowLibrary);
				pnlDrawingBoard.setSelectedTool(pnlDrawingBoard
						.getonWallPlacementTool());
			}
		});
		btnWindow.setToolTipText("Windows");
		btnWindow.setBorderPainted(false);
		btnWindow.setMargin(new Insets(0, 0, 0, 0));
		btnWindow.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/fenetreB.png")));
		btnWindow.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/fenetre.png")));
		GridBagConstraints gbc_btnWindow = new GridBagConstraints();
		gbc_btnWindow.fill = GridBagConstraints.VERTICAL;
		gbc_btnWindow.insets = new Insets(0, 0, 0, 5);
		gbc_btnWindow.gridx = 2;
		gbc_btnWindow.gridy = 4;
		pnlToolsBtn.add(btnWindow, gbc_btnWindow);

		//sélection de la librairie porte
		JButton btnDoor = new JButton("");
		btnDoor.setToolTipText("Doors");
		btnDoor.setBorderPainted(false);
		btnDoor.setMargin(new Insets(0, 0, 0, 0));
		btnDoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectLibrary(doorLibrary);
				pnlDrawingBoard.setSelectedTool(pnlDrawingBoard
						.getonWallPlacementTool());
			}
		});
		btnDoor.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/porteB.png")));
		btnDoor.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/porte.png")));
		GridBagConstraints gbc_btnDoor = new GridBagConstraints();
		gbc_btnDoor.fill = GridBagConstraints.VERTICAL;
		gbc_btnDoor.insets = new Insets(0, 0, 0, 5);
		gbc_btnDoor.gridx = 3;
		gbc_btnDoor.gridy = 4;
		pnlToolsBtn.add(btnDoor, gbc_btnDoor);

		//sélection de la librairie escalier
		JButton btnStair = new JButton("");
		btnStair.setBorderPainted(false);
		btnStair.setMargin(new Insets(0, 0, 0, 0));
		btnStair.setSelectedIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/escaliersB.png")));
		btnStair.setToolTipText("Stairs");
		btnStair.setBorderPainted(false);
		btnStair.setMargin(new Insets(0, 0, 0, 0));
		btnStair.setIcon(new ImageIcon(MainFrame.class
				.getResource("/gui/img/escaliers.png")));
		GridBagConstraints gbc_btnStair = new GridBagConstraints();
		gbc_btnStair.fill = GridBagConstraints.BOTH;
		gbc_btnStair.insets = new Insets(0, 0, 0, 5);
		gbc_btnStair.gridx = 4;
		gbc_btnStair.gridy = 4;
		pnlToolsBtn.add(btnStair, gbc_btnStair);

		/*
		 * Controles pour la sélection de meubles dans la librairie sélectionnée 
		 */
		JPanel pnlObjects = new JPanel();
		pnlObjects.setAlignmentX(Component.RIGHT_ALIGNMENT);
		pnlTools.add(pnlObjects);
		GridBagLayout gbl_pnlObjects = new GridBagLayout();
		gbl_pnlObjects.columnWidths = new int[] { 323, 0 };
		gbl_pnlObjects.rowHeights = new int[] { 22, 14, 107, 0 };
		gbl_pnlObjects.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_pnlObjects.rowWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		pnlObjects.setLayout(gbl_pnlObjects);

		lblSelectedobjectlibrary = new JLabel("Selected Object Library :");
		lblSelectedobjectlibrary.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblSelectedobjectlibrary = new GridBagConstraints();
		gbc_lblSelectedobjectlibrary.anchor = GridBagConstraints.NORTH;
		gbc_lblSelectedobjectlibrary.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSelectedobjectlibrary.insets = new Insets(0, 0, 5, 0);
		gbc_lblSelectedobjectlibrary.gridx = 0;
		gbc_lblSelectedobjectlibrary.gridy = 1;
		pnlObjects.add(lblSelectedobjectlibrary, gbc_lblSelectedobjectlibrary);
		
		//JPanel d'affichage de la librairie sélectionnée
		pnlFurnitureLibrary = new JPanel();
		GridLayout furnitureLibraryLayout = new GridLayout(0,3,0,0);
		pnlFurnitureLibrary.setLayout(furnitureLibraryLayout);
		
		JScrollPane scrollPaneFurnitureLibrary = new JScrollPane(pnlFurnitureLibrary);
		GridBagConstraints gbc_pnlObjectLibrary = new GridBagConstraints();
		gbc_pnlObjectLibrary.fill = GridBagConstraints.BOTH;
		gbc_pnlObjectLibrary.gridx = 0;
		gbc_pnlObjectLibrary.gridy = 2;
		pnlObjects.add(scrollPaneFurnitureLibrary, gbc_pnlObjectLibrary);

		/*
		 * Controles pour afficher les objets utilisés dans le plan
		 */
		pnlObjectTree = new JPanel();
		pnlObjectTree.setAlignmentX(1.0f);
		pnlTools.add(pnlObjectTree);
		pnlObjectTree.setLayout(new BorderLayout(0, 0));

		JLabel lblObjectTree = new JLabel("Used objects");
		lblObjectTree.setFont(new Font("Tahoma", Font.BOLD, 11));
		pnlObjectTree.add(lblObjectTree, BorderLayout.NORTH);

		//DynamicTree permettant d'afficher les objets utilisés dans le plan
		DynamicTree treePanel = new DynamicTree();
		pnlObjectTree.add(treePanel);
		for (FurnitureLibrary furnitureLibrary : furnitureLibraries) {
			furnitureLibrary.setJtreeNode(treePanel.addObject(null,
					furnitureLibrary.getName()));
		}
		pnlDrawingBoard.addContentObserver(treePanel);

		/*
		 * Controle permettant de modifier les propriétés du meuble actuellement sélectionné dans le plan
		 */
		pnlFurnitureProperties = new JPanel();
		pnlFurnitureProperties.setAlignmentX(Component.RIGHT_ALIGNMENT);
		pnlTools.add(pnlFurnitureProperties);
		GridBagLayout gbl_pnlDescription = new GridBagLayout();
		gbl_pnlDescription.columnWidths = new int[] { 27, 86 };
		gbl_pnlDescription.rowHeights = new int[] { 0, 0, 20, 0, 0, 0 };
		gbl_pnlDescription.columnWeights = new double[] { 1.0, 1.0 };
		gbl_pnlDescription.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0 };
		pnlFurnitureProperties.setLayout(gbl_pnlDescription);

		JLabel lblFurnitureProperties = new JLabel("Properties");
		lblFurnitureProperties.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblProperties = new GridBagConstraints();
		gbc_lblProperties.gridwidth = 2;
		gbc_lblProperties.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblProperties.insets = new Insets(0, 0, 5, 0);
		gbc_lblProperties.gridx = 0;
		gbc_lblProperties.gridy = 0;
		pnlFurnitureProperties.add(lblFurnitureProperties, gbc_lblProperties);

		JSeparator separator = new JSeparator();
		separator.setBackground(Color.BLACK);
		separator.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridwidth = 2;
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 1;
		pnlFurnitureProperties.add(separator, gbc_separator);

		/*
		 * Controles pour la modification du nom du meuble
		 */
		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 2;
		pnlFurnitureProperties.add(lblName, gbc_lblName);

		txtName = new JTextField();
		lblName.setLabelFor(txtName);
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.anchor = GridBagConstraints.NORTH;
		gbc_txtName.insets = new Insets(0, 0, 5, 0);
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 2;
		pnlFurnitureProperties.add(txtName, gbc_txtName);
		txtName.setColumns(10);
		
		/*
		 * Controles pour la modification de la description du meuble
		 */
		JLabel lblDescription = new JLabel("Description");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.anchor = GridBagConstraints.WEST;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 3;
		pnlFurnitureProperties.add(lblDescription, gbc_lblDescription);

		txtDescription = new JTextField();
		lblDescription.setLabelFor(txtDescription);
		GridBagConstraints gbc_txtDescription= new GridBagConstraints();
		gbc_txtDescription.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescription.anchor = GridBagConstraints.NORTH;
		gbc_txtDescription.insets = new Insets(0, 0, 5, 0);
		gbc_txtDescription.gridx = 1;
		gbc_txtDescription.gridy = 3;
		pnlFurnitureProperties.add(txtDescription, gbc_txtDescription);
		txtDescription.setColumns(10);

		/*
		 * Controles pour la modification de la largeur du meuble
		 */
		JLabel lblWidth = new JLabel("Width");
		GridBagConstraints gbc_lblWidth = new GridBagConstraints();
		gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblWidth.anchor = GridBagConstraints.WEST;
		gbc_lblWidth.gridx = 0;
		gbc_lblWidth.gridy = 4;
		pnlFurnitureProperties.add(lblWidth, gbc_lblWidth);

		txtWidth = new JFormattedTextField();
		lblWidth.setLabelFor(txtWidth);
		GridBagConstraints gbc_txtWidth = new GridBagConstraints();
		gbc_txtWidth.anchor = GridBagConstraints.NORTH;
		gbc_txtWidth.insets = new Insets(0, 0, 5, 0);
		gbc_txtWidth.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtWidth.gridx = 1;
		gbc_txtWidth.gridy = 4;
		pnlFurnitureProperties.add(txtWidth, gbc_txtWidth);
		txtWidth.setColumns(10);

		/*
		 * Controles pour la modification de la hauteur du meuble
		 */
		JLabel lblHeight = new JLabel("Height");
		GridBagConstraints gbc_lblHeight = new GridBagConstraints();
		gbc_lblHeight.anchor = GridBagConstraints.WEST;
		gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeight.gridx = 0;
		gbc_lblHeight.gridy = 5;
		pnlFurnitureProperties.add(lblHeight, gbc_lblHeight);

		txtHeight = new JFormattedTextField();
		lblHeight.setLabelFor(txtHeight);
		GridBagConstraints gbc_txtHeight = new GridBagConstraints();
		gbc_txtHeight.anchor = GridBagConstraints.NORTH;
		gbc_txtHeight.insets = new Insets(0, 0, 5, 0);
		gbc_txtHeight.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtHeight.gridx = 1;
		gbc_txtHeight.gridy = 5;
		pnlFurnitureProperties.add(txtHeight, gbc_txtHeight);
		txtHeight.setColumns(10);

		/*
		 * Controles pour la modification de l'angle de rotation du meuble
		 */
		JLabel lblRotation = new JLabel("Rotation angle");
		GridBagConstraints gbc_lblRotation = new GridBagConstraints();
		gbc_lblRotation.anchor = GridBagConstraints.WEST;
		gbc_lblRotation.insets = new Insets(0, 0, 0, 5);
		gbc_lblRotation.gridx = 0;
		gbc_lblRotation.gridy = 6;
		pnlFurnitureProperties.add(lblRotation, gbc_lblRotation);

		txtRotation = new JFormattedTextField();
		lblRotation.setLabelFor(txtRotation);
		GridBagConstraints gbc_txtRotation = new GridBagConstraints();
		gbc_txtRotation.anchor = GridBagConstraints.NORTH;
		gbc_txtRotation.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRotation.gridx = 1;
		gbc_txtRotation.gridy = 6;
		pnlFurnitureProperties.add(txtRotation, gbc_txtRotation);
		txtRotation.setColumns(10);
		
		/*
		 * Controles pour la modification de la couleur de fond du meuble
		 */
		lblColor = new JLabel("Background color");
		lblColor.setOpaque(true);
		GridBagConstraints gbc_lblColor = new GridBagConstraints();
		gbc_lblColor.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblColor.insets = new Insets(0, 0, 0, 5);
		gbc_lblColor.gridx = 0;
		gbc_lblColor.gridy = 7;
		pnlFurnitureProperties.add(lblColor, gbc_lblColor);

		JButton btnBackgroundColor = new JButton("BackgroundColor");
		btnBackgroundColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color bgColor = JColorChooser.showDialog(MainFrame.this, "Choisir une couleur de fond", lblColor.getBackground());
				if (bgColor != null){
					lblColor.setBackground(bgColor);
				}
			}
		});
		GridBagConstraints gbc_btnBackgroundColor = new GridBagConstraints();
		gbc_btnBackgroundColor.anchor = GridBagConstraints.NORTH;
		gbc_btnBackgroundColor.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBackgroundColor.insets = new Insets(0, 0, 0, 5);
		gbc_btnBackgroundColor.gridx = 1;
		gbc_btnBackgroundColor.gridy = 7;
		pnlFurnitureProperties.add(btnBackgroundColor, gbc_btnBackgroundColor);
		
		/*
		 * Controles pour le vérouillage du meuble
		 */
		checkBoxLocked = new JCheckBox("Locked");
		GridBagConstraints gbc_checkBoxLocked = new GridBagConstraints();
		gbc_checkBoxLocked.anchor = GridBagConstraints.NORTH;
		gbc_checkBoxLocked.insets = new Insets(0, 0, 0, 5);
		gbc_checkBoxLocked.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBoxLocked.gridx = 0;
		gbc_checkBoxLocked.gridy = 8;
		pnlFurnitureProperties.add(checkBoxLocked, gbc_checkBoxLocked);
		
		//bouton pour appliquer les modifications au meuble
		JButton btnModifyFurniture = new JButton("Modify");
		btnModifyFurniture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Furniture f = pnlDrawingBoard.getDrawingBoardContent().getSelectedFurniture();
				if(!f.getLocked() || !checkBoxLocked.isSelected()) {
					f.setName(txtName.getText());
					f.setDescription(txtDescription.getText());
					f.setDimension(new Dimension(Integer.valueOf(txtWidth.getText()), Integer.valueOf(txtHeight.getText())));
					f.setOrientation(Double.valueOf(txtRotation.getText()));
					f.setColor(lblColor.getBackground());
					f.setLocked(checkBoxLocked.isSelected());
					f.getJtreeNode().setUserObject(f.getName());
					
					repaint();
				}
			}
		});
		GridBagConstraints gbc_btnModifyFurniture = new GridBagConstraints();
		gbc_btnModifyFurniture.anchor = GridBagConstraints.NORTH;
		gbc_btnModifyFurniture.insets = new Insets(0, 0, 0, 5);
		gbc_btnModifyFurniture.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnModifyFurniture.gridx = 1;
		gbc_btnModifyFurniture.gridy = 8;
		pnlFurnitureProperties.add(btnModifyFurniture, gbc_btnModifyFurniture);
		
		pnlDrawingBoard.addContentObserver(this);

		pack();
	}

	/**
	 * Sélection d'une librairie de meuble et affichage de son contenu.
	 * 
	 * @param furnitureLibrary
	 *            : librairie de meuble à afficher
	 */
	public void selectLibrary(FurnitureLibrary furnitureLibrary) {
		lblSelectedobjectlibrary.setText("Selected Object Library : "
				+ furnitureLibrary.getName());
		furnitureLibrary.loadLibraryContent();
		showContentOfLibrary(furnitureLibrary);
	
		pnlDrawingBoard.setSelectedFurnitureLibrary(furnitureLibrary);
		pnlDrawingBoard.setSelectedModelFurniture(null);
	}

	/**
	 * Remplissage de l'interface graphique avec le contenu d'une librairie de
	 * meuble.
	 * 
	 * @param furnitureLibrary
	 *            : librairie de meuble à afficher
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
	 * Contient une miniature de l'image d'un meuble et son nom. Permet la
	 * sélection du meuble représenté pour l'utiliser dans le plan.
	 */
	class FurnitureMiniature extends JPanel implements MouseListener {
		//meuble représenté par la miniature
		private Furniture furniture;
		
		private JLabel lblName;
		private JComponent miniature;
		private Dimension miniatureDimension;

		public FurnitureMiniature(Furniture furniture) {
			this.furniture = furniture;

			addMouseListener(this);
			//affichage du nom et de la miniature l'un par dessus l'autre
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			//nom du meuble
			lblName = new JLabel(furniture.getName());
			add(lblName);

			//l'image miniature à un dimension minimale de 80x80
			int reducedWidth = furniture.getDimension().width > 80 ? 80 : furniture.getDimension().width;
			int reducedHeight = furniture.getDimension().height > 80 ? 80 : furniture.getDimension().height;
			miniatureDimension = new Dimension(reducedWidth, reducedHeight);

			//miniature de l'image du meuble
			miniature = new JComponent() {
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(FurnitureMiniature.this.furniture
							.getLoadedPicture(), 0, 0,
							miniatureDimension.width,
							miniatureDimension.height,
							FurnitureMiniature.this.furniture.getColor(), null);
				}
			};
			miniature.setPreferredSize(miniatureDimension);
			add(miniature);
		}

		public Dimension getPrefferedSize() {
			return new Dimension(300, 300);
		}

		/**
		 * Sur le clic de souris, on sélectionne le meuble pour pouvoir le
		 * placer dans le plan.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			pnlDrawingBoard.setSelectedModelFurniture(furniture);
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	@Override
	public Observer getAdditionObserver() {
		return null;
	}

	@Override
	public Observer getDeletionObserver() {
		return null;
	}

	@Override
	public Observer getModificationObserver() {
		return modificationListener;
	}
	
    private class ModificationListener implements Observer {

		@Override
		/**
		 * Remplissage des controles d'édition des propriétés de meuble.
		 * @param arg : le meuble dont il faut afficher les propriétés
		 */
		public void update(Observable o, Object arg) {
			Furniture f = ((Furniture)arg);
			lblColor.setBackground(f.getColor());
			txtDescription.setText(f.getDescription());
			txtRotation.setText(String.valueOf(f.getOrientation()));
			txtName.setText(f.getName());
			txtWidth.setText(String.valueOf(f.getDimension().width));
			txtHeight.setText(String.valueOf(f.getDimension().height));
			checkBoxLocked.setSelected(f.getLocked());
		}
    	
    }

}
