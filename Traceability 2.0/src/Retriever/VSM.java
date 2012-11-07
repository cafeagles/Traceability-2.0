package Retriever;

import java.util.List;

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
		//TODO dotProduct
		//The hard one
		return 0;
	}
		
	
	public void calculateThetas(){
		for(Document d: documents){
			d.setTheta(getTheta(query, d));
		}	
	}
	
	public static void main(String[] args) {

	}

	
	
}
