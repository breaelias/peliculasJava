package model.streaming.content;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase modela una temporada de una determinada serie
 */
public class Season {
	private int number;
	private List<Episode> episodes = new ArrayList<>();

	/**
	 * Constructor para crear una nueva temporada.
	 * @param number El número de la temporada.
	 */
	public Season(int number) {
		this.number = number;
	}

	/**
	 * Agrega un episodio a la lista de episodios de la temporada.
	 * @param e El episodio a agregar.
	 */
	public void addEpisode(Episode e) {
		episodes.add(e);

	}

	/**
	 * Obtiene la lista de episodios de esta temporada.
	 * @return Una lista de episodios.
	 */
	public List<Episode> getEpisodes() {
		return episodes;
	}

	/**
	 * Obtiene el número de la temporada.
	 * @return El número de la temporada.
	 */
	public int getNumber() {
		return number;
	}

}