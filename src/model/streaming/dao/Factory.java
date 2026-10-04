package model.streaming.dao;

import model.streaming.dao.implementation.MovieDAOJDBC;
import model.streaming.dao.implementation.ReviewDAOJDBC;
import model.streaming.dao.implementation.UserDAOJDBC;

public class Factory {

	public static UserDAOJDBC getUserDao() {
		return new UserDAOJDBC();
	}
	
	public static MovieDAOJDBC getMovieDao() {
		return new MovieDAOJDBC();
	}

	public static ReviewDAO getReviewDao() {
		
		return new ReviewDAOJDBC();
	}


}
