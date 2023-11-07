package presentacion;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Fprincipal extends JFrame {
	public Fprincipal()
	{
		
		setLayout(new BorderLayout());
		
		JLabel derecha = new JLabel("derecha");
		derecha.setBackground(Color.GRAY);
		derecha.setOpaque(true);
		JLabel centro = new JLabel("centro");
		centro.setBackground(Color.lightGray);
		centro.setOpaque(true);
		
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
