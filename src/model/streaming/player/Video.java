package model.streaming.player;

 /**
  * Esta clase modela un video con su determinada calidad
  */

public class Video {
	private String quality;
	private String URL;

	/**
	 * Obtiene la calidad del video.
	 * @return Un String que representa la calidad (ej. "1080p", "720p").
	 */
	public String getQuality() {
		return quality;
	}

	/**
	 * Establece la calidad del video.
	 * @param quality El nuevo valor para la calidad del video.
	 */
	public void setQuality(String quality) {
		this.quality = quality;
	}

	/**
	 * Obtiene la URL del archivo de video.
	 * @return La URL del video.
	 */
	public String getURL() {
		return URL;
	}

}