package es.deusto.ingenieria.repaso.medallero.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import es.deusto.ingenieria.repaso.medallero.domain.Medalla;
import es.deusto.ingenieria.repaso.medallero.domain.MedalleroPais;
import es.deusto.ingenieria.repaso.medallero.domain.TipoMedalla;

public class MedalleroApp {

	private static final String MEDALS_FILE = "data/medals.csv";
	private static final String MEDALLERO_FILE = "data/medallero.csv";
	
	private static final String PAIS = "country";
	private static final String CODIGO_PAIS = "country_code";
	private static final String TIPO_MEDALLA = "medal_type";
	
	private static int indicePais = -1;
	private static int indiceCodigoPais = -1;			
	private static int indiceTipoMedalla = -1;
	
	public static void main(String[] args) {		
		MedalleroApp app = new MedalleroApp();
		
		// Leemos el archivo CSV y obtenemos la lista de medallas
		List<Medalla> medallas = app.leerCSV(MEDALS_FILE);		
		app.imprimirMedallas(medallas);
		
		// Procesamos la lista de medallas y obtenemos el medallero por país
		Map<String, MedalleroPais> medallero = app.acumularMedallas(medallas);
		app.imprimirMedallero(medallero);
			
		// Imprimimos el medallero ordenado según el criterio oficial (oro, plata, bronce, nombre)
		app.imprimirMedalleroOrdenOficial(medallero);
		
		// Imprimimos el medallero ordenado por número total de medallas
		//app.imprimirMedalleroOrdenNumMedallas(medallero);
		
		// Imprimimos el medallero ordenado por nombre de país
		//app.imprimirMedalleroOrdenPais(medallero);
		
		// Imprimimos el medallero ordenado únicamente por número de tipos de medallas (sin tener en cuenta el nombre)
		//app.imprimirMedalleroSoloMetales(medallero);
		
		// Generamos el archivo CSV con el medallero ordenado según el criterio oficial
		List<MedalleroPais> medalleroOrdenado = new ArrayList<>(medallero.values());
		Collections.sort(medalleroOrdenado);
		app.escribirCSV(medalleroOrdenado);		
	}
	
	private void localizarIndicesCabecera(String[] cabecera) {
		for (int i = 0; i < cabecera.length; i++) {
			String columna = cabecera[i].trim().toLowerCase();
			
			if (columna.equals(PAIS)) {
				indicePais = i;
			} else if (columna.equals(CODIGO_PAIS)) {
				indiceCodigoPais = i;
			} else if (columna.equals(TIPO_MEDALLA)) {
				indiceTipoMedalla = i;
			}
		}
	}
	
	private List<Medalla> leerCSV(String path) {
		List<Medalla> medallas = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {			
			// Leemos la cabecera para localizar los índices de las columnas que nos interesan
			String linea = br.readLine();
			String[] valores = linea.split(",");			
			localizarIndicesCabecera(valores);						
			
			while ((linea = br.readLine()) != null) {
				valores = linea.split(",");

				TipoMedalla tipoMedalla = TipoMedalla.fromString(valores[indiceTipoMedalla].trim().toUpperCase());		
				
				if (tipoMedalla != null) {				
					medallas.add(new Medalla(valores[indicePais].trim(), valores[indiceCodigoPais].trim(), tipoMedalla));
				}
				
			}
		} catch (IOException e) {
			System.err.println("Error al leer el archivo CSV: " + e.getMessage());
		}
		
		System.out.println("\nArchivo CSV leído correctamente: " + MEDALS_FILE);
		
		return medallas;
	}
	
