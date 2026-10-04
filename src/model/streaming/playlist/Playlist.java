package model.streaming.playlist;

import java.util.ArrayList;
import java.util.List;

import model.streaming.content.Content;

/**
 * Esta clase modela una playlist creada por un usuario
 */
public class Playlist {
	private String name;
	private List<Content> contents = new ArrayList<>();

	/**
	 * Constructor para crear una nueva playlist.
	 * @param name El nombre de la playlist.
	 */
	public Playlist(String name) {
		this.name = name;
	}

	/**
	 * Agrega un contenido a la playlist.
	 * @param c El contenido a agregar.
	 */
	public void addContent(Content c) {
		if (c != null)
			contents.add(c);
	}

	/**
	 * Elimina un contenido de la playlist.
	 * @param c El contenido a eliminar.
	 */
	public void removeContent(Content c) {
		contents.remove(c);
	}

	/**
	 * Obtiene una copia de la lista de contenidos de la playlist.
	 * @return Una nueva lista con los contenidos de la playlist.
	 */
	public List<Content> getContents() {
		return new ArrayList<>(contents);
	}

	/**
	 * Obtiene el nombre de la playlist.
	 * @return El nombre de la playlist.
	 */
	public String getName() {
		return name;
	}

}