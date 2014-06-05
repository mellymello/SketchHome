package gui;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import drawableObjects.FurnitureLibrary;
import fileFeatures.ContentExporter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class FurnitureCreationSave extends JFrame {

	private FurnitureLibrary xmlLibrary;

	private JPanel buttonPanel;
	private JPanel infoPanelBase;

	private JLabel nameLabel;
	private JLabel descriptionLabel;
	private JLabel widthLabel;
	private JLabel heightLabel;
	private JLabel errorLabel;

	private JTextField name;
	private JTextField description;
	private JTextField width;
	private JTextField height;

	private JButton save;
	private JButton cancel;

	private FileNameExtensionFilter extensionFilterPng;
	private ContentExporter contentExport;
	
	public FurnitureCreationSave(ContentExporter contentExporter) {
		this.contentExport = contentExporter;
		this.extensionFilterPng = new FileNameExtensionFilter(
				"Portable Network Graphics", "png");

		setTitle("Save personal furniture");
		setPreferredSize(new Dimension(275,200));
		setMinimumSize(new Dimension(275,200));
		setLayout(new BorderLayout());

		makeInfo();
		makeButtons();

		add(infoPanelBase, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		setVisible(true);
		pack();
	}

	private void makeInfo() {
		infoPanelBase = new JPanel();
		infoPanelBase.setLayout(new BorderLayout());
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(4, 2));

		nameLabel = new JLabel("Name",JLabel.CENTER);
		descriptionLabel = new JLabel("Description",JLabel.CENTER);
		widthLabel = new JLabel("Width",JLabel.CENTER);
		heightLabel = new JLabel("Hieght",JLabel.CENTER);
		errorLabel= new JLabel(" ",JLabel.CENTER);
		errorLabel.setForeground(Color.RED);

		name = new JTextField();
		description = new JTextField();
		width = new JTextField();
		height = new JTextField();

		width.setText(Integer.toString(contentExport.getImageWidth()));
		height.setText(Integer.toString(contentExport.getImageHeigtH()));
		
		infoPanel.add(nameLabel);
		infoPanel.add(name);

		infoPanel.add(descriptionLabel);
		infoPanel.add(description);

		infoPanel.add(widthLabel);
		infoPanel.add(width);

		infoPanel.add(heightLabel);
		infoPanel.add(height);
		
		infoPanelBase.add(infoPanel,BorderLayout.CENTER);
		infoPanelBase.add(errorLabel,BorderLayout.SOUTH);
		

	}

	private void makeButtons() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		save = new JButton("Save");
		cancel = new JButton("Cancel");

		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (checkValues()) {
					errorLabel.setText(" ");
					dispose();
					JFileChooser fc = new JFileChooser();
					fc.setCurrentDirectory(new File("library/custom"));
					fc.addChoosableFileFilter(extensionFilterPng);
					fc.setFileFilter(extensionFilterPng);

					int action = fc.showSaveDialog(null);
					fc.setMultiSelectionEnabled(false);

					if (action == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fc.getSelectedFile();
						String filePath = selectedFile.getAbsolutePath();
						// le fichier selectionné est bien un fichier .png
						if (filePath.substring(filePath.lastIndexOf(".") + 1,
								filePath.length()).equals(
								extensionFilterPng.getExtensions()[0])) {

							contentExport.createPng(selectedFile);
							updateXLMLibrary(selectedFile.getPath());
						} else {
							File f = new File(filePath.concat(".").concat(
									extensionFilterPng.getExtensions()[0]));
							contentExport.createPng(f);
							updateXLMLibrary(f.getPath());
						}
					}

				}
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				
			}
		});
		
		buttonPanel.add(save);
		buttonPanel.add(cancel);
	}

	private boolean checkValues(){
		boolean isOk=true;
			if(name.getText().isEmpty()){
				isOk=false;
				errorLabel.setText("give a name");
			}
			else if(description.getText().isEmpty()){
				isOk=false;
				errorLabel.setText("give a description");
			}
			else if(width.getText().isEmpty()){
				isOk=false;
				errorLabel.setText("give a width");
			}
			else if(height.getText().isEmpty()){
				isOk=false;
				errorLabel.setText("give an height");
			}
			else{
				try{
					Integer.parseInt(width.getText());
					Integer.parseInt(height.getText());
				}catch(NumberFormatException nfe){
					isOk= false;
					errorLabel.setText("Dimension must be integer");
				}
			}
			
			
		return isOk;
	}
	
	private void updateXLMLibrary(String furniturePath) {
		try {

			File xmlFile = new File("library/custom.xml");

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// creating input stream
			Document doc = builder.parse(xmlFile);

			// Xpath compiler
			// XPathFactory xpf = XPathFactory.newInstance();
			// XPath xpath = xpf.newXPath();

			// XPath Query
			// XPathExpression expr = xpath.compile("/");
			// Node attributeElement = (Node) expr.evaluate(doc,
			// XPathConstants.NODE);

			Node root = doc.getFirstChild();

			Element name = doc.createElement("name");
			Element description = doc.createElement("description");
			Node dimension = doc.createElement("dimension");
			Element width = doc.createElement("width");
			Element height = doc.createElement("height");
			Element picture = doc.createElement("picture");
			Element orientation = doc.createElement("orientation");
			Node color = doc.createElement("color");
			Element red = doc.createElement("red");
			Element green = doc.createElement("green");
			Element blue = doc.createElement("blue");

			name.setTextContent(this.name.getText());
			description.setTextContent(this.description.getText());
			width.setTextContent(this.width.getText());
			height.setTextContent(this.height.getText());
			dimension.appendChild(width);
			dimension.appendChild(height);

			picture.setTextContent(furniturePath);
			orientation.setTextContent("0");
			red.setTextContent("255");
			green.setTextContent("255");
			blue.setTextContent("255");

			color.appendChild(red);
			color.appendChild(green);
			color.appendChild(blue);

			// New Node
			Node furnitureNode = doc.createElement("furniture");

			furnitureNode.appendChild(name);
			furnitureNode.appendChild(description);
			furnitureNode.appendChild(dimension);
			furnitureNode.appendChild(picture);
			furnitureNode.appendChild(orientation);
			furnitureNode.appendChild(color);

			root.appendChild(furnitureNode);

			// writing xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			// File outputFile = new File("src/input2.xml");

			StreamResult result = new StreamResult(xmlFile);
			// creating output stream
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
