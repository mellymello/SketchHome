package drawableObjects;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * Représente le contenu d'un plan dans SketchHome
 */
public class DrawingBoardContent extends Observable {
	
	//murs fixé sur le plan
	private LinkedList<Wall> placedWalls = new LinkedList<Wall>();
	//mur temporaire dessiné sur le plan 
	private Wall tmpWall;
	//le ctrlPoint sélectionné  
	private CtrlPoint selectedCtrlPoint;
	//le mur sélectionné
	private Wall selectedWall;
	//variable utilisé pour fixer le diamètre du ctrlPoint
	private int ctrlPointDiameter;
	//variable utilisé pour fixer l'épesseur du mur
	private int wallThickness;
	
	//meubles du plan
	private LinkedList<Furniture> placedFurnitures = new LinkedList<Furniture>();
	//le modèle de meuble sélectionné dans la librairie
	private Furniture selectedFurnitureModel;
	//le meuble sélectionné dans le plan
	private Furniture selectedFurniture;
	
	private ModificationObservable modificationObservable = new ModificationObservable();
	private AdditionObservable additionObservable = new AdditionObservable();
	private DeletionObservable deletionObservable = new DeletionObservable();
	
	
	/**
	 * Objet observable utilisé pour signaler la modification d'un meuble
	 */
	public class ModificationObservable extends Observable {
		public void sendNotify(Furniture modifiedFurniture) {
			setChanged();
			notifyObservers(modifiedFurniture);
		}
	}
	
	/**
	 * Objet observable utilisé pour signaler l'ajout d'un meuble
	 */
	private class AdditionObservable extends Observable {
		public void sendNotify(Furniture addedFurniture) {
			setChanged();
			notifyObservers(addedFurniture);
		}
	}
	
	/**
	 * Objet observable utilisé pour signaler la supprssion d'un meuble
	 */
	private class DeletionObservable extends Observable {
		public void sendNotify(Furniture deletedFurniture) {
			setChanged();
			notifyObservers(deletedFurniture);
		}
	}
	
	
	/**
	 * Génère un nouveau contenu de plan
	 * @param ctrlPointDiameter : diamètre des points de contrôle de mur dans le plan
	 * @param wallThickness : largeur des murs dans le plan
	 */
	public DrawingBoardContent(int ctrlPointDiameter, int wallThickness) {
		this.ctrlPointDiameter = ctrlPointDiameter;
		this.wallThickness = wallThickness;
	}
	
	/**
	 * Abonne un observateur à l'ajout de meuble dans le plan.
	 * @param obs : observateur à abonner.
	 */
	public void addAdditionObserver(Observer obs) {
		additionObservable.addObserver(obs);
	}
	
	/**
	 * Abonne un observateur à la suppression de meuble dans le plan.
	 * @param obs : observateur à abonner.
	 */	
	public void addDeletionObserver(Observer obs) {
		deletionObservable.addObserver(obs);
	}
	
	/**
	 * Abonne un observateur à la modification de meuble dans le plan.
	 * @param obs : observateur à abonner.
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
	 * @param f : meuble à ajouter
	 */
	public void addFurniture(Furniture f) {
		placedFurnitures.add(f);
		additionObservable.sendNotify(f);
	}

	/**
	 * Supprime un meuble du plan
	 * Notifie les DeletionObserver du plan
	 * @param f : meuble à supprimer
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
	 * Change le meuble sélectionné dans le plan.
	 * Si un meuble est réellement sélectionné, notifie les ModificationObserver du plan.
	 * @param selectedFurniture : meuble sélectionné dans le plan
	 */
	public void setSelectedFurniture(Furniture selectedFurniture) {
		//désélection du meuble précédemment sélectionné
		if (this.selectedFurniture != null) {
			this.selectedFurniture.setSelected(false);
		}
		//sélection du meuble
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
