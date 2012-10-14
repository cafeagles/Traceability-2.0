package Indexer;

import java.util.LinkedList;
import java.util.List;

public class CodeChunk extends Chunk {
	public CodeChunk(){
		super();
	}
	public CodeChunk(StringBuffer startBuffer){
		super(startBuffer);
	}

	@Override
	public StringBuffer getBuffer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isComplete() {
		int i = 1;
		
		//System.out.println(buffer);
		
		if(buffer.length() < 2){
			return false;
		}
		
		char x = buffer.charAt(0);
		char y = buffer.charAt(1);
		
		for(;i<buffer.length();i++,x=y){
			
			y=buffer.charAt(i);
			if(x == '/' && (y == '/' || y=='*')){
			
				// This deletes all unimportant data from the front of the stringbuffer 
				buffer = buffer.delete(0,i-1);
				return true;
				
			}
			content.append(x);
		}
		content.append(y);
		return false;
	}


	@Override
	public void parse(TokenTracker tt) {
		List<String> wordList = new LinkedList<String>();
		
		
		wordList = Parser.cleanseChunk(buffer);
		
		
		for(String s:wordList)
			tt.addCodeToken(s);
//		return wordList;
	}

	@Override
	//provides the next chunk once this one has been closed
	public Chunk nextChunk() {
		return new CommentChunk(buffer);
	}
	
	// this was used to ensure the threaded queue was working
	public void test() {
		// TODO Auto-generated method stub
		
	}

}
