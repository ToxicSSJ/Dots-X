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

package com.toxicnether.dotsx.cgraph.bundle;

import javafx.scene.layout.Pane;

/**
 * 
 * Esta interfaz contiene metodos importantes
 * para la iniciación de escenas o paneles
 * para ser añadidos al panel principal en un
 * entorno Android.
 * 
 * @author Abraham Lora
 *
 */
public interface AndroidReferrer {

	/**
	 * 
	 * Este metodo permite definir los objetos por
	 * defecto que componen una escena. Estos datos
	 * serán procesados por la instancia y en base a ellos
	 * se producirán cambios importantes durante
	 * la ejecución.
	 * 
	 * @param res Los objetos que componen la escena.
	 */
	public void setDefaults(Object...res);
	
	/**
	 * 
	 * Este metodo permite inicializar el referente
	 * que en base a lo predefinido mostrará ciertos
	 * componentes a la vista del usuario por medio
	 * de metodos que se utilizan en el Framework.
	 * 
	 */
	public void start();
	
	/**
	 * 
	 * Este metodo devuelve el panel funcional ya producido
	 * por la escena y listo para ser cargado.
	 * 
	 * @return El panel funcional de la escena.
	 */
	public Pane getPane();
	
	
}
