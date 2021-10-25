package modelo;

import java.util.ArrayList;

public class PromocionPorcentual extends Promocion {

	private double porcentajeACobrar;

	public PromocionPorcentual(int id, String nombre, ArrayList<Atraccion> atracciones, int porcentajeDescuento) {
		super(id, nombre, atracciones);
		this.porcentajeACobrar = (100 - porcentajeDescuento)/100.0;
	}

	@Override
	public int getPrecio() {
		return (int) (super.getPrecio() * this.porcentajeACobrar);
	}
}