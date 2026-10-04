package model.streaming.user;

import java.util.ArrayList;
import java.util.List;

import model.streaming.content.Content;
//import model.streaming.history.ViewingHistory;
import model.streaming.playlist.Playlist;

/**
 * Esta clase modela los datos en comun entre usuarios y administradores
 * 
 * @author Mateo-Elias
 * @version 0.6
 * @since 2025-09-15
 */

public abstract class User {
	private int ID;
	private String plan;
//  private ViewingHistory viewingHistory = new ViewingHistory();
	private List<Playlist> playlists = new ArrayList<>();
	private String name;
	private String surname;
	private String username;
	private int DNI;
	private String email;
	private String language;
	private List<String> preference;

	/**
	 * Constructor para la clase User.
	 * 
	 * @param plan       El plan al que está subscripto del usuario.
	 * @param name       El nombre del usuario.
	 * @param surname  	 El apellido del usuario
	 * @param DNI		 El DNI del usuario
	 * @param email      El email del usuario.
	 * @param language   El lenguaje selecionado del usuario
	 * @param preference Una lista con las preferencias
	 */

	public User() {}
	
	public User(String plan, String name,String surname, String username,int DNI, String email, String language, List<String> preference) {
		super();
		this.plan = plan;
		this.name = name;
		this.surname = surname;
		this.setUsername(username);
		this.DNI = DNI;
		this.email = email;
		this.language = language;
		this.preference = preference;
	}

	/**
	 * Obtiene el nombre del usuario.
	 * 
	 * @return El nombre del usuario.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Establece el nombre del usuario.
	 * 
	 * @param name El nuevo nombre para el usuario.
	 */
	public void setName(String name) {
		this.name = name;
	}

	public int getDNI() {
		return DNI;
	}

	public void setDNI(int dNI) {
		DNI = dNI;
	}

	/**
	 * Obtiene el correo electrónico del usuario.
	 * 
	 * @return El correo electrónico del usuario.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Obtiene el idioma de preferencia del usuario.
	 * 
	 * @return El idioma de preferencia.
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Establece el correo electrónico del usuario.
	 * 
	 * @param email El nuevo correo electrónico para el usuario.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Establece el idioma de preferencia del usuario.
	 * 
	 * @param language El nuevo idioma de preferencia.
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Obtiene la lista de preferencias de contenido del usuario.
	 * 
	 * @return La lista de preferencias.
	 */
	public List<String> getPreference() {
		return preference;
	}

	/**
	 * Establece la lista de preferencias del usuario.
	 * 
	 * @param preference La nueva lista de preferencias.
	 */
	public void setPreference(List<String> preference) {
		this.preference = preference;
	}

	/**
	 * getter
	 * 
	 * @return String retorna el plan del usuario
	 */
	public String getPlan() {
		return plan;
	}

//	public ViewingHistory getViewingHistory() {
//		return viewingHistory;
//	}

	/**
	 * getter
	 * 
	 * @return List Retorna todas las playlists de usuario
	 */

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	/**
	 * Este método crea una playlist en base al nombre que recibe como parámetro
	 * 
	 * @param name el nombre de la nueva playlist
	 * @return Playlist Esto retorna la playlist creada
	 */
	public Playlist createPlaylist(String name) {
		var p = new Playlist(name);
		playlists.add(p);
		return p;
	}

	/**
	 * Retorna una representación en String del objeto User.
	 * 
	 * @return un String con datos personales del usuario.
	 */
	@Override
	public String toString() {
		return "Nombre: " + name + "\nApellido: " + surname + "\nDNI: "+DNI+"\nEmail: "+ email +"\nUsernme: "+ username ;
	}

	/**
	 * Este método agrega un contenido a una playlist
	 * 
	 * @param c el contenido que se va a agregar
	 * @param p la playlist a la que se le agrega el contenido
	 */
	public void addToPlaylist(Content c, Playlist p) {
		if (c != null)
			p.addContent(c);
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

}