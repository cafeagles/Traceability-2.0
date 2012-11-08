package Retriever;

import java.util.Comparator;

public class DocumentComparator implements Comparator{

	@Override
	public int compare(Object doc0, Object doc1) {
		
		double thetaD1 = ((Document)doc0).getTheta();
		double thetaD2 = ((Document)doc1).getTheta();
		
		if(thetaD1 > thetaD2)
			return 1;
		else if(thetaD2> thetaD1)
			return -1;
		else
			return 0;
		
	}

}
