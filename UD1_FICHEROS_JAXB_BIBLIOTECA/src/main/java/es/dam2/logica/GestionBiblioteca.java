package es.dam2.logica;

import java.io.File;

import es.dam2.model.Biblioteca;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class GestionBiblioteca {

	private Biblioteca getBiblioteca() {
		Biblioteca biblioteca = null;

		JAXBContext contexto;
		try {
			// Al pasarle al método newInstance un objeto Biblioteca.class, le estamos
			// pasando información de cómo está estructurada la clase Biblioteca, que es lo
			// que necesita JAXBContext. Esto sería equivalente a tener ya un objeto
			// instanciado de Biblioteca (por ejemplo, biblio) y usar biblio.getClass(). La
			// ventaja que nos da hacerlo de esta última forma es que no tenemos por qué
			// saber de qué clase es el objeto que estamos usando, pues ya nos lo da el
			// método Object.getClass()
			contexto = JAXBContext.newInstance(Biblioteca.class);
			Unmarshaller unmarshaller = contexto.createUnmarshaller();
			biblioteca = (Biblioteca) unmarshaller.unmarshal(new File("datos\\biblioteca.xml"));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return biblioteca;
	}

	public void mostrarTitulos() {
		// Es mejor tener el objeto de Biblioteca dentro del método en lugar de como
		// propiedad de la clase, ya que es un objeto pesado
		Biblioteca biblioteca = getBiblioteca();

		biblioteca.getLibros().stream().forEach(x -> x.getTitulo().getValor());
	}

	public boolean cambiarNacionalidad(String nombreAutor, String nuevaNacionalidad) {
		boolean cambiada = false;
		Biblioteca biblioteca = getBiblioteca();

		if (biblioteca != null) {
			biblioteca.getLibros().stream().forEach(x -> {
				if (x.getAutor().getNombre().equals(nombreAutor)) {
					x.getAutor().setNacionalidad(nuevaNacionalidad);
				}
			});
			cambiada = true;
			escribirXML(biblioteca);
		}

		return cambiada;
	}

	private void escribirXML(Biblioteca biblioteca) {
		JAXBContext contexto;
		try {
			contexto = JAXBContext.newInstance(Biblioteca.class);
			Marshaller marshaller = contexto.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(biblioteca, new File("datos\\biblioteca.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

}
