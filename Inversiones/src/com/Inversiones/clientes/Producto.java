package com.Inversiones.clientes;

public class Producto {
	private int id;
	private int cantidad;
	private String nombre;
	private Double precio;
	private TipoProducto tipo;
	
	public Producto(int id, int cantidad, String nombre, Double precio, TipoProducto tipo) {
		this.id = id;
		this.cantidad = cantidad;
		this.nombre = nombre;
		this.precio = precio;
		this.tipo = tipo;
	}
	
	public Producto(int cantidad, String nombre, Double precio, TipoProducto tipo) {
		this.cantidad = cantidad;
		this.nombre = nombre;
		this.precio = precio;
		this.tipo = tipo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public TipoProducto getTipo() {
		return tipo;
	}

	public void setTipo(TipoProducto tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", cantidad=" + cantidad + ", nombre=" + nombre + ", precio=" + precio + ", tipo="
				+ tipo + "]";
	}
	
	
	
}
