package usuarioTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import modelo.Atraccion;
import modelo.Itinerario;
import modelo.PromocionPorcentual;
import modelo.Sugerencia;
import modelo.Usuario;

public class UsuarioTest {

	Usuario usuario;
	Atraccion atraccion;
	Atraccion atraccion2;
	Atraccion atraccion3;
	ArrayList<Atraccion> misAtracciones = new ArrayList<Atraccion>();
	PromocionPorcentual promocion;

	@Before
	public void setup() {
		usuario = new Usuario(1, "Emir", 50, 15, "Aventuras");
		atraccion = new Atraccion(2, "Moria", 10, 5.5, 3, "Paisaje");
		atraccion2 = new Atraccion(3, "La Cueva", 15, 3.5, 10, "Paisaje");
		atraccion3 = new Atraccion(4, "Cafe Vasco", 10, 3, 15, "Aventuras");
		misAtracciones.add(atraccion);
		misAtracciones.add(atraccion2);
		promocion = new PromocionPorcentual(1, "Promo Epica", misAtracciones, 15);
	}

	@Test
	public void crearUsuarioTest() {
		assertNotNull(usuario);
	}

	@Test
	public void comprarTest() {
		Atraccion miAtraccion = new Atraccion(1,"VueltaAlMundo", 15, 0.5, 2, "Aventuras");
		usuario.comprar(miAtraccion);

		int dineroDispObtenido = usuario.getDineroDisponible();
		int dineroDispEsperado = 35;
		double tiempoDispObtenido = usuario.getTiempoDisponible();
		double tiempoDispEsperado = 14.5;

		assertEquals(dineroDispEsperado, dineroDispObtenido);
		assertEquals(tiempoDispEsperado, tiempoDispObtenido, 0);
	}
	
	@Test
	public void setItinerarioTest() {
		ArrayList<Sugerencia> compras = new ArrayList<Sugerencia>(misAtracciones);
		Itinerario miItinerario = new Itinerario(1, compras, 10, 5);
		
		assertTrue(usuario.getItinerario().getSugerenciasYaCompradas().isEmpty());
		
		usuario.setItinerario(miItinerario);
		
		assertFalse(usuario.getItinerario().getSugerenciasYaCompradas().isEmpty());
		
	}
}
