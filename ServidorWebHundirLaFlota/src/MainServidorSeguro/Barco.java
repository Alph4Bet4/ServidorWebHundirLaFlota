/**
 * 
 */
package MainServidorSeguro;

import java.io.Serializable;

/**
 * 
 */
public class Barco implements Serializable {
	String posicion;
	boolean isGolpeado;

	/**
	 * @param posicion
	 * @param isGolpeado
	 */
	public Barco(String posicion, boolean isGolpeado) {
		this.posicion = posicion;
		this.isGolpeado = isGolpeado;
	}

	public String getPosicion() {
		return posicion;
	}

	public boolean isGolpeado() {
		return isGolpeado;
	}

	public void setGolpeado(boolean isGolpeado) {
		this.isGolpeado = isGolpeado;
	}

}
