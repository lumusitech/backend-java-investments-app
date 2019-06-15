package com.Inversiones.clientes;

public class Producto {
	private int cantidad;
	private String nombre;
	private Double precio;
	
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
	
	public Producto(int cantidad, String nombre, Double precio) {
		super();
		this.cantidad = cantidad;
		this.nombre = nombre;
		this.precio = precio;
	}
	
	@Override
	public String toString() {
		return "Producto [cantidad=" + cantidad + ", nombre=" + nombre + ", precio=" + precio + "]";
	}
	
}
