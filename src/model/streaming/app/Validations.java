package model.streaming.app;

import model.streaming.dao.Factory;
import model.streaming.dao.UserDAO;

public class Validations {

	//solamente letras y espacios
    public static boolean esTextoValido(String texto) {
        return texto != null && texto.matches("^[A-Za-z]+(?:\\s[a-zA-Z]+)*$");
    }

    // 
    public static boolean esEmailValido(String email) {
        return email != null && email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    	//validamos que el dni no exista en la DB
    public static String validarDNI(int dni) {
        UserDAO userDAO = Factory.getUserDao();
        if (dni <= 0) {
            return "El DNI debe ser un número positivo.";
        }
        if (userDAO.dniExists(dni)) {
            return "El DNI ya se encuentra registrado en el sistema.";
        }
        return null; // null significa q es valido
    }

    // validar que el usuario no este registrado ni vacio
    public static String validarUsername(String username) {
        UserDAO userDAO = Factory.getUserDao();
        if (username == null || username.trim().isEmpty()) {
            return "El nombre de usuario no puede estar vacío.";
        }
        if (userDAO.userExists(username)) { 
            return "El nombre de usuario ya existe.";
        }
        return null; // null significa OK
    }
    
    public static boolean esPasswordValida(String pass) {
        return pass != null && pass.length() >= 4;
    }
}