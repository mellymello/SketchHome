package features;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import javax.swing.JFileChooser;

import drawableObject.DrawingBoardContent;
import drawableObject.Furniture;
import drawableObject.Wall;


public class SaveContent
{
	private File file;
	private FileOutputStream fos = null;
	private ObjectOutputStream out;
	
	private DrawingBoardContent content;
	LinkedList<Furniture> listFur = new LinkedList<Furniture>();
	LinkedList<Wall> listWall = new LinkedList<Wall>();
	
	
	public SaveContent(DrawingBoardContent dbc)
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
			// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		listWall = content.getWalls();
		for (Wall w :listWall )
		{
			try {
				out.writeObject(w);
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
			if(fos != null)
			{
				fos.close();
			}
			if(out != null)
			{
				out.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setDrawingBoardContent (DrawingBoardContent dbc)
	{
		content = dbc;
	}
}
