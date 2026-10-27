package es.dam2.gestion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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

	/**
	 * Método para construir Document a partir de un File con extensión .xml
	 * 
	 * @param file el archivo asociado al fichero XML
	 * @return Document o null si no se pudo construir el DOM
	 */
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

	/**
	 * Método para generar una lista de productos
	 * 
	 * @return la lista de productos con sus elementos
	 */
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

	/**
	 * Método para construir un Pedido con todos sus datos
	 * 
	 * @param listaProductos la lista de productos para añadir al Pedido
	 * @return el Pedido que se ha creado
	 */
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

	/**
	 * Método para insertar un nuevo Pedido en el fichero XML de pedidos
	 * 
	 * @param pedido el Pedido a insertar
	 */
	public void insertarPedido(Pedido pedido) {

		// Obtenemos el nodo pedidos
		Element pedidosNode = (Element) document.getFirstChild();
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

	/**
	 * Método para eliminar todos los pedidos asociados a un Cliente
	 * 
	 * @param nifCliente el nif del Cliente cuyos pedidos quieren eliminarse
	 * @return el número de pedidos eliminados
	 */
	public int eliminarPedidosCliente(String nifCliente) {

		int numPedidosEliminados = 0;
		NodeList pedidosNodeList = document.getElementsByTagName("pedido");

		for (int i = pedidosNodeList.getLength() - 1; i >= 0; i--) {
			Element pedido = (Element) pedidosNodeList.item(i);
			Element cliente = (Element) pedido.getElementsByTagName("cliente").item(0);
			String nif = cliente.getElementsByTagName("nif").item(0).getTextContent();
			if (nif.equals(nifCliente)) {
				pedido.getParentNode().removeChild(pedido);
				numPedidosEliminados++;
			}
		}

		if (numPedidosEliminados > 0) {
			updateFile();
		}
		return numPedidosEliminados;
	}

	/**
	 * Método para mostrar por consola los pedidos asociados a un Cliente
	 * 
	 * @param nifCliente el nif del Cliente cuyos pedidos se quiere mostrar
	 */
	public void mostrarPedidosCliente(String nifCliente) {

		NodeList pedidosNodeList = document.getElementsByTagName("pedido");

		System.out.printf("Códigos de pedidos del cliente con nif %s: ", nifCliente);
		for (int i = pedidosNodeList.getLength() - 1; i >= 0; i--) {
			Element pedido = (Element) pedidosNodeList.item(i);
			Element cliente = (Element) pedido.getElementsByTagName("cliente").item(0);
			String nif = cliente.getElementsByTagName("nif").item(0).getTextContent();
			if (nif.equals(nifCliente)) {
				System.out.print(pedido.getAttribute("codigo") + ", ");
			}
			System.out.println();
		}
	}

	// Mejor este método que la versión Alt de debajo, es mucho más sencillo!!

	/**
	 * Método para mostrar los gastos totales de un Cliente
	 * 
	 * @param nifCliente el nif del Cliente cuyo gasto total se quiere calcular
	 * @return un double con el gasto total asociado al cliente encontrado, o -1 si
	 *         no se encontró un Cliente con ese nif
	 */
	public double devolverGastosCiente(String nifCliente) {
		double gastoTotal = 0;
		boolean existeCliente = false;

		NodeList pedidosNodeList = document.getElementsByTagName("pedido");

		for (int i = pedidosNodeList.getLength() - 1; i >= 0; i--) {
			Element pedido = (Element) pedidosNodeList.item(i);
			Element cliente = (Element) pedido.getElementsByTagName("cliente").item(0);
			String nif = cliente.getElementsByTagName("nif").item(0).getTextContent();
			if (nif.equals(nifCliente)) {
				existeCliente = true;
				NodeList listaProductos = pedido.getElementsByTagName("producto");
				for (int j = 0; j < listaProductos.getLength(); j++) {
					Element producto = (Element) listaProductos.item(j);
					gastoTotal += (Double.parseDouble(producto.getElementsByTagName("precio").item(0).getTextContent())
							* Integer.parseInt(producto.getElementsByTagName("cantidad").item(0).getTextContent()));
				}
			}
		}

		if (!existeCliente) {
			gastoTotal = -1;
		}
		return gastoTotal;
	}

	// Este método está guay para entender lo que devuelve el método getChildNodes()
	// y cómo gestionarlo, pero es mucho más tedioso y complicado

	/**
	 * Método alternativo para mostrar los gastos totales de un Cliente
	 * 
	 * @param nifCliente el nif del Cliente cuyo gasto total se quiere calcular
	 * @return un double con el gasto total asociado al cliente encontrado, o -1 si
	 *         no se encontró un Cliente con ese nif
	 */
	public double devolverGastosCienteAlt(String nifCliente) {
		double gastoTotal = 0;
		boolean existeCliente = false;

		NodeList pedidosNodeList = document.getElementsByTagName("pedido");

		for (int i = pedidosNodeList.getLength() - 1; i >= 0; i--) {
			Element pedido = (Element) pedidosNodeList.item(i);
			Element cliente = (Element) pedido.getElementsByTagName("cliente").item(0);
			String nif = cliente.getElementsByTagName("nif").item(0).getTextContent();
			if (nif.equals(nifCliente)) {
				existeCliente = true;
				// CUIDADO: El método getChildNodes() devuelve todos los nodos hijos de primer
				// nivel (sólo los descendientes directos, no es recursivo). Pero hay que tener
				// en cuenta que en esos nodos hijos, no sólo habrá nodos de tipo elemento
				// (<etiqueta>), sino que también habrá otros tipos de nodo, como los nodos de
				// tipo texto (como un salto de línea "\n")
				NodeList listaProductos = pedido.getElementsByTagName("productos").item(0).getChildNodes();
				for (int j = 0; j < listaProductos.getLength(); j++) {
					Element producto = null;
					if (listaProductos.item(j).getNodeType() == Node.ELEMENT_NODE) {
						producto = (Element) listaProductos.item(j);
						NodeList atributosProducto = producto.getChildNodes();

						// Variables necesarias para gestionar lo que va a ocurrir dentro del for de
						// debajo
						boolean precioEncontrado = false;
						boolean cantidadEncontrada = false;
						Element cantidad = null;
						Element precio = null;
						for (int k = 0; (!cantidadEncontrada && !precioEncontrado) || k < atributosProducto.getLength(); k++) {
							Node atributoNode = atributosProducto.item(k);
							if (atributoNode.getNodeType() == Node.ELEMENT_NODE) {
								if (atributoNode.getNodeName().equals("cantidad")) {
									cantidad = (Element) atributoNode;
									cantidadEncontrada = true;
								}
								if (atributoNode.getNodeName().equals("precio")) {
									precio = (Element) atributoNode;
									precioEncontrado = true;
								}
								// Si y sólo si se han encontrado tanto el precio como la cantidad, los
								// parseamos y los tenemos en cuenta para actualizar el valor de gastoTotal
								if (cantidadEncontrada && precioEncontrado) {
									gastoTotal += (Double.parseDouble(precio.getTextContent())
											* Integer.parseInt(cantidad.getTextContent()));
								}
							}
						}
					}
				}
			}
		}

		if (!existeCliente) {
			gastoTotal = -1;
		}
		return gastoTotal;
	}

	/**
	 * Método para actualizar el fichero datos//pedidos.xml con los cambios que se
	 * hayan podido realizar. Además, formatea el fichero para identar y normalizar
	 */
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
