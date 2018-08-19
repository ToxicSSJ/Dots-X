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

package com.toxicnether.dotsx.core.sound.type;

import com.toxicnether.dotsx.core.sound.MusicPlay;

public enum MusicType {

	CONSOLE_ORIGINAL_MUSIC1("music/original/console1.mp3"),
	CONSOLE_ORIGINAL_MUSIC2("music/original/console2.mp3"),
	CONSOLE_ORIGINAL_MUSIC3("music/original/console3.mp3"),
	
	CONSOLE_BOMBER_MUSIC1("music/bomber/bomber1.mp3"),
	CONSOLE_BOMBER_MUSIC2("music/bomber/bomber2.mp3"),
	CONSOLE_BOMBER_MUSIC3("music/bomber/bomber3.mp3"),
	
	GRAPHIC_MENU_MUSIC1("music/graphic/menu/menu1.mp3"),
	GRAPHIC_MENU_MUSIC2("music/graphic/menu/menu2.mp3"),
	GRAPHIC_MENU_MUSIC3("music/graphic/menu/menu3.mp3"),
	GRAPHIC_MENU_MUSIC4("music/graphic/menu/menu4.mp3"),
	GRAPHIC_MENU_MUSIC5("music/graphic/menu/menu5.mp3"),
	GRAPHIC_MENU_MUSIC6("music/graphic/menu/menu6.mp3"),
	
	;
	
	private String audioPath;
	
	MusicType(String audioPath){
		this.audioPath = "assets/sounds/" + audioPath;
	}
	
	public MusicPlay getPlay() {
		return new MusicPlay(audioPath);
	}
	
	public String getAudioPath() {
		return audioPath;
	}
	
}
