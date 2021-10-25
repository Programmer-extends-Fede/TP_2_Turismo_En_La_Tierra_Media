package daoTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
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
