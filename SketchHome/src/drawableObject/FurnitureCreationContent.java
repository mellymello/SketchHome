package drawableObject;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class FurnitureCreationContent {

	
	private Ellipse2D tmpEllipse;
	private ArrayList<Ellipse2D> ellipses;
	
	public FurnitureCreationContent(){
		ellipses=new ArrayList<>();
		tmpEllipse=null;
	}
	
	public ArrayList<Ellipse2D> getEllipses(){
		return ellipses;
	}
	
	public void setTmpEllipse(Ellipse2D tmpEllipse){
		this.tmpEllipse=tmpEllipse;
	}
	
	public void addEllipse(Ellipse2D finalEllipse){
		ellipses.add(finalEllipse);
	}
}
