package es.dam2.implementaciones;

import es.dam2.interfaces.Calcular;

public class Multiplicar implements Calcular {
	
	@Override
	public int operar(int x, int y) {
		
		return x * y;
	}

}
