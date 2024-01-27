/**
 * 
 */
package MainGet;

import java.net.ServerSocket;
import java.net.Socket;

import Clases.Usuario;
import Consultas.ConexionABBDD;
import PaginaWebGet.ServidorHTTP;

/**
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSocket servidor = null;
		Socket conexion = null;
		System.out.println(mostrarInformacionPantalla());
		ConexionABBDD conexionBBDD = new ConexionABBDD(); // TODO cambiar dentro de la conexion bbdd que haga una
															// consulta con todas las partidas finalizadas y debe
															// mostrar ambos tableros

		try {
			servidor = new ServerSocket(5000);

			while (true) {
				conexion = servidor.accept();
				System.out.println("Un usuario ha hecho una conexion: " + conexion.getLocalAddress());

				ServidorHTTP servidorHTTP = new ServidorHTTP();
				servidorHTTP.recibirPeticion(conexion);

				conexion.close();
				System.out.println("Conexión cerrada");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String mostrarInformacionPantalla() {
		return "El servidor se encuentra abierto en el puerto 5000\r\n" + "1. http://localhost:5000\r\n";
	}
}
