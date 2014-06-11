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

/**
 * Outil pour sauvegarder un plan dans un fichier SketchHome
 */
public class ContentSaver
{
	private File file;
	private FileOutputStream fos = null;
	private ObjectOutputStream out;
	
	private DrawingBoardContent content;
	
	/**
	 * Crée un nouvel sauvegardeur de fichier SketchHome
	 * @param dbc : plan à sauvegarder
	 */
	public ContentSaver(DrawingBoardContent dbc)
	{
		content = dbc;
	}
	
	/**
	 * Sauvegarde le plan dans un nouveau fichier 
	 * @param f : nouveau fichier à créer
	 */
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
	
	/**
	 * Sauvegarde le plan dans le fichier géré par le sauvegardeur. 
	 */
	public void save () throws FileNotFoundException
	{
		if(file == null){
			throw new FileNotFoundException();
		}
		
		openFile(file);
		
		//sauvegarde des meubles
		for (Furniture f :content.getPlacedFurnitures())
		{
			try {
				out.writeObject(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//sauvegarde des murs
		for (Wall w :content.getPlacedWalls())
		{
			try {
				out.writeObject(w);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		closeFile();
	}
	
	/**
	 * Ouvre un fichier.
	 * @param file fichier à ouvrir
	 */
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
	
	/**
	 * Ferme le fichier géré par l'ouvreur de fichier
	 */
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
	
	public void setOpenedFile(File f){
		this.file=f;
	}
}
