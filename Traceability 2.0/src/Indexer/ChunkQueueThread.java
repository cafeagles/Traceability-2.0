package Indexer;
//
//import java.lang.Thread;
//import java.util.LinkedList;
//import java.util.Queue;
import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;



import java.util.LinkedList;
import java.util.List;
public class ChunkQueueThread extends Thread{
    public boolean complete;
    private BlockingQueue<Chunk> queue = new LinkedBlockingQueue<Chunk>();
    private static long timeOut = 1000;
    private TokenTracker tt;
     
    public ChunkQueueThread(TokenTracker tt){
        complete = false;
        this.tt = tt;
    }

    public void setComplete(){
    	
    	complete = true;
    }
    
    public void run(){
        Chunk current;
//        List<String> wl = new LinkedList<String>();
        while(!(complete && queue.isEmpty())){
          try{
        	 if(!queue.isEmpty()){
        		 
              current = queue.poll(timeOut,TimeUnit.MILLISECONDS);
             // current.test();
              current.parse(tt);
        	 }
            }
          catch(InterruptedException ie){
        	  System.out.println("CQT EXCEPTION:" + ie);
           }
          catch(Exception e){
        	  //System.out.println("Eroor:" + e); //Eroor:java.lang.StringIndexOutOfBoundsException: String index out of range: 0
          }
         
          //current.parse();
          
        }
        //System.out.println("Ending Thread");
    }

    public void append(Chunk c){
      try{
       queue.offer(c,timeOut,TimeUnit.MILLISECONDS);
       }
       catch(InterruptedException ie){
       
       }
    }
}



