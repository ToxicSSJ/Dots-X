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

package com.toxicnether.dotsx.desktop.nodes.calc.action;

import java.util.LinkedList;

import com.toxicnether.dotsx.core.sound.SoundPlay;
import com.toxicnether.dotsx.core.sound.type.SoundType;
import com.toxicnether.dotsx.desktop.nodes.calc.DotActioner;
import com.toxicnether.dotsx.desktop.nodes.component.game.GameDot;

import javafx.collections.ObservableList;

public class BackDotActioner implements DotActioner {

	@Override
	public void applyAction(LinkedList<GameDot> dots, ObservableList<GameDot> selection, GameDot next, Object...extras) {
		
		GameDot dot = selection.remove(selection.size() - 1);
		dot.changeAttribute("selected", false);
		
		SoundPlay tick = SoundType.GRAPHIC_DOT_BLIP.getPlay();
		
		tick.setRate(selection.size() * .2);
		tick.play();
		
		return;
		
	}
	
	public static DotActioner getInstance() {
		return new BackDotActioner();
	}

}
