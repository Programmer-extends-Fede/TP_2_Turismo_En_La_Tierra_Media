package modelo;

import java.util.ArrayList;
import java.util.Objects;

public class Itinerario {

	private int fkUsuario;
	private ArrayList<Sugerencia> sugerenciasDiarias = new ArrayList<Sugerencia>();
	private ArrayList<Sugerencia> sugerenciasYaCompradas = new ArrayList<Sugerencia>();
	private int costoDelItinerario = 0;
	private double duracionDelItinerario = 0;

	public Itinerario(int id) {
		this.fkUsuario = id;
	}

	public Itinerario(int idUsuario, ArrayList<Sugerencia> sugerenciasYaCompradas, int costoDelItinerario,
			double duracionDelItinerario) {
		this.fkUsuario = idUsuario;
		this.sugerenciasYaCompradas = sugerenciasYaCompradas;
		this.costoDelItinerario = costoDelItinerario;
		this.duracionDelItinerario = duracionDelItinerario;
	}

	public ArrayList<Sugerencia> getSugerenciasDiarias() {
		return sugerenciasDiarias;
	}

	public ArrayList<Sugerencia> getSugerenciasYaCompradas() {
		return sugerenciasYaCompradas;
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
		this.sugerenciasDiarias.add(unaSugerencia);
		this.costoDelItinerario += unaSugerencia.getPrecio();
		this.duracionDelItinerario += unaSugerencia.getDuracion();
	}

	@Override
	public String toString() {

		ArrayList<Sugerencia> todasLasSugerencias = new ArrayList<Sugerencia>(this.sugerenciasYaCompradas);
		todasLasSugerencias.addAll(sugerenciasDiarias);
		String sugerenciasDiariasLimpio = todasLasSugerencias.toString().replace("[", " ").replace(", ", "\n ")
				.replace("]", "\n");
		return sugerenciasDiariasLimpio + ("\nCosto total: " + this.costoDelItinerario
				+ " monedas.                 Duracion total: " + this.duracionDelItinerario + " hs.").indent(30);
	}

	@Override
	public int hashCode() {
		return Objects.hash(costoDelItinerario, duracionDelItinerario, fkUsuario, sugerenciasDiarias,
				sugerenciasYaCompradas);
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
				&& fkUsuario == other.fkUsuario && Objects.equals(sugerenciasDiarias, other.sugerenciasDiarias)
				&& Objects.equals(sugerenciasYaCompradas, other.sugerenciasYaCompradas);
	}
}
