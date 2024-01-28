/**
 * 
 */
package Clases;

import java.io.IOException;
import java.net.Socket;

import PaginaWeb.ServidorHTTP;

/**
 * 
 */
public class HiloCliente extends Thread {
	Socket conexion;

	public HiloCliente(Socket conexion) {
		this.conexion = conexion;
	}

	@Override
	public void run() {
		System.out.println("Un usuario ha hecho una conexion: " + conexion.getLocalAddress());
		ServidorHTTP servidorHTTP = new ServidorHTTP();
		servidorHTTP.recibirPeticion(conexion);

		try {
			conexion.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
