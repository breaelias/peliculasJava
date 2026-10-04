package model.streaming.player;

/**
 * Esta clase modela el audio en un determinado idioma
 */
public class AudioTrack {
	private String language;
	private String url;

	/**
	 * Constructor para crear una nueva pista de audio.
	 * @param language El idioma de la pista de audio.
	 * @param url La URL del archivo de audio.
	 */
	public AudioTrack(String language, String url) {
		this.language = language;
		this.url = url;
	}

	/**
	 * Obtiene el idioma de la pista de audio.
	 * @return El idioma de la pista de audio.
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Obtiene la URL del archivo de la pista de audio.
	 * @return La URL del archivo de audio.
	 */
	public String getUrl() {
		return url;
	}

}