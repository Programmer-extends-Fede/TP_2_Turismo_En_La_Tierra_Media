package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import jdbc.ProveedorDeConexion;
import modelo.Atraccion;

public class AtraccionDAO {

	public HashMap<Integer, Atraccion> cargarAtracciones() {
		String sql = "SELECT * FROM atracciones";
		HashMap<Integer, Atraccion> atracciones = new HashMap<Integer, Atraccion>();

		try {
			Connection conexion = ProveedorDeConexion.getConexion();
			PreparedStatement declaracion = conexion.prepareStatement(sql);
			ResultSet resultados = declaracion.executeQuery();

			while (resultados.next()) {
				atracciones.put(resultados.getInt("id"), crearAtraccion(resultados));
			}
		} catch (Exception e) {
			throw new DatosPerdidosError(e);
		}
		return atracciones;
	}

	public int actualizarCupo(List<Atraccion> atracciones) {
		String sql = "UPDATE atracciones SET cupo = ? WHERE id = ?";
		int filasModificadas = 0;
		try {
			Connection conexion = ProveedorDeConexion.getConexion();
			PreparedStatement declaracion = conexion.prepareStatement(sql);

			for (Atraccion atraccion : atracciones) {
				declaracion.setInt(1, atraccion.getCupo());
				declaracion.setInt(2, atraccion.getId());

				filasModificadas = declaracion.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DatosPerdidosError(e);
		}
		return filasModificadas;
	}

	private Atraccion crearAtraccion(ResultSet resultados) throws Exception {
		int id = resultados.getInt("id");
		String nombre = resultados.getString("nombre");
		int precio = resultados.getInt("precio");
		double duracion = resultados.getDouble("duracion");
		int cupo = resultados.getInt("cupo");
		String tipo = resultados.getString("fk_tipo_atraccion");

		return new Atraccion(id, nombre, precio, duracion, cupo, tipo);
	}
}
