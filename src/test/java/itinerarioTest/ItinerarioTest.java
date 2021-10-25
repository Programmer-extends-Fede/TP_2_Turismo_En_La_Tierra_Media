package itinerarioTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import modelo.Atraccion;
import modelo.Itinerario;
import modelo.PromocionPorcentual;
import modelo.Sugerencia;

public class ItinerarioTest {

	Itinerario itinerario;
	Atraccion atraccion;
	Atraccion atraccion2;
	Atraccion atraccion3;
	ArrayList<Atraccion> misAtracciones = new ArrayList<Atraccion>();
	Sugerencia promocion;

	@Before
	public void setup() {
		atraccion = new Atraccion(1, "Moria", 10, 5.5, 3, "Paisaje");
		atraccion2 = new Atraccion(2, "La Cueva", 15, 3.5, 10, "Paisaje");
		atraccion3 = new Atraccion(3, "Cafe Vasco", 10, 3, 15, "Aventuras");
		misAtracciones.add(atraccion);
		misAtracciones.add(atraccion2);
		promocion = new PromocionPorcentual(4, "Promo Epica", misAtracciones, 15);
		itinerario = new Itinerario(1);
	}

	@Test
	public void crearItinerarioTest() {
		assertNotNull(itinerario);
	}

	@Test
	public void crearItinerarioConComprasTest() {
		ArrayList<Sugerencia> compras = new ArrayList<Sugerencia>(misAtracciones);
		Itinerario miItinerario = new Itinerario(1, compras, 25, 9);

		assertNotNull(miItinerario);
		assertFalse(miItinerario.getSugerenciasYaCompradas().isEmpty());
		assertEquals(25, miItinerario.getCostoDelItinerario());
		assertEquals(9, miItinerario.getDuracionDelItinerario(),0);
	}

	@Test
	public void agregarCompraTest() {
		itinerario.agregarLaCompraDe(atraccion3);
		itinerario.agregarLaCompraDe(promocion);

		int costoObtenido = itinerario.getCostoDelItinerario();
		int costoEsperado = 31;
		double duracionObtenida = itinerario.getDuracionDelItinerario();
		double duracionEsperada = 12;

		assertFalse(itinerario.getSugerenciasDiarias().isEmpty());
		assertEquals(costoEsperado, costoObtenido);
		assertEquals(duracionEsperada, duracionObtenida, 0);
	}

}
