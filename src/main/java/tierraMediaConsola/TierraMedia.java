package tierraMediaConsola;

import java.util.ArrayList;
import java.util.HashMap;

import dao.AtraccionDAO;
import dao.FabricaDAO;
import dao.PromocionDAO;
import dao.UsuarioDAO;
import modelo.Atraccion;
import modelo.Promocion;
import modelo.Sugerencia;
import modelo.Usuario;
import ordenarSugerencias.OrdenarSugerencias;

public class TierraMedia {

	private static TierraMedia tierraMedia;
	private HashMap<Integer, Usuario> usuarios = new HashMap<Integer, Usuario>();
	private HashMap<Integer, Atraccion> atracciones = new HashMap<Integer, Atraccion>();
	private HashMap<Integer, Promocion> promociones = new HashMap<Integer, Promocion>();
	private ArrayList<Sugerencia> sugerencias = new ArrayList<Sugerencia>();

	private TierraMedia() {
	}
	
	public synchronized static TierraMedia getTierraMedia() {
		if (tierraMedia == null) {
			tierraMedia = new TierraMedia();
			tierraMedia.construirAtracciones();
			tierraMedia.construirPromociones();
			tierraMedia.construirSugerencias();
			tierraMedia.construirUsuarios();
		}
		return tierraMedia;
	}

	private void construirUsuarios() {
		UsuarioDAO usuarioDAO = FabricaDAO.getUsuarioDAO();
		usuarios = usuarioDAO.cargarUsuarios();
	}

	private void construirAtracciones() {
		AtraccionDAO atraccionDAO = FabricaDAO.getAtraccionDAO();
		atracciones = atraccionDAO.cargarAtracciones();
	}

	private void construirPromociones() {
		if (!atracciones.isEmpty()) {
			PromocionDAO promocionDAO = FabricaDAO.getPromocionDAO();
			promociones = promocionDAO.cargarPromociones(this.atracciones);
		}
	}

	private void construirSugerencias() {
		if (!atracciones.isEmpty()) {
			sugerencias.addAll(atracciones.values());
			sugerencias.addAll(promociones.values());
		}
	}

	public void ordenarSugerencias(String preferenciaDeUsuario) {
		sugerencias.sort(new OrdenarSugerencias(preferenciaDeUsuario));
	}

	public ArrayList<Usuario> getUsuarios() {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>(this.usuarios.values());

		return usuarios;
	}

	public HashMap<Integer, Atraccion> getAtracciones() {
		return atracciones;
	}

	public HashMap<Integer, Promocion> getPromociones() {
		return promociones;
	}

	public ArrayList<Sugerencia> getSugerencias() {
		return sugerencias;
	}
}
