package com.Inversiones.clientes;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import java.sql.*;

public class ModeloClientes {
	
	private DataSource origenDatos; 
	
	public ModeloClientes(DataSource origenDatos) {
		this.origenDatos = origenDatos; //Se alamacena el pool creado
	}
	
	//////////////////////////////////////////////////////////////////
	
	public List<Cliente> getClientes() throws Exception{
		
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		Connection miConexion = null;
		Statement miStatement = null;
		ResultSet miResultSet = null;
		
		try {
			//Establecer la conexión
			miConexion = this.origenDatos.getConnection();
			
			
			//Crear la sentencia SQL y el Statement
			String SQL = "SELECT * FROM cliente";
			miStatement = miConexion.createStatement();
			
			//Ejecutar SQL
			miResultSet = miStatement.executeQuery(SQL);
			
			//Recorrer el ResultSet obtenido
			while(miResultSet.next()) {
				clientes.add(
					new Cliente(
						miResultSet.getInt("id"), 
						miResultSet.getString("nombre"),
						miResultSet.getString("pass"),
						miResultSet.getString("nac"),
						miResultSet.getString("email"),
						miResultSet.getString("dni"),
						miResultSet.getDouble("saldo"),
						TipoPerfil.valueOf(miResultSet.getString("perfil"))//valueOf() recibe un String y devuelve el Enum que conincide con eso
					)
				);
			}
		}finally {
			miStatement.close();
			miConexion.close();
		}
		
		return clientes;
	}

	//////////////////////////////////////////////////////////////////

	public void agregarNuevoCliente(Cliente cliente) throws Exception{
		
		Connection miConexion = null;
		PreparedStatement miStatement = null;

		try {
			//Obtener la conexión
			miConexion = origenDatos.getConnection();
		
			
			//Crear la instrucción SQL que inserte el Cliente (Statement)
			String SQL = "INSERT INTO cliente(nombre_usuario, email, pass, saldo, dni, fecha_nacimiento) VALUES(?,?,?,?,?,?,?);";
			miStatement = miConexion.prepareStatement(SQL);
			
			//Establecer los parámetros para el Cliente
			miStatement.setString(1, cliente.getNombre());
			miStatement.setString(2, cliente.getEmail());
			miStatement.setString(3, cliente.getPass());
			miStatement.setDouble(4, cliente.getSaldo());
			miStatement.setString(5, cliente.getDni());
			miStatement.setString(6, cliente.getFechaNacimiento());
			miStatement.setString(7, cliente.getPerfil().toString());
		
			
			//Ejecutar la instrucción SQL
			miStatement.execute();
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			miStatement.close();
			miConexion.close();
		}
	}
	
	//////////////////////////////////////////////////////////////////

	public Cliente getCliente(Cliente cliente_verificar) throws Exception{
		Cliente c = null;
		
		Connection miConexion = null;
		PreparedStatement miStatement = null;
		ResultSet miResultSet = null;
		
		try {
			// Establecer la conexión
			miConexion = origenDatos.getConnection();
			
			//Crear SQL para buscar el Cliente con el id pasado
			String SQL = "SELECT * FROM cliente WHERE nombre_usuario=? AND pass=?";
			
			//Crear la consulta preparada
			miStatement = miConexion.prepareStatement(SQL);
			
			//Establecer los parámetros
			miStatement.setString(1, cliente_verificar.getNombre());
			miStatement.setString(2, cliente_verificar.getPass());
			
			//Ejecutar la consulta
			miResultSet = miStatement.executeQuery();
			
			//Obtener los datos de respuesta
			if(miResultSet.next()) {
				c = new Cliente(
						miResultSet.getInt("id_cliente"),
						miResultSet.getString("nombre_usuario"), 
						miResultSet.getString("pass"),
						miResultSet.getString("fecha_nacimiento"),
						miResultSet.getString("email"),
						miResultSet.getString("dni"),
						miResultSet.getDouble("saldo"),
						TipoPerfil.valueOf(miResultSet.getString("perfil"))
					);
			} else {
				throw new Exception("El cliente no existe, revise sus credenciales o proceda a registrarse");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			miStatement.close();
			miConexion.close();
		}
		
		return c;
		
	}
	
	//////////////////////////////////////////////////////////////////
	
	public Cliente getCliente(int id_cliente) throws Exception{
		Cliente c = null;
		
		Connection miConexion = null;
		PreparedStatement miStatement = null;
		ResultSet miResultSet = null;
		
		try {
			// Establecer la conexión
			miConexion = origenDatos.getConnection();
			
			//Crear SQL para buscar el Cliente con el id pasado
			String SQL = "SELECT * FROM cliente WHERE id_cliente=?";
			
			//Crear la consulta preparada
			miStatement = miConexion.prepareStatement(SQL);
			
			//Establecer los parámetros
			miStatement.setInt(1, id_cliente);
			
			//Ejecutar la consulta
			miResultSet = miStatement.executeQuery();
			
			//Obtener los datos de respuesta
			if(miResultSet.next()) {
				c = new Cliente(
						miResultSet.getInt("id_cliente"),
						miResultSet.getString("nombre_usuario"), 
						miResultSet.getString("pass"),
						miResultSet.getString("fecha_nacimiento"),
						miResultSet.getString("email"),
						miResultSet.getString("dni"),
						miResultSet.getDouble("saldo"),
						TipoPerfil.valueOf(miResultSet.getString("perfil"))
					);
			} else {
				throw new Exception("El cliente no existe, revise sus credenciales o proceda a registrarse");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			miStatement.close();
			miConexion.close();
		}
		
		return c;
		
	}
	
	//////////////////////////////////////////////////////////////////
	
	public boolean actualizarPerfil(int id_cliente, TipoPerfil perfil) {
		
		return true;
	}
	
	
}
