/**
 * 
 */
package Scraper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String pgABuscar = "http://localhost:5000/ListaPartidasGet";
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder = obtenerURLGET(pgABuscar);

		guardarXMLInformacionListaPartidas(stringBuilder);
		ArrayList<Integer> listaPartidas = leerXMLListaPartidas();

		if (listaPartidas != null) {
			for (Integer numPartida : listaPartidas) {
				pgABuscar = "http://localhost:5000/Partida?idPartida=" + numPartida;
				stringBuilder = obtenerURLGET(pgABuscar);
				guardarXMLInformacionPartida(stringBuilder);
				System.out.println("----Partida: " + numPartida + " ----");
				leerXMLPartidas();
				System.out.println();

			}
		}
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
			// Capturamos el content Type
			String contentType = urlConexion.getContentType();
			// Y el content length
			int contentLength = urlConexion.getContentLength();
			// Leemos el cuerpo
			StringBuilder body = Lector.leerBody(urlConexion);

			Lector.imprimirResultado(header, contentType, contentLength, body.toString());
			return body;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cadenaDeString;
	}

	/**
	 * Método que guarda en un xml la información de las páginas para luego
	 * posteriormente tratarlas
	 * 
	 * @param stringBuilder
	 */
	public static void guardarXMLInformacionListaPartidas(StringBuilder stringBuilder) {
		FileWriter fichero = null;
		BufferedWriter escritor = null;
		try {
			fichero = new FileWriter("InformacionPartidas.xml");
			escritor = new BufferedWriter(fichero);

			// Escribe para que pueda ser un xml
			escritor.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			escritor.write(stringBuilder.toString());
			escritor.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fichero != null) {
					fichero.close();
				}
				if (escritor != null) {
					escritor.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	/**
	 * Método que guarda en un xml la información de las partidas, se machaca
	 * constantemente
	 * 
	 * @param stringBuilder
	 */
	public static void guardarXMLInformacionPartida(StringBuilder stringBuilder) {
		FileWriter fichero = null;
		BufferedWriter escritor = null;
		try {
			fichero = new FileWriter("Partida.xml");
			escritor = new BufferedWriter(fichero);

			// Escribe para que pueda ser un xml
			escritor.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			escritor.write(stringBuilder.toString());
			escritor.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fichero != null) {
					fichero.close();
				}
				if (escritor != null) {
					escritor.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	/**
	 * Método que recorre los options para sacar las claves de las partidas
	 * 
	 * @return
	 */
	public static ArrayList<Integer> leerXMLListaPartidas() {
		File fichero = null;
		Document documento = null;
		DocumentBuilderFactory dBFactory = null;
		DocumentBuilder dBuilder = null;
		ArrayList<Integer> listaPartidas = new ArrayList<>();

		try {
			fichero = new File("InformacionPartidas.xml");
			dBFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dBFactory.newDocumentBuilder();
			documento = dBuilder.parse(fichero);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Iniciamos

		try {
			documento.getDocumentElement().normalize();

			NodeList nodoPadre = documento.getElementsByTagName("option");

			for (int i = 0; i < nodoPadre.getLength(); i++) {
				Node nodo = nodoPadre.item(i);

				if (nodo.getNodeType() == Node.ELEMENT_NODE) {
					Element elemento = (Element) nodo;

					listaPartidas.add(Integer.parseInt(elemento.getTextContent()));

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaPartidas;
	}

	/**
	 * Método que recorre la partida para encontrar quién ha ganado y quién perdió
	 * 
	 * @return
	 */
	public static void leerXMLPartidas() {
		File fichero = null;
		Document documento = null;
		DocumentBuilderFactory dBFactory = null;
		DocumentBuilder dBuilder = null;
		ArrayList<Integer> listaPartidas = new ArrayList<>();

		// Strings que no debe de leer
		String verde = "Verde - Golpeo a Barco";
		String marron = "Marron - Barco colocado";
		String rojo = "Rojo - Disparo fallado";
		String indice = "Indice";

		try {
			fichero = new File("Partida.xml");
			dBFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dBFactory.newDocumentBuilder();
			documento = dBuilder.parse(fichero);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Iniciamos

		try {
			documento.getDocumentElement().normalize();

			NodeList nodoPadre = documento.getElementsByTagName("p");

			for (int i = 0; i < nodoPadre.getLength(); i++) {
				Node nodo = nodoPadre.item(i);

				if (nodo.getNodeType() == Node.ELEMENT_NODE) {
					Element elemento = (Element) nodo;

					if (!elemento.getTextContent().equals(verde) && !elemento.getTextContent().equals(marron)
							&& !elemento.getTextContent().equals(rojo) && !elemento.getTextContent().equals(indice)) {
						System.out.println(elemento.getTextContent());
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
