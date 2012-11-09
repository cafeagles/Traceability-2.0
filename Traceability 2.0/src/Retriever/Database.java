package Retriever;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

//import com.mysql.jdbc.Statement;

public class Database {

	private final String dbName = "traceability";
	private final String password = "freels";
	private final String userName = "Traceability_app";
	private final String ip = "24.233.212.34:3306";
	private final String driver = "com.mysql.jdbc.Driver";
	private final String url = "jdbc:mysql://" +ip + "/";
	private LinkedList<Document> returnDocs = new LinkedList<Document>();
	private LinkedList<Token> returnTokens = new LinkedList<Token>();
	private HashMap<Integer, Token> tokenMap = new HashMap<Integer, Token>();
	private HashMap<Integer, Document> docMap = new HashMap<Integer, Document>();
	private Document queryDoc;
	private Statement ps;
	//	private final String retrivingQuery = "SELECT generalLink.tokenID, generalLink.DocID, quantity FROM generalLink INNER JOIN token ON generallink.tokenid = token.id WHERE token.data in (%s) ORDER BY generalLink.docID ASC";
	//	private final String documentNameRetrieve = "SELECT Path FROM Document WHERE id in (%s)";
	private final String retrieverQuery = "" +
			"SELECT * " +
			"FROM token " +
			"INNER JOIN generalLink " +
			"ON token.id = generallink.tokenid " +
			"INNER JOIN document " +
			"ON document.id = generallink.docid " +
			"WHERE document.id IN( " +
			"SELECT document.id " +
			"FROM document " +
			"INNER JOIN generallink " +
			"ON document.id = generallink.docid " +
			"INNER JOIN token " +
			"ON generallink.tokenid = token.id " +
			"Where token.data in(?) "+
			")";
	/*
	 * SELECT *
	FROM token
	INNER JOIN generalLink
	ON token.id = generallink.tokenid
	Inner JOIN document
	on document.id = generallink.docid
	WHERE document.id in 
	(
		SELECT document.id 
		FROM document
		INNER JOIN generallink
		on document.id = generallink.docid
		inner join token
		on generallink.tokenid = token.id
		where token.data in ()
	);
	 */

	public static void main (String[] args){
		Database test = new Database();
		Set<String> testWords = new TreeSet<String>();
		testWords.add("doctor");
		testWords.add("medicine");
		test.doSearch(testWords);
		List<Token> outputTokens = test.getTokens();
		List<Document> outputDocuments = test.getDocuments();
		Document query = test.getQuery();

		System.out.println("Documents:");
		if(outputDocuments != null){
			for(Document d: outputDocuments){
				System.out.println(d);
			}
		}
		else{
			System.out.println("No Documents found");
		}
		System.out.println("-----------");

		System.out.println("Tokens:");
		for(Token t: outputTokens){
			System.out.println(t);
		}
	}


	private Connection conn = null;
	//	LinkedList<Document> documents;
	//	LinkedList<Link> links = new LinkedList<Link>();
	//	
	////	TreeSet<Integer> docIdTracker = new TreeSet<Integer>();
	//	HashMap<Integer, Token> tokenIdTracker = new HashMap<Integer,Token>();
	//	


	public Database(){
		connect();
	}

	public List<Document> getDocuments(){
		return returnDocs;
	}
	public List<Token> getTokens(){
		return returnTokens;
	}
	public Document getQuery(){
		return queryDoc;
	}
	public void doSearch(Set<String> args){
		//Construct SQL Query
		//Store keywords of query
		Iterator<String> keywordIterator = args.iterator();
		HashMap<String,Token> queryKeywords = new HashMap<String, Token>();
		StringBuffer sb = new StringBuffer("'");
		String eachKeyword;
		queryDoc = new Document("QUERY: SHOULD NOT BE DISPLAYED");
		//		returnTokens.clear();
		//		returnDocs.clear();
		System.out.println("Database.doSearch: Keywords Recieved:");
		while(keywordIterator.hasNext()){
			eachKeyword = keywordIterator.next();
			System.out.println(eachKeyword);
			queryKeywords.put(eachKeyword,null);
			sb.append(eachKeyword + "','");
		}
		System.out.println("-----");
		sb.delete(sb.length()-3, sb.length()-1);
//		sb.deleteCharAt(sb.length()-1);
//		System.out.println("Arguments for query: |" + sb.toString() + "|");
		//Submit SQL query
		try {
			System.out.println(retrieverQuery.replace("?",sb.toString()));
			ps.execute(retrieverQuery.replace("?",sb.toString()));
			//Get Results
			System.out.println("--- "+ ps +" ----");
			ResultSet rs = ps.getResultSet();
			
			//For each result
			while(rs.next()){
				int tokenId = rs.getInt("token.id");
				int documentId = rs.getInt("document.id");
				String documentName = rs.getString("Document.path");
				String tokenData = rs.getString("token.Data");
				int count = rs.getInt("quantity");
				//	each.Intersection = new Intersection(each.count)
				Intersection eachIntersection = new Intersection(count);
				//	each.token = getToken(TokenID)
				Token eachToken = getToken(tokenId, tokenData);
				//	if this token is in query:
				if(queryKeywords.containsKey(tokenData)){
					queryKeywords.put(tokenData, eachToken);
				}
				// 	each.document = getDocument(DocID)
				Document eachDocument = getDoc(documentId, documentName);
				//	each.token.addIntersection(each.Intersection)
				eachToken.addIntersection(eachIntersection);
				//	each.document.addIntersection(each.Intersection)
				eachDocument.addIntersection(eachIntersection);
			}
			//Build List of Tokens
			for(int id:tokenMap.keySet()){
				returnTokens.add(tokenMap.get(id));
			}
			//Build List of Documents
			for(int id: docMap.keySet()){
				returnDocs.add(docMap.get(id));
			}
			int d = returnDocs.size();

			for(String s: queryKeywords.keySet()){
				Token t = queryKeywords.get(s);
				if(t != null){
					Intersection i = new Intersection(1);
					i.setWeight(Math.log(d/t.getDfi()));
					queryDoc.addIntersection(i);
				}
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}




	private Token getToken(int tokenId, String data) {
		if(tokenMap.containsKey(tokenId)){
			return tokenMap.get(tokenId);
		}
		else{
			Token tmp = new Token(data);
			tokenMap.put(tokenId, tmp);
			return tmp;
		}
	}

	private Document getDoc(int docId, String name){
		if(docMap.containsKey(docId)){
			return docMap.get(docId);
		}
		else{
			Document tmp = new Document(name);
			docMap.put(docId, tmp);
			return tmp;
		}
	}
	// Connection Code
	public void connect(){
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url+dbName,userName,password);
			// Do something with the Connection
			ps = conn.createStatement();
			
		} 
		catch (Exception e) {
			System.out.println("Connection to database failed");
			System.out.println(e.toString());
			// handle any errors

		}

	}

	//Disconnection Code
	public void closeCon(){

		try{
			conn.close();
		}
		catch(Exception e){
			System.out.println(e);
		}

	}
}
