package com.alura.jdbc.modelo;

public class Huesped {

	private int ID;
	private String Nombre;
	private String Apellido;
	private String FechaNacimiento;
	private String Nacionalidad;
	private String Telefono;
	
	public Huesped(String Nombre, String Apellido, String FechaNacimiento, String Nacionalidad, String Telefono) {
		this.Nombre = Nombre;
		this.Apellido = Apellido;
		this.FechaNacimiento = FechaNacimiento;;
		this.Nacionalidad = Nacionalidad;
		this.Telefono =Telefono;
	}
	
	
	public String getNombre() {
		return this.Nombre;
	}

	public String getApellido() {
		return Apellido;
	}

	public String getFechaNacimiento() {
		return FechaNacimiento;
	}

	public String getNacionalidad() {
		return Nacionalidad;
	}

	public String getTelefono() {
		return Telefono;
	}
	public int getID(){
		return this.ID;
	}


	public void setID(int iD) {
		this.ID = iD;
	}
	
	@Override
		public String toString() {
		return String.format("{id:%s,"
				+ " nombre:%s,"
				+ " apellido:%s,"
				+ " fechaNacimiento:%s,"
				+ " nacionalidad:%s,"
				+ " Telefono:%s}",
				this.ID,
				this.Nombre,
				this.Apellido,
				this.FechaNacimiento,
				this.Nacionalidad,
				this.Telefono);
	}


}
