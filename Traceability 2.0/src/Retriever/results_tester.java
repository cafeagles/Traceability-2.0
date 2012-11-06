package Retriever;

public class results_tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Database db = new Database();
		System.out.println(args[0]);
		Results rs = db.retrieve(args);
		rs.doAllCalculations();
		Document[] best = rs.getBestDocs(1.00);
		for(Document d: best){
			System.out.println("------------------");
			System.out.println(d.getPath() + "   " + d.theta);
		}
		
	}

}
