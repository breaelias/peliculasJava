package model.streaming.content;

import java.time.Duration;

import model.streaming.player.Video;
/**
 * Esta clase modela un episodio de una serie con sus distintas calidades de video
 */

public class Episode extends Content {
	private int number;
	private Duration duration;
	private Video[] videoQualities;


	/**
	 * Obtiene el número del episodio.
	 * @return El número del episodio.
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Establece el número del episodio.
	 * @param number El nuevo número para el episodio.
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Obtiene el título del episodio.
	 * @return El título del episodio.
	 */
	public String getTitle() {
		return super.getTitle();
	}


	/**
	 * Obtiene la duración del episodio.
	 * @return La duración del episodio.
	 */
	public Duration getDuration() {
		return duration;
	}

	/**
	 * Establece la duración del episodio.
	 * @param duration La nueva duración para el episodio.
	 */
	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public Video[] getVideoQualities() {
		return videoQualities;
	}

	public void setVideoQualities(Video[] videoQualities) {
		this.videoQualities = videoQualities;
	}

}