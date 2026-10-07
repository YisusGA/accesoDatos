package es.dam2.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import es.dam2.dao.FestivalDAO;
import es.dam2.entity.Concierto;
import es.dam2.entity.Festival;
import teclado.TecladoOK;

public class FestivalService {

	private FestivalDAO dao;

	public FestivalService(FestivalDAO dao) {
		this.dao = dao;
	}

	public void crearFestivalSinConciertos() {
		Scanner sc = new Scanner(System.in);

		Festival festival = new Festival();

		System.out.println("Nombre festival: ");
		festival.setNombre(sc.nextLine());

		System.out.println("Ciudad festival: ");
		festival.setCiudad(sc.nextLine());

		festival.setConciertos(new ArrayList<>());

		dao.insert(festival);
	}

	public List<Concierto> recuperaListaConciertosFestival(String nombre) {
		return dao.findById(nombre).orElseThrow().getConciertos().stream().toList();
	}

	// Devuelve en diferentes líneas artista -> cache
	public String recuperaConciertosFestival(String nombre) {
		return dao.findById(nombre).orElseThrow().getConciertos().stream()
				.map(x -> x.getArtista() + "->" + x.getCache()).collect(Collectors.joining("\n"));
	}

	public void addConcierto(Concierto concierto, String nombreFestival) throws NoSuchElementException {
		// Lo ideal sería escribir todo el código de nuevo aquí, en lugar de usar los
		// métodos aque ya hay en el DAO. Porque si usamos los métodos que ya hay en el
		// DAO, estamos accediendo 2 veces al fichero, con el findById(String nombre) y
		// con el update (Festival festival)
		Festival f = dao.findById(nombreFestival).orElseThrow();
		if (!f.getConciertos().contains(concierto)) {
			f.getConciertos().add(concierto);
			dao.update(f);
		}
	}

	// Agrega un montón de conciertos a un festival
	public int agregaConciertos(String nombre) {
		int numConciertosAgregados = 0;
		// Haciendo un Set de nuevos conciertos a añadir, nos aseguramos de que no nos
		// añadan un concierto 2 veces en el pack
		Set<Concierto> conciertos = new HashSet<>();
		System.out.println("¿Cuántos conciertos quieres agregar?");
		int numConciertos = TecladoOK.leerEntero();
		Festival f = dao.findById(nombre).orElseThrow();
		List<Concierto> conciertosFestival = f.getConciertos();
		for (int i = 0; i < numConciertos; i++) {
			int codigo = TecladoOK.leerEntero();
			String artista = TecladoOK.leerCadena();
			int duracion = TecladoOK.leerEntero();
			double cache = TecladoOK.leerDecimal();
			Concierto c = new Concierto(codigo, artista, duracion, cache);
			// Si el Festival no contenía ya ese concierto y si no nos han metido 2 veces el
			// mismo Festival en el pack a añadir, lo añadimos al Set, para luego pasarle
			// ese Set depurado al Festival y agregarle todos los Conciertos del Set
			if (!conciertosFestival.contains(c) && conciertos.add(c)) {
				numConciertosAgregados++;
			}
		}
		f.getConciertos().addAll(conciertos);
		dao.update(f);
		return numConciertosAgregados;
	}
}
