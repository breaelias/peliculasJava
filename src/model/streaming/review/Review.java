package model.streaming.review;

import java.time.LocalDateTime;

import model.streaming.content.Content;
import model.streaming.user.User;

/**
 * Esta clase modela una reseña de un determinado contenido
 */
public class Review {
	private int ID;
	private User author;
	private Content content;
	private String comment;
	private int rating;
	private LocalDateTime date;
	private boolean approved;

	/**
	 * Constructor para crear una nueva reseña.
	 * 
	 * @param author  El usuario que escribe la reseña.
	 * @param content El contenido que se está reseñando.
	 * @param comment    El texto del comentario de la reseña.
	 * @param rating  La calificación numérica (ej. de 1 a 5).
	 */
	public Review(User author, Content content, String comment, int rating) {
		this.author = author;
		this.content = content;
		this.comment = comment;
		this.rating = rating;
		this.date = LocalDateTime.now();
	}

	public Review() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Este método permite al admin aprobar la reseña
	 */
	public void approve() {
		approved = true;
	}

	/**
	 * Este método permite al admin rechazar la reseña
	 */
	public void reject() {
		approved = false;
	}

	/**
	 * Verifica si la reseña ha sido aprobada.
	 * 
	 * @return {@code true} si la reseña está aprobada, {@code false} en caso
	 *         contrario.
	 */
	public boolean isApproved() {
		return approved;
	}

	
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	/**
	 * Obtiene el texto del comentario de la reseña.
	 * 
	 * @return El comentario de la reseña.
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Obtiene la calificación numérica de la reseña.
	 * 
	 * @return La calificación de la reseña.
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Obtiene la fecha y hora en que se creó la reseña.
	 * 
	 * @return La fecha de creación.
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * Retorna una representación en String de la reseña.
	 * 
	 * @return Un String con datos de la reseña.
	 */
	@Override
	public String toString() {
		return "---------------- Pelicula: " + content.getTitle() + ", Puntuación: " + rating + "\nComentario: "+ comment+"\n";
	}


	/**
	 * Este metodo permite cambiar el comentario de la reseña por uno nuevo
	 * 
	 * @param newComment el comentario que reemplaza al anterior
	 */
	public void editComment(String newComment) {
		this.comment = newComment;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
}