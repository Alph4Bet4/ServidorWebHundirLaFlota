/**
 * 
 */
package PaginaWeb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import Clases.Partida;
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
		try {
			InputStreamReader flujoEntrada = new InputStreamReader(conexion.getInputStream());
			BufferedReader lector = new BufferedReader(flujoEntrada);
			PrintWriter escritor = new PrintWriter(conexion.getOutputStream(), true);

			peticion = lector.readLine();
			peticion = peticion.replaceAll(" ", "");

			if (peticion.contains("/favicon.ico") == false || !peticion.contains("/main.css") == false) {
				System.out.println(peticion);
				peticionAMostrar = peticion;
				while ((peticionAMostrar = lector.readLine()).equals("") == false) {
					System.out.println(peticionAMostrar);
				}
			}
			System.out.println("");
			
			comprobarPeticion(peticion, escritor);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void comprobarPeticion(String peticion, PrintWriter escritor) {

		String errorPeticion = peticion.substring(3, peticion.lastIndexOf("HTTP"));

		if (errorPeticion.equals("/?") == false) {
			if (peticion.startsWith("GET")) {
				//Cortamos para ver la peticion
				peticion = peticion.substring(3, peticion.lastIndexOf("HTTP"));
				System.out.println("\t\t\t\t\t" + peticion);
				
				//Cortamos para ver el valor de la ID
				String valorID = peticion.substring(19);
				
				if (peticion.length() == 0 || peticion.equals("/") || peticion.equals("/index")) {
					mostrarIndexGet(peticion, escritor);
				} else if (peticion.equals("/formularioGet")) {
					mostrarPartidasTerminadasGet(peticion, escritor);
				} else if (peticion.equals("/Partida?idPartida=" + valorID)) {
					verPartidaTerminada(peticion, escritor, Integer.parseInt(valorID));
				}


			} else if (peticion.startsWith("POST")) {
				//Cortamos para ver la peticion
				peticion = peticion.substring(4, peticion.lastIndexOf("HTTP"));
				System.out.println("\t\t\t\t\t" + peticion);
				System.out.println("Un usuario ha intentado hacer una peticion POST en el servidor de GET");
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
	public static void mostrarIndexGet(String peticion, PrintWriter escritor) {
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

			System.out.println(html); // TODO hay un error en el html, me muestra "</"
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
	 * Método que muestra las partidas terminadas
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
				html = html.concat("<td style=\"border: 1px solid black; padding: 5px;\">" + partida.getJugador1().getNombre()
						+ " vs " + partida.getJugador2().getNombre() + "</td>");
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
			System.out.println(html);
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
		
		Partida partidaActual = comprobarPartidaActual(idPartida);
		
		
		
	}
	
	/**
	 * Método que busca en el array para encontrar la partida que quiere ver el usuario
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
			// Hacemos un flush por si acaso
//			escritor.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
