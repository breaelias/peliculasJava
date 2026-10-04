package model.streaming.dao;

import java.util.List;

import model.streaming.user.User;

public interface UserDAO {

	

	User findById(int id);

	User findByUsernameAndPassword(String username, String password);

	List<User> findAllByName();

	List<User> findAllByDNI();

	void delete(User user);

	boolean dniExists(int dni);

	void save(User user, String password);

	List<User> findAll();

	public boolean userExists(String username);
	
}
