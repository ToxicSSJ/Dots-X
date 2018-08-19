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

package com.toxicnether.dotsx.desktop.bundle;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * 
 * Esta clase tipo utilidad tiene como función
 * ofrecer ciertos metodos utiles para la creación
 * rapida de Timers así como también ofrece el Sleep
 * para ser utilizado en cualquier contexto.
 * 
 * @author Abraham Lora
 *
 */
public class TimerUtility {
	
	/**
	 * @return Devuelve una nueva instancia de Timer que
	 * puede ser utilizada.
	 */
	public static Timer getTimer() {
		return new Timer();
	}
	
	/**
	 * 
	 * Este metodo permite dormir el Thread desde el cual
	 * se ejecutó.
	 * 
	 * @param millis Los milli-segundos de la espera.
	 */
	public static void sleep(long millis) {
		
		try {
			
			TimeUnit.MILLISECONDS.sleep(millis);
			
		} catch (InterruptedException e) {}
		
	}
	
}
