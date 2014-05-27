package features;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import drawableObject.DrawingBoardContent;
import drawableObject.Furniture;
import drawableObject.Wall;

public class RestoreContent 
{
	File file;
	DrawingBoardContent content;
	FileInputStream fis;
	ObjectInputStream in;
	
	LinkedList<Wall> listWall = new LinkedList<Wall>();
	
	public RestoreContent (DrawingBoardContent dbc)
	{
		content = dbc;
	}
	
	public void restore (File f)
	{
		openFile(f);
		Object o;
		
		//nettoyage des vieux objets
		content.clearContent();
		
		try {
			while((o=in.readObject()) != null)
			{
				if(o instanceof Furniture)
				{
					content.addFurniture(((Furniture)o));
				}
				else if (o instanceof Wall)
				{
					content.addWall(((Wall)o));
				}

			}

		
		} catch (IOException e) {
			closeFile();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeFile();
		}
	}
		
	public void openFile(File file)
	{
		this.file = file;
		try {
			fis = new FileInputStream(file);
			in = new ObjectInputStream(fis);
			
		}catch(FileNotFoundException fNFE)
		{
			fNFE.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void closeFile()
	{
		try {
			if(fis != null)
			{
				fis.close();
			}
			if(in != null)
			{
				in.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
