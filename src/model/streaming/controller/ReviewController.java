package model.streaming.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.streaming.content.Movie;
import model.streaming.dao.Factory; 
import model.streaming.dao.ReviewDAO;
import model.streaming.review.Review;
import model.streaming.user.User;
import model.streaming.view.ReviewDialog;

public class ReviewController {

    private ReviewDialog view;
    private User user;
    private Movie movie;
    private DashboardController dashboardController;
    private ReviewDAO reviewDAO;
    private JFrame parentFrame;

    public ReviewController(JFrame parent, User user, Movie movie, DashboardController dashboardCtrl) {
        this.parentFrame = parent;
        this.user = user;
        this.movie = movie;
        this.dashboardController = dashboardCtrl;
        
        this.reviewDAO = Factory.getReviewDao();
        this.view = new ReviewDialog(parent, movie.getTitle());

        this.view.addSaveListener(new SaveListener());

        this.view.setVisible(true);
    }

    private class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int rating = view.getRating();          
                String comment = view.getComment(); 

                Review review = new Review();
                review.setAuthor(user);
                review.setContent(movie);
                review.setRating(rating);
                review.setComment(comment); 
                review.setDate(LocalDateTime.now()); 

                reviewDAO.save(review);
                
                JOptionPane.showMessageDialog(parentFrame, "Reseña guardada exitosamente.");
                view.dispose();
                
                dashboardController.updateTableRowToRated(movie.getTitle());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentFrame, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}