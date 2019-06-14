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
public class ControladorEncuesta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControladorEncuesta() {
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
		
		String tiempoInversion = request.getParameter("tiempo");
		String reaccion10 = request.getParameter("reaccion10");
		String reaccion25 = request.getParameter("reaccion25");
		String experiencia = request.getParameter("experiencia");
		String preferencia = request.getParameter("preferencia");
		
		System.out.println("Usted eligió:\ntiempo de inversión:"+tiempoInversion+"\nreacción si cae 10%: "+reaccion10+"\nreacción si cae 25%"+reaccion25+"\nexperiencia: "+experiencia+"\npreferencia: "+preferencia);
		
		
		RequestDispatcher miDispatcher = request.getRequestDispatcher("/clasificacion.jsp");
		miDispatcher.forward(request, response);
		
	}

}
