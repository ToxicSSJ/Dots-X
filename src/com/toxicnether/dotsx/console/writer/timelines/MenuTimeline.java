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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.toxicnether.dotsx.console.ConsoleAdapter;
import com.toxicnether.dotsx.console.bundle.TimeFormatter;
import com.toxicnether.dotsx.console.bundle.exception.ConsoleUpdateException;
import com.toxicnether.dotsx.console.reader.component.KeyAction;
import com.toxicnether.dotsx.console.writer.ConsoleTimeline;
import com.toxicnether.dotsx.console.writer.ConsoleWriter;
import com.toxicnether.dotsx.console.writer.component.Variable;
import com.toxicnether.dotsx.console.writer.layer.Layer;
import com.toxicnether.dotsx.console.writer.layer.group.BreakLayer;
import com.toxicnether.dotsx.console.writer.layer.group.VariableLayer;
import com.toxicnether.dotsx.console.writer.timelines.game.BomberTimeline;
import com.toxicnether.dotsx.console.writer.timelines.game.InfiniteTimeline;
import com.toxicnether.dotsx.console.writer.timelines.game.OriginalTimeline;
import com.toxicnether.dotsx.core.config.Configuration;
import com.toxicnether.dotsx.core.sound.type.SoundType;

public class MenuTimeline implements ConsoleTimeline {

	protected MenuTimeline instance;
	protected Variable selectedVar = null;
	
	protected Variable originalGame = new Variable("%1", "&w");
	protected Variable bomberGame = new Variable("%3", "&k");
	protected Variable configVar = new Variable("%4", "&k");
	protected Variable xGame = new Variable("%2", "&k");
	
	protected Variable dateVar = new Variable("%fecha", TimeFormatter.getCurrentTime());
	protected Variable dotsVar = new Variable("%dots", Configuration.get("dots"));
	protected Variable starsVar = new Variable("%stars", Configuration.get("stars"));
	
	protected LinkedList<Variable> hotkeys = Configuration.getHotkeys();
	
