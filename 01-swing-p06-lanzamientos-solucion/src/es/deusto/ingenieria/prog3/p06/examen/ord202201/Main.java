package es.deusto.ingenieria.prog3.p06.examen.ord202201;

import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		try {			
			//Carga los lanzamientos del CSV, agrupados por compañía.
			GestorDatos gestorDatos = new GestorDatos();
			Map<String, List<RocketLaunch>> launches = gestorDatos.getLaunches();
			
			SwingUtilities.invokeLater(() -> new JFrameLanzamientos(launches));
		} catch (Exception ex) {
			System.err.println("Error al cargar los lanzamientos: " + ex.getMessage());
		}
	}
}
