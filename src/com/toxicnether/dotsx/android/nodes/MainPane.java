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

package com.toxicnether.dotsx.android.nodes;

import com.toxicnether.dotsx.android.nodes.type.SceneType;
import com.toxicnether.dotsx.desktop.DesktopAdapter;

import javafx.scene.layout.Pane;

/**
 * 
 * Este es el panel principal del sistema
 * DesktopAdapter, con este panel se piensa
 * manejar todos los componentes añadiendo
 * los subpaneles necesarios para jugar.
 * Nota: Esta desarrollado especificamente
 * como ambiente para Android.
 * 
 * @author Abraham Lora
 *
 */
public class MainPane extends Pane {
	
	private SceneType type = SceneType.SPLASH_SCENE;

	/**
	 * 
	 * Se construye el panel principal y se
	 * dan ciertos valores por defecto a todo
	 * el sistema.
	 * 
	 */
	public MainPane() {
		
		this.setWidth(DesktopAdapter.getDimension().getWidth());
		this.setHeight(DesktopAdapter.getDimension().getHeight());
		
		this.setMaxWidth(DesktopAdapter.getDimension().getWidth());
		this.setMaxHeight(DesktopAdapter.getDimension().getHeight());
		
		this.getChildren().add(type.getReferrer().getPane());
		
	}
	
	/**
	 * 
	 * Permite recibir el tipo de escena que actualmente
	 * está mostrandose en el panel.
	 * 
	 * @return El tipo de Escena.
	 * @see SceneType
	 */
	public SceneType getType() {
		return type;
	}
	
}
