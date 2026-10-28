package es.dam2.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

//Nos ahorra depender de getters y setters para el mapeo
@XmlAccessorType(XmlAccessType.FIELD)
// Establece el orden de los elementos hijos (no incluye los atributos). Los nombres que se ponen son los de las propiedades de la clase en java
@XmlType(propOrder = { "nombre", "nacionalidad" })
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
	// XML. Por lo tanto, existirá en memoria cuando el programa se ejecute pero no
	// persistirá al XML (podemos elegir persistirlo a otro lado si nos hace falta)
	@XmlTransient
	private Double sueldo;

	// Incluir siempre un constructor sin parámetros para que lo pueda usar la JAXB
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

	public Double getSueldo() {
		return sueldo;
	}

	public void setSueldo(Double sueldo) {
		this.sueldo = sueldo;
	}

}
