package Retriever;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import com.mysql.jdbc.Statement;

public class Database {
	
	private final String dbName = "traceability";
	private final String password = "freels";
	private final String userName = "Traceability_app";
	private final String ip = "24.233.212.34:3306";
	private final String driver = "com.mysql.jdbc.Driver";
	//	private final String url = ip;
	private final String url = "jdbc:mysql://" +ip + "/";
	private PreparedStatement statement;
	private final String retrivingQuery = "SELECT generalLink.tokenID, generalLink.DocID, quantity FROM generalLink INNER JOIN token ON generallink.tokenid = token.id WHERE token.data in (%s) ORDER BY generalLink.docID ASC";
	private final String documentNameRetrieve = "SELECT Path FROM Document WHERE id in (%s)";
	private Connection conn = null;
	LinkedList<Document> documents;
	LinkedList<Link> links = new LinkedList<Link>();
	
//	TreeSet<Integer> docIdTracker = new TreeSet<Integer>();
	HashMap<Integer, Token> tokenIdTracker = new HashMap<Integer,Token>();
	
	

    public Database(){
    	connect();
    }
	
    public void getDocumentNames(List<Document> ld){
    	StringBuffer sb = new StringBuffer();
    	for(Document d: ld){
    		sb.append("" + d.id + ", ");
    	}
    	
    	try {
			PreparedStatement st = conn.prepareStatement(String.format(documentNameRetrieve, sb.toString()));
			st.execute();
			ResultSet rs = st.getResultSet();
			int i = 0;
			while(rs.next()){
				ld.get(i).setPath(rs.getNString("Path"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
	public Results retrieve(String[] args){
		
		
		StringBuffer argBuffer = new StringBuffer();
		for(String s:args){
			
			argBuffer.append(s + ",");
		}
		argBuffer.deleteCharAt(argBuffer.length()-1);
		try {
			statement = conn.prepareStatement(String.format(retrivingQuery,argBuffer.toString()));
			statement.execute();
			ResultSet results = statement.getResultSet();

			
			int tokenID;
			int docID;
			int dfi;
			
			Results r = new Results();
			while(results.next()){
				tokenID = results.getInt("tokenId");
				docID = results.getInt("docID");
				dfi = results.getInt("quantity");
				r.addLink(tokenID,docID, dfi);
			}
			return r;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	

	public Token[] getTokens(){
		return null;
	}
	
	public Token getToken(int id){
		if(tokenIdTracker.containsKey(id)){
			return tokenIdTracker.get(id);
		}
		else{
			Token tmp = new Token(id);
			tokenIdTracker.put(id, tmp);
			return tmp;
		}
		
		
	}
	
	public Document getDocument(int id){
		if(documents.peekLast().getId() != id ){
			Document tmp = new Document(id);
			documents.addLast(tmp);
			return tmp;
		}
		else{
			return documents.peekLast();
		}
		
		
	}
	public Document[] getDocuments(){
		return null;
	}
	public Link[] getLinks(){
		return null;
	}
	
	
	
	// Connection Code
	public void connect(){
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url+dbName,userName,password);
			// Do something with the Connection

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
