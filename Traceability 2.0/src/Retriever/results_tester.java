package Retriever;

public class results_tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Database db = new Database();
		Results rs = db.retrieve(args);
		rs.doAllCalculations();
		Document[] best = rs.getBestDocs( 80.0);
		for(Document d: best){
			System.out.println(d.getPath() + "   " + d.theta);
		}
		
	}

}
