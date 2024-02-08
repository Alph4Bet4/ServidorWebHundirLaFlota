/**
 * 
 */
package Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;

import Clases.HiloCliente;
import Clases.Partida;
import Clases.Usuario;
import Consultas.ConexionABBDD;
import Contenedor.ContenedorDatos;
import PaginaWeb.ServidorHTTP;

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
			// Abrimos el servidor - No seguro
			servidor = new ServerSocket(5000);
			//Abrimos el servidor - Seguro
//			servidorSeguroFactory =  (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
//			servidor = servidorSeguroFactory.createServerSocket(5000);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {			
			Socket conexion = null;
			
			try {
				// Aceptamos las conexiones - No seguro
				conexion = servidor.accept();
				//Aceptamos las conexiones - Seguro // En chuidiang se encuentra una buena guía de como usar
//				conexion = servidor.accept();
				
				HiloCliente hiloCliente = new HiloCliente(conexion, contenedor);				
				hiloCliente.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
