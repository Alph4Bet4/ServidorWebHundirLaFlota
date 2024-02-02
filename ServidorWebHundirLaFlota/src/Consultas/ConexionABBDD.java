/**
 * 
 */
package Consultas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Clases.*;

/**
 * 
 */
public class ConexionABBDD {

	private String nombreBaseDatos = "hundirlaflota";
	private String usuario = "root";
	private String contrasenia = "root";
	private String conexionString = "jdbc:mysql://localhost:3306/";
	private Connection conexion;

	/**
	 * Método que recibe un nombre y devuelve un usuario, principalmente usado para
	 * crear partidas
	 * 
	 * @param nombre
	 * @return
	 */
	public Usuario buscarUsuarioBuscandoPorID(int idJugador) {
		// Abrimos la conexión
		try {
			conexion = DriverManager.getConnection(this.conexionString + this.nombreBaseDatos, this.usuario,
					this.contrasenia);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Usuario usuario = null;
		if (conexion != null) {
			PreparedStatement query = null;
			ResultSet resultado = null;
			int idUsuario;
			String nombreUsuario;
			String contrasenia;
			int partidasJugadas;
			int puntuacion;

			try {
				query = conexion.prepareStatement("SELECT * FROM hundirlaflota.usuarios WHERE IDUsuario = ?");
				query.setInt(1, idJugador);

				resultado = query.executeQuery();
				resultado.next();
				idUsuario = resultado.getInt(1);
				nombreUsuario = resultado.getString(2);
				contrasenia = "vacio"; // Por tema de seguridad no lo añadimos
				partidasJugadas = resultado.getInt(4);
				puntuacion = resultado.getInt(5);
				
				usuario = new Usuario(idUsuario, nombreUsuario, contrasenia, partidasJugadas, puntuacion);

			} catch (Exception e) {
				System.out.println("- No se ha encontrado un usuario con ese nombre");
			} finally {
				try {
					if (conexion != null) {
						conexion.close();
					}
					if (query != null) {
						query.close();
					}
					if (resultado != null) {
						resultado.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} else {
			System.out.println("- Ha ocurrido un error en buscarUsuario");
		}

		return usuario;
	}

	/**
	 * Método que busca la información sobre un tablero recibiendo por parámetros un
	 * idTablero
	 * 
	 * @param idTablero
	 * @return
	 */
	public Tablero buscarInformacionTablero(int idTablero, int idUsuarioPropio, int idUsuarioEnemigo, String posicion) {
		// Abrimos la conexión
		try {
			conexion = DriverManager.getConnection(this.conexionString + this.nombreBaseDatos, this.usuario,
					this.contrasenia);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (conexion != null) {
			PreparedStatement query = null;
			ResultSet resultado = null;
			boolean isTurnoJugadorPropio = false;
			boolean isTurnoJugadorEnemigo = false;
			int numBarcosRestantesJugadorPropio = 0;
			int numBarcosRestantesJugadorEnemigo = 0;
			ArrayList<String> listaDisparosUsuarioPropio = new ArrayList<String>();
			ArrayList<String> listaDisparosUsuarioEnemigo = new ArrayList<String>();
			ArrayList<Barco> listaBarcos = new ArrayList<Barco>();
			Tablero nuevoTablero = null;

			try {
				query = conexion.prepareStatement("SELECT * FROM hundirlaflota.tablero WHERE IdTablero = ?");
				query.setInt(1, idTablero);

				resultado = query.executeQuery();

				while (resultado.next()) {

					if (resultado.getInt(2) == 0 ? (isTurnoJugadorPropio = false) : (isTurnoJugadorPropio = true))
						;
					if (resultado.getInt(3) == 0 ? (isTurnoJugadorEnemigo = false) : (isTurnoJugadorEnemigo = true))
						;

					numBarcosRestantesJugadorPropio = resultado.getInt(4);
					numBarcosRestantesJugadorEnemigo = resultado.getInt(5);

					System.out.println("IDUSUARIO ENEMIGO " + idUsuarioEnemigo); //TODO
					System.out.println("IDUSUARIO PROPIO " + idUsuarioPropio);
					
					listaDisparosUsuarioPropio = buscarInformacionDisparos(idUsuarioPropio, idTablero);
					
					listaDisparosUsuarioEnemigo = buscarInformacionDisparos(idUsuarioEnemigo, idTablero);

					listaBarcos = buscarInformacionBarco(idTablero, idUsuarioPropio);

					nuevoTablero = new Tablero(idTablero, listaBarcos, listaDisparosUsuarioPropio,
							listaDisparosUsuarioEnemigo, isTurnoJugadorEnemigo, numBarcosRestantesJugadorPropio);
				}

				return nuevoTablero;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (conexion != null) {
						conexion.close();
					}
					if (query != null) {
						query.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} else {
			System.out.println("- Ha ocurrido un error buscando información sobre tablero");
		}
		return null;
	}

	/**
	 * Método que busca por idUsuario y idTablero para recopilar los movimientos
	 * 
	 * @param idUsuario
	 * @param idTablero
	 * @return
	 */
	public ArrayList<String> buscarInformacionDisparos(int idUsuario, int idTablero) {
		ArrayList<String> listaDisparos = new ArrayList<String>();
		// Abrimos la conexión
		try {
			conexion = DriverManager.getConnection(this.conexionString + this.nombreBaseDatos, this.usuario,
					this.contrasenia);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (conexion != null) {
			PreparedStatement query = null;
			ResultSet resultado = null;
			String posicionDisparo;
			try {
				query = conexion.prepareStatement(
						"SELECT * FROM hundirlaflota.movimientos WHERE IdTablero = ? AND IdJugador = ?;");
				query.setInt(1, idTablero);
				query.setInt(2, idUsuario);

				resultado = query.executeQuery();
				
				while (resultado.next()) {
					posicionDisparo = resultado.getString(4);
					System.out.println(idUsuario + " ----Disparo:" + posicionDisparo); //TODO
					listaDisparos.add(posicionDisparo);
				}
				System.out.println();
				return listaDisparos;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (conexion != null) {
						conexion.close();
					}
					if (query != null) {
						query.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		} else {
			System.out.println("- Ha ocurrido un error buscando información sobre disparos");
		}

		return null;
	}

	/**
	 * Método que busca la información sobre los barcos de un tablero de un jugador
	 * 
	 * @return
	 */
	public ArrayList<Barco> buscarInformacionBarco(int idTablero, int idJugador) {
		ArrayList<Barco> listaBarcos = new ArrayList<Barco>();
		// Abrimos la conexión
		try {
			conexion = DriverManager.getConnection(this.conexionString + this.nombreBaseDatos, this.usuario,
					this.contrasenia);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (conexion != null) {
			PreparedStatement query = null;
			ResultSet resultado = null;
			try {
				query = conexion
						.prepareStatement("SELECT * FROM hundirlaflota.barcos WHERE IdTablero = ? AND IdJugador = ?");
				query.setInt(1, idTablero);
				query.setInt(2, idJugador);
				resultado = query.executeQuery();
				if (resultado.next()) {
					for (int i = 3; i <= 9; i = i + 2) {
						String posicionBarco;
						try {
							posicionBarco = resultado.getString(i);
							System.out.println("ID: " + idJugador + "+++++++++++++++posicion barco " + posicionBarco);
						} catch (Exception e) {
							posicionBarco = "vacio";
							e.printStackTrace();
						}

						boolean isGolpeado = false;
						try {
							if (resultado.getInt(i + 1) == 0 ? (isGolpeado = false) : (isGolpeado = true))
								;
						} catch (Exception e) {
							e.printStackTrace();
							isGolpeado = false;
						}
						Barco nuevoBarco = new Barco(posicionBarco, isGolpeado);
						listaBarcos.add(nuevoBarco);
					}
				}
				return listaBarcos;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (conexion != null) {
						conexion.close();
					}
					if (query != null) {
						query.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} else {
			System.out.println("- Ha ocurrido un error buscando información sobre barcos");
		}

		return null;
	}

	/**
	 * Método que busca las partidas del usuario que están finalizadas
	 * 
	 * @param usuarioPropio
	 * @return
	 */
	public ArrayList<Partida> buscarInformacionSobrePartidasTerminadas() {
		ArrayList<Partida> listaPartidas = new ArrayList<Partida>();
		// Abrimos la conexión
		try {
			conexion = DriverManager.getConnection(this.conexionString + this.nombreBaseDatos, this.usuario,
					this.contrasenia);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (conexion != null) {
			PreparedStatement query = null;
			ResultSet resultado = null;
			int idUsuario = 0;
			int idTablero = 0;
			int idUsuarioEnemigo = 0;
			int idPartida = 0;
			int idJugadorPropio = 0;
			int idJugadorEnemigo = 0;
			boolean isTerminada = false;
			int movimientosTotales = 0;
			Usuario usuarioEnemigo;
			Usuario usuarioPropio;
			Tablero tableroJugador1 = null;
			Tablero tableroJugador2 = null;
			Partida nuevaPartida = null;
			String lugarJugadorPropio = "";
			String lugarJugadorEnemigo = "";
			String jugadorGanador;

			try {
				query = conexion.prepareStatement("SELECT * FROM hundirlaflota.partida WHERE isTerminada = 1");

				resultado = query.executeQuery();

				// Recorremos las partidas
				while (resultado.next()) {

					idPartida = resultado.getInt(1);
					idTablero = resultado.getInt(2);
					idJugadorPropio = resultado.getInt(3);
					idJugadorEnemigo = resultado.getInt(4);
					if (resultado.getInt(5) == 0 ? (isTerminada = false) : (isTerminada = true))
						;
					movimientosTotales = resultado.getInt(6);

					lugarJugadorPropio = "izq";
					lugarJugadorEnemigo = "der";
					
					
					usuarioPropio = buscarUsuarioBuscandoPorID(idJugadorPropio);

					usuarioEnemigo = buscarUsuarioBuscandoPorID(idJugadorEnemigo);
					
					//TODO arreglado, borrar cosas
					jugadorGanador = resultado.getString(7);

					tableroJugador1 = buscarInformacionTablero(idTablero, idJugadorEnemigo, idJugadorPropio,
							lugarJugadorPropio);
					
					tableroJugador2 = buscarInformacionTablero(idTablero, idJugadorPropio, idJugadorEnemigo,
							lugarJugadorPropio);

					nuevaPartida = new Partida(idPartida, tableroJugador1, tableroJugador2, usuarioPropio, usuarioEnemigo,
							movimientosTotales, isTerminada, jugadorGanador);
					
					listaPartidas.add(nuevaPartida);
				}

				return listaPartidas;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (conexion != null) {
						conexion.close();
					}
					if (query != null) {
						query.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} else {
			System.out.println("- Ha ocurrido un error buscando información sobre partidas");
		}

		return null;
	}
}
