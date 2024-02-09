/**
 * 
 */
package LectorScraper;

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
		Map<String, List<String>> cabeceraRespuesta = conexionURL.getHeaderFields();
		return cabeceraRespuesta;
	}

	/**
	 * Método que te lee el cuerpo
	 * 
	 * @param conexionURL
	 * @return
	 */
	public static StringBuffer leerBody(HttpURLConnection conexionURL) {
		String lineaEntrada;
		StringBuffer cadenaString = new StringBuffer();
		try {
			BufferedReader flujoEntrada = new BufferedReader(
					new InputStreamReader(conexionURL.getInputStream(), "UTF-8"));
			while ((lineaEntrada = flujoEntrada.readLine()) != null) {
				cadenaString.append(lineaEntrada);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cadenaString;
	}

	public static StringBuilder imprimirResultado(Map<String, List<String>> header, String contentType,
			int contentLength, String body) {
		StringBuilder sb = new StringBuilder();
		
		System.out.println("Todos los headers leidos");
		System.out.println(header);
		System.out.println();
		
		System.out.println("Header Content-Type");
		System.out.println(contentType);
		System.out.println("Header contentLength");
		System.out.println(contentLength);
		
		System.out.println("Body Leido:");
		System.out.println(body);
		
		sb.append(body);
		
		body = body.replaceAll("<[^>]*>", "");
		System.out.println("Body leido eliminando todas las etiquetas");
		System.out.println(body);
		
		return sb;
		
	}
}
