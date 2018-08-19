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

package com.toxicnether.dotsx.console.writer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import com.toxicnether.dotsx.console.bundle.ansi.AnsiConverter;
import com.toxicnether.dotsx.console.bundle.exception.ConsoleUpdateException;
import com.toxicnether.dotsx.console.writer.layer.Layer;

public class ConsoleWriter {
	
	protected ConsoleWriter instance;
	
	protected LinkedList<Layer> layers = new LinkedList<Layer>();
	protected short updaterMillis;
	
	protected Runnable consoleRunnable;
	protected Thread consoleThread;
	
	public ConsoleWriter(short updaterMillis) {
		
		this.instance = this;
		this.updaterMillis = updaterMillis;
		
	}
	
	public void addLayer(Layer layer) {
		layers.add(layer);
	}
	
	public void setLayer(Layer layer, int index) {
		layers.set(index, layer);
	}
	
	public void removeLayer(Layer layer) {
		layers.remove(layer);
	}
	
	public void clearLayers() {
		layers.clear();
	}
	
	public void start(short updaterMillis) {
		
		this.updaterMillis = updaterMillis;
		start();
		
	}
	
	public void start() {
		
		if(consoleThread != null)
			return;
		
		consoleThread = new Thread() {
			
			@Override
			public void run() {
				
				while(true) {
					
				    instance.sleep(updaterMillis);
					update();
				    
				}
				
			}
		    
		};
		
		consoleThread.start();
		
	}
	
	@SuppressWarnings("deprecation")
	public void stop() {
		
		if(consoleThread != null)
			consoleThread.stop();
		
		consoleThread = null;
		
	}
	
	@SuppressWarnings("deprecation")
	public void stop(long millis) {
		
		new Thread() {
			
			@Override
			public void run() {
				
				instance.sleep(millis);
				
				if(consoleThread != null)
					consoleThread.stop();
				
				consoleThread = null;
				
				
			}
			
		}.start();
		
	}
	
	public void update() {
		
		clear();
		
	    for(Layer layer : layers)
	    	for(String line : layer.getContent())
	    		System.out.println(AnsiConverter.color(line));
		
	}
	
	public void clear() {
		
		try {
			
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			return;
			
		} catch (InterruptedException | IOException e) {
			
			if(consoleThread != null)
				throw new ConsoleUpdateException("Ha fallado la actualización de la consola.");
			
		}
		
	}
	
	public void sleep(long millis) {
		
		try { 
			
			TimeUnit.MILLISECONDS.sleep(millis);
			return;
			
		} catch (InterruptedException e) {
			
			if(consoleThread != null)
				throw new ConsoleUpdateException("Ha fallado la espera para la actualización.");
			
		}
		
	}
	
}
