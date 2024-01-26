package Clases;

import java.io.Serializable;

public class Partida implements Serializable {

	Tablero tablero;
	Usuario jugador1;
	Usuario jugador2;
	int cantidadMovimientos;
	boolean isTerminada;
	String nombreJugadorGanador;

	/**
	 * @param tablero
	 * @param jugador1
	 * @param jugador2
	 * @param cantidadMovimientos
	 * @param isTerminada
	 */
	public Partida(Tablero tablero, Usuario jugador1, Usuario jugador2, int cantidadMovimientos, boolean isTerminada, String nombreJugadorGanador) {
		this.tablero = tablero;
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
		this.cantidadMovimientos = cantidadMovimientos;
		this.isTerminada = isTerminada;
		this.nombreJugadorGanador = nombreJugadorGanador;
	}

	public boolean isTerminada() {
		return isTerminada;
	}

	public Tablero getTablero() {
		return tablero;
	}

	public Usuario getJugador1() {
		return jugador1;
	}

	public Usuario getJugador2() {
		return jugador2;
	}

	public int getCantidadMovimientos() {
		return cantidadMovimientos;
	}

}
