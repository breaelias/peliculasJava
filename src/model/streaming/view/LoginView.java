package model.streaming.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LoginView extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JButton btnRegistrar;

    public LoginView() {
        super("Neflis");

        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 400));
        
        //dividimos en dos mitades la ventana
        setLayout(new GridLayout(1, 2));

        add(new ImagePanel());

        JPanel rightPanel = new JPanel(new GridBagLayout()); //gridbag para ajustar automatico
        rightPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.gridx = 0; 

        // etiqueta de iniciar sesión.
        JLabel lblTitle = new JLabel("Iniciar Sesión");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 0; 
        rightPanel.add(lblTitle, gbc);

        // la etiqueta para el cuadro del usuario
        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridy = 1;
        rightPanel.add(lblUser, gbc);

        // donde se escribe el usuario
        txtUsername = new JTextField(20);
        gbc.gridy = 2;
        rightPanel.add(txtUsername, gbc);

        // el texto sobre el cuadro de la contraseña
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridy = 3;
        rightPanel.add(lblPass, gbc);

        // el cuadrito de la contraseña
        txtPassword = new JPasswordField(20);
        gbc.gridy = 4;
        rightPanel.add(txtPassword, gbc);

      
        gbc.gridy = 5;
        rightPanel.add(new JLabel(" "), gbc);

        // el boton de ingresar
        btnIngresar = new JButton("Login");
        btnIngresar.setBackground(new Color(33, 150, 243));
        btnIngresar.setForeground(Color.WHITE);
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER; 
        rightPanel.add(btnIngresar, gbc);

       //el panel de registro
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(new JLabel("¿Aún no sos usuario?"));
        
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(33, 150, 243));
        btnRegistrar.setForeground(Color.WHITE);
        bottomPanel.add(btnRegistrar);
        
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(bottomPanel, gbc);

        add(rightPanel);
    }

    private class ImagePanel extends JPanel {
        private Image img;

        public ImagePanel() {
            try {
                URL url = getClass().getResource("/images/logo.jpg");
                if (url != null) {
                    img = ImageIO.read(url);
                } else {
                    img = ImageIO.read(new File("src/images/logo.jpg"));
                }
            } catch (IOException e) {
                System.err.println("No se pudo cargar la imagen: " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (img != null) {
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    // --- Getters ---
    public String getUsername() { return txtUsername.getText(); }
    public String getPass() { return new String(txtPassword.getPassword()); }
    public JButton getBtnIngresar() { return btnIngresar; }
    public JButton getBtnRegistrarse() { return btnRegistrar; }

    public void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}