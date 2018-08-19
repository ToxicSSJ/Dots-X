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

import java.io.Serializable;

/**
 * 
 * Esta clase es utilizada a modo de almacen ya
 * que puede contener una llave y un valor que
 * son mas que nada utilizados como atributos
 * que pueden ser añadidos en una lista a un
 * componente.
 * 
 * @author Abraham Lora
 *
 * @param <T> El tipo de atribute que se utilizará.
 * @see GameDot
 */
public class DotAttribute<T> implements Serializable {

	private static final long serialVersionUID = 7390745861246749095L;
	
	private String key;
	private T value;
	
	/**
	 * 
	 * El constructor que recibe el parametro
	 * de la llave que necesitará el atributo
	 * y su respectivo valor.
	 * 
	 * @param key La llave
	 * @param value El valor cuyo tipo es especificado
	 * como el valor generico T.
	 */
	public DotAttribute(String key, T value) {
		
		this.key = key;
		this.value = value;
		
	}

	/**
	 * @return La llave del atributo en formato String.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return El valor del atributo en un valor Generico.
	 */
	@SuppressWarnings("unchecked")
	public <E> E getValue() {
		return (E) value;
	}

	/**
	 * 
	 * Este metodo redefine el valor actual del atributo.
	 * 
	 * @param value El valor por el cual se redefinirá el
	 * atributo.
	 */
	@SuppressWarnings("unchecked")
	public <E> void setValue(E value) {
		this.value = (T) value;
	}
	
	/**
	 * 
	 * Permite realizar una instacia de DotAttribute
	 * por medio de sus parametros base.
	 * 
	 * @param <E> El tipo de valor generico a utilizar.
	 * @param key La llave de los atributos.
	 * @param value El valor de los attributos.
	 * 
	 * @return Una instancia con los valores dados.
	 * @see DotAttribute
	 */
	public static <E> DotAttribute<E> makeInstance(String key, E value){
		return new DotAttribute<E>(key, value);
	}
	
}
