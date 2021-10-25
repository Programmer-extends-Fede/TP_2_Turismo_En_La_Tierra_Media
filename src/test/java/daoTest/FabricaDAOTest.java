package daoTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
		assertNotNull(otroAtraccionDAO);
		assertEquals(atraccionDAO, otroAtraccionDAO);
		assertEquals(atraccionDAO.hashCode(), otroAtraccionDAO.hashCode());
	}
	
	@Test
	public void getPromocionDAOTest() {
		PromocionDAO promocionDAO = FabricaDAO.getPromocionDAO();
		PromocionDAO otroPromocionDAO = FabricaDAO.getPromocionDAO();
		
		assertNotNull(promocionDAO);
		assertNotNull(otroPromocionDAO);
		assertEquals(promocionDAO, otroPromocionDAO);
		assertEquals(promocionDAO.hashCode(), otroPromocionDAO.hashCode());
	}
	
	@Test
	public void getUsuarioDAOTest() {
		UsuarioDAO usuarioDAO = FabricaDAO.getUsuarioDAO();
		UsuarioDAO otroUsuarioDAO = FabricaDAO.getUsuarioDAO();
		
		assertNotNull(usuarioDAO);
		assertNotNull(otroUsuarioDAO);
		assertEquals(usuarioDAO, otroUsuarioDAO);
		assertEquals(usuarioDAO.hashCode(), otroUsuarioDAO.hashCode());
	}
	
	@Test
	public void getItinerarioDAOTest() {
		ItinerarioDAO itinerarioDAO = FabricaDAO.getItinerarioDAO();
		ItinerarioDAO otroItinerarioDAO = FabricaDAO.getItinerarioDAO();
		
		assertNotNull(itinerarioDAO);
		assertNotNull(otroItinerarioDAO);
		assertEquals(itinerarioDAO, otroItinerarioDAO);
		assertEquals(itinerarioDAO.hashCode(), otroItinerarioDAO.hashCode());
	}
}
