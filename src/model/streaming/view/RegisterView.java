package model.streaming.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class RegisterView extends JDialog {
    private JTextField txtName, txtUser, txtEmail, txtDni, txtPass;
    private JButton btnSave;

    public RegisterView(JFrame parent) {
        super(parent, "Registro de Usuario", true);
        setSize(350, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // campos de la ventana
        gbc.gridx=0; gbc.gridy=0; add(new JLabel("Nombre:"), gbc);
        gbc.gridx=1; txtName = new JTextField(15); add(txtName, gbc);

        gbc.gridx=0; gbc.gridy=1; add(new JLabel("Usuario:"), gbc);
        gbc.gridx=1; txtUser = new JTextField(15); add(txtUser, gbc);

        gbc.gridx=0; gbc.gridy=2; add(new JLabel("Contraseña:"), gbc);
        gbc.gridx=1; txtPass = new JTextField(15); add(txtPass, gbc);

        gbc.gridx=0; gbc.gridy=3; add(new JLabel("DNI (Num):"), gbc);
        gbc.gridx=1; txtDni = new JTextField(15); add(txtDni, gbc);

        gbc.gridx=0; gbc.gridy=4; add(new JLabel("Email:"), gbc);
        gbc.gridx=1; txtEmail = new JTextField(15); add(txtEmail, gbc);

        // boton guardar usuario 
        gbc.gridx=0; gbc.gridy=5; gbc.gridwidth=2; 
        btnSave = new JButton("Guardar Usuario");
        btnSave.setBackground(new Color(76, 175, 80));
        btnSave.setForeground(Color.WHITE);
        add(btnSave, gbc);
    }

    public JButton getBtnSave() { return btnSave; }
    public String getNameField() { return txtName.getText(); }
    public String getUserField() { return txtUser.getText(); }
    public String getPassField() { return new String(txtPass.getText()); }
    public String getEmailField() { return txtEmail.getText(); }
    public String getDniField() { return txtDni.getText();}

}