package Retriever;
import java.sql.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Database {
    
        private final String dbName = "traceability";
	private final String password = "ab1234";
	private final String userName = "root";
	private final String ip = "localhost:3306";
	private final String driver = "com.mysql.jdbc.Driver";
	//private final String url = ip;
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
}