	private void escribirCSV(List<MedalleroPais> medallero) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEDALLERO_FILE))) {			
			// Escribimos la cabecera del archivo CSV
			writer.write("country_code,country,gold_medals,silver_medals,bronze_medals,total_medals");
			
			for (MedalleroPais pais : medallero) {
				writer.newLine();
				writer.write(pais.toCSV());				
			}			
		} catch (IOException e) {
			System.err.println("Error al escribir el archivo CSV: " + e.getMessage());
		}
		
		System.out.println("\nArchivo CSV generado correctamente: " + MEDALLERO_FILE);
	}
	
	private Map<String, MedalleroPais> acumularMedallas(List<Medalla> medallas) {
		Map<String, MedalleroPais> medallero = new java.util.HashMap<>();
		
		for (Medalla medalla : medallas) {
			String pais = medalla.getPais().getNombre();
			medallero.putIfAbsent(pais, new MedalleroPais(medalla.getPais()));			
			medallero.get(pais).agregarMedalla(medalla.getTipo());
		}
		
		return medallero;
	}
	
	
	protected void imprimirMedallas(List<Medalla> medallas) {
		System.out.println("Listado de medallas:");		
		medallas.forEach(m -> System.out.println(m));
	}
	
	protected void imprimirMedallero(Map<String, MedalleroPais> medallero) {
		System.out.println("\nMedallero sin ordenación:");
		medallero.values().forEach(m -> System.out.println(m));
	}
	
	protected void imprimirMedalleroOrdenOficial(Map<String, MedalleroPais> medallero) {		
		List<MedalleroPais> medalleroOrdenado = new ArrayList<>(medallero.values());
		Collections.sort(medalleroOrdenado);
		
		System.out.println("\nMedallero con ordenación oficial:");
		
		for (int i=0; i<medalleroOrdenado.size(); i++) {
			System.out.println(String.format("%d. %s", (i+1), medalleroOrdenado.get(i)));
		}
	}
	
	protected void imprimirMedalleroOrdenNumMedallas(Map<String, MedalleroPais> medallero) {
		// Comparador para ordenar por número total de medallas
		Comparator<MedalleroPais> comparador = new Comparator<MedalleroPais>() {
		    @Override
		    public int compare(MedalleroPais o1, MedalleroPais o2) {
		        return Integer.compare(o2.getTotalMedallas(), o1.getTotalMedallas());
		    }
		};

		List<MedalleroPais> medalleroOrdenado = new ArrayList<>(medallero.values());
		Collections.sort(medalleroOrdenado, comparador);
		
		System.out.println("\nMedallero con ordenación por total de medallas:");
		
		for (int i=0; i<medalleroOrdenado.size(); i++) {
			System.out.println(String.format("%d. %s", (i+1), medalleroOrdenado.get(i)));
		}
	}
	
	protected void imprimirMedalleroOrdenPais(Map<String, MedalleroPais> medallero) {
		// Comparador para ordenar por nombre de país
		Comparator<MedalleroPais> comparador = new Comparator<MedalleroPais>() {
		    @Override
		    public int compare(MedalleroPais o1, MedalleroPais o2) {
		        return o1.getPais().getNombre().compareTo(o2.getPais().getNombre());
		    }
		};

		List<MedalleroPais> medalleroOrdenado = new ArrayList<>(medallero.values());
		Collections.sort(medalleroOrdenado, comparador);
		System.out.println("\nMedallero con ordenación por nombre de país:");
		
		for (int i=0; i<medalleroOrdenado.size(); i++) {
			System.out.println(String.format("%d. %s", (i+1), medalleroOrdenado.get(i)));
		}
	}
	
	protected void imprimirMedalleroSoloMetales(Map<String, MedalleroPais> medallero) {
		// Comparador para ordenar únicamente por número de tipos de medallas (sin tener en cuenta el nombre)
		Comparator<MedalleroPais> comparador = new Comparator<MedalleroPais>() {
		    @Override
		    public int compare(MedalleroPais o1, MedalleroPais o2) {
		    	int comparacion = Integer.compare(o2.getMedallas(TipoMedalla.GOLD), o1.getMedallas(TipoMedalla.GOLD));
				
				if (comparacion == 0) {
					comparacion = Integer.compare(o2.getMedallas(TipoMedalla.SILVER), o1.getMedallas(TipoMedalla.SILVER));
					
					if (comparacion == 0) {
						comparacion = Integer.compare(o2.getMedallas(TipoMedalla.BRONCE), o1.getMedallas(TipoMedalla.BRONCE));
					}
				}
				
				return comparacion;
		    }
		};

		List<MedalleroPais> medalleroOrdenado = new ArrayList<>(medallero.values());
		Collections.sort(medalleroOrdenado, comparador);
		System.out.println("\nMedallero compartiendo posición por metales:");
		
		int ranking = 1;
		MedalleroPais ultimoPais = null;

		//Como ya hemos ordenado la lista, podemos recorrerla y comparar cada país con el anterior para determinar si hay empate o no.
		//Si hay empates, se debe mostrar el mismo ranking para los países empatados.
		for (MedalleroPais paisActual : medalleroOrdenado) {

			if (ultimoPais != null) {
				if (comparador.compare(paisActual, ultimoPais) != 0) {
					ranking++;
				}
			}
			
			System.out.println(String.format("%d. %s", ranking, paisActual));
			ultimoPais = paisActual;
		}
		
	}
}
