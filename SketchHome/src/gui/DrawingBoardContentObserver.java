package gui;

import java.util.Observer;

public interface DrawingBoardContentObserver {
	public Observer getAdditionObserver();
	public Observer getDeletionObserver();
	public Observer getModificationObserver();
}
