package es.deusto.ingenieria.repaso.medallero.domain;

public enum TipoMedalla {
	GOLD("GOLD MEDAL"),
	SILVER("SILVER MEDAL"),
	BRONCE("BRONZE MEDAL");
	
	private String descripcion;
	
	private TipoMedalla(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	// Obtiene el tipo de medalla a partir de su descripción.
	public static TipoMedalla fromString(String descripcion) {
		for (TipoMedalla tipo : TipoMedalla.values()) {
			if (tipo.descripcion.equalsIgnoreCase(descripcion)) {
				return tipo;
			}
		}
		
		// Si no se encuentra el tipo de medalla se devuelve null.
		return null;
	}
}
