package es.dam2.app;

import es.dam2.gestion.Gestion;

public class Main {

	public static void main(String[] args) {
		
		Gestion gestion = new Gestion();
		
		gestion.mostrarResponsables();
		gestion.contarInvestigaciones();
		System.out.println("El número de evidencias encontradas para el expediente EXP-001 es: " + gestion.contarEvidencias("EXP-001"));
		System.out.println("Eliminar investigaciones anteriores al año 2020");
		System.out.println("Número de investigaciones eliminadas: " + gestion.eliminarInvestigacionesAnteriores(2020));
	}

}