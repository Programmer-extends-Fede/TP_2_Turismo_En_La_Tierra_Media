package ordenarSugerenciasTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import modelo.Atraccion;
import modelo.Promocion;
import modelo.PromocionAbsoluta;
import modelo.PromocionPorcentual;
import modelo.Sugerencia;
import ordenarSugerencias.OrdenarSugerencias;

public class OrdenarSugerenciasTest {

	@Test
	public void crearOrdenarTest() {
		OrdenarSugerencias miOrden = new OrdenarSugerencias(null);

		assertNotNull(miOrden);
	}

	@Test
	public void ordenCorrectoTest() {
		OrdenarSugerencias miOrden  = new OrdenarSugerencias("Aventuras");
		Atraccion atraccion1 = new Atraccion(1,"Erebor", 12, 6, 1, "Paisaje");
		Atraccion atraccion2 = new Atraccion(2,"Abismo de Helm", 12, 2, 15, "Degustacion");
		Atraccion atraccion3 = new Atraccion(3,"Minas Tirith", 10, 2.5, 25, "Aventuras");
		Atraccion atraccion4 = new Atraccion(4,"Lothloriem", 35, 1, 30, "Degustacion");
		Atraccion atraccion5 = new Atraccion(5,"Mordor", 10, 3, 4, "Aventuras");
		Atraccion[] misAtracciones = {atraccion1, atraccion2, atraccion3, atraccion4, atraccion5};
		
		ArrayList<Atraccion> atraccionesAventura = new ArrayList<Atraccion>();
		atraccionesAventura.add(atraccion3);
		atraccionesAventura.add(atraccion5);
		ArrayList<Atraccion> atraccionesDegustacion = new ArrayList<Atraccion>();
		atraccionesDegustacion.add(atraccion2);
		atraccionesDegustacion.add(atraccion4);
		Promocion promo1 = new PromocionAbsoluta(1,"Pack Degustacion", atraccionesDegustacion, 36);
		Promocion promo2 = new PromocionPorcentual(2,"Pack Aventuras", atraccionesAventura, 20);
		
		ArrayList<Sugerencia> ordenObtenido = new ArrayList<Sugerencia>();
		ordenObtenido.addAll(Arrays.asList(misAtracciones));
		ordenObtenido.add(promo1);
		ordenObtenido.add(promo2);
		ordenObtenido.sort(miOrden);
		ArrayList<Sugerencia> ordenEsperado = new ArrayList<Sugerencia>();
		ordenEsperado.add(promo2);
		ordenEsperado.add(promo1);
		ordenEsperado.add(atraccion5);
		ordenEsperado.add(atraccion3);
		ordenEsperado.add(atraccion4);
		ordenEsperado.add(atraccion1);
		ordenEsperado.add(atraccion2);
		
		assertEquals(ordenEsperado, ordenObtenido);
	}
}
