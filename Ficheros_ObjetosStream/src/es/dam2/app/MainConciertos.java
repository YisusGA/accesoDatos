package es.dam2.app;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import es.dam2.DAOImplFicheros.FestivalDAOImpl;
import es.dam2.entity.Concierto;
import es.dam2.entity.Festival;

public class MainConciertos {

	public static void main(String[] args) {
		
		List<Concierto> conciertos = new ArrayList<>();
		conciertos.add(new Concierto());
		
		FestivalDAOImpl dao = new FestivalDAOImpl(Path.of("festivales.dat"));

		dao.insert(new Festival("Rock in Rio", "Madrid", conciertos));
		dao.insert(new Festival("Mad Cool", "Madrid", conciertos));
		dao.insert(new Festival("Arenal Sound", "Valencia", conciertos));
		
		
		dao.findAll().stream().forEach(x -> System.out.println("Nombre: " + x.getNombre() + ", Ciudad: "+ x.getCiudad()));
		
		
//		dao.delete("Rock in Rio");
		dao.update(new Festival("Rock in Rio", "Wherever", conciertos));
		
		System.out.println();
		dao.findAll().stream().forEach(x -> System.out.println("Nombre: " + x.getNombre() + " Ciudad: "+ x.getCiudad()));
	}

}
