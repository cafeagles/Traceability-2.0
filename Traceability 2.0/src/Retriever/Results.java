package Retriever;

public class Results {
    
    protected Link[] link;
    
    public Results()
    {
        
    }
    
    public int getDocCount()
    {
        //Database db = Database.getInstance();;
        int results;
        
        //Query to retrieve the number of Documents
        String query = "select count(Quantity) from Token ";
        
        
        //db.submitQuery(query);
        
        //returns the Document count
        
        
        
        return 0;
    }
    
    public int getKeywordFrequency(String keyword )
    {
        //Database db = Database.getInstance();
        return 0;
    }
    
    //public int[] getDocIds()
    //{
        
    //}
    
    
}
