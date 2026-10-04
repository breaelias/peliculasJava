package model.streaming.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ReviewDialog extends JDialog {

    private JTextArea txtComment;
    private JButton btnSave;
    
    // Para manejar las estrellas
    private int currentRating = 0; 
    private List<JButton> starButtons; 

    public ReviewDialog(JFrame parent, String movieTitle) {
        super(parent, "Calificar Película", true); // Modal
        setSize(500, 400);
        setLocationRelativeTo(parent);
        
        // panel ppal 
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5); 
        
        // etiqueta con titulo de la peli
        JLabel lblTitle = new JLabel(movieTitle);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2; 
        mainPanel.add(lblTitle, gbc);

        // etiqueta para la calificacion
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblRating = new JLabel("Calificación:");
        lblRating.setFont(new Font("SansSerif", Font.PLAIN, 16));
        mainPanel.add(lblRating, gbc);

        // panel con las estrellas
        JPanel starsPanel = createStarPanel();
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        mainPanel.add(starsPanel, gbc);

        // etiqueta para el comment
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Ocupa todo el ancho
        gbc.weightx = 0;
        JLabel lblComment = new JLabel("Comentario:");
        lblComment.setFont(new Font("SansSerif", Font.PLAIN, 16));
        mainPanel.add(lblComment, gbc);

        // area del texto
        txtComment = new JTextArea(5, 20);
        txtComment.setLineWrap(true);
        txtComment.setWrapStyleWord(true);
        txtComment.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtComment.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        JScrollPane scroll = new JScrollPane(txtComment);
        gbc.gridy = 3;
        gbc.weighty = 1.0; 
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(scroll, gbc);

        // boton para guardar la review
        btnSave = new JButton("Guardar");
        btnSave.setBackground(new Color(33, 150, 243)); // Azul
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSave.setFocusPainted(false);
        btnSave.setPreferredSize(new Dimension(150, 40));
        
        gbc.gridy = 4;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(btnSave, gbc);

        add(mainPanel);
    }

    // lógica de las estrellas (botones con forma de estrella)
    private JPanel createStarPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setBackground(Color.WHITE);
        starButtons = new ArrayList<>();

        Font starFont = new Font("SansSerif", Font.BOLD, 30); 

        for (int i = 1; i <= 5; i++) {
            JButton btn = new JButton("☆");  
            btn.setFont(starFont);
            btn.setForeground(new Color(255, 193, 7)); // les ponemos color
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            //final para usar dentro de la clase anónima
            final int ratingValue = i;
            
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateStars(ratingValue);
                }
            });
            
            starButtons.add(btn);
            p.add(btn);
        }
        return p;
    }

    private void updateStars(int rating) {
        this.currentRating = rating;
        for (int i = 0; i < starButtons.size(); i++) {
            if (i < rating) {
                starButtons.get(i).setText("★"); // Estrella llena
            } else {
                starButtons.get(i).setText("☆"); // Estrella vacía
            }
        }
    }

  
    public void addSaveListener(final ActionListener listener) {
        
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //nos fijamos que haya elegido puntuación
                if (currentRating == 0) {
                    JOptionPane.showMessageDialog(ReviewDialog.this, 
                        "Por favor, selecciona una calificación (estrellas).", 
                        "Faltan datos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
              //nos fijamos que haya hecho un comentario
                if (txtComment.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(ReviewDialog.this, 
                        "Por favor, escribe un comentario.", 
                        "Faltan datos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                //todo ok entonces seguimos
                listener.actionPerformed(e);
            }
        });
    }

    public int getRating() {
        return currentRating;
    }

    public String getComment() {
        return txtComment.getText().trim();
    }
}