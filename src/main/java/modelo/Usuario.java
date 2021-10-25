package modelo;

import java.util.Objects;

public class Usuario {

	private int id;
	private String nombre;
	private int dineroDisponible;
	private double tiempoDisponible;
	private String preferencia;
	private Itinerario itinerario;

	public Usuario(int id, String nombre, int dineroDisponible, double tiempoDisponible, String preferencia) {
		this.id = id;
		this.nombre = nombre;
		this.dineroDisponible = dineroDisponible;
		this.tiempoDisponible = tiempoDisponible;
		this.preferencia = preferencia;
		this.itinerario = new Itinerario(this.id);
	}

	public int getId() {
		return this.id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public int getDineroDisponible() {
		return this.dineroDisponible;
	}

	public double getTiempoDisponible() {
		return this.tiempoDisponible;
	}

	public String getPreferencia() {
		return this.preferencia;
	}

	public Itinerario getItinerario() {
		return this.itinerario;
	}

	public void setItinerario(Itinerario itinerario) {
		this.itinerario = itinerario;
	}

	public void comprar(Sugerencia unaSugerencia) {
		this.dineroDisponible -= unaSugerencia.getPrecio();
		this.tiempoDisponible -= unaSugerencia.getDuracion();
		this.itinerario.agregarLaCompraDe(unaSugerencia);
	}

	@Override
	public String toString() {
		return this.nombre.toUpperCase() + "\n\nSu saldo inicial es: " + this.dineroDisponible
				+ " monedas y su tiempo disponible: " + this.tiempoDisponible + " hs.";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dineroDisponible, id, itinerario, nombre, preferencia, tiempoDisponible);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return dineroDisponible == other.dineroDisponible && id == other.id
				&& Objects.equals(itinerario, other.itinerario) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(preferencia, other.preferencia)
				&& Double.doubleToLongBits(tiempoDisponible) == Double.doubleToLongBits(other.tiempoDisponible);
	}
}
