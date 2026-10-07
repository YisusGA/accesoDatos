package es.dam2.app;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;
import es.dam2.DAOImplFicheros.FestivalDAOImpl;
import es.dam2.entity.Concierto;
import es.dam2.service.FestivalService;

public class MainConciertos {

	public static void main(String[] args) {
		
		// Clase que está hecha para leer ficheros .properties
		// El nombre del fichero de festivales lo metemos en el fichero.properties. L
		Properties properties = new Properties();
		try {
			properties.load(new FileReader("src\\fichero.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FestivalService festServ = new FestivalService(new FestivalDAOImpl(Path.of(properties.getProperty("nombre") + ".dat")));
//		festServ.crearFestivalSinConciertos();
		System.out.println("Conciertos recuperados antes de añadir conciertos:");
		System.out.println(festServ.recuperaConciertosFestival("Mad Cool"));
		festServ.addConcierto(new Concierto(5, "My Chemical Romance", 120, 35.6), "Mad Cool");
		System.out.println("Conciertos recuperados desoués de añadir conciertos:");
		System.out.println(festServ.recuperaConciertosFestival("Mad Cool"));
	}

}
