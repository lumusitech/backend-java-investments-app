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
	
	@Resource(name="jdbc/Inversiones")
	private DataSource miPool;
	

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try {
			this.modeloClientes = new ModeloClientes(miPool);
		}catch(Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Leer los datos que mandaron
		String instruccion = request.getParameter("instruccion");
		
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
		default : instruccion = "login";
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
		Double saldo = 135265.50;
		
		if(!nombre.equals("") && !pass.equals("") && !nac.equals("") && !email.equals("") && !dni.equals("")) {//Comprobar lo que se necesite
			//Crear un objeto de tipo Cliente
			Cliente cliente = new Cliente(nombre, pass, nac, email, dni, saldo, null);
			cliente.setPerfil(TipoPerfil.NINGUNO);
			
			//Enviar el objeto al modelo para que lo inserte en la BBDD
			try {
				modeloClientes.agregarNuevoCliente(cliente);
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
						//Acciones
						Producto alua = new Producto(150, "ALUA", 16.60);
						Producto bma = new Producto(150, "BMA", 1297.65);
						Producto byma = new Producto(150, "BYMA", 372.50);
						Producto cepu = new Producto(150, "CEPU", 38.50);
						Producto come = new Producto(150, "COME", 3.16);
						
						//Criptomonedas
						Producto btc = new Producto(150, "BTC", 8536.00);
						Producto eth = new Producto(150, "BMA", 256.78);
						
						//Fondos
						Producto fondoConservador = new Producto(150, "Fondo conservador", 356.60);
						Producto fondoModerado = new Producto(150, "Fondo moderado", 1297.65);
						Producto fondoArriesgado = new Producto(150, "Fondo arriesgado", 5325.50);
						
						List<Producto> productosConservador = new ArrayList<Producto>();
						List<Producto> productosModerado = new ArrayList<Producto>();
						List<Producto> productosArriesgado = new ArrayList<Producto>();
						
						//Conservador
						productosConservador.add(alua);
						productosConservador.add(bma);
						productosConservador.add(byma);
						productosConservador.add(cepu);
						productosConservador.add(come);
						productosConservador.add(btc);
						productosConservador.add(eth);
						productosConservador.add(fondoConservador);
						
						//Moderado
						productosModerado.add(alua);
						productosModerado.add(bma);
						productosModerado.add(byma);
						productosModerado.add(cepu);
						productosModerado.add(come);
						productosModerado.add(btc);
						productosModerado.add(eth);
						productosModerado.add(fondoConservador);
						productosModerado.add(fondoModerado);
						
						//Arriesgado
						productosArriesgado.add(alua);
						productosArriesgado.add(bma);
						productosArriesgado.add(byma);
						productosArriesgado.add(cepu);
						productosArriesgado.add(come);
						productosArriesgado.add(btc);
						productosArriesgado.add(eth);
						productosArriesgado.add(fondoConservador);
						productosArriesgado.add(fondoModerado);
						productosArriesgado.add(fondoArriesgado);
						
						switch (cliente.getPerfil()) {
						case CONSERVADOR: request.setAttribute("listaProductos", productosConservador); break;
						case MODERADO : request.setAttribute("listaProductos", productosModerado); break;
						case ARRIESGADO : request.setAttribute("listaProductos", productosArriesgado); break;
						default : request.setAttribute("listaProductos", productosConservador);
						}
						
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
}
