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

package com.toxicnether.dotsx.android.nodes.type;

import com.toxicnether.dotsx.cgraph.bundle.AndroidReferrer;
import com.toxicnether.dotsx.android.nodes.operations.*;

/**
 * 
 * Este enumerador contiene todos los tipos
 * de escenas validos del sistema android.
 * 
 * @author Abraham Lora
 *
 */
public enum SceneType {

	SPLASH_SCENE(new SplashPane()),
	MENU_SCENE(new SplashPane()),
	
	;
	
	private AndroidReferrer referrer;
	
	/**
	 * 
	 * Este constructor pide un AndroidRefferer
	 * para ser cargado una vez se ejecute la escena
	 * por el panel principal.
	 * 
	 * @param referrer El referente.
	 */
	SceneType(AndroidReferrer referrer){
		
		this.referrer = referrer;
		
	}
	
	/**
	 * @return Devuelve el referente que pertenece
	 * a la escena actual.
	 */
	public AndroidReferrer getReferrer() {
		return referrer;
	}
	
}
