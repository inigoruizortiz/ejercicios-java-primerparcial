package es.deusto.ingenieria.repaso.medallero.domain;

import java.util.HashMap;
import java.util.Map;

public class MedalleroPais implements Comparable<MedalleroPais> {
	private Pais pais;
	private Map<TipoMedalla, Integer> medallas;
	
	public MedalleroPais(Pais pais) {
		this.pais = pais;
		this.medallas = new HashMap<>();
		
		// Inicializamos el mapa de medallas con 0 para cada tipo de medalla
		for (TipoMedalla tipo : TipoMedalla.values()) {
			medallas.put(tipo, 0);
		}
	}
	
	public Pais getPais() {
		return pais;
	}
	
	public Map<TipoMedalla, Integer> getMedallas() {
		return medallas;
	}
		
	public int getMedallas(TipoMedalla tipo) {
		return medallas.get(tipo);
	}
	
	public int getTotalMedallas() {
		int resultado = 0;
		
		for (Integer cantidad : medallas.values()) {
			resultado += cantidad;
		}
		
		return resultado;
	}
	
	// Agrega una medalla del tipo especificado
	public void agregarMedalla(TipoMedalla tipo) {
		medallas.put(tipo, medallas.get(tipo) + 1);
	}
	
	@Override
	public String toString() {
		return String.format("%s -> GOLD: %d, SILVER: %d, BRONCE: %d, Total: %d", 
				pais.getNombre(), 
				medallas.get(TipoMedalla.GOLD), 
				medallas.get(TipoMedalla.SILVER), 
				medallas.get(TipoMedalla.BRONCE),
				getTotalMedallas());
	}
	
	// Devuelve una representación en formato CSV de la información del país y sus medallas
	public String toCSV() {
		return String.format("%s,%s,%d,%d,%d,%d", 
				pais.getCodigo(),
				pais.getNombre(),				
				medallas.get(TipoMedalla.GOLD), 
				medallas.get(TipoMedalla.SILVER), 
				medallas.get(TipoMedalla.BRONCE),
				getTotalMedallas());
	}

	@Override
	public int compareTo(MedalleroPais o) {
		// El criterio de comparación es:
		// 1. Número de medallas de oro (mayor primero)
		// 2. Número de medallas de plata (mayor primero)
		// 3. Número de medallas de bronce (mayor primero)
		// 4. Orden alfabético del código del país (menor primero)
		int comparacion = Integer.compare(o.getMedallas(TipoMedalla.GOLD), this.getMedallas(TipoMedalla.GOLD));
		
		if (comparacion == 0) {
			comparacion = Integer.compare(o.getMedallas(TipoMedalla.SILVER), this.getMedallas(TipoMedalla.SILVER));
			
			if (comparacion == 0) {
				comparacion = Integer.compare(o.getMedallas(TipoMedalla.BRONCE), this.getMedallas(TipoMedalla.BRONCE));
				
				if (comparacion == 0) {
					comparacion = this.pais.getCodigo().compareTo(o.getPais().getCodigo());
				}
			}
		}
		
		return comparacion;
	}
}
