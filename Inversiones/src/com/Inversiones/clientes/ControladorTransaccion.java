package com.Inversiones.clientes;

import java.io.IOException;

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
		
		//Recibimos datos del producto
		int cantidad = Integer.parseInt(request.getParameter("cantidad"));
		String nombreProducto = request.getParameter("nombre");
		
		//Recibimos id del cliente
		int id_cliente = Integer.parseInt(request.getParameter("id_cliente"));
		
		if(this.modeloTransaccion.realizarTransaccion("", cantidad, id_cliente, nombreProducto)) {
			
		}
		
		boolean confirmacion = true;
			
	}
}
