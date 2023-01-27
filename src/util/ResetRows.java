package util;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.alura.jdbc.DAO.HuespedDAO;
import com.alura.jdbc.DAO.ReservaDAO;

import sql.connection.ConnectionFactory;

public class ResetRows {
private int number;
private JTable tbReservas;
private JTable tbHuespedes;
private DefaultTableModel modelo;
private DefaultTableModel modeloH;
	
public ResetRows(int number,JTable tbReservas,JTable tbHuespedes,DefaultTableModel modelo,DefaultTableModel modeloH) {
	this.number = number;
	this.tbHuespedes =tbHuespedes;
	this.tbReservas = tbReservas;
	this.modelo = modelo;
	this.modeloH = modeloH;
}


	public void reset() {
		 if(number == 0) {
			 int filas = tbReservas.getRowCount();
			 System.out.println("reservas");
		    for (int a = 0; filas > a; a++) {
		       modelo.removeRow(0);
		    }
		    ReservaDAO reservaDAO = new ReservaDAO(new ConnectionFactory().recuperarCenexion());
		    
		    reservaDAO.listar().forEach(a -> modelo.addRow(new Object[] { a.getID(), a.getID_persona(),
		    		a.getFecha_entrada(), a.getFecha_salida(), a.getValor(), a.getForma_pago(),a.getTipoHabitacion() }));
		}else {
			 int filas = tbHuespedes.getRowCount();
			 
			    for (int a = 0; filas > a; a++) {
			       modeloH.removeRow(0);
			    }
			HuespedDAO huespedDAO = new HuespedDAO(new ConnectionFactory().recuperarCenexion());
			
			huespedDAO.listar().forEach(a -> modeloH.addRow(new Object[] { a.getID(), a.getNombre(), a.getApellido(),
					a.getFechaNacimiento(), a.getNacionalidad(), a.getTelefono() }));
			
		}
		
	}
	
	public void listarBusqueda(String itemSelected, String texto) {
		 if(number == 0) {
			 int filas = tbReservas.getRowCount();
			 
		    for (int a = 0; filas > a; a++) {
		       modelo.removeRow(0);
		    }
		ReservaDAO reservaDAO = new ReservaDAO(new ConnectionFactory().recuperarCenexion());
		reservaDAO.listarBusqueda(itemSelected,texto).forEach(a -> modelo.addRow(new Object[] { a.getID(), a.getID_persona(),
				a.getFecha_entrada(), a.getFecha_salida(), a.getValor(), a.getForma_pago(),a.getTipoHabitacion()}));
		 }else {
			 int filas = tbHuespedes.getRowCount();
			 for(int a=0; filas>a;a++) {
				 modeloH.removeRow(0);
			 }
			 HuespedDAO huespedDAO = new HuespedDAO(new ConnectionFactory().recuperarCenexion());
		 
			 huespedDAO.listarBusqueda(itemSelected, texto).forEach(a -> modeloH.addRow(new Object[] { a.getID(), a.getNombre(), a.getApellido(),
						a.getFechaNacimiento(), a.getNacionalidad(), a.getTelefono() }));
		 
		 }
	}
}






