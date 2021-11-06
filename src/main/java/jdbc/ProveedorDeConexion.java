package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dao.DatosPerdidosError;

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

	public static boolean cerrarConexion() {
		if (conexion != null) {
			try {
				conexion.close();
			} catch (SQLException e) {
				throw new DatosPerdidosError(e);
			}
			return true;
		} else
			return false;
	}
}
