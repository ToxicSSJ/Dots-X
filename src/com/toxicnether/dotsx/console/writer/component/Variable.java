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

package com.toxicnether.dotsx.console.writer.component;

import java.io.Serializable;

/**
 * 
 * Esta clase se encarga de almacenar información
 * importante que sirve para ser atribuída a los
 * diferentes componentes de juego en el contexto
 * Consola. Esto con el fin de poder producir
 * eventos oportunos en caso de que se realize un
 * cambio directo sobre alguna de estas variables.
 * 
 * @author Abraham Lora
 *
 */
public class Variable implements Serializable {

	private static final long serialVersionUID = -7614274623559070033L;
	
	private String key;
	private String value;
	
	private boolean countable = false;
	private VariableAction action;
	
	/**
	 * 
	 * Este constructor debe ser definido con
	 * una llave y un valor que luego podrá
	 * ser cambiada con ciertos metodos.
	 * 
	 * @param key La llave.
	 * @param value El valor.
	 */
	public Variable(String key, String value) {
		
		this.key = key;
		this.value = value;
		
	}

	/**
	 * 
	 * Con este metodo se puede cambiar
	 * el valor definido para esta variable.
	 * Una vez cambiado dicho valor se producirá
	 * un evento en caso de que el Listener haya
	 * sido definido con anterioridad.
	 * 
	 * @param value El nuevo valor.
	 */
	public void change(String value) {
		
		this.value = value;
		
		if(action != null)
			action.change(value);
		
		return;
		
	}
	
	/**
	 * 
	 * Con este metodo se puede cambiar
	 * el valor definido para esta variable.
	 * Si existe un Listener para esta variable
	 * no será disparado.
	 * 
	 * @param value El nuevo valor.
	 */
	public void silentChange(String value) {
		
		this.value = value;
		return;
		
	}
	
	/**
	 * 
	 * Este metodo permite definir el Listener de la
	 * variable, para luego poder ser disparado en
	 * cada cambio directo realizado.
	 * 
	 * @param action El listener.
	 * @see VariableAction
	 */
	public void makeAction(VariableAction action) {
		
		this.action = action;
		return;
		
	}
	
	/**
	 * @return Devuelve la llave de la variable.
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * @return Devuelve el valor de la variable.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return Devuelve el listener actual de esta
	 * variable.
	 */
	public VariableAction getAction() {
		return action;
	}

	/**
	 * @return Permite conocer si la variable es
	 * contable.
	 */
	public boolean isCountable() {
		return countable;
	}

	/**
	 * 
	 * Este metodo permite saber si la variable es
	 * contable, con el fin de adicionar un numero
	 * al final de la misma que incremente a medida
	 * que se va generando.
	 * 
	 * @return Devuelve la instancia.
	 */
	public Variable makeCountable() {
		this.countable = true;
		return this;
	}
	
	/**
	 * @return Devuelve la llave y el valor separados de
	 * un punto y coma (;).
	 */
	@Override
	public String toString() {
		return key + ";" + value;
	}
	
	/**
	 * 
	 * Este metodo estatico permite generar una instancia
	 * de Variable en base a los datos previamente convertidos
	 * a texto. Devolverá null como no se reconozca el
	 * formato pre-establecido.
	 * 
	 * @param data La variable en forma de texto.
	 * @return La variable ya convertida a una instancia nueva.
	 */
	public static Variable fromString(String data) {
		
		if(data.contains(";"))
			return new Variable(data.split(";")[0], data.split(";")[1]);
			
		return null;
		
	}
	
}
