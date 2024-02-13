/**
 * 
 */
package MainServidorSeguro;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 */
public class Tablero implements Serializable {

	int idTablero;
	ArrayList<Barco> listaPosicionesBarco;
	ArrayList<Barco> listaPosicionesBarcoEnemigo;
	ArrayList<String> posicionesDisparoJugador1;
	ArrayList<String> posicionesDisparoJugador2;
	boolean turnoJugador;
	int barcosRestantes;

	/**
	 * @param idTablero
	 * @param listaPosicionesBarco
	 * @param listaPosicionesBarcoEnemigo
	 * @param posicionesDisparoJugador1
	 * @param posicionesDisparoJugador2
	 * @param turnoJugador
	 * @param barcosRestantes
	 */
	public Tablero(int idTablero, ArrayList<Barco> listaPosicionesBarco, ArrayList<Barco> listaPosicionesBarcoEnemigo,
			ArrayList<String> posicionesDisparoJugador1, ArrayList<String> posicionesDisparoJugador2,
			boolean turnoJugador, int barcosRestantes) {
		this.idTablero = idTablero;
		this.listaPosicionesBarco = listaPosicionesBarco;
		this.listaPosicionesBarcoEnemigo = listaPosicionesBarcoEnemigo;
		this.posicionesDisparoJugador1 = posicionesDisparoJugador1;
		this.posicionesDisparoJugador2 = posicionesDisparoJugador2;
		this.turnoJugador = turnoJugador;
		this.barcosRestantes = barcosRestantes;
	}

	public ArrayList<Barco> getListaPosicionesBarcoEnemigo() {
		return listaPosicionesBarcoEnemigo;
	}

	public void setListaPosicionesBarcoEnemigo(ArrayList<Barco> listaPosicionesBarcoEnemigo) {
		this.listaPosicionesBarcoEnemigo = listaPosicionesBarcoEnemigo;
	}

	public void setIdTablero(int idTablero) {
		this.idTablero = idTablero;
	}

	public void setListaPosicionesBarco(ArrayList<Barco> listaPosicionesBarco) {
		this.listaPosicionesBarco = listaPosicionesBarco;
	}

	public void setPosicionesDisparoJugador1(ArrayList<String> posicionesDisparoJugador1) {
		this.posicionesDisparoJugador1 = posicionesDisparoJugador1;
	}

	public void setPosicionesDisparoJugador2(ArrayList<String> posicionesDisparoJugador2) {
		this.posicionesDisparoJugador2 = posicionesDisparoJugador2;
	}

	public void setTurnoJugador(boolean turnoJugador) {
		this.turnoJugador = turnoJugador;
	}

	public void setBarcosRestantes(int barcosRestantes) {
		this.barcosRestantes = barcosRestantes;
	}
	
	public int getIdTablero() {
		return idTablero;
	}

	public ArrayList<String> getPosicionesDisparoJugador1() {
		return posicionesDisparoJugador1;
	}

	public ArrayList<String> getPosicionesDisparoJugador2() {
		return posicionesDisparoJugador2;
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
