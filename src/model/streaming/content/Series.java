package model.streaming.content;

import java.util.ArrayList;
import java.util.List;


/**
 * Esta clase modela una serie con sus temporadas
 */

public class Series extends Content {
	private List<Season> seasons = new ArrayList<>();
	private int seasonsNum;

	

	/**
	 * Agrega una temporada a la lista de temporadas de la serie.
	 * 
	 * @param season La temporada a agregar.
	 */
	public void addSeason(Season season) {
		seasons.add(season);
	}

	/**
	 * Obtiene la lista de temporadas de la serie.
	 * 
	 * @return Una lista de temporadas.
	 */
	public List<Season> getSeasons() {
		return seasons;
	}

	/**
	 * Obtiene el número total de temporadas de la serie.
	 * 
	 * @return El número de temporadas.
	 */
	public int getSeasonsNum() {
		return seasonsNum;
	}
}