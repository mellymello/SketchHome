package fileFeatures;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.JPanel;

/**
 * Outil pour imprimer le JPanel contenant le dessin du plan.
 */
public class Printer implements Printable {

	private JPanel panelToPrint = null;
	
	/**
	 * Crée un nouvel "imprimeur" de JPanel
	 * @param p : JPanel à imprimer
	 */
	public Printer (JPanel p)
	{
		panelToPrint = p;
	}
	
	
	@Override
	/**
	 * Impression d'un objet Graphics (méthode appelée automatiquement).
	 */
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		if (pageIndex > 0) { 
			return NO_SUCH_PAGE;
		}
  		int W = (int) pageFormat.getImageableWidth(), H = (int) pageFormat.getImageableHeight();

		BufferedImage img = new BufferedImage(panelToPrint.getWidth(), panelToPrint.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2 = img.createGraphics();
		
		panelToPrint.paint(g2);
		
		BufferedImage scaledImg = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = scaledImg.createGraphics();
		g.drawImage(img, 0, 0, W, H, null);
		g.dispose();
		
		graphics.drawImage(scaledImg, 0, 0, scaledImg.getWidth(), scaledImg.getHeight(), null);
//		graphics.drawRect(2, 2, W-2, H-2);

		//informe l'appelant que cette page fait partie du document imprimé.
		return PAGE_EXISTS;
	}

}
