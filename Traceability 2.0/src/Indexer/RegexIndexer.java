package Indexer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexIndexer {


	public String buffer;
	private String code;
	private String comments;
	public  HashMap<String, Integer> CommentKeyWords;
	public  HashMap<String, Integer> CodeKeyWords;
	
	public RegexIndexer()
	{
		buffer = new String();
		code = new String();
		comments = new String();
		CommentKeyWords = new HashMap<String,Integer>();
		CodeKeyWords = new HashMap<String,Integer>();
//		Scanner fileScanner;
//		try {
//			fileScanner = new Scanner(new File("C:\\Users\\Chris\\Desktop\\AdverseEventBean.java"));
//			while(fileScanner.hasNext())
//			{
//				buffer += fileScanner.nextLine() + "\n";
//			}
//			//System.out.println(buffer);
//			split(buffer);
//		} catch (FileNotFoundException e) {
//				System.out.println(e);
//		}	

	}
	
	public void split(String buffer, TokenTracker tt){
		
		
		Pattern pattern =  Pattern.compile("(//.*\\n)|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/");
		Matcher matcher = pattern.matcher(buffer);
		while(matcher.find()){
			
			 comments += matcher.group();	
		}
		code = matcher.replaceAll("");
		parseComments(comments, tt);
		parseCode(code, tt);
	}

	private void parseComments(String comments, TokenTracker tt) {
		Stemmer s = new Stemmer();
		StopKeywordRemover stkwremover = StopKeywordRemover.getInstance();
		
			//remove non letters
			Matcher m = Pattern.compile("[\\W\\d_]").matcher(comments);
			comments = m.replaceAll(" ");
			comments = splitCamelCase(comments);
			
			//split into individual words
			m = Pattern.compile("\\S+").matcher(comments);
			String temp;
			while (m.find()) {
				temp = (m.group().toLowerCase()).trim();
				
				//remove stop words 
				if (!stkwremover.removeWord(temp)) {
					//stem
					s.add(temp.toCharArray(), temp.length());
					s.stem();
					temp = s.toString();
					// add to the hashmap
					
					tt.addCommentToken(temp);
					if (CommentKeyWords.containsKey(temp)) {
						Integer i = CommentKeyWords.get(temp);
						CommentKeyWords.put(temp, ++i);
					} else
						CommentKeyWords.put(temp, 1);
				}
			}
//		}
	}

	private void parseCode(String code, TokenTracker tt) {
		Stemmer s = new Stemmer();
		StopKeywordRemover stkwremover = StopKeywordRemover.getInstance();
		
		//remove non letters
		Matcher m = Pattern.compile("[\\W\\d_]").matcher(code);
		code = m.replaceAll(" ");
		code = splitCamelCase(code);
		//split into individual words
		m = Pattern.compile("\\S+").matcher(code);
		String temp;
		while (m.find()) {
			temp = m.group();
			temp.trim();
			
			//remove stop words
			if (!stkwremover.removeWord(temp)) {
				//stem
				s.add(temp.toLowerCase().toCharArray(), temp.length());
				s.stem();
				temp = s.toString();
				// add to the hashmap
				tt.addCodeToken(temp);
				if (CodeKeyWords.containsKey(temp)) {
					Integer i = CodeKeyWords.get(temp);
					CodeKeyWords.put(temp, ++i);
				} else
					CodeKeyWords.put(temp, 1);
			}
		}

	}
	
	public String splitCamelCase(String s) {
		   return s.replaceAll(
		      String.format("%s|%s|%s",
		         "(?<=[A-Z])(?=[A-Z][a-z])",
		         "(?<=[^A-Z])(?=[A-Z])",
		         "(?<=[A-Za-z])(?=[^A-Za-z])"
		      ),
		      " "
		   );
		}
	
	public static void main(String[] args) {
		RegexIndexer i = new RegexIndexer();
		Scanner fileScanner;
		TokenTracker tt = new TokenTracker();
		try {
			fileScanner = new Scanner(new File("C:\\Users\\Chris\\Desktop\\AddOfficeVisitAction.java"));
			while(fileScanner.hasNext())
			{
				i.buffer += fileScanner.nextLine() + "\n";
			}
			//System.out.println(buffer);
			i.split(i.buffer, tt);
		} catch (FileNotFoundException e) {
				System.out.println(e);
		}	

		Set<String> CodeKeys = i.CodeKeyWords.keySet();
		Set<String> CommentKeys = i.CommentKeyWords.keySet();
		Iterator<String> it = CodeKeys.iterator();
		String temp;
		System.out.println("======CODE======");
		while (it.hasNext())
		{
			temp = it.next();
			System.out.println(temp + " " + i.CodeKeyWords.get(temp));
		}
		it = CommentKeys.iterator();
		System.out.println("======COMMENTS======");
		while (it.hasNext())
		{
			temp = it.next();
			System.out.println(temp + " " + i.CommentKeyWords.get(temp));
		}
	}
}
