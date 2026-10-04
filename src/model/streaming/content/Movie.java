package model.streaming.content;

import java.time.Duration;
import java.util.List;

import model.streaming.genre.Genre;
import model.streaming.player.Video;

/**
 * Esta clase se utiliza para modelar las peliculas
 */
public class Movie extends Content {
	private Duration duration;
	@SuppressWarnings("unused")
	private Video[] videoQualities;
	private String Overview;
	private int voteCount;
	private String poster;
	/**
	 * Constructor para crear una nueva película
	 * 
	 * @param title          El título de la película.
	 * @param genres         La lista de géneros de la película.
	 * @param languages      La lista de idiomas de audio disponibles.
	 * @param subtitles      La lista de idiomas de subtítulos disponibles.
	 * @param director       El director de la película.
	 * @param duration       La duración precisa del video.
	 * @param videoQualities Un arreglo de objetos Video con las diferentes calidades disponibles.
	 */
	public Movie(int id, String title, Genre genre, List<String> subtitles, String director,
			Duration duration, Video[] videoQualities, double rating, int year, String poster) {
		super(id,title, genre, subtitles, director,rating,year,poster);
		this.duration = duration;
		this.videoQualities = videoQualities;
	}

	public Movie() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Obtiene la duración precisa de la película.
	 * 
	 * @return La duración de la película.
	 */
	public Duration getDuration() {
		return duration;
	}
	
	/**
	 * Establece la duración precisa de la película.
	 * 
	 * @param duration La nueva duración para la película.
	 */
	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public void setTitle(String title) {
		super.setTitle(title);
	}
	
	public String toString() {
		return super.toString() + "\n Duracion: "+duration.toMinutes();
	}

	public String getOverview() {
		return Overview;
	}

	public void setSynopsis (String synopsis) {
		Overview = synopsis;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	
}