/**
 * 
 */
package Clases;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 */
public class Tablero implements Serializable {

	int idTablero;
	ArrayList<Barco> listaPosicionesBarco;
	ArrayList<String> posicionesDisparoJugador1;
	ArrayList<String> posicionesDisparoJugador2;
	boolean turnoJugador;
	int barcosRestantes;

	public int getIdTablero() {
		return idTablero;
	}

	public ArrayList<String> getPosicionesDisparoJugador1() {
		return posicionesDisparoJugador1;
	}

	public ArrayList<String> getPosicionesDisparoJugador2() {
		return posicionesDisparoJugador2;
	}

	/**
	 * @param idTablero
	 * @param listaPosicionesBarco
	 * @param posicionesDisparoJugador1
	 * @param posicionesDisparoJugador2
	 * @param turnoJugador
	 * @param barcosRestantes
	 */
	public Tablero(int idTablero, ArrayList<Barco> listaPosicionesBarco, ArrayList<String> posicionesDisparoJugador1,
			ArrayList<String> posicionesDisparoJugador2, boolean turnoJugador, int barcosRestantes) {
		this.idTablero = idTablero;
		this.listaPosicionesBarco = listaPosicionesBarco;
		this.posicionesDisparoJugador1 = posicionesDisparoJugador1;
		this.posicionesDisparoJugador2 = posicionesDisparoJugador2;
		this.turnoJugador = turnoJugador;
		this.barcosRestantes = barcosRestantes;
	}

	public void setTurnoJugador1(boolean turnoJugador1) {
		this.turnoJugador = turnoJugador1;
	}

	public ArrayList<Barco> getListaPosicionesBarco() {
		return listaPosicionesBarco;
	}

	public int getBarcosRestantes() {
		return barcosRestantes;
	}

	/**
	 * Método que se usa cuando un barco ha sido golpeado
	 */
	public void restarUnBarco() {
		this.barcosRestantes--;
	}

	public boolean isTurnoJugador() {
		return turnoJugador;
	}

}
