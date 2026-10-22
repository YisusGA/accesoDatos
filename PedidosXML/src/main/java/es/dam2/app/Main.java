package es.dam2.app;

import java.io.FileNotFoundException;
import es.dam2.gestion.GestionPedidos;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		String nifTest = "" + 1;
		
		GestionPedidos gestion = new GestionPedidos();
//		gestion.insertarPedido(gestion.crearPedido(gestion.crearListaProductos()));
		gestion.mostrarPedidosCliente(nifTest);
		System.out.printf("El cliente con nif %s ha gastado %.2f", nifTest, gestion.devolverGastosCiente(nifTest));
	}

}
