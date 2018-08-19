package com.toxicnether.dotsx.core.component;

/**
 * 
 * Esta interfaz contiene las funciones
 * primarias que debe contener un Dot
 * en el juego.
 * 
 * @author Abraham Lora
 *
 */
public interface Token {

	/**
	 * 
	 * Este metodo permite ejecutar
	 * funciones tipo Callback luego
	 * de aparecer el Token.
	 * 
	 */
	public void appear();
	
	/**
	 * 
	 * Este metodo permite ejecutar
	 * funciones tipo Callback luego
	 * de destruir el Token.
	 * 
	 */
	public void destroy();
	
}
