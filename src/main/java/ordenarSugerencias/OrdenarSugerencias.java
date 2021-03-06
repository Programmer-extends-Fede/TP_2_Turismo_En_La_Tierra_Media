package ordenarSugerencias;

import java.util.Comparator;

import modelo.Sugerencia;

public class OrdenarSugerencias implements Comparator<Sugerencia> {

	private String usuarioTipo;

	public OrdenarSugerencias(String usuarioTipo) {
		this.usuarioTipo = usuarioTipo;
	}

	@Override
	public int compare(Sugerencia o1, Sugerencia o2) {
		int resultado;
		String o1Tipo = o1.getTipo();
		String o2Tipo = o2.getTipo();

		resultado = -Boolean.compare(o1.esPromocion(), o2.esPromocion());
		if (resultado == 0)
			resultado = o1Tipo.equals(usuarioTipo) && !o2Tipo.equals(usuarioTipo) ? -1
					: !o1Tipo.equals(usuarioTipo) && o2Tipo.equals(usuarioTipo) ? 1 : 0;
		if (resultado == 0)
			resultado = -Integer.compare(o1.getPrecio(), o2.getPrecio());
		if (resultado == 0)
			resultado = -Double.compare(o1.getDuracion(), o2.getDuracion());
		return resultado;
	}
}
