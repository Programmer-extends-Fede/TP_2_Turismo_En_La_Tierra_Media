package daoTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.FabricaDAO;
import dao.PromocionDAO;
import jdbc.ProveedorDeConexion;
import modelo.Atraccion;
import modelo.Promocion;
import modelo.PromocionPorcentual;

public class PromocionDAOTest {
	private static Connection conexion;
	private static PreparedStatement declaracion;
	private static String sqlTipoAtrac = "INSERT INTO tipos_de_atraccion (tipo)"
			+ "VALUES ('aventuras');";
	private static String sqlTipoProm = "INSERT INTO tipos_de_promocion(tipo)"
			+ "VALUES ('porcentual');";
	private static String sqlAtracciones = "INSERT INTO atracciones (id, nombre, precio, duracion, cupo, fk_tipo_atraccion)"
			+ "VALUES ('1', 'Mordor', '25', '3', '3', 'aventuras'),"
			+ "('2', 'Bosque Negro', '3', '4', '11', 'aventuras');";
	private static String sqlPromociones = "INSERT INTO promociones (id, tipo_promocion, nombre, descuento)"
			+ "VALUES ('1', 'porcentual', 'Pack Aventuras', '20');";
	private static String sqlAtracEnProm = "INSERT INTO atracciones_en_promocion (fk_promocion, fk_atraccion)"
			+ "VALUES ('1', '1'),"
			+ "('1', '2');";

	@BeforeClass
	public static void setUp() throws SQLException {
		conexion = ProveedorDeConexion.getConexion("src/test/resources/TierraMediaTest.db");
		
		/*declaracion = conexion.prepareStatement(sqlTipoAtrac);
		declaracion.execute();
		declaracion = conexion.prepareStatement(sqlTipoProm);
		declaracion.execute();
		declaracion = conexion.prepareStatement(sqlAtracciones);
		declaracion.execute();
		declaracion = conexion.prepareStatement(sqlPromociones);
		declaracion.execute();
		declaracion = conexion.prepareStatement(sqlAtracEnProm);
		declaracion.execute();*/
		
		conexion.setAutoCommit(false);
	}

	@After
	public void tearDown() throws SQLException {
		conexion.rollback();
	}

	@AfterClass
	public static void tearDownClass() throws SQLException {
		conexion.setAutoCommit(true);
		
		/*declaracion = conexion.prepareStatement("DELETE FROM promociones");
		declaracion.execute();
		declaracion = conexion.prepareStatement("DELETE FROM atracciones");
		declaracion.execute();
		declaracion = conexion.prepareStatement("DELETE FROM atracciones_en_promocion");
		declaracion.execute();
		declaracion = conexion.prepareStatement("DELETE FROM tipos_de_promocion");
		declaracion.execute();
		declaracion = conexion.prepareStatement("DELETE FROM tipos_de_atraccion");
		declaracion.execute();*/
		
		ProveedorDeConexion.cerrarConexion();
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

		PromocionDAO promocionDAO = FabricaDAO.getPromocionDAO();

		HashMap<Integer, Promocion> promocionesObtenidas = promocionDAO.cargarPromociones();
		promocionesEsperadas.put(promoEsperada.getId(), promoEsperada);

		assertNotNull(promocionesObtenidas);
		assertFalse(promocionesObtenidas.isEmpty());
		assertEquals(promocionesEsperadas, promocionesObtenidas);
	}

}
