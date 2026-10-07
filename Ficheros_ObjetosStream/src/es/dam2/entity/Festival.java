package es.dam2.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Festival implements Serializable {

	// Acordarse de que para poder serializar un objeto, hay que hacer que
	// implemente la interfaz Serializable. Y para evitar problemas futuros si se
	// añaden o eliminan propiedades de la clase, debe también dársele un serialID
	private static final long serialVersionUID = 1L;
	// Vamos a asumir que vivimos en un mundo en el que el nombre del festival es
	// único, siendo por tanto su clave primaria
	private String nombre;
	private String ciudad;
	private List<Concierto> conciertos;

	public Festival(String nombre, String ciudad, List<Concierto> conciertos) {
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.conciertos = conciertos;
	}

	public Festival() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public List<Concierto> getConciertos() {
		return conciertos;
	}

	public void setConciertos(List<Concierto> conciertos) {
		this.conciertos = conciertos;
	}

	// En Eclipse, click derecho>Source>Generate hashCode() and equals() y elegir la
	// propiedad de la clase que queremos usar como criterio de igualdad entre
	// objetos de la clase

	// Esto sirve para establecer el criterio de igualdad entre objetos de la clase
	// si usamos objetos de la clase como claves de un HashMap
	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	// Con esto, lo que hacemos es proporcionar a Java un criterio de comparación
	// entre objetos de Festival, que en este caso sería el nombre. Si 2 festivales
	// tienen el mismo nombre, entonces son iguales. Si pongo
	// festival1.equals(festival2) y ambos tienen el mismo nombre, devolverá un
	// true, o false si los nombres son distintos
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Festival other = (Festival) obj;
		return Objects.equals(nombre, other.nombre);
	}

}