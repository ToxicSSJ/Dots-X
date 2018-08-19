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

import java.util.LinkedList;

import com.toxicnether.dotsx.desktop.nodes.component.game.GameDot;

import javafx.collections.ObservableList;

/**
 * 
 * Esta interfaz es utilizada para generar
 * acciones de juego luego de haber hecho
 * calculos para cada acción.
 * 
 * @author Abraham Lora
 *
 */
public interface DotActioner {

	/**
	 * 
	 * Este metodo contiene las instancias necesarias
	 * para la ejecución de acciones y es utilizado
	 * para en su mayoría ejecutar funciones simples
	 * que alteren el tablero.
	 * 
	 * @param dots La lista de dots del tablero.
	 * @param selection La selección actual.
	 * @param next El siguiente dot.
	 * @param extra Las referencias a objetos extras
	 * que se desean utilizar.
	 */
	public void applyAction(LinkedList<GameDot> dots, ObservableList<GameDot> selection, GameDot next, Object...extra);
	
}
