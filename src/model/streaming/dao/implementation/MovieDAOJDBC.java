package model.streaming.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import model.streaming.content.Movie;
import model.streaming.dao.MovieDAO;
import model.streaming.dao.MyConnection;
import model.streaming.genre.Genre;

public class MovieDAOJDBC implements MovieDAO {

	@Override
	public void save(Movie movie) {
		Connection connection = MyConnection.getCon();
		PreparedStatement statementMovie = null;

		String sqlMovie = "INSERT INTO MOVIE (GENRE, TITLE, DIRECTOR, DURATION, RATING, YEAR, POSTER, SYNOPSIS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			connection.setAutoCommit(false);

			statementMovie = connection.prepareStatement(sqlMovie);
			statementMovie.setString(1, movie.getGenre().name());
			statementMovie.setString(2, movie.getTitle());
			statementMovie.setString(3, movie.getDirector());
			statementMovie.setLong(4, movie.getDuration().toMinutes());
			statementMovie.setDouble(5, movie.getRating());
			statementMovie.setInt(6, movie.getYear());
			statementMovie.setString(7, movie.getPoster());
			statementMovie.setString(8, movie.getOverview());

			int added = statementMovie.executeUpdate();

			if (added == 0) {
				throw new SQLException("No se pudo guardar en MOVIE, ninguna fila afectada.");
			}

			connection.commit(); 

			statementMovie.close();

		} catch (SQLException e) {
			System.out.println("Error al guardar película: " + e.getMessage());
			try {
				connection.rollback(); // si falla hacemos rollback
			} catch (SQLException ex) {
				System.out.println("Error en rollback: " + ex.getMessage());
			}
		} finally {
			try {
				connection.setAutoCommit(true); // activamos el autocommit de nuevo
			} catch (SQLException e) {
				System.out.println("Error al restaurar auto-commit: " + e.getMessage());
			}
		}
	}

	
	@Override         //traer todas las pelis
	public List<Movie> findAll() {
		String sql = "SELECT * FROM MOVIE";
		return executeQuery(sql, -1);
	}

	

	// metodo comun para no repetir codigo entre el findall y el isempty
	private List<Movie> executeQuery(String sql, int parameter) {
		Connection connection = MyConnection.getCon();
		List<Movie> moviesList = new LinkedList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = connection.prepareStatement(sql);

			// Si el método recibe un parámetro (distinto de -1), lo asignamos al SQL
			if (parameter != -1) {
				stmt.setInt(1, parameter);
			}

			rs = stmt.executeQuery();

			while (rs.next()) {
				Movie movie = new Movie();
				movie.setID(rs.getInt("ID"));

				try {
					movie.setGenre(Genre.valueOf(rs.getString("GENRE")));
				} catch (Exception e) {
					movie.setGenre(Genre.DRAMA); // si hay algun genero que no coincida le ponemos drama por defecto
				}

				movie.setTitle(rs.getString("TITLE"));
				movie.setDirector(rs.getString("DIRECTOR"));
				movie.setDuration(Duration.ofMinutes(rs.getLong("DURATION")));
				movie.setRating(rs.getDouble("RATING"));
				movie.setYear(rs.getInt("YEAR"));
				movie.setPoster(rs.getString("POSTER"));
				movie.setSynopsis(rs.getString("SYNOPSIS"));

				moviesList.add(movie);
			}
		} catch (SQLException e) {
			System.out.println("Error consultando base de datos: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.out.println("Error cerrando recursos: " + e.getMessage());
			}
		}
		return moviesList;
	}


	@Override
	public int getMovieIdByTitle(Connection conn, String title) {
		String SQL_MOVIE_ID = "SELECT ID FROM MOVIE WHERE TITLE = ?";
		
		try (PreparedStatement ps = conn.prepareStatement(SQL_MOVIE_ID)) {
			ps.setString(1, title);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("ID");
				}
			}
		} catch (SQLException e) {
			System.out.println("Error buscando ID de película: " + e.getMessage());
		}
		return -1;
	}


	@Override
	public boolean isEmpty() {

		String SQL_MOVIE_ID = "SELECT * FROM MOVIE WHERE ID = 1";

		List<Movie> movieList = executeQuery(SQL_MOVIE_ID, -1);

		if (movieList.isEmpty())
			return true;

		return false;
	}
}