package es.dam2.implementaciones;

import es.dam2.interfaces.Calcular;

public class Sumar implements Calcular {

	@Override
	public int operar(int x, int y) {
		
		return x + y;
	}

}
