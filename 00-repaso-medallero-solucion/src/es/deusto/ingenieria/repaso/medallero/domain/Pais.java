package es.deusto.ingenieria.repaso.medallero.domain;

import java.util.Objects;

public class Pais {
	private String nombre;
	private String codigo;

	public Pais(String nombre, String codigo) {
		this.nombre = nombre;
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCodigo() {
		return codigo;
	}
	
	// Override toString
	@Override
	public String toString() {
		return String.format("%s - %s", nombre, codigo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pais other = (Pais) obj;
		return Objects.equals(codigo, other.codigo);
	}	
}
