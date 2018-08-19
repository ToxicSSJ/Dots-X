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
import java.util.LinkedList;

import com.toxicnether.dotsx.console.writer.layer.Layer;

public class BreakLayer extends Layer implements Serializable {

	private static final long serialVersionUID = 476099406261298818L;

	protected BreakLayer instance;
	
	protected boolean stop = false;
	
	public BreakLayer(String name, int lines) {
		
		super(name, "assets/console/layers/VOID");
		
		this.instance = this;
		
		LinkedList<String> content = new LinkedList<String>();
		
		for(int i = 0; i < lines; i++)
			content.add("");
		
		setContent(content);
		return;
		
	}
	
}