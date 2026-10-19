package es.dam2.app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.dam2.DAOImplFicheros.FestivalDAOImpl;
import es.dam2.entity.Concierto;
import es.dam2.service.FestivalService;

public class MainConciertos {
	private static final Logger logger = LogManager.getLogger(MainConciertos.class);

	public static void main(String[] args) {

		logger.info("Arrancando aplicación");

		// Clase que está hecha para leer ficheros .properties
		// El nombre del fichero de festivales lo metemos en el fichero.properties. L
		Properties properties = new Properties();
		try {
			// Esto comentado funciona, porque le pasamos una ruta válida del sistema de
			// archivos a la clase FileReader de java.io. Pero esto podría no funcionar si
			// cambia la ruta de los recursos. NO ES PORTABLE
			// properties.load(new FileReader("src\\main\\resources\\fichero.properties"));
			
			// Esto de debajo es la forma adecuada de hacerlo, ES PORTABLE 
			InputStream is = MainConciertos.class.getClassLoader().getResourceAsStream("fichero.properties");
			properties.load(is);
		} catch (NullPointerException e) {
			logger.error("No se encontró el fichero", e.getMessage(), e);
		} catch (IOException e) {
			logger.error("Error de lectura o escritura en el fichero", e.getMessage(), e);
		}

		FestivalService festServ = new FestivalService(
				new FestivalDAOImpl(Path.of(properties.getProperty("nombre") + ".dat")));
		festServ.crearFestivalSinConciertos();
		System.out.println("Conciertos recuperados antes de añadir conciertos:");
		System.out.println(festServ.recuperaConciertosFestival("Mad Cool"));
		festServ.addConcierto(new Concierto(5, "My Chemical Romance", 120, 35.6), "Mad Cool");
		System.out.println("Conciertos recuperados desoués de añadir conciertos:");
		System.out.println(festServ.recuperaConciertosFestival("Mad Cool"));
	}

}
