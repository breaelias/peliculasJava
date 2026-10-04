package model.streaming.user;

import java.util.List;

public class CommonUser extends User {

	public CommonUser(String plan, String name,String surname,String username, int DNI, String email, String language, List<String> preference) {
		super(plan, name,surname,username, DNI, email, language, preference);
	}

	public CommonUser() {
		// TODO Auto-generated constructor stub
	}
	
	
}
