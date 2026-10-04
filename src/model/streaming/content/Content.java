package model.streaming.content;

import java.util.List;

import model.streaming.genre.Genre;

/**
 * Esta clase abstracta modela un contenido general
 */
public abstract class Content {
	private int ID;
	private String title;
	private Genre genre;
	private List<String> audioLanguages;
	private List<String> subtitles;
	private String director;
	private double rating;
	private int year;
	private String poster;
	
	public Content() {
		
	}
	/**
	 * Constructor para crear un contenido con título y duración.
	 * 
	 * @param title          El título del contenido.
	 * @param genres         La lista de géneros del contenido.
	 * @param audioLanguages La lista de idiomas de audio disponibles.
	 * @param subtitles      La lista de idiomas de subtítulos disponibles.
	 * @param director       El director del contenido.
	 */
	public Content(int iD, String title, Genre genre, List<String> subtitles, String director, double rating, int year,
			String poster) {
		super();
		ID = iD;
		this.title = title;
		this.genre = genre;
		this.subtitles = subtitles;
		this.director = director;
		this.rating = rating;
		this.year = year;
		this.poster = poster;
	}
	

	/**
	 * Obtiene el título del contenido.
	 * 
	 * @return El título del contenido.
	 */
	public String getTitle() {
		return title;
	}

	
	
	/**
	 * Obtiene la duración del contenido en minutos.
	 * 
	 * @return La duración en minutos.
	 */

	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	/**
	 * Obtiene la lista de géneros asociados al contenido.
	 * 
	 * @return Una lista de géneros.
	 */
	public Genre getGenre() {
		return genre;
	}

	/**
	 * Establece la lista de géneros para el contenido.
	 * 
	 * @param genres La nueva lista de géneros.
	 */
	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	/**
	 * Obtiene la lista de idiomas de audio disponibles.
	 * 
	 * @return Una lista de idiomas.
	 */
	public List<String> getAudioLanguages() {
		return audioLanguages;
	}

	/**
	 * Establece la lista de idiomas de audio para el contenido.
	 * 
	 * @param languages La nueva lista de idiomas.
	 */
	public void setLanguages(List<String> languages) {
		this.audioLanguages = languages;
	}

	/**
	 * Obtiene la lista de idiomas de subtítulos disponibles.
	 * 
	 * @return Una lista de idiomas de subtítulos.
	 */
	public List<String> getSubtitles() {
		return subtitles;
	}

	/**
	 * Establece la lista de idiomas de subtítulos para el contenido.
	 * 
	 * @param subtitles La nueva lista de idiomas de subtítulos.
	 */
	public void setSubtitles(List<String> subtitles) {
		this.subtitles = subtitles;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}
	public void setTitle(String title) {
		this.title=title;
	}

	public String toString() {
		return "\n Titulo: "+title +"\n Director: "+director+ "\n Genero: "+ genre;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
}