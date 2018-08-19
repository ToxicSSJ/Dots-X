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

package com.toxicnether.dotsx.console.writer.layer;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.UUID;

import com.toxicnether.dotsx.console.writer.layer.model.LayerEvent;
import com.toxicnether.dotsx.core.util.FileReader;

public class Layer implements Serializable {

	private static final long serialVersionUID = 7133703130263301939L;

	private UUID uniqueID = UUID.randomUUID();
	
	private String name;
	private LinkedList<String> content;
	private LinkedList<LayerEvent> event;
	
	public Layer(String name, LinkedList<String> content) {
		
		this.name = name;
		this.content = content;
		
		this.event = new LinkedList<LayerEvent>();
		
	}

	public Layer(String name, String contentPath) {
		
		this.name = name;
		this.content = FileReader.readFile(contentPath);
		
		this.event = new LinkedList<LayerEvent>();
		
	}
	
	public String getName() {
		return name;
	}
	
	public Layer registerEvent(LayerEvent e) {
		event.add(e);
		return this;
	}
	
	public LinkedList<LayerEvent> getEvents(){
		return event;
	}
	
	public void clearEvents() {
		event.clear();
	}
	
	protected void setContent(LinkedList<String> content) {
		this.content = content;
	}
	
	public LinkedList<String> getContent() {
		return content;
	}

	public UUID getUniqueID() {
		return uniqueID;
	}
	
}
