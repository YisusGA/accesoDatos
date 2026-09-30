package es.dam2.dao;

import java.util.List;

import es.dam2.entity.Concierto;
import es.dam2.entity.Festival;

public interface FestivalDAO {

	// En las interfaces, los métodos son públicos por defecto, así que no hace
	// falta poner el modificado public en los métodos

	void insert(Festival festival);

	Concierto findById(String nombre);

	void delete(String nombre);

	void update(Festival festival);

	List<Concierto> findAll();

}
