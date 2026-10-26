package es.dam2.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "archivo")
@XmlAccessorType(XmlAccessType.FIELD) // Nos ahorra depender de getters y setters para el mapeo
public class Archivo {
	@XmlElement(name = "expediente")
	private List<Expediente> expedientes = new ArrayList<>();

	// Las clases vinculadas suelen necesitar un constructor sin argumentos, al
	// menos no privado
	public Archivo() {
	}

}
