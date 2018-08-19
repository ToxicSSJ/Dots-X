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

package com.toxicnether.dotsx.console.writer.layer.group;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.toxicnether.dotsx.console.bundle.exception.ConsoleUpdateException;
import com.toxicnether.dotsx.console.writer.component.Variable;
import com.toxicnether.dotsx.console.writer.component.VariableAction;
import com.toxicnether.dotsx.console.writer.layer.Layer;
import com.toxicnether.dotsx.console.writer.layer.model.LayerModifier;

public class VariableLayer extends Layer implements Serializable {
	
	private static final long serialVersionUID = -3375981238495536951L;

	protected transient VariableLayer instance;
	
	@SuppressWarnings("unchecked")
	protected LinkedList<String> unattached = (LinkedList<String>) getContent().clone();
	protected LinkedList<LayerModifier> modifiers = new LinkedList<LayerModifier>();
	
	protected Map<Variable, String> cacheSearch = new HashMap<Variable, String>();
	protected List<Variable> variables = new ArrayList<Variable>();
	
	protected boolean stop = false;
	
	public VariableLayer(String name, String contentPath) {
		
		super(name, contentPath);
		
		this.instance = this;
		return;
		
	}
	
	@SuppressWarnings("unchecked")
	public VariableLayer attach(List<Variable> variables) {
		
		this.variables = variables;
		LinkedList<String> content = (LinkedList<String>) unattached.clone();
		
		for(Variable var : variables) {
			
			var.makeAction(new VariableAction() {

				@Override
				public void change(String value) {
					
					update();
					return;
					
				}
				
			});
			
			for(int i = 0; i < content.size(); i++)
				content.set(i, var.isCountable() ? content.get(i).replaceFirst(var.getKey(), var.getValue()) :  content.get(i).replaceAll(var.getKey(), var.getValue()));
			
		}
		
		setContent(content);
		return this;
		
	}
	
	@SuppressWarnings("unchecked")
	public Entry<Integer, Integer> getPos(Variable variable) {
		
		LinkedList<String> content = (LinkedList<String>) unattached.clone();
		
		for(int i = 0; i < content.size(); i++)
			if(content.get(i).contains(variable.getKey()))
				return new AbstractMap.SimpleEntry<Integer, Integer>(i, content.get(i).indexOf(variable.getKey()) - 1);
		
		return null;
		
	}
	
	public VariableLayer addModifier(LayerModifier modifier) {
		
		modifiers.add(modifier);
		return this;
		
	}
	
	public void clearModifiers() {
		modifiers.clear();
	}
	
	@SuppressWarnings("unchecked")
	public VariableLayer update() {
		
		LinkedList<String> content = (LinkedList<String>) unattached.clone();
		
		for(LayerModifier m : modifiers)
			m.apply(content);
		
		for(Variable var : variables)
			for(int i = 0; i < content.size(); i++)
				content.set(i, content.get(i).replaceAll(var.getKey(), var.getValue()));
		
		setContent(content);
		return this;
		
	}

	public void sleep(long millis) {
		
		try { 
			
			TimeUnit.MILLISECONDS.sleep(millis);
			return;
			
		} catch (InterruptedException e) {
			throw new ConsoleUpdateException("Ha fallado la espera para la actualización.");
		}
		
	}
	
}
