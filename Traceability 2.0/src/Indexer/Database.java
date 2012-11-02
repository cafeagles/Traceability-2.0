package Indexer;


import java.util.List;
import java.sql.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;


public class Database {
	private final String dbName = "traceability";
	private final String password = "chris";
	private final String userName = "traceablility";
	private final String ip = "24.233.212.34:3306";
	private final String driver = "com.mysql.jdbc.Driver";
	//	private final String url = ip;
	private final String url = "jdbc:mysql://" +ip + "/";

	private Connection conn = null;
	PreparedStatement tokenStatement;
	PreparedStatement docStatement;
	PreparedStatement linkStatement;
	
	
	List<String> docsToBeStored = new LinkedList<String>();
	HashSet<String> tokensToBeStored = new HashSet<String>();
	List<String[]> linksToBeStored = new LinkedList<String[]>();

	private final String addTokenQuery = "INSERT token (data) VALUES (?) ;"; // Data
	private final String addLinkQuery = "INSERT INTO link (TokenID,DocID, Quantity, SourceType) VALUES ((SELECT ID FROM token WHERE data=?),(SELECT ID FROM document WHERE path=?) ,?, ?); ";
	private final String addDocQuery = "INSERT INTO document(path) VALUES (?);";

	//singleton code
	private static Database db = null;

	//getInstance() is how you access the Database
	public static Database getInstance(){
		if(db==null){
			db = new Database();
			return db;
		}
		else
		{
			return db;
		}

	}


	// constructor
	protected Database(){
		openConnect();

	}


	public void buildQuery(){

	} 

	private void submitQuery(String statement){
		try{
//		System.out.println(statement);
		Statement stmt = conn.createStatement();
        int rs = stmt.executeUpdate(statement);
		}
		catch(Exception e){
			System.out.println(e);
//			System.out.println("Query: " + statement);
//			System.exit(1);
		}


	}
	
	public void storeTokens(TokenTracker tt, String docName){
		docsToBeStored.add(docName);
		String[] link = new String[4];
		
		for(String codeToken: tt.getCodeKeys()){
			link = new String[4];
			tokensToBeStored.add(codeToken);
			link[0] = codeToken;
			link[1] = docName;
			link[2] = Integer.toString(tt.getCodeTokCount(codeToken));
			link[3] = "CODE";
			linksToBeStored.add(link);
		}
		

		
		for(String commentToken:tt.getCommentKeys()){
			link = new String[4];
			tokensToBeStored.add(commentToken);
			link[0] = commentToken;
			link[1] = docName;
			link[2] = Integer.toString(tt.getCommentTokCount(commentToken));
			link[3] = "COMMENT";
			linksToBeStored.add(link);
		}
	}

//	public void storeTokens(TokenTracker tt, String docName){
//		submitQuery(String.format(addDocQuery,docName));
//		
//		Set<String> codeTokens = tt.getCodeKeys();
//		Set<String> commentTokens = tt.getCommentKeys();
//
////		HashMap<String, Boolean> tokenStored = new HashMap<String, Boolean>();
//
//		Iterator<String> codeIterator = codeTokens.iterator();
//		Iterator<String> commentIterator = commentTokens.iterator(); 
//
//		StringBuffer queryBuffer = new StringBuffer();
//
//		String eachTokenInsertQuery;
//		String eachToken;
//		String eachTokenCount;
//		String eachLinkInsertQuery;
//
//		while(codeIterator.hasNext()){
//			eachToken = codeIterator.next();
//			eachTokenCount = Integer.toString(tt.getCodeTokCount(eachToken));
//
//			
//			//submitQuery(String.format(addTokenQuery, eachToken) + String.format(addLinkQuery, eachToken,docName,eachTokenCount,"CODE"));
//			
////			submitQuery(String.format(addTokenQuery, eachToken));
////			submitQuery(String.format(addLinkQuery, eachToken,docName,eachTokenCount,"CODE"));
//			
//			eachTokenInsertQuery = String.format(addTokenQuery, eachToken);
//			queryBuffer.append(eachTokenInsertQuery);
//
//			eachLinkInsertQuery = String.format(addLinkQuery, eachToken,docName,eachTokenCount,"CODE");
//			queryBuffer.append(eachLinkInsertQuery);
//			
////			System.out.println(eachTokenInsertQuery.toString());
//			submitQuery(eachTokenInsertQuery.toString());
//			submitQuery(eachLinkInsertQuery.toString());
////			tokenStored.put(eachToken,true);
//
//		}
//
//		while(commentIterator.hasNext()){
//			
//			eachToken = commentIterator.next();
//			eachTokenCount = Integer.toString(tt.getCommentTokCount(eachToken));
//
//			
//			//submitQuery(String.format(addTokenQuery, eachToken) + String.format(addLinkQuery, eachToken,docName,eachTokenCount,"COMMENT"));
//
//			
//			submitQuery(String.format(addTokenQuery, eachToken));
//			submitQuery(String.format(addLinkQuery, eachToken,docName,eachTokenCount,"COMMENT"));
////			if(!tokenStored.containsKey(eachToken)){
////				eachTokenInsertQuery = String.format(addTokenQuery, commentIterator.next());
////				System.out.println(eachTokenInsertQuery.toString());
////				submitQuery(eachTokenInsertQuery.toString());
//////				queryBuffer.append(eachTokenInsertQuery);
////			}
//
////			eachLinkInsertQuery = String.format(addLinkQuery, eachToken,docName,eachTokenCount,"COMMENT");
////			queryBuffer.append(eachLinkInsertQuery);
//			
//			
//			
//		}
//		//once its done building it submits the Query
//
//	}



	//-------------------------------------------------------------

	public void openConnect(){
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url+dbName,userName,password);
			tokenStatement = conn.prepareStatement(addTokenQuery);
			docStatement = conn.prepareStatement(addDocQuery);
			linkStatement = conn.prepareStatement(addLinkQuery);
			// Do something with the Connection

		} 
		catch (Exception e) {
			System.out.println("Connection to database failed");
			System.out.println(e.toString());
			// handle any errors
			closeConnect();
			System.exit(1);

		}

	}


	public void closeConnect(){
		if(db!=null){
			try{
				conn.close();
				db = null;

			}
			catch(Exception e){

			}
		}
	}


	public void finish() {
		Iterator<String> tokenIterator = tokensToBeStored.iterator();
		Iterator<String> docIterator = docsToBeStored.iterator();
		Iterator<String[]> linkIterator = linksToBeStored.iterator();
		String statement;
		
		while(tokenIterator.hasNext()){
			try {
				
				tokenStatement.setString(1, tokenIterator.next());
				tokenStatement.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Failed to send token sql statement.");
				System.out.println(e);
//				e.printStackTrace();
//				System.exit(1);
			}
		}
		
		
		while(docIterator.hasNext()){
			try {
				docStatement.setString(1, docIterator.next());
				docStatement.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Failed to send Document sql statement.");
				System.out.println(e);
//				e.printStackTrace();
//				System.exit(1);
			}
		}
	
		while(linkIterator.hasNext()){
			String thisLink[] = linkIterator.next();
			try {
				linkStatement.setString(1, thisLink[0]);
				linkStatement.setString(2, thisLink[1]);
				linkStatement.setString(3, thisLink[2]);
				linkStatement.setString(4, thisLink[3]);
				linkStatement.execute();
			} catch (SQLException e) {
				System.out.println("Failed to send link sql statement.");
				System.out.println(e);
//				e.printStackTrace();
//				System.exit(1);
			}
		}
		
		closeConnect();
		
	}
}
