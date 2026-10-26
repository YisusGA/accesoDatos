package es.dam2.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class Evidencia {
	@XmlAttribute
	private String tipo;
	@XmlValue
	private String subtipo;
	
	public Evidencia() {
	}

}
