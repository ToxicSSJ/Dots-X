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

import com.toxicnether.dotsx.DotsX;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class MessageDialog extends StackPane implements Serializable {
	
	private static final long serialVersionUID = -5456705572226655013L;
	
	private Label message;
	private Rectangle background;
	
	public MessageDialog(double width, double height) {
		
		Font msgFont = Font.loadFont(DotsX.getResource("fonts/Less-Bold.otf").toExternalForm(), 40);
		
		this.message = new Label();
		
		this.background = new Rectangle();
		
		this.background.setArcHeight(30);
		this.background.setArcWidth(30);
		this.background.setFill(Paint.valueOf("white"));
		
		this.message.setFont(msgFont);
		this.message.setText("");
		
		this.setPrefHeight(height);
		this.setPrefWidth(width);
		
		this.setMaxHeight(height);
		this.setMaxWidth(width);
		
		this.setMinHeight(height);
		this.setMaxWidth(width);
		
	}
	
	public void setMessage(String message) {
		
		this.message.setText(message);
		
	}
	
}
