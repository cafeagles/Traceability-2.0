package Retriever;

import java.util.LinkedList;
import java.util.List;

import org.omg.CORBA.FREE_MEM;


public class Token {
    List<Intersection> intersections;
    
    public Token(){
       
    }
    
    public double getIDF(int d, int DFI){
    	 return Math.log(d/DFI);
    }
    
    public int getDfi(){
    	return intersections.size();
    }
    
    public void calcIntersectionWeights(int d){
    	int DFI = getDfi();
		double IDF = getIDF(d, DFI);
    	for(Intersection t: intersections){
    		t.setWeight(IDF * t.getTFI());
    	}
    	//TODO calcIntersectionWeights
    	//Find IDF here
    	//Iterate Each sections
    	//Intersection.weight = Intersection.TFI * Token.IDF
    }

	public void addIntersection(Intersection i) {
		intersections.add(i);		
	}
    
}