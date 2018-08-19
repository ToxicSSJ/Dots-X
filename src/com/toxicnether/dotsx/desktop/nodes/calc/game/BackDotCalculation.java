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

package com.toxicnether.dotsx.desktop.nodes.calc.game;

import java.util.LinkedList;

import com.toxicnether.dotsx.desktop.nodes.calc.DotCalculation;
import com.toxicnether.dotsx.desktop.nodes.calc.DotResult;
import com.toxicnether.dotsx.desktop.nodes.component.game.GameDot;

import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * 
 * Esta clase se encarga de procesar
 * el calculo para la acción de volver atrás
 * durante el tablero de juego.
 * 
 * @author Abraham Lora
 *
 */
public class BackDotCalculation implements DotCalculation {

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public DotResult getResult(StackPane grid, VBox vbox, LinkedList<GameDot> dots, ObservableList<GameDot> selection, GameDot next) {
		
		if(selection.size() < 2)
			return DotResult.CONTINUE;
		
		if(selection.equals(selection.get(0)))
			return DotResult.CONTINUE;
		
		if(selection.contains(next) && selection.get(selection.indexOf(selection.get(selection.size() - 1)) - 1).equals(next))
			return DotResult.BACK_ACTION;
		
		return DotResult.CONTINUE;
		
	}
	
	public String getName() {
		return "BackDotCalculation";
	}
	
	public static DotCalculation getInstance() {
		return new BackDotCalculation();
	}
	
}
