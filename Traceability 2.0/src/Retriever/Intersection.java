package Retriever;
//testing netbeans
public class Intersection {
	private Token t;
	private Document d;
	private double weight;
	private int tfi;
	
	public Intersection(int tfi){
		this.tfi = tfi;
	}
	
	public void setWeight(double w){
		weight = w;
	}
	
	public void setToken(Token t){
		this.t =t;

	}
	
	public void setDocument(Document d){
		this.d = d;
	}
	
	public int getTFI(){
		return tfi;
	}
	
	public double getWeight(){
		return weight;
	}
	
	public Token getToken(){
		return t;
	}
	
	public Document getDocument(){
		return d;
	}
	
}
