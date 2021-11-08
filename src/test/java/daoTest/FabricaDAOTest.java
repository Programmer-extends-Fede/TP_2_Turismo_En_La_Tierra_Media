package daoTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import dao.AtraccionDAO;
import dao.FabricaDAO;
import dao.ItinerarioDAO;
import dao.PromocionDAO;
import dao.UsuarioDAO;

public class FabricaDAOTest {

	@Test
	public void getAtraccionDAOTest() {
		AtraccionDAO atraccionDAO = FabricaDAO.getAtraccionDAO();
		AtraccionDAO otroAtraccionDAO = FabricaDAO.getAtraccionDAO();
		
		assertNotNull(atraccionDAO);
		assertSame(atraccionDAO, otroAtraccionDAO);
	}
	
	@Test
	public void getPromocionDAOTest() {
		PromocionDAO promocionDAO = FabricaDAO.getPromocionDAO();
		PromocionDAO otroPromocionDAO = FabricaDAO.getPromocionDAO();
		
		assertNotNull(promocionDAO);
		assertSame(promocionDAO, otroPromocionDAO);
	}
	
	@Test
	public void getUsuarioDAOTest() {
		UsuarioDAO usuarioDAO = FabricaDAO.getUsuarioDAO();
		UsuarioDAO otroUsuarioDAO = FabricaDAO.getUsuarioDAO();
		
		assertNotNull(usuarioDAO);
		assertSame(usuarioDAO, otroUsuarioDAO);
	}
	
	@Test
	public void getItinerarioDAOTest() {
		ItinerarioDAO itinerarioDAO = FabricaDAO.getItinerarioDAO();
		ItinerarioDAO otroItinerarioDAO = FabricaDAO.getItinerarioDAO();
		
		assertNotNull(itinerarioDAO);
		assertSame(itinerarioDAO, otroItinerarioDAO);
	}
}
