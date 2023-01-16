package sql.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {

	private DataSource dataSource;
	
	public  ConnectionFactory() {
		
		ComboPooledDataSource pooledDateSource = new ComboPooledDataSource();
		pooledDateSource.setJdbcUrl("jdbc:mysql://localhost:3306/huespedes?useTimeZone=true&serverTimeZone=UTC");
		pooledDateSource.setUser("root");
		pooledDateSource.setPassword("123456");
		
		this.dataSource = pooledDateSource;
	}
	
		public  Connection  recuperarCenexion() {
			
			try {
				return this.dataSource.getConnection();
				
			} catch (SQLException e) {
			throw new RuntimeException(e);
			}
		
	}
}
