package model.streaming.user;

import java.util.List;

import model.streaming.content.Content;

public class AdminUser extends User {

	public AdminUser(String plan, String name,String surname,String username, int DNI, String email, String language, List<String> preference) {
		super(plan, name,surname, username, DNI, email, language, preference);
	}
	
	public void addContent(Content c) {
		
	}
	
	public void removeContent(Content c) {
		
	}
	
}
