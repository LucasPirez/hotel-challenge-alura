package sql.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PruebaConexion {

	
	public static void main(String[] args) throws SQLException {
		
	Connection con = DriverManager.getConnection(
			"jdbc:mysql://localhost:3306/huespedes?useTimeZone=true&serverTimeZone=UTC", 
				"root", "123456");
	
		System.out.println("Cerrando coneccion");
		
		con.close();
	}
}
