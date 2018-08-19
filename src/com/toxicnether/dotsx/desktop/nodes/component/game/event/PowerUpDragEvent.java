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

package com.toxicnether.dotsx.desktop.nodes.component.game.event;

import com.toxicnether.dotsx.desktop.nodes.component.game.GameDot;

import javafx.geometry.Point2D;

public interface PowerUpDragEvent {

	/**
	 * 
	 * Se llama cuando se suelta el PowerUp sobre cualquier
	 * punto del Stage.
	 * 
	 * @param point El punto donde se colocó el PowerUp.
	 * @return <s>true</s> si se aplicó el powerup.
	 */
	public boolean fireDrag(Point2D point);
	
	/**
	 * 
	 * Se llama cuando se mueve el PowerUp sobre cualquier
	 * punto del Stage.
	 * 
	 * @param point El punto donde se movió el PowerUp.
	 * @return El GameDot de la posición actual, null en caso
	 * de no existir.
	 * @see GameDot
	 */
	public GameDot fireMove(Point2D point);
	
}
