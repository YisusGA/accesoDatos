package es.dam2.gestion;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Gestion {

	private Document doc;
	private File file;
	private static final Logger logger = LogManager.getLogger(Gestion.class);

	public Gestion() {

		logger.debug("Instanciando gestión fenómenos...");

		Properties properties = new Properties();

		InputStream is = Gestion.class.getClassLoader().getResourceAsStream("app.properties");
		try {
			properties.load(is);
		} catch (IOException e) {
			logger.error("No se pudo cargar el fichero de propiedades de la aplicación", e.getMessage(), e);
		}
		file = new File(properties.getProperty("ficheroXML"));
		doc = getDocument(file);
	}

	private Document getDocument(File file) {

		Document doc = null;
		// DocumentBuilderFactory es una clase abstracta, así que no puede instanciarse
		// con constructor, necesitamos usar el método estático
		// DocumentBuilderFactory.newInstance() para generar una instancia
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// DocumentBuilder es una clase abstracta, así que no puede instanciarse
			// con constructor, necesitamos usar el método newDocumentBuilder() de la
			// instancia de DocumentBuilderFactory para generar una instancia
			DocumentBuilder db = dbf.newDocumentBuilder();
			// Creada la instancia de DocumentBuilder, ya podemos usar su método parse(File
			// file) para parsear un fichero XML y contruir un DOM a partir de él,
			// almacenándolo en nuestro instancia de Document
			doc = db.parse(file);
			// Cuando metemeos un salto de línea en un XML para conseguir una estructura
			// más legible para el ser humano, metemos un carácter \n. Y esto es reconocido
			// como un nodo texto en el DOM. El método normalize() de Document sirve para
			// limpiar estos nodos "vacíos" a la hora de construir el DOM
			doc.normalize();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error("No se pudo parsear el XML", e.getMessage(), e);
		}
		return doc;
	}

	private void updateFile() {

		TransformerFactory tf = TransformerFactory.newInstance();
		try {
			Transformer tr = tf.newTransformer();
			tr.setOutputProperty(OutputKeys.INDENT, "yes");
			tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			doc.getDocumentElement().normalize();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			tr.transform(source, result);
		} catch (TransformerException e) {
			logger.error("No se pudo actualizar el XML", e.getMessage(), e);
		}
	}

	public void mostrarResponsables() {

		// Este método saca sólo los nodos que son elementos (etiquetas)
		NodeList expedientes = doc.getElementsByTagName("expediente");

		for (int i = 0; i < expedientes.getLength(); i++) {

			// Puedo hacer este casting a Element sin peligro, pues antes he sacado una
			// NodeList que sé que sólo contiene nodos elemento
			Element expediente = (Element) expedientes.item(i);

			// Obtener y mostrar el título del expediente

			System.out.println(
					"Título del expediente: " + expediente.getElementsByTagName("titulo").item(0).getTextContent());

			// Obtener las investigaciones de este expediente

			NodeList investigaciones = expediente.getElementsByTagName("investigacion");

			// Recorrerlas y mostrar sus responsables
			for (int j = 0; j < investigaciones.getLength(); j++) {
				Element investigacion = (Element) investigaciones.item(j);
				System.out.printf("Responsables de investigacion del año %s: %s\n", investigacion.getAttribute("año"),
						investigacion.getElementsByTagName("responsable").item(0).getTextContent());
			}
			System.out.println();
		}
	}

	public void contarInvestigaciones() {

		NodeList expedientes = doc.getElementsByTagName("expediente");

		for (int i = 0; i < expedientes.getLength(); i++) {

			Element expediente = (Element) expedientes.item(i);

			NodeList investigaciones = expediente.getElementsByTagName("investigacion");

			System.out.printf("El expediente con código %s tiene %s investigaciones\n",
					expediente.getAttribute("codigo"), investigaciones.getLength());
		}
	}

	/**
	 * 
	 * @param codigoExpediente el código de expediente a buscar
	 * @return el número de evidencias, o -1 si no se ha encontrado un expediente
	 *         con ese código
	 */
	public int contarEvidencias(String codigoExpediente) {
		int numeroEvidencias = 0;

		NodeList expedientes = doc.getElementsByTagName("expediente");

		Element expediente = null;

		for (int i = 0; i < expedientes.getLength(); i++) {
			if (((Element) expedientes.item(i)).getAttribute("codigo").equals(codigoExpediente)) {
				expediente = (Element) expedientes.item(i);
			}
		}
		if (expediente != null) {
			NodeList evidencias = expediente.getElementsByTagName("evidencia");
			numeroEvidencias = evidencias.getLength();
		} else {
			numeroEvidencias = -1;
		}
		return numeroEvidencias;
	}

	public int eliminarInvestigacionesAnteriores(int anio) {
		int numInvestigacionesEliminadas = 0;

		NodeList investigaciones = doc.getElementsByTagName("investigacion");
		// IMPORTANTE: Iterar en sentido inverso, para que no se salte elementos al
		// cambiar de posición cuando se borran. Si iteramos desde la posición 0 en
		// adelante, si borra, por ejemplo, el elemento de la posición 1, el elemento de
		// la posición 2 pasa a la posición 1, pero el contador i pasa a ser 2. Y
		// entonces el bucle de iteración se saltará el elemento que inicialmente estaba
		// en la posición 2 (y ahora en la 1). Si iteramos desde el final, evitamos
		// saltarnos elementos por este motivo. Para ver una explicación más detallada,
		// mirar apuntes del 21/10/2026
		for (int i = investigaciones.getLength() - 1; i >= 0; i--) {
			Element investigacion = (Element) investigaciones.item(i);
			try {
				if (Integer.parseInt(investigacion.getAttribute("año")) < anio) {
					investigacion.getParentNode().removeChild(investigacion);
					numInvestigacionesEliminadas++;
				}
			} catch (NumberFormatException e) {
				logger.error("No se pudo parsear el año de la investigación. Investigación ignorada");
			}
		}
		updateFile();
		return numInvestigacionesEliminadas;
	}

}
