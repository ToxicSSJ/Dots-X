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

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.toxicnether.dotsx.console.ConsoleAdapter;
import com.toxicnether.dotsx.console.bundle.NumberFormatter;
import com.toxicnether.dotsx.console.bundle.exception.ConsoleUpdateException;
import com.toxicnether.dotsx.console.reader.component.KeyAction;
import com.toxicnether.dotsx.console.writer.ConsoleTimeline;
import com.toxicnether.dotsx.console.writer.ConsoleWriter;
import com.toxicnether.dotsx.console.writer.component.Variable;
import com.toxicnether.dotsx.console.writer.layer.group.BreakLayer;
import com.toxicnether.dotsx.console.writer.layer.group.VariableLayer;
import com.toxicnether.dotsx.core.config.Configuration;
import com.toxicnether.dotsx.core.sound.type.SoundType;

public class WinTimeline implements ConsoleTimeline {

	protected transient WinTimeline instance;
	
	protected Variable modeVar = new Variable("%mode", "Desconocido");
	protected Variable scoreVar = new Variable("%score", "Desconocido");
	protected Variable starsVar = new Variable("%stars", "Desconocido");
	
	public void initDefault(String mode, int score, int stars) {
		
		modeVar.silentChange(mode);
		scoreVar.silentChange(String.valueOf(score));
		starsVar.silentChange(String.valueOf(stars));
		
		if(NumberFormatter.isNumber(Configuration.get("dots")))
			Configuration.set("dots", String.valueOf(score + Integer.parseInt(Configuration.get("dots"))));
		else
			Configuration.set("dots", String.valueOf(score));
			
		if(NumberFormatter.isNumber(Configuration.get("stars")))
			Configuration.set("stars", String.valueOf(stars + Integer.parseInt(Configuration.get("stars"))));
		else
			Configuration.set("stars", String.valueOf(stars));
		
		start();
		return;
		
	}
	
	@Override
	public void start() {
		
		instance = this;
		
		ConsoleAdapter.getAdapter().setWriter(new ConsoleWriter((short) 0));
		
		ConsoleAdapter.getAdapter().getWriter().addLayer(new BreakLayer("Divisor", 1));
		ConsoleAdapter.getAdapter().getWriter().addLayer(new VariableLayer("Continue", "assets/console/layers/WIN").attach(Arrays.asList(modeVar, scoreVar, starsVar)));
		ConsoleAdapter.getAdapter().getWriter().addLayer(new BreakLayer("Divisor", 1));
		
		SoundType.CONSOLE_GAME_WIN.getPlay().play();
		
		ConsoleAdapter.getAdapter().getWriter().update();
		ConsoleAdapter.getAdapter().getReader().clear();
		
		ConsoleAdapter.getAdapter().getReader().addAction(new KeyAction() {

			@Override
			public boolean action(String input) {
				
				SoundType.CONSOLE_GAME_PRESS_START.getPlay().play();
				
				ConsoleAdapter.getAdapter().getWriter().stop();
				ConsoleAdapter.getAdapter().getWriter().clear();
				ConsoleAdapter.getAdapter().getReader().clear();
				
				MenuTimeline.getInstance().start();
				return false;
			}
			
		});
		
		return;
		
	}

	@Override
	public void finish() {
		
		ConsoleAdapter.getAdapter().getWriter().stop();
		ConsoleAdapter.getAdapter().getWriter().clear();
		
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
		return new WinTimeline();
	}
	
	public static WinTimeline getModeInstance() {
		return new WinTimeline();
	}
	
}
