package es.dam2.dao;

import es.dam2.expedientes.*;
import jakarta.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación recibida de un proveedor externo. Auditar su comportamiento.
 */
public class ExpedienteDAOXml implements ExpedienteDAO {
	private final File origen;
	private final File destino;
	private final JAXBContext contexto;
	private final ArchivoType archivo;

	public ExpedienteDAOXml(File origen, File destino) {
		try {
			this.origen = origen;
			this.destino = destino;
			contexto = JAXBContext.newInstance(ArchivoType.class);
			Unmarshaller lector = contexto.createUnmarshaller();
			// Como no hay declarado un rootElement, tenemos que almacenarlo en un
			// JAXBElement intermedio
			JAXBElement<ArchivoType> raiz = lector.unmarshal(new StreamSource(this.origen), ArchivoType.class);
			// Y luego nosotros almacenamos el valor de ese JAXBElement en nuestra clase
			// raíz ArchivoType
			archivo = raiz.getValue();
		} catch (JAXBException ex) {
			throw new IllegalStateException("No se pudo cargar el archivo XML", ex);
		}
	}

	public List<ExpedienteType> listarExpedientes() {
		return archivo.getExpediente();
	}

	public ExpedienteType buscarPorCodigo(String codigo) {
		for (ExpedienteType e : archivo.getExpediente()) {
			if (e.getCodigo().equals(codigo))
				return e;
		}
		return null;
	}

	public long contarInvestigaciones(int desde, int hasta) {
		long total = 0;
//		for (ExpedienteType e : archivo.getExpediente()) {
//			for (InvestigacionType i : e.getInvestigaciones().getInvestigacion()) {
//				int anio = i.getAño().getYear();
//				if (anio >= desde && anio <= hasta)
//					total++;
//			}
//		}
		total = archivo.getExpediente().stream().flatMap(x -> x.getInvestigaciones().getInvestigacion().stream())
				.filter(x -> x.getAño().getYear() >= desde && x.getAño().getYear() <= hasta).count();
		return total;
	}

	public List<ExpedienteType> buscarPorTipoEvidencia(String tipo) {
		List<ExpedienteType> resultado = new ArrayList<>();
		// Aquí no había comprobaciones de null, y entonces petaba al intentar obtener
		// EvidenciaType en las InvestigacionType que no tienen evidencias
		if (archivo.getExpediente() != null) {
			for (ExpedienteType e : archivo.getExpediente()) {
				if (e.getInvestigaciones() != null && e.getInvestigaciones().getInvestigacion().size() > 0) {
					for (InvestigacionType i : e.getInvestigaciones().getInvestigacion()) {
						if (i.getEvidencias() != null && i.getEvidencias().getEvidencia().size() > 0) {
							for (EvidenciaType evidencia : i.getEvidencias().getEvidencia()) {
								if (evidencia != null && evidencia.getTipo().value().equals(tipo))
									resultado.add(e);
							}
						}
					}
				}
			}
		}
		// Me queda completar esta cadena de streams para poder sacar el Expediente al
		// que pertenecen las evidencias filtradas
//		archivo.getExpediente().stream().filter(x -> x != null).filter(x -> x.getInvestigaciones() != null)
//				.filter(x -> x.getInvestigaciones().getInvestigacion().size() > 0)
//				.flatMap(x -> x.getInvestigaciones().getInvestigacion().stream()).filter(x -> x.getEvidencias() != null)
//				.filter(x -> x.getEvidencias().getEvidencia().size() > 0)
//				.flatMap(x -> x.getEvidencias().getEvidencia().stream()).filter(x -> x.getTipo().value().equals(tipo));
		return resultado;
	}

	public boolean agregarInvestigacion(String codigo, InvestigacionType nueva) {
		boolean agregada = false;
		ExpedienteType e = buscarPorCodigo(codigo);
		// Si se hace esto se hace como lo que está comentado, no añadiría nada
//		List<InvestigacionType> investigaciones = new ArrayList<>(e.getInvestigaciones().getInvestigacion());
//		investigaciones.add(nueva);
		if (e != null) {
			agregada = e.getInvestigaciones().getInvestigacion().add(nueva);
		}
		if (agregada) {
			guardar();
		}
		return agregada;
	}

	public long eliminarInvestigacionesAnteriores(int anio) {
		long eliminadas = 0;
		for (ExpedienteType e : archivo.getExpediente()) {
			// Como hemos visto ya en numerosas ocasiones, no es buena idea realizar
			// borrados es una colección mientras iteramos con ella en un bucle, pues puede
			// surgir un error llamado ConcurrentModificationException, debido a que Java
			// detecta que se han realizado modificaciones sobre la colección mientras la
			// recorría, y lanza la excepción para evitar comportamientos impredecibles o
			// corrupción de memoria. Para evitar esto, podemos usar el Iterator y su método
			// remove(Object o), como ya hemos visto. Otra forma mucho más rápida y cómoda
			// de lograr esto es usar el método removeIf(Predicate) de las colecciones, que
			// eliminan de forma segura todos aquellos elementos que cumplen la condición
			// que pasamos como Predicate

//			for (InvestigacionType i : e.getInvestigaciones().getInvestigacion()) {
//				if (i.getAño().getYear() <= anio) {
//					e.getInvestigaciones().getInvestigacion().remove(i);
//					eliminadas++;
//				}
//			}
			// Contamos las que cumplen el criterio y que se eliminarán, para hacer el
			// return con el número de investigaciones que se han eliminado
			eliminadas = e.getInvestigaciones().getInvestigacion().stream().filter(x -> x.getAño().getYear() <= anio)
					.count();
			// Y las eliminamos
			e.getInvestigaciones().getInvestigacion().removeIf(x -> x.getAño().getYear() <= anio);
		}
		if (eliminadas > 0) {
			guardar();
		}
		return eliminadas;
	}

	public boolean cerrarExpediente(String codigo) {
		boolean cerrado = false;
		ExpedienteType e = buscarPorCodigo(codigo);
		if (e != null) {
			cerrado = true;
			e.setEstado(EstadoType.CERRADO);
		}
		if (cerrado) {
			guardar();
		}
		return cerrado;
	}

	// Este método estaba mal, porque faltaba el paso intermedio de definir el
	// elemento raíz como el origen del marshal. Ahora ya está corregido
	public void guardar() {
		try {
			Marshaller escritor = contexto.createMarshaller();
			escritor.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ObjectFactory factory = new ObjectFactory();
			JAXBElement<ArchivoType> raiz = factory.createArchivo(archivo);
			escritor.marshal(raiz, destino);
		} catch (JAXBException ex) {
			System.err.println("Aviso: " + ex.getMessage());
		}
	}
}
