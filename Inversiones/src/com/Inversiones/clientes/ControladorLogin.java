package com.Inversiones.clientes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.*;

/**
 * Servlet implementation class loginController
 */
@WebServlet("/ControladorLogin")
public class ControladorLogin extends HttpServlet {
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
		
		String instruccion;
    	//Leer los datos que mandaron
		if(request.getParameter("instruccion") != null) {
			instruccion = request.getParameter("instruccion");
		}else {
			instruccion = "";
		}
		
		
		switch(instruccion) {
		case "login" : try {
							logueoCliente(request, response);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}; break;
		case "registro" : try {
							registrarCliente(request, response);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}; break;
		default : try {
					cambiarPagina(request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} break;
		}
		
	}

	private void cambiarPagina(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String direccion = request.getParameter("direccion");
		String id_recibido = request.getParameter("id_cliente");
		Cliente cliente = null;
		
		if(!request.getParameter("id_cliente").equals("") ){
			int id = Integer.parseInt(id_recibido);
			cliente = this.modeloClientes.getCliente(id);
			//Mandamos al cliente
			request.setAttribute("cliente", cliente);
			//Mandamoslos productos ofrecidos
			obtenerProductos(request, cliente);
			
			//Se envía el cliente y la lista que le corresponde según su perfil
			ArrayList<Producto> productosCliente = this.modeloTransaccion.getPortafolio(cliente.getId());
			request.setAttribute("productosCliente", productosCliente);
			RequestDispatcher miDispatcher = request.getRequestDispatcher(direccion);
			miDispatcher.forward(request, response);
		}else {
			RequestDispatcher miDispatcher = request.getRequestDispatcher("/login.jsp");
			miDispatcher.forward(request, response);
		}
		
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void registrarCliente(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//Leer la información del Cliente que viene del formulario
		String nombre = request.getParameter("nombre");
		String pass = request.getParameter("pass");
		String nac = request.getParameter("nac");
		String email = request.getParameter("email");
		String dni = request.getParameter("dni");
		Double saldo = 800000.50;
		
		if(!nombre.equals("") && !pass.equals("") && !nac.equals("") && !email.equals("") && !dni.equals("")) {//Comprobar lo que se necesite
			//Crear un objeto de tipo Cliente
			Cliente cliente = new Cliente(nombre, pass, nac, email, dni, saldo, null);
			cliente.setPerfil(TipoPerfil.NINGUNO);
			
			//Enviar el objeto al modelo para que lo inserte en la BBDD
			try {
				this.modeloClientes.agregarNuevoCliente(cliente);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Se lo envía a hacer la encuesta para clasificarlo
			Cliente clienteAEnviar = this.modeloClientes.getCliente(new Cliente(nombre, pass));
			request.setAttribute("cliente", clienteAEnviar);
			request.setAttribute("errorRegistro", false);
			RequestDispatcher miDispatcher = request.getRequestDispatcher("/clasificarPerfil.jsp");
			miDispatcher.forward(request, response);
		} else {
			//Si no se llenaron bien los campos o quedó vacío alguno:
			//Volver a registro con el msj de error
			request.setAttribute("errorRegistro", true);
			RequestDispatcher miDispatcher = request.getRequestDispatcher("/registro.jsp");
			miDispatcher.forward(request, response);
		}
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	private void logueoCliente(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Cliente cliente = null;
		
		if(request.getParameter("id_cliente") != null ) {
			int id = Integer.parseInt(request.getParameter("id_cliente"));
			
			//Se Analiza el perfil
			int tiempo = Integer.parseInt(request.getParameter("tiempo")); 
			int reaccion10 = Integer.parseInt(request.getParameter("reaccion10")); 
			int reaccion25 = Integer.parseInt(request.getParameter("reaccion25"));
			int experiencia = Integer.parseInt(request.getParameter("experiencia"));
			int preferencia = Integer.parseInt(request.getParameter("preferencia"));
			
			int suma = tiempo + reaccion10 + reaccion25 + experiencia + preferencia;
			TipoPerfil perfil;
			
			if(suma>=25) perfil = TipoPerfil.ARRIESGADO;
			else if(suma>=10 && suma<25) perfil = TipoPerfil.MODERADO;
			else if(suma<10) perfil = TipoPerfil.CONSERVADOR;
			else perfil = TipoPerfil.NINGUNO;
			
			try {
				this.modeloClientes.actualizarPerfil(id, perfil);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RequestDispatcher miDispatcher = request.getRequestDispatcher("/login.jsp");
			miDispatcher.forward(request, response);
		}else {
			String nombre = request.getParameter("nombre");
			String pass = request.getParameter("pass");
			boolean error = true;
			
			if( !nombre.equals("") && !pass.equals("") ) {
				Cliente cliente_verificar = new Cliente(nombre, pass);
				cliente = this.modeloClientes.getCliente(cliente_verificar);
	
				if(cliente != null) {
					error=false;
					if(cliente.getPerfil().equals(TipoPerfil.NINGUNO)) {
						request.setAttribute("cliente", cliente);
						RequestDispatcher miDispatcher = request.getRequestDispatcher("/clasificarPerfil.jsp");
						miDispatcher.forward(request, response);
					}
					else {
						//Mandamos al cliente
						request.setAttribute("cliente", cliente);
						//Mandamoslos productos ofrecidos
						obtenerProductos(request, cliente);
						
						//Se envía el cliente y la lista que le corresponde según su perfil
						ArrayList<Producto> productosCliente = this.modeloTransaccion.getPortafolio(cliente.getId());
						request.setAttribute("productosCliente", productosCliente);
						RequestDispatcher miDispatcher = request.getRequestDispatcher("/portafolio.jsp");
						miDispatcher.forward(request, response);
					}
				}else {
					error = true;
					request.setAttribute("errorLogin", error);
					RequestDispatcher miDispatcher = request.getRequestDispatcher("/login.jsp");
					miDispatcher.forward(request, response);
				}
			}else {
				error = true;
				request.setAttribute("errorLogin", error);
				RequestDispatcher miDispatcher = request.getRequestDispatcher("/login.jsp");
				miDispatcher.forward(request, response);
			}
		}
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
