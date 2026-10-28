package es.dam2.app;

import es.dam2.logica.GestionBiblioteca;

public class Main {

	public static void main(String[] args) {
		GestionBiblioteca gestionBiblioteca = new GestionBiblioteca();
		
		gestionBiblioteca.mostrarTitulos();
		gestionBiblioteca.cambiarNacionalidad("Carlos Ruiz Zafón", "Española");

	}

}
