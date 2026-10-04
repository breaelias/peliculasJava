package model.streaming.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.streaming.app.Validations;
import model.streaming.dao.Factory;
import model.streaming.dao.UserDAO;
import model.streaming.exception.EmptyFieldException;
import model.streaming.exception.UserAlreadyExistsException;
import model.streaming.user.CommonUser;
import model.streaming.view.RegisterView;

public class RegisterController {

    private RegisterView view;
    private UserDAO userDAO;

    public RegisterController(JFrame parentView) {
        this.view = new RegisterView(parentView);
        this.userDAO = Factory.getUserDao();

        
        this.view.getBtnSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });

        this.view.setVisible(true);
    }
    


    private void handleRegister() {
        try {
            String name = view.getNameField().trim();
            String user = view.getUserField().trim();
            String pass = view.getPassField().trim();
            String email = view.getEmailField().trim();
            
            int dni = 0;
            try {
                dni = Integer.parseInt(view.getDniField());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "El DNI debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // chequeamos que no esten vacios
            if (name.isEmpty()) throw new EmptyFieldException("Nombre");
            if (user.isEmpty()) throw new EmptyFieldException("Usuario");
            if (pass.isEmpty()) throw new EmptyFieldException("Contraseña");
            if (email.isEmpty()) throw new EmptyFieldException("Email");

            // validamos formatos
            if (!Validations.esTextoValido(name)) {
                JOptionPane.showMessageDialog(view, "El nombre es inválido (solo letras).", "Formato Incorrecto", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!Validations.esEmailValido(email)) {
                JOptionPane.showMessageDialog(view, "El email es inválido. Se esperaba: ejemplo@dominio.ext", "Formato Incorrecto", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!Validations.esPasswordValida(pass)) {
                JOptionPane.showMessageDialog(view, "La contraseña es muy corta (mínimo 4 caracteres).", "Seguridad", JOptionPane.WARNING_MESSAGE);
                return;
            }

           
            // Validar DNI
            String errorDni = Validations.validarDNI(dni);
            if (errorDni != null) {
                throw new UserAlreadyExistsException(errorDni);
            }

            // Validar Usuario
            String errorUser = Validations.validarUsername(user);
            if (errorUser != null) {
                throw new UserAlreadyExistsException(errorUser);
            }

            // Guardamos (ya es valido pq paso los chequeos)
            CommonUser newUser = new CommonUser();
            newUser.setName(name);
            newUser.setSurname("Apellido"); 
            newUser.setDNI(dni);
            newUser.setUsername(user);
            newUser.setEmail(email);
 
            userDAO.save(newUser, pass);

            JOptionPane.showMessageDialog(view, "¡Usuario registrado con éxito!", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();

        } catch (EmptyFieldException | UserAlreadyExistsException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Error de Registro", JOptionPane.WARNING_MESSAGE);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}