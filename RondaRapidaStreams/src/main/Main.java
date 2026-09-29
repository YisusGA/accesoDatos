package main;

import java.util.List;
import java.util.function.Supplier;

public class Main {

	private static List<Integer> numeros = List.of(3, 8, 12, 5, 7, 20, 1, 14);
	private static List<String> nombres = List.of("Ana", "Pedro", "Luis", "Marta", "Alberto", "Lucía");

	public static void main(String[] args) {
		
		System.out.println("---------------------");
		System.out.println("Números mayores de 10");
		System.out.println("---------------------");
		numeros.stream().filter(x -> x > 10).forEach(System.out::println);
		
		System.out.println("---------------------");
		System.out.println("Números pares");
		System.out.println("-------------");
		numeros.stream().filter(x -> x % 2 == 0).forEach(System.out::println);
		
		System.out.println("---------------------");
		System.out.println("Números multiplicados por 2");
		System.out.println("---------------------");
		numeros.stream().map(x -> x *2).forEach(System.out::println);
		
		System.out.println("---------------------");
		System.out.println("Números impares multiplicados por 3");
		System.out.println("---------------------");
		numeros.stream().filter(x -> x % 2 != 0).map(x -> x * 3).forEach(System.out::println);
		
		System.out.println("---------------------");
		System.out.println("Contaje de números mayores que 5: " + numeros.stream().filter(x -> x > 5).count());
		System.out.println("---------------------");
		
		System.out.println("---------------------");
		Supplier<Integer> sp = () -> {return -1;};
		System.out.println("Primer número mayor que 10: " + numeros.stream().filter(x -> x > 10).findFirst().orElseGet(sp));
		System.out.println("---------------------");
		
		
		System.out.println("---------------------");
		// La forma recomendada de hacerlo
		System.out.println("Suma de todos los números: " + numeros.stream().mapToInt(Integer::intValue).sum());
		// Otra forma de hacerlo
//		System.out.println("Suma de todos los números: " + numeros.stream().collect(Collectors.summingInt(Integer::intValue)));
		// Otra forma de hacerlo
//		System.out.println("Suma de todos los números: " + numeros.stream().collect(Collectors.summingInt(n -> n)));
		System.out.println("---------------------");
		
		System.out.println("---------------------");
		System.out.println("Número máximo: " + numeros.stream().max(Integer::compareTo).get());
		System.out.println("---------------------");
		
		System.out.println("---------------------");
		System.out.println("Nombres que empiezan por A");
		System.out.println("---------------------");
		nombres.stream().filter(x -> x.startsWith("A")).forEach(System.out::println);
		
		System.out.println("---------------------");
		System.out.println("Nombres en mayúsculas");
		System.out.println("---------------------");
		nombres.stream().map(x -> x.toUpperCase()).forEach(System.out::println);
		
		System.out.println("---------------------");
		System.out.println("Nombres con más de 4 caracteres");
		System.out.println("---------------------");
		nombres.stream().filter(x -> x.length() > 4).forEach(System.out::println);
	}

}
