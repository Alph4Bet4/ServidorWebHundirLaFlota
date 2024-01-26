/**
 * 
 */
package Clases;

import java.io.Serializable;

/**
 * 
 */
public class Disparo implements Serializable {
	int idTablero;
	int idJugadorDisparando;
	int idJugadorDisparado;
	String posicionDisparo;

	/**
	 * @param idTablero
	 * @param idJugadorDisparando
	 * @param idJugadorDisparado
	 * @param posicionDisparo
	 */
	public Disparo(int idTablero, int idJugadorDisparando, int idJugadorDisparado, String posicionDisparo) {
		this.idTablero = idTablero;
		this.idJugadorDisparando = idJugadorDisparando;
		this.idJugadorDisparado = idJugadorDisparado;
		this.posicionDisparo = posicionDisparo;
	}

	public int getIdTablero() {
		return idTablero;
	}

	public void setIdTablero(int idTablero) {
		this.idTablero = idTablero;
	}

	public int getIdJugadorDisparando() {
		return idJugadorDisparando;
	}

	public void setIdJugadorDisparando(int idJugadorDisparando) {
		this.idJugadorDisparando = idJugadorDisparando;
	}

	public int getIdJugadorDisparado() {
		return idJugadorDisparado;
	}

	public void setIdJugadorDisparado(int idJugadorDisparado) {
		this.idJugadorDisparado = idJugadorDisparado;
	}

	public String getPosicionDisparo() {
		return posicionDisparo;
	}

	public void setPosicionDisparo(String posicionDisparo) {
		this.posicionDisparo = posicionDisparo;
	}

}
