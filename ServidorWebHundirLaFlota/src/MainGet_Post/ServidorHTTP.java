/**
 * 
 */
package MainGet_Post;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import Clases.Barco;
import Clases.Partida;
import Clases.Tablero;
import Contenedor.ContenedorDatos;
import PaginaWeb.Mensajes;

/**
 * 
 */
public class ServidorHTTP {
	private static ContenedorDatos contenedorDatos;

	public ServidorHTTP(ContenedorDatos contenedorDatos) {
		ServidorHTTP.contenedorDatos = contenedorDatos;
	}

	public static ContenedorDatos getContenedorDatos() {
		return contenedorDatos;
	}

	public void recibirPeticion(Socket conexion) {
		String peticion;
		String peticionAMostrar;
		StringBuilder sb = new StringBuilder();
		String longitudContenido = null;
		try {
			InputStreamReader flujoEntrada = new InputStreamReader(conexion.getInputStream());
			BufferedReader lector = new BufferedReader(flujoEntrada);
			PrintWriter escritor = new PrintWriter(conexion.getOutputStream(), true);

			peticion = lector.readLine();
			peticion = peticion.replaceAll(" ", "");

			try {
				if (peticion.substring(3, 15).equals("/favicon.ico") == false
						|| peticion.substring(3, 12).equals("/main.css") == false) {
					System.out.println(peticion);
					peticionAMostrar = peticion;

					while ((peticionAMostrar = lector.readLine()).equals("") == false) {
						System.out.println(peticionAMostrar);

						if (peticionAMostrar.startsWith("Content-Length")) {
							longitudContenido = peticionAMostrar.substring(16);

						}
					}
				}
			} catch (Exception e) {
				// Nada
			}

			System.out.println("");

			if (longitudContenido != null) {
				int caracter;
				int cantidadLeido = Integer.parseInt(longitudContenido);

				while ((caracter = lector.read()) != -1 && cantidadLeido > 1) {
					sb.append((char) caracter);
					cantidadLeido--;

				}
				sb.append((char) caracter);
				System.out.println(sb); // TODO borrar
			}

			comprobarPeticion(peticion, escritor, sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void comprobarPeticion(String peticion, PrintWriter escritor, String lineaPost) {
		String valorID = "0";
		String errorPeticion = peticion.substring(3, peticion.lastIndexOf("HTTP"));
		String separador = "\\?";

		if (errorPeticion.equals("/?") == false) {
			if (peticion.startsWith("GET")) {
				// Cortamos para ver la peticion
				peticion = peticion.substring(3, peticion.lastIndexOf("HTTP"));
				System.out.println("\t\t\t\t\tPeticion: " + peticion); // TODO borrar

				if (peticion.length() == 0 || peticion.equals("/") || peticion.equals("/index")) {
					mostrarIndex(peticion, escritor);

				} else if (peticion.equals("/ListaPartidasGet")) {
					mostrarPartidasTerminadasGet(peticion, escritor);

				} else if (peticion.split(separador)[0].equals("/Partida")) {
					// Cortamos para ver el valor de la ID
					valorID = peticion.substring(19);
					verPartidaTerminada(peticion, escritor, Integer.parseInt(valorID));

				} else if (peticion.equals("/ListaPartidasPost")) {
					mostrarPartidasTerminadasPost(lineaPost, escritor);

				}

			} else if (peticion.startsWith("POST")) {
				// Cortamos para ver la peticion
				peticion = peticion.substring(4, peticion.lastIndexOf("HTTP"));
				System.out.println("\t\t\t\t\tPeticion: " + peticion); // TODO limpiar cosas

				if (peticion.equals("/Partida")) {
					valorID = lineaPost.substring(10);
					verPartidaTerminada(peticion, escritor, Integer.parseInt(valorID));

				}

			}

		} else {
			System.out.println("Alguien ha buscado una página que no existe");
		}
	}

	/**
	 * Método que muestra el índice
	 * 
	 * @param peticion
	 * @param escritor
	 */
	public static void mostrarIndex(String peticion, PrintWriter escritor) {
		FileReader ficheroALeer = null;
		BufferedReader lector = null;
		String linea = "";
		String html = "";

		try {
			ficheroALeer = new FileReader("index.html");
			lector = new BufferedReader(ficheroALeer);

			while ((linea = lector.readLine()) != null) {
				if (linea != null) {
					html = html.concat(linea);
				}
			}

			enviarInformacionPantalla(html, escritor);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ficheroALeer != null) {
					ficheroALeer.close();
				}
				if (lector != null) {
					lector.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * Método que muestra las partidas terminadas de get
	 * 
	 * @param peticion
	 * @param escritor
	 */
	public static void mostrarPartidasTerminadasGet(String peticion, PrintWriter escritor) {
		FileReader ficheroALeer = null;
		BufferedReader lector = null;
		String linea = "";
		String html = "";
		ArrayList<Partida> listaPartidas = new ArrayList<>();

		listaPartidas = contenedorDatos.getListaPartidasTerminadas();

		try {
			ficheroALeer = new FileReader("ListaPartidasGet.html");
			lector = new BufferedReader(ficheroALeer);

			while ((linea = lector.readLine()) != null) {
				if (linea != null) {
					html = html.concat(linea);
				}
			}

			for (Partida partida : listaPartidas) {
				html = html.concat("<div style=\"padding: 5px;\">");
				html = html.concat("<table style=\"border: 3px solid black; padding: 10px;\">");
				html = html.concat("<tr>");
				html = html.concat("<th>ID Partida:</th>");
				html = html.concat("<th style=\"border: 1px solid black;\">Jugadores</th>");
				html = html.concat("<th style=\"padding: 10px; border: 1px solid black;\">Ganador:</th>");
				html = html.concat("</tr>");
				html = html.concat("<tr>");
				html = html.concat("<td>" + partida.getIdPartida() + "</td>");
				html = html.concat("<td style=\"border: 1px solid black; padding: 5px;\">"
						+ partida.getJugador1().getNombre() + " vs " + partida.getJugador2().getNombre() + "</td>");
				html = html.concat("<td style=\"padding: 10px; border: 1px solid black;\">"
						+ partida.getNombreJugadorGanador() + "</td>");
				html = html.concat("</tr>");
				html = html.concat("</table>");
				html = html.concat("</div>");
			}

			html = html.concat("<form action=\"/Partida\" method=\"get\" style=\"padding: 5px;\">");
			html = html.concat("<p>Seleccione partida a ver:</p>");
			html = html.concat("<select name=\"idPartida\">");

			for (Partida partida : listaPartidas) {
				// Muestra todas las partidas
				html = html.concat(
						"<option value=\"" + partida.getIdPartida() + "\">" + partida.getIdPartida() + "</option>");
			}
			html = html.concat("<input type=\"submit\" value=\"Ver partida\">");
			html = html.concat("</form>");

			html = html.concat("</body>" + "</html>");
			enviarInformacionPantalla(html, escritor);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ficheroALeer != null) {
					ficheroALeer.close();
				}
				if (lector != null) {
					lector.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * Método que muestra las partidas terminadas de post
	 * 
	 * @param peticion
	 * @param escritor
	 */
	public static void mostrarPartidasTerminadasPost(String peticion, PrintWriter escritor) {
		FileReader ficheroALeer = null;
		BufferedReader lector = null;
		String linea = "";
		String html = "";
		ArrayList<Partida> listaPartidas = new ArrayList<>();

		listaPartidas = contenedorDatos.getListaPartidasTerminadas();

		try {
			ficheroALeer = new FileReader("ListaPartidasPost.html");
			lector = new BufferedReader(ficheroALeer);

			while ((linea = lector.readLine()) != null) {
				if (linea != null) {
					html = html.concat(linea);
				}
			}

			for (Partida partida : listaPartidas) {
				html = html.concat("<div style=\"padding: 5px;\">");
				html = html.concat("<table style=\"border: 3px solid black; padding: 10px;\">");
				html = html.concat("<tr>");
				html = html.concat("<th>ID Partida:</th>");
				html = html.concat("<th style=\"border: 1px solid black;\">Jugadores</th>");
				html = html.concat("<th style=\"padding: 10px; border: 1px solid black;\">Ganador:</th>");
				html = html.concat("</tr>");
				html = html.concat("<tr>");
				html = html.concat("<td>" + partida.getIdPartida() + "</td>");
				html = html.concat("<td style=\"border: 1px solid black; padding: 5px;\">"
						+ partida.getJugador1().getNombre() + " vs " + partida.getJugador2().getNombre() + "</td>");
				html = html.concat("<td style=\"padding: 10px; border: 1px solid black;\">"
						+ partida.getNombreJugadorGanador() + "</td>");
				html = html.concat("</tr>");
				html = html.concat("</table>");
				html = html.concat("</div>");
			}

			html = html.concat("<form action=\"/Partida\" method=\"post\" style=\"padding: 5px;\">");
			html = html.concat("<p>Seleccione partida a ver:</p>");
			html = html.concat("<select name=\"idPartida\">");

			for (Partida partida : listaPartidas) {
				// Muestra todas las partidas
				html = html.concat(
						"<option value=\"" + partida.getIdPartida() + "\">" + partida.getIdPartida() + "</option>");
			}
			html = html.concat("<input type=\"submit\" value=\"Ver partida\">");
			html = html.concat("</form>");

			html = html.concat("</body>" + "</html>");
			enviarInformacionPantalla(html, escritor);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ficheroALeer != null) {
					ficheroALeer.close();
				}
				if (lector != null) {
					lector.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public static void verPartidaTerminada(String peticion, PrintWriter escritor, int idPartida) {
		FileReader ficheroALeer = null;
		BufferedReader lector = null;
		String linea = "";
		String html = "";
		String cadenaLetras = "ABCDE";
		String cadenaNumeros = "12345";
		Partida partidaActual = comprobarPartidaActual(idPartida);

		try {
			ficheroALeer = new FileReader("Partida.html");
			lector = new BufferedReader(ficheroALeer);

			while ((linea = lector.readLine()) != null) {
				if (linea != null) {
					html = html.concat(linea);

				}
			}

			// Tabla jugador 1
			html = html.concat("<p style=\"text-align: left; font-size: 20px;\">Jugador 1: "
					+ partidaActual.getJugador1().getNombre() + "</p>");
			html = html.concat("<table>");
			for (int i = 0; i < 5; i++) {
				html = html.concat("<tr>");
				String letraActual = String.valueOf(cadenaLetras.charAt(i));
				for (int j = 0; j < 5; j++) {
					boolean isBarcoEnEsaPosicion = false;
					boolean isCasillaDisparada = false;
					String numeroActual = String.valueOf(cadenaNumeros.charAt(j));

					String posicionActual = letraActual + numeroActual;

					// Comprueba la posición con los barcos que el usuario había colocado
					for (Barco barco : partidaActual.getTableroJugador2().getListaPosicionesBarco()) {
						if (posicionActual.equals(barco.getPosicion())) {
							isBarcoEnEsaPosicion = true;
							break;
						}
					}
					// El jugador 2 hace disparos en el tablero 1
					for (String disparos : partidaActual.getTableroJugador1().getPosicionesDisparoJugador1()) {
						if (posicionActual.equals(disparos)) {
							isCasillaDisparada = true;
							break;
						}
					}

					// Si hay un barco y no está disparado lo coloca
					if (isBarcoEnEsaPosicion == true && isCasillaDisparada == false) {
						html = html.concat("<td style=\"background-color: brown;\">" + "Barco" + "</td>");
					} else if (isBarcoEnEsaPosicion == false && isCasillaDisparada == true) {
						html = html.concat("<td style=\"background-color: red;\">" + "X" + "</td>");
					} else if (isCasillaDisparada == true) {
						// Si hay un disparo lo coloca
						html = html.concat("<td style=\"background-color: lime;\">" + "X" + "</td>");
					} else if (isCasillaDisparada == false && isBarcoEnEsaPosicion == false) {
						// Si no hay disparo ni barco simplemente marca el nombre de la casilla
						html = html.concat("<td>" + posicionActual + "</td>");
					}

				}
				html = html.concat("</tr>");
			}
			html = html.concat("</table>");

			// Tabla jugador 2
			html = html.concat("<p style=\"text-align: left; font-size: 20px;\">Jugador 2: "
					+ partidaActual.getJugador2().getNombre() + "</p>");
			html = html.concat("<table>");
			for (int i = 0; i < 5; i++) {
				html = html.concat("<tr>");
				String letraActual = String.valueOf(cadenaLetras.charAt(i));
				for (int j = 0; j < 5; j++) {
					boolean isBarcoEnEsaPosicion = false;
					boolean isCasillaDisparada = false;
					String numeroActual = String.valueOf(cadenaNumeros.charAt(j));

					String posicionActual = letraActual + numeroActual;
					// Comprueba la posición con los barcos que el usuario había colocado
					for (Barco barco : partidaActual.getTableroJugador1().getListaPosicionesBarco()) {
						if (posicionActual.equals(barco.getPosicion())) {
							isBarcoEnEsaPosicion = true;
							break;
						}
					}
					// El jugador 1 hace disparos en el tablero 2
					for (String disparos : partidaActual.getTableroJugador2().getPosicionesDisparoJugador1()) {
						if (posicionActual.equals(disparos)) {
							isCasillaDisparada = true;
							break;
						}
					}

					// Si hay un barco y no está disparado lo coloca
					if (isBarcoEnEsaPosicion == true && isCasillaDisparada == false) {
						html = html.concat("<td style=\"background-color: brown;\">" + "Barco" + "</td>");
					} else if (isBarcoEnEsaPosicion == false && isCasillaDisparada == true) {
						html = html.concat("<td style=\"background-color: red;\">" + "X" + "</td>");
					} else if (isCasillaDisparada == true) {
						// Si hay un disparo lo coloca
						html = html.concat("<td style=\"background-color: lime;\">" + "X" + "</td>");
					} else if (isCasillaDisparada == false && isBarcoEnEsaPosicion == false) {
						// Si no hay disparo ni barco simplemente marca el nombre de la casilla
						html = html.concat("<td>" + posicionActual + "</td>");
					}

				}
				html = html.concat("</tr>");
			}
			html = html.concat("</table>");
			html = html.concat("<p>Ganador: " + partidaActual.getNombreJugadorGanador() + "</p>");
			html = html.concat("<p><a href=\"/index\">Indice</a></p>");

			html = html.concat("</body>");
			html = html.concat("</html>");

			enviarInformacionPantalla(html, escritor);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ficheroALeer != null) {
					ficheroALeer.close();
				}
				if (lector != null) {
					lector.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	/**
	 * Método que busca en el array para encontrar la partida que quiere ver el
	 * usuario
	 * 
	 * @param idPartida
	 * @return
	 */
	public static Partida comprobarPartidaActual(int idPartida) {
		ArrayList<Partida> listaPartidas = contenedorDatos.getListaPartidasTerminadas();
		for (Partida partida : listaPartidas) {
			if (partida.getIdPartida() == idPartida) {
				return partida;
			}
		}
		return null;
	}

	/**
	 * Método que envía la información al cliente
	 */
	public static void enviarInformacionPantalla(String htmlMostrar, PrintWriter escritor) {
		try {
			escritor.println(Mensajes.lineaInicial_OK);
			escritor.println(Mensajes.primeraCabecera);
			escritor.println("Content-Length: " + htmlMostrar.length());
			escritor.println("\n");
			escritor.println(htmlMostrar);
			System.out.println(htmlMostrar);
			// Hacemos un flush por si acaso
//			escritor.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
