/**
 * 
 */
package MainServidorSeguro;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.util.ArrayList;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.*;

import Clases.Partida;
import Consultas.ConexionABBDD;
import ContenedorGet_Post.ContenedorDatos;

/**
 * 
 */
public class Main {

//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//
//		
//		ContenedorDatos contenedorDatos = new ContenedorDatos();
//		ArrayList<Partida> listaPartidas = new ArrayList<>();
//		System.out.println(mostrarInformacionPantalla());
//		ConexionABBDD conexionBBDD = new ConexionABBDD();
//
//		listaPartidas = conexionBBDD.buscarInformacionSobrePartidasTerminadas();
//		contenedorDatos.setListaPartidasTerminadas(listaPartidas);
//		
//		abrirServidor(contenedorDatos);
//	}

	public static String mostrarInformacionPantalla() {
		return "El servidor se encuentra abierto en el puerto 5000\r\n" + "1. https://localhost:5000\r\n";
	}

//	public static void abrirServidor(ContenedorDatos contenedor) {
//		SSLServerSocketFactory servidorSeguroFactory = null;
//		ServerSocket servidor = null;
//		try {
//			System.setProperty("javax.net.ssl.keyStore", "certificados/servidor/serverKey.jks");
//			System.setProperty("javax.net.ssl.keyStorePassword", "servpass");
//			System.setProperty("javax.net.ssl.trustStore", "certificados/servidor/serverTrustedCerts.jks");
//			System.setProperty("javax.net.ssl.trustStorePassword", "servpass");
//		} catch (Exception e) {
//			System.out.println("Error leyendo los certificados");
//		}
//		try {
//			// Abrimos el servidor - Seguro
//			servidorSeguroFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
//			servidor = servidorSeguroFactory.createServerSocket(5000);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		while (true) {
//			Socket conexion = null;
//
//			try {
//				// Aceptamos las conexiones - Seguro // En
//				// "https://chuidiang.org/index.php?title=Socket_SSL_con_Java#" se encuentra una
//				// buena guía de como usar
//				conexion = servidor.accept();
//
//				HiloCliente hiloCliente = new HiloCliente(conexion, contenedor);
//				hiloCliente.start();
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//	}

	public static void main(String[] args) {
		try {

			// Carga el almacén de claves
			char[] passphrase = "contraseniaCert".toCharArray();
			KeyStore keyStore = KeyStore.getInstance("JKS");
			keyStore.load(new FileInputStream("certificados/servidor/keystore.jks"), passphrase);

			// Crea el administrador de claves
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
			keyManagerFactory.init(keyStore, passphrase);

			// Crea el administrador de confianza
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
			trustManagerFactory.init(keyStore);

			// Configura el contexto SSL
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

			// Crea el server socket seguro
			SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
			SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(5000);

			System.out.println(mostrarInformacionPantalla());
			
			while (true) {
				SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();

				ContenedorDatos contenedor = null;
				HiloCliente hiloCliente = new HiloCliente(sslSocket, contenedor);
				hiloCliente.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
