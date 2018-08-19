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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.toxicnether.dotsx.console.ConsoleAdapter;
import com.toxicnether.dotsx.console.bundle.exception.ConsoleUpdateException;
import com.toxicnether.dotsx.console.reader.component.KeyAction;
import com.toxicnether.dotsx.console.writer.ConsoleTimeline;
import com.toxicnether.dotsx.console.writer.ConsoleWriter;
import com.toxicnether.dotsx.console.writer.component.Variable;
import com.toxicnether.dotsx.console.writer.layer.group.BreakLayer;
import com.toxicnether.dotsx.console.writer.layer.group.VariableLayer;
import com.toxicnether.dotsx.core.config.Configuration;
import com.toxicnether.dotsx.core.sound.type.SoundType;
import com.toxicnether.dotsx.core.util.FileReader;

public class ContinueTimeline implements ConsoleTimeline {

	protected transient ContinueTimeline instance;
	
	protected GameTimeline oldGame;
	protected Variable selectedVar = null;
	
	protected Variable dateVar = new Variable("%fecha", "Desconocida");
	protected Variable modeVar = new Variable("%mode", "Desconocido");
	protected Variable scoreVar = new Variable("%score", "Desconocido");
	
	protected Variable yesVar = new Variable("%yes", "&w");
	protected Variable noVar = new Variable("%no", "&k");
	
	protected LinkedList<Variable> hotkeys = Configuration.getHotkeys();
	
	public void initDefault(GameTimeline game) {
		
		LinkedList<String> state = FileReader.readFile(Configuration.CONSOLE_STATE_FILE.getPath(), false);
		
		for(String k : state)
			if(k.startsWith("FECHA: "))
				dateVar.silentChange(k.replaceFirst("FECHA: ", ""));
			else if(k.startsWith("MODO: "))
				modeVar.silentChange(k.replaceFirst("MODO: ", ""));
			else if(k.startsWith("SCORE: "))
				scoreVar.silentChange(k.replaceFirst("SCORE: ", ""));
		
		this.oldGame = game;
		start();
		return;
		
	}
	
	@Override
	public void start() {
		
		instance = this;
		selectedVar = yesVar;
		
		List<Variable> variables = new ArrayList<Variable>();
		
		variables.add(yesVar);
		variables.add(noVar);
		variables.add(dateVar);
		variables.add(modeVar);
		variables.add(scoreVar);
		
		variables.addAll(hotkeys);
		
		VariableLayer layer = new VariableLayer("Continue", "assets/console/layers/CONTINUE").attach(variables);
		
		ConsoleAdapter.getAdapter().setWriter(new ConsoleWriter((short) 0));
		
		ConsoleAdapter.getAdapter().getWriter().addLayer(new BreakLayer("Divisor", 1));
		ConsoleAdapter.getAdapter().getWriter().addLayer(layer);
		ConsoleAdapter.getAdapter().getWriter().addLayer(new BreakLayer("Divisor", 1));
		
		ConsoleAdapter.getAdapter().getWriter().update();
		ConsoleAdapter.getAdapter().getReader().clear();
		
		ConsoleAdapter.getAdapter().getReader().addAction(new KeyAction() {

			@Override
			public boolean action(String input) {
				
				if(input.startsWith(Configuration.get("hotkey_a")) && !selectedVar.equals(yesVar)) {
					
					selectedVar.change("&k");
					
					selectedVar = yesVar;
					selectedVar.change("&w");
					
					SoundType.CONSOLE_MENU_SELECT.getPlay().play();
					
					layer.update();
					ConsoleAdapter.getAdapter().getWriter().update();
					return false;
					
				}
				
				if(input.startsWith(Configuration.get("hotkey_d")) && !selectedVar.equals(noVar)) {
					
					selectedVar.change("&k");
					
					selectedVar = noVar;
					selectedVar.change("&w");
					
					SoundType.CONSOLE_MENU_SELECT.getPlay().play();
					ConsoleAdapter.getAdapter().getWriter().update();
					return false;
					
				}
				
				if(input.startsWith(Configuration.get("hotkey_p"))) {
					
					SoundType.CONSOLE_MENU_SELECT_ENTER.getPlay().play();
					ConsoleAdapter.getAdapter().getReader().clear();
					
					selectedVar.change("&r");
					ConsoleAdapter.getAdapter().getWriter().update();
					
					sleep(200);
					
					if(selectedVar.equals(yesVar)) {
						
						ConsoleAdapter.getAdapter().getWriter().stop();
						ConsoleAdapter.getAdapter().getWriter().clear();
						ConsoleAdapter.getAdapter().getReader().clear();
						
						oldGame.show();
						return false;
						
					}
					
					ConsoleAdapter.getAdapter().getWriter().stop();
					ConsoleAdapter.getAdapter().getWriter().clear();
					ConsoleAdapter.getAdapter().getReader().clear();
					
					MenuTimeline.getInstance().start();
					return false;
					
				}
				
				ConsoleAdapter.getAdapter().getWriter().update();
				return true;
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
		return new ContinueTimeline();
	}
	
	public static ContinueTimeline getModeInstance() {
		return new ContinueTimeline();
	}
	
}
