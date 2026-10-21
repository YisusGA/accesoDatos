package teclado;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Con esta clase, hacemos métodos que nos van a permitir leer int, double y
 * String por teclado sin el problema de que escaneemos una línea vacía al hacer
 * un scan.nextLine() tras haber hecho un scan.nextInt(). Como los métodos de la
 * clase son static, los podemos llamar directamente con el nombre de la
 * clase.Metodo, sin necesidad de crear una instancia de la clase.
 */

public class Teclado {
	public static int leerEntero() {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int result = 0;
		boolean lecturaValida = false;
		while (!lecturaValida) {
			try {
				result = Integer.parseInt(br.readLine());
				lecturaValida = true;
			} catch (NumberFormatException e) {
				System.err.println("Entrada no válida, prueba de nuevo");
//				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Entrada no válida, prueba de nuevo");
//				e.printStackTrace();
			}
		}
		return result;

	}

	public static double leerDecimal() { // Tiene que introducirse con punto, pues lo que hace es leer una cadena y
											// convertirla a número double. Y java
											// necesita que el decimal esté puesto con punto
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		double result = 0;
		boolean lecturaValida = false;
		while (!lecturaValida) {
			try {
				result = Double.parseDouble(br.readLine());
				lecturaValida = true;
			} catch (NumberFormatException e) {
				System.err.println("Entrada no válida, prueba de nuevo");
//				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Entrada no válida, prueba de nuevo");
//				e.printStackTrace();
			}
		}
		return result;
	}

	public static String leerCadena() {
		String result = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean lecturaValida = false;
		while (!lecturaValida) {
			try {
				result = br.readLine();
				lecturaValida = true;
			} catch (IOException e) {
//				e.printStackTrace();
				System.err.println("Entrada no válida, prueba de nuevo");
			}
		}
		return result;
	}

}
