package Retriever;
import java.util.ArrayList;

public class Results {
    
    protected Link[] link;
 
    public Results()
    {
        
    }
    
    public int getDocCount()
    {
        //Initialize Database
        //Database db = Database.getInstance();
        
        int results;
        
        //Query to retrieve the number of Documents
        String query = "select count(Quantity) from link ";
        
        //Submit the query
        //db.submitQuery(query)
        
        
        
        
        //returns the Document count
        return 0;
    }
    
    public int getKeywordFrequency(String keyword )
    {
        //Database db = Database.getInstance();
        return 0;
    }
    
    
    public boolean greaterThan(Token t)
    {
        //if(this.data>t.data)
            return true;
    }
    
    /*
    Returns an array of doc Ids
    public int[] getDocIds()
    {
        //Database db = db.getInstance
        
        String query = "select DocId from link";
        
       // int[] docIds;
        //ArrayList<Integer> docIds = new ArrayList<Integer>();
        
       // return docIds;
    
    }
    */
    
}
