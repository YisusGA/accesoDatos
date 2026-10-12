package es.dam2.entity;

import java.io.Serializable;
import java.util.Objects;

public class Concierto implements Serializable {

	// Aunque sólo serializamos objetos de la clase Festival, como Festival contiene
	// objetos de Concierto, Concierto debe poder ser Serializable también
	private static final long serialVersionUID = 1L;

	private Integer codigo;
	private String artista;
	private int duracion;
	private double cache;
	
	public Concierto(Integer codigo, String artista, int duracion, double cache) {
		this.codigo = codigo;
		this.artista = artista;
		this.duracion = duracion;
		this.cache = cache;
	}

	public Concierto() {
		
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public double getCache() {
		return cache;
	}

	public void setCache(double cache) {
		this.cache = cache;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Concierto other = (Concierto) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
	
}