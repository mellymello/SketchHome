package fileFeatures;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

import drawableObjects.DrawingBoardContent;
import drawableObjects.Furniture;
import drawableObjects.Wall;

public class ContentRestorer 
{
	File file;
	DrawingBoardContent content;
	FileInputStream fis;
	ObjectInputStream in;
	
	LinkedList<Wall> listWall = new LinkedList<Wall>();
	
	public ContentRestorer (DrawingBoardContent dbc)
	{
		content = dbc;
	}
	
	public void restore (File f)
	{
		openFile(f);
		Object o;
		
		//suppression des objets actuels
		content.clearContent();
		
		//importation des objets du fichier
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
			e.printStackTrace();
		}
	}
}
