package modelo;

import java.util.ArrayList;

public interface Sugerencia {

	public int getId();
	
	public String getNombre();

	public int getPrecio();

	public double getDuracion();

	public int getCupo();

	public String getTipo();

	public boolean esPromocion();

	public void restarCupo();

	public boolean noEstaIncluidaEn(ArrayList<Atraccion> atraccionesCompradas);

}
