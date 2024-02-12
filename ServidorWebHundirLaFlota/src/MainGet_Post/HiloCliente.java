/**
 * 
 */
package MainGet_Post;

import java.io.IOException;
import java.net.Socket;

import ContenedorGet_Post.ContenedorDatos;

/**
 * 
 */
public class HiloCliente extends Thread {
	Socket conexion;
	ContenedorDatos contenedorDatos;

	public HiloCliente(Socket conexion, ContenedorDatos contenedorDatos) {
		this.conexion = conexion;
		this.contenedorDatos = contenedorDatos;
	}

	@Override
	public void run() {
		System.out.println("Un usuario ha hecho una conexion: " + conexion.getLocalAddress());
		ServidorHTTP servidorHTTP = new ServidorHTTP(this.contenedorDatos);
		servidorHTTP.recibirPeticion(conexion);

		try {
			conexion.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
