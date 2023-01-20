package com.alura.jdbc.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.alura.jdbc.modelo.Reserva;

public class ReservaDAO {
	final private Connection con;

	public ReservaDAO(Connection con) {
		this.con = con;
	}

	public List<Reserva> listar() {
		List<Reserva> lista = new ArrayList<>();

		try (con) {
			final PreparedStatement statement = con.prepareStatement("select * from tbreserva");

			try (statement) {
				final ResultSet resultSet = statement.executeQuery();
				try (resultSet) {
					while (resultSet.next()) {

						Reserva reserva = new Reserva(resultSet.getString("Fecha_entrada"), resultSet.getString("Fecha_salida"),
								resultSet.getFloat("valor"), resultSet.getString("Forma_pago"), resultSet.getString("Tipo_habitacion"));

						reserva.setID_persona(resultSet.getInt("ID_persona"));
						reserva.setID(resultSet.getInt("ID"));
						lista.add(reserva);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return lista;
	}

	public int guardarReserva(Reserva reserva) {
		int n = -1;
		try (con) {
			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO TBRESERVA (ID_persona, Fecha_entrada, Fecha_salida, valor, Forma_pago,Tipo_habitacion) "
							+ "VALUES(?,?,?,?,?,?)",
					java.sql.Statement.RETURN_GENERATED_KEYS);

			try (statement) {

				statement.setInt(1, reserva.getID_persona());
				statement.setString(2, reserva.getFecha_entrada());
				statement.setString(3, reserva.getFecha_salida());
				statement.setFloat(4, reserva.getValor());
				statement.setString(5, reserva.getForma_pago());
				statement.setString(6,reserva.getTipoHabitacion());
				statement.execute();

				final ResultSet resultSet = statement.getGeneratedKeys();

				try (resultSet) {
					while (resultSet.next()) {
						n = resultSet.getInt(1);
						System.out.println(String.format("Se realizo la siguiente reserva: %s", reserva));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}
	
	public void editarReserva(Reserva reserva) {
	
			try(con){
				
				final PreparedStatement statement = con.prepareStatement("UPDATE TBRESERVA SET " +
												" Fecha_entrada = ? " + 
												", Fecha_salida = ? " + 
												", valor = ?" + 
												", Forma_pago = ? " + 
												", Tipo_habitacion = ? " +
												" WHERE ID = ?");
				
				try(statement){
					
					statement.setString(1, reserva.getFecha_entrada());
					statement.setString(2,reserva.getFecha_salida());
					statement.setFloat(3, reserva.getValor());
					statement.setString(4,reserva.getForma_pago());
					statement.setInt(5,reserva.getID());
					statement.setString(6, reserva.getTipoHabitacion());
					statement.execute();

					int updateCount = statement.getUpdateCount();
					
					if (updateCount == 0) {
						JOptionPane.showMessageDialog(null, this, " No se ha actualizado ningun valor ", updateCount);
					} else {
						JOptionPane.showMessageDialog(null, String.format("Se ha actualizado el huesped %s ", reserva));
					}
					
					
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
	
	public void eliminarReserva(int ID) {
		
		try(con){
			final PreparedStatement statement = con.prepareStatement("DELETE FROM TBRESERVA WHERE  ID = ?");
			
			statement.setInt(1, ID);
			statement.execute();
			int cantidadEliminada = statement.getUpdateCount();

			if (cantidadEliminada == 1) {
				JOptionPane.showMessageDialog(null, cantidadEliminada + "  Eliminada Con Exito");
			} else {
				JOptionPane.showMessageDialog(null, "Ha Ocurrido un Error");
			}
		}catch(SQLException e2) {
			e2.printStackTrace();
		}
		
	}
	
	public List<Reserva> listarBusqueda(String tipo,String value){
		List<Reserva> lista= new ArrayList<>();
		
		try (con) {
			final PreparedStatement statement = con.prepareStatement("SELECT * FROM TBRESERVA WHERE " +  tipo + " = '" + value + "'");
			
			try (statement) {
				final ResultSet resultSet = statement.executeQuery();
				try (resultSet) {
					while (resultSet.next()) {

						Reserva reserva = new Reserva(resultSet.getString("Fecha_entrada"), resultSet.getString("Fecha_salida"),
								resultSet.getFloat("valor"), resultSet.getString("Forma_pago"), resultSet.getString("tipo_habitacion"));

						reserva.setID_persona(resultSet.getInt("ID_persona"));
						reserva.setID(resultSet.getInt("ID"));
						lista.add(reserva);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		lista.forEach(a ->  System.out.println(a.getFecha_entrada()));
		return lista;
	}

}
