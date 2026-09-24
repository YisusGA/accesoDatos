package es.dam2.service;

import java.util.function.BiFunction;

import es.dam2.interfaces.Calcular;

public class OperacionesService {
	
	public int calcular(int a, int b, Calcular calc) {
		return calc.operar(a, b);
	}
	
	// Sin usar Calcular. No haría falta crear la interfaz funcional Calcular ni sus implementaciones (Sumar y Multiplicar)
	public int calcularChuli(int a, int b, BiFunction<Integer, Integer, Integer> calc) {
		return calc.apply(a, b); // Apply es el método abstracto de BiFunction
	}
}
