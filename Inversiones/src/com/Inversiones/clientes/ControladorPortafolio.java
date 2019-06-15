package com.Inversiones.clientes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ControladorPortafolio
 */
@WebServlet("/ControladorPortafolio")
public class ControladorPortafolio extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ModeloClientes modeloClientes;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControladorPortafolio() {
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
		
		
		int id_cliente = Integer.parseInt(request.getParameter("id_cliente"));
		Cliente cliente = null;
		try {
			cliente = this.modeloClientes.getCliente(id_cliente);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch (cliente.getPerfil()) {
		case CONSERVADOR: request.setAttribute("listaProductos", productosConservador); break;
		case MODERADO : request.setAttribute("listaProductos", productosModerado); break;
		case ARRIESGADO : request.setAttribute("listaProductos", productosArriesgado); break;
		default : request.setAttribute("listaProductos", productosConservador);
		}
		
		RequestDispatcher miDispatcher = request.getRequestDispatcher("/portafolio.jsp");
		miDispatcher.forward(request, response);
	}

}
