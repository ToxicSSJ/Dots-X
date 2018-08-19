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
import com.toxicnether.dotsx.core.color.DotColorType;
import com.toxicnether.dotsx.desktop.DesktopAdapter;
import com.toxicnether.dotsx.desktop.nodes.component.DialogPane;
import com.toxicnether.dotsx.desktop.nodes.component.game.DotPowerUp;
import com.toxicnether.dotsx.desktop.nodes.component.game.GameDot;
import com.toxicnether.dotsx.desktop.nodes.component.powerups.ChangeDotPowerUp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class SelectDotTypeDialog extends StackPane {

	public SelectDotTypeDialog(GameDot change, DotPowerUp powerup, DialogPane dialog) {
		
		this.setPrefHeight(750);
		this.setPrefWidth(DesktopAdapter.getDimension().getHeight() / 2.5);
		
		this.setMaxHeight(750);
		this.setMaxWidth(DesktopAdapter.getDimension().getHeight() / 2.5);
		
		VBox pbox = new VBox(30);
		HBox box = new HBox(20);
		
		Label close = new Label("CANCELAR");
		Label select = new Label("Selecciona un color:");
		
		close.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 60));
		select.setFont(Font.loadFont(DotsX.getResource("fonts/Less-Bold.otf").toExternalForm(), 25));
		
		close.setTextFill(Color.ORANGERED);
		select.setTextFill(Color.WHITE);
		
		for(DotColorType type : DotColorType.values()) {
			
			if(type.equals(DotColorType.UNKNOW))
				continue;
			
			if(!((ChangeDotPowerUp) powerup).useCustom())
				if(type.equals(DotColorType.CUSTOM))
					continue;
			
			if(change.getColorType() == type)
				continue;
			
			GameDot dot = new GameDot(30);
			dot.setType(type);
			
			dot.setOnMouseClicked(e -> {
				
				Timeline swap = new Timeline();
				
				swap.getKeyFrames().add(new KeyFrame(Duration.ZERO, k -> change.delete(true)));
				swap.getKeyFrames().add(new KeyFrame(Duration.ZERO, k -> change.changeAttribute("selected", true)));
				swap.getKeyFrames().add(new KeyFrame(Duration.millis(155), k -> change.setType(type)));
				swap.getKeyFrames().add(new KeyFrame(Duration.millis(175), k -> change.appear(true)));
				swap.getKeyFrames().add(new KeyFrame(Duration.millis(355), k -> change.changeAttribute("selected", false)));
				
				swap.play();
				
				dialog.hide();
				powerup.setQuantity(powerup.getQuantity() - 1);
				return;
				
			});
			
			dot.setOnMouseEntered(e -> {
				dot.setEffect(new Glow(.5));
			});
			
			dot.setOnMouseExited(e -> {
				dot.setEffect(null);
			});
			
			box.getChildren().add(dot);
			
		}
		
		pbox.getChildren().add(close);
		pbox.getChildren().add(select);
		pbox.getChildren().add(box);
		
		getChildren().add(pbox);
		
		setAlignment(Pos.CENTER);
		StackPane.setAlignment(box, Pos.CENTER);
		
		close.setOnMouseClicked(e -> {
			dialog.hide();
		});
		
		close.setOnMouseEntered(e -> {
			close.setEffect(new Glow(.3));
		});
		
		close.setOnMouseExited(e -> {
			close.setEffect(null);
		});
		
	}
	
}
