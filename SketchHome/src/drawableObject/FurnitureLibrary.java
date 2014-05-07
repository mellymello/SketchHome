package drawableObject;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FurnitureLibrary {
	private LinkedList<Furniture> libraryContent = new LinkedList<Furniture>();
	private String xmlFilePath;
	private String name; 
	
	public FurnitureLibrary(String xmlFilePath, String name) {
		this.xmlFilePath = xmlFilePath;
		loadLibraryContent();
		this.name = name;
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
			 
				for (int temp = 0; temp < nList.getLength(); temp++) {
			 
					Node nNode = nList.item(temp);
			 
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			 
						Element eElement = (Element) nNode;
						
						libraryContent.add(new Furniture(eElement.getElementsByTagName("name").item(0).getTextContent(),
								eElement.getElementsByTagName("description").item(0).getTextContent(),
								eElement.getElementsByTagName("picture").item(0).getTextContent(),
								new Dimension(Integer.valueOf(eElement.getElementsByTagName("width").item(0).getTextContent()), Integer.valueOf(eElement.getElementsByTagName("height").item(0).getTextContent())),
								new Point(0, 0),
								Double.valueOf(eElement.getElementsByTagName("orientation").item(0).getTextContent()),
								false,
								true));
						
					}
				}
		    } catch (Exception e) {
			e.printStackTrace();
	    }
	}

	public LinkedList<Furniture> getFurnitures() {
		return libraryContent;
	}
	public String getName () {
		return name;
	}
}
