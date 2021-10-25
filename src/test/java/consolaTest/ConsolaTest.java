package consolaTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import modelo.Atraccion;
import modelo.Promocion;
import modelo.Sugerencia;
import modelo.Usuario;
import tierraMediaConsola.Consola;

public class ConsolaTest {

	Usuario usuario;
	Atraccion atraccion;
	Atraccion atraccion2;
	Atraccion atraccion3;
	ArrayList<Atraccion> atraccionesPromo = new ArrayList<Atraccion>();
	Promocion promo;

	@Before
	public void setup() {
		usuario = new Usuario(1, "ObtenidoTest", 15, 5, "Paisaje");
	}

	@Test
	public void usuarioPuedeComprarTest() {
		int dineroDisp = usuario.getDineroDisponible();
		double tiempoDisp = usuario.getTiempoDisponible();
		Atraccion atraccion = new Atraccion(1, "El Coliseo", dineroDisp, tiempoDisp, 1, "Paisaje");

		assertTrue(Consola.puedeComprar(atraccion, usuario));
	}

	@Test
	public void usuarioNoLeAlcanzaDineroTest() {
		int dineroDisp = usuario.getDineroDisponible();
		double tiempoDisp = usuario.getTiempoDisponible();
		Atraccion atraccion = new Atraccion(1, "El Coliseo", dineroDisp + 1, tiempoDisp, 1, "Paisaje");

		assertFalse(Consola.puedeComprar(atraccion, usuario));
	}

	@Test
	public void usuarioNoLeAlcanzaTiempoTest() {
		int dineroDisp = usuario.getDineroDisponible();
		double tiempoDisp = usuario.getTiempoDisponible();
		Atraccion atraccion = new Atraccion(1, "El Coliseo", dineroDisp, tiempoDisp + 1, 1, "Paisaje");

		assertFalse(Consola.puedeComprar(atraccion, usuario));
	}

	@Test
	public void mostrarSugerenciaSitieneCupoTest() {
		Atraccion atraccion = new Atraccion(1, "El Coliseo", 2, 2, 1, "Paisaje");

		assertTrue(Consola.tieneCupo(atraccion));
	}

	@Test
	public void noMostrarSugerenciaSiNoTieneCupoTest() {
		Atraccion atraccion = new Atraccion(1, "El Coliseo", 2, 2, 1, "Paisaje");
		atraccion.restarCupo();

		assertFalse(Consola.tieneCupo(atraccion));
	}

	@Test
	public void mostrarSiSugerenciaNoSeComproTest() {
		atraccion = new Atraccion(1, "El Coliseo", 10, 5, 1, "Paisaje");
		atraccion2 = new Atraccion(2, "La Moria", 12, 6, 3, "Degustacion");
		atraccion3 = new Atraccion(3, "La Comarca", 20, 2, 6, "Aventuras");
		Consola.agregarAtraccionComprada(atraccion2);
		Consola.agregarAtraccionComprada(atraccion3);

		assertTrue(Consola.noSeCompro(atraccion));
		assertFalse(Consola.noSeCompro(atraccion2));
		assertFalse(Consola.noSeCompro(atraccion3));
	}

	@Test
	public void mostrarSiSugerenciaSeComproAnteriormenteTest() {
		atraccion = new Atraccion(1, "El Coliseo", 10, 5, 1, "Paisaje");
		atraccion2 = new Atraccion(2, "La Moria", 12, 6, 3, "Degustacion");
		atraccion3 = new Atraccion(3, "La Comarca", 20, 2, 6, "Aventuras");

		ArrayList<Sugerencia> atraccionesYaCompradas = new ArrayList<Sugerencia>();
		atraccionesYaCompradas.add(atraccion2);
		atraccionesYaCompradas.add(atraccion3);
		Consola.cargarAtraccionesCompradas(atraccionesYaCompradas);

		assertTrue(Consola.noSeCompro(atraccion));
		assertFalse(Consola.noSeCompro(atraccion2));
		assertFalse(Consola.noSeCompro(atraccion3));
	}
}
