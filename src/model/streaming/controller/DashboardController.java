package model.streaming.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import model.streaming.content.Movie;
import model.streaming.dao.Factory;
import model.streaming.dao.ReviewDAO;
import model.streaming.service.OMDbService;
import model.streaming.user.User;
import model.streaming.review.Review;
import model.streaming.view.DashboardView;
import model.streaming.view.ReviewDialog;

public class DashboardController {

    private DashboardView dashboardView;
    private User currentUser;
    private ReviewDAO reviewDAO;	
    
    private List<Movie> currentList; 
    private boolean isFirstAccess;

    public DashboardController(User user, List<Movie> moviesToShow, boolean hasHistory) {
        this.currentUser = user;
        this.currentList = moviesToShow;
        
        this.reviewDAO = Factory.getReviewDao();
        this.isFirstAccess = !hasHistory;
        
        initDashboard();
    }

    private void initDashboard() {
    	
        dashboardView = new DashboardView();
        
        String msg = isFirstAccess ? "Top 10 películas rankeadas" : "recomendaciones para vos";
        dashboardView.setWelcomeMessage("Bienvenido " + currentUser.getUsername() + " - " + msg);

        refreshMovieTable();

        dashboardView.getBtnLogout().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });

        dashboardView.getBtnSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performExternalSearch();
            }
        });

        dashboardView.getTable().addMouseListener(new TableMouseListener());

        dashboardView.setVisible(true);
    }

    private void refreshMovieTable() {
        javax.swing.table.DefaultTableModel model = dashboardView.getModel();
        model.setRowCount(0); 

        if (currentList != null) {
            for (Movie m : currentList) {
                String posterData = (m.getPoster() == null || m.getPoster().isEmpty()) ? "src/images/logo.jpg" : m.getPoster();
                boolean isRated = reviewDAO.exists(currentUser.getID(), m.getID());
                String buttonText = isRated ? "Calificado" : "Calificar";
                String genreText = (m.getGenre() != null) ? m.getGenre().toString() : "Sin género";

                Object[] row = {
                    posterData,            
                    m.getTitle(),          
                    genreText,          
                    m.getOverview(),       
                    buttonText 
                };
                model.addRow(row);
            }
        }
    }

    private void handleLogout() {
        dashboardView.dispose();
        new LoginController(); 
    }
    
    
    // bbuscamos en el ombd
    private void performExternalSearch() {
        final String query = dashboardView.getTxtSearch().getText().trim();
        
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(dashboardView, "Ingrese un título para buscar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        dashboardView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                Movie result = null;
                try {
                    result = OMDbService.buscarPelicula(query);
                } catch (Exception e) {
                    e.printStackTrace();
                    }
                
                showMovieDetail(result, query);
            }
        });
        
        worker.start();
    }
    
    @SuppressWarnings("deprecation")
	private void showMovieDetail(Movie movie, String originalQuery) {
        dashboardView.setCursor(Cursor.getDefaultCursor());

        if (movie == null) {
            String msg = "El contenido no se encuentra disponible.";
            if (originalQuery != null && !originalQuery.isEmpty()) {
                msg = "El contenido \"" + originalQuery + "\" no se encuentra disponible.";
            }
            JOptionPane.showMessageDialog(dashboardView, msg, "Resultado", JOptionPane.INFORMATION_MESSAGE);
            dashboardView.getTxtSearch().setText("");
            return;
        }

        final JDialog dialog = new JDialog(dashboardView, "Detalle de Película", true);
        dialog.setSize(600, 450);
        dialog.setLocationRelativeTo(dashboardView);
        dialog.setLayout(new BorderLayout(15, 15));
        dialog.setMinimumSize(new Dimension(600, 400));
        
        
        //aca preparamos el panel dd la imagen
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 0));
        
        JLabel lblPoster = new JLabel("", SwingConstants.CENTER);
        lblPoster.setPreferredSize(new Dimension(180, 270));
        lblPoster.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        if (movie.getPoster() != null && !movie.getPoster().isEmpty() && !movie.getPoster().equals("N/A")) {
            try {
                ImageIcon icon;
                if (movie.getPoster().startsWith("http")) {
                     icon = new ImageIcon(new URL(movie.getPoster()));
                } else {
                     icon = new ImageIcon(movie.getPoster());
                }
                Image img = icon.getImage().getScaledInstance(180, 270, Image.SCALE_SMOOTH);
                lblPoster.setIcon(new ImageIcon(img));
            } catch (Exception e) { lblPoster.setText("Sin Imagen"); }
        } else { lblPoster.setText("Sin Imagen"); }
        
        leftPanel.add(lblPoster, BorderLayout.CENTER);

       //preparamos el panel central que tiene los detalles de la peli
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(0, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        centerPanel.setBackground(Color.WHITE);

        JPanel headerInfo = new JPanel();
        headerInfo.setLayout(new BoxLayout(headerInfo, BoxLayout.Y_AXIS));
        headerInfo.setBackground(Color.WHITE);
        
        // aca elt itulo
        JTextArea txtTitle = new JTextArea(movie.getTitle());
        txtTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        txtTitle.setLineWrap(true);
        txtTitle.setWrapStyleWord(true);
        txtTitle.setEditable(false);
        txtTitle.setOpaque(false);
        txtTitle.setBorder(null);
        
        //  aca el año
        JLabel lblYear = new JLabel("Año: " + movie.getYear());
        lblYear.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        headerInfo.add(txtTitle);
        headerInfo.add(Box.createVerticalStrut(10));
        headerInfo.add(lblYear);

        // aca el resumen de la pelicula
        JTextArea txtPlot = new JTextArea(movie.getOverview());
        txtPlot.setLineWrap(true);
        txtPlot.setWrapStyleWord(true);
        txtPlot.setEditable(false);
        txtPlot.setOpaque(false);
        txtPlot.setFont(new Font("SansSerif", Font.PLAIN, 12));
        txtPlot.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 

        JScrollPane scrollPlot = new JScrollPane(txtPlot);
        scrollPlot.setBorder(null);
        scrollPlot.getViewport().setOpaque(false);
        scrollPlot.setOpaque(false);

        centerPanel.add(headerInfo, BorderLayout.NORTH);
        centerPanel.add(scrollPlot, BorderLayout.CENTER);

        // un boton  para cerrar la ventana
        JButton btnClose = new JButton("Continuar");
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnClose);

        dialog.add(leftPanel, BorderLayout.WEST);
        dialog.add(centerPanel, BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        
        dashboardView.getTxtSearch().setText("");
        dialog.setVisible(true);
    }

    
    private void openReviewDialog(String movieTitle) {
        Movie selectedMovie = null;
        for (Movie m : currentList) {
            if (m.getTitle().equals(movieTitle)) {
                selectedMovie = m;
                break;
            }
        }

        if (selectedMovie != null) {
            ReviewDialog reviewDialog = new ReviewDialog(dashboardView, selectedMovie.getTitle());
            final Movie movieFinal = selectedMovie; 
            
            reviewDialog.addSaveListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int rating = reviewDialog.getRating();
                    String comment = reviewDialog.getComment();

                    Review review = new Review();
                    review.setAuthor(currentUser);
                    review.setContent(movieFinal);
                    review.setRating(rating);
                    review.setComment(comment);
                    review.setDate(LocalDateTime.now());
                    
                    reviewDAO.save(review);
                    
                    JOptionPane.showMessageDialog(dashboardView, "Reseña guardada exitosamente.");
                    reviewDialog.dispose();
                    
                    updateTableRowToRated(movieFinal.getTitle());
                }
            });

            reviewDialog.setVisible(true);
        }
    }

    public void updateTableRowToRated(String movieTitle) {
        javax.swing.table.DefaultTableModel model = dashboardView.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String titleInRow = (String) model.getValueAt(i, 1);
            if (titleInRow.equals(movieTitle)) {
                model.setValueAt("Calificado", i, 4); 
                break;
            }
        }
    }

   
    private class TableMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JTable table = dashboardView.getTable();
            int row = table.rowAtPoint(e.getPoint());
            int col = table.columnAtPoint(e.getPoint());

            if (row >= 0) {
                if (col == 4) { 
                    String status = (String) table.getValueAt(row, 4);
                    if (!"Calificado".equals(status)) {
                        String title = (String) table.getValueAt(row, 1);
                        openReviewDialog(title);
                    }
                } else if (e.getClickCount() == 2) {
                    String title = (String) table.getValueAt(row, 1);
                    Movie selected = null;
                    for (Movie m : currentList) {
                        if (m.getTitle().equals(title)) {
                            selected = m;
                            break;
                        }
                    }
                    if (selected != null) {
                        showMovieDetail(selected, ""); 
                    }
                }
            }
        }
    }
}