package Retriever;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;


//TODO FIX SORT to sort the documents based on theta's

public class VSM {
	List<Token> tokens;
	List<Document> documents;
	Document query;

	public VSM(Set<String> args){
		//Sets up database and gets builds the required lists
		Database db = new Database();
		db.doSearch(args);

		//get the tokens , query, and documents form the database
		tokens = db.getTokens();
		query = db.getQuery();
		documents = db.getDocuments();

		//System.out.println(query.intersections.get(0).getToken().data);


		//Set the weights in the intersections and then the thetas in the documents
		calculateWeights();
		calculateThetas();

		String template = "%.3f";

		
		Collections.sort(documents,new DocumentComparator());
		for(Document d: documents){

			if(d.getTheta() > .01){
				System.out.println(String.format(template,d.getTheta()) + d.name);

			}
		}
		
		
	}


	public double getTheta(Document d1, Document d2){
		if(d1.name.equals("TransactionType.java")){
			String temp = "%.3f";
			
			System.out.println("++++++===++++++");
			System.out.println(String.format(temp,dotProduct(d1, d2)));
			System.out.println("-");
			System.out.println(String.format(temp,d1.getMagnitude()));
			System.out.println("-");
			System.out.println(String.format(temp,d1.getMagnitude()));
			System.out.println("++++++===++++++");
		}
		
		return dotProduct(d1, d2) / (d1.getMagnitude() * d2.getMagnitude());
		//dotProduct(d1,d2) / ( |d1| |d2|)
		//use Document.getMagnitude
	}

	//This can be used if the query is the default comparitor
	public double getTheta(Document d1){

		return dotProduct(query,d1) / query.getMagnitude() * d1.getMagnitude();
	}


	public void calculateWeights(){
		int d = documents.size();
		System.out.println("TOTAL DOCS:" + d);
		for(Token t: tokens){
			t.calcIntersectionWeights(d);
		}
	}
	
	public List<String> getDocsList(){
		List<String> docs = new LinkedList();
		
		for(Document d: documents){
			docs.add(":"+d.name);
		}
		
		return docs;
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
			//System.out.println(i.getWeight());
			dotCheck.put(i.getToken(), i.getWeight());
		}

		for(Intersection i: doc2){

			Token tokKey = i.getToken();
			if(dotCheck.containsKey(tokKey)){
				dotProduct += dotCheck.get(tokKey) * i.getWeight();
			}
		}

		String temp = "%.3f";
		System.out.println(d2.name + ":" + dotProduct);
		return dotProduct;

	}


	public void calculateThetas(){
		//System.out.println(documents.size());
		//System.out.println("pow camp");
		double tempTheta;
		//    	String temp = "%.3f";
		for(Document d: documents){
			String temp = "%.3f";

			tempTheta = getTheta(query, d);
			//System.out.println(String.format(temp,tempTheta) + ":" + d.name );
        	
			d.setTheta(tempTheta);
		}   
	}


	public void buildThreshhold(){



	}


	public static void main(String[] args) {

	}



}