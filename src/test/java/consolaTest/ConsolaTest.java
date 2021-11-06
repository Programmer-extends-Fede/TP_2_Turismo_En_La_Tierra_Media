package consolaTest;

import java.util.ArrayList;

import org.junit.Before;

import modelo.Atraccion;
import modelo.Promocion;
import modelo.Usuario;

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
}
