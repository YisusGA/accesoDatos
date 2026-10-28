package es.dam2.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Libro {
	@XmlAttribute
	private String isbn;
	@XmlElement
	private Titulo titulo;
	@XmlElement
	private Autor autor;
	@XmlElement
	private Integer anio;
	@XmlElement
	private Boolean disponible;

	// Incluir siempre un constructor sin parámetros para que lo pueda usar la JAXB
	public Libro() {
	}

	public Libro(String isbn, Titulo titulo, Autor autor, Integer anio, Boolean disponible) {
		this.isbn = isbn;
		this.titulo = titulo;
		this.autor = autor;
		this.anio = anio;
		this.disponible = disponible;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Titulo getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Boolean getDisponible() {
		return disponible;
	}

	public void setDisponible(Boolean disponible) {
		this.disponible = disponible;
	}

}
