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
import com.toxicnether.dotsx.core.config.Configuration;
import com.toxicnether.dotsx.desktop.DesktopAdapter;
import com.toxicnether.dotsx.desktop.nodes.MainPane;
import com.toxicnether.dotsx.desktop.nodes.component.DialogPane;
import com.toxicnether.dotsx.desktop.nodes.component.GameMode;
import com.toxicnether.dotsx.desktop.nodes.operations.game.OriginalGamePane;
import com.toxicnether.dotsx.desktop.nodes.operations.game.OriginalGamePane.OriginalGameSizes;
import com.toxicnether.dotsx.desktop.nodes.type.SceneType;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * 
 * Esta clase contiene los componentes que son
 * utilizados para el manejo del modo de juego
 * Original, que es representado durante la selección
 * del modo de juego.
 * 
 * @author Abraham Lora
 *
 */
public class OriginalGameMode extends GameMode {

	private static final long serialVersionUID = 2311057261700491649L;
	
	private HBox box = new HBox(20);
	private VBox vbox = new VBox(30);
	
	private Label logo = new Label();
	private Label icon = new Label();
	
	public OriginalGameMode() {
		super(new Image(DotsX.getResource("images/present/orpresent.jpg").toExternalForm()));

		box.setMinWidth(0);
		box.setMinHeight(0);
		
		box.setPrefHeight(0);
		box.setPrefWidth(0);
		
		icon.setFont(Font.loadFont(DotsX.getResource("fonts/Gem.ttf").toExternalForm(), 90));
		icon.setTextFill(Color.CYAN);
		
		icon.setText("q");
		
		logo.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 90));
		logo.setTextFill(Color.CYAN);
		
		logo.setText("MODO ORIGINAL");
		box.setAlignment(Pos.TOP_CENTER);
		
		HBox.setMargin(logo, new Insets(25, 0, 0, 0));
		
		box.getChildren().addAll(logo, icon);
		vbox.getChildren().addAll(box);
		
		this.getChildren().add(vbox);
		
		vbox.setMaxWidth(DesktopAdapter.getDimension().getWidth() / 2);
		vbox.setMaxHeight(icon.getHeight() + logo.getHeight());
		
		box.setMaxWidth(DesktopAdapter.getDimension().getWidth() / 2);
		box.setMaxHeight(icon.getHeight() + logo.getHeight());
		
		StackPane.setAlignment(vbox, Pos.TOP_CENTER);
		
		Timeline rotate = new Timeline();
		
		rotate.setCycleCount(Timeline.INDEFINITE);
		rotate.getKeyFrames().add(new KeyFrame(Duration.millis(5), e -> icon.rotateProperty().set(icon.getRotate() + 1)));
		
		this.getFade().addListener(e -> {
			
			boolean fade = this.getFade().getValue();
			
			if(fade) {
				
				rotate.play();
				return;
				
			}
			
			rotate.pause();
			return;
			
		});
		
	}
	
	@Override
	public void start() {
		
		if(DialogPane.openedPanes.size() > 0)
			return;
		
		OriginalGamePane pane = new OriginalGamePane(OriginalGameSizes.getSize(Configuration.get("orsize")));
		MainPane.getInstance().change(SceneType.CUSTOM_SCENE, pane);
		
		return;
		
	}
	
	@Override
	public void config() {
		
		if(DialogPane.openedPanes.size() > 0)
			return;
		
		MainPane.getInstance().change(SceneType.ORIGINAL_GAME_CONFIG_SCENE);
		return;
		
	}
	
	@Override
	public void hide() {
		
		Timeline timeline = new Timeline();
		
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(120), new KeyValue(this.scaleXProperty(), 0)));
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(120), new KeyValue(this.scaleYProperty(), 0)));
		
		timeline.play();
		
	}
	
	public static GameMode makeInstance() {
		return new OriginalGameMode();
	}
	
}
