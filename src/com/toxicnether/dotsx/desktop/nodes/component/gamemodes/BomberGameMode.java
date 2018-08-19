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

package com.toxicnether.dotsx.desktop.nodes.component.gamemodes;

import com.toxicnether.dotsx.DotsX;
import com.toxicnether.dotsx.desktop.DesktopAdapter;
import com.toxicnether.dotsx.desktop.nodes.component.DialogPane;
import com.toxicnether.dotsx.desktop.nodes.component.GameMode;
import com.toxicnether.dotsx.desktop.nodes.component.dialog.ComingSoonDialog;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * 
 * Esta clase contiene los componentes que son
 * utilizados para el manejo del modo de juego
 * Bomba, que es representado durante la selección
 * del modo de juego.
 * 
 * @author Abraham Lora
 *
 */
public class BomberGameMode extends GameMode {

	private static final long serialVersionUID = 3158992947576270431L;

	private HBox box = new HBox(20);
	
	private Label logo = new Label();
	private Label icon = new Label();
	
	public BomberGameMode() {
		super(new Image(DotsX.getResource("images/present/bmpresent.jpg").toExternalForm()));
		
		box.setMinWidth(0);
		box.setMinHeight(0);
		
		box.setPrefHeight(0);
		box.setPrefWidth(0);
		
		icon.setFont(Font.loadFont(DotsX.getResource("fonts/Gem.ttf").toExternalForm(), 90));
		icon.setTextFill(Color.DARKORANGE);
		
		icon.setText("E");
		
		logo.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 90));
		logo.setTextFill(Color.DARKORANGE);
		
		logo.setText("MODO BOMBA");
		box.setAlignment(Pos.TOP_CENTER);
		
		HBox.setMargin(logo, new Insets(25, 0, 0, 0));
		
		box.getChildren().addAll(logo, icon);
		this.getChildren().add(box);
		
		box.setMaxWidth(DesktopAdapter.getDimension().getWidth() / 2);
		box.setMaxHeight(icon.getHeight() + logo.getHeight());
		
		StackPane.setAlignment(box, Pos.TOP_CENTER);
		
	}
	
	@Override
	public void start() {
		
		if(DialogPane.openedPanes.size() > 0)
			return;
		
		DialogPane dialog = new DialogPane();
		dialog.setItems(new ComingSoonDialog(dialog));
		dialog.show();
		
	}
	
	@Override
	public void config() {
		
		if(DialogPane.openedPanes.size() > 0)
			return;
		
		DialogPane dialog = new DialogPane();
		dialog.setItems(new ComingSoonDialog(dialog));
		dialog.show();
		
	}
	
	@Override
	public void hide() {
		
		Timeline timeline = new Timeline();
		
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(120), new KeyValue(this.scaleXProperty(), 0)));
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(120), new KeyValue(this.scaleYProperty(), 0)));
		
		timeline.play();
		
	}
	
	public static GameMode makeInstance() {
		return new BomberGameMode();
	}
	
}
