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

package com.toxicnether.dotsx.console;

import java.util.Arrays;
import java.util.LinkedList;

import com.toxicnether.dotsx.DotsX;
import com.toxicnether.dotsx.console.reader.ConsoleReader;
import com.toxicnether.dotsx.console.writer.ConsoleTimeline;
import com.toxicnether.dotsx.console.writer.ConsoleWriter;
import com.toxicnether.dotsx.console.writer.timelines.MenuTimeline;
import com.toxicnether.dotsx.console.writer.timelines.SplashTimeline;
import com.toxicnether.dotsx.core.config.Configuration;
import com.toxicnether.dotsx.core.game.GameAdapter;

public class ConsoleAdapter extends GameAdapter {
	
	private ConsoleWriter writer;
	private ConsoleReader reader;
	
	private LinkedList<ConsoleTimeline> timelines = getPack();
	
	@Override
	public void start() {
		
		if(writer != null)
			return;
		
		reader = new ConsoleReader();
		reader.start();
		
		if(Configuration.get("animations").equals("true"))
			timelines.getFirst().start();
		else
			timelines.getLast().start();
		
	}

	@Override
	public void exit() {
		
	}

	public void setWriter(ConsoleWriter writer) {
		if(this.writer != null)
			this.writer.stop();
		
		this.writer = writer;
	}
	
	public ConsoleWriter getWriter() {
		return writer;
	}
	
	public ConsoleReader getReader() {
		return reader;
	}
	
	private LinkedList<ConsoleTimeline> getPack() {
		
		return new LinkedList<ConsoleTimeline>(
				
				Arrays.asList(
								
								SplashTimeline.getInstance(),
								MenuTimeline.getInstance()
								
							 )
				
				);
		
	}
	
	public static ConsoleAdapter getAdapter() {
		return (ConsoleAdapter) DotsX.getAdapter();
	}
	
}
