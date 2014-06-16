package fileFeatures;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Outil pour exporter un JPanel sous forme d'image png
 */
public class ContentExporter 
{
	private JPanel content;

	/**
	 * Crée un nouvel exportateur.
	 * 
	 * @param p : JPanel à gérer
	 */
	public ContentExporter (JPanel p)
	{
		content = p;
	}
	
	/**
	 * Enregistre le JPanel de l'exportateur sous forme d'image png dans le fichier donné.
	 * 
	 * @param selectedFile : fichier destination d'exportation
	 */
	public void createPng(File selectedFile) 
	{
		BufferedImage img = new BufferedImage(content.getWidth(), content.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = img.createGraphics();
		content.paint(g2);
		try{
			ImageIO.write(img, "png", selectedFile);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public int getImageWidth(){
		return content.getWidth();
	}
	public int getImageHeigtH(){
		return content.getHeight();
	}

}
