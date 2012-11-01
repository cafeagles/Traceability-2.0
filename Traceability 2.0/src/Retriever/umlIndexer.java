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

	// constructor takes a file name as a string
	public umlIndexer(String filename) {

		String temp;
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(new File(filename));
			while (fileScanner.hasNext()) {
				buffer += fileScanner.nextLine() + "\n";
			}
			// System.out.println(buffer);
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		umlParse();
	}

	/*************************************
	 * parses the whole uml document and adds keywords to the hashmap
	 ************************************/
	public void umlParse() {
		Stemmer s = new Stemmer();
		StopKeywordRemover stkwremover = StopKeywordRemover.getInstance();

		Pattern pattern = Pattern.compile("\\[.*?\\]");// [*anything*]
		Matcher matcher = pattern.matcher(buffer);
		buffer = matcher.replaceAll("");

		Matcher m = Pattern.compile("[\\W\\d_]").matcher(buffer);// non word and
																// digits
		buffer = m.replaceAll(" ");
		m = Pattern.compile("\\S+").matcher(buffer);// non spaces
		String temp;
		while (m.find()) {
			temp = (m.group().toLowerCase()).trim();
			if (!stkwremover.removeWord(temp)) {
				s.add(temp.toCharArray(), temp.length());
				s.stem();
				temp = s.toString();
				// add to the hashmap if it is not a stopword
				if (keywords.containsKey(temp)) {
					Integer i = keywords.get(temp);
					keywords.put(temp, ++i);
				} else
					keywords.put(temp, 1);
			}
		}

	}

	public Set<String> getKeySet() {
		return keywords.keySet();
	}

	public HashMap<String, Integer> getKeywordMap() {
		return keywords;
	}

	public static void main(String[] args) {
		System.out.println("Start\n");
		umlIndexer uml = new umlIndexer("C:\\Users\\Chris\\Desktop\\uml.txt");
		HashMap<String, Integer> keywords = uml.getKeywordMap();
		Set<String> keyset = uml.getKeySet();
		Iterator<String> it = keyset.iterator();
		String temp;
		while (it.hasNext()) {
			temp = it.next();
			System.out.println(temp + " " + keywords.get(temp));
		}
		// System.out.println(uml.buffer);
		System.out.println("End\n");
	}
}
