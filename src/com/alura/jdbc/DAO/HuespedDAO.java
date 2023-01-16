package com.alura.jdbc.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.alura.jdbc.modelo.Huesped;
import com.alura.jdbc.modelo.Reserva;

import sql.connection.ConnectionFactory;

public class HuespedDAO {

	final private Connection con;

	public HuespedDAO(Connection con) {
		this.con = con;
	}

	public int guardar(Huesped huesped) {
		int n = -1;
		try (con) {
			con.setAutoCommit(false);
			final java.sql.PreparedStatement statement = con.prepareStatement(
					"INSERT INTO TBHUESPEDES (Nombre, Apellido, FechaNacimiento, Nacionalidad, Telefono) "
							+ " VALUES(?,?,?,?,?)",
					java.sql.Statement.RETURN_GENERATED_KEYS);
			try (statement) {

				String x = huesped.getNombre();
				statement.setString(1, x);
				statement.setString(2, huesped.getApellido());
				statement.setString(3, huesped.getFechaNacimiento());
				statement.setString(4, huesped.getNacionalidad());
				statement.setInt(5, Integer.valueOf(huesped.getTelefono()));
				statement.execute();

				ResultSet resultSet = statement.getGeneratedKeys();

				while (resultSet.next()) {
					huesped.setID(resultSet.getInt(1));
					n = resultSet.getInt(1);
					System.out.println(String.format("fue insertado el huesped %s", huesped));
				}

				con.commit();
				con.close();
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return n;
	}

	public List<Huesped> listar() {

		List<Huesped> arr = new ArrayList<>();

		try {
			PreparedStatement statement = con.prepareStatement("SELECT * FROM TBHUESPEDES");
			statement.execute();

			ResultSet resultSet = statement.getResultSet();
			while (resultSet.next()) {
				Huesped fila = new Huesped(resultSet.getString("Nombre"), resultSet.getString("Apellido"),
						String.valueOf(resultSet.getDate("FechaNacimiento")), resultSet.getString("Nacionalidad"),
						String.valueOf(resultSet.getInt("Telefono")));
				fila.setID(resultSet.getInt("ID"));
				arr.add(fila);
			}
			con.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return arr;
	}

	public void editarHuesped(Huesped huesped) {
		try (con) {
			java.sql.PreparedStatement statement = con
					.prepareStatement("UPDATE TBHUESPEDES SET " + " Nombre = ?" + ", Apellido = ?"
							+ ", FechaNacimiento = ?" + ", Nacionalidad = ?" + ", Telefono = ?" + "  WHERE ID = ?");

			statement.setString(1, huesped.getNombre());
			statement.setString(2, huesped.getApellido());
			statement.setString(3, huesped.getFechaNacimiento());
			statement.setString(4, huesped.getNacionalidad());
			statement.setInt(5, Integer.valueOf(huesped.getTelefono()));
			statement.setInt(6, huesped.getID());
			statement.execute();

			int updateCount = statement.getUpdateCount();

			if (updateCount == 0) {
				JOptionPane.showMessageDialog(null, this, " No se ha actualizado ningun valor ", updateCount);
			} else {
				JOptionPane.showMessageDialog(null, String.format("Se ha actualizado el huesped %s ", huesped));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eliminarHuesped(Object id) {
		System.out.println(id);
		try {
			java.sql.PreparedStatement statement = con.prepareStatement("DELETE FROM TBHUESPEDES WHERE ID = ?");

			statement.setInt(1, (int) id);
			statement.execute();
			int cantidadEliminada = statement.getUpdateCount();

			if (cantidadEliminada == 1) {
				JOptionPane.showMessageDialog(null, cantidadEliminada + "  Eliminada Con Exito");
			} else {
				JOptionPane.showMessageDialog(null, "Ha Ocurrido un Error");
			}
			con.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public List<Huesped> listarBusqueda(String tipo,String value){
		List<Huesped> lista= new ArrayList<>();
		
		try (con) {
			final PreparedStatement statement = con.prepareStatement("SELECT * FROM TBHUESPEDES WHERE " +  tipo + " = '" + value + "'");
			
			try (statement) {
				final ResultSet resultSet = statement.executeQuery();
				try (resultSet) {
					while (resultSet.next()) {

						Huesped huesped = new Huesped(resultSet.getString("Nombre"), resultSet.getString("Apellido"),
								String.valueOf(resultSet.getDate("FechaNacimiento")), resultSet.getString("Nacionalidad"),
								String.valueOf(resultSet.getInt("Telefono")));
						
						huesped.setID(resultSet.getInt("ID"));
						lista.add(huesped);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return lista;
	}
}
