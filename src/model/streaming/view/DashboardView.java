package model.streaming.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class DashboardView extends JFrame {

	private JLabel lblWelcome;
	private JTable tableMovies;
	private DefaultTableModel tableModel;
	private JButton btnLogout;
	private JTextField txtSearch;
	private JButton btnSearch;

	// un map con las imagenes de la tabla para facilitar el repintado (sino se
	// traba mucho)
	private Map<String, ImageIcon> imageCache = new HashMap<>();

	public DashboardView() {
		super("Plataforma Streaming - Cartelera");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		// el panel de arriba
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(Color.WHITE);
		topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

		lblWelcome = new JLabel("Bienvenido a la Plataforma");
		lblWelcome.setFont(new Font("SansSerif", Font.BOLD, 22));

		JPanel rightTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		rightTop.setBackground(Color.WHITE);

		txtSearch = new JTextField(15);

		btnSearch = new JButton("🔍");
		btnSearch.setBackground(new Color(220, 220, 220));
		btnSearch.setToolTipText("Buscar en OMDb");

		btnLogout = new JButton("Cerrar Sesión");
		btnLogout.setBackground(new Color(33, 150, 243));
		btnLogout.setForeground(Color.WHITE);

		rightTop.add(txtSearch);
		rightTop.add(btnSearch);
		rightTop.add(btnLogout);
		topPanel.add(lblWelcome, BorderLayout.WEST);
		topPanel.add(rightTop, BorderLayout.EAST);

		add(topPanel, BorderLayout.NORTH);

		String[] columns = { "Póster", "Título", "Género", "Resumen", "Acción" };

		// modelo para la tabla (clase anónima).
		tableModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tableMovies = new JTable(tableModel);
		tableMovies.setRowHeight(80);
		tableMovies.setFont(new Font("SansSerif", Font.PLAIN, 14));
		tableMovies.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
		tableMovies.getTableHeader().setBackground(new Color(240, 240, 240));

		// ponemos el ancho de cada columna
		tableMovies.getColumnModel().getColumn(0).setPreferredWidth(80);
		tableMovies.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableMovies.getColumnModel().getColumn(2).setPreferredWidth(100);
		tableMovies.getColumnModel().getColumn(3).setPreferredWidth(400);
		tableMovies.getColumnModel().getColumn(4).setPreferredWidth(120);

		// le seteamos un ordenador automatico a la tabla
		javax.swing.table.TableRowSorter<javax.swing.table.TableModel> sorter = new javax.swing.table.TableRowSorter<>(
				tableModel);

		tableMovies.setRowSorter(sorter);

	// config para que solo se ordenen las de titulo y genero 
		sorter.setSortable(0, false); 
		sorter.setSortable(3, false); 
		sorter.setSortable(4, false); 

		// le asignamos la personalizacion de cadacelda
		tableMovies.getColumnModel().getColumn(0).setCellRenderer(new PosterRenderer());
		tableMovies.getColumnModel().getColumn(3).setCellRenderer(new MultiLineRenderer());
		tableMovies.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());

		JScrollPane scrollPane = new JScrollPane(tableMovies);
		scrollPane.getViewport().setBackground(Color.WHITE);
		add(scrollPane, BorderLayout.CENTER);
	}

	public void setWelcomeMessage(String msg) {
		lblWelcome.setText(msg);
	}

	public JButton getBtnLogout() {
		return btnLogout;
	}

	public JTable getTable() {
		return tableMovies;
	}

	public DefaultTableModel getModel() {
		return tableModel;
	}

	public JTextField getTxtSearch() {
		return txtSearch;
	}

	public JButton getBtnSearch() {
		return btnSearch;
	}

	// para Botones
	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			String text = (value == null) ? "" : value.toString();
			setText(text);

			if ("Calificado".equals(text)) {
				setBackground(Color.GRAY);
				setForeground(Color.LIGHT_GRAY);
				setEnabled(false);
			} else {
				setBackground(new Color(33, 150, 243));
				setForeground(Color.WHITE);
				setEnabled(true);
			}
			return this;
		}
	}

	class MultiLineRenderer extends JTextArea implements TableCellRenderer {
		public MultiLineRenderer() {
			setLineWrap(true);
			setWrapStyleWord(true);
			setOpaque(true);
			setFont(new Font("SansSerif", Font.PLAIN, 12));
			setMargin(new Insets(5, 5, 5, 5));
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText(value != null ? value.toString() : "");
			if (isSelected) {
				setBackground(table.getSelectionBackground());
				setForeground(table.getSelectionForeground());
			} else {
				setBackground(table.getBackground());
				setForeground(table.getForeground());
			}
			return this;
		}
	}

	// clase para que no se trabe tanto la app al carga rimagenes
	class PosterRenderer extends DefaultTableCellRenderer {

		private final ImageIcon loadingIcon;

		public PosterRenderer() {
			String loadingPath = "src/images/not_found.jpg";
			ImageIcon temp = new ImageIcon(loadingPath);

			if (temp.getImageLoadStatus() == MediaTracker.COMPLETE) {
				Image img = temp.getImage().getScaledInstance(40, 60, Image.SCALE_SMOOTH);
				loadingIcon = new ImageIcon(img);
			} else {
				loadingIcon = new ImageIcon(); // si falla vacio
			}
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JLabel label = new JLabel();
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setOpaque(true);

			if (isSelected)
				label.setBackground(table.getSelectionBackground());
			else
				label.setBackground(Color.WHITE);

			if (value != null) {
				final String imagePath = value.toString(); // final para que la clase anónima del hilo la pueda usar

				if (imageCache.containsKey(imagePath)) {
					// Si no tiene la imagen es null -> es que se está cargando, mostramos el
					// cargando
					if (imageCache.get(imagePath) == null) {
						label.setIcon(loadingIcon);
						label.setText("Cargando...");
					} else {
						label.setIcon(imageCache.get(imagePath));
						label.setText("");
					}
				}

				else if (!imagePath.startsWith("http")) {
					label.setIcon(new ImageIcon(imagePath));
				}

				// si es una url se Descarga en Hilo
				else {
					label.setIcon(loadingIcon);
					label.setText("Cargando...");

					// ponemos null para saber que ya se está cargando y evitar hilos duplicados
					imageCache.put(imagePath, null);

					// HILO CON CLASE ANÓNIMA (Reemplazo de Lambda)
					Thread downloadThread = new Thread(new Runnable() {
						@SuppressWarnings("deprecation")
						@Override
						public void run() {
							try {
								URL url = new URL(imagePath);
								ImageIcon icon = new ImageIcon(url);

								// si se descargó bien la guardamo
								if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
									Image img = icon.getImage().getScaledInstance(40, 60, Image.SCALE_SMOOTH);
									ImageIcon finalIcon = new ImageIcon(img);

									imageCache.put(imagePath, finalIcon);

									// Repintamos
									table.repaint();
								}
							} catch (Exception e) {
								imageCache.remove(imagePath); // si falló la sacamos para reintentar
							}
						}
					});
					downloadThread.start();
				}
			} else {
				label.setText("Sin Imagen");
			}
			return label;
		}
	}
}