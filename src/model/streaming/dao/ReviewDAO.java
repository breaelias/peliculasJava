package model.streaming.dao;
import java.util.List;

import model.streaming.review.Review; 

public interface ReviewDAO {

	void save(Review review);
	
	Review findById(int id);
	
	List<Review> findAll();

	List<Review> findUnapproved();

	void approve(Review review);

	boolean exists(int userId, int movieId); 
	
	boolean hasReviews(int userId);
}
