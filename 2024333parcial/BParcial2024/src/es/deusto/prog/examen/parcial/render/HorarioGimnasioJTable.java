// IAG (Claude 3.5 Sonnet y ChatGPT 4o)
// ADAPTADO: El código ha sido creado con Claude y refinado con ChatGPT 4o.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.parcial.render;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import es.deusto.prog.examen.parcial.Actividad;
import es.deusto.prog.examen.parcial.Sesion;

public class HorarioGimnasioJTable extends JFrame {
    private static final long serialVersionUID = 1L;
    private List<Actividad> actividades;
    
    private HorarioGimnasioModelo modelo;
    private JTable tablaActividades;
    
   
  
    
    public HorarioGimnasioJTable(List<Actividad> actividades) {
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.actividades = actividades;
    	
		//TODO: TAREA 2: Modificar el modelo de datos y renderizado de la tabla de actividades.
   //     String[] columnNames = {"", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
    //    String[][] data = {
      //      {"09:00", "Yoga", "", "Yoga", "", ""},
        //    {"10:00", "", "Pilates", "", "Pilates", ""},
         //   {"11:00", "", "Zumba", "", "Zumba", ""},
          //  {"12:00", "Boxeo", "", "", "", "Boxeo"},
            //{"13:00", "Spinning", "", "Spinning", "", "Spinning"}
        //};

    //    JTable tablaActividades = new JTable(data, columnNames);                       
        modelo = new HorarioGimnasioModelo(actividades);
        tablaActividades = new JTable(modelo);
        
        tablaActividades.setRowHeight(80);
        JScrollPane scroll = new JScrollPane(tablaActividades);
       
        
        tablaActividades.getTableHeader().setReorderingAllowed(false);
        tablaActividades.setDefaultRenderer(Object.class, new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				JLabel lbl = new JLabel();
				lbl.setHorizontalAlignment(JLabel.CENTER);
				if (column == 0) {
					lbl.setText(value.toString());
					lbl.setFont(new Font("Arial", Font.BOLD, 18));
				} else {
					if (value != null) {
						lbl.setOpaque(true);
						
						Sesion s = (Sesion) value;
						lbl.setBackground(s.getActividad().getTipo().getColor());
						String nombreFoto = s.getActividad().getTipo().toString().toLowerCase()+".png";
						ImageIcon im = new ImageIcon("resources/images/"+nombreFoto);
						lbl.setIcon(im);
						lbl.setToolTipText(s.getActividad().getTipo().toString());
					}

				}
				
				return lbl;
			}
		});
        
        
       
        tablaActividades.getTableHeader().setDefaultRenderer(new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				JLabel lbl = new JLabel(value.toString());
				lbl.setHorizontalAlignment(JLabel.CENTER);
				lbl.setFont(new Font("Arial", Font.BOLD, 18));
				return lbl;
			}
		});
        
        tablaActividades.setShowGrid(false); // que la tabla no tenga linea de cuadricula
        
        //TODO: TAREA 3: Mostrar un mensaje de confirmación para cerrar de la aplicación al pulsar CTRL + E.
        
          KeyListener kL = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// presionar y soltar una tecla
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// que queremos que pase al soltar la tecla
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// que queremos que pase al presionar
				if (e.getKeyCode() == KeyEvent.VK_E && e.isControlDown()) {
					int opcion = JOptionPane.showConfirmDialog(null, "¿Quieres salir?");	// LINEA MUY IMPORTANTE, SACA UNA VENTANA EMERJENTE CON RESPUESTA SI/NO		
					if (opcion == 0) { // devuelve un 0 si selecciona sí, un 1 si selecciona no y un 2 si es cancelar
						System.exit(0);
					}
				}				
			}
		};
		// si hace doble click en la ventana tambien se cierra:
		MouseListener mouseL = new MouseListener() {
			
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
				tablaActividades.setBorder(null);
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				tablaActividades.setBorder(BorderFactory.createLineBorder(Color.PINK));
	
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					System.exit(0);
				}
				// si hace click derecho:
				if (e.getButton() == MouseEvent.BUTTON3) {
					System.exit(0);
				}
				
			}
		};
		
		
		
		

		this.setFocusable(true);
		this.add(tablaActividades.getTableHeader(), BorderLayout.NORTH);
		this.add(scroll, BorderLayout.CENTER);
		tablaActividades.addKeyListener(kL);
		JLabel lblFooter = new JLabel("<html><a href=\"\">Icons created by Freepik - Flaticon (https://www.flaticon.com/authors/freepik)</a><html>", JLabel.RIGHT);
		lblFooter.setForeground(Color.BLUE);
		this.add(lblFooter, BorderLayout.SOUTH);

		this.setTitle("Horario Semanal de Actividades");
	   	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
    }
}