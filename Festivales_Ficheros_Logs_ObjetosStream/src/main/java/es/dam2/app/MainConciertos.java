package es.dam2.app;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
		
		// Clase que está hecha para leer ficheros .properties
		// El nombre del fichero de festivales lo metemos en el fichero.properties. L
		Properties properties = new Properties();
		try {
			properties.load(new FileReader("src\\main\\resources\\fichero.properties"));
		} catch (FileNotFoundException e) {
			logger.error("No se encontró el fichero", e.getMessage(), e);
		} catch (IOException e) {
			logger.error("Error de lectura o escritura en el fichero", e.getMessage(), e);
		}
		
		FestivalService festServ = new FestivalService(new FestivalDAOImpl(Path.of(properties.getProperty("nombre") + ".dat")));
		festServ.crearFestivalSinConciertos();
		System.out.println("Conciertos recuperados antes de añadir conciertos:");
		System.out.println(festServ.recuperaConciertosFestival("Mad Cool"));
		festServ.addConcierto(new Concierto(5, "My Chemical Romance", 120, 35.6), "Mad Cool");
		System.out.println("Conciertos recuperados desoués de añadir conciertos:");
		System.out.println(festServ.recuperaConciertosFestival("Mad Cool"));
	}

}
