package es.dam2.model;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Expediente {
	@XmlAttribute
	String codigo;
	@XmlAttribute
	String estado;
	@XmlElement
	String titulo;
	@XmlElement
	Ubicacion ubicacion;
	@XmlElementWrapper
	List<Investigacion> investigaciones;
}
