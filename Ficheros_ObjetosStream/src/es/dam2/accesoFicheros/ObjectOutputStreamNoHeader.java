package es.dam2.accesoFicheros;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectOutputStreamNoHeader extends ObjectOutputStream {

	// Recordando cosas del año pasado, si queremos serializar un objeto en un
	// fichero que ya tiene objetos serializados, si usamos el método
	// ObjectOutputStream.writeObject(Object o) tal cual, nos va a escribir una
	// cabecera en mitad del fichero, a pesar de que el fichero ya comienza con una
	// cabecera. Y eso nos va a dar un error. Por ello, creamos una clase custom
	// ObjectOutputStreamNoHeader, que hereda de ObjectOutputStream, y que
	// sobreescribe su método writeStreamHeader por uno que no hace absolutamente
	// nada

	// No hay que saberse de memoria hacer este constructor, sale un warning en el
	// nombre de la clase y una de las soluciones precisamente genera este
	// constructor de forma automática
	public ObjectOutputStreamNoHeader(OutputStream out) throws IOException {
		super(out);
		// TODO Auto-generated constructor stub
	}

	// Esto sí que hay que memorizarlo. Aunque se puede ir a la declaración de la
	// clase ObjectOutputStream y buscar ahí el método para copiar el nombre y poder
	// hacer el Override por un método vacío
	@Override
	public void writeStreamHeader() {

	}

}
