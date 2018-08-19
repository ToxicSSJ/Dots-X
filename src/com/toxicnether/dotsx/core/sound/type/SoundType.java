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

import com.toxicnether.dotsx.core.sound.SoundPlay;

public enum SoundType {

	CONSOLE_MENU_SELECT("console/select.wav"),
	CONSOLE_MENU_SELECT_ENTER("console/select_enter.wav"),
	
	CONSOLE_MOVE("console/move.wav"),
	CONSOLE_INVALID_MOVE("console/invalid_move.wav"),
	
	CONSOLE_GAME_COUNTDOWN("console/countdown.wav"),
	CONSOLE_GAME_GO("console/go.wav"),
	
	CONSOLE_GAME_BOMBER_PREBOMB("console/game/bomber/prebomb.wav"),
	CONSOLE_GAME_BOMBER_BOMB("console/game/bomber/bomb1.wav"),
	CONSOLE_GAME_BOMBER_BOMB2("console/game/bomber/bomb2.wav"),
	
	CONSOLE_GAME_PRESS_START("console/press_start.wav"),
	CONSOLE_GAME_REMOVE("console/remove.wav"),
	CONSOLE_GAME_UNSELECT("console/unsel.wav"),
	CONSOLE_GAME_REPLACE("console/replace.wav"),
	
	CONSOLE_GAME_CRAFT("console/game_craft.wav"),
	CONSOLE_GAME_SUPER("console/super.wav"),
	
	CONSOLE_GAME_WIN("console/win.wav"),
	
	GRAPHIC_WOOSH("graphic/woosh.wav"),
	GRAPHIC_LETTER_HIT("graphic/letter_hit.wav"),
	GRAPHIC_DOT_BLIP("graphic/blip.mp3"),
	GRAPHIC_DOT_COIN("graphic/coin.mp3"),
	GRAPHIC_DOT_WINLEVEL("graphic/winlevel.mp3"),
	GRAPHIC_DOT_SUCESS("graphic/sucess.mp3")
	
	;
	
	private String audioPath;
	
	SoundType(String audioPath){
		this.audioPath = "assets/sounds/" + audioPath;
	}
	
	public SoundPlay getPlay() {
		return new SoundPlay(audioPath);
	}
	
	public String getAudioPath() {
		return audioPath;
	}
	
}
