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
	
	public boolean realizarTransaccionVenta(Transaccion transaccion) throws Exception{
		
		
		
		return true;
	}
	
	//////////////////////////////////////////////////////////////////
	
	public boolean realizarTransaccionCompra(Transaccion transaccion) throws Exception{
		
		
		
		return true;
	}
	
	//////////////////////////////////////////////////////////////////

	public void actualizarSaldo(Cliente cliente) throws Exception{
		// TODO Auto-generated method stub
		
	}
	
	//////////////////////////////////////////////////////////////////

	public void actualizarCantidad(Producto producto)  throws Exception{
		// TODO Auto-generated method stub
		
	}
	
	//////////////////////////////////////////////////////////////////

	public void registrarComision() {
		// TODO Auto-generated method stub
		
	}

	//////////////////////////////////////////////////////////////////
	
	public ArrayList<Producto> getPortafolio(int id_cliente) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//////////////////////////////////////////////////////////////////

	public void actualizarPortafolio(int id_cliente, int id_producto, int cantidad) {
		// TODO Auto-generated method stub
		
	}
	
	//////////////////////////////////////////////////////////////////

	public ArrayList<Producto> getProductos() {
		// TODO Auto-generated method stub
		return null;
	}
}





