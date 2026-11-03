package es.deusto.prog.examen.parcial.render;

import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import es.deusto.prog.examen.parcial.Actividad;
import es.deusto.prog.examen.parcial.Sesion;
import es.deusto.prog.examen.parcial.Sesion.DiaSemana;

public class HorarioGimnasioModelo extends DefaultTableModel {
	// ESTA CLASE LA CREAMOS ENTERA NOSOTROS DESDE 0, IMPORTANTE SABERSE TODA LA ESTRUCTURA
	private List<String> lTitulos = Arrays.asList("", "LUNES", "MARTES", "MIERCOLES", "JUEVES" , "VIERNES");
	private Object [][] matriz;
	

	public HorarioGimnasioModelo(List<Actividad> l) {
		
		matriz = new Object [5][6];
		// guardamos las horas en la columna 0
		for (int i = 0; i < matriz.length; i++) {
			matriz[i][0] = (i + 9 )+ ":00";
			
		}
		// recorremos la lista de actividades para guardar cada sesion de actividad en la posicion que le corresponda
		for (Actividad a : l) {
			
			for (Sesion s : a.getSesiones()) {
				int hora = s.getHoraInicio();
				DiaSemana dia = s.getDia();
				int fila = hora - 9;
				int columna;
				switch (dia) {
				case LUNES -> columna = 1;
				case MARTES -> columna =2;
				case MIERCOLES -> columna =3;
				case JUEVES -> columna = 4;
				case VIERNES -> columna = 5;
				default -> columna = 0;
				}
				matriz[fila][columna] = s;
				
				}
			}
		
		
		}


	@Override
	public int getRowCount() {
		if(matriz == null) {
			return 0;
		} else {
			return matriz.length;
		}
	}


	@Override
	public int getColumnCount() {
		return lTitulos.size();
	}
													
																						
	@Override
	public String getColumnName(int column) {
		return lTitulos.get(column);
	}


	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}


	@Override
	public Object getValueAt(int row, int column) {
		return matriz[row][column];
	}
	}


