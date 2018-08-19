package com.toxicnether.dotsx.core.component;

import java.util.UUID;

import com.toxicnether.dotsx.core.color.DotColorType;

/**
 * 
 * Esta clase funciona de manera abstracta como
 * un prototipo base para el funcionamiento de
 * los Dots del juego.
 * 
 * @author Abraham Lora
 *
 */
public abstract class Dot implements Token {

	private UUID uniqueID = UUID.randomUUID();
	private DotColorType color = DotColorType.UNKNOW;
	
	public Dot() {}
	
	/**
	 * @return La ID unica del Dot.
	 */
	public UUID getUniqueID() {
		return uniqueID;
	}

	/**
	 * 
	 * Este metodo permite redefiner la ID unica
	 * de un Dot.
	 * 
	 * @param uniqueID La ID unica.
	 */
	public void setUniqueID(UUID uniqueID) {
		this.uniqueID = uniqueID;
	}

	/**
	 * 
	 * Este metodo permite recibir el color
	 * de un Dot.
	 * 
	 * @return El color del dot.
	 * @see DotColorType
	 */
	public DotColorType getColor() {
		return color;
	}

	/**
	 * 
	 * Este metodo permite redefinir el color
	 * del Dot.
	 * 
	 * @param color El color del dot.
	 * @see DotColorType
	 */
	public void setColor(DotColorType color) {
		this.color = color;
	}
	
}
