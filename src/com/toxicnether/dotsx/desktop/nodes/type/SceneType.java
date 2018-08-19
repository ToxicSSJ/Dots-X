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

package com.toxicnether.dotsx.desktop.nodes.type;

import com.toxicnether.dotsx.desktop.nodes.operations.*;
import com.toxicnether.dotsx.cgraph.bundle.GraphicReferrer;

import com.toxicnether.dotsx.desktop.nodes.operations.config.OriginalGameConfigurationPane;

/**
 * 
 * Este enumerador contiene los tipos
 * de escenas principales que componen el
 * sistema de escritorio.
 * 
 * @author Abraham Lora
 *
 */
public enum SceneType {

	SPLASH_SCENE(new SplashPane()),
	MENU_SCENE(new MenuPane()),
	
	CUSTOM_SCENE(),
	SELECTOR_SCENE(new GameSelectorPane()),
	CREDITS_SCENE(new CreditsPane()),
	ORIGINAL_GAME_CONFIG_SCENE(new OriginalGameConfigurationPane()),
	CONFIG_SCENE(new ConfigurationPane()),
	
	;
	
	private GraphicReferrer referrer;

	SceneType(){ this.referrer = null; }
	
	/**
	 * 
	 * Permite construir un tipo de escena
	 * con un referente concreto previamente
	 * inicializado.
	 * 
	 * @param referrer El referente que se mostrará.
	 */
	SceneType(GraphicReferrer referrer){ this.referrer = referrer; }
	
	/**
	 * 
	 * Con este metodo se puede obtener la instancia
	 * referente que compone el tipo de escena.
	 * Devuelve 'null' si se trata de una escena CUSTOM.
	 * 
	 * @return El referente.
	 * @see GraphicReferrer
	 */
	public GraphicReferrer getReferrer() {
		return referrer;
	}
	
}
