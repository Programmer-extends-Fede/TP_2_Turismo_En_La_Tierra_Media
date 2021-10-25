package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map.Entry;

import jdbc.ProveedorDeConexion;
import modelo.Itinerario;
import modelo.Usuario;
import tierraMediaConsola.TierraMedia;

public class UsuarioDAO {

	public HashMap<Integer, Usuario> cargarUsuarios() {
		String sql = "SELECT * FROM usuarios";
		HashMap<Integer, Usuario> usuarios = new HashMap<Integer, Usuario>();
		try {
			Connection conexion = ProveedorDeConexion.getConexion();
			PreparedStatement declaracion = conexion.prepareStatement(sql);
			ResultSet resultados = declaracion.executeQuery();
			
			ItinerarioDAO itinerarioDAO = FabricaDAO.getItinerarioDAO();
			TierraMedia tierraMedia = TierraMedia.getTierraMedia();
			HashMap<Integer, Itinerario> itinerarios = itinerarioDAO.cargarItinerarios(tierraMedia.getPromociones(),
					tierraMedia.getAtracciones());

			while (resultados.next()) {
				usuarios.put(resultados.getInt("id"), crearUsuario(resultados));
			}

			for (Entry<Integer, Itinerario> itinerario : itinerarios.entrySet()) {
				Usuario usuario = usuarios.get(itinerario.getKey());
				usuario.setItinerario(itinerario.getValue());
			}

		} catch (Exception e) {
			throw new DatosPerdidosError(e);
		}
		return usuarios;
	}

	public int actualizar(Usuario usuario) {
		String sql = "UPDATE usuarios SET dinero_disp = ?, tiempo_disp = ? WHERE id = ?";
		int filasModificadas = 0;

		try {
			Connection conexion = ProveedorDeConexion.getConexion();
			PreparedStatement declaracion = conexion.prepareStatement(sql);

			declaracion.setInt(1, usuario.getDineroDisponible());
			declaracion.setDouble(2, usuario.getTiempoDisponible());
			declaracion.setInt(3, usuario.getId());

			filasModificadas = declaracion.executeUpdate();
		} catch (Exception e) {
			throw new DatosPerdidosError(e);
		}
		return filasModificadas;
	}

	private Usuario crearUsuario(ResultSet resultados) throws Exception {
		int id = resultados.getInt("id");
		String nombre = resultados.getString("nombre");
		int dinero = resultados.getInt("dinero_disp");
		double tiempo = resultados.getDouble("tiempo_disp");
		String preferencia = resultados.getString("tipo_preferencia");

		return new Usuario(id, nombre, dinero, tiempo, preferencia);
	}
}
