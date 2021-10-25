package modelo;

import java.util.ArrayList;

public class PromocionAbsoluta extends Promocion {

	private int precioDePaquete;

	public PromocionAbsoluta(int id, String nombre, ArrayList<Atraccion> atracciones, int precioDePaquete) {
		super(id, nombre, atracciones);
		this.precioDePaquete = precioDePaquete;
	}

	@Override
	public int getPrecio() {
		return this.precioDePaquete;
	}
}