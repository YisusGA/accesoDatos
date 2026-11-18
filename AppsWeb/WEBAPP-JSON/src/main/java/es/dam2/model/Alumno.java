package es.dam2.model;

public class Alumno {
	private String nombre;
	private double nota;
	
	public Alumno(String nombre, double nota) {
		super();
		this.nombre = nombre;
		this.nota = nota;
	}

	public Alumno() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}
	
	

}
