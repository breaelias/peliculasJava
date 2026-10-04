package model.streaming.app;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	
	
	static void createDBTables(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		String sql;

		sql = " CREATE TABLE IF NOT EXISTS PERSONALDATA (" 
				+ "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " 
				+ "NAME TEXT(100) NOT NULL, " 
				+ "SURNAME TEXT(100) NOT NULL, " 
				+ "DNI INTEGER NOT NULL" 
				+ ");";
		stmt.executeUpdate(sql);

			sql = " CREATE TABLE IF NOT EXISTS MOVIE (" 
	            + "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " 
	            + "GENRE TEXT(100), " 
	            + "TITLE TEXT(100) NOT NULL, " 
	            + "DIRECTOR TEXT(100), "       
	            + "DURATION INTEGER, "         
	            + "RATING REAL, "             
	            + "YEAR INTEGER, "            
	            + "POSTER TEXT(200), "        
	            + "SYNOPSIS TEXT(1000)"       
	            + ");"; 
	    stmt.executeUpdate(sql);

		sql = " CREATE TABLE IF NOT EXISTS USER (" 
				+ "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " 
				+ "USERNAME TEXT NOT NULL, " 
				+ "EMAIL TEXT NOT NULL, "
				+ "PASSWORD TEXT NOT NULL, " 
				+ "PERSONALDATA_ID INTEGER NOT NULL, "
				+ "CONSTRAINT USER_PERSONALDATA_FK FOREIGN KEY (PERSONALDATA_ID) REFERENCES PERSONALDATA(ID)" 
				+ ");";
		stmt.executeUpdate(sql);

		sql = " CREATE TABLE IF NOT EXISTS REVIEW (" 
				+ "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " 
				+ "RATING INTEGER NOT NULL, " 
				+ "COMMENT TEXT(500), " 
				+ "APPROVED INTEGER DEFAULT (0) NOT NULL, " 
				+ "DATE DATETIME NOT NULL, " 
				+ "USER_ID INTEGER NOT NULL, " 
				+ "MOVIE_ID INTEGER NOT NULL, "
				+ "CONSTRAINT USER_REVIEW_FK FOREIGN KEY (USER_ID) REFERENCES USER(ID), "
				+ "CONSTRAINT MOVIE_REVIEW_FK FOREIGN KEY (MOVIE_ID) REFERENCES MOVIE(ID)" 
				+ ");";
		stmt.executeUpdate(sql);
		
		stmt.close();
	}

}
