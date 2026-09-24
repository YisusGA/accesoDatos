package es.dam2.main;

import java.util.Scanner;
import java.util.function.BiFunction;

import es.dam2.implementaciones.Multiplicar;
import es.dam2.implementaciones.Sumar;
import es.dam2.interfaces.Calcular;
import es.dam2.service.OperacionesService;

public class MainOperaciones {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		System.out.println("Primer operando");
		int x = scan.nextInt();
		System.out.println("Segundo operando");
		int y = scan.nextInt();

		OperacionesService serv = new OperacionesService();
		System.out.println("La suma es: ");
		int suma = serv.calcular(x, y, new Sumar()); // El tercer parámetro debe ser un objeto que implemente Calcular,
														// pues Calcular, al ser una interfaz, no puede instanciarse
		System.out.println(suma);

		System.out.println("La multiplicación es: ");
		int multiplicacion = serv.calcular(x, y, new Multiplicar()); // El tercer parámetro debe ser un objeto que
																		// implemente Calcular, pues Calcular, al ser
																		// una interfaz, no puede instanciarse
		System.out.println(multiplicacion);

		// Para evitar tener que crear (e instanciar) las implementaciones de Calcular,
		// podemos usar 2 estrategias: clases anónimas o expresiones lambda

		// OPCIÓN 1: Clases anónimas
		System.out.println("La suma es: ");
		suma = serv.calcular(x, y, new Calcular() {

			@Override
			public int operar(int x, int y) {
				return x + y;
			}

		});
		System.out.println(suma);

		System.out.println("La multiplicación es: ");
		multiplicacion = serv.calcular(x, y, new Calcular() {

			@Override
			public int operar(int x, int y) {
				return x * y;
			}

		});

		// OPCIÓN 2: Expresiones lambda
		System.out.println("La suma es: ");

		// Si la expresión lambda va a tener varias líneas (no sería este el caso), se
		// abren llaves, se escriben todas las líneas y se debe poner un return
//		suma = serv.calcular(x, y, (a,b)-> {
//			return a + b;
//		});

		// Como en este caso sólo es una línea, se escribe la expresión lambda de forma
		// simplificada, y el return es lo que está a la derecha de la flecha ->
		// Como el método abstracto de la interfaz funcional requiere más de 1 parámetro
		// (2), se deben meter entre paréntesis y separados por coma. Si fuera sólo un
		// parámetro, no haría falta poner paréntesis
		suma = serv.calcular(x, y, (a, b) -> a + b);
		System.out.println(suma);

		System.out.println("La multiplicación es: ");
		multiplicacion = serv.calcular(x, y, (a, b) -> a * b);
		System.out.println(multiplicacion);

		// Usando el método calcularChuli(int a, int b, BiFunction<Integer, Integer,
		// Integer> calc) que hemos creado en OperacionesService, ya no nos haría falta
		// crear la interfaz funcional Calcular, ni sus implementaciones Sumar y
		// Multiplicar
		suma = serv.calcularChuli(x, y, (a, b) -> a + b);
		System.out.println(suma);

		System.out.println("La multiplicación es: ");
		multiplicacion = serv.calcularChuli(x, y, (a, b) -> a * b);
		System.out.println(multiplicacion);

		scan.close();
	}

}
