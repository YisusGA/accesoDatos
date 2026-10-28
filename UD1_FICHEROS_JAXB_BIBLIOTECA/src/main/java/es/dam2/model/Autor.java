package es.dam2.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class Autor {
	// En este caso, no sería necesario poner la anotación. Pero si la ponemos,
	// tenemos la ventaja de que podemos darle el nombre que queramos al elemento,
	// no tiene por qué coincidir con el nombre de la propiedad en Java. Es
	// recomendable ponerla en cualquier caso
	@XmlElement(name = "nombre")
	private String nombre;
	@XmlElement(name = "nacionalidad")
	private String nacionalidad;
	// Esta anotación implica que JAXB ignore esta propiedad cuando haga marshall al
	// XML
	@XmlTransient
	private Double sueldo;

	public Autor() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

}
