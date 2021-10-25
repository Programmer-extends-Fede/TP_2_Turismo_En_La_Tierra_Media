package atraccionTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import modelo.Atraccion;

public class AtraccionTest {
	
	Atraccion atraccion;
	
	@Before
	public void setup() {
		atraccion = new Atraccion(1,"VueltaAlMundo", 15, 0.5, 2, "Aventuras");
	}
	
	@Test
	public void crearAtraccionTest() {
		assertNotNull(atraccion);
	}
	
	@Test
	public void restarCupoTest() {
		atraccion.restarCupo();
		
		int cupoObtenido = atraccion.getCupo();
		int cupoEsperado = 1;
		
		assertEquals(cupoEsperado, cupoObtenido);
	}
	
	@Test
	public void noEstaIncluidaTest() {
		ArrayList<Atraccion> atraccionIncluida = new ArrayList<Atraccion>();
		ArrayList<Atraccion> atraccionNoIncluida = new ArrayList<Atraccion>();
		atraccionIncluida.add(atraccion);
		atraccionNoIncluida.add(new Atraccion(1,"La Galaxia", 10, 2.5, 23, "Paisaje"));

		assertFalse(atraccion.noEstaIncluidaEn(atraccionIncluida));
		assertTrue(atraccion.noEstaIncluidaEn(atraccionNoIncluida));
	}
}
