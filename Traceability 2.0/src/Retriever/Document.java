package Retriever;



import java.util.List;
import java.util.ListIterator;



public class Document  {
	String name;
	double theta;
	List<Intersection> intersections;
	
	
	public Document(String name){
		this.name = name;
	}
	
	public double getMagnitude(){
		return 0;
		//TODO Ã(  intersection_n^2 )
	}
	
	public void setTheta(double theta){
		this.theta = theta;
	}
	
}