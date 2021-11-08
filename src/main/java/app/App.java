package app;

import java.sql.Date;
import java.time.LocalDate;

import tierraMediaConsola.Consola;

public class App {

	public static void main(String[] args) {
		Consola consola = new Consola();
		consola.iniciarInteraccion();
		
		System.out.println(Date.valueOf(LocalDate.now()).toString());
	}
}
