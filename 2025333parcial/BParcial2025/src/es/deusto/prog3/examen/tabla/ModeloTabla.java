package es.deusto.prog3.examen.tabla;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.deusto.prog3.examen.domain.PiezaAjedrez;

public class ModeloTabla extends DefaultTableModel { // acordarse de poner el extends DefaultTableModel
	// ESTA CLASE LA CREAMOS ENTERA NOSOTROS DESDE 0, IMPORTANTE SABERSE TODA LA ESTRUCTURA
	private List<String> lTitulos = Arrays.asList("", "a", "b", "c", "d", "e", "f", "g","h", "");
	private Object[][] matriz;
	
	public ModeloTabla(PiezaAjedrez[][] tablero) { // el constructor siempre debe recibir algo, depende de lo que nos den en el examen
		matriz = new Object[9][10];
		
		// guardamos los numeros de las columans 0 y 9
		int num = 8;
		for (int i = 0; i <8; i++) {
			matriz[i][0] = num;
			matriz[i][9] = num;
			num--;
		
		}
		// guardamos las letras de la ultima fila
		for (int i = 0; i < lTitulos.size(); i++) {
			matriz[8][i] = lTitulos.get(i);
			
			
		}
		// guardamos las piezas
		for (int i = 0; i < tablero.length; i++) {
			
			for (int j = 0; j < tablero[i].length; j++) { // FOR MUY IMPORTANTE RECORDAR PARA EL EXAMEN
				matriz[i][j+1] = tablero[i][j]; // la columna 0 de nuestra matriz es la columna 1 de la matriz tablero
				
				
				
			}
			
		}
		
		
	}

	// METODOS QUE SIEMPRE HAY QUE PONER: 
	
	@Override
	public int getRowCount() {
		if(matriz == null) {
			return 0;
		}
		return matriz.length;
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
