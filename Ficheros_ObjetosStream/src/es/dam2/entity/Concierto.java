package es.dam2.entity;

import java.io.Serializable;

public class Concierto implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer codigo;
	private String artista;
    private int duracion;
    private double cache;
    
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
}