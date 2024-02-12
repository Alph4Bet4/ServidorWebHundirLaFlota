/**
 * 
 */
package MainServidorSeguro;

import java.io.IOException;
import java.net.Socket;

import javax.net.ssl.SSLSocket;

import ContenedorGet_Post.ContenedorDatos;
import PaginaWeb.ServidorHTTP;

/**
 * 
 */
public class HiloCliente extends Thread {
	SSLSocket conexion;
	ContenedorDatos contenedorDatos;

	public HiloCliente(SSLSocket conexion, ContenedorDatos contenedorDatos) {
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
