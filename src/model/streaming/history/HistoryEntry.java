package model.streaming.history;

import java.time.Duration;
import java.time.LocalDateTime;

import model.streaming.content.Content;
/**
 * Esta clase modela el historial de un contenido específico
 */
public class HistoryEntry {
	private Content content;
	private int lastPositionSec;
	private Duration watchedDuration;
	private LocalDateTime viewedAt;

	/**
	 * Constructor para crear una nueva entrada en el historial.
	 * @param content El contenido que fue visto.
	 * @param lastPositionSec El último segundo de reproducción.
	 * @param watchedDuration La duración total acumulada de visualización.
	 * @param viewedAt La fecha y hora de la última visualización.
	 */
	public HistoryEntry(Content content, int lastPositionSec, Duration watchedDuration, LocalDateTime viewedAt) {
		this.setContent(content);
		this.watchedDuration = watchedDuration;
		this.viewedAt = viewedAt;
	}

	/**
	 * Obtiene el último segundo en el que se detuvo la reproducción.
	 * @return El segundo de la última posición como un entero.
	 */
	public int getLastPostionSec() {
		return lastPositionSec;
	}

	/**
	 * Obtiene la fecha y hora de la última vez que se vio el contenido.
	 * @return La fecha y hora de la última visualización.
	 */
	public LocalDateTime getViewedAt() {
		return viewedAt;
	}

	/**
	 * Obtiene la duración total de tiempo que el contenido ha sido visto.
	 * @return La duración de visualización acumulada.
	 */
	public Duration getWatchedDuration() {
		return watchedDuration;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}
}