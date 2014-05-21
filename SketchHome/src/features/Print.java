package features;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.JPanel;

public class Print implements Printable{

	private JPanel panelToPrint = null;
	
	public Print (JPanel p)
	{
		panelToPrint = p;
	}
	
	
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		if (pageIndex > 0) { 
			return NO_SUCH_PAGE;
		}
  		int W = (int) pageFormat.getImageableWidth(), H = (int) pageFormat.getImageableHeight();

		BufferedImage img = new BufferedImage(panelToPrint.getWidth(), panelToPrint.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = img.createGraphics();
		g2.scale(1, 1);
		panelToPrint.paint(g2);
		
		graphics.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
		graphics.drawRect(1, 1, W-2, H-2);

		/* tell the caller that this page is part of the printed document */
		return PAGE_EXISTS;
	}

}
