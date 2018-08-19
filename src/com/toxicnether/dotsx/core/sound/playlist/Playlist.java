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

package com.toxicnether.dotsx.core.sound.playlist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.toxicnether.dotsx.core.sound.MusicPlay;
import com.toxicnether.dotsx.core.sound.type.MusicType;

public class Playlist implements Serializable {

	private static final long serialVersionUID = 1095430224303359895L;
	
	private List<MusicPlay> songs = new ArrayList<MusicPlay>();
	private PlaylistNextEvent event;
	
	private boolean loop = false;
	private int index = -1;
	
	public Playlist(MusicType...types) {
		
		for(MusicType type : types)
			songs.add(type.getPlay());
		
		Collections.shuffle(songs);
		
	}
	
	public void play(boolean loop) {
		
		this.loop = loop;
		
		for(MusicPlay song : songs) {
			
			song.attach(new Runnable() {

				@Override
				public void run() {
					
					next();
					return;
					
				}
				
			});
			
		}
		
		next();
		return;
		
	}
	
	public void setVolume(double value) {
		
		for(MusicPlay play : songs)
			play.setVolume(value);
		
	}
	
	public void stop() {
		
		index = -5;
		
		for(MusicPlay play : songs)
			play.stop();
		
	}
	
	private void next() {
		
		if(index == -5)
			return;
		
		index++;
		
		if(index >= songs.size())
			if(!loop)
				return;
			else
				index = 0;
		
		songs.get(index).play();
		
		if(event != null)
			event.next(songs.get(index).getAudioPath());
			
		return;
		
	}
	
	public void attach(PlaylistNextEvent e) {
		
		this.event = e;
		return;
		
	}
	
}
