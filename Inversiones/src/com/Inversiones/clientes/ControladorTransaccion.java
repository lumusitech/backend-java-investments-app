package com.Inversiones.clientes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    private final Double COMISION = 100.00;
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
		
		//Recibimos datos del producto desde el front
		int cantidad = Integer.parseInt(request.getParameter("cantidad"));
		String nombreProducto = request.getParameter("nombre");
		Producto producto = this.modeloTransaccion.getProducto(nombreProducto);
		int id_producto = producto.getId();
		Double precio = producto.getPrecio();
		
		producto.setCantidad(producto.getCantidad() - cantidad);
		
		//Recibimos id del cliente desde el front
		int id_cliente = Integer.parseInt(request.getParameter("id_cliente"));
		Cliente cliente = this.modeloClientes.getCliente(id_cliente);
		
		Double total = ( cliente.getSaldo() - (cantidad * precio) );
		
		if( total >= COMISION ) {  //programar cuando no se cumple esto, el error
			
			cliente.setSaldo(total - COMISION);
			
			//Creamos la nueva transacción
			String fecha = obtenerFechaActual();
			TipoTransaccion tipo = TipoTransaccion.COMPRA;
			
			Transaccion transaccion = new Transaccion(fecha, cantidad, id_cliente, id_producto, (cantidad * precio), tipo);
			
			//Guardamos el id de transacción
			int idTransaccion = this.modeloTransaccion.realizarTransaccionCompra(transaccion);//ok
			
			//Actualizamos el saldo del cliente
			this.modeloTransaccion.actualizarSaldo(cliente); //ok
			
			//Actualizamos la cantidad del producto
			this.modeloTransaccion.actualizarCantidad(producto); // ok
			
			//Registramos la comision
			this.modeloTransaccion.registrarComision(idTransaccion, COMISION); // ok
			
			//Actualizamos los productos del portafolio del cliente
			this.modeloTransaccion.actualizarPortafolioCompra(id_cliente, id_producto, cantidad); //ok
			
			//Obtenemos los productos ofrecidos
			obtenerProductos(request, cliente);
			
			//Obtenemos la lista de productos del portafolio del cliente
			ArrayList<Producto> productosCliente = this.modeloTransaccion.getPortafolio(id_cliente);
			request.setAttribute("cliente", cliente);
			request.setAttribute("productosCliente", productosCliente);
			request.setAttribute("errorSaldo", false);
			RequestDispatcher miDispatcher = request.getRequestDispatcher("/redireccionador.jsp");
			miDispatcher.forward(request, response);
			
		}else {
			//Obtenemos los productos ofrecidos
			obtenerProductos(request, cliente);
			
			//Obtenemos la lista de productos del portafolio del cliente
			request.setAttribute("cliente", cliente);
			ArrayList<Producto> productosCliente = this.modeloTransaccion.getPortafolio(id_cliente);
			request.setAttribute("productosCliente", productosCliente);
			request.setAttribute("errorSaldo", true);
			RequestDispatcher miDispatcher = request.getRequestDispatcher("/portafolio.jsp");
			miDispatcher.forward(request, response);
		}

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
		
		Double total = ( cliente.getSaldo() + (cantidad * precio) );
		if( total > COMISION ) {  //programar cuando no se cumple esto, el error
			
			//Actualizamos los productos del portafolio del cliente
			int productosRestantes = Integer.parseInt(request.getParameter("cantidadInicial")) - cantidad;
			if(productosRestantes >= 0) {
				this.modeloTransaccion.actualizarPortafolioVenta(id_cliente, id_producto, productosRestantes); //ok
				
				cliente.setSaldo(total - COMISION);
				
				//Creamos la nueva transacción
				String fecha = obtenerFechaActual();
				TipoTransaccion tipo = TipoTransaccion.VENTA;
				Transaccion transaccion = new Transaccion(fecha, cantidad, id_cliente, id_producto, total, tipo);
				
				//Si la transacción se efectúa			
				int idTransaccion = this.modeloTransaccion.realizarTransaccionVenta(transaccion);//ok
				
				//Actualizamos el saldo del cliente
				this.modeloTransaccion.actualizarSaldo(cliente); //ok
				
				//Actualizamos la cantidad del producto
				this.modeloTransaccion.actualizarCantidad(producto); // ok
				
				//Registramos la comision
				this.modeloTransaccion.registrarComision(idTransaccion, COMISION); // ok
				
				//Obtenemos los productos ofrecidos
				obtenerProductos(request, cliente);
				
				//Obtenemos la lista de productos del portafolio del cliente
				ArrayList<Producto> productosCliente = this.modeloTransaccion.getPortafolio(id_cliente);
				request.setAttribute("cliente", cliente);
				request.setAttribute("productosCliente", productosCliente);
				request.setAttribute("errorSaldo", false);
				request.setAttribute("errorCantidad", false);
				RequestDispatcher miDispatcher = request.getRequestDispatcher("/redireccionador.jsp");
				miDispatcher.forward(request, response);
			}else {
				//Obtenemos los productos ofrecidos
				obtenerProductos(request, cliente);
				
				//Obtenemos la lista de productos del portafolio del cliente
				request.setAttribute("cliente", cliente);
				ArrayList<Producto> productosCliente = this.modeloTransaccion.getPortafolio(id_cliente);
				request.setAttribute("productosCliente", productosCliente);
				request.setAttribute("errorCantidad", true);
				RequestDispatcher miDispatcher = request.getRequestDispatcher("/portafolio.jsp");
				miDispatcher.forward(request, response);
			}
			
		}else {
			//Obtenemos los productos ofrecidos
			obtenerProductos(request, cliente);
			
			//Obtenemos la lista de productos del portafolio del cliente
			request.setAttribute("cliente", cliente);
			ArrayList<Producto> productosCliente = this.modeloTransaccion.getPortafolio(id_cliente);
			request.setAttribute("productosCliente", productosCliente);
			request.setAttribute("errorSaldo", true);
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
	
	/////////////////////////////////////////////////////////////////////////////////////////////

	private void obtenerProductos(HttpServletRequest request, Cliente cliente) throws Exception {
		ArrayList<Producto> productos = this.modeloTransaccion.getProductos();
		
		List<Producto> productosConservador = new ArrayList<Producto>();
		List<Producto> productosModerado = new ArrayList<Producto>();
		List<Producto> productosArriesgado = new ArrayList<Producto>();
		
		for(Producto producto : productos) {
			if(producto.getTipo() == TipoProducto.ACCIONES
			|| producto.getTipo() == TipoProducto.FONDO_CONSERVADOR) {
				productosConservador.add(producto);
				productosModerado.add(producto);
				productosArriesgado.add(producto);
			}
			if(producto.getTipo() == TipoProducto.FONDO_MODERADO) {
				productosModerado.add(producto);
				productosArriesgado.add(producto);
			}
			if(producto.getTipo() == TipoProducto.CRIPTOMONEDA
			|| producto.getTipo() == TipoProducto.FONDO_ARRIESGADO) {
				productosArriesgado.add(producto);
			}
		}
		
		switch (cliente.getPerfil()) {
		case CONSERVADOR: request.setAttribute("listaProductos", productosConservador); break;
		case MODERADO : request.setAttribute("listaProductos", productosModerado); break;
		case ARRIESGADO : request.setAttribute("listaProductos", productosArriesgado); break;
		default : request.setAttribute("listaProductos", productosConservador);
		}
	}
}
