package model.streaming.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class LoadingView extends JDialog {

	
    public LoadingView(JFrame parent) {
        super(parent, true); // true para q sea modal (bloquea la ventana de atrás)
        setUndecorated(true); // Quita los bordes  para q se vea más estetico
        setSize(400, 120);
        setLocationRelativeTo(parent); // centramos sobre la ventana padre

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(new Color(33, 150, 243), 2)); 
        panel.setBackground(Color.WHITE);

        
        panel.add(Box.createVerticalStrut(20));

        // texts
        JLabel lblTitle = new JLabel("Cargando catálogo de películas...");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitle);

        JLabel lblSub = new JLabel("Por favor espere");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(Color.GRAY);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblSub);

        
        panel.add(Box.createVerticalStrut(15));

        // barra d progreso
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true); // animacion 
        progressBar.setBackground(Color.WHITE);
        progressBar.setForeground(new Color(33, 150, 243));
        progressBar.setMaximumSize(new Dimension(350, 15));
        
        panel.add(progressBar);
        panel.add(Box.createVerticalStrut(20));

        add(panel);
    }
}
