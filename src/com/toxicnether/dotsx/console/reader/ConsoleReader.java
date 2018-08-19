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

package com.toxicnether.dotsx.console.reader;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.toxicnether.dotsx.console.reader.component.KeyAction;

public class ConsoleReader {

	protected ConsoleReader instance;
	
	protected Runnable consoleRunnable;
	protected Thread consoleThread;
	
	protected Set<KeyAction> actions = new HashSet<KeyAction>();
	
	public ConsoleReader() {}
	
	public void start() {
		
		consoleThread = new Thread() {
			
			Scanner scanner = new Scanner(System.in);
			
			@Override
			public void run() {
				
				while(true) {
					
					String input = "";
					
					try { input = scanner.nextLine().toUpperCase();
					} catch(Exception e) {}
					
					if(input.length() > 1)
						for(char c : input.toCharArray())
								for(KeyAction action : actions)
									action.action(String.valueOf(c));
					else
						A : for(KeyAction action : actions)
								if(!action.action(input))
									break A;
					
				}
				
			}
			
		    
		};
		
		consoleThread.start();
		
	}
	
	@SuppressWarnings("deprecation")
	public void stop() {
		
		consoleThread.stop();
		consoleThread = null;
		
	}
	
	public void addAction(KeyAction action) {
		actions.add(action);
	}
	
	public void clear() {
		actions.clear();
	}
	
}
