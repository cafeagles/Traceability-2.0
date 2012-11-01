package Retriever;

/**
 * @author ryan && john
 *
 */
public class Token {
    boolean inQuery;
    Document[] locations;
    int[] docFrequencies;
    double weights;
    int DFI;
    double IDF;
    
    public Token(boolean inQuery, Document[] locations, int D){
        this.inQuery = inQuery;
        this.locations = locations;
        DFI = locations.length;
        IDF = Math.log(D/(DFI+1));
    }
    
    public Document[] getLocations(){
        return locations;
    }
    
    
    public Document docAt(int i){
       
         if(i < DFI - 1 && i > 0){
           return locations[i];
         }
         else{
           throw new Error("Index out of bounds");
         }
       
    }
    
    
    public int countAt(int i){
    if(i < DFI - 1 && i > 0){
         return docFrequencies[i];
       }
    else
    	throw new Error("Index out of bounds");
     
    }
    
    public void calculateWeights(){
    
    	// for all of the documents contained in this token
    	// we get a tfi * IDF 
    	
    	// DFI decremented because the length is not the same as the last index
    	for(int i = 0; i < (DFI - 1); i++){
        
    		 // doc frequencies is the tfi
    		 // IDF is calculated in the constructor
     		 weights = docFrequencies[i] * IDF ;
     		 
     		 // weights are the added to their appropriated weight object
             Weight thistokWeight = new Weight(this,weights);
             locations[i].weights.add(thistokWeight) ;
             
        }
        
       
    } 
    
       
}
