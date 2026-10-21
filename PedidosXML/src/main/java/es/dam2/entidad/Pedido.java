package es.dam2.entidad;

import java.util.List;
import java.util.Objects;

public class Pedido {
	
	private String codigo;
	private Cliente cliente;
	private List<Producto> productos;
	
	public Pedido(String codigo, Cliente cliente, List<Producto> productos) {
		this.codigo = codigo;
		this.cliente = cliente;
		this.productos = productos;
	}

	public Pedido() {
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "Pedido [codigo=" + codigo + ", cliente=" + cliente.getNombre() + " - " + cliente.getNif() + ", productos=" + productos + "]";
	}
	
	

}
