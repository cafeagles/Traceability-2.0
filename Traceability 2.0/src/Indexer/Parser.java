package Indexer;



import java.util.LinkedList;
import java.util.List;



public class Parser {
	//private static Stemmer s = null;

	// removes camel case, punctuation, and splits words
	// the words are checked against the keyword/stopword hash
	// before being added to the wordList for futher testing.
	public static List<String> cleanseChunk(StringBuffer buffer){

		StopKeywordRemover stkwremover = StopKeywordRemover.getInstance();
		//		s = new Stemmer();

		//System.out.println(buffer.toString());

		List<String> wordList = new LinkedList<String>();


		int beginWord = 0;
		char lastChar = ' ';
		char tempChar = ' ';
		String word = " ";


		//System.out.println(buffer.length());
		if(buffer.length() <2) return wordList;
		for(int k=1; k<buffer.length(); k++)
		{


			tempChar = buffer.charAt(k);


			lastChar = buffer.charAt(k-1);




			//replaces non letter characters with whitespace
			if(!Character.isLetter(tempChar))
			{
				buffer.setCharAt(k, ' ');
			}


			//breaks Camel case words into two words 
			//  and stores as a lower case word
			if(Character.isUpperCase(tempChar) && Character.isLowerCase(lastChar)){
				word = buffer.substring(beginWord, k);
				if(!stkwremover.removeWord(word.trim().toLowerCase())){
					//System.out.print("Word before stem 1: " + word);

					word = stem(word.trim().toLowerCase());
					//System.out.println(" Word after Stem: "+word);

					wordList.add(word.trim().toLowerCase());
				}
				beginWord = k;
			}


			//Checks for whitespace to indicate the end of a word
			// Also checks to see if the buffer still has not
			// been dumped by the end of the file
			//stores the words in lower case
			if((lastChar == ' ' && Character.isLetter(tempChar))){
				word = buffer.substring(beginWord, k);
				if(!stkwremover.removeWord(word.trim().toLowerCase())){
					//System.out.print("Word before stem 2: " + word);
					word = stem(word.trim().toLowerCase());
					wordList.add(word.trim().toLowerCase());
					//System.out.println(" Word after Stem: "+word);

				}
				beginWord = k;
			}
			else if(k==buffer.length()-1){
				word = buffer.substring(beginWord, k+1);
				if(!stkwremover.removeWord(word.trim().toLowerCase())){
					//System.out.print("Word before stem 3: " + word);
					word = stem(word.trim().toLowerCase());
					//System.out.println(" Word after Stem: "+word);
					wordList.add(word.trim().toLowerCase());
				}
			}



		}


		return wordList;
	} 


	public static String stem(String word){

		Stemmer s = new Stemmer();
		//		char[] array = word.toCharArray();

		for(char c:word.toCharArray()){

			s.add(c);
		}
		//		s.add(word.toCharArray(), word.length());
		//		System.out.print(s.getResultBuffer());

		s.stem();
		//		for(int i = 0;i<s.getResultLength();i++){
		//			System.out.print(s.getResultBuffer()[i]);
		//		}

		//		}
		//array = s.getResultBuffer();
		//		for(int i = 0;i<s.getResultLength();i++){
		//			System.out.print(array[i]);
		//		}

		//System.out.print("||" + s.toString() + "||");
		return s.toString();
	}
}

