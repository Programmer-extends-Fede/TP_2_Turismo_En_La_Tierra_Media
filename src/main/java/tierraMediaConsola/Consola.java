package tierraMediaConsola;

import java.util.ArrayList;
import java.util.Scanner;

import dao.AtraccionDAO;
import dao.FabricaDAO;
import dao.ItinerarioDAO;
import dao.UsuarioDAO;
import modelo.Itinerario;
import modelo.Sugerencia;
import modelo.Usuario;

public class Consola {

	private Scanner entrada = new Scanner(System.in);
	private boolean salir = false;
	private final String ENTRADA_INCORRECTA = "<<<ENTRADA INCORRECTA>>>".indent(50);
	private final String SEPARADOR_USUARIO = "_".repeat(80).indent(23);
	private final String SUBRAYADO = "-".repeat(45);
	private final String MENSAJE_INICIAL = "\nESTE ES EL SISTEMA DE COMPRAS DE TIERRAMEDIA\n".indent(47)
			+ "\nRECUERDE, PRESIONAR \"S\" PARA ACEPTAR LA COMPRA Y \"N\" PARA RECHAZARLA".indent(35)
			+ "\nESCRIBA \"SALIR\" PARA FINALIZAR LAS TRANSACCIONES CON EL USUARIO ACTUAL".indent(34)
			+ "\nPRESIONE ENTER PARA CONTINUAR".indent(53);
	private final String MENSAJE_FINAL = SUBRAYADO.indent(43)
			+ "MUCHAS GRACIAS, YA NO QUEDAN USUARIOS POR VER".indent(43) + SUBRAYADO.indent(43);

	public void iniciarInteraccion() {
		TierraMedia tierraMedia = TierraMedia.getInstancia();
		ArrayList<Usuario> usuarios = tierraMedia.getUsuarios();
		ArrayList<Sugerencia> sugerencias = tierraMedia.getSugerencias();
		System.out.println(MENSAJE_INICIAL);

		for (Usuario usuario : usuarios) {
			tierraMedia.ordenarSugerencias(usuario.getPreferencia());
			entrada.nextLine();
			Itinerario itinDeUsuario = usuario.getItinerario();

			if (itinDeUsuario.getSugerenciasCargadas().isEmpty())
				System.out.println("BIENVENIDO/A " + usuario + "\n");
			else {
				System.out.println("BIENVENIDO/A NUEVAMENTE " + usuario.getNombre().toUpperCase() + "\n");
				System.out.println("\nUsted ya ha realizado las siguientes compras:\n");
				for (Sugerencia sugerencia : itinDeUsuario.getSugerenciasCargadas()) {
					System.out.println(sugerencia.getNombre().indent(10));
				}
				System.out.println("Su saldo actual es de " + usuario.getDineroDisponible()
						+ " monedas y su tiempo disponible " + usuario.getTiempoDisponible() + " Hs.\n");

			}
			boolean seOferto = false;
			for (Sugerencia laSugerencia : sugerencias) {
				if (salir)
					break;
				if (laSugerencia.tieneCupo() && usuario.puedeComprarA(laSugerencia))
					seOferto = ofertar(laSugerencia, usuario);
			}
			salir = false;
			if (!seOferto) {
				System.out.println("\nTu saldo o tiempo no es suficiente para realizar alguna compra.\n\n");
			}
			if (itinDeUsuario.getSugerenciasCompradas().isEmpty() && itinDeUsuario.getSugerenciasCargadas().isEmpty())
				System.out.println("\nNO REALIZASTE COMPRAS");
			else {
				if (!itinDeUsuario.getSugerenciasCompradas().isEmpty())
					guardarCambios(usuario);
				System.out.println("\nEste es el detalle de tu itinerario".indent(6) + SUBRAYADO + "\n");
				System.out.println(itinDeUsuario);
				System.out.println(("Tu dinero restante: " + usuario.getDineroDisponible() + " monedas."
						+ " ".repeat(11) + "Tu tiempo restante: " + usuario.getTiempoDisponible() + " hs.").indent(30));
			}
			System.out.println(SEPARADOR_USUARIO);
			if (!usuarios.get(usuarios.size() - 1).equals(usuario))
				System.out.print("PRESIONA ENTER PARA MOSTRAR EL SIGUIENTE USUARIO".indent(40));
		}
		System.out.println(MENSAJE_FINAL);
		entrada.close();
	}

	private boolean ofertar(Sugerencia laSugerencia, Usuario usuario) {
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
				System.out.println(("[Monedas restantes: " + usuario.getDineroDisponible() + "]       [Horas restante: "
						+ usuario.getTiempoDisponible() + "]").indent(35));
			} else
				System.out.println(ENTRADA_INCORRECTA);
		} while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));
		return true;
	}

	private void guardarCambios(Usuario usuario) {
		AtraccionDAO atraccioDAO = FabricaDAO.getAtraccionDAO();
		ItinerarioDAO itinerarioDAO = FabricaDAO.getItinerarioDAO();
		UsuarioDAO usuarioDAO = FabricaDAO.getUsuarioDAO();

		usuarioDAO.actualizar(usuario);
		itinerarioDAO.actualizar(usuario.getItinerario());
		atraccioDAO.actualizarCupo(usuario.getItinerario().getAtraccionesCompradas());
	}
}