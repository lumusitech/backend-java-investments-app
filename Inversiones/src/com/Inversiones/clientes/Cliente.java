package com.Inversiones.clientes;


public class Cliente {
	private int id;
	private String nombre;
	private String pass;
	private String fechaNacimiento;
	private String email;
	private String dni;
	private Double saldo;
	private TipoPerfil perfil; //NINGUNO, CONSERVADOR, MODERADO, ARRIESGADO;
	
	public Cliente(int id, String nombre, String pass, String fechaNacimiento, String email, String dni, Double saldo, TipoPerfil perfil) {
		this.id = id;
		this.nombre = nombre;
		this.pass = pass;
		this.dni = dni;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.nombre = nombre;
		this.saldo = saldo;
		this.setPerfil(perfil);
	}
	
	public Cliente(String nombre, String pass, String fechaNacimiento, String email, String dni, Double saldo, TipoPerfil perfil) {
		this.nombre = nombre;
		this.pass = pass;
		this.dni = dni;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.nombre = nombre;
		this.saldo = saldo;
	}
	
	public Cliente(String nombre, String pass) {
		this.nombre = nombre;
		this.pass = pass;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

	public TipoPerfil getPerfil() {
		return perfil;
	}
	
	public void setPerfil(TipoPerfil perfil) {
		this.perfil = perfil;
	}

	@Override
	public String toString() {
		return "Cliente:\n\tid=" + id + "\n\tnombre=" + nombre + "\n\tpass=" + pass + "\n\tfechaNacimiento=" + fechaNacimiento
				+ "\n\temail=" + email + "\n\tdni=" + dni + "\n\tsaldo=" + saldo + "\n\tperfil=" + getPerfil() + "\n";
	}
	
	
}
