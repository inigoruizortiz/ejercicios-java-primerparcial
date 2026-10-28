package es.deusto.ingenieria.swing.p02;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

// Este ejemplo ha sigo creado a partir de una versión generada con ChatGPT 4o
public class Calculadora extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField display;

    public Calculadora() {
        // Configuración básica de la ventana
    	setTitle("Calculadora Básica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Crear el display de la calculadora
        display = new JTextField();
        display.setText("3+5");									// Texto de ejemplo
        display.setEditable(false); 							// No editable
        display.setHorizontalAlignment(JTextField.RIGHT); 		// Alineación del texto
        display.setFont(new Font("Courier", Font.BOLD, 24));	// Formato de la fuente
        add(display, BorderLayout.NORTH);						// Añadir el display a la ventana

        // Panel de botones numéricos y de operaciones
        JPanel buttonPanel = new JPanel();
        // Definir una cuadrícula de 4x4 con 5 píxeles de separación
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));

        // Crear los textos mostrados EN los botones
        String[] buttonsText = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        // Crear los botones y añadirlos al panel        
        for (String text : buttonsText) {
            buttonPanel.add(new JButton(text));
        }

        // Añadir el panel de botones al centro de la ventana
        add(buttonPanel, BorderLayout.CENTER);
    }

    // Método principal
    public static void main(String[] args) {
        // Crear la ventana en el hilo de eventos de Swing
    	SwingUtilities.invokeLater(() -> {
        	// Crear una instancia de la calculadora y hacerla visible
            Calculadora calculadora = new Calculadora();
            calculadora.setVisible(true);
        });
    }
}
