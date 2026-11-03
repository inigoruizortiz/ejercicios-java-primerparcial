// IAG (Claude 3.5 Sonnet y ChatGPT 4o)
// ADAPTADO: El código ha sido creado con Claude y refinado con ChatGPT 4o.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.parcial.threads;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import es.deusto.prog.examen.parcial.Actividad.TipoActividad;

public class SlotMachineGame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JLabel[] slots;
	private JButton startButton = new JButton("Iniciar");
	private JButton stopButton = new JButton("Parar");

	private List<TipoActividad> actividades = List.of(TipoActividad.values());
	
	// mio:
	private final Object monitor = new Object();
	private volatile boolean pausa = true;
	private Thread[] hilos;
	
	public SlotMachineGame() {
		JPanel slotPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		
		// mio:
		slots = new JLabel[3];

		for (int i = 0; i < 3; i++) {
			slots[i] = new JLabel();
			Random random = new Random();
			this.actualizarLabel(slots[i], actividades.get(random.nextInt(actividades.size())));
			slotPanel.add(slots[i]);
		}

		startButton.addActionListener(e -> {
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			startGame();			
		});
		
		stopButton.addActionListener(e -> {
			stopButton.setEnabled(false);
			startButton.setEnabled(true);
			stopGame();
		});
		
		stopButton.setEnabled(false);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);

		this.setLayout(new BorderLayout());
		this.add(slotPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);

		this.setTitle("Slot Machine de Actividades");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(400, 200);
		this.setLocationRelativeTo(null);
		
		// tarea 4:
		 hilos = new Thread[3];
		 for ( int i = 0; i <3; i++) {
			 final int index = i; // VARIABLE AUXILIAR QUE COPIA A I, NO SE PUEDE USAR LA VARIABE DE UN FOR EN UN THREAD
			 hilos[i] = new Thread(new Runnable() {
				
				@Override
				public void run() {
					while (true) {
						synchronized (monitor) {
							if (pausa ==  true) {
								try {
									monitor.wait();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						Random random = new Random();
						actualizarLabel(slots[index], actividades.get(random.nextInt(actividades.size())));
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}

					
				}
			});

			hilos[i].start();
		}
		 
	}
	
	// Actializa el label con la imagen de una actividad
	private void actualizarLabel(JLabel label, TipoActividad actividad) {
		String imagePath = "resources/images/" + actividad.toString().toLowerCase() + ".png";
		label.setIcon(new ImageIcon(imagePath));
		label.setBackground(actividad.getColor());
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setOpaque(true);
	}
	
	
	// TODO: Tarea 4: Iniciar el juego
	private void startGame() {
		synchronized (monitor) {
	        pausa = false;
	        monitor.notifyAll(); // Despierta a los 3 hilos bloqueados en monitor.wait()
	    }


	}

	// TODO: Tarea 4: Detener el juego
	private void stopGame() {
		pausa = true;
		// Verificar si todas las imágenes son iguales
		if (checkWin()) {
			JOptionPane.showMessageDialog(this, "¡Felicidades! ¡Has ganado un abono mensual!", "Premio", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "¡Lo siento! Inténtalo de nuevo.", "Otra vez será", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

	// Verifica si todas las imágenes son iguales
	private boolean checkWin() {
		Icon firstIcon = slots[0].getIcon();
		
		for (int i = 1; i < slots.length; i++) {
			if (!slots[i].getIcon().equals(firstIcon)) {
				return false;
			}
		}

		return true;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SlotMachineGame juego = new SlotMachineGame();
			juego.setVisible(true);
		});
	}
}