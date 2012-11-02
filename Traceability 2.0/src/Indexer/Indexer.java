package Indexer;
import java.io.*;
import java.net.URLDecoder;
//import java.util.AbstractQueue;
import java.util.LinkedList;
import java.util.Queue; 
import java.util.Scanner;

public class Indexer{
	public static void main(String[] args){
				
//		if(new File(args[0]).isDirectory()) System.out.println("yes");
//		else System.out.println("no");
		System.out.println("Starting Indexer");

		//Loops through each argument in passed through arguments and runs index on them
//		for(String eachArg: args){
//		    try{
////		    	URLDecoder.decode(path);
////		    	index(new File(URLDecoder.decode(eachArg)));
//		    	
//			   index(new File(eachArg));
//			  
//			}
//			catch(Exception e){
//				System.out.println(e.toString() + "\t" + eachArg + "jghewiusnv");
//			}
//
//		}
		try {
			index(new File(args[0]));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(args[0]);
			e.printStackTrace();
		}
		Database db = Database.getInstance();
		db.finish();
		System.out.println("Finished Indexer");
		
		
	}

	
	private static void index(File f) throws FileNotFoundException{
		
//		System.out.println(" asdf");
		String fileName = f.getName();
		System.out.println("Indexing: " + fileName);
//		System.out.println("jkl;");
//		
		if(!f.exists()){
			System.out.println(fileName + " does not exist");
			try {
				File test = new File("LOOK HERE");
				test.createNewFile();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// If the passed file is a directory, recursively call index on each file
		if(f.isDirectory()){
//			System.out.println(fileName + " is a directory*********/***********");

			for(File eachFile:f.listFiles()){
//				System.out.println("**********" + eachFile.getPath()+ "**********");
				index(eachFile);
				
			}
		}
		else{
			if(f.isFile() && fileName.contains(".java")){
		//		System.out.println("Indexing: " + fileName);
				//Scanner to get lines from the passed in java source file
				Scanner fileScanner = new Scanner(f);
				//start currentChunk that is worked with as a CommentChunk because it is more often the first type of chunk in a file. 
				Chunk currentChunk = new CommentChunk();
				//Pace hold chunk for switching chunks
				Chunk temp;
//				Queue<Chunk> chunkQueue = new LinkedList<Chunk>();
				TokenTracker tt = new TokenTracker();
				ChunkQueueThread CQT = new ChunkQueueThread(tt);
//				CQT.start();
				String buffer = new String();
				RegexIndexer regex = new RegexIndexer();
				while(fileScanner.hasNext()){
//					currentChunk.addLine(new StringBuffer(fileScanner.nextLine()));
//					if (currentChunk.isComplete()){
//						temp = currentChunk.nextChunk();
//						CQT.append(currentChunk);
//						currentChunk = temp;
//						
//					}
					buffer += fileScanner.nextLine() + "\n";
				}
				regex.split(buffer, tt);
				System.out.println("Done parsing");
				CQT.setComplete();
				while(CQT.isAlive());
				CQT.run();
				Database db = Database.getInstance();
//				for(String s:tt.getCodeKeys())
//					System.out.println(s);
				db.storeTokens(tt,fileName);
//				db.closeConnect();
				
				
				
			}
			else{
//				System.out.println(fileName + " is not supported");
			}
		}
	}
}
