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
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.toxicnether.dotsx.console.ConsoleAdapter;
import com.toxicnether.dotsx.console.bundle.ansi.AnsiConverter;
import com.toxicnether.dotsx.console.bundle.exception.ConsoleUpdateException;
import com.toxicnether.dotsx.console.reader.component.KeyAction;
import com.toxicnether.dotsx.console.writer.ConsoleTimeline;
import com.toxicnether.dotsx.console.writer.ConsoleWriter;
import com.toxicnether.dotsx.console.writer.component.Variable;
import com.toxicnether.dotsx.console.writer.layer.group.BreakLayer;
import com.toxicnether.dotsx.console.writer.layer.group.VariableLayer;
import com.toxicnether.dotsx.core.config.Configuration;
import com.toxicnether.dotsx.core.config.key.KeyType;
import com.toxicnether.dotsx.core.sound.type.SoundType;

public class ConfigurationTimeline implements ConsoleTimeline {

	protected transient ConfigurationTimeline instance;
	
	protected VariableLayer configuration;
	protected Variable selectedVar = null;
	
	protected Variable animations = new Variable("%animations", Configuration.get("animations").equals("true") ? "Si" : "No");
	protected Variable music = new Variable("%music", Configuration.get("music").equals("true") ? "Si" : "No");
	
	protected LinkedList<Variable> configs = new LinkedList<Variable>();
	protected LinkedList<Variable> hotkeys = Configuration.getHotkeys();
	
	protected List<Variable> variables = new ArrayList<Variable>();
	
	@Override
	public void start() {
		
		for(int i = 1; i <= 8; i++)
			configs.add(new Variable("%sel" + i, "&k"));
		
		instance = this;
		
		selectedVar = configs.getFirst();
		selectedVar.silentChange("&w");

		variables.add(animations);
		variables.add(music);
		
		variables.addAll(configs);
		variables.addAll(hotkeys);
		
		configuration = new VariableLayer("Continue", "assets/console/layers/CONFIGURATION").attach(variables);
		
		ConsoleAdapter.getAdapter().setWriter(new ConsoleWriter((short) 0));
		
		ConsoleAdapter.getAdapter().getWriter().addLayer(new BreakLayer("Divisor", 1));
		ConsoleAdapter.getAdapter().getWriter().addLayer(configuration);
		ConsoleAdapter.getAdapter().getWriter().addLayer(new BreakLayer("Divisor", 1));
		
		ConsoleAdapter.getAdapter().getWriter().update();
		ConsoleAdapter.getAdapter().getReader().clear();
		
		ConsoleAdapter.getAdapter().getReader().addAction(new KeyAction() {

			@SuppressWarnings("resource")
			@Override
			public boolean action(String input) {
				
				int index = configs.indexOf(selectedVar);
				
				if(input.startsWith(Configuration.get("hotkey_w")) && index - 1 >= 0) {
					
					selectedVar.change("&k");
					
					selectedVar = configs.get(index - 1);
					selectedVar.change("&w");
					
					SoundType.CONSOLE_MENU_SELECT.getPlay().play();
					
				}
				
				if(input.startsWith(Configuration.get("hotkey_s")) && index + 1 < configs.size()) {
					
					selectedVar.change("&k");
					
					selectedVar = configs.get(index + 1);
					selectedVar.change("&w");
					
					SoundType.CONSOLE_MENU_SELECT.getPlay().play();
					
				}
				
				if(input.startsWith(Configuration.get("hotkey_p"))) {
					
					SoundType.CONSOLE_GAME_CRAFT.getPlay().play();
					
					selectedVar.change("&r");
					ConsoleAdapter.getAdapter().getWriter().update();
					
					sleep(20);
					
					if(selectedVar.equals(configs.get(0))) {
						
						selectedVar.change("&w");
						
						if(Configuration.get("animations").equals("true"))
							Configuration.set("animations", "false");
						else
							Configuration.set("animations", "true");
						
						SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
						
					} else if(selectedVar.equals(configs.get(1))) {
						
						selectedVar.change("&w");
						
						if(Configuration.get("music").equals("true"))
							Configuration.set("music", "false");
						else
							Configuration.set("music", "true");
						
						SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
						
					} else if(selectedVar.equals(configs.getLast())){
						
						SoundType.CONSOLE_MENU_SELECT_ENTER.getPlay().play();
						
						ConsoleAdapter.getAdapter().getWriter().stop();
						ConsoleAdapter.getAdapter().getWriter().clear();
						ConsoleAdapter.getAdapter().getReader().clear();
						
						MenuTimeline.getInstance().start();
						return false;
						
					} else {
						
						String hotkey = index == 2 ? "hotkey_w" : index == 3 ? "hotkey_a" : index == 4 ? "hotkey_s" : index == 5 ? "hotkey_d" : "hotkey_p";
						SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
						
						System.out.println(AnsiConverter.color("&k>&w>&k> &kIngresa la nueva tecla para la acción &r" + hotkey.toUpperCase() + "&k: "));
						Scanner scanner = new Scanner(System.in);
						
						String key = scanner.nextLine();
						
						if(KeyType.isValidHotkey(key)) {
							
							Configuration.set(hotkey, key.toUpperCase());
							System.out.println(AnsiConverter.color("&k>&w>&k> kLa acción &r" + hotkey.toUpperCase() + " &kfue cambiada por &w'" + key.toUpperCase() + "' &kexitosamente!"));
							
							sleep(1000);
							
						} else {
							
							System.out.println(AnsiConverter.color("&k>&w>&k> &tLa tecla '" + key.toUpperCase() + "' es invalida!"));
							sleep(1000);
							
						}
						
					}
					
				}
				
				update();
				return false;
				
			}
			
			public void update() {
				
				LinkedList<Variable> cache = Configuration.getHotkeys();
				
				animations.silentChange(Configuration.get("animations").equals("true") ? "Si" : "No");
				music.silentChange(Configuration.get("music").equals("true") ? "Si" : "No");
				
				for(Variable hotkey : hotkeys)
					for(Variable ckey : cache)
						if(hotkey.getKey().equalsIgnoreCase(ckey.getKey()))
							hotkey.silentChange(ckey.getValue());
				
				configuration.update();
				ConsoleAdapter.getAdapter().getWriter().update();
				return;
				
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
		return new ConfigurationTimeline();
	}
	
	public static ConfigurationTimeline getModeInstance() {
		return new ConfigurationTimeline();
	}
	
}
