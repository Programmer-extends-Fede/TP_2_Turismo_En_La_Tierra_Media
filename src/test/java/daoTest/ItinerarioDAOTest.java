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
import dao.ItinerarioDAO;
import dao.PromocionDAO;
import dao.UsuarioDAO;
import jdbc.ProveedorDeConexion;
import modelo.Atraccion;
import modelo.Itinerario;
import modelo.Promocion;
import modelo.Sugerencia;
import modelo.Usuario;

public class ItinerarioDAOTest {

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
	public void cargarItinerariosTest() {
		AtraccionDAO atraccionDAO = FabricaDAO.getAtraccionDAO();
		PromocionDAO promocionDAO = FabricaDAO.getPromocionDAO();
		ItinerarioDAO itinerarioDAO = FabricaDAO.getItinerarioDAO();

		Atraccion atraccion3 = new Atraccion(3, "La Bruja", 8, 3.5, 10, "degustacion");
		ArrayList<Sugerencia> sugerenciasEsperadas = new ArrayList<Sugerencia>();
		sugerenciasEsperadas.add(atraccion3);
		Itinerario itinerario = new Itinerario(1, sugerenciasEsperadas, atraccion3.getPrecio(),
				atraccion3.getDuracion());

		HashMap<Integer, Itinerario> itinerariosEsperados = new HashMap<Integer, Itinerario>();
		itinerariosEsperados.put(itinerario.getFkUsuario(), itinerario);

		HashMap<Integer, Atraccion> atraccionesObtenidas = atraccionDAO.cargarAtracciones();
		HashMap<Integer, Promocion> promocionesObtenidas = promocionDAO.cargarPromociones(atraccionesObtenidas);
		HashMap<Integer, Itinerario> itinerariosObtenidos = itinerarioDAO.cargarItinerarios(promocionesObtenidas,
				atraccionesObtenidas);

		assertEquals(itinerariosEsperados, itinerariosObtenidos);
	}

	@Test
	public void insertarComprasEnBBDDTest() {
		AtraccionDAO atraccionDAO = FabricaDAO.getAtraccionDAO();
		PromocionDAO promocionDAO = FabricaDAO.getPromocionDAO();
		ItinerarioDAO itinerarioDAO = FabricaDAO.getItinerarioDAO();
		UsuarioDAO usuarioDAO = FabricaDAO.getUsuarioDAO();

		Atraccion atraccion1 = new Atraccion(1, "Mordor", 25, 3, 3, "aventuras");
		Atraccion atraccion3 = new Atraccion(3, "La Bruja", 8, 3.5, 10, "degustacion");
		
		ArrayList<Sugerencia> sugerenciasEsperadas = new ArrayList<Sugerencia>();
		sugerenciasEsperadas.add(atraccion1);
		sugerenciasEsperadas.add(atraccion3);
		Itinerario itinerarioEsperado = new Itinerario(1, sugerenciasEsperadas, 33, 6.5);

		HashMap<Integer, Usuario> usuariosObtenidos = usuarioDAO.cargarUsuarios();
		HashMap<Integer, Atraccion> atraccionesObtenidas = atraccionDAO.cargarAtracciones();
		HashMap<Integer, Promocion> promocionesObtenidas = promocionDAO.cargarPromociones(atraccionesObtenidas);
		HashMap<Integer, Itinerario> itinerariosObtenidos = itinerarioDAO.cargarItinerarios(promocionesObtenidas,
				atraccionesObtenidas);
		Itinerario itinerarioObtenido = itinerariosObtenidos.get(1);
		
		assertNotEquals(itinerarioEsperado, itinerarioObtenido);

		Usuario usuarioObtenido = usuariosObtenidos.get(1);
		usuarioObtenido.setItinerario(itinerarioObtenido);
		usuarioObtenido.comprar(atraccion1);
		
		usuarioDAO.actualizar(usuarioObtenido);
		itinerarioDAO.actualizar(itinerarioObtenido);
		itinerariosObtenidos = itinerarioDAO.cargarItinerarios(promocionesObtenidas, atraccionesObtenidas);
		itinerarioObtenido = itinerariosObtenidos.get(1);

		assertEquals(itinerarioEsperado, itinerarioObtenido);
	}

}
