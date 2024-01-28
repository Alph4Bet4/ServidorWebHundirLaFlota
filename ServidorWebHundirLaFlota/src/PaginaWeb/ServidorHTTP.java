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

				if (peticion.length() == 0 || peticion.equals("/") || peticion.equals("/index")) {
					mostrarIndexGet(peticion, escritor);
				}

				if (peticion.equals("/formularioGet")) {
					mostrarPartidasTerminadasGet(peticion, escritor);
				}

			} else if (peticion.startsWith("POST")) {
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

		peticion = peticion.substring(3, peticion.lastIndexOf("HTTP"));

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

		peticion = peticion.substring(3, peticion.lastIndexOf("HTTP"));

		try {
			ficheroALeer = new FileReader("ListaPartidasGet.html");
			lector = new BufferedReader(ficheroALeer);

			while ((linea = lector.readLine()) != null) {
				if (linea != null) {
					html = html.concat(linea);
				}
			}

			for (Partida partida : listaPartidas) {
				html = html.concat("<table style=\"border: 1px solid black;\">");
				
			}

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
			escritor.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
