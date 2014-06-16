package drawableObjects;

import java.awt.Color;
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

/**
 * Représente une librairie de meubles.
 */
public class FurnitureLibrary implements Serializable {

	private LinkedList<Furniture> libraryContent = new LinkedList<Furniture>();
	private String xmlFilePath;
	private String name;

	//noeud représentant la librairie dans l'arborescence du plan
	private DefaultMutableTreeNode jTreeNode;
	
	/**
	 * Crée une nouvelle librairie de meuble.
	 * @param xmlFilePath : chemin d'accès du fichier XML du contenu de la librairie
	 * @param name : nom de la librairie
	 */
	public FurnitureLibrary(String xmlFilePath, String name) {
		this.xmlFilePath = xmlFilePath;
		this.name = name;
	}
	
	/**
	 * Chargement du contenu de la librairie en lisant son fichier XML.
	 */
	public void loadLibraryContent() {
		libraryContent.clear();
		try {
				/*
				 * Ouverture du fichier XML
				 */
				File fXmlFile = new File(xmlFilePath);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
			 
				doc.getDocumentElement().normalize();
			 
				/*
				 * Lecture des noeuds "furniture"
				 */
				NodeList nList = doc.getElementsByTagName("furniture");
			 
				for (int i = 0; i < nList.getLength(); i++) {
			 
					Node nNode = nList.item(i);
			 
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			 
						Element eElement = (Element) nNode;
						
						boolean placedOnWall = eElement.getElementsByTagName("isMural").item(0).getTextContent().equals("0") ? false : true;
						
						libraryContent.add(new Furniture(eElement.getElementsByTagName("name").item(0).getTextContent(),
								eElement.getElementsByTagName("description").item(0).getTextContent(),
								eElement.getElementsByTagName("picture").item(0).getTextContent(),
								new Dimension(Integer.valueOf(eElement.getElementsByTagName("width").item(0).getTextContent()), Integer.valueOf(eElement.getElementsByTagName("height").item(0).getTextContent())),
								new Point(0, 0),
								Double.valueOf(eElement.getElementsByTagName("orientation").item(0).getTextContent()),
								false,
								true,
								this,
								new Color(Integer.valueOf(eElement.getElementsByTagName("red").item(0).getTextContent()), Integer.valueOf(eElement.getElementsByTagName("green").item(0).getTextContent()), Integer.valueOf(eElement.getElementsByTagName("blue").item(0).getTextContent())),
								placedOnWall));
						
					}
				}
	    } catch (Exception e) {
			e.printStackTrace();
	    }
	}

	public String getName () {
		return name;
	}
	public LinkedList<Furniture> getFurnitures() {
		return libraryContent;
	}
	public DefaultMutableTreeNode getJTreeNode() {
		return jTreeNode;
	}
	public void setJtreeNode(DefaultMutableTreeNode jTreeNode) {
		this.jTreeNode = jTreeNode;		
	}
}
