package es.dam2.utilidades;

import java.nio.file.Path;

import es.dam2.model.Alumno;
import tools.jackson.databind.ObjectMapper;

public class UtilidadesJson {
	
	public static Alumno leeAlumno(Path fichero) {
		ObjectMapper objectMaper = new ObjectMapper();
		return objectMaper.readValue(fichero.toFile(), Alumno.class);
	}

}
