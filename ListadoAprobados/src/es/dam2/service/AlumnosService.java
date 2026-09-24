package es.dam2.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlumnosService {

	private File alumnos; // El fichero va a ser común a todos los métodos de la clase, así que tiene
							// sentido que el fichero sea una propiedad de la clase
	private Path pathAlumnos;
	private Path pathNotas;

	public AlumnosService(File alumnos, Path pathAlumnos, Path pathNotas) { // Aquí se le pasa por constructor el
																			// fichero, en el momento
		// de instanciar la
		// clase. Esto se llama inyección de dependencias
		// No se suelen pasar los 2 por constructor, pero en este caso, lo vamos a hacer
		this.alumnos = alumnos;
		this.pathAlumnos = pathAlumnos;
		this.pathNotas = pathNotas;
	}

	public void listarAprobados() {
		try (BufferedReader br = new BufferedReader(new FileReader(alumnos))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.endsWith("APROBADO")) {
					System.out.println(line.substring(0, line.indexOf(";"))); // El método substring nos pide 2 int como
																				// parámetros, uno que indique el
																				// carácter de comienzo y otro que
																				// indique el carácter de final
				}
			}
		} catch (IOException e) {
			e.printStackTrace(); // Esto se quitaría en el momento de desplegar la app
		}
	}

	public void listarAprobados_NIO() {
		List<String> lineas;
		try {
			lineas = Files.readAllLines(pathAlumnos); // Esto es muy cómodo, pero el problema que tiene es que carga
														// TODAS las líneas del fichero en memoria, lo cual podría
														// sobrecargar mucho la memoria RAM en caso de haber muchas
														// líneas
			for (String linea : lineas) {
				if (linea.endsWith("APROBADO")) {
					System.out.println(linea.substring(0, linea.indexOf(";")));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void listarAprobados_NIOLambda() { // Con expresiones lambda
		try {
			// El método Files.lines(Path path) abre un stream, que no carga todas las
			// líneas, sino que actúa como una especie de "puerta" al fichero, que se queda
			// a la espera para hacer las cosas que se le vayan pidiendo después. Esto es lo
			// que se llama trabajar en modo "LAZY"

			// Esto genera otro stream con los elementos distintos. Es lo que se llama una
			// operación no final, pues no hace
			// nada con los elementos (no los muestra/escribe)
//			Stream<String> lineas = Files.lines(pathAlumnos).distinct();

			// Esto ya sería una operación final, pues toma el stream y genera algo con él
			// (un print por consola en este caso)
			Stream<String> lineas = Files.lines(pathAlumnos);
			lineas.filter(a -> a.endsWith("APROBADO")).forEach(a -> System.out.println(a.substring(0, a.indexOf(";"))));

			// Pasar los nombres de alumnos a mayúsculas

			// Hay que abrir de nuevo el stream, pues al haber usado una operación final
			// antes (el forEach), se cerró el stream
			lineas = Files.lines(pathAlumnos);

//			lineas.map(a -> a.substring(0, a.indexOf(";")).toUpperCase() + a.substring(a.indexOf(";"), a.length())).forEach(a -> System.out.println(a));

			// Forma abreviada del forEach: se le indica lo que se hace con el único
			// parámetro que recibe: de la clase System.out, usa el método println.
			// Esta forma de abreviar siempre que el método de la interfaz reciba el
			// parámetro tal cual, sin modificar
			// Si necesitamos modificar el parámetro de entrada antes de pasárselo, no se
			// podría usar esta forma abreviada. Por ejemplo, si quisiera
			// mostrar Hola + String: tendría que poner .forEach(a ->
			// System.out.println("Hola " + a));
			lineas.map(a -> a.substring(0, a.indexOf(";")).toUpperCase() + a.substring(a.indexOf(";"), a.length()))
					.forEach(System.out::println);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ejemploConSupplier() {
		// Un Supplier es una interfaz que te permite crear algo de la nada
		Supplier<List<String>> sp = () -> {
			System.out.println("Voy a crear una lista a través de un Supplier");
			return new ArrayList<String>();
		};

		List<String> lista = sp.get();
		lista.add("Hola");
		lista.add("usuario");
		System.out.println("Estos son los elementos que hay en la lista que hemos creado con el Supplier");
		for (String s : lista) {
			System.out.println(s);
		}
	}

	// Completa el fichero de notas: los alumnos sin nota (la línea termina en ;),
	// reciben una nota aleatoria entre 1 y 10
	public void completaNotas() {
		// Le damos comportamiento al Supplier.
		// Para redondear a 2 decimales, multiplicamos por 1000 para pasar del 0.x que
		// genera el Math.random a un número con 3 cifras, luego quitamos los decimales
		// con el método round, y finalmente dividimos entre 100.0 para tener el número
		// con 2 decimales.
		// Importante el dividir entre 100.0 y no 100, pues Math.round genera un entero.
		// Y dividir un entero por un entero da un entero. Así que divivimos un entero
		// por un double (100.0) y así obtenemos un double
		// Y como queremos que los decimales se representen en el fichero con coma, lo
		// transformamos a String y reemplazamos punto por coma
		Supplier<String> generarNota = () -> ((Math.round(Math.random() * 10 * 100) / 100.0) + "").replace(".", ",");
		try {
			// Stream del fichero de notas
			Stream<String> lineas = Files.lines(pathNotas);
			// Si la línea termina en ; (no hay nota), se genera una nota y se concatena el
			// nombre con ella; si no, no se concatena la línea a nada.
			// El collect hace una reducción de todos los elementos juntos del Stream en una
			// variable. En este caso, como usamos Collectors.joining como Collector para el
			// método collect, se reduce todo a una String
			String lineaFinal = lineas.map(x -> x + (x.endsWith(";") ? generarNota.get() : ""))
					.collect(Collectors.joining("\n"));
			// Antes hemos generado una String, con los saltos de línea marcados como \n.
			// Así que ahora podemos usar el método writeString de Files para escribir esa
			// String en el fichero
			Files.writeString(pathNotas, lineaFinal);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Ejemplo Collectors.groupingBy
	public void listadoAprobadosSuspensos() {
		try {
			// Usando Collectors.groupingBy usando lo que va después del ; como parámetro,
			// creamos un mapa cuyas claves de sus elementos son lo que va tras el ;
			// (APROBADO y SUSPENSO), y los valores asociados a esas claves son 2
			// listas de "alumno1;APROBADO","alumno2;APROBADO"... y "alumno3;SUSPENSO",
			// "alumno4;SUSPENSO", respectivamente. Se agrupa por el criterio que le demos a
			// la Function que usa el groupingBy. Y eso por lo que se agrupa, se convierte
			// en las claves del Map
			Map<String, List<String>> grupos = Files.lines(pathAlumnos)
					.collect(Collectors.groupingBy(x -> x.substring(x.indexOf(";") + 1, x.length())));
			for (Map.Entry<String, List<String>> entry : grupos.entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue().stream()
						.map(x -> x.substring(0, x.indexOf(";"))).collect(Collectors.joining(", ")));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
