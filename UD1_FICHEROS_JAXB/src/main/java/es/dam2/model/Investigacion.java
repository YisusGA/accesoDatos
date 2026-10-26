package es.dam2.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "responsable", "resultado", "evidencias" })
public class Investigacion {
	@XmlAttribute
	private int year;
	@XmlElement
	private String responsable;
	@XmlElement
	private String resultado;
	@XmlElementWrapper(name="evidencias")
	@XmlElement(name="evidencia")
	private List<Evidencia> evidencias = new ArrayList<>();
	
	public Investigacion() {
	}
	
	
}
