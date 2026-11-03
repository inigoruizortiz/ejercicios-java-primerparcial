package es.deusto.prog3.examen.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import es.deusto.prog3.examen.domain.Emparejamiento;

public class VentanaEmparejamientos extends JFrame {
		
	private static final long serialVersionUID = 1L;
	
	private int MAX_EMPAREJAMIENTOS = 3;	
	
	private JPanel pCentro;
	
	public VentanaEmparejamientos(Map<LocalDate, List<Emparejamiento>> emparejamientos) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Emparejamientos Campeonato");
		setSize(800, 600);
		setLocationRelativeTo(null);
		
		DefaultListModel<LocalDate> modeloLista = new DefaultListModel<>();	
		for (LocalDate dia : emparejamientos.keySet()) {
			modeloLista.addElement(dia);
		}
		
		JList<LocalDate> jList = new JList<>(modeloLista);
		add(jList, BorderLayout.WEST);
		
		jList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					List<Emparejamiento> emparejamientosDia = emparejamientos.get(jList.getSelectedValue());
					mostrarEmparejamientos(emparejamientosDia);
					revalidate();
				}
			}
		});
		
		setVisible(true);
	}
	
	private void mostrarEmparejamientos(List<Emparejamiento> emparejamientosDia) {
		// TAREA 1.1 Implementa aquí la tarea
		pCentro = new JPanel( new GridLayout(3, 2, 20, 20));
		add(pCentro, BorderLayout.CENTER);
		
		pCentro.setBackground(Color.WHITE);
		for (Emparejamiento emparejamiento : emparejamientosDia) {
			
			// para el jugador1:
			JPanel pJugador1 = new JPanel(new BorderLayout());
			JLabel lblNorte = new JLabel("Emparejamiento"+emparejamiento.getHora().toString());
			lblNorte.setOpaque(true);
			lblNorte.setBackground(Color.LIGHT_GRAY);
			pJugador1.add(lblNorte, BorderLayout.NORTH);
			JLabel lblNombre = new JLabel( "nombre:"+emparejamiento.getJugador1().getNombre());
			JLabel lblApellidos = new JLabel("apellidos:"+emparejamiento.getJugador1().getApellido());
			JLabel lblPuntuacion = new JLabel("puntuacion:" + emparejamiento.getJugador1().getPuntuacion() );
			JLabel lblPais = new JLabel("pais:" + emparejamiento.getJugador1().getPais() );
			JPanel pInfo = new 	JPanel(new GridLayout(4, 1));
			pInfo.add(lblNombre);
			pInfo.add(lblApellidos);
			pInfo.add(lblPuntuacion);
			pInfo.add(lblPais);
			pInfo.setBackground(Color.WHITE);
			pJugador1.add(pInfo, BorderLayout.CENTER);
			
			JLabel lblFoto = new JLabel();
			lblFoto.setIcon(cargarImagen(emparejamiento.getJugador1().getPerfil(), new Dimension(50, 50)));
			JPanel pFoto = new JPanel();
			pFoto.add(lblFoto); // para que la foto salga arriba
			pFoto.setOpaque(true);
			pFoto.setBackground(Color.WHITE);
			pJugador1.add(pFoto, BorderLayout.EAST);
			lblFoto.setOpaque(true);
			lblFoto.setBackground(Color.WHITE);
			pCentro.add(pJugador1);
			
			// para el jugador 2: FALTAN COSAS PARA EL JUGADOR 2 PERO ES IGUAL QUE PARA EL 1 EL CODIGO
			JPanel pJugador2 = new JPanel(new BorderLayout());
		
			JLabel lblNombre2 = new JLabel( "nombre:"+emparejamiento.getJugador2().getNombre());
			JLabel lblApellidos2 = new JLabel("apellidos:"+emparejamiento.getJugador2().getApellido());
			JLabel lblPuntuacion2 = new JLabel("puntuacion:" + emparejamiento.getJugador2().getPuntuacion() );
			JLabel lblPais2 = new JLabel("pais:" + emparejamiento.getJugador2().getPais() );
			JPanel pInfo2= new 	JPanel(new GridLayout(4, 1));
			pInfo2.add(lblNombre2);
			pInfo2.add(lblApellidos2);
			pInfo2.add(lblPuntuacion2);
			pInfo2.add(lblPais2);
			pInfo2.setBackground(Color.WHITE);
			pJugador2.add(pInfo2, BorderLayout.CENTER);
			
			JLabel lblFoto2 = new JLabel();
			lblFoto2.setIcon(cargarImagen(emparejamiento.getJugador2().getPerfil(), new Dimension(50, 50)));
			pJugador2.add(lblFoto2, BorderLayout.EAST);
			pCentro.add(pJugador2);
			
		}
		
		

	}
	
	private ImageIcon cargarImagen(String imagen, Dimension dimension) {
		ImageIcon imageIcon = new ImageIcon(imagen);
		Image img = imageIcon.getImage();
		Image newimg = img.getScaledInstance((int) dimension.getWidth(), (int) dimension.getHeight(),  java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}
}
