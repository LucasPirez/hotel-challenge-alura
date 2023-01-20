package com.alura.jdbc.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class UserDAO {

	final private Connection con;

	public UserDAO(Connection con) {
		this.con = con;
	}

	public String verificarUser(String user) {
		String resultSetPassword = null;

		try (con) {
			con.setAutoCommit(false);
			final PreparedStatement statement = con
					.prepareStatement("select * from tbusers where aliasUser = '" + user + "'");

			try (statement) {
				final ResultSet resultSet = statement.executeQuery();
				try (resultSet) {
					while (resultSet.next()) {
						resultSetPassword = resultSet.getString("passwordUser");
					}
				}
			}
			con.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSetPassword;
	}

	public void createUser(String name, String password) {

		try (con) {
			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO TBUSERS(aliasUser,passwordUser)" + " VALUES(?,?)",
					java.sql.Statement.RETURN_GENERATED_KEYS);

			try (statement) {
				statement.setString(1, name);
				statement.setString(2, password);
				statement.execute();
				System.out.println(name);
				System.out.println(password);
				final ResultSet resultSet = statement.getGeneratedKeys();

				try (resultSet) {
					while (resultSet.next()) {
						JOptionPane.showMessageDialog(null, this,
								"Se ah creado el usuario: " + name + "password: " + password, 0);
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
