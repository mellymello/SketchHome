package drawableObject;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class DrawingBoardContent extends Observable {
	
	private LinkedList<Wall> walls = new LinkedList<Wall>();
	private Wall tmpWall;
	private CtrlPoint selectedCtrlPoint;
	private int ctrlPointDiameter;
	private int wallThickness;
	private Wall selectedWall;
	
	private LinkedList<Furniture> placedFurnitures = new LinkedList<Furniture>();
	//le modèle du meuble sélectionné dans la librairie
	private Furniture selectedModelFurniture;
	//le meuble sélectionné dans la plan
	private Furniture selectedFurniture;
	
	private class ModificationObservable extends Observable {
		public void sendNotify(Furniture modifiedFurniture) {
			setChanged();
			notifyObservers(modifiedFurniture);
		}
	}
	private class AdditionObservable extends Observable {
		public void sendNotify(Furniture addedFurniture) {
			setChanged();
			notifyObservers(addedFurniture);
		}
	}
	private class DeletionObservable extends Observable {
		public void sendNotify(Furniture deletedFurniture) {
			setChanged();
			notifyObservers(deletedFurniture);
		}
	}
	
	private ModificationObservable modificationObservable = new ModificationObservable();
	private AdditionObservable additionObservable = new AdditionObservable();
	private DeletionObservable deletionObservable = new DeletionObservable();
	
	public DrawingBoardContent(int ctrlPointDiameter, int wallThickness) {
		this.ctrlPointDiameter = ctrlPointDiameter;
		this.setWallThickness(wallThickness);
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

	public void setCtrlPointDiameter(int ctrlPointDiameter) {
		this.ctrlPointDiameter = ctrlPointDiameter;
	}

	public Wall getSelectedWall() {
		return selectedWall;
	}

	public void setSelectedWall(Wall selectedWall) {
		this.selectedWall = selectedWall;
	}

	public LinkedList<Furniture> getFurnitures() {
		return placedFurnitures;
	}

	public void setFurnitures(LinkedList<Furniture> furnitures) {
		this.placedFurnitures = furnitures;
	}

	public void addFurniture(Furniture f) {
		placedFurnitures.add(f);
		
		additionObservable.sendNotify(f);
	}

	public void deleteFurniture(Furniture f) {
		placedFurnitures.remove(f);
		deletionObservable.sendNotify(f);
	}
	
	public LinkedList<Wall> getWalls() {
		return walls;
	}

	public void setWalls(LinkedList<Wall> walls) {
		this.walls = walls;
	}

	public int getWallThickness() {
		return wallThickness;
	}

	public void setWallThickness(int wallThickness) {
		this.wallThickness = wallThickness;
	}

	public Furniture getSelectedFurniture() {
		return selectedFurniture;
	}

	public void setSelectedFurniture(Furniture selectedFurniture) {
		this.selectedFurniture = selectedFurniture;
	}

	public Furniture getSelectedModelFurniture() {
		return selectedModelFurniture;
	}

	public void setSelectedModelFurniture(Furniture selectedModelFurniture) {
		this.selectedModelFurniture = selectedModelFurniture;
	}

	public void addAdditionObserver(Observer obs) {
		additionObservable.addObserver(obs);
	}
	
	public void addDeletionObserver(Observer obs) {
		deletionObservable.addObserver(obs);
	}
	
	public void addModificationObserver(Observer obs) {
		modificationObservable.addObserver(obs);
	}
}