package es.dam2.app;

import java.io.FileNotFoundException;
import es.dam2.gestion.GestionPedidos;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		GestionPedidos gestion = new GestionPedidos();
		gestion.insertarPedido(gestion.crearPedido(gestion.crearListaProductos()));
	}

}
