/*
 * 
 *	         :::::::::   :::::::: ::::::::::: ::::::::  :::    :::    
 *	       :+:    :+: :+:    :+:    :+:    :+:    :+: :+:    :+:     
 *	      +:+    +:+ +:+    +:+    +:+    +:+         +:+  +:+       
 *	     +#+    +:+ +#+    +:+    +#+    +#++:++#++   +#++:+         
 *	    +#+    +#+ +#+    +#+    +#+           +#+  +#+  +#+         
 *	   #+#    #+# #+#    #+#    #+#    #+#    #+# #+#    #+#         
 *	  #########   ########     ###     ########  ###    ###    
 * 
 * This program is free software: you can redistribute it and/or modify
 *
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.toxicnether.dotsx.core.color;

import java.io.Serializable;

import javafx.scene.paint.Color;

/**
 * 
 * Este enumerador tiene los colores importantes
 * que son utilizados para todo el proyecto y todos
 * los sistemas.
 * 
 * @author Abraham Lora
 *
 */
public enum DotColorType implements Serializable {

	UNKNOW(Color.TRANSPARENT, "\u001B[30m"),
	CUSTOM(Color.WHITE, "\u001B[0m"),
	
	RED(Color.RED, "\u001B[31m"),
	CYAN(Color.CYAN, "\u001B[36m"),
	LIME(Color.LIME, "\u001B[32m"),
	YELLOW(Color.YELLOW, "\u001B[33m"),
	PURPLE(Color.BLUEVIOLET, "\u001B[35m"),
	
	;
	
	private Color color;
	private String ansi;
	
	/**
	 * 
	 * Este es el constructor del tipo
	 * de color.
	 * 
	 * @param color El color del dot.
	 * @param ansi El color tipo ANSI del dot.
	 * 
	 */
	DotColorType(Color color, String ansi){
		
		this.color = color;
		this.ansi = ansi;
		
	}
	
	/**
	 * 
	 * Este metodo devuelve el color
	 * del dot.
	 * 
	 * @return El color del dot.
	 * @see Color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * 
	 * Este metodo devuelve el color
	 * del dot en formato ANSI.
	 * 
	 * @return Devuelve el codigo ANSI
	 * para entornos Consola.
	 */
	public String getAnsi() {
		return ansi;
	}
	
	/**
	 * 
	 * Este metodo permite recibir el tipo de color
	 * en base a un texto con el nombre literal.
	 * 
	 * @param id El texto con el nombre literal.
	 * @return
	 */
	public static DotColorType getType(String id) {
		
		for(DotColorType type : DotColorType.values())
			if(type.name().toLowerCase().contains(id.toLowerCase()))
				return type;
		
		return DotColorType.UNKNOW;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir los tipos de colores
	 * en un arreglo con los colores de JavaFX.
	 * 
	 * @return Los colores de los Dots.
	 */
	public static Color[] group() {
		
		int index = 0;
		Color[] result = new Color[DotColorType.values().length];
		
		for(DotColorType type : DotColorType.values())
			result[index++] = type.getColor();
		
		return result;
		
	}
	
}
