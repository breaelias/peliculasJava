package model.streaming.dao;

import java.sql.Connection;
import java.util.List;

import model.streaming.content.Movie; 

/**
 * Interfaz DAO para la entidad Movie.
 */
public interface MovieDAO {

    /**
     * Guarda una nueva película en la base de datos.
     * @param movie La película a guardar.
     */
    void save(Movie movie);


    /**
     * Devuelve una lista de todas las películas en la base de datos.
     * @return Una lista con todas las películas.
     */
    List<Movie> findAll();
    
    int getMovieIdByTitle(Connection conn, String title);

    
	boolean isEmpty();
   }