package es.dam2.daoImpl;

import java.nio.file.Path;
import java.util.List;
import es.dam2.dao.AlumnoDAO;
import es.dam2.model.Alumno;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

public class AlumnoDaoJson implements AlumnoDAO {
	
	private static Path fileAlumnos = Path.of("C:\\Users\\jezu2\\Desktop\\datos");

	@Override
	public void insertarAlumno(Alumno a) {
		ObjectMapper objectMapper = new ObjectMapper();
		// Si mi fichero json sólo tuviera un Alumno, y no necesitáramos una List, el segundo 
		// parámetro de readValue sería Alumno.class. Como nuestro json no es así, lo lógico sería
		// pensar que hay que poner List<Alumno>.class. Pero esta clase no existe. Así que lo siguiente
		// sería pensar que hay que poner List<T>.class. Pero T es un tipo genérico, y no nos permite hacerlo.
		// 
		List<Alumno> alumnos = objectMapper.readValue(fileAlumnos.toFile(), new TypeReference<List<Alumno>>() {});
		
		alumnos.add(a);
		// PrettyPrinter que escribe muy bonico, y usamos su método writeValue(File target, Object value)
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileAlumnos.toFile(), alumnos);
	}

	@Override
	public void updateAlumno(Alumno a) {
		// TODO Auto-generated method stub
		
	}

}
