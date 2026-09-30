package es.dam2.entity;

import java.io.Serializable;
import java.util.List;

public class Festival implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Vamos a asumir que vivimos en un mundo en el que el nombre del festival es
	// único, siendo por tanto su clave primaria
	private String nombre;
	private String ciudad;
	private List<Concierto> conciertos;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public List<Concierto> getConciertos() {
		return conciertos;
	}

	public void setConciertos(List<Concierto> conciertos) {
		this.conciertos = conciertos;
	}

}