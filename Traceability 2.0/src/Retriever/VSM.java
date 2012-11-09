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
				System.out.println(String.format(template,d.getTheta()) + ":" +  d.name);

			}
		}
		
		
	}


	public double getTheta(Document d1, Document d2){
			
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
			if(d.theta > 0.12)
			  docs.add(":"+d.name);
		}
		
		return docs;
	}

	public static double dotProduct(Document d1, Document d2){
		// This takes all of the intersections from the query and stores them to a hashmap
		// Then run through all the intersections of the comparative document and check to
		// to see if the tokens between them match.

		// if there is a match then we multiply them together and update the dotproduct running sum

		double dotProduct = 0;
		List<Intersection> doc1, doc2;


		doc1 = d1.getIntersections();
		doc2 = d2.getIntersections();

		//System.out.println(doc1.size());
		
		HashMap<Integer,Double> dotCheck = new HashMap<Integer, Double>();

		for(Intersection i : doc1){
//			System.out.println(i.getToken().data);
			dotCheck.put(i.getToken().hashCode(), i.getWeight());
		}
		
		//System.out.println(dotCheck.size());

		for(Intersection i: doc2){
			
			//System.out.println(i.getToken().data);
			
//			if(d2.name.equals("TransactionType.java")){
//				System.out.println("--");
//				double thing = i.getWeight();
//				System.out.println("HEY:" + thing );
//			}

			int tokKey = i.getToken().hashCode();
			if(dotCheck.containsKey(tokKey)){
				dotProduct += dotCheck.get(tokKey) * i.getWeight();
			}
		}

		String temp = "%.3f";
		//System.out.println(d2.name + ":" + dotProduct);
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
		Document d1 = new Document("one");
		Document d2 = new Document("two");
		
		Token t1 = new Token("doctor");
		Token t2 = new Token("patient");
		Token t3 = new Token("medic");
		Token t4 = new Token("blue");
		
		Intersection d1t1 = new Intersection(1);
		d1t1.setToken(t1);
		d1t1.setWeight(5);
		d1.addIntersection(d1t1);
		t1.addIntersection(d1t1);
		
		Intersection d1t2 = new Intersection(1);
		d1t2.setToken(t2);
		d1t2.setWeight(1);
		d1.addIntersection(d1t2);
		t2.addIntersection(d1t2);
		
		Intersection d1t3 = new Intersection(1);
		d1t3.setToken(t3);
		d1t3.setWeight(0);
		d1.addIntersection(d1t3);
		t3.addIntersection(d1t3);
		
		Intersection d1t4 = new Intersection(0);
		d1t4.setToken(t4);
		d1t4.setWeight(3);
		d1.addIntersection(d1t4);
		t4.addIntersection(d1t4);
		
		
		Intersection d2t1 = new Intersection(2);
		d2t1.setToken(t1);
		d2t1.setWeight(2);
		d2.addIntersection(d2t1);
		t1.addIntersection(d2t1);
		
		Intersection d2t2 = new Intersection(3);
		d2t2.setToken(t2);
		d2t2.setWeight(3);
		d2.addIntersection(d2t2);
		t2.addIntersection(d2t2);
		
		Intersection d2t3 = new Intersection(0);
		d2t3.setToken(t3);
		d2t3.setWeight(4);
		d2.addIntersection(d2t3);
		t3.addIntersection(d2t3);
		
		Intersection d2t4 = new Intersection(1);
		d2t4.setToken(t4);
		d2t4.setWeight(5);
		d2.addIntersection(d2t4);
		t4.addIntersection(d2t4);
		
		
		double dot = dotProduct(d1,d2);
		double theta = dot / d1.getMagnitude()*d2.getMagnitude();
		
		
		
		
//		String temp = "%.3f : %.3f";
//		System.out.println(String.format(temp, d1.getMagnitude(),d2.getMagnitude()));
//		System.out.println(String.format(temp, dot,theta));
//		
		
		
		
	}



}