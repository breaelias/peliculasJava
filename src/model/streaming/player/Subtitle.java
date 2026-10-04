package model.streaming.player;

/**
 * Esta clase modela los subtitulos en un determinado idioma
 */

public class Subtitle {
	private String language;
	private String url;


	public Subtitle(String language, String url) {
		this.language = language;
		this.url = url;
	}

	/**
	 * Obtiene el idioma de los subtítulos.
	 * @return El idioma de los subtítulos.
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Obtiene la URL del archivo de subtítulos.
	 * @return La URL de los subtítulos.
	 */
	public String getUrl() {
		return url;
	}
}