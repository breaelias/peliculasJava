package model.streaming.app;

import model.streaming.controller.LoginController;
import model.streaming.dao.MyConnection;


public class Main {
	public static void main(String[] args) {
		try {
			DBManager.createDBTables(MyConnection.getCon());
		} catch (Exception e) {
			e.printStackTrace();
		}
			new LoginController();
		}		
	}
	


