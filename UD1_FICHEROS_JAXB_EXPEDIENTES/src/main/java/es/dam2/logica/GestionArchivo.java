package es.dam2.logica;

import java.io.File;

import es.dam2.Archivo;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class GestionArchivo {

	private Archivo getArchivo() {
		Archivo biblioteca = null;

		JAXBContext contexto;
		try {
			// Al pasarle al método newInstance un objeto Biblioteca.class, le estamos
			// pasando información de cómo está estructurada la clase Biblioteca, que es lo
			// que necesita JAXBContext. Esto sería equivalente a tener ya un objeto
			// instanciado de Biblioteca (por ejemplo, biblio) y usar biblio.getClass(). La
			// ventaja que nos da hacerlo de esta última forma es que no tenemos por qué
			// saber de qué clase es el objeto que estamos usando, pues ya nos lo da el
			// método Object.getClass()
			contexto = JAXBContext.newInstance(Archivo.class);
			Unmarshaller unmarshaller = contexto.createUnmarshaller();
			biblioteca = (Archivo) unmarshaller.unmarshal(new File("datos\\fenomenos.xml"));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return biblioteca;
	}

	private void escribirXML(Archivo archivo) {
		JAXBContext contexto;
		try {
			contexto = JAXBContext.newInstance(Archivo.class);
			Marshaller marshaller = contexto.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(archivo, new File("datos\\fenomenos.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	public void mostrarExpedientes() {
		Archivo archivo = getArchivo();
		System.out.printf("Hay %d expedientes:\n------------------\n", archivo.getExpediente().size());
		archivo.getExpediente().stream().forEach(x -> System.out.printf("Código: %s, titulo: %s, estado: %s\n",
				x.getCodigo(), x.getTitulo(), x.getEstado()));
	}

}
