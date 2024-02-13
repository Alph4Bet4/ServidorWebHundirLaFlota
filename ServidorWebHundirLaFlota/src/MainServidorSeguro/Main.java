/**
 * 
 */
package MainServidorSeguro;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.*;

import Clases.Partida;
import Contenedor.ContenedorDatos;
import MainGet_Post.ConexionABBDD;

/**
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		ContenedorDatos contenedorDatos = new ContenedorDatos();
		ArrayList<Partida> listaPartidas = new ArrayList<>();
		System.out.println(mostrarInformacionPantalla());
		ConexionABBDD conexionBBDD = new ConexionABBDD();

		listaPartidas = conexionBBDD.buscarInformacionSobrePartidasTerminadas();
		
		abrirServidor(contenedorDatos);
	}

	public static String mostrarInformacionPantalla() {
		return "El servidor SEGURO se encuentra abierto en el puerto 5000\r\n" + "1. https://localhost:5000\r\n";
	}

	public static void abrirServidor(ContenedorDatos contenedor) {
		// Carga el almacén de claves
		SSLServerSocket sslServerSocket;
		try {
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
			sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(5000);
			
			
			while (true) {
				SSLSocket sslSocket = null;

				try {
					sslSocket = (SSLSocket) sslServerSocket.accept();

					HiloCliente hiloCliente = new HiloCliente(sslSocket, contenedor);
					hiloCliente.start();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
