package com.Inversiones.clientes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class ControladorTransaccion
 */
@WebServlet("/ControladorTransaccion")
public class ControladorTransaccion extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final int COMISION = 100;
	private ModeloClientes modeloClientes;
	private ModeloTransaccion modeloTransaccion;
	
	@Resource(name="jdbc/Inversiones")
	private DataSource miPool;
	

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try {
			this.modeloClientes = new ModeloClientes(miPool);
			this.modeloTransaccion = new ModeloTransaccion(miPool);
		}catch(Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		//Leer los datos que mandaron
		String transaccion = request.getParameter("transaccion");
		
		switch(transaccion) {
		case "venta" : try {
							venta(request, response);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}; break;
		case "compra" : try {
							compra(request, response);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}; break;
		}
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////

	private void compra(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////

	private void venta(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		
		//Recibimos datos del producto desde el front
		int cantidad = Integer.parseInt(request.getParameter("cantidad"));
		String nombreProducto = request.getParameter("nombre");
		Producto producto = this.modeloTransaccion.getProducto(nombreProducto);
		int id_producto = producto.getId();
		Double precio = producto.getPrecio();
		producto.setCantidad(producto.getCantidad() + cantidad);
		
		//Recibimos id del cliente desde el front
		int id_cliente = Integer.parseInt(request.getParameter("id_cliente"));
		Cliente cliente = this.modeloClientes.getCliente(id_cliente);
		if( (( cantidad * precio ) - COMISION) >= 0 ) {
			Double total = ( cantidad * precio ) - COMISION;
			cliente.setSaldo(cliente.getSaldo() + total);
			
			//Creamos la nueva transacción
			String fecha = obtenerFechaActual();
			TipoTransaccion tipo = TipoTransaccion.VENTA;
			Transaccion transaccion = new Transaccion(fecha, cantidad, id_cliente, id_producto, total, tipo);
			
			//Si la transacción se efectúa
			if(this.modeloTransaccion.realizarTransaccionVenta(transaccion)) {
				
				//Actualizamos el saldo del cliente
				this.modeloTransaccion.actualizarSaldo(cliente);
				
				//Actualizamos la cantidad del producto
				this.modeloTransaccion.actualizarCantidad(producto);
				
				//Registramos la comision
				this.modeloTransaccion.registrarComision();
				
				//Actualizamos los productos del portafolio del cliente
				this.modeloTransaccion.actualizarPortafolio(id_cliente, id_producto, cantidad);
				
				//Obtenemos la lista de productos del portafolio del cliente
				ArrayList<Producto> productosCliente = this.modeloTransaccion.getPortafolio(id_cliente);
				request.setAttribute("productosCliente", productosCliente);
				request.setAttribute("error", false);
				RequestDispatcher miDispatcher = request.getRequestDispatcher("/portafolio.jsp");
				miDispatcher.forward(request, response);
			
			}else {
				//Obtenemos la lista de productos del portafolio del cliente
				ArrayList<Producto> productosCliente = this.modeloTransaccion.getPortafolio(id_cliente);
				request.setAttribute("productosCliente", productosCliente);
				request.setAttribute("error", true);
				RequestDispatcher miDispatcher = request.getRequestDispatcher("/portafolio.jsp");
				miDispatcher.forward(request, response);
			}
		}else {
			//Obtenemos la lista de productos del portafolio del cliente
			ArrayList<Producto> productosCliente = this.modeloTransaccion.getPortafolio(id_cliente);
			request.setAttribute("productosCliente", productosCliente);
			request.setAttribute("error", true);
			RequestDispatcher miDispatcher = request.getRequestDispatcher("/portafolio.jsp");
			miDispatcher.forward(request, response);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String obtenerFechaActual() {
		String formato = "yyyy-MM-dd"; //"yyyy-MM-dd HH:mm:ss" <-- Es la forma más completa
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
		LocalDateTime ahora = LocalDateTime.now();
		return formateador.format(ahora);
	}
}
