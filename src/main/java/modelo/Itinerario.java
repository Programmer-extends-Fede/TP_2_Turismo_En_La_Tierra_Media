package modelo;

import java.util.ArrayList;
import java.util.Objects;

public class Itinerario {

	private int fkUsuario;
	private ArrayList<Sugerencia> sugerenciasCompradas = new ArrayList<Sugerencia>();
	private ArrayList<Sugerencia> sugerenciasCargadas = new ArrayList<Sugerencia>();
	private ArrayList<Atraccion> atraccionesCompradas = new ArrayList<Atraccion>();
	private int costoDelItinerario = 0;
	private double duracionDelItinerario = 0;

	public Itinerario(int id) {
		this.fkUsuario = id;
	}

	public Itinerario(int idUsuario, ArrayList<Sugerencia> sugerenciasCompAnteriormente, int costoDelItinerario,
			double duracionDelItinerario) {
		this.fkUsuario = idUsuario;
		this.sugerenciasCargadas = sugerenciasCompAnteriormente;
		this.costoDelItinerario = costoDelItinerario;
		this.duracionDelItinerario = duracionDelItinerario;

		for (Sugerencia sugerencia : sugerenciasCompAnteriormente) {
			agregarAtraccionComprada(sugerencia);
		}
	}

	public ArrayList<Sugerencia> getSugerenciasCompradas() {
		return sugerenciasCompradas;
	}

	public ArrayList<Sugerencia> getSugerenciasCargadas() {
		return sugerenciasCargadas;
	}

	public ArrayList<Atraccion> getAtraccionesCompradas() {
		return this.atraccionesCompradas;
	}

	public int getFkUsuario() {
		return this.fkUsuario;
	}

	public int getCostoDelItinerario() {
		return this.costoDelItinerario;
	}

	public double getDuracionDelItinerario() {
		return this.duracionDelItinerario;
	}

	public void agregarLaCompraDe(Sugerencia unaSugerencia) {
		this.sugerenciasCompradas.add(unaSugerencia);
		this.costoDelItinerario += unaSugerencia.getPrecio();
		this.duracionDelItinerario += unaSugerencia.getDuracion();

		agregarAtraccionComprada(unaSugerencia);
	}

	public boolean noTieneA(Sugerencia unaSugerencia) {
		return unaSugerencia.noEstaIncluidaEn(this.atraccionesCompradas);
	}

	private void agregarAtraccionComprada(Sugerencia sugerencia) {
		if (sugerencia.esPromocion()) {
			Promocion miPromo = (Promocion) sugerencia;
			atraccionesCompradas.addAll(miPromo.getAtracciones());
		} else {
			atraccionesCompradas.add((Atraccion) sugerencia);
		}
	}

	@Override
	public String toString() {

		ArrayList<Sugerencia> todasLasSugerencias = new ArrayList<Sugerencia>(this.sugerenciasCargadas);
		todasLasSugerencias.addAll(sugerenciasCompradas);
		String sugerenciasDiariasLimpio = todasLasSugerencias.toString().replace("[", " ").replace(", ", "\n ")
				.replace("]", "\n");
		return sugerenciasDiariasLimpio + ("\nCosto total: " + this.costoDelItinerario
				+ " monedas.                 Duracion total: " + this.duracionDelItinerario + " hs.").indent(30);
	}

	@Override
	public int hashCode() {
		return Objects.hash(costoDelItinerario, duracionDelItinerario, fkUsuario, sugerenciasCompradas,
				sugerenciasCargadas);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Itinerario other = (Itinerario) obj;
		return costoDelItinerario == other.costoDelItinerario
				&& Double.doubleToLongBits(duracionDelItinerario) == Double
						.doubleToLongBits(other.duracionDelItinerario)
				&& fkUsuario == other.fkUsuario && Objects.equals(sugerenciasCompradas, other.sugerenciasCompradas)
				&& Objects.equals(sugerenciasCargadas, other.sugerenciasCargadas);
	}
}
