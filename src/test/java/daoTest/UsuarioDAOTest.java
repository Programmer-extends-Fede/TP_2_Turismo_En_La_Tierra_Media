package daoTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
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
import dao.ItinerarioDAO;
import dao.UsuarioDAO;
import jdbc.ProveedorDeConexion;
import modelo.Atraccion;
import modelo.Itinerario;
import modelo.Sugerencia;
import modelo.Usuario;

public class UsuarioDAOTest {
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
	public void cargarUsuariosTest() {
		UsuarioDAO usuarioDAO = FabricaDAO.getUsuarioDAO();
		
		Atraccion atraccion3 = new Atraccion(3, "La Bruja", 8, 3.5, 10, "degustacion");
		ArrayList<Sugerencia> sugerencias = new ArrayList<Sugerencia>();
		sugerencias.add(atraccion3);	
		
		Usuario usuarioEsperado = new Usuario(1, "Jose Olaechea", 32, 26.5, "degustacion");
	    Itinerario itinerarioEsperado = new Itinerario(usuarioEsperado.getId(), sugerencias, 8, 3.5);
		usuarioEsperado.setItinerario(itinerarioEsperado);
		HashMap<Integer, Usuario> usuariosEsperados = new HashMap<Integer, Usuario>();
		
		HashMap<Integer, Usuario>usuariosObtenidos = usuarioDAO.cargarUsuarios();
		usuariosEsperados.put(usuarioEsperado.getId(), usuarioEsperado);
		
		assertNotNull(usuariosObtenidos);
		assertFalse(usuariosObtenidos.isEmpty());
		assertEquals(usuariosEsperados, usuariosObtenidos);
	}
	
	@Test
	public void actualizarUsuarioTest() {
		UsuarioDAO usuarioDAO = FabricaDAO.getUsuarioDAO();
		ItinerarioDAO itinerarioDAO = FabricaDAO.getItinerarioDAO();
		
		Atraccion atraccion1 = new Atraccion(1, "Mordor", 25, 3, 3, "aventuras");
		Atraccion atraccion3 = new Atraccion(3, "La Bruja", 8, 3.5, 10, "degustacion");
		ArrayList<Sugerencia> sugerencias = new ArrayList<Sugerencia>();
		sugerencias.add(atraccion1);
		sugerencias.add(atraccion3);
				
		Usuario usuarioEsperado = new Usuario(1, "Jose Olaechea", 7, 23.5, "degustacion");
		Itinerario itinerario = new Itinerario(usuarioEsperado.getId(), sugerencias, 33, 6.5);
		usuarioEsperado.setItinerario(itinerario);
		
		HashMap<Integer, Usuario> usuariosEsperados = new HashMap<Integer, Usuario>();
		usuariosEsperados.put(usuarioEsperado.getId(), usuarioEsperado);
		HashMap<Integer, Usuario> usuariosObtenidos = usuarioDAO.cargarUsuarios();
		
		assertNotEquals(usuariosEsperados, usuariosObtenidos);
		
		Usuario usuarioObtenido = usuariosObtenidos.get(1);
		usuarioObtenido.comprar(atraccion1);
		usuarioDAO.actualizar(usuarioObtenido);
		itinerarioDAO.actualizar(usuarioObtenido.getItinerario());
		usuariosObtenidos = usuarioDAO.cargarUsuarios();
		
		assertEquals(usuariosEsperados, usuariosObtenidos);
	}
}
