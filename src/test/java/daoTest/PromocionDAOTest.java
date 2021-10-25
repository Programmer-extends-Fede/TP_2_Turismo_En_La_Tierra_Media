package daoTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.AtraccionDAO;
import dao.FabricaDAO;
import dao.PromocionDAO;
import jdbc.ProveedorDeConexion;
import modelo.Atraccion;
import modelo.Promocion;
import modelo.PromocionPorcentual;

public class PromocionDAOTest {

	@Before
	public void setUp() throws SQLException {
		Connection conexion = ProveedorDeConexion.getConexion("src/test/resources/TierraMediaTest.db");
		conexion.setAutoCommit(false);
	}

	@After
	public void tearDown() throws SQLException {
		Connection conexion = ProveedorDeConexion.getConexion("src/test/resources/TierraMediaTest.db");
		conexion.rollback();
		conexion.setAutoCommit(true);
	}

	@Test
	public void cargarPromocionesTest() {
		Atraccion atraccion1 = new Atraccion(1, "Mordor", 25, 3, 3, "aventuras");
		Atraccion atraccion2 = new Atraccion(2, "Bosque Negro", 3, 4, 11, "aventuras");
		ArrayList<Atraccion> atraccionesEsperadas = new ArrayList<Atraccion>();
		atraccionesEsperadas.add(atraccion1);
		atraccionesEsperadas.add(atraccion2);
		Promocion promoEsperada = new PromocionPorcentual(1, "Pack Aventuras", atraccionesEsperadas, 20);
		HashMap<Integer, Promocion> promocionesEsperadas = new HashMap<Integer, Promocion>();

		AtraccionDAO atraccionDAO = FabricaDAO.getAtraccionDAO();
		HashMap<Integer, Atraccion> atraccionesObtenidas = atraccionDAO.cargarAtracciones();
		PromocionDAO promocionDAO = FabricaDAO.getPromocionDAO();

		HashMap<Integer, Promocion> promocionesObtenidas = promocionDAO.cargarPromociones(atraccionesObtenidas);
		promocionesEsperadas.put(promoEsperada.getId(), promoEsperada);

		assertNotNull(promocionesObtenidas);
		assertFalse(promocionesObtenidas.isEmpty());
		assertEquals(promocionesEsperadas, promocionesObtenidas);
	}

}
