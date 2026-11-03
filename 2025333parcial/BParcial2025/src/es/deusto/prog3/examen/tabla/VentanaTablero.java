package es.deusto.prog3.examen.tabla;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import es.deusto.prog3.examen.domain.PiezaAjedrez; 

public class VentanaTablero extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private PiezaAjedrez[][] tablero;
	private ModeloTabla modelo; // LO CREAMOS NOSOTROS, OJO DARSE CUENRTA EN EL EXAMEN HAY QUE SABERSELO DE MEMORIA PARA EL EXAMEN
	private JTable tablaJuego; // LO CREAMOS NOSOTROS, OJO DARSE CUENRTA EN EL EXAMEN HAY QUE SABERSELO DE MEMORIA PARA EL EXAMEN

	public VentanaTablero() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Chess Master 2000");
		setSize(800, 650);
		setLocationRelativeTo(null);
		 
		inicializarTablero();
		
		// TAREA 1.2 Implementa aquí la tarea
		
//		String[] cabecera = { "a", "b", "c", "d", "e", "f", "g", "h" };
//		JTable tablaJuego = new JTable(convertirAString(tablero), cabecera);
		
		// SABER ESTO DE MEMORIA PARA EL EXAMEN (ESTAS DOS SIGUIENTES LINEAS) IMPORTANTE PARA LOS APUNTES 
		modelo = new ModeloTabla(tablero);
		tablaJuego = new JTable(modelo);
		
		JScrollPane scrollPane = new JScrollPane(tablaJuego);
		add(scrollPane);
		tablaJuego.setRowHeight(50);
		// TAREA 1.3 Implementa aquí la tarea
		
		tablaJuego.setDefaultRenderer(Object.class, new TableCellRenderer() {
		    
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		            int row, int column) {
		        
		        // Manejar valores nulos para evitar NullPointerException
		       
		        JLabel lbl = new JLabel(value.toString());
		        lbl.setOpaque(true);
		        lbl.setHorizontalAlignment(JLabel.CENTER);
		      
		        
		        // Tablero estándar de ajedrez (8x8)
		        if (row < 8 && column < 9 && column>0) {
		            // Si la suma de fila y columna es par -> color claro, si es impar -> color oscuro
		            if ((row + column) % 2 == 0) {
		                lbl.setBackground(new Color(239, 238, 211)); // Casilla clara
		            } else {
		                lbl.setBackground(new Color(120, 151, 88));  // Casilla oscura
		            }
		            
		            PiezaAjedrez pieza = (PiezaAjedrez) value;
		            if(pieza!=null && !pieza.equals(PiezaAjedrez.VACIO)) {
		            	lbl.setText(""); // vaciamos la lbl
		            	ImageIcon im = new ImageIcon(pieza.getImagen());
		            	lbl.setIcon(im);
		            }
		            
		        }
		        
	
		        
		        return lbl;
		    }
		});
		
		
		// TAREA 1.4 Implementa aquí la tarea
		MouseListener mL = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = e.getPoint();
				int fila = tablaJuego.rowAtPoint(p);
				int columna = tablaJuego.columnAtPoint(p);
				if(fila>= 0 && fila<=7 && columna>=1 && columna<=8) {
					tablero[fila][columna-1] = PiezaAjedrez.PEON_BLANCO;
					modelo = new ModeloTabla(tablero);
					tablaJuego.setModel(modelo);
				}
				
			}
		};
		tablaJuego.addMouseListener(mL);
				setVisible(true);
	}

	private void inicializarTablero() {
		tablero = new PiezaAjedrez[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tablero[i][j] = PiezaAjedrez.VACIO;
			}
		}
		
		tablero[0][5] = PiezaAjedrez.REY_BLANCO;
		tablero[1][0] = PiezaAjedrez.PEON_BLANCO;
		tablero[1][3] = PiezaAjedrez.PEON_NEGRO;
		tablero[1][7] = PiezaAjedrez.PEON_NEGRO;
		tablero[2][1] = PiezaAjedrez.PEON_BLANCO;
		tablero[4][7] = PiezaAjedrez.REY_NEGRO;
		tablero[6][4] = PiezaAjedrez.PEON_NEGRO;
		tablero[7][2] = PiezaAjedrez.TORRE_BLANCA;
	}
	
	Object[][] convertirAString(PiezaAjedrez[][] tablero) {
		Object[][] tableroString = new Object[8][8];
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				tableroString[i][j] = tablero[i][j].getSimbolo();
			}
		}
		
		return tableroString;
	}
	
}
