package Retriever;

import java.util.List;
import java.util.ListIterator;

public class Document {
	public List<Weight> weights;
	double theta;
	String path;


	public Document(String path) {
		
	   this.path = path;	
	}



	public void calculateTheta(Document query, Token[] keywords){

		// used for summation of the squares 
		double MagSumQ = 0 ;
		double MagSumD = 0 ;

		double MagQ = 0;
		double MagD = 0;

		// used to store the running dot product of the two vectors( not actually java vectors)   
		double dotPro = 0;

		ListIterator<Weight> qIt = query.weights.listIterator();
		ListIterator<Weight> dIt = this.weights.listIterator();

		Weight qCurrent = qIt.next();
		Weight dCurrent = dIt.next(); 

		if((qCurrent != null) || (dCurrent != null))
			throw new Error("Document with no keywords associated");


		// Looking through each Keyword (curTok) 
		// Check curTok against the tokens of the weights qCurrent and dCurrent
		//    If all 3 are equal
		//    Dot product must be done
		// Else Check to see if either of the current weights are equal to curTok
		//    If one is, check if its iterator has a next and set it to that
		//    If the iterator does not have a next, break
		for(Token curTok: keywords){
			if(qCurrent.tok == curTok && dCurrent.tok == curTok){
				// Math explaination :
				// This adds all non trivial vector products together to get the sum. (Dot Product) This reduces computation time 
				// and space. The same is being done for the magnitudes of the vectors. The magnitudes will be square rooted
				// after the sums are complete

				dotPro += qCurrent.weight * dCurrent.weight;
				MagSumQ += qCurrent.weight * qCurrent.weight;
				MagSumD += dCurrent.weight * dCurrent.weight;
			}
			else{
				if(qCurrent.tok == curTok){
					if(qIt.hasNext())
						qCurrent = qIt.next();
					else
						break;
				}
				if(dCurrent.tok == curTok){
					if(dIt.hasNext())
						dCurrent = dIt.next();
					else
						break;
				}
			}

		}

		MagQ = Math.sqrt(MagSumQ);
		MagD = Math.sqrt(MagSumD);

		theta = Math.acos(dotPro / (MagQ*MagD));


	}

}

