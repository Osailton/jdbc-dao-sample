package connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import exceptions.DBException;

public class DBConnection {
	
	private static Connection conn = null;
	
	// Singleton pattern to return a Connection instance
	public static Connection getConnection() {
		
		// If the connection doesn't is null, the class instantiates it before return
		if (conn == null) {
			try {
				Properties p = loadProperties();
				String url = p.getProperty("dburl");
				String user = p.getProperty("user");
				String pass = p.getProperty("password");
				conn = DriverManager.getConnection(url, user, pass);
			}
			catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
		
		return conn;
	}
	
	public static Properties loadProperties() {
		
		// Get the properties file and load it
		try (FileInputStream fi = new FileInputStream("db.properties")) {
			Properties p = new Properties();
			p.load(fi);
			return p;
		}
		catch (IOException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}

}
