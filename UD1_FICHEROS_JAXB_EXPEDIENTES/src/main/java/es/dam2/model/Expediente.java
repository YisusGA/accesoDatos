package es.dam2.model;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
// Establece el orden de los elementos hijos (no de los atributos). codigo y estado son atributos, así que no se consideran elementos
@XmlType(propOrder = { "titulo", "ubicacion", "investigaciones" })
public class Expediente {
	@XmlAttribute
	private String codigo;
	@XmlAttribute
	private String estado;
	@XmlElement
	private String titulo;
	@XmlElement
	private Ubicacion ubicacion;
	// El @XmlElementWrapper(name = "investigaciones") crea el elemento investigaciones, que es el contenedor de varios
	// elementos investigacion. Y hay que definir que contiene esos elementos con @XmlElement(name = "investigacion")
	@XmlElementWrapper(name = "investigaciones")
	@XmlElement(name = "investigacion")
	private List<Investigacion> investigaciones;
	// Esto sirve para que exista en memoria cuando el programa se ejecuta pero no
	// se persistirá al XML (podemos persistirlo a otro lado)
	@XmlTransient
	private String notaInterna;

	public Expediente() {
	}

}
