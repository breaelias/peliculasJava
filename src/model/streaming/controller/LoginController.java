
package model.streaming.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.streaming.dao.Factory; 
import model.streaming.dao.MovieDAO;
import model.streaming.dao.ReviewDAO;
import model.streaming.dao.UserDAO;
import model.streaming.content.Movie;
import model.streaming.exception.EmptyFieldException;
import model.streaming.exception.InvalidCredentialsException;
import model.streaming.user.User;
import model.streaming.util.CSVUtil;
import model.streaming.content.MovieComparator.*;
import model.streaming.view.LoadingView;
import model.streaming.view.LoginView;

public class LoginController {

	private LoginView view;
	private LoadingView loadingView;

	private UserDAO userDAO;
	private MovieDAO movieDAO;
	private ReviewDAO reviewDAO;

	public LoginController() {
		this.view = new LoginView();

		this.userDAO = Factory.getUserDao();
		this.movieDAO = Factory.getMovieDao();
		this.reviewDAO = Factory.getReviewDao();

		this.view.getBtnIngresar().addActionListener(new LoginListener());
		this.view.getBtnRegistrarse().addActionListener(new RegisterListener());

		this.view.setVisible(true);
	}

	private void handleLogin() {
		try {
			String username = view.getUsername();
			String password = view.getPass();

			if (username.trim().isEmpty())
				throw new EmptyFieldException("Usuario");
			if (password.trim().isEmpty())
				throw new EmptyFieldException("Contraseña");

			User user = userDAO.findByUsernameAndPassword(username, password);

			if (user == null)
				throw new InvalidCredentialsException("Usuario o contraseña incorrectos.");

			view.setVisible(false);
			determineDataFlow(user);

		} catch (Exception e) {
			view.mostrarMensaje("Error: " + e.getMessage());
			view.setVisible(true);
		}
	}

	//aca se obtienen datos para saber si hay que cargar la base de datos o no, y tambien si hay que mostrar el
	//top 10 peliculas o randoms y se manda a buscar al csv
	private void determineDataFlow(User user) {
		boolean isDbPopulated = !movieDAO.isEmpty();

		boolean hasHistory = reviewDAO.hasReviews(user.getID());

		startDataLoading(user, isDbPopulated, hasHistory);
	}

	
	private void startDataLoading(User user, boolean isDbPopulated, boolean hasHistory) {

		DataLoadTask task = new DataLoadTask(user, isDbPopulated, hasHistory);
		Thread worker = new Thread(task);
		worker.start();
		loadingView = new LoadingView(view);
		loadingView.setVisible(true);
	}

	private void openRegister() {
		new RegisterController(view);
	}

	private List<Movie> getTop10ByRating(List<Movie> movies) {
		Collections.sort(movies, new CompareByRating());

		return new ArrayList<>(movies.subList(0, 10));
	}

	private List<Movie> getRandomMovies(List<Movie> movies) {
		Collections.shuffle(movies);
		return new ArrayList<>(movies.subList(0, 10));
	}

	private class LoginListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			handleLogin();
		}
	}

	private class RegisterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			openRegister();
		}
	}

	// clase para crear un hilo aprte que cargue las peliculas
	private class DataLoadTask implements Runnable {
		private User user;
		private boolean notDBEmpty;
		private boolean hasHistory;

		public DataLoadTask(User user, boolean notDBEmpty, boolean hasHistory) {
			this.user = user;
			this.notDBEmpty = notDBEmpty;
			this.hasHistory = hasHistory;
		}

		@Override
		public void run() {
			List<Movie> loadedMovies = null;
			try {
				loadedMovies = CSVUtil.loadMoviesFromCSV("/csv/movies_database.csv", notDBEmpty);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				List<Movie> finalMovieList;

				if (loadedMovies != null && !loadedMovies.isEmpty()) {
					if (!hasHistory) {
						finalMovieList = getTop10ByRating(loadedMovies);
					} else {
						finalMovieList = getRandomMovies(loadedMovies);
					}
				} else {
					finalMovieList = new ArrayList<>();
				}

				loadingView.dispose();
				view.dispose();

				new DashboardController(user, finalMovieList, hasHistory);
			}
		}
	}
}