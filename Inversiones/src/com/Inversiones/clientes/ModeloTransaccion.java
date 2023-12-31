package com.Inversiones.clientes;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class ModeloTransaccion {
private DataSource origenDatos; 
	
	public ModeloTransaccion(DataSource origenDatos) {
		this.origenDatos = origenDatos; //Se alamacena el pool creado
	}
	
	//////////////////////////////////////////////////////////////////
	
	public Producto getProducto(String nombre) throws Exception{
		Producto p = null;
		
		Connection miConexion = null;
		PreparedStatement miStatement = null;
		ResultSet miResultSet = null;
		
		try {
			// Establecer la conexión
			miConexion = origenDatos.getConnection();
			
			//Crear SQL para buscar el Cliente con el id pasado
			String SQL = "SELECT * FROM producto_inversion WHERE nombre=?";
			
			//Crear la consulta preparada
			miStatement = miConexion.prepareStatement(SQL);
			
			//Establecer los parámetros
			miStatement.setString(1, nombre);
			
			//Ejecutar la consulta
			miResultSet = miStatement.executeQuery();
			
			//Obtener los datos de respuesta
			if(miResultSet.next()) {
				p = new Producto(
						miResultSet.getInt("id_producto"),
						miResultSet.getInt("cantidad"), 
						miResultSet.getString("nombre"),
						miResultSet.getDouble("precio"),
						TipoProducto.valueOf(miResultSet.getString("tipo"))
					);
			} else {
				throw new Exception("El producto no existe, revise sus credenciales o proceda a registrarse");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			miStatement.close();
			miConexion.close();
		}
		
		return p;
		
	}
	
	//////////////////////////////////////////////////////////////////
	
	public Producto getProducto(int id_prod) throws Exception{
		Producto p = null;
		
		Connection miConexion = null;
		PreparedStatement miStatement = null;
		ResultSet miResultSet = null;
		
		try {
			// Establecer la conexión
			miConexion = origenDatos.getConnection();
			
			//Crear SQL para buscar el Cliente con el id pasado
			String SQL = "SELECT * FROM producto_inversion WHERE id_producto=?";
			
			//Crear la consulta preparada
			miStatement = miConexion.prepareStatement(SQL);
			
			//Establecer los parámetros
			miStatement.setInt(1, id_prod);
			
			//Ejecutar la consulta
			miResultSet = miStatement.executeQuery();
			
			//Obtener los datos de respuesta
			if(miResultSet.next()) {
				p = new Producto(
						miResultSet.getInt("id_producto"),
						miResultSet.getInt("cantidad"), 
						miResultSet.getString("nombre"),
						miResultSet.getDouble("precio"),
						TipoProducto.valueOf(miResultSet.getString("tipo"))
					);
			} else {
				throw new Exception("El producto no existe, revise sus credenciales o proceda a registrarse");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			miStatement.close();
			miConexion.close();
		}
		
		return p;
		
	}
	
	//////////////////////////////////////////////////////////////////
	
	public int realizarTransaccionVenta(Transaccion transaccion) throws Exception{
		int idGenerado = 0;
		Connection miConexion = null;
		PreparedStatement miStatement = null;

		try {
			//Obtener la conexión
			miConexion = origenDatos.getConnection();
			
			//Crear la instrucción SQL que inserte el Cliente (Statement)
			String SQL = "INSERT INTO transaccion(fecha, cantidad_producto, id_cliente, id_producto, precio, tipo) VALUES(?,?,?,?,?,?);";
			miStatement = miConexion.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			
			//Establecer los parámetros para el Cliente
			miStatement.setString(1, transaccion.getFecha());
			miStatement.setInt(2, transaccion.getCantidad());
			miStatement.setInt(3, transaccion.getId_cliente());
			miStatement.setInt(4, transaccion.getId_producto());
			miStatement.setDouble(5, transaccion.getTotal());
			miStatement.setString(6, transaccion.getTipo().toString());
			

			int affectedRows = miStatement.executeUpdate();
			if (affectedRows == 0) {
			        throw new SQLException("No se pudo guardar");
			}

			ResultSet generatedKeys = miStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
			         idGenerado = generatedKeys.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}finally {
			miStatement.close();
			miConexion.close();
		}
		return idGenerado;
	}
	
	//////////////////////////////////////////////////////////////////
	
	public int realizarTransaccionCompra(Transaccion transaccion) throws Exception{
		int idGenerado = 0;
		Connection miConexion = null;
		PreparedStatement miStatement = null;

		try {
			//Obtener la conexión
			miConexion = origenDatos.getConnection();
			
			//Crear la instrucción SQL que inserte el Cliente (Statement)
			String SQL = "INSERT INTO transaccion(fecha, cantidad_producto, id_cliente, id_producto, precio, tipo) VALUES(?,?,?,?,?,?);";
			miStatement = miConexion.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			
			//Establecer los parámetros para el Cliente
			miStatement.setString(1, transaccion.getFecha());
			miStatement.setInt(2, transaccion.getCantidad());
			miStatement.setInt(3, transaccion.getId_cliente());
			miStatement.setInt(4, transaccion.getId_producto());
			miStatement.setDouble(5, transaccion.getTotal());
			miStatement.setString(6, transaccion.getTipo().toString());
			

			int affectedRows = miStatement.executeUpdate();
			if (affectedRows == 0) {
			        throw new SQLException("No se pudo guardar");
			}

			ResultSet generatedKeys = miStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
			         idGenerado = generatedKeys.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}finally {
			miStatement.close();
			miConexion.close();
		}
		return idGenerado;
	}
	
	//////////////////////////////////////////////////////////////////

	public void actualizarSaldo(Cliente cliente) throws Exception{
		Connection miConexion = null;
		PreparedStatement miStatement = null;
		
		try {			
			//Obtener la conexión
			miConexion = origenDatos.getConnection();

			//Crear la instrucción SQL que inserte el Cliente (Statement)
			String SQL = "UPDATE cliente SET saldo=? WHERE id_cliente=?";
			miStatement = miConexion.prepareStatement(SQL);

			//Establecer los parámetros para el Cliente
			miStatement.setDouble(1, cliente.getSaldo());
			miStatement.setInt(2, cliente.getId());

			//Ejecutar la instrucción SQL
			miStatement.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			miStatement.close();
			miConexion.close();
		}	
	}
	
	//////////////////////////////////////////////////////////////////

	public void actualizarCantidad(Producto producto)  throws Exception{
		Connection miConexion = null;
		PreparedStatement miStatement = null;
		
		try {			
			//Obtener la conexión
			miConexion = origenDatos.getConnection();
			
			//Crear la instrucción SQL que actualiza la cantidad de los productos (Statement)
			String SQL = "UPDATE producto_inversion SET cantidad=? WHERE id_producto=?";
			miStatement = miConexion.prepareStatement(SQL); 
			//Establecer los parámetros para el Cliente
			miStatement.setDouble(1, producto.getCantidad());
			miStatement.setInt(2, producto.getId());

			//Ejecutar la instrucción SQL
			miStatement.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}finally {
			miStatement.close();
			miConexion.close();
		}	
		
	}
	
	//////////////////////////////////////////////////////////////////

	public void registrarComision(int id_transaccion, Double precio) throws Exception{
		Connection miConexion = null;
		PreparedStatement miStatement = null;
		
		try {			
			//Obtener la conexión
			miConexion = origenDatos.getConnection();

			//Crear la instrucción SQL que inserte la comisión (Statement)
			String SQL = "INSERT INTO comision(precio, id_transaccion) VALUES(?,?)";
			miStatement = miConexion.prepareStatement(SQL);

			//Establecer los parámetros para el Cliente
			miStatement.setDouble(1, precio);
			miStatement.setInt(2, id_transaccion);

			//Ejecutar la instrucción SQL
			miStatement.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}finally {
			miStatement.close();
			miConexion.close();
		}	
		
	}

	//////////////////////////////////////////////////////////////////
	
	public ArrayList<Producto> getPortafolio(int id_cl) throws Exception{
		
		ArrayList<Producto> productos = new ArrayList<Producto>();
		productos = this.getProductos();
		
		ArrayList<Portafolio> portafolio = new ArrayList<Portafolio>();
		
		ArrayList<Producto> resultado = new ArrayList<Producto>();
		
		Connection miConexion = null;
		Statement miStatement = null;
		ResultSet miResultSet = null;
		
		try {
			//Establecer la conexión
			miConexion = this.origenDatos.getConnection();
			
			//Crear la sentencia SQL y el Statement
			String SQL = "SELECT * FROM portafolio";
			miStatement = miConexion.createStatement();
			
			//Ejecutar SQL
			miResultSet = miStatement.executeQuery(SQL);
			
			//Recorrer el ResultSet obtenido
			while(miResultSet.next()) {
				portafolio.add(
					new Portafolio(
						miResultSet.getInt("id_portafolio"), 
						miResultSet.getInt("id_cliente"),
						miResultSet.getInt("id_producto"),
						miResultSet.getInt("cantidad_producto"),
						miResultSet.getDouble("precio")
					)
				);
			}
			
			for(Producto p : productos) {
				for(Portafolio pf : portafolio) {
					if(p.getId() == pf.getId_producto() && pf.getId_cliente() == id_cl) {
						p.setCantidad(pf.getCantidad_producto());
						resultado.add(p);
					}
				}
			}
			
		}finally {
			miStatement.close();
			miConexion.close();
		}
		
		return resultado;
	}
	
	//////////////////////////////////////////////////////////////////

	public void actualizarPortafolioCompra(int id_cl, int id_prod, int cantidad) throws Exception{
		
		Connection miConexion = null;
		PreparedStatement miStatement = null;
		
		Double precio = getProducto(id_prod).getPrecio();
		int cantidadActualizada = 0;
		
		boolean insert = false;
		ArrayList<Producto> portafolio = getPortafolio(id_cl);
		
		if(portafolio.size() == 0) {
			insert = true;
			cantidadActualizada = cantidad;
		}else {
			for(Producto pf : portafolio) {
				if(pf.getId() != id_prod) {
					insert = true;//Si no tengo a ese producto en el portafolio, haré un insert
					cantidadActualizada = cantidad;
				}else {
					insert = false;
					cantidadActualizada = pf.getCantidad() + cantidad;
				}
			}//else: no será un insert porque ya lo tenía, haré un update
		}

		try {			
			//Obtener la conexión
			miConexion = origenDatos.getConnection();
			
			//Crear la instrucción SQL que inserte el Cliente (Statement)
			String SQL;
			if(insert) {
				System.out.println("\n\nSe procede a insertar el producto al portafolio");
				SQL = "INSERT INTO portafolio(id_cliente, id_producto, cantidad_producto, precio) VALUES(?,?,?,?)";
				miStatement = miConexion.prepareStatement(SQL);

				//Establecer los parámetros para el Cliente
				miStatement.setInt(1, id_cl);
				miStatement.setInt(2, id_prod);
				miStatement.setInt(3, cantidadActualizada);
				miStatement.setDouble(4, precio);
			}else {
				SQL = "UPDATE portafolio SET cantidad_producto=? WHERE id_producto=? AND id_cliente=?";
				miStatement = miConexion.prepareStatement(SQL);

				//Establecer los parámetros para el Cliente
				miStatement.setInt(1, cantidadActualizada);
				miStatement.setInt(2, id_prod);
				miStatement.setInt(3, id_cl);
			}

			//Ejecutar la instrucción SQL
			miStatement.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e);
		}finally {
			miStatement.close();
			miConexion.close();
		}	
	}
	
//////////////////////////////////////////////////////////////////

public void actualizarPortafolioVenta(int id_cl, int id_prod, int cantidadRestante) throws Exception{

	Connection miConexion = null;
	PreparedStatement miStatement = null;
	boolean delete = false;
	
	if(cantidadRestante == 0) delete = true;
	
	try {			
		//Obtener la conexión
		miConexion = origenDatos.getConnection();
		
		if(delete) {
			//Crear la instrucción SQL que borre el producto (Statement)
			String SQL = "DELETE FROM portafolio WHERE id_producto=? AND id_cliente=?";
			miStatement = miConexion.prepareStatement(SQL);
			
			//Establecer los parámetros para el Cliente
			miStatement.setInt(1, id_prod);
			miStatement.setInt(2, id_cl);
		}else {
			//Crear la instrucción SQL que inserte el Cliente (Statement)
			String SQL = "UPDATE portafolio SET cantidad_producto=? WHERE id_producto=? AND id_cliente=?";
			miStatement = miConexion.prepareStatement(SQL);
			
			//Establecer los parámetros para el Cliente
			miStatement.setInt(1, cantidadRestante);
			miStatement.setInt(2, id_prod);
			miStatement.setInt(3, id_cl);
		}
		
		
		//Ejecutar la instrucción SQL
		miStatement.executeUpdate();
	
	} catch (Exception e) {
		// TODO: handle exception
	}finally {
		miStatement.close();
		miConexion.close();
	}	
}
	
	//////////////////////////////////////////////////////////////////

	public ArrayList<Producto> getProductos() throws Exception{
		ArrayList<Producto> productos = new ArrayList<Producto>();
		
		Connection miConexion = null;
		Statement miStatement = null;
		ResultSet miResultSet = null;
		
		try {
			//Establecer la conexión
			miConexion = this.origenDatos.getConnection();
			
			//Crear la sentencia SQL y el Statement
			String SQL = "SELECT * FROM producto_inversion";
			miStatement = miConexion.createStatement();
			
			//Ejecutar SQL
			miResultSet = miStatement.executeQuery(SQL);
			
			//Recorrer el ResultSet obtenido
			while(miResultSet.next()) {
				productos.add(
					new Producto(
						miResultSet.getInt("id_producto"), 
						miResultSet.getInt("cantidad"),
						miResultSet.getString("nombre"),
						miResultSet.getDouble("precio"),
						TipoProducto.valueOf(miResultSet.getString("tipo"))
					)
				);
			}
		}finally {
			miStatement.close();
			miConexion.close();
		}
		return productos;
	}
	
	//////////////////////////////////////////////////////////////////

	public void crearPortafolio(int id_cliente) throws Exception{
		Connection miConexion = null;
		PreparedStatement miStatement = null;
		
		try {			
			//Obtener la conexión
			miConexion = origenDatos.getConnection();

			//Crear la instrucción SQL que inserte el Cliente (Statement)
			String SQL = "INSERT INTO portafolio(id_cliente, id_producto, cantidad_producto, precio) VALUES(?,?,?,?)";
			miStatement = miConexion.prepareStatement(SQL);

			//Establecer los parámetros para el Cliente
			miStatement.setInt(1, id_cliente);
			miStatement.setInt(2, 0);
			miStatement.setInt(3, 0);
			miStatement.setDouble(4, 0);

			//Ejecutar la instrucción SQL
			miStatement.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			miStatement.close();
			miConexion.close();
		}	
		
	}
	
	//////////////////////////////////////////////////////////////////

}





