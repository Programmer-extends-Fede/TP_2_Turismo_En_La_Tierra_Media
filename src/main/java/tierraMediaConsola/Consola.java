package tierraMediaConsola;

import java.util.ArrayList;
import java.util.Scanner;

import dao.AtraccionDAO;
import dao.FabricaDAO;
import dao.ItinerarioDAO;
import dao.UsuarioDAO;
import modelo.Atraccion;
import modelo.Promocion;
import modelo.Sugerencia;
import modelo.Usuario;

public class Consola {

	private static ArrayList<Atraccion> atraccionesTemp = new ArrayList<Atraccion>();
	private static ArrayList<Atraccion> atraccionesYaCompradasTemp = new ArrayList<Atraccion>();
	private static Scanner entrada = new Scanner(System.in);
	private static boolean salir = false;
	private static final String ENTRADA_INCORRECTA = "<<<ENTRADA INCORRECTA>>>".indent(50);
	private static final String SEPARADOR_USUARIO = "_".repeat(80).indent(23);
	private static final String SUBRAYADO = "-".repeat(45);
	private static final String MENSAJE_INICIAL = "\nESTE ES EL SISTEMA DE COMPRAS DE TIERRAMEDIA\n".indent(47)
			+ "\nRECUERDE, PRESIONAR \"S\" PARA ACEPTAR LA COMPRA Y \"N\" PARA RECHAZARLA".indent(35)
			+ "\nESCRIBA \"SALIR\" PARA FINALIZAR LAS TRANSACCIONES CON EL USUARIO ACTUAL".indent(34)
			+ "\nPRESIONE ENTER PARA CONTINUAR".indent(53);
	private static final String MENSAJE_FINAL = SUBRAYADO.indent(43)
			+ "MUCHAS GRACIAS, YA NO QUEDAN USUARIOS POR VER".indent(43) + SUBRAYADO.indent(43);

	public static void iniciarInteraccion() {
		TierraMedia tierraMedia = TierraMedia.getTierraMedia();
		ArrayList<Usuario> usuarios = tierraMedia.getUsuarios();
		ArrayList<Sugerencia> sugerencias = tierraMedia.getSugerencias();
		System.out.println(MENSAJE_INICIAL);

		for (Usuario usuario : usuarios) {
			cargarAtraccionesCompradas(usuario.getItinerario().getSugerenciasYaCompradas());
			tierraMedia.ordenarSugerencias(usuario.getPreferencia());
			entrada.nextLine();

			if (atraccionesYaCompradasTemp.isEmpty())
				System.out.println("BIENVENIDO/A " + usuario + "\n");
			else {
				System.out.println("BIENVENIDO/A NUEVAMENTE " + usuario.getNombre().toUpperCase() + "\n");
				System.out.println("\nUsted ya ha realizado las siguientes compras:\n");
				for (Sugerencia sugerencia : usuario.getItinerario().getSugerenciasYaCompradas()) {
					System.out.println(sugerencia.getNombre().indent(10));
				}
				System.out.println("Su saldo actual es de " + usuario.getDineroDisponible()
						+ " monedas y su tiempo disponible " + usuario.getTiempoDisponible() + " Hs.\n");

			}
			boolean seOferto = false;
			for (Sugerencia laSugerencia : sugerencias) {
				if (salir)	
					break;
				if (tieneCupo(laSugerencia) && puedeComprar(laSugerencia, usuario) && noSeCompro(laSugerencia))
					seOferto = ofertar(laSugerencia, usuario);
			}
			salir = false;
			if(!seOferto) {
				System.out.println("\nTu saldo o tiempo no es suficiente para realizar alguna compra.\n\n");
			}
			if (atraccionesTemp.isEmpty() && atraccionesYaCompradasTemp.isEmpty())
				System.out.println("\nNO REALIZASTE COMPRAS");
			else {
				if (!atraccionesTemp.isEmpty())
					guardarCambios(usuario);
				System.out.println("\nEste es el detalle de tu itinerario".indent(6) + SUBRAYADO + "\n");
				System.out.println(usuario.getItinerario());
				System.out.println(("Tu dinero restante: " + usuario.getDineroDisponible() + " monedas."
						+ " ".repeat(11) + "Tu tiempo restante: " + usuario.getTiempoDisponible() + " hs.").indent(30));
			}
			System.out.println(SEPARADOR_USUARIO);
			if (!usuarios.get(usuarios.size() - 1).equals(usuario))
				System.out.print("PRESIONA ENTER PARA MOSTRAR EL SIGUIENTE USUARIO".indent(40));

			atraccionesTemp.clear();
			atraccionesYaCompradasTemp.clear();
		}
		System.out.println(MENSAJE_FINAL);
		entrada.close();
	}

	private static boolean ofertar(Sugerencia laSugerencia, Usuario usuario) {
		System.out.println("Deseas comprar " + laSugerencia);
		String respuesta = "";
		do {
			respuesta = entrada.nextLine();

			if (respuesta.equalsIgnoreCase("n"))
				System.out.println();
			else if (respuesta.equalsIgnoreCase("salir")) {
				salir = true;
				break;
			} else if (respuesta.equalsIgnoreCase("s")) {
				laSugerencia.restarCupo();
				usuario.comprar(laSugerencia);
				agregarAtraccionComprada(laSugerencia);
				System.out.println(("[Monedas restantes: " + usuario.getDineroDisponible() + "]       [Horas restante: "
						+ usuario.getTiempoDisponible() + "]").indent(35));
			} else
				System.out.println(ENTRADA_INCORRECTA);
		} while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));
		return true;
	}

	public static boolean puedeComprar(Sugerencia laSugerencia, Usuario usuario) {
		return usuario.getDineroDisponible() >= laSugerencia.getPrecio()
				&& usuario.getTiempoDisponible() >= laSugerencia.getDuracion();
	}

	public static boolean tieneCupo(Sugerencia laSugerencia) {
		return laSugerencia.getCupo() > 0;
	}

	public static boolean noSeCompro(Sugerencia laSugerencia) {
		return laSugerencia.noEstaIncluidaEn(atraccionesTemp)
				&& laSugerencia.noEstaIncluidaEn(atraccionesYaCompradasTemp);
	}

	public static void agregarAtraccionComprada(Sugerencia sugerencia) {
		if (sugerencia.esPromocion()) {
			Promocion miPromo = (Promocion) sugerencia;
			atraccionesTemp.addAll(miPromo.getAtracciones());
		} else {
			atraccionesTemp.add((Atraccion) sugerencia);
		}
	}

	public static void cargarAtraccionesCompradas(ArrayList<Sugerencia> sugerenciasCompradas) {
		for (Sugerencia sugerencia : sugerenciasCompradas) {
			if (sugerencia.esPromocion()) {
				Promocion miPromo = (Promocion) sugerencia;
				atraccionesYaCompradasTemp.addAll(miPromo.getAtracciones());
			} else {
				atraccionesYaCompradasTemp.add((Atraccion) sugerencia);
			}
		}
	}

	private static void guardarCambios(Usuario usuario) {
		AtraccionDAO atraccioDAO = FabricaDAO.getAtraccionDAO();
		ItinerarioDAO itinerarioDAO = FabricaDAO.getItinerarioDAO();
		UsuarioDAO usuarioDAO = FabricaDAO.getUsuarioDAO();

		usuarioDAO.actualizar(usuario);
		atraccioDAO.actualizarCupo(atraccionesTemp);
		itinerarioDAO.actualizar(usuario.getItinerario());
	}
}