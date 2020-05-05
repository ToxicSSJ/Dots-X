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

package com.toxicnether.dotsx.desktop.nodes;

import com.toxicnether.dotsx.DotsX;
import com.toxicnether.dotsx.core.config.Configuration;
import com.toxicnether.dotsx.core.sound.playlist.PlaylistNextEvent;
import com.toxicnether.dotsx.core.util.ColorUtil;
import com.toxicnether.dotsx.desktop.DesktopAdapter;
import com.toxicnether.dotsx.desktop.nodes.component.DropdownLabel;
import com.toxicnether.dotsx.desktop.nodes.operations.MenuPane;
import com.toxicnether.dotsx.desktop.nodes.type.SceneType;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * 
 * Esta clase contiene las funciones necesarias
 * para el uso de los diferentes paneles del
 * sistema.
 * 
 * @author Abraham Lora
 *
 */
public class MainPane extends Pane {
	
	protected static MainPane instance;
	
	public static MediaView bgVideo;
	public static MediaPlayer bgPlayer;
	
	public static HBox songBox;
	public static ImageView diskGif;
	public static Label songName;
	
	private SceneType type = SceneType.SPLASH_SCENE;

	/**
	 * 
	 * Este constructor realiza las inicializaciones
	 * principales de las variables estaticas que componen
	 * la clase y que son utilizadas para mas de 1 escena.
	 * 
	 */
	public MainPane() {
		
		instance = this;
		
		bgVideo = new MediaView();
		bgPlayer = new MediaPlayer(new Media(DotsX.getResource("videos/bg.mp4").toExternalForm()));
		
		bgPlayer.setMute(true);
		bgPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		bgPlayer.setAutoPlay(true);
		 
		bgVideo.setMediaPlayer(bgPlayer);
		bgVideo.setPreserveRatio(false);
		 
		bgVideo.setFitWidth(DesktopAdapter.getDimension().getWidth());
		bgVideo.setFitHeight(DesktopAdapter.getDimension().getHeight());
		
		diskGif = new ImageView(new Image(DotsX.getResourceAsStream("images/gif/disc.gif")));
		diskGif.setPreserveRatio(false);

		diskGif.setFitHeight(35);
		diskGif.setFitWidth(35);
		
		diskGif.maxHeight(35);
		diskGif.maxWidth(35);
		
		diskGif.prefHeight(35);
		diskGif.prefWidth(35);
		
		songName = new Label();
		
		songBox = new HBox();
		songBox.setMaxWidth(DesktopAdapter.getDimension().getWidth());
		songBox.setMaxHeight(diskGif.getFitHeight());
		
		songBox.setAlignment(Pos.TOP_LEFT);
		songBox.paddingProperty().set(new Insets(5, 0, 0, 5));
		songBox.setSpacing(7);
		
		DropdownLabel dropdown = new DropdownLabel(MainPane.songName);
		
		MenuPane.playlist.attach(new PlaylistNextEvent() {

			@Override
			public void next(String audioPath) {
				
				dropdown.change(getSongName(audioPath));
				MainPane.songName.setTextFill(ColorUtil.randomGradient());
				
				dropdown.fold(8 * 1000);
				
			}
			
			public String getSongName(String audioPath) {
				
				if(audioPath.contains("menu1"))
					return "A Meeting of Genres - Two StepSynthwave";
				
				if(audioPath.contains("menu2"))
					return "Odyssey Stratosphere - Trance140bpm";
				
				if(audioPath.contains("menu3"))
					return "Ascent to the Station - Synthwave";
				
				if(audioPath.contains("menu4"))
					return "Retro Future Nights - Synthwave";
				
				if(audioPath.contains("menu5"))
					return "Edge of Tomorrow - Synthwave";
				
				if(audioPath.contains("menu6"))
					return "Invading the 80s - Synthwave";
				
				return "Desconocida";
				
			}
			
		});
		
		if(!Boolean.valueOf(Configuration.get("splashlogo")))
			type = SceneType.MENU_SCENE;
		
		this.setWidth(DesktopAdapter.getDimension().getWidth());
		this.setHeight(DesktopAdapter.getDimension().getHeight());
		
		this.setMaxWidth(DesktopAdapter.getDimension().getWidth());
		this.setMaxHeight(DesktopAdapter.getDimension().getHeight());
		
		this.getChildren().add(type.getReferrer().getPane());
		type.getReferrer().start();
		
	}
	
	/**
	 * 
	 * Este metodo permite intercambiar una escena
	 * de cierto tipo colocando un panel customizado.
	 * 
	 * @param type El tipo de escena que se colocará.
	 * @param custom El panel que se usará.
	 */
	public void change(SceneType type, Pane...custom) {
		
		this.type = type;
		this.getChildren().clear();
		
		if(type.equals(SceneType.CUSTOM_SCENE)) {
			
			this.getChildren().add(custom[0]);
			return;
			
		}
		
		this.getChildren().add(type.getReferrer().getPane());
		type.getReferrer().start();
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir el tipo de esecena
	 * actual del sistema.
	 * 
	 * @return La escena actual del sistema.
	 * @see SceneType
	 */
	public SceneType getType() {
		return type;
	}
	
	/**
	 * 
	 * Este metodo estatico permite recibir la instancia
	 * creada del panel principal.
	 * 
	 * @return
	 */
	public static MainPane getInstance() {
		return instance;
	}
	
}
