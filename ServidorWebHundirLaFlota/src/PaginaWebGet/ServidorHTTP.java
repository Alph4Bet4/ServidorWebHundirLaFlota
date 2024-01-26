/**
 * 
 */
package PaginaWebGet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import PaginaWebGet.Mensajes;

/**
 * 
 */
public class ServidorHTTP {

	public void recibirPeticion(Socket conexion) {
		String peticion;
		try {
			InputStreamReader flujoEntrada = new InputStreamReader(conexion.getInputStream());
			BufferedReader lector = new BufferedReader(flujoEntrada);
			PrintWriter escritor = new PrintWriter(conexion.getOutputStream(), true);

			peticion = lector.readLine();

			System.out.println(peticion);

			peticion = peticion.replaceAll(" ", "");

			comprobarPeticion(peticion, escritor);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void comprobarPeticion(String peticion, PrintWriter escritor) {
		FileReader ficheroALeer = null;
		BufferedReader lector = null;
		String linea = "";
		String html = "";

		if (peticion.startsWith("GET")) {
			peticion = peticion.substring(3, peticion.lastIndexOf("HTTP"));

			if (peticion.length() == 0 || peticion.equals("/") || peticion.equals("index")) {
				try {
					ficheroALeer = new FileReader("index.html");
					lector = new BufferedReader(ficheroALeer);

					while ((linea = lector.readLine()) != null) {
						if (linea != null) {
							html = html.concat(linea);
						}						
					}

					escritor.println(Mensajes.lineaInicial_OK);
					escritor.println(Mensajes.primeraCabecera);
					escritor.println("Content-Length: " + html.length());
					escritor.println("\n");
					escritor.println(html);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Un usuario ha intentado hacer una peticion POST en el servidor de GET");
		}

	}

}
