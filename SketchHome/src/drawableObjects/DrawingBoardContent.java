package drawableObjects;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * Repr�sente le contenu d'un plan dans SketchHome
 */
public class DrawingBoardContent extends Observable {
	
	//murs fix� sur le plan
	private LinkedList<Wall> placedWalls = new LinkedList<Wall>();
	//mur temporaire dessin� sur le plan 
	private Wall tmpWall;
	//le ctrlPoint s�lectionn�  
	private CtrlPoint selectedCtrlPoint;
	//le mur s�lectionn�
	private Wall selectedWall;
	//variable utilis� pour fixer le diam�tre du ctrlPoint
	private int ctrlPointDiameter;
	//variable utilis� pour fixer l'�pesseur du mur
	private int wallThickness;
	
	//meubles du plan
	private LinkedList<Furniture> placedFurnitures = new LinkedList<Furniture>();
	//le mod�le de meuble s�lectionn� dans la librairie
	private Furniture selectedFurnitureModel;
	//le meuble s�lectionn� dans le plan
	private Furniture selectedFurniture;
	
	private ModificationObservable modificationObservable = new ModificationObservable();
	private AdditionObservable additionObservable = new AdditionObservable();
	private DeletionObservable deletionObservable = new DeletionObservable();
	
	
	/**
	 * Objet observable utilis� pour signaler la modification d'un meuble
	 */
	public class ModificationObservable extends Observable {
		public void sendNotify(Furniture modifiedFurniture) {
			setChanged();
			notifyObservers(modifiedFurniture);
		}
	}
	
	/**
	 * Objet observable utilis� pour signaler l'ajout d'un meuble
	 */
	private class AdditionObservable extends Observable {
		public void sendNotify(Furniture addedFurniture) {
			setChanged();
			notifyObservers(addedFurniture);
		}
	}
	
	/**
	 * Objet observable utilis� pour signaler la supprssion d'un meuble
	 */
	private class DeletionObservable extends Observable {
		public void sendNotify(Furniture deletedFurniture) {
			setChanged();
			notifyObservers(deletedFurniture);
		}
	}
	
	
	/**
	 * G�n�re un nouveau contenu de plan
	 * @param ctrlPointDiameter : diam�tre des points de contr�le de mur dans le plan
	 * @param wallThickness : largeur des murs dans le plan
	 */
	public DrawingBoardContent(int ctrlPointDiameter, int wallThickness) {
		this.ctrlPointDiameter = ctrlPointDiameter;
		this.wallThickness = wallThickness;
	}
	
	/**
	 * Abonne un observateur � l'ajout de meuble dans le plan.
	 * @param obs : observateur � abonner.
	 */
	public void addAdditionObserver(Observer obs) {
		additionObservable.addObserver(obs);
	}
	
	/**
	 * Abonne un observateur � la suppression de meuble dans le plan.
	 * @param obs : observateur � abonner.
	 */	
	public void addDeletionObserver(Observer obs) {
		deletionObservable.addObserver(obs);
	}
	
	/**
	 * Abonne un observateur � la modification de meuble dans le plan.
	 * @param obs : observateur � abonner.
	 */
	public void addModificationObserver(Observer obs) {
		modificationObservable.addObserver(obs);
	}
	
	public ModificationObservable getModificationObservable() {
		return modificationObservable;
	}
	
	public Wall getTmpWall() {
		return tmpWall;
	}

	public void setTmpWall(Wall tmpWall) {
		this.tmpWall = tmpWall;
	}

	public CtrlPoint getSelectedCtrlPoint() {
		return selectedCtrlPoint;
	}

	public void setSelectedCtrlPoint(CtrlPoint selectedCtrlPoint) {
		this.selectedCtrlPoint = selectedCtrlPoint;
	}

	public int getCtrlPointDiameter() {
		return ctrlPointDiameter;
	}

	public Wall getSelectedWall() {
		return selectedWall;
	}

	public void setSelectedWall(Wall selectedWall) {
		this.selectedWall = selectedWall;
	}

	public LinkedList<Furniture> getPlacedFurnitures() {
		return placedFurnitures;
	}

	/**
	 * Ajoute un meuble au plan
	 * Notifie les AdditionObserver du plan
	 * @param f : meuble � ajouter
	 */
	public void addFurniture(Furniture f) {
		placedFurnitures.add(f);
		additionObservable.sendNotify(f);
	}

	/**
	 * Supprime un meuble du plan
	 * Notifie les DeletionObserver du plan
	 * @param f : meuble � supprimer
	 */
	public void deleteFurniture(Furniture f) {
		placedFurnitures.remove(f);
		deletionObservable.sendNotify(f);
	}
	
	public LinkedList<Wall> getPlacedWalls() {
		return placedWalls;
	}

	public void addWall(Wall w) {
		placedWalls.add(w);
	}

	public int getWallThickness() {
		return wallThickness;
	}

	public Furniture getSelectedFurniture() {
		return selectedFurniture;
	}

	/**
	 * Change le meuble s�lectionn� dans le plan.
	 * Si un meuble est r�ellement s�lectionn�, notifie les ModificationObserver du plan.
	 * @param selectedFurniture : meuble s�lectionn� dans le plan
	 */
	public void setSelectedFurniture(Furniture selectedFurniture) {
		//d�s�lection du meuble pr�c�demment s�lectionn�
		if (this.selectedFurniture != null) {
			this.selectedFurniture.setSelected(false);
		}
		//s�lection du meuble
		this.selectedFurniture = selectedFurniture;
		if(selectedFurniture != null) {
			selectedFurniture.setSelected(true);
			modificationObservable.sendNotify(selectedFurniture);
		}
	}

	public Furniture getSelectedFurnitureModel() {
		return selectedFurnitureModel;
	}

	public void setSelectedFurnitureModel(Furniture selectedlFurnitureModel) {
		this.selectedFurnitureModel = selectedlFurnitureModel;
	}

	/**
	 * Vide le contenu du plan
	 */
	public void clearContent(){
		placedWalls.clear();
		tmpWall=null;
		selectedCtrlPoint=null;
		selectedWall=null;
		while(placedFurnitures.size() > 0) {
			deleteFurniture(placedFurnitures.getFirst());
		}
		selectedFurnitureModel=null;
		selectedFurniture=null;
	}
}
