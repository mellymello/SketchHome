package features;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ExportContent 
{
	private JPanel content;
	
	public ExportContent (JPanel p)
	{
		content = p;
	}
	
	
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

}
