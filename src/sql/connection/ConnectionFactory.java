package sql.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	
		public  Connection  recuperarCenexion() throws SQLException {
				Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/huespedes?useTimeZone=true&serverTimeZone=UTC", 
					"root", "123456");
			
			return con;
		
	}
}
