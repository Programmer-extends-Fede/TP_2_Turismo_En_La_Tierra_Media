package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ProveedorDeConexion {

	private static String url = "jdbc:sqlite:TierraMediaBBDD.db";
	private static Connection conexion;

	public static Connection getConexion() throws SQLException {
		if (conexion == null) {
			conexion = DriverManager.getConnection(url);
		}
		return conexion;
	}
	
	public static Connection getConexion(String url) throws SQLException {
		if (conexion == null) {
			conexion = DriverManager.getConnection("jdbc:sqlite:" + url);
		}
		return conexion;
	}
}
