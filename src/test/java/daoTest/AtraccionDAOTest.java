package daoTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.AtraccionDAO;
import dao.FabricaDAO;
import jdbc.ProveedorDeConexion;
import modelo.Atraccion;

public class AtraccionDAOTest {
	private static Connection conexion;
	private static PreparedStatement declaracion;
	private static String sqlTipoAtrac = "INSERT INTO tipos_de_atraccion (tipo)"
			+ "VALUES ('aventuras'),"
			+ "('degustacion'),"
			+ "('paisaje');";
	private static String sqlAtracciones = "INSERT INTO atracciones (id, nombre, precio, duracion, cupo, fk_tipo_atraccion)"
			+ "VALUES ('1', 'Mordor', '25', '3', '3', 'aventuras'),"
			+ "('2', 'Bosque Negro', '3', '4', '11', 'aventuras'),"
			+ "('3', 'La Bruja', '8', '3.5',	'10', 'degustacion');";

	@BeforeClass
	public static void setUp() throws SQLException {
		conexion = ProveedorDeConexion.getConexion("src/test/resources/TierraMediaTest2.db");

		declaracion = conexion.prepareStatement(sqlTipoAtrac);
		declaracion.execute();
		declaracion = conexion.prepareStatement(sqlAtracciones);
		declaracion.execute();
		
		conexion.setAutoCommit(false);
	}

	@After
	public void tearDown() throws SQLException {
		conexion.rollback();
	}

	@AfterClass
	public static void tearDownClass() throws SQLException {
		conexion.setAutoCommit(true);
		
		declaracion = conexion.prepareStatement("DELETE FROM atracciones;");
		declaracion.execute();
		declaracion = conexion.prepareStatement("DELETE FROM tipos_de_atraccion;");
		declaracion.execute();
		
		ProveedorDeConexion.cerrarConexion();
	}

	@Test
	public void cargarAtraccionesTest() {
		AtraccionDAO atraccionDAO = FabricaDAO.getAtraccionDAO();
		Atraccion atraccion1 = new Atraccion(1, "Mordor", 25, 3, 3, "aventuras");
		Atraccion atraccion2 = new Atraccion(2, "Bosque Negro", 3, 4, 11, "aventuras");
		Atraccion atraccion3 = new Atraccion(3, "La Bruja", 8, 3.5, 10, "degustacion");

		HashMap<Integer, Atraccion> atraccionesEsperadas = new HashMap<Integer, Atraccion>();
		atraccionesEsperadas.put(atraccion1.getId(), atraccion1);
		atraccionesEsperadas.put(atraccion2.getId(), atraccion2);
		atraccionesEsperadas.put(atraccion3.getId(), atraccion3);
		HashMap<Integer, Atraccion> atraccionesObtenidas = atraccionDAO.cargarAtracciones();

		assertEquals(atraccionesEsperadas, atraccionesObtenidas);
	}

	@Test
	public void actualizarPorCupoTest2() {
		AtraccionDAO atraccionDAO = FabricaDAO.getAtraccionDAO();
		HashMap<Integer, Atraccion> atraccionesObtenidas = atraccionDAO.cargarAtracciones();
		ArrayList<Atraccion> atracciones = new ArrayList<Atraccion>(atraccionesObtenidas.values());

		int cupoInicial = atraccionesObtenidas.get(1).getCupo();
		atraccionesObtenidas.get(1).restarCupo();
		atraccionDAO.actualizarCupo(atracciones);
		atraccionesObtenidas = atraccionDAO.cargarAtracciones();
		int cupoModificado = atraccionesObtenidas.get(1).getCupo();

		assertNotEquals(cupoInicial, cupoModificado);
	}
}
