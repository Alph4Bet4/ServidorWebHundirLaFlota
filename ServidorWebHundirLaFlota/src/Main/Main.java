/**
 * 
 */
package Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
		ServerSocket servidor = null;
		try {
			// Abrimos el servidor
			servidor = new ServerSocket(5000);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			Socket conexion = null;
			try {

				// Aceptamos las conexiones
				conexion = servidor.accept();
				HiloCliente hiloCliente = new HiloCliente(conexion, contenedor);				
				hiloCliente.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
