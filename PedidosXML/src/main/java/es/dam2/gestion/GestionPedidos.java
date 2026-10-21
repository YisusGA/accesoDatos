package es.dam2.gestion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

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
import org.xml.sax.SAXException;

import es.dam2.entidad.Cliente;
import es.dam2.entidad.Pedido;
import es.dam2.entidad.Producto;
import teclado.Teclado;

public class GestionPedidos {

	private File carpetaDatos;
	private File ficheroPedidos;
	private Document document;
	private static final Logger logger = LogManager.getLogger();

	public GestionPedidos() throws FileNotFoundException {
		logger.debug("Instanciando gestión de pedidos");

		Properties properties = new Properties();
		InputStream is = GestionPedidos.class.getClassLoader().getResourceAsStream("app.properties");
		try {
			properties.load(is);
		} catch (IOException e) {
			logger.error("No se pudo leer el fichero de propiedades de la aplicacion");
		}
		carpetaDatos = new File(properties.getProperty("carpetaDatos"));
		ficheroPedidos = new File(properties.getProperty("carpetaDatos") + "\\" + properties.getProperty("ficheroXML"));
		if (!carpetaDatos.exists()) {
			carpetaDatos.mkdir();
		}
		if (carpetaDatos.exists() && !ficheroPedidos.exists()) {
			try {
				ficheroPedidos.createNewFile();
			} catch (IOException e) {
				logger.fatal("El fichero de pedidos no existe y no pudo crearse uno nuevo");
			}
		}

		document = getDocument(ficheroPedidos);
		
		try {
			if (Files.size(ficheroPedidos.toPath()) == 0) {
				updateFile();
			}
		} catch (IOException e) {
			logger.error("Error accediendo al tamaño del fichero");
		}

		if (document == null) {
			throw new FileNotFoundException("No se pudo generar el Document");
		}

	}

	public Document getDocument(File file) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();

			if (Files.size(ficheroPedidos.toPath()) == 0) {
				doc = db.newDocument();
				Element pedidosNode = doc.createElement("pedidos");
				doc.appendChild(pedidosNode);
			} else {
				doc = db.parse(file);
				doc.normalize();
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error("Error obteniendo el Document");
		}

		return doc;
	}

	public List<Producto> crearListaProductos() {
		List<Producto> productos = new ArrayList<>();
		System.out.println("¿Cuántos productos quieres añadir?");
		int numProductos = Teclado.leerEntero();

		for (int i = 0; i < numProductos; i++) {
			Producto prod = new Producto();
			System.out.println("Introduce el código del producto");
			prod.setCodigo(Teclado.leerCadena());
			System.out.println("Introduce la descripción del producto");
			prod.setDescripcion(Teclado.leerCadena());
			System.out.println("Introduce la cantidad deseada del producto");
			prod.setCantidad(Teclado.leerEntero());
			System.out.println("Introduce el precio del producto");
			prod.setPrecio(Teclado.leerDecimal());
			productos.add(prod);
		}
		return productos;
	}

	public Pedido crearPedido(List<Producto> listaProductos) {
		Pedido pedido = null;

		System.out.println("Inserta el código del pedido");
		String codigo = Teclado.leerCadena();
		System.out.println("Inserta el nombre del cliente asociado al pedido");
		String nombre = Teclado.leerCadena();
		System.out.println("Inserta el nif del cliente asociado al pedido");
		String nif = Teclado.leerCadena();

		Cliente cliente = new Cliente(nif, nombre);
		pedido = new Pedido(codigo, cliente, listaProductos);

		return pedido;
	}

	public void insertarPedido(Pedido pedido) {

		// Obtenemos el nodo pedidos
		Element pedidosNode = (Element) document.getElementsByTagName("pedidos").item(0);
		// Nodo pedido con sus hijos cliente y productos
		Element pedidoNode = (Element) pedidosNode.appendChild(document.createElement("pedido"));
		pedidoNode.setAttribute("codigo", pedido.getCodigo());
		// Nodo cliente con sus hijos nombre y nif
		Element cliente = (Element) pedidoNode.appendChild(document.createElement("cliente"));
		Element nombreCliente = document.createElement("nombre");
		nombreCliente.setTextContent(pedido.getCliente().getNombre());
		cliente.appendChild(nombreCliente);
		Element nifCliente = document.createElement("nif");
		nifCliente.setTextContent(pedido.getCliente().getNif());
		cliente.appendChild(nifCliente);
		pedidoNode.appendChild(cliente);
		// Nodo productos
		Element listaProductosNode = (Element) pedidoNode.appendChild(document.createElement("productos"));
		List<Producto> productos = pedido.getProductos();
		// Todos los nodos producto
		for (Producto p : productos) {
			Element productoNode = document.createElement("producto");
			Element codigoNode = document.createElement("codigo");
			codigoNode.setTextContent(p.getCodigo());
			productoNode.appendChild(codigoNode);
			Element cantidadNode = document.createElement("cantidad");
			cantidadNode.setTextContent(p.getCantidad().toString());
			productoNode.appendChild(cantidadNode);
			Element precioNode = document.createElement("precio");
			precioNode.setTextContent(p.getPrecio().toString());
			productoNode.appendChild(precioNode);
			listaProductosNode.appendChild(productoNode);
		}
		updateFile();
	}

	private void updateFile() {

		TransformerFactory tf = TransformerFactory.newInstance();
		try {
			Transformer tr = tf.newTransformer();
			tr.setOutputProperty(OutputKeys.INDENT, "yes");
			tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			document.getDocumentElement().normalize();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(ficheroPedidos);
			tr.transform(source, result);
		} catch (TransformerException e) {
			logger.error("No se pudo actualizar el XML", e.getMessage(), e);
		}
	}
}
