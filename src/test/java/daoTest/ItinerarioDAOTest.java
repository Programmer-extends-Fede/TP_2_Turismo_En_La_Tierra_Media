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

import dao.FabricaDAO;
import dao.ItinerarioDAO;
import dao.UsuarioDAO;
import jdbc.ProveedorDeConexion;
import modelo.Atraccion;
import modelo.Itinerario;
import modelo.Sugerencia;
import modelo.Usuario;

public class ItinerarioDAOTest {
	private static Connection conexion;
	private static PreparedStatement declaracion;
	private static String sqlTipoAtrac = "INSERT INTO tipos_de_atraccion (tipo)"
			+ "VALUES ('aventuras'),"
			+ "('degustacion');";
	private static String sqlAtracciones = "INSERT INTO atracciones (id, nombre, precio, duracion, cupo, fk_tipo_atraccion)"
			+ "VALUES ('1', 'Mordor', '25', '3', '3', 'aventuras'),"
			+ "('3', 'La Bruja', '8', '3.5',	'10', 'degustacion');";
	private static String sqlUsuario = "INSERT INTO usuarios (id, nombre, dinero_disp, tiempo_disp, tipo_preferencia)"
			+ "VALUES ('1', 'Jose Olaechea', '32', '26.5', 'degustacion');";
	private static String sqlItinerario = "UPDATE itinerarios SET costo = 8, duracion = 3.5 WHERE id = 1;";
	private static String sqlCompra = "INSERT INTO compras_de_itinerarios (fk_itinerario, fk_atraccion) "
			+ "VALUES ('1', '3');";

	@BeforeClass
	public static void setUp() throws SQLException {
		conexion = ProveedorDeConexion.getConexion("src/test/resources/TierraMediaTest2.db");
		
		declaracion = conexion.prepareStatement(sqlTipoAtrac);
		declaracion.execute();
		declaracion = conexion.prepareStatement(sqlAtracciones);
		declaracion.execute();
		declaracion = conexion.prepareStatement(sqlUsuario);
		declaracion.execute();
		declaracion = conexion.prepareStatement(sqlItinerario);
		declaracion.execute();
		declaracion = conexion.prepareStatement(sqlCompra);
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
		
		declaracion = conexion.prepareStatement("DELETE FROM compras_de_itinerarios");
		declaracion.execute();
		declaracion = conexion.prepareStatement("DELETE FROM itinerarios");
		declaracion.execute();
		declaracion = conexion.prepareStatement("DELETE FROM usuarios");
		declaracion.execute();
		declaracion = conexion.prepareStatement("DELETE FROM atracciones");
		declaracion.execute();
		declaracion = conexion.prepareStatement("DELETE FROM tipos_de_atraccion");
		declaracion.execute();
		
		ProveedorDeConexion.cerrarConexion();
	}

	@Test
	public void cargarItinerariosTest() {
		ItinerarioDAO itinerarioDAO = FabricaDAO.getItinerarioDAO();

		Atraccion atraccion3 = new Atraccion(3, "La Bruja", 8, 3.5, 10, "degustacion");
		ArrayList<Sugerencia> sugerenciasEsperadas = new ArrayList<Sugerencia>();
		sugerenciasEsperadas.add(atraccion3);
		Itinerario itinerario = new Itinerario(1, sugerenciasEsperadas, atraccion3.getPrecio(),
				atraccion3.getDuracion());

		HashMap<Integer, Itinerario> itinerariosEsperados = new HashMap<Integer, Itinerario>();
		itinerariosEsperados.put(itinerario.getFkUsuario(), itinerario);

		HashMap<Integer, Itinerario> itinerariosObtenidos = itinerarioDAO.cargarItinerarios();

		assertEquals(itinerariosEsperados, itinerariosObtenidos);
	}

	@Test
	public void insertarComprasEnBBDDTest() {
		ItinerarioDAO itinerarioDAO = FabricaDAO.getItinerarioDAO();
		UsuarioDAO usuarioDAO = FabricaDAO.getUsuarioDAO();

		Atraccion atraccion1 = new Atraccion(1, "Mordor", 25, 3, 3, "aventuras");
		Atraccion atraccion3 = new Atraccion(3, "La Bruja", 8, 3.5, 10, "degustacion");
		
		ArrayList<Sugerencia> sugerenciasEsperadas = new ArrayList<Sugerencia>();
		sugerenciasEsperadas.add(atraccion1);
		sugerenciasEsperadas.add(atraccion3);
		Itinerario itinerarioEsperado = new Itinerario(1, sugerenciasEsperadas, 33, 6.5);

		HashMap<Integer, Usuario> usuariosObtenidos = usuarioDAO.cargarUsuarios();
		HashMap<Integer, Itinerario> itinerariosObtenidos = itinerarioDAO.cargarItinerarios();
		Itinerario itinerarioObtenido = itinerariosObtenidos.get(1);
		
		assertNotEquals(itinerarioEsperado, itinerarioObtenido);

		Usuario usuarioObtenido = usuariosObtenidos.get(1);
		usuarioObtenido.setItinerario(itinerarioObtenido);
		usuarioObtenido.comprar(atraccion1);
		
		usuarioDAO.actualizar(usuarioObtenido);
		itinerarioDAO.actualizar(itinerarioObtenido);
		itinerariosObtenidos = itinerarioDAO.cargarItinerarios();
		itinerarioObtenido = itinerariosObtenidos.get(1);

		assertEquals(itinerarioEsperado, itinerarioObtenido);
	}

}
