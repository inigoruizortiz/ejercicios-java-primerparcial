package es.deusto.ingenieria.repaso.medallero.domain;

public class Medalla {
	private Pais pais;
	private TipoMedalla tipo;
	
	public Medalla(String nombrePais, String codigoPais, TipoMedalla tipo) {
		this.pais = new Pais(nombrePais, codigoPais);
		this.tipo = tipo;
	}
	
	public Pais getPais() {
		return pais;
	}
	
	public TipoMedalla getTipo() {
		return tipo;
	}	
	
	@Override
	public String toString() {				
		return String.format("%s -> %s", pais, tipo);
	}	
}
