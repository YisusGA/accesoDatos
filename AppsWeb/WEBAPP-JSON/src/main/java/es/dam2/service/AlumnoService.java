package es.dam2.service;

import es.dam2.dao.AlumnoDAO;
import es.dam2.daoImpl.AlumnoDaoJson;
import es.dam2.model.Alumno;

public class AlumnoService {
	
	public void insertarAlumno (Alumno a) {
		AlumnoDAO  dao =  new AlumnoDaoJson();
		dao.insertarAlumno(a);
	}

}
