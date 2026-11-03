// IAG (Claude 3.5 Sonnet y ChatGPT 4o)
// ADAPTADO: El código ha sido creado con Claude y refinado con ChatGPT 4o.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.parcial.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.deusto.prog.examen.parcial.Actividad;
import es.deusto.prog.examen.parcial.Sesion;
import es.deusto.prog.examen.parcial.Sesion.DiaSemana;

public class HorarioGimnasioLayout extends JFrame {
	private static final long serialVersionUID = 1L;
	private List<Actividad> actividades;
	
	
	private JPanel pCentro; // creado por mi
	private JLabel matriz [][];  // creado por mi
	public HorarioGimnasioLayout(List<Actividad> actividades) {
		this.actividades = actividades;

		//TODO: TAREA 1: Configurar el layaout del horario de actividades
		this.add(new JLabel("Añade aquí el horario de actividades", JLabel.CENTER));
		
		this.setTitle("Horario Semanal de Actividades");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		// creado por mi:
		pCentro = new JPanel(new  GridLayout(6, 6, 10, 10));
		add(pCentro, BorderLayout.CENTER);
		matriz = new JLabel[6][6]; // cada casilla de la matriz tiene un null despues de crearla
		
		// cargamos los dias de la semana
		int col = 1;
		for(DiaSemana d: DiaSemana.values()) {  // recorremos los valores de la enumeracion DiaSemana
			JLabel lbl =  new JLabel(d.toString());
			lbl.setHorizontalAlignment(JLabel.CENTER);
			matriz[0][col] = lbl;
			col++;
		}
		int fil = 1;
		
		// cargamos las horas
		for(int hora=9;hora<=13;hora++) {
			JLabel lbl = new JLabel();
			if (hora < 10) {
				lbl.setText("0"+hora+":00");				
			} else {
				lbl.setText(hora+":00");
			}
			lbl.setHorizontalAlignment(JLabel.CENTER);
			matriz[fil][0] = lbl;
			fil++;
		}
		
		// cargamos las actividades
		for (Actividad a : this.actividades) {
			Color color = a.getTipo().getColor();
			for (Sesion s : a.getSesiones()) {
				int fila = s.getHoraInicio() - 8; 
				int columna = switch (s.getDia()) {
					case LUNES -> 1;
					case MARTES -> 2;
					case MIERCOLES -> 3;
					case JUEVES -> 4;
					case VIERNES -> 5;
					default -> 0;
				};
				JLabel lbl = new JLabel(a.getTipo().toString());
				lbl.setOpaque(true);
				lbl.setBackground(color);
				lbl.setHorizontalAlignment(JLabel.CENTER);
				lbl.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				// lbl.setFont(new Font(lbl.getFont().getName(), Font.ITALIC, lbl.getFont().getSize()));    // PONER LA LABEL EN CURSIVA,
				// en negrita es Font.BOLD pero de por sí ya viene en negrita
				matriz[fila][columna] = lbl;
			}
		}
		
		
		matriz[0][0] = new JLabel(); // creamos una label vacía arriba a la izquierda, así no le añadimos el borde en el for de abajo
		
		// recorremos la matriz y la cargamos en el panel centro
		for (int i = 0; i < matriz.length; i++) { // recorrer las filas de la matriz
			for (int j = 0; j < matriz[i].length; j++) { // recorrer las columnas de la matriz
				if (matriz[i][j] == null) {
					JLabel lbl = new JLabel();
					lbl.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
					pCentro.add(lbl);
				} else {
					pCentro.add(matriz[i][j]);
				}
			
			}
		}		
		
	}
}