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

package com.toxicnether.dotsx.desktop.nodes.operations.config.object;

/**
 * 
 * Esta clase contiene los atributos necesarios
 * para la utilización de tamaños predefinidos
 * durante la creación y uso de un tablero de
 * juego.
 * 
 * @author Abraham Lora
 *
 */
public class GameSizes {

	private int size, dotsize, spacing, linesize;
	
	/**
	 * 
	 * Este constructor debe contener los atributos
	 * mas importantes para la elaboración del GRID, donde
	 * estarán los Dots y donde se llevará a acabo toda la
	 * modalidad original.
	 * 
	 * @param size El tamaño de la proporción (px).
	 * @param dotsize El tamaño de las fichas (px).
	 * @param spacing El tamaño de los espacios entre
	 * fichas (px).
	 * @param linesize El tamaño de la linea (px).
	 */
	public GameSizes(int size, int dotsize, int spacing, int linesize){
		
		this.size = size;
		this.dotsize = dotsize;
		this.spacing = spacing;
		this.linesize = linesize;
		
	}

	/**
	 * @return El tamaño de la proporción.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return El tamaño de las fichas.
	 */
	public int getDotSize() {
		return dotsize;
	}
	
	/**
	 * @return El tamaño de los espacios entre
	 * fichas.
	 */
	public int getSpacing() {
		return spacing;
	}
	
	/**
	 * @return El tamaño de las lineas entre
	 * fichas.
	 */
	public int getLineSize() {
		return linesize;
	}
	
	/**
	 * @return El texto para la previsualización
	 * de la proporción.
	 */
	public String getVisual() {
		return size + "x" + size;
	}
	
}
