package com.Inversiones.clientes;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ControladorEncuesta
 */
@WebServlet("/ControladorEncuesta")
public class ControladorPerfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ModeloClientes modeloClientes;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControladorPerfil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		clasificar(request, response);
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////

	private void clasificar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int id_cliente = Integer.parseInt(request.getParameter("id_cliente"));
		int tiempo = Integer.parseInt(request.getParameter("tiempo")); 
		int reaccion10 = Integer.parseInt(request.getParameter("reaccion10")); 
		int reaccion25 = Integer.parseInt(request.getParameter("reaccion25"));
		int experiencia = Integer.parseInt(request.getParameter("experiencia"));
		int preferencia = Integer.parseInt(request.getParameter("preferencia"));
		
		int suma = tiempo + reaccion10 + reaccion25 + experiencia + preferencia;
		TipoPerfil perfil;
		
		if(suma>=25) perfil = TipoPerfil.ARRIESGADO;
		if(suma<25 && suma>=10) perfil = TipoPerfil.MODERADO;
		else perfil = TipoPerfil.CONSERVADOR;
		
		boolean actualizado = this.modeloClientes.actualizarPerfil(id_cliente, perfil);
		
		request.setAttribute("id_cliente", id_cliente);
		
		if (actualizado) {
			RequestDispatcher miDispatcher = request.getRequestDispatcher("/ControladorPortafolio.java");
			miDispatcher.forward(request, response);
		}
	}

}
