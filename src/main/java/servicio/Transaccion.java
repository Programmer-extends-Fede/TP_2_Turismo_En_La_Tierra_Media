package servicio;

import java.sql.Connection;
import java.sql.SQLException;

import dao.AtraccionDAO;
import dao.DatosPerdidosError;
import dao.FabricaDAO;
import dao.ItinerarioDAO;
import dao.UsuarioDAO;
import jdbc.ProveedorDeConexion;
import modelo.Usuario;

public class Transaccion {
	
	AtraccionDAO atraccioDAO = FabricaDAO.getAtraccionDAO();
	ItinerarioDAO itinerarioDAO = FabricaDAO.getItinerarioDAO();
	UsuarioDAO usuarioDAO = FabricaDAO.getUsuarioDAO();

	public boolean actualizarEnBBDD(Usuario usuario) {
		Connection conexion = null;
		try {
			conexion = ProveedorDeConexion.getConexion();
			conexion.setAutoCommit(false);

			try {
				usuarioDAO.actualizar(usuario);
				itinerarioDAO.actualizar(usuario.getItinerario());
				atraccioDAO.actualizarCupo(usuario.getItinerario().getAtraccionesCompradas());
				conexion.commit();
				
			} catch (DatosPerdidosError e) {
				conexion.rollback();
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			try {
				conexion.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
