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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * 
 * Esta interfaz permite ejercer los calculos
 * de una función primaria del juego.
 * 
 * @author Abraham Lora
 *
 */
public interface DotCalculation {

	/**
	 * 
	 * Este metodo incluye las referencias de objetos
	 * importantes que componen el panel de juego para
	 * modificarse por medio de calculos realizados a
	 * partir de los mismos.
	 * 
	 * @param grid El panel principal que se muestra.
	 * @param vbox La caja vertical general del tablero.
	 * @param dots La lista de dots del juego.
	 * @param selection La selección de dots actuales.
	 * @param next El dot seleccionado siguiente.
	 * @return El resultado del calculo.
	 */
	public DotResult getResult(StackPane grid, VBox vbox, LinkedList<GameDot> dots, ObservableList<GameDot> selection, GameDot next);
	
	/**
	 * @return El nombre del calculo.
	 */
	public String getName();
	
}
