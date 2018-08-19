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

package com.toxicnether.dotsx.core.util;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

import com.toxicnether.dotsx.DotsX;

public class FileReader {
	
	public static LinkedList<String> readFile(String path){
		return readFile(path, true);
	}
	
	public static LinkedList<String> readFile(String path, boolean resource){
		
		LinkedList<String> content = new LinkedList<String>();
		
		try {
			
			Scanner scanner = resource ? new Scanner(DotsX.getResourceAsStream(path), "UTF-8") : new Scanner(new File(path), "UTF-8");
			
			while(scanner.hasNextLine())
				content.add(scanner.nextLine());
			
			scanner.close();
			
		} catch (Exception e) { }
		
		return content;
		
	}
	
}
