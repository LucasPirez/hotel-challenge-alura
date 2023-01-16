package com.alura.jdbc.modelo;

public class Reserva {
	
	private int ID;
	private int ID_persona;
	private String Fecha_entrada;
	private String Fecha_salida;
	private float valor;
	private String Forma_pago;
	private String tipoHabitacion;
	
	public Reserva(String fecha_entrada, String fecha_salida, float valor,String forma_pago,String tipoHabitacion) {
		this.Fecha_entrada = fecha_entrada;
		this.Fecha_salida = fecha_salida;
		this.valor = valor;
		this.Forma_pago = forma_pago;
		this.tipoHabitacion = tipoHabitacion;
	}

	public int getID() {
		return ID;
	}
	
	public void setID(int id) {
		this.ID  = id;
	}

	public int getID_persona() {
		return ID_persona;
	}

	public void setID_persona(int iD_p1ersona) {
		ID_persona = iD_p1ersona;
	}
	
	
	public String getFecha_entrada() {
		return Fecha_entrada;
	}

	public String getFecha_salida() {
		return Fecha_salida;
	}

	public float getValor() {
		return valor;
	}

	public String getForma_pago() {
		return Forma_pago;
	}
	
	@Override
public String toString() {
		return String.format("{id_persona:%s,"
				+ " fechaEntrada: %s,"
				+ " fechaSalida: %s,"
				+ " valor: %s,"
				+ " metodoPago: %s}",
				this.ID_persona,
				this.Fecha_entrada,
				this.Fecha_salida,
				this.valor,
				this.Forma_pago);
	}

	public String getTipoHabitacion() {
		return tipoHabitacion;
	}

	
	
	
	
	
}
