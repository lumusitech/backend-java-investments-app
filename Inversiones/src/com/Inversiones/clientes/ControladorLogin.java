package com.Inversiones.clientes;

import java.io.IOException;

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

	private void registrarCliente(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//Leer la información del Cliente que viene del formulario
		String nombre = request.getParameter("nombre");
		String pass = request.getParameter("pass");
		String nac = request.getParameter("nac");
		String email = request.getParameter("email");
		String dni = request.getParameter("dni");
		Double saldo = 135265.50;
		
		if(!nombre.equals("") && !pass.equals("")) {//Comprobar lo que se necesite
			//Crear un objeto de tipo Cliente
			Cliente cliente = new Cliente(nombre, pass, nac, email, dni, saldo, TipoPerfil.NINGUNO);
			System.out.println(cliente);
			//Enviar el objeto al modelo para que lo inserte en la BBDD
			try {
				modeloClientes.agregarNuevoCliente(cliente);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Se lo envía a hacer la encuesta para clasificarlo
			request.setAttribute("errorRegistro", false);
			RequestDispatcher miDispatcher = request.getRequestDispatcher("/clasificacion.jsp");
			miDispatcher.forward(request, response);
		} else {
			//Si no se llenaron bien los campos o quedó vacío alguno:
			//Volver a registro con el msj de error
			request.setAttribute("errorRegistro", true);
			RequestDispatcher miDispatcher = request.getRequestDispatcher("/registro.jsp");
			miDispatcher.forward(request, response);
		}
		
	}

	private void logueoCliente(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		//Leer datos recibidos y crear el objeto cliente:
		String nombre = request.getParameter("nombre");
		String pass = request.getParameter("pass");
		Cliente cliente_verificar = new Cliente(nombre, pass);
		
		//Verificamos si ya existe:
		Cliente cliente_recibido = this.modeloClientes.getCliente(cliente_verificar);
		
		if(cliente_recibido != null) {
			
			//Agregar ese cliente a la respuesta (request)
			request.setAttribute("cliente", cliente_recibido);
			//Además, se avisa que no hubo error en el logueo
			request.setAttribute("errorLogin", false);
			
			//Enviar Cliente al portafolio o a la encuesta(jsp)
			if(cliente_recibido.getPerfil().equals(TipoPerfil.NINGUNO)) {
				RequestDispatcher miDispatcher = request.getRequestDispatcher("/perfil.jsp");
				miDispatcher.forward(request, response);
			}else {
				request.setAttribute("cliente", cliente_recibido);
				RequestDispatcher miDispatcher = request.getRequestDispatcher("/portafolio.jsp");
				miDispatcher.forward(request, response);
			}
			
		}else {
			//Login no válido, vuelve a login con mensaje de error
			request.setAttribute("errorLogin", true);
			RequestDispatcher miDispatcher = request.getRequestDispatcher("/login.jsp");
			miDispatcher.forward(request, response);
		}
		
	}

}
