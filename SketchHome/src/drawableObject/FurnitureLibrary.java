package drawableObject;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FurnitureLibrary implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6647904402578830435L;
	private LinkedList<Furniture> libraryContent = new LinkedList<Furniture>();
	private String xmlFilePath;
	private String name;

	private DefaultMutableTreeNode jTreeNode;
	
	public FurnitureLibrary(String xmlFilePath, String name, DefaultMutableTreeNode jTreeNode) {
		this.xmlFilePath = xmlFilePath;
		this.name = name;
		this.jTreeNode = jTreeNode;
	}
	
	//TODO : source : http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
	/**
	 * Change the currently used furniture library. Loads a xml File.
	 * @param libraryFilePath
	 */
	public void loadLibraryContent() {
		libraryContent.clear();
		try {
				File fXmlFile = new File(xmlFilePath);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
			 
				//optional, but recommended
				//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
				doc.getDocumentElement().normalize();
			 
				NodeList nList = doc.getElementsByTagName("furniture");
			 
				for (int i = 0; i < nList.getLength(); i++) {
			 
					Node nNode = nList.item(i);
			 
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			 
						Element eElement = (Element) nNode;
						
						libraryContent.add(new Furniture(eElement.getElementsByTagName("name").item(0).getTextContent(),
								eElement.getElementsByTagName("description").item(0).getTextContent(),
								eElement.getElementsByTagName("picture").item(0).getTextContent(),
								new Dimension(Integer.valueOf(eElement.getElementsByTagName("width").item(0).getTextContent()), Integer.valueOf(eElement.getElementsByTagName("height").item(0).getTextContent())),
								new Point(0, 0),
								Double.valueOf(eElement.getElementsByTagName("orientation").item(0).getTextContent()),
								false,
								true,
								this,
								new Color(Float.valueOf(eElement.getElementsByTagName("red").item(0).getTextContent()), Float.valueOf(eElement.getElementsByTagName("green").item(0).getTextContent()), Float.valueOf(eElement.getElementsByTagName("blue").item(0).getTextContent()))));
						
					}
				}
		    } catch (Exception e) {
			e.printStackTrace();
	    }
	}

	public LinkedList<Furniture> getFurnitures() {
		return libraryContent;
	}
	public DefaultMutableTreeNode getJTreeNode() {
		return jTreeNode;
	}
	public String getName () {
		return name;
	}
}
