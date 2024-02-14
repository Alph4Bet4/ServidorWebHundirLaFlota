/**
 * 
 */
package Scraper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * 
 */
public class Lector {

	/**
	 * Método que te lee el header
	 * 
	 * @param conexionURL
	 * @return
	 */
	public static Map<String, List<String>> leerHeader(HttpURLConnection conexionURL) {
		Map<String, List<String>> cabeceraRespuesta = null;
		try {
			cabeceraRespuesta = conexionURL.getHeaderFields();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error leyendo cabeceras");
		}
		
		return cabeceraRespuesta;
	}

	/**
	 * Método que te lee el cuerpo
	 * 
	 * @param conexionURL
	 * @return
	 */
	public static StringBuilder leerBody(HttpURLConnection conexionURL) {
		String lineaEntrada;
		StringBuilder cadenaString = new StringBuilder();
		try {
			BufferedReader flujoEntrada = new BufferedReader(
					new InputStreamReader(conexionURL.getInputStream(), "UTF-8"));
			while ((lineaEntrada = flujoEntrada.readLine()) != null) {
				cadenaString.append(lineaEntrada);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error en leer el body");
		}
		return cadenaString;
	}

	public static StringBuilder imprimirResultado(Map<String, List<String>> header, String contentType,
			int contentLength, String body) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(body);

		
		return sb;
		
	}
}
