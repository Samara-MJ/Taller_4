package presentacion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Icon;


public class Fprincipal extends JFrame {
	public Fprincipal()
	{
		
		setLayout(new BorderLayout());
		
		JLabel derecha = new JLabel("derecha");
		derecha.setSize(200,500);
		derecha.setBackground(Color.GRAY);
		derecha.setOpaque(true);
		JLabel centro = new JLabel();
		centro.setBackground(Color.YELLOW);
		centro.setOpaque(true);
		centro.setSize(100,100);
		ImageIcon imagen = new ImageIcon("data/luz.png");
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(centro.getWidth(), centro.getHeight(), Image.SCALE_DEFAULT));
		centro.setIcon(icono);
		
		
		add(derecha, BorderLayout.EAST);
		add(centro, BorderLayout.CENTER);
		
		
		
		setTitle("LightsOut");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400,500);
		setVisible(true);
		
	}
	
	public static void main(String[]args) {
		new Fprincipal();
	}

}
