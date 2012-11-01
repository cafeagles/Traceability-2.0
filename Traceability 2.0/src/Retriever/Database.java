package Retriever;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
	
	private final String dbName = "traceability";
	private final String password = "freels";
	private final String userName = "traceability_app";
	private final String ip = "24.233.212.34:3306";
	private final String driver = "com.mysql.jdbc.Driver";
	//	private final String url = ip;
	private final String url = "jdbc:mysql://" +ip + "/";

	private Connection conn = null;
	
	

    
	
	public Database(String[] args){
		
	}
	
	public Token[] getTokens(){
		return null;
	}
	public Document[] getDocuments(){
		return null;
	}
	public Link[] getLinks(){
		return null;
	}
	
	
	
	// Connection Code
	public void Connect(){
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
