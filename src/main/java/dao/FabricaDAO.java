package dao;

public class FabricaDAO {

	private static AtraccionDAO atraccionDAO = new AtraccionDAO();
	private static PromocionDAO promocionDAO = new PromocionDAO();
	private static UsuarioDAO usuarioDAO = new UsuarioDAO();
	private static ItinerarioDAO itinerarioDAO = new ItinerarioDAO();

	public static AtraccionDAO getAtraccionDAO() {
		return atraccionDAO;
	}

	public static PromocionDAO getPromocionDAO() {
		return promocionDAO;
	}

	public static UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public static ItinerarioDAO getItinerarioDAO() {
		return itinerarioDAO;
	}
}
