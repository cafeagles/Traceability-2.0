package Retriever;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.StringBuffer;
import java.io.*;
import Indexer.StopKeywordRemover;
import Indexer.Stemmer;


public class umlIndexer {

	private HashMap<String, Integer> keywords = new HashMap<String, Integer>();
	private String buffer = new String();
	
	public umlIndexer(String filename){
	
		String temp;
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(new File(filename));
			while(fileScanner.hasNext())
			{
				buffer += fileScanner.nextLine();
			}
			System.out.println(buffer);
		} catch (FileNotFoundException e) {
				System.out.println(e);
		}
		umlParse();
	}
	
	public void umlParse(){
		Stemmer s = new Stemmer();
		StopKeywordRemover stkwremover = StopKeywordRemover.getInstance();
		
		Pattern pattern =  Pattern.compile("\\[.*?\\]");//(?![@',&])
		Matcher matcher = pattern.matcher(buffer);
		 buffer = matcher.replaceAll("");
		 //System.out.print(buffer);System.out.print("\nDONE2\n");
		 
		 Matcher m = Pattern.compile("[\\W\\d]").matcher(buffer);
		 buffer = m.replaceAll(" ");
		 m = Pattern.compile("\\S+").matcher(buffer);
		 String temp;
		 while (m.find()) {
			 temp = (m.group().toLowerCase()).trim();
			 if(!stkwremover.removeWord(temp))
			 {
				 s.add(temp.toCharArray(), temp.length());
				 s.stem();
				 temp = s.toString();
				 if(keywords.containsKey(temp))
					{	Integer i = keywords.get(temp);
						keywords.put(temp,++i);
					}
					else
						keywords.put(temp,1);
//				 System.out.println(temp);
			 }
		 }
		 
	}
	
	public Set<String> getKeySet()
	{
		return keywords.keySet();
	}
	public HashMap<String, Integer> getKeywordMap()
	{
		return keywords;
	}
	
	
	public static void main(String[] args)
	{
		System.out.println("Start\n");
		umlIndexer uml = new umlIndexer("C:\\Users\\Chris\\Desktop\\uml.txt");
		HashMap<String, Integer> keywords = uml.getKeywordMap();
		Set<String> keyset = uml.getKeySet();
		Iterator<String> it = keyset.iterator();
		String temp;
		while (it.hasNext())
		{
			temp = it.next();
			System.out.println(temp + " " + keywords.get(temp));
		}
//		System.out.println(uml.buffer);
		System.out.println("End\n");
	}
}

