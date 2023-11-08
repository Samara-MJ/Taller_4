package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import uniandes.dpoo.taller4.modelo.Tablero;
import uniandes.dpoo.taller4.modelo.Top10;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Fprincipal {

	private JFrame frame;
	private Tablero tablero;
	private int dificultad;
	private JPanel centro;
	private JComboBox sizeComboBox;
	private JPanel abajo;
	private JLabel jugadas;
	private Top10 jugadores;
	private String nombreActual;
	private JLabel jugadorActual;

	public Fprincipal() {

		frame = new JFrame();
		nombreActual = crearJugador();
		jugadores = new Top10();
		jugadorActual = new JLabel();
		jugadorActual.setText("Jugador Actual: " + nombreActual);
		crearCuadricula(5);
		crearDificultades();
		mostrarBotones();
		mostrarJugadas();

		frame.setTitle("LightsOut");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					jugadores.salvarRecords(new File("data/top10.csv"));
				} catch (FileNotFoundException | UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// frame.setLayout(new BorderLayout());

	}

	public void crearCuadricula(int dificultad) {
		this.dificultad = dificultad;
		this.tablero = new Tablero(dificultad);
		tablero.desordenar(dificultad);
		if (centro != null) {
			frame.remove(centro);
		}

		this.centro = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;

				int cellSize = getWidth() / dificultad;

				for (int i = 0; i < dificultad; i++) {
					for (int j = 0; j < dificultad; j++) {
						if (tablero.darTablero()[i][j] == true) {
							g2d.setColor(Color.YELLOW);
						} else {
							g2d.setColor(Color.BLACK);

						}
						int jugadascont = tablero.darJugadas();
						jugadas.setText("Jugadas: " + jugadascont);
						g2d.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
						g.setColor(Color.GRAY);
						g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
						BufferedImage image = cargarImagen("data/luz.png");
						g2d.drawImage(image, j * cellSize, i * cellSize, cellSize, cellSize, null);

					}
				}
			}
		};

		centro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int cellSize = centro.getWidth() / dificultad;
				int columna = e.getX() / cellSize;
				int fila = e.getY() / cellSize;
				tablero.jugar(fila, columna);

				centro.repaint();
			}

		});

		frame.add(centro, BorderLayout.CENTER);

	}

	private BufferedImage cargarImagen(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void crearDificultades() {
		JPanel arriba = new JPanel();
		arriba.setBackground(Color.BLUE);
		arriba.setOpaque(true);
		String[] sizes = { "5x5", "7x7", "10x10" };
		sizeComboBox = new JComboBox<>(sizes);
		ButtonGroup difficultyGroup = new ButtonGroup();
		JRadioButton easyRadioButton = new JRadioButton("Fácil");
		JRadioButton mediumRadioButton = new JRadioButton("Medio");
		JRadioButton hardRadioButton = new JRadioButton("Difícil");
		difficultyGroup.add(easyRadioButton);
		difficultyGroup.add(mediumRadioButton);
		difficultyGroup.add(hardRadioButton);

		sizeComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedSize = (String) sizeComboBox.getSelectedItem();

				switch (selectedSize) {
				case "5x5":
					crearCuadricula(5);
					break;
				case "7x7":
					crearCuadricula(7);
					break;
				case "10x10":
					crearCuadricula(10);
					break;
				default:
					break;
				}

				frame.repaint();
				frame.revalidate();

			}

		});
		easyRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleDifficultySelection("Fácil");
			}
		});
		mediumRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleDifficultySelection("Medio");
			}

		});
		hardRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleDifficultySelection("Difícil");
			}

		});
		arriba.add(new JLabel("Selecciona el tamaño de la cuadrícula:"));
		arriba.add(easyRadioButton);
		arriba.add(mediumRadioButton);
		arriba.add(hardRadioButton);
		arriba.add(sizeComboBox);
		frame.add(arriba, BorderLayout.NORTH);

	}

	public void handleDifficultySelection(String opcion) {
		switch (opcion) {
		case "Fácil":
			sizeComboBox.setSelectedItem("5x5");
			break;
		case "Medio":
			sizeComboBox.setSelectedItem("7x7");
			break;
		case "Difícil":
			sizeComboBox.setSelectedItem("10x10");
			break;

		}

	}

	public void mostrarJugadas() {
		jugadas = new JLabel();
		abajo = new JPanel();
		abajo.setBackground(Color.GRAY);
		abajo.setOpaque(true);
		int jugadascont = this.tablero.darJugadas();
		jugadas.setText("Jugadas: " + jugadascont);
		abajo.add(jugadas);
		abajo.add(jugadorActual);
		abajo.revalidate();
		abajo.repaint();
		frame.add(abajo, BorderLayout.SOUTH);

	}

	public void mostrarBotones() {
		JButton reiniciar = new JButton("Reiniciar");
		reiniciar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tablero.reiniciar();
				int jugadascont = tablero.darJugadas();
				jugadas.setText("Jugadas: " + jugadascont);
				crearCuadricula(dificultad);
				frame.repaint();
				frame.revalidate();
			}

		});
		JButton nuevo = new JButton("Nuevo");
		nuevo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jugadores.agregarRegistro(nombreActual, tablero.calcularPuntaje());
				nombreActual = crearJugador();
				jugadorActual.setText("Jugador Actual: " + nombreActual);
				tablero.reiniciar();
				int jugadascont = tablero.darJugadas();
				jugadas.setText("Jugadas: " + jugadascont);
				crearCuadricula(dificultad);
				frame.repaint();
				frame.revalidate();
			}

		});
		JButton mostrartop10 = new JButton("TOP-10");
		mostrartop10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarTop10();
			}

		});

		JPanel derecha = new JPanel();
		derecha.add(reiniciar);
		derecha.add(nuevo);
		derecha.add(mostrartop10);
		derecha.setSize(400, 500);
		derecha.setBackground(Color.GRAY);
		derecha.setOpaque(true);
		frame.add(derecha, BorderLayout.EAST);
	}

	public String crearJugador() {
		String nombre = JOptionPane.showInputDialog("Por favor, ingresa tu nombre:");
		if (nombre != null && !nombre.isEmpty()) {
			return nombre;
		}
		return null;
	}

	public void mostrarTop10() {

	}

	public static void main(String[] args) throws HeadlessException {
		new Fprincipal();
	}

}
