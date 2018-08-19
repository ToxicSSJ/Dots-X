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

package com.toxicnether.dotsx.desktop.nodes.operations;

import com.toxicnether.dotsx.DotsX;
import com.toxicnether.dotsx.cgraph.bundle.GraphicReferrer;
import com.toxicnether.dotsx.core.sound.playlist.Playlist;
import com.toxicnether.dotsx.core.sound.type.MusicType;
import com.toxicnether.dotsx.desktop.DesktopAdapter;
import com.toxicnether.dotsx.desktop.nodes.MainPane;
import com.toxicnether.dotsx.desktop.nodes.component.GameMode;
import com.toxicnether.dotsx.desktop.nodes.component.gamemodes.BomberGameMode;
import com.toxicnether.dotsx.desktop.nodes.component.gamemodes.OriginalGameMode;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * 
 * Esta clase permite definir un selector
 * al juego.
 * 
 * @author Abraham Lora
 *
 */
public class GameSelectorPane extends StackPane implements GraphicReferrer {

	/**
	 * 
	 * Esta variable permite saber si ya se ha
	 * inicializado el panel con anterioridad.
	 * 
	 */
	protected static boolean started = false;
	
	protected transient final JFXPanel fxPanel = new JFXPanel();
	protected transient StackPane pane;
	
	protected Playlist playlist = new Playlist(MusicType.GRAPHIC_MENU_MUSIC1,
											   MusicType.GRAPHIC_MENU_MUSIC2,
											   MusicType.GRAPHIC_MENU_MUSIC3,
											   MusicType.GRAPHIC_MENU_MUSIC4,
											   MusicType.GRAPHIC_MENU_MUSIC5,
											   MusicType.GRAPHIC_MENU_MUSIC6);
	
	private Rectangle background;
	private HBox optionsBox;
	
	private ObservableList<GameMode> gamemodes = FXCollections.observableArrayList(OriginalGameMode.makeInstance(),
																				   BomberGameMode.makeInstance());
	
	public GameSelectorPane() {
		
		this.pane = this;
		this.background = new Rectangle();
		
		this.background.setStrokeWidth(0);
		
		this.background.setWidth(DesktopAdapter.getDimension().getWidth());
		this.background.setHeight(DesktopAdapter.getDimension().getHeight());
		
		this.background.setFill(Paint.valueOf("black"));
		
		this.optionsBox = new HBox();
		this.optionsBox.setAlignment(Pos.CENTER);
		this.optionsBox.setSpacing(0);
		
		this.pane.setMaxWidth(DesktopAdapter.getDimension().getWidth());
		this.pane.setMaxHeight(DesktopAdapter.getDimension().getHeight());
		
		this.pane.setMinWidth(DesktopAdapter.getDimension().getWidth());
		this.pane.setMinHeight(DesktopAdapter.getDimension().getHeight());
		
		this.pane.setPrefWidth(DesktopAdapter.getDimension().getWidth());
		this.pane.setPrefHeight(DesktopAdapter.getDimension().getHeight());
		
		this.pane.setMaxSize(DesktopAdapter.getDimension().getWidth(), DesktopAdapter.getDimension().getHeight());
		this.pane.getStylesheets().add(DotsX.getResource("css/config.css").toExternalForm());
		
		this.optionsBox.getChildren().addAll(gamemodes);
		super.setBackground(new Background(new BackgroundFill(Color.web("black"), CornerRadii.EMPTY, Insets.EMPTY)));
		
	}
	
	@Override
	public void start() {
		
		if(started == true)
			return;
		
		started = true;
		
		this.optionsBox.getChildren().addAll();
		
		this.pane.getChildren().addAll(MainPane.bgVideo, optionsBox);
		StackPane.setAlignment(optionsBox, Pos.CENTER);
		
		listen();
		
	}
	
	public void listen() {
		
		for(GameMode mode : gamemodes) {
			
			mode.setOnMouseEntered(e -> {
				
				mode.appear();
				return;
				
			});
			
			mode.setOnMouseExited(e -> {
				
				mode.disappear();
				return;
				
			});
			
		}
		
	}
	
	@Override
	public StackPane getPane() {
		return pane;
	}
	
}