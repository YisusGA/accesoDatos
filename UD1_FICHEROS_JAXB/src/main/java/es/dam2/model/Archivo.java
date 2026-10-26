package es.dam2.model;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

// Se podría hacer esto para darle otro nombre diferente al del nombre de la clase
// @XmlRootElement(name="archivo")

@XmlRootElement
public class Archivo {
	@XmlElementWrapper
	List<Expediente> expedientes;

}
