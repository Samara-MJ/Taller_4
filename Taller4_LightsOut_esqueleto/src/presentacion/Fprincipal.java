package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uniandes.dpoo.taller4.modelo.Tablero;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Fprincipal {

	private JFrame frame;
	private Tablero tablero;
	private Boolean bandera = true;

	public Fprincipal() {
		this.tablero = new Tablero(5);
		frame = new JFrame();
		JLabel derecha = new JLabel("derecha");
		derecha.setSize(400, 500);
		derecha.setBackground(Color.GRAY);
		derecha.setOpaque(true);
		JLabel arriba = new JLabel("Tamaño");
		arriba.setBackground(Color.BLUE);
		arriba.setOpaque(true);
		JLabel abajo = new JLabel("Jugadas");
		abajo.setBackground(Color.WHITE);
		abajo.setOpaque(true);
		crearCuadricula();

		frame.add(derecha, BorderLayout.EAST);
		frame.add(arriba, BorderLayout.NORTH);
		frame.add(abajo, BorderLayout.SOUTH);

		frame.setTitle("LightsOut");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 500);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());

	}

	public void crearCuadricula() {
		JPanel centro = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;

				int cellSize = getWidth() / 5;

				for (int i = 0; i < 5; i++) {
					for (int j = 0; j < 5; j++) {
						if (bandera) {
							int fila = i;
							int columna = j;
							if (j % 2 == 0) {
								tablero.darTablero()[i][j] = true;
								g2d.setColor(Color.YELLOW);
							} else {
								tablero.darTablero()[i][j] = false;
								g2d.setColor(Color.BLACK);
							}
							BufferedImage image = cargarImagen("data/luz.png");
							g2d.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
							g2d.drawImage(image, j * cellSize, i * cellSize, cellSize, cellSize, null);
							// g2d.dispose();
							g.setColor(Color.GRAY);
							g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
							

						}else {
									if (tablero.darTablero()[i][j] == true) {
										g2d.setColor(Color.YELLOW);
									} else {
										g2d.setColor(Color.BLACK);

									}

								
							
							g2d.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
							g.setColor(Color.GRAY);
							g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
							BufferedImage image = cargarImagen("data/luz.png");
							g2d.drawImage(image, j * cellSize, i * cellSize, cellSize, cellSize, null);
							
						}
					}
				}
			}
		};
		
		centro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bandera = false;
				int cellSize = centro.getWidth() / 5;
				int columna = e.getX() / cellSize;
				int fila = e.getY() / cellSize;
				tablero.jugar(fila, columna);
				centro.repaint();
				// actualizarApariencia(centro);

			}

		});

		centro.setSize(100, 100);
		frame.add(centro, BorderLayout.CENTER);

	}

	public void actualizarApariencia(Graphics2D g2d) {
		int index = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (tablero.darTablero()[i][j] == true) {
					g2d.setColor(Color.YELLOW);
				} else {
					g2d.setColor(Color.BLACK);

				}

				index++;
			}
		}

	}

	private BufferedImage cargarImagen(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) throws HeadlessException {
		new Fprincipal();
	}

}
