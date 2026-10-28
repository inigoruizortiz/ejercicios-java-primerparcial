package es.deusto.ingenieria.prog3.p06.examen.ord202201;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Lee los lanzamientos del CSV y los agrupa por compañía. */
public class GestorDatos {
    private final String archivo;

    /** Ejecutar desde la raíz del proyecto. */
    public GestorDatos() {
        this("resources/data/launches.csv");
    }

    public GestorDatos(String archivo) {
        this.archivo = archivo;
    }

    public Map<String, List<RocketLaunch>> getLaunches() throws IOException {
        Map<String, List<RocketLaunch>> launches = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo, StandardCharsets.UTF_8))) {
            reader.readLine(); // Omitir la cabecera.
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(";");
                RocketLaunch launch = new RocketLaunch(
                        campos[0],
                        LocalDateTime.parse(campos[1]),
                        campos[2],
                        campos[3],
                        Float.parseFloat(campos[4]),
                        campos[5].equals("1"));

                launches.putIfAbsent(launch.getCompany(), new ArrayList<>());
                launches.get(launch.getCompany()).add(launch);
            }
        }

        return launches;
    }
}