	@Override
	public void start() {
		
		instance = this;
		selectedVar = originalGame;
		
		GameTimeline oldGame = Configuration.getConsoleState();
		ConsoleAdapter.getAdapter().setWriter(new ConsoleWriter((short) 100));
		
		if(oldGame != null) {
			
			ConsoleAdapter.getAdapter().getWriter().stop();
			ConsoleAdapter.getAdapter().getWriter().clear();
			ConsoleAdapter.getAdapter().getReader().clear();
			
			ContinueTimeline.getModeInstance().initDefault(oldGame);
			return;
			
		}
		
		List<Variable> instructionsVars = new ArrayList<Variable>();
		
		instructionsVars.add(dateVar);
		instructionsVars.add(dotsVar);
		instructionsVars.add(starsVar);
		
		instructionsVars.addAll(hotkeys);
		
		VariableLayer instructions = new VariableLayer("Instructions", "assets/console/layers/INSTRUCTIONS").attach(instructionsVars);
		
		ConsoleAdapter.getAdapter().setWriter(new ConsoleWriter((short) 0));
		
		ConsoleAdapter.getAdapter().getWriter().addLayer(new BreakLayer("Divisor", 1));
		ConsoleAdapter.getAdapter().getWriter().addLayer(new Layer("Logo", "assets/console/layers/LOGO"));
		ConsoleAdapter.getAdapter().getWriter().addLayer(new BreakLayer("Divisor", 1));
		ConsoleAdapter.getAdapter().getWriter().addLayer(instructions);
		ConsoleAdapter.getAdapter().getWriter().addLayer(new BreakLayer("Divisor", 1));
		ConsoleAdapter.getAdapter().getWriter().addLayer(new VariableLayer("Menu", "assets/console/layers/MENU").attach(Arrays.asList(originalGame, bomberGame, xGame, configVar)));
		ConsoleAdapter.getAdapter().getWriter().addLayer(new BreakLayer("Divisor", 1));
		
		ConsoleAdapter.getAdapter().getWriter().update();
		ConsoleAdapter.getAdapter().getReader().clear();
		
		ConsoleAdapter.getAdapter().getReader().addAction(new KeyAction() {

			@Override
			public boolean action(String input) {
				
				if((input.startsWith(Configuration.get("hotkey_w")) || input.startsWith("1")) && selectedVar.equals(configVar)) {
					
					selectedVar.change("&k");
					xGame.change("&w");
					
					selectedVar = xGame;
					SoundType.CONSOLE_MENU_SELECT.getPlay().play();
					
					ConsoleAdapter.getAdapter().getWriter().update();
					return false;
					
				}
				
				if((input.startsWith(Configuration.get("hotkey_a")) || input.startsWith("2")) && !selectedVar.equals(configVar) && !selectedVar.equals(originalGame)) {
					
					selectedVar.change("&k");
					
					if(selectedVar.equals(xGame))
						selectedVar = originalGame;
					
					if(selectedVar.equals(bomberGame))
						selectedVar = xGame;
					
					selectedVar.change("&w");
					SoundType.CONSOLE_MENU_SELECT.getPlay().play();
					ConsoleAdapter.getAdapter().getWriter().update();
					return false;
					
				}
				
				if((input.startsWith(Configuration.get("hotkey_s")) || input.startsWith("4")) && !selectedVar.equals(configVar)) {
					
					selectedVar.change("&k");
					configVar.change("&w");
					
					selectedVar = configVar;
					SoundType.CONSOLE_MENU_SELECT.getPlay().play();
					ConsoleAdapter.getAdapter().getWriter().update();
					return false;
					
				}
				
				if((input.startsWith(Configuration.get("hotkey_d")) || input.startsWith("3")) && !selectedVar.equals(configVar) && !selectedVar.equals(bomberGame)) {
					
					selectedVar.change("&k");
					
					if(selectedVar.equals(xGame))
						selectedVar = bomberGame;
					
					if(selectedVar.equals(originalGame))
						selectedVar = xGame;
					
					selectedVar.change("&w");
					SoundType.CONSOLE_MENU_SELECT.getPlay().play();
					ConsoleAdapter.getAdapter().getWriter().update();
					return false;
					
				}
				
				if((input.startsWith(Configuration.get("hotkey_p")))) {
					
					SoundType.CONSOLE_MENU_SELECT_ENTER.getPlay().play();
					ConsoleAdapter.getAdapter().getReader().clear();
					
					selectedVar.change("&r");
					ConsoleAdapter.getAdapter().getWriter().update();
					
					sleep(200);
					
					if(selectedVar.equals(originalGame)) {
						
						ConsoleAdapter.getAdapter().getWriter().stop();
						ConsoleAdapter.getAdapter().getWriter().clear();
						ConsoleAdapter.getAdapter().getReader().clear();
						
						OriginalTimeline.getInstance().start();
						
					}
					
					if(selectedVar.equals(xGame)) {
						
						ConsoleAdapter.getAdapter().getWriter().stop();
						ConsoleAdapter.getAdapter().getWriter().clear();
						ConsoleAdapter.getAdapter().getReader().clear();
						
						InfiniteTimeline.getInstance().start();
						
					}
					
					if(selectedVar.equals(bomberGame)) {
						
						ConsoleAdapter.getAdapter().getWriter().stop();
						ConsoleAdapter.getAdapter().getWriter().clear();
						ConsoleAdapter.getAdapter().getReader().clear();
						
						BomberTimeline.getInstance().start();
						
					}
					
					if(selectedVar.equals(configVar)) {
						
						ConsoleAdapter.getAdapter().getWriter().stop();
						ConsoleAdapter.getAdapter().getWriter().clear();
						ConsoleAdapter.getAdapter().getReader().clear();
						
						ConfigurationTimeline.getInstance().start();
						
					}
					
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
		return new MenuTimeline();
	}
	
}
