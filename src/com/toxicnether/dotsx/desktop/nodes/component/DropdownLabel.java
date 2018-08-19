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

package com.toxicnether.dotsx.desktop.nodes.component;

import java.io.Serializable;
import java.util.TimerTask;

import com.toxicnether.dotsx.desktop.bundle.TimerUtility;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class DropdownLabel implements Serializable {

	private static final long serialVersionUID = 3155793457489305368L;

	private Label label;
	
	private boolean status = true;
	private boolean block = false;
	
	private String text;
	
	public DropdownLabel(Label label) {
		
		this.label = label;
		
	}
	
	public void fold(long millis) {
		
		new Thread() {
			
			@Override
			public void run() {
				
				TimerUtility.sleep(millis);
				
				if(block) {
					
					fold(millis);
					return;
					
				}
				
				Platform.runLater(() -> {
					fold();
				});
				
			}
			
		}.start();
		
	}
	
	public void fold() {
		
		if(!status)
			return;
		
		status = false;
		
		StringBuilder builder = new StringBuilder().append(this.label.getText());
		
		if(builder.toString().isEmpty())
			return;
		
		new Thread() {
				
			@Override
			public void run() {
					
				for(int i = builder.length() - 1; i >= 0; i--) {
					
					builder.setCharAt(i, ' ');
					TimerUtility.sleep(65);
					
					Platform.runLater(() -> {
						label.setText(builder.toString());
					});
					
				}
				
			}
			
		}.start();
		
	}
	
	public void dropdown() {
		
		if(status)
			return;
		
		status = true;
		
		StringBuilder builder = new StringBuilder();
		
		TimerUtility.getTimer().schedule(new TimerTask() {

			@Override
			public void run() {
				
				Platform.runLater(() -> {
					
					for(int i = 0; i < text.length(); i++) {
						
						builder.append(text.charAt(i));
						label.setText(builder.toString());
						TimerUtility.sleep(65);
						
					}
					
				});
				
			}
			
		}, 0);
		
	}
	
	public void change(String text) {
		
		this.text = text;
		
		if(status)
			this.label.setText(text);
		else
			dropdown();
		
	}
	
	public void block(boolean block) {
		
		this.block = block;
		return;
		
	}
	
	public Label getLabel() {
		return label;
	}
	
}
