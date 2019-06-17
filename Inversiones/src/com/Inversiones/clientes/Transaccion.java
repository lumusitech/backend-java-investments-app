package com.Inversiones.clientes;

public class Transaccion {
	private int id;
	private String fecha;
	private int cantidad;
	private int id_cliente;
	private int id_producto;
	private Double total;
	private TipoTransaccion tipo;
	
	
	public Transaccion(int id, String fecha, int cantidad, int id_cliente, int id_producto, Double total, TipoTransaccion tipo) {
		this.id = id;
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.id_cliente = id_cliente;
		this.id_producto = id_producto;
		this.total = total;
		this.tipo = tipo;
	}
	
	public Transaccion(String fecha, int cantidad, int id_cliente, int id_producto, Double total, TipoTransaccion tipo) {
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.id_cliente = id_cliente;
		this.id_producto = id_producto;
		this.total = total;
		this.tipo = tipo;
	}

	public TipoTransaccion getTipo() {
		return tipo;
	}

	public void setTipo(TipoTransaccion tipo) {
		this.tipo = tipo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
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

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Transaccion [id=" + id + ", fecha=" + fecha + ", cantidad=" + cantidad + ", id_cliente=" + id_cliente
				+ ", id_producto=" + id_producto + ", total=" + total + ", tipo=" + tipo + "]";
	}
		
}
