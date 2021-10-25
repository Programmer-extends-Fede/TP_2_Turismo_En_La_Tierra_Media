package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import jdbc.ProveedorDeConexion;
import modelo.Atraccion;
import modelo.Promocion;
import modelo.PromocionAPorB;
import modelo.PromocionAbsoluta;
import modelo.PromocionPorcentual;

public class PromocionDAO {

	public HashMap<Integer, Promocion> cargarPromociones(HashMap<Integer, Atraccion> atracciones) {
		String sqlDatosPromocion = "SELECT * FROM promociones";
		String sqlAtraccEnPromocion = "SELECT a.id AS \"id atraccion\" FROM promociones AS \"p\", atracciones_en_promocion AS \"ap\", atracciones AS \"a\" WHERE p.id = ? AND p.id = ap.fk_promocion AND ap.fk_atraccion = a.id";
		HashMap<Integer, Promocion> promociones = new HashMap<Integer, Promocion>();

		try {
			Connection conexion = ProveedorDeConexion.getConexion();
			PreparedStatement declarDatosProm = conexion.prepareStatement(sqlDatosPromocion);
			PreparedStatement declarAtraccEnProm = conexion.prepareStatement(sqlAtraccEnPromocion);
			ResultSet resultadosPromociones = declarDatosProm.executeQuery();

			while (resultadosPromociones.next()) {
				declarAtraccEnProm.setInt(1, resultadosPromociones.getInt("id"));
				ResultSet resultadosIdAtracc = declarAtraccEnProm.executeQuery();
				promociones.put(resultadosPromociones.getInt("id"),
						crearPromocion(atracciones, resultadosPromociones, resultadosIdAtracc));
			}
		} catch (Exception e) {
			throw new DatosPerdidosError(e);
		}
		return promociones;
	}

	private Promocion crearPromocion(HashMap<Integer, Atraccion> atracciones, ResultSet resultadosProm,
			ResultSet resultadosIdAtracc) throws Exception {
		String tipoPromocion = resultadosProm.getString("tipo_promocion");
		int id = resultadosProm.getInt("id");
		String nombre = resultadosProm.getString("nombre");
		int descuento = resultadosProm.getInt("descuento");
		ArrayList<Atraccion> atraccionesDeProm = new ArrayList<Atraccion>();

		while (resultadosIdAtracc.next()) {
			int id_atraccion = resultadosIdAtracc.getInt("id atraccion");
			atraccionesDeProm.add(atracciones.get(id_atraccion));
		}

		if (tipoPromocion.equalsIgnoreCase("Porcentual"))
			return new PromocionPorcentual(id, nombre, atraccionesDeProm, descuento);
		else if (tipoPromocion.equalsIgnoreCase("Absoluta"))
			return new PromocionAbsoluta(id, nombre, atraccionesDeProm, descuento);
		else
			return new PromocionAPorB(id, nombre, atraccionesDeProm, descuento);
	}
}
