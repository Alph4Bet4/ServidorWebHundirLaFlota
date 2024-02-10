/**
 * 
 */
package MainServidorSeguro;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.*;

import Clases.HiloCliente;
import Clases.Partida;
import Consultas.ConexionABBDD;
import ContenedorGet_Post.ContenedorDatos;

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
		contenedorDatos.setListaPartidasTerminadas(listaPartidas);
		
		abrirServidor(contenedorDatos);
	}

	public static String mostrarInformacionPantalla() {
		return "El servidor se encuentra abierto en el puerto 5000\r\n" + "1. http://localhost:5000\r\n";
	}

	public static void abrirServidor(ContenedorDatos contenedor) {
		SSLServerSocketFactory servidorSeguroFactory = null;
		ServerSocket servidor = null;
		try {
			System.setProperty("javax.net.ssl.keyStore", "certificados/servidor/serverKey.jks");
			System.setProperty("javax.net.ssl.keyStorePassword","servpass");
			System.setProperty("javax.net.ssl.trustStore", "certificados/servidor/serverTrustedCerts.jks");
			System.setProperty("javax.net.ssl.trustStorePassword", "servpass");
			
		} catch (Exception e) {
			System.out.println("Error leyendo los certificados");
		}
		try {
			//Abrimos el servidor - Seguro
			servidorSeguroFactory =  (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			servidor = servidorSeguroFactory.createServerSocket(5000);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {			
			Socket conexion = null;
			
			try {
				//Aceptamos las conexiones - Seguro // En "https://chuidiang.org/index.php?title=Socket_SSL_con_Java#C%C3%B3digo_de_ejemplo" se encuentra una buena guía de como usar
				conexion = servidor.accept();
				
				HiloCliente hiloCliente = new HiloCliente(conexion, contenedor);				
				hiloCliente.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
