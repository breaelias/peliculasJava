package model.streaming.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import model.streaming.content.Movie;
import model.streaming.dao.MyConnection;
import model.streaming.dao.ReviewDAO;
import model.streaming.review.Review;
import model.streaming.user.CommonUser;

public class ReviewDAOJDBC implements ReviewDAO {

	private static final String sqlReview = "INSERT INTO REVIEW (RATING, COMMENT, DATE, USER_ID, MOVIE_ID) "
			+ "VALUES (?, ?, ?, ?, ?)";

	@Override
	public void save(Review review) {
		Connection connection = MyConnection.getCon();
		PreparedStatement statement = null;
		try {			
			statement = connection.prepareStatement(sqlReview);

			statement.setInt(1, review.getRating());

			statement.setString(2, review.getComment());

			LocalDateTime date = review.getDate();
			statement.setTimestamp(3, Timestamp.valueOf(date));

			statement.setInt(4, review.getAuthor().getID());
			statement.setInt(5, review.getContent().getID());

			if (statement.executeUpdate() > 0)
				System.out.println("Reseña guardada con éxito.");
			else
				System.out.println("Error al guardar la reseña");

		} catch (SQLException e) {
			System.out.println("Error SQL al guardar la reseña: " + e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					System.out.println("Error al cerrar statement: " + e.getMessage());
				}
			}
		}
	}

	@Override
    public boolean exists(int userId, int movieId) {
        Connection connection = MyConnection.getCon();
        String sql = "SELECT ID FROM REVIEW WHERE USER_ID = ? AND MOVIE_ID = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, movieId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // si se cargo alguno ese metdo devuelve true
            }
        } catch (SQLException e) {
            System.out.println("Error verificando reseña: " + e.getMessage());
            return false;
        }
    }
	
	@Override
	public Review findById(int id) {

		return null;
	}

	@Override
	public List<Review> findAll() {

		return null;
	}

	public List<Review> findUnapproved() {
		Connection connection = MyConnection.getCon();
		PreparedStatement statementData = null;

		ResultSet rs = null;
		List<Review> reviewList = new LinkedList<>();
		String sqlReviewSelect = "SELECT REVIEW.ID, REVIEW.RATING, REVIEW.COMMENT, REVIEW.DATE, USER.USERNAME, MOVIE.TITLE FROM REVIEW INNER JOIN USER ON REVIEW.USER_ID = USER.ID INNER JOIN MOVIE ON REVIEW.MOVIE_ID = MOVIE.ID WHERE REVIEW.APPROVED = 0";

		try {
			statementData = connection.prepareStatement(sqlReviewSelect);
			rs = statementData.executeQuery();

			while (rs.next()) {
				Review review = new Review();
				review.setID(rs.getInt("ID"));
				review.setComment(rs.getString("COMMENT"));
				review.setRating(rs.getInt("RATING"));
				review.setDate(rs.getTimestamp("DATE").toLocalDateTime());
				review.setAuthor(new CommonUser());
				review.getAuthor().setUsername(rs.getString("USERNAME"));
				review.setContent(new Movie());
				review.getContent().setTitle(rs.getString("TITLE"));
				reviewList.add(review);
			}

			statementData.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return reviewList;
	}

	public void approve(Review review) {
		Connection connection = MyConnection.getCon();
		PreparedStatement statement = null;
		String sqlReviewSelect = "UPDATE REVIEW SET APPROVED = 1 WHERE ID = ?";
		try {			
			statement = connection.prepareStatement(sqlReviewSelect);
			statement.setInt(1, review.getID());
			
			int x= statement.executeUpdate();
			if(x>0) {
				System.out.println("Reseña aprobada");
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		} finally {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

	}
	
	@Override
    public boolean hasReviews(int userId) {
        Connection connection = MyConnection.getCon();
        String sql = "SELECT COUNT(*) FROM REVIEW WHERE USER_ID = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // si se contaron 1 o mas devuelve true
                }
            }
        } catch (SQLException e) {
            System.out.println("Error verificando historial de usuario: " + e.getMessage());
        }
        return false;
    }

}