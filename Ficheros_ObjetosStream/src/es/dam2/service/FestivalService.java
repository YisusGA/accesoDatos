package es.dam2.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	
	// Agrega un montón de conciertos a un festival
	public int agregaConciertos(String nombre) {
		int numConciertosAgregados = 0;
		Set<Concierto> conciertos = new HashSet<>();
		System.out.println("¿Cuántos conciertos quieres agregar?");
		int numConciertos = TecladoOK.leerEntero();
		for (int i = 0; i < numConciertos; i++) {
			int codigo = TecladoOK.leerEntero();
			String artista = TecladoOK.leerCadena();
			int duracion = TecladoOK.leerEntero();
			double cache = TecladoOK.leerDecimal();
			if (conciertos.add(new Concierto(codigo, artista, duracion, cache))) {
				numConciertosAgregados++;
			}
		}
		dao.findById(nombre).orElseThrow().getConciertos().addAll(conciertos);
		return numConciertosAgregados;
	}
}
