/**
 * 
 */
package Scraper;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;


/**
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String pgABuscar = "http://localhost:5000/";
//		String pgABuscar = "http://localhost:5000/Partida?idPartida=14";
		String pgABuscar = "http://localhost:5000/ListaPartidasGet";
//		String pgABuscar = "http://localhost:5000/ListaPartidasPost";
		
		obtenerURLGET(pgABuscar);
		
	}
	
	private static StringBuilder obtenerURLGET(String urlAObtener) {
		URI uri = null;
		URL url = null;
		StringBuilder cadenaDeString = new StringBuilder();
		try {
			uri = new URI(urlAObtener);	
			url = uri.toURL();
		
			cadenaDeString = obtenerValoresURL(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cadenaDeString;
	}

	
	private static StringBuilder obtenerValoresURL(URL url) {
		StringBuilder cadenaDeString = new StringBuilder();
		try {
			HttpURLConnection urlConexion = (HttpURLConnection) url.openConnection();
			
			Map<String, List<String>> header = Lector.leerHeader(urlConexion);
			//Capturamos el content Type
			String contentType = urlConexion.getContentType();
			//Y el content length
			int contentLength = urlConexion.getContentLength();
			//Leemos el cuerpo
			StringBuffer body = Lector.leerBody(urlConexion);
			
			return Lector.imprimirResultado(header, contentType, contentLength, body.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cadenaDeString;
	}
	
	//TODO hacer DOM
	
	public void leerXML(StringBuilder cadena) {
		
	}
	
}
