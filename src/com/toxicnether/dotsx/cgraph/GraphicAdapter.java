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

package com.toxicnether.dotsx.cgraph;

import com.toxicnether.dotsx.DotsX;
import com.toxicnether.dotsx.cgraph.bundle.GraphicProvider;
import com.toxicnether.dotsx.core.game.GameAdapter;
import com.toxicnether.dotsx.desktop.DesktopAdapter;

import javafx.scene.text.Font;

/**
 * 
 * Este es el adaptador grafico que dispone
 * de diferentes funciones importantes para la
 * utilización del mismo en un entorno de
 * escritorio.
 * 
 * @author Abraham Lora
 *
 */
public class GraphicAdapter extends GameAdapter {
	
	/**
	 * 
	 * La fuente de 8Bits utilizada durante ciertas
	 * escenas del juego para mayor accesibilidad
	 * del sistema.
	 * 
	 */
	public static Font font;
	
	private GraphicProvider provider;
	
	/**
	 * 
	 * Con este metodo se inicializa el adaptador
	 * grafico.
	 * 
	 */
	@Override
	public void start() {
		
		font = Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 70);
		provider = DesktopAdapter.createProvider();
		
		provider.start();
		
	}

	/**
	 * 
	 * Con este metodo se ejecuta alguna función una
	 * vez se termina o se mata el proceso.
	 * 
	 */
	@Override
	public void exit() {}

}
