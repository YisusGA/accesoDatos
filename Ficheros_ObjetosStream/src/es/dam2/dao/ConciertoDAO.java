package es.dam2.dao;

import java.util.List;

import es.dam2.entity.Concierto;

public interface ConciertoDAO {

	// En las interfaces, los métodos son públicos por defecto, así que no hace
	// falta poner el modificado public en los métodos

	void insert(Concierto concierto);

	Concierto findById(Integer codigo);

	void delete(Integer codigo);

	void update(Concierto concierto);

	List<Concierto> findAll();

}
