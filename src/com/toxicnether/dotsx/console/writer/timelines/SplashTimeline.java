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

package com.toxicnether.dotsx.console.writer.timelines;

import java.util.concurrent.TimeUnit;

import com.toxicnether.dotsx.console.ConsoleAdapter;
import com.toxicnether.dotsx.console.bundle.exception.ConsoleUpdateException;
import com.toxicnether.dotsx.console.writer.ConsoleTimeline;
import com.toxicnether.dotsx.console.writer.ConsoleWriter;
import com.toxicnether.dotsx.console.writer.layer.group.AnimationLayer;
import com.toxicnether.dotsx.console.writer.layer.group.BreakLayer;
import com.toxicnether.dotsx.console.writer.layer.model.LayerEvent;

public class SplashTimeline implements ConsoleTimeline {

	protected SplashTimeline instance;

	@Override
	public void start() {
		
		instance = this;
		
		ConsoleAdapter.getAdapter().setWriter(new ConsoleWriter((short) 100));
		
		ConsoleAdapter.getAdapter().getWriter().addLayer(new BreakLayer("Divisor", 5));
		ConsoleAdapter.getAdapter().getWriter().addLayer(new AnimationLayer("SplashScreen", "assets/console/animations/splashscreen/", 25, false).autoFrameUp(200));
		ConsoleAdapter.getAdapter().getWriter().addLayer(new BreakLayer("Divisor", 3));
		ConsoleAdapter.getAdapter().getWriter().addLayer(
				new AnimationLayer("Loading", "assets/console/animations/loading/", 8, false)
				.autoFrameUp(420)
				.registerEvent(new LayerEvent() {

					@Override
					public void onFinish() {
						
						finish();
						return;
						
					}

					@Override
					public void onFrame() {}
					
				}));
		ConsoleAdapter.getAdapter().getWriter().addLayer(new BreakLayer("Divisor", 5));
		
		return;
		
	}

	@Override
	public void finish() {
		
		ConsoleAdapter.getAdapter().getWriter().stop();
		ConsoleAdapter.getAdapter().getWriter().clear();
		
		MenuTimeline.getInstance().start();
		
	}
	
	public void sleep(long millis) {
		
		try { 
			
			TimeUnit.MILLISECONDS.sleep(millis);
			return;
			
		} catch (InterruptedException e) {
			throw new ConsoleUpdateException("Ha fallado la espera para la actualización.");
		}
		
	}
	
	public static ConsoleTimeline getInstance() {
		return new SplashTimeline();
	}
	
}
