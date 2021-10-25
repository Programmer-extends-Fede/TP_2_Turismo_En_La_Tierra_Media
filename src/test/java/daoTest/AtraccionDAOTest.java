package daoTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.AtraccionDAO;
import dao.FabricaDAO;
import jdbc.ProveedorDeConexion;
import modelo.Atraccion;

public class AtraccionDAOTest {

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
	public void cargarAtraccionesTest() {
		AtraccionDAO atraccionDAO = FabricaDAO.getAtraccionDAO();
		Atraccion atraccion1 = new Atraccion(1, "Mordor", 25, 3, 3, "aventuras");
		Atraccion atraccion2 = new Atraccion(2, "Bosque Negro", 3, 4, 11, "aventuras");
		Atraccion atraccion3 = new Atraccion(3, "La Bruja", 8, 3.5, 10, "degustacion");
		
		HashMap <Integer, Atraccion>atraccionesEsperadas = new HashMap<Integer, Atraccion>();
		atraccionesEsperadas.put(atraccion1.getId(), atraccion1);
		atraccionesEsperadas.put(atraccion2.getId(), atraccion2);
		atraccionesEsperadas.put(atraccion3.getId(), atraccion3);
		HashMap <Integer, Atraccion>atraccionesObtenidas = atraccionDAO.cargarAtracciones();
		
		assertEquals(atraccionesEsperadas, atraccionesObtenidas);		
	}
	
	@Test
	public void actualizarPorCupoTest2() {
		AtraccionDAO atraccionDAO = FabricaDAO.getAtraccionDAO();
		HashMap <Integer, Atraccion>atraccionesObtenidas = atraccionDAO.cargarAtracciones();
		ArrayList<Atraccion> atracciones = new ArrayList<Atraccion>(atraccionesObtenidas.values());
		
		int cupoInicial = atraccionesObtenidas.get(1).getCupo();
		atraccionesObtenidas.get(1).restarCupo();
		atraccionDAO.actualizarCupo(atracciones);
		atraccionesObtenidas = atraccionDAO.cargarAtracciones();
		int cupoModificado = atraccionesObtenidas.get(1).getCupo();
		
		assertNotEquals(cupoInicial, cupoModificado);
	}
}
