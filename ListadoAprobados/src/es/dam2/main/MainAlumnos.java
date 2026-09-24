package es.dam2.main;

import java.io.File;
import java.nio.file.Path;

import es.dam2.service.AlumnosService;

public class MainAlumnos {

	public static void main(String[] args) {
		AlumnosService serv = new AlumnosService(new File("listadoAlumnos.txt"), Path.of("listadoAlumnos.txt"), Path.of("listadoNotas.txt"));
		System.out.println("Método java.io");
		serv.listarAprobados();
		System.out.println("Método java.nio");
		serv.listarAprobados_NIO();
		System.out.println("Expresiones lambda");
		serv.listarAprobados_NIOLambda();
		
		serv.ejemploConSupplier();
		
		serv.completaNotas();
		
		serv.listadoAprobadosSuspensos();
	}

}
