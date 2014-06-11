package fileFeatures;

import gui.MainFrame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import drawableObjects.DrawingBoardContent;
import drawableObjects.Furniture;
import drawableObjects.Wall;

/**
 * Outil pour ouvrir un fichier SketchHome enregistré
 */
public class ContentRestorer 
{

	private DrawingBoardContent content;
	private MainFrame libraryContainer;
	private FileInputStream fis;
	private ObjectInputStream in;
	
	/**
	 * Crée un nouvel ouvreur de fichier SketchHome
	 * @param libraryContainer : contient les librairies de meubles pour paramétrer les meubles à restaurer
	 * @param dbc : plan à remplir lors de la restauration de contenu
	 */
	public ContentRestorer (MainFrame libraryContainer, DrawingBoardContent dbc)
	{
		this.libraryContainer = libraryContainer;
		content = dbc;
	}
	
	/**
	 * Restaure un plan sauvegardé en lisant le fichier donné.
	 * @param f : fichier SketchHome à ouvrir
	 */
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
					//paramétrage de la librairie du meuble
					((Furniture)o).setLibrary(libraryContainer.getLibraryByName(((Furniture)o).getLibraryName()));
					
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
		
	/**
	 * Ouvre un fichier.
	 * @param file fichier à ouvrir
	 */
	public void openFile(File file)
	{

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
	
	/**
	 * Ferme le fichier géré par l'ouvreur de fichier
	 */
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
