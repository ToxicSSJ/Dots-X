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

package com.toxicnether.dotsx.desktop.nodes.calc;

import com.toxicnether.dotsx.desktop.nodes.calc.action.*;

/**
 * 
 * Este enumerador contiene diversos
 * tipos de datos que son retornados durante
 * un calculo del tablero.
 * 
 * @author Abraham Lora
 * @see DotCalculation
 */
public enum DotResult {

	CONTINUE,
	WAIT,
	
	BREAK(true),
	STOP(true),
	
	BACK_ACTION(BackDotActioner.getInstance())
	
	;
	
	private DotActioner actioner;
	
	private boolean stop = false;
	private boolean ignore = true;
	
	DotResult() {}
	
	/**
	 * 
	 * Este constructor permite definir el
	 * resultado como una acción que debe parar el
	 * proceso de calculos siguientes.
	 * 
	 * @param stop Si se deben parar los calculos.
	 */
	DotResult(boolean stop) { this.stop = stop; }
    
	/**
	 * 
	 * Este constructor permite definir una
	 * acción como muestra de un resultado que
	 * debe ser ejecutada luego de devolver
	 * el calculo.
	 * 
	 * @param actioner La acción que se debe
	 * ejecutar.
	 */
    DotResult(DotActioner actioner) {
		
		this.actioner = actioner;
		this.ignore = false;
		
	}
    
    /**
     * @return Devuelve la acción del resultado.
     */
    public DotActioner getActioner() {
    	return actioner;
    }
	
    /**
     * @return <strong>true</strong> si se desea
     * parar el proceso.
     */
    public boolean stop() {
    	return stop;
    }
    
    /**
     * @return <strong>true</strong> si es necesario
     * ignorar el resultado.
     */
    public boolean ignore() {
    	return ignore;
    }
    
}
