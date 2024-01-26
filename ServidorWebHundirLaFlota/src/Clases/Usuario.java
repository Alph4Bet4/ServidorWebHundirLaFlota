/**
 * 
 */
package Clases;

import java.io.Serializable;

/**
 * 
 */
public class Usuario implements Serializable {
	int idJugador;
	String nombre;
	String contrasenia;
	int partidasJugadas;
	int puntuacion;
	
	/**
	 * @param nombre
	 * @param contrasenia
	 * @param partidasJugadas
	 * @param puntuacion
	 */
	public Usuario(int idJugador, String nombre, String contrasenia, int partidasJugadas, int puntuacion) {
		this.idJugador = idJugador;
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.partidasJugadas = partidasJugadas;
		this.puntuacion = puntuacion;
	}

	public String getNombre() {
		return nombre;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public int getPartidasJugadas() {
		return partidasJugadas;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public int getIdJugador() {
		return idJugador;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}
	
}
