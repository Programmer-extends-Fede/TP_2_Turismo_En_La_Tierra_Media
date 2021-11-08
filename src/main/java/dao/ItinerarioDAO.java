package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import jdbc.ProveedorDeConexion;
import modelo.Atraccion;
import modelo.Itinerario;
import modelo.Promocion;
import modelo.Sugerencia;
import tierraMediaConsola.TierraMedia;

public class ItinerarioDAO {

	public HashMap<Integer, Itinerario> cargarItinerarios() {

		String sqlItinerarios = "SELECT id, costo, duracion FROM itinerarios";
		String sqlIdPromociones = "SELECT fk_promocion FROM compras_de_itinerarios WHERE fk_itinerario = ? AND fk_promocion IS NOT NULL";
		String sqlIdAtracciones = "SELECT fk_atraccion FROM compras_de_itinerarios WHERE fk_itinerario = ? AND fk_atraccion IS NOT NULL";
		HashMap<Integer, Itinerario> itinerarios = new HashMap<Integer, Itinerario>();

		try {
			Connection conexion = ProveedorDeConexion.getConexion();
			PreparedStatement declarItinerario = conexion.prepareStatement(sqlItinerarios);
			PreparedStatement declarIdPromociones = conexion.prepareStatement(sqlIdPromociones);
			PreparedStatement declarIdAtracciones = conexion.prepareStatement(sqlIdAtracciones);
			ResultSet resultItinerario = declarItinerario.executeQuery();

			while (resultItinerario.next()) {
				int idItinerario = resultItinerario.getInt("id");
				declarIdPromociones.setInt(1, idItinerario);
				declarIdAtracciones.setInt(1, idItinerario);

				ResultSet resultIdPromociones = declarIdPromociones.executeQuery();
				ResultSet resultIdAtracciones = declarIdAtracciones.executeQuery();

				itinerarios.put(idItinerario,
						crearItinerario(resultItinerario, resultIdPromociones, resultIdAtracciones));
			}
		} catch (Exception e) {
			throw new DatosPerdidosError(e);
		}
		return itinerarios;
	}

	public int actualizar(Itinerario itinerario) {
		String sql = "UPDATE itinerarios SET costo = ?, duracion = ? WHERE id = ?";
		int filasModificadas = 0;

		try {
			Connection conexion = ProveedorDeConexion.getConexion();
			PreparedStatement declaracion = conexion.prepareStatement(sql);

			declaracion.setInt(1, itinerario.getCostoDelItinerario());
			declaracion.setDouble(2, itinerario.getDuracionDelItinerario());
			declaracion.setInt(3, itinerario.getFkUsuario());

			insertarCompras(itinerario, conexion);
			filasModificadas = declaracion.executeUpdate();

		} catch (Exception e) {
			throw new DatosPerdidosError(e);
		}
		return filasModificadas;
	}

	private void insertarCompras(Itinerario itinerario, Connection conexion) throws Exception {
		String sqlPromoComprada = "INSERT INTO compras_de_itinerarios (fk_itinerario, fk_promocion, costo, fecha_de_compra) VALUES (?, ?, ?, ?)";
		String sqlAtracComprada = "INSERT INTO compras_de_itinerarios (fk_itinerario, fk_atraccion, costo, fecha_de_compra) VALUES (?, ?, ?, ?)";

		PreparedStatement declarPromoComprada = conexion.prepareStatement(sqlPromoComprada);
		PreparedStatement declarAtracComprada = conexion.prepareStatement(sqlAtracComprada);
		ArrayList<Sugerencia> sugerenciasCompradas = itinerario.getSugerenciasCompradas();
		
		for (Sugerencia sugerencia : sugerenciasCompradas) {
			if (sugerencia.esPromocion()) {
				declarPromoComprada.setInt(1, itinerario.getFkUsuario());
				declarPromoComprada.setInt(2, sugerencia.getId());
				declarPromoComprada.setInt(3, sugerencia.getPrecio());
				declarPromoComprada.setDate(4, Date.valueOf(LocalDate.now()));
				declarPromoComprada.executeUpdate();
			} else {
				declarAtracComprada.setInt(1, itinerario.getFkUsuario());
				declarAtracComprada.setInt(2, sugerencia.getId());
				declarAtracComprada.setInt(3, sugerencia.getPrecio());
				declarAtracComprada.setTime(4, Time.valueOf(LocalTime.now()));
				//declarAtracComprada.setString(4, Date.valueOf(LocalDate.now()).toString());
				declarAtracComprada.executeUpdate();
			}
		}
	}

	private Itinerario crearItinerario(ResultSet resultItinerario, ResultSet resultIdPromo, ResultSet resultIdAtrac)
			throws Exception {
		int fkUsuario = resultItinerario.getInt("id");
		int costo = resultItinerario.getInt("costo");
		double duracion = resultItinerario.getDouble("duracion");

		TierraMedia tierraMedia = TierraMedia.getInstancia();
		HashMap<Integer, Atraccion> atracciones = tierraMedia.getAtracciones();
		HashMap<Integer, Promocion> promociones = tierraMedia.getPromociones();

		ArrayList<Sugerencia> sugerenciasYaCompradas = new ArrayList<Sugerencia>();

		while (resultIdPromo.next()) {
			int idPromocion = resultIdPromo.getInt("fk_promocion");
			sugerenciasYaCompradas.add(promociones.get(idPromocion));
		}
		while (resultIdAtrac.next()) {
			int idAtraccion = resultIdAtrac.getInt("fk_atraccion");
			sugerenciasYaCompradas.add(atracciones.get(idAtraccion));
		}

		return new Itinerario(fkUsuario, sugerenciasYaCompradas, costo, duracion);
	}
}
