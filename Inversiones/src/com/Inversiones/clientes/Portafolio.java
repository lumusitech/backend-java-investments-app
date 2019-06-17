package com.Inversiones.clientes;

public class Portafolio {
	private int id;
	private int id_cliente;
	private int id_producto;
	private int cantidad_producto;
	private Double precio_producto;
	
	public Portafolio(int id, int id_cliente, int id_producto, int cantidad_producto, Double precio_producto) {
		this.id = id;
		this.id_cliente = id_cliente;
		this.id_producto = id_producto;
		this.cantidad_producto = cantidad_producto;
		this.precio_producto = precio_producto;
	}
	
	public Portafolio(int id_cliente, int id_producto, int cantidad_producto, Double precio_producto) {
		this.id_cliente = id_cliente;
		this.id_producto = id_producto;
		this.cantidad_producto = cantidad_producto;
		this.precio_producto = precio_producto;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public int getId_producto() {
		return id_producto;
	}
	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}
	public int getCantidad_producto() {
		return cantidad_producto;
	}
	public void setCantidad_producto(int cantidad_producto) {
		this.cantidad_producto = cantidad_producto;
	}
	public Double getPrecio_producto() {
		return precio_producto;
	}
	public void setPrecio_producto(Double precio_producto) {
		this.precio_producto = precio_producto;
	}
}


