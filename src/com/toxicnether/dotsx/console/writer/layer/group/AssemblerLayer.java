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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import com.toxicnether.dotsx.console.ConsoleAdapter;
import com.toxicnether.dotsx.console.bundle.exception.ConsoleUpdateException;
import com.toxicnether.dotsx.console.writer.layer.Layer;
import com.toxicnether.dotsx.console.writer.layer.model.LayerEvent;

public class AssemblerLayer extends Layer implements Serializable {

	private static final long serialVersionUID = 3175993660115712565L;

	protected AssemblerLayer instance;
	
	private String contentPath;
	private Thread assemblerThread;
	
	protected LinkedList<String> content;
	protected Iterator<String> assembler;
	
	protected boolean stop = false;
	
	@SuppressWarnings("unchecked")
	public AssemblerLayer(String name, String contentPath) {
		super(name, contentPath);
		
		this.instance = this;
		
		this.content = new LinkedList<String>();
		this.assembler = ((LinkedList<String>) getContent().clone()).iterator();
		this.contentPath = contentPath;
		
		setContent(content);
		
	}

	public AssemblerLayer autoFrameUp(long millis) {
		
		assemblerThread = new Thread() {
			
			@Override
			public void run() {
				
				while(assembler.hasNext()) {
					
					if(stop)
						break;
					
				   instance.sleep(millis);
				   instance.frameUp();
				    
				   for(LayerEvent e : getEvents())
						e.onFrame();
				   
				}
				
				for(LayerEvent e : getEvents())
					e.onFinish();
				
			}
		    
		};
		
		assemblerThread.start();
		return this;
		
	}
	
	public void stopFrameUp() {
		
		stop = true;
		assemblerThread.interrupt();
		
		return;
		
	}
	
	public void frameUp(){
		
		content.add(assembler.next());
		
	    setContent(content);
	    ConsoleAdapter.getAdapter().getWriter().update();
		return;
	    
	}

	public Thread getAssemblerThread() {
		return assemblerThread;
	}
	
	public void sleep(long millis) {
		
		try { 
			
			TimeUnit.MILLISECONDS.sleep(millis);
			return;
			
		} catch (InterruptedException e) {
			throw new ConsoleUpdateException("Ha fallado la espera para la actualización.");
		}
		
	}

	public String getContentPath() {
		return contentPath;
	}
	
}
