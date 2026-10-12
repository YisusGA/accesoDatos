package es.dam2.dao;

import java.util.List;
import java.util.Optional;

import es.dam2.entity.Concierto;
import es.dam2.entity.Festival;

public interface FestivalDAO {

	// En las interfaces, los métodos son públicos por defecto, así que no hace
	// falta poner el modificado public en los métodos

	void insert(Festival festival);

	Optional<Festival> findById(String nombre);

	void delete(String nombre);

	void update(Festival festival);

	List<Festival> findAll();

}
