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

package com.toxicnether.dotsx.console.bundle.ansi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.toxicnether.dotsx.core.color.DotColorType;

public class AnsiConverter {
	
	public static final LinkedHashMap<DotColorType, String> COLOR_CODES = new LinkedHashMap<DotColorType, String>();
	
	static {
		
		COLOR_CODES.put(DotColorType.UNKNOW, "&x");
		COLOR_CODES.put(DotColorType.CUSTOM, "&k");
		COLOR_CODES.put(DotColorType.CYAN, "&q");
		COLOR_CODES.put(DotColorType.LIME, "&w");
		COLOR_CODES.put(DotColorType.YELLOW, "&e");
		COLOR_CODES.put(DotColorType.PURPLE, "&r");
		COLOR_CODES.put(DotColorType.RED, "&t");
		
	}
	
	public static String color(String str) {
		
		for(Map.Entry<DotColorType, String> entry : COLOR_CODES.entrySet())
			str = str.replaceAll(entry.getValue(), entry.getKey().getAnsi());
		
		return str.replaceAll("&k", "\u001B[0m");
		
	}
	
	public static String random() {
		
		List<String> colors = new ArrayList<String>(COLOR_CODES.values());
		return colors.get(ThreadLocalRandom.current().nextInt(2, colors.size()));
		
	}
	
	public static DotColorType getColorType(String code) {
		
		for(DotColorType type : COLOR_CODES.keySet())
			if(COLOR_CODES.get(type).equals(code))
				return type;
		
		return DotColorType.UNKNOW;
		
	}
	
}
