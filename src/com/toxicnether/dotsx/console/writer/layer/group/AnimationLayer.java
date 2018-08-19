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
import java.util.concurrent.TimeUnit;

import com.toxicnether.dotsx.console.ConsoleAdapter;
import com.toxicnether.dotsx.console.bundle.exception.ConsoleUpdateException;
import com.toxicnether.dotsx.console.writer.layer.Layer;
import com.toxicnether.dotsx.console.writer.layer.model.LayerEvent;
import com.toxicnether.dotsx.core.util.FileReader;

public class AnimationLayer extends Layer implements Serializable {

	private static final long serialVersionUID = -4504169086673680536L;

	protected AnimationLayer instance;
	
	private int pos = 1;
	private int maxpos = -1;
	private boolean infinite = false;
	
	private String contentPath;
	private Thread animationThread;
	
	protected boolean stop = false;
	
	public AnimationLayer(String name, String contentPath, int maxpos, boolean infinite) {
		
		super(name, contentPath + "01");
		
		this.instance = this;
		
		this.infinite = infinite;
		this.maxpos = maxpos;
		this.contentPath = contentPath;
		
	}
	
	public AnimationLayer autoFrameUp(long millis) {
		
		animationThread = new Thread() {
			
			@Override
			public void run() {
				
				ConsoleAdapter.getAdapter().getWriter().update();
				
				for(LayerEvent e : getEvents())
					e.onFrame();
				
				instance.sleep(millis);
				
				while(true) {
					
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
		
		animationThread.start();
		return this;
		
	}
	
	public void stopFrameUp() {
		
		stop = true;
		animationThread.interrupt();
		
		return;
		
	}
	
	public void frameUp(){
		
		if(pos + 1 > maxpos && !infinite) {
			
			stopFrameUp();
			return;
			
		}
		
		if(pos + 1 > maxpos)
			pos = 0;
		
	    setContent(FileReader.readFile(contentPath + String.format("%02d", ++pos)));
	    ConsoleAdapter.getAdapter().getWriter().update();
		return;
	    
	}

	public Thread getAnimationThread() {
		return animationThread;
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
