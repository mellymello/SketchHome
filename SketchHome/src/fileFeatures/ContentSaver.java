package fileFeatures;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import drawableObjects.DrawingBoardContent;
import drawableObjects.Furniture;
import drawableObjects.Wall;


public class ContentSaver
{
	private File file;
	private FileOutputStream fos = null;
	private ObjectOutputStream out;
	
	private DrawingBoardContent content;
	LinkedList<Furniture> listFur;
	LinkedList<Wall> listWall;
	
	
	public ContentSaver(DrawingBoardContent dbc)
	{
		content = dbc;
	}
	
	public void saveAs (File f)
	{
		file = f;
		
		openFile(file);
		
		try {
			save();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public void save () throws FileNotFoundException
	{
		if(file == null){
			throw new FileNotFoundException();
		}
		
		openFile(file);
		
		listFur = content.getFurnitures();
		for (Furniture f :listFur )
		{
			try {
				out.writeObject(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		listWall = content.getWalls();
		for (Wall w :listWall )
		{
			try {
				out.writeObject(w);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		closeFile();
	}
	public void openFile(File file)
	{
		this.file = file;
		try {
			fos = new FileOutputStream(file);
			out = new ObjectOutputStream(fos);
		}catch(FileNotFoundException fNFE) {
			fNFE.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void closeFile()
	{
		try {
			if(fos != null)
			{
				fos.close();
			}
			if(out != null)
			{
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	public void setDrawingBoardContent (DrawingBoardContent dbc)
//	{
//		content = dbc;
//	}
	
	public void setOpenedFile(File f){
		this.file=f;
	}
}
