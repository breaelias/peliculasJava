package model.streaming.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

	private static Connection con = null;

	private MyConnection() {
	}

	static {
		try {
			con = DriverManager.getConnection("jdbc:sqlite:DB.db");
		} catch (java.sql.SQLException e) {
			System.out.println("Error de SQL: " + e.getMessage());
		}
	}

	public static Connection getCon() {
		return con;
	}

	
	public static void closeCon() {
		if (con!=null) {
			try {
				con.close();
			}catch(SQLException e) {
				System.out.println("error al cerrar la conexión base de datos: "+ e.getMessage());
			}
		}
	}
}
