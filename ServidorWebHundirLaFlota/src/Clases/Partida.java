package Clases;

import java.io.Serializable;

public class Partida implements Serializable {

	Tablero tableroJugador1;
	Tablero tableroJugador2;
	Usuario jugador1;
	Usuario jugador2;
	int cantidadMovimientos;
	boolean isTerminada;
	String nombreJugadorGanador;


	/**
	 * @param tableroJugador1
	 * @param tableroJugador2
	 * @param jugador1
	 * @param jugador2
	 * @param cantidadMovimientos
	 * @param isTerminada
	 * @param nombreJugadorGanador
	 */
	public Partida(Tablero tableroJugador1, Tablero tableroJugador2, Usuario jugador1, Usuario jugador2,
			int cantidadMovimientos, boolean isTerminada, String nombreJugadorGanador) {
		this.tableroJugador1 = tableroJugador1;
		this.tableroJugador2 = tableroJugador2;
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
		this.cantidadMovimientos = cantidadMovimientos;
		this.isTerminada = isTerminada;
		this.nombreJugadorGanador = nombreJugadorGanador;
	}


	public Tablero getTableroJugador1() {
		return tableroJugador1;
	}


	public void setTableroJugador1(Tablero tableroJugador1) {
		this.tableroJugador1 = tableroJugador1;
	}


	public Tablero getTableroJugador2() {
		return tableroJugador2;
	}


	public void setTableroJugador2(Tablero tableroJugador2) {
		this.tableroJugador2 = tableroJugador2;
	}


	public String getNombreJugadorGanador() {
		return nombreJugadorGanador;
	}


	public void setNombreJugadorGanador(String nombreJugadorGanador) {
		this.nombreJugadorGanador = nombreJugadorGanador;
	}


	public void setJugador1(Usuario jugador1) {
		this.jugador1 = jugador1;
	}


	public void setJugador2(Usuario jugador2) {
		this.jugador2 = jugador2;
	}


	public void setCantidadMovimientos(int cantidadMovimientos) {
		this.cantidadMovimientos = cantidadMovimientos;
	}


	public void setTerminada(boolean isTerminada) {
		this.isTerminada = isTerminada;
	}


	public boolean isTerminada() {
		return isTerminada;
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
