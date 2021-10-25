package modelo;

import java.util.ArrayList;
import java.util.Objects;

public class Atraccion implements Sugerencia, Comparable<Atraccion> {
	
	private int id;
	private String nombre;
	private int precio;
	private double duracion;
	private int cupo;
	private String tipo;
	
	public Atraccion(int id, String nombre, int precio, double duracion, int cupo, String tipo) {
		this.nombre = nombre;
		this.precio = precio;
		this.duracion = duracion;
		this.cupo = cupo;
		this.tipo = tipo;
		this.id = id;
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
		return this.precio;
	}

	@Override
	public double getDuracion() {
		return this.duracion;
	}

	@Override
	public int getCupo() {
		return this.cupo;
	}

	@Override
	public String getTipo() {
		return this.tipo;
	}

	@Override
	public boolean esPromocion() {
		return false;
	}

	@Override
	public void restarCupo() {
		this.cupo--;
	}

	@Override
	public String toString() {
		return this.nombre + ": de tipo " + this.tipo + ". Su precio es de " + this.precio
				+ " monedas. Su duracion de " + this.duracion + " hs.\n";
	}

	@Override
	public boolean noEstaIncluidaEn(ArrayList<Atraccion> atraccionesCompradas) {
		return !atraccionesCompradas.contains(this);
	}

	@Override
	public int compareTo(Atraccion o) {
		return -Integer.compare(this.precio, o.precio);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cupo, duracion, id, nombre, precio, tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Atraccion other = (Atraccion) obj;
		return cupo == other.cupo && Double.doubleToLongBits(duracion) == Double.doubleToLongBits(other.duracion)
				&& id == other.id && Objects.equals(nombre, other.nombre) && precio == other.precio
				&& Objects.equals(tipo, other.tipo);
	}

	
}
