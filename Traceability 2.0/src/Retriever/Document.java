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

	//Justin Moore and Chris Myers
	// gets the magnitude of the weights vector for individual documents
	public double getMagnitude(){
		double temp = 0;

		for (Intersection item: intersections)
		{
			temp +=Math.pow(item.getWeight(),2);
		} 
		return Math.sqrt(temp);


	}

	public void setTheta(double theta){
		this.theta = theta;
	}

	public List<Intersection> getIntersections(){
		return intersections;
	}

	public void addIntersection(Intersection i) {
		intersections.add(i);
	}

}