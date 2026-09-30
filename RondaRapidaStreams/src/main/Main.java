package main;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {

	// Al crear una lista con el método List.of(), se crea una lista inmutable, es
	// decir, no se pueden añadir ni eliminar elementos
	private static List<Integer> numeros = List.of(3, 8, 12, 5, 7, 20, 1, 14);
	private static List<String> nombres = List.of("Ana", "Pedro", "Luis", "Marta", "Alberto", "Lucía");

	public static void main(String[] args) throws Exception {
		
		// 1
		System.out.println("---------------------");
		System.out.println("Números mayores de 10");
		System.out.println("---------------------");
		numeros.stream().filter(x -> x > 10).forEach(System.out::println);

		// 2
		System.out.println("---------------------");
		System.out.println("Números pares");
		System.out.println("-------------");
		numeros.stream().filter(x -> x % 2 == 0).forEach(System.out::println);

		// 3
		System.out.println("---------------------");
		System.out.println("Números multiplicados por 2");
		System.out.println("---------------------");
		numeros.stream().map(x -> x * 2).forEach(System.out::println);

		// 4
		System.out.println("---------------------");
		System.out.println("Números impares multiplicados por 3");
		System.out.println("---------------------");
		numeros.stream().filter(x -> x % 2 != 0).map(x -> x * 3).forEach(System.out::println);

		// 5
		System.out.println("---------------------");
		System.out.println("Contaje de números mayores que 5: " + numeros.stream().filter(x -> x > 5).count());
		System.out.println("---------------------");

		// 6
		System.out.println("---------------------");
		// En el .orElseGet(), pasamos como parámetro una función lambda para crear un
		// Supplier
		System.out.println("Primer número mayor que 10: "
				+ numeros.stream().filter(x -> x > 10).findFirst().orElseGet(() -> (int) (Math.random() * 10) + 10));
		// Podemos hacer que si hay un Null en el Optional, devuelva una Exception
		// personalizada nuestra. Y usamos el constructor de la Exception para darle el
		// mensaje personalizado. Y luego propagamos la Exception hacia arriba
		System.out.println("Primer número mayor que 10: " + numeros.stream().filter(x -> x > 10).findFirst()
				.orElseThrow(() -> new Exception("No hay números mayores que 10")));
		System.out.println("---------------------");

		// 7
		System.out.println("---------------------");
		// Un stream sólo te ofrece operaciones que se puedan hacer con sus elementos.
		// Para hacer la operación suma, hace falta que los elementos sean int, no
		// Integer. Y para ello, usamos el método mapToInt del stream, y le pasamos como
		// parámetro la lambda Integer::intValue, que sería la forma abreviada de poner
		// x -> x.intValue
		
		// La forma recomendada de hacerlo
		System.out.println("Suma de todos los números: " + numeros.stream().mapToInt(Integer::intValue).sum());
		// Otra forma de hacerlo
//		System.out.println("Suma de todos los números: " + numeros.stream().collect(Collectors.summingInt(Integer::intValue)));
		// Otra forma de hacerlo
//		System.out.println("Suma de todos los números: " + numeros.stream().collect(Collectors.summingInt(n -> n)));
		// Una forma más enrevesada, usando un collect con 3 parámetros: Supplier, un BiConsumer acumulador y un BiConsumer combinador
		System.out.println("Suma de todos los números: " + numeros.stream().collect(
				() -> {
					int[] acum = new int[1]; // Creamos un array con 1 elemento
					acum[0] = 0; // Comenzamos asignándole el valor 0 a ese elemento
					return acum; // Devolvemos un array con 1 elemento
					  },
				(acum, x) -> acum[0] += x, // A la posición 0 del array acumulador, le sumamos el nuevo valor que entre del stream
				(acum1, acum2) -> acum1[0] += acum2[0] // Procesamiento en paralelo, tenemos más de 1 hilo acumulando y sumamos lo que lleven acumulado los hilos
				)[0]);// Recuperamos la posición 0 del array que devuelve el collect
		System.out.println("---------------------");

		// 8
		System.out.println("---------------------");
		System.out.println("Número máximo: " + numeros.stream().max(Integer::compareTo).get());
		System.out.println("---------------------");

		// 9
		System.out.println("---------------------");
		System.out.println("Nombres que empiezan por A");
		System.out.println("---------------------");
		nombres.stream().filter(x -> x.startsWith("A")).forEach(System.out::println);

		// 10
		System.out.println("---------------------");
		System.out.println("Nombres en mayúsculas");
		System.out.println("---------------------");
		nombres.stream().map(x -> x.toUpperCase()).forEach(System.out::println);

		// 11
		System.out.println("---------------------");
		System.out.println("Nombres con más de 4 caracteres");
		System.out.println("---------------------");
		nombres.stream().filter(x -> x.length() > 4).forEach(System.out::println);
	}

}
