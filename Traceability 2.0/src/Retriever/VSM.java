package Retriever;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class VSM {
	List<Token> tokens;
	List<Document> documents;
	Document query;

	public VSM(List<String> args){

	}

	public double getTheta(Document d1, Document d2){
		return dotProduct(d1, d2) / (d1.getMagnitude() * d2.getMagnitude());
		//dotProduct(d1,d2) / ( |d1| |d2|)
		//use Document.getMagnitude

	}

	public void calculateWeights(){
		int d = documents.size();
		for(Token t: tokens){
			t.calcIntersectionWeights(d);
		}
	}

	public double dotProduct(Document d1, Document d2){
		// This takes all of the intersections from the query and stores them to a hashmap
		// Then run through all the intersections of the comparative document and check to
		// to see if the tokens between them match.
		
		// if there is a match then we multiply them together and update the dotproduct running sum
		
		double dotProduct = 0;
		List<Intersection> doc1, doc2;


		doc1 = d1.getIntersections();
		doc2 = d2.getIntersections();

		HashMap<Token,Double> dotCheck = new HashMap<Token, Double>();

		for(Intersection i : doc1){
			dotCheck.put(i.getToken(), i.getWeight());
		}

		for(Intersection i: doc2){
			Token tokKey = i.getToken();
			if(dotCheck.containsKey(tokKey)){
				dotProduct += dotCheck.get(tokKey) * i.getWeight();
			}
		}


		return dotProduct;

	}


	public void calculateThetas(){
		for(Document d: documents){
			d.setTheta(getTheta(query, d));
		}	
	}


	public static void main(String[] args) {

	}



}
