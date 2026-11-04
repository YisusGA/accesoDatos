package es.dam2.main;

import es.dam2.dao.*;
import es.dam2.servicio.*;
import java.io.File;

public class Main {
	public static void main(String[] args) {
		
		ExpedienteDAO dao = new ExpedienteDAOXml(new File("datos/fenomenos_original.xml"),
				new File("datos/fenomenos_salida.xml"));
		ObservatorioService servicio = new ObservatorioService(dao);
		System.out.printf("Lista de expedientes\n--------------------\n");
		servicio.imprimirResumen();
		System.out.printf("\nInvestigaciones entre 2018 y 2024: %d\n\n", dao.contarInvestigaciones(2018, 2024));
		System.out.printf("Evidencia audio: %d\n\n", dao.buscarPorTipoEvidencia("audio").size());
		System.out.printf("Búsqueda EXP-003: %s\n\n", dao.buscarPorCodigo("EXP-003") != null ? dao.buscarPorCodigo("EXP-003").getTitulo() : "No se encontró expediente con ese código");
		System.out.printf("Actividad: %s\n\n", servicio.actividadPorResponsable());
		// Probamos métodos que modifican el ficheo 
		if (dao.cerrarExpediente("EXP-001")) {
			System.out.printf("Expediente cerrado\n\n");
		} else {
			System.err.printf("No se encontró el expediente\n\n");
		}
	}
}