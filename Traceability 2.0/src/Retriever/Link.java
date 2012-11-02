package Retriever;

public class Link {
	private Token t;
	private int tokenId;
	private Document d;
	private int count;
	public Document getDocument;
	private double weight;
 	private enum type{
 		CODE, COMMENT
 	}
 	public Link(Document d, Token t, int count){
 		this.d = d;
 		this.t = t;
 		this.count = count;
 	}
	public Token getToken() {
		return t;
	}
	
	public void calculateWeight(double IDF){
		weight = count * IDF;
	}
	public double getWeight(){
		return weight;
	}
 	
}
