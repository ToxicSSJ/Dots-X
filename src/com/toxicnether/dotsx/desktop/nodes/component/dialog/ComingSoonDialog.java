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

package com.toxicnether.dotsx.desktop.nodes.component.dialog;

import com.toxicnether.dotsx.DotsX;
import com.toxicnether.dotsx.core.sound.type.SoundType;
import com.toxicnether.dotsx.core.util.ColorUtil;
import com.toxicnether.dotsx.desktop.DesktopAdapter;
import com.toxicnether.dotsx.desktop.nodes.component.DialogPane;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class ComingSoonDialog extends StackPane {

	public ComingSoonDialog(DialogPane dialog) {
		
		this.setPrefHeight(750);
		this.setPrefWidth(DesktopAdapter.getDimension().getHeight() / 2.5);
		
		this.setMaxHeight(750);
		this.setMaxWidth(DesktopAdapter.getDimension().getHeight() / 2.5);
		
		this.getStylesheets().add(DotsX.getResource("css/config.css").toExternalForm());
		
		Rectangle rect = new Rectangle(750, 5);
		VBox box = new VBox(50);
		
		rect.setFill(Color.TRANSPARENT);
		box.setPrefWidth(730);
		box.setMaxWidth(730);
		
		box.setPrefHeight(730);
		box.setMaxHeight(730);
		box.setAlignment(Pos.CENTER);
		
		Font titleFont = Font.loadFont(DotsX.getResource("fonts/Lazer84.ttf").toExternalForm(), 35);
		Font font = Font.loadFont(DotsX.getResource("fonts/Less-Bold.otf").toExternalForm(), 30);
		
		Label title = new Label("Proximamente");
		Label volver = new Label("Volver");
		
		title.setFont(titleFont);
		volver.setFont(font);
		
		title.setTextFill(ColorUtil.getGradient(Color.ORANGE, Color.ORANGERED));
		volver.setTextFill(Color.WHITESMOKE);
		
		volver.setOnMouseClicked(e -> {
			
			dialog.hide();
			SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
			
		});
		
		box.getChildren().addAll(title, volver, rect);
		box.setAlignment(Pos.CENTER);
		
		stylishOption(false, volver);
		this.getChildren().addAll(box);
		
		setAlignment(Pos.CENTER);
		StackPane.setAlignment(box, Pos.CENTER);
		
	}
	
	public void stylishOption(boolean green, Label...labels) {
		
		for(Label label : labels) {
			
			label.setOnMouseEntered(e -> {
				
				Stop[] stops = new Stop[] { new Stop(0, green ? Color.LIMEGREEN : Color.ORANGERED), new Stop(1, green ? Color.LIME : Color.RED)};
		        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
				
				label.setTextFill(gradient);
				SoundType.CONSOLE_MOVE.getPlay().play();
				
			});
			
			label.setOnMouseExited(e -> label.setTextFill(Color.WHITESMOKE));
			
		}
		
	}
	
	public HBox getConfigComponent(Node cname, Node cexe) {
		
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(cname, cexe);
		
		return box;
		
	}
	
	public HBox getConfigComponent(int spacing, Node cname, Node cexe) {
		
		HBox box = new HBox(spacing);
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(cname, cexe);
		
		return box;
		
	}
	
}
