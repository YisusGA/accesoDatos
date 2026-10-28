package es.dam2.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

// Si no pongo el name, JAXB va a pillar el nombre de la clase en minúscula
@XmlRootElement(name = "biblioteca")
@XmlAccessorType(XmlAccessType.FIELD)
public class Biblioteca {
	@XmlAttribute
	private String nombre;
	// El @XmlElementWrapper(name = "investigaciones") crea el elemento
	// investigaciones, que es el contenedor de varios elementos investigacion. Y
	// hay que definir que contiene esos elementos con @XmlElement(name =
	// "investigacion")
	@XmlElementWrapper(name = "libros")
	@XmlElement(name = "libro")
	private List<Libro> libros = new ArrayList<>();

	// Incluir siempre un constructor sin parámetros para que lo pueda usar la JAXB
	public Biblioteca() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Libro> getLibros() {
		return libros;
	}

	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}
}
