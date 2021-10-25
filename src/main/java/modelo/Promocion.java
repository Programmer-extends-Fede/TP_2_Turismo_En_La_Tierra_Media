package modelo;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Promocion implements Sugerencia {

	private int id;
	private String nombre;
	private ArrayList<Atraccion> atracciones;
	private String tipoDePromocion;

	public Promocion(int id, String nombre, ArrayList<Atraccion> atracciones) {
		this.nombre = nombre + ": incluye ";
		this.tipoDePromocion = atracciones.get(0).getTipo();
		this.atracciones = atracciones;
		this.id = id;

		for (Atraccion atraccion : atracciones) {
			this.nombre += "(" + atraccion.getNombre() + ")";
		}
	}

	public ArrayList<Atraccion> getAtracciones() {
		return this.atracciones;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public int getPrecio() {
		int precio = 0;
		for (Atraccion atraccion : atracciones) {
			precio += atraccion.getPrecio();
		}
		return precio;
	}

	@Override
	public double getDuracion() {
		double duracion = 0;
		for (Atraccion atraccion : atracciones) {
			duracion += atraccion.getDuracion();
		}
		return duracion;
	}

	@Override
	public int getCupo() {
		int cupo = atracciones.get(0).getCupo();
		for (Atraccion atraccion : atracciones) {
			cupo = atraccion.getCupo() < cupo ? atraccion.getCupo() : cupo;
		}
		return cupo;
	}

	@Override
	public String getTipo() {
		return tipoDePromocion;
	}

	@Override
	public boolean esPromocion() {
		return true;
	}

	@Override
	public void restarCupo() {
		for (Atraccion atraccion : atracciones) {
			atraccion.restarCupo();
		}
	}

	@Override
	public String toString() {
		return getNombre() + ". Tipo " + this.tipoDePromocion + ". Su costo es de " + getPrecio()
				+ " monedas. Su duracion de " + getDuracion() + " hs.\n";
	}

	@Override
	public boolean noEstaIncluidaEn(ArrayList<Atraccion> atraccionesCompradas) {
		boolean noEstaIncluida = true;
		for (Atraccion atraccion : atracciones) {
			if (atraccionesCompradas.contains(atraccion)) {
				noEstaIncluida = false;
				break;
			}
		}
		return noEstaIncluida;
	}

	@Override
	public int hashCode() {
		return Objects.hash(atracciones, id, nombre, tipoDePromocion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Promocion other = (Promocion) obj;
		return Objects.equals(atracciones, other.atracciones) && id == other.id && Objects.equals(nombre, other.nombre)
				&& Objects.equals(tipoDePromocion, other.tipoDePromocion);
	}
	
}