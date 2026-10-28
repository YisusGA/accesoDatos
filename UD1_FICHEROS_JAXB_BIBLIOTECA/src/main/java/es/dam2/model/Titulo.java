package es.dam2.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class Titulo {
	@XmlAttribute(name = "codigo")
	private String cod;
	// Si no ponemos esta anotación, JAXB mapearía valor como un element de Titulo,
	// en lugar de como su valor del elemento Titulo
	@XmlValue
	private String valor;
}
