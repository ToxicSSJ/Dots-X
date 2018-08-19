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
import com.toxicnether.dotsx.core.config.Configuration;
import com.toxicnether.dotsx.core.sound.MusicPlay;
import com.toxicnether.dotsx.core.sound.SoundPlay;
import com.toxicnether.dotsx.core.sound.type.SoundType;
import com.toxicnether.dotsx.core.util.ColorUtil;
import com.toxicnether.dotsx.desktop.DesktopAdapter;
import com.toxicnether.dotsx.desktop.nodes.MainPane;
import com.toxicnether.dotsx.desktop.nodes.component.DialogPane;
import com.toxicnether.dotsx.desktop.nodes.component.GameField;
import com.toxicnether.dotsx.desktop.nodes.operations.FinishGamePane;
import com.toxicnether.dotsx.desktop.nodes.operations.MenuPane;
import com.toxicnether.dotsx.desktop.nodes.operations.game.GamePane;
import com.toxicnether.dotsx.desktop.nodes.type.SceneType;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class PauseGameDialog extends StackPane {

	public PauseGameDialog(GamePane game, DialogPane dialog) {
		
		this.setPrefHeight(450);
		this.setPrefHeight(DesktopAdapter.getDimension().getHeight() / 2.5);
		
		this.setMaxWidth(750);
		this.setMaxWidth(DesktopAdapter.getDimension().getHeight() / 2.5);
		
		this.getStylesheets().add(DotsX.getResource("css/config.css").toExternalForm());
		
		Rectangle rect = new Rectangle(750, 5);
		VBox box = new VBox(30);
		
		rect.setFill(Color.TRANSPARENT);
		box.setPrefWidth(730);
		box.setMaxWidth(730);
		
		box.setPrefHeight(730);
		box.setMaxHeight(730);
		box.setAlignment(Pos.CENTER);
		
		Font titleFont = Font.loadFont(DotsX.getResource("fonts/Lazer84.ttf").toExternalForm(), 35);
		Font font = Font.loadFont(DotsX.getResource("fonts/Less-Bold.otf").toExternalForm(), 30);
		
		Label title = new Label("Opciones");
		
		Label guardar = new Label("Guardar Partida");
		Label terminar = new Label("Terminar Partida");
		Label abandonar = new Label("Abandonar Partida");
		Label volumen = new Label("Volumen");
		Label continuar = new Label("Continuar");
		
		title.setFont(titleFont);
		guardar.setFont(font);
		terminar.setFont(font);
		abandonar.setFont(font);
		volumen.setFont(font);
		continuar.setFont(font);
		
		title.setTextFill(ColorUtil.getGradient(Color.ORANGE, Color.ORANGERED));
		abandonar.setTextFill(Color.WHITESMOKE);
		guardar.setTextFill(Color.WHITESMOKE);
		terminar.setTextFill(Color.WHITESMOKE);
		volumen.setTextFill(Color.WHITESMOKE);
		continuar.setTextFill(Color.WHITESMOKE);
		
		box.getChildren().addAll(title, guardar, terminar, abandonar, volumen, rect, continuar);
		this.getChildren().addAll(box);
		
		VBox.setMargin(title, new Insets(15));
		stylishOption(false, guardar, terminar, abandonar, volumen, continuar);
		
		abandonar.setOnMouseClicked(e -> {
			
			dialog.hide();
			MainPane.getInstance().change(SceneType.MENU_SCENE);
			
			MainPane.songBox.getChildren().remove(0);
			SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
			return;
			
		});
		
		terminar.setOnMouseClicked(e -> {
			
			dialog.hide();
			
			FinishGamePane pane = new FinishGamePane(game);
			MainPane.getInstance().change(SceneType.CUSTOM_SCENE, pane);
			
			SoundType.CONSOLE_GAME_WIN.getPlay().play();
			return;
			
		});
		
		volumen.setOnMouseClicked(e -> {
			
			Label vtitle = new Label("Volumen");
			VBox vbox = new VBox(30);
			
			Label volver = new Label("Volver");
			
			vtitle.setFont(Font.loadFont(DotsX.getResource("fonts/Lazer84.ttf").toExternalForm(), 35));
			vtitle.setTextFill(ColorUtil.getGradient(Color.ORANGE, Color.ORANGERED));
			
			SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
			
			Label musicVolume = new Label("Volumen de la Musica: ");
			Label soundsVolume = new Label("Volumen de los Sonidos: ");
			
			Slider mVolume = new Slider();
			Slider sVolume = new Slider();
			
			musicVolume.setTextFill(Color.WHITESMOKE);
			soundsVolume.setTextFill(Color.WHITESMOKE);
			volver.setTextFill(Color.WHITESMOKE);
			
			volver.setFont(font);
		    musicVolume.setFont(font);
			soundsVolume.setFont(font);
			
			stylishOption(false, volver);
			
			mVolume.setValue(Double.valueOf(Configuration.get("musicvolume")) * 100);
			sVolume.setValue(Double.valueOf(Configuration.get("soundsvolume")) * 100);
			
			mVolume.valueProperty().addListener((a, b, c) -> {
				
				Double volume = mVolume.getValue() / 100;
				
				Configuration.set("musicvolume", String.valueOf(volume));
				MenuPane.playlist.setVolume(volume);
				MusicPlay.setGlobalVolume(volume);
				return;
				
			});
			
			sVolume.valueProperty().addListener((a, b, c) -> {
				
				Double volume = sVolume.getValue() / 100;
				
				Configuration.set("soundsvolume", String.valueOf(volume));
				SoundPlay.setGlobalVolume(volume);
				return;
				
			});
			
			volver.setOnMouseClicked(k -> {
				
				getChildren().clear();
				box.getChildren().add(box.getChildren().size() - 1, rect);
				
				getChildren().addAll(box);
				SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
				
				setAlignment(Pos.CENTER);
				StackPane.setAlignment(box, Pos.CENTER);
				
			});
			
			vbox.setAlignment(Pos.CENTER);
			
			vbox.getChildren().addAll(vtitle,
					                  getConfigComponent(musicVolume, mVolume),
									  getConfigComponent(soundsVolume, sVolume),
									  rect,
									  volver);
			
			getChildren().clear();
			getChildren().addAll(vbox);
			
			setAlignment(Pos.CENTER);
			StackPane.setAlignment(vbox, Pos.CENTER);
			
		});
		
		guardar.setOnMouseClicked(e -> {
			
			if(!game.saveName().equals("$null")) {
				
				Configuration.saveGraphicGame(game.saveName(), game);
				Label label = new Label("¡Partida Guardada!");
				
				label.setTextFill(Color.LIME);
				label.setFont(font);
				
				getChildren().clear();
				getChildren().addAll(label, rect);
				
				Timeline back = new Timeline();
				
				back.getKeyFrames().add(new KeyFrame(Duration.millis(2500), h -> {
					
					getChildren().clear();
					box.getChildren().add(box.getChildren().size() - 1, rect);
					
					getChildren().addAll(box);
					SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
					
					setAlignment(Pos.CENTER);
					StackPane.setAlignment(box, Pos.CENTER);
					
				}));
				
				back.play();
				return;
				
			}
			
			Label gtitle = new Label("GUARDAR");
			VBox gbox = new VBox(30);
			
			Label volver = new Label("Volver");
			Label oguardar = new Label("Guardar");
			
			gtitle.setFont(Font.loadFont(DotsX.getResource("fonts/Lazer84.ttf").toExternalForm(), 35));
			gtitle.setTextFill(ColorUtil.getGradient(Color.ORANGE, Color.ORANGERED));
			
			SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
			
			Label partidaNombre = new Label("Nombre: ");
			GameField nombreField = new GameField();
			
			volver.setTextFill(Color.WHITESMOKE);
			oguardar.setTextFill(Color.WHITESMOKE);
			partidaNombre.setTextFill(Color.WHITESMOKE);
			
			volver.setFont(font);
			oguardar.setFont(font);
			partidaNombre.setFont(font);
			
			stylishOption(false, volver);
			stylishOption(true, oguardar);
			
			volver.setOnMouseClicked(k -> {
				
				getChildren().clear();
				box.getChildren().add(box.getChildren().size() - 1, rect);
				
				getChildren().addAll(box);
				SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
				
				setAlignment(Pos.CENTER);
				StackPane.setAlignment(box, Pos.CENTER);
				
			});
			
			oguardar.setOnMouseClicked(k -> {
				
				if(nombreField.getText().isEmpty())
					return;
				
				if(nombreField.getText().length() > 20)
					return;
				
				Configuration.saveGraphicGame(nombreField.getText(), game);
				Label label = new Label("¡Partida Guardada!");
				
				label.setTextFill(Color.LIME);
				label.setFont(font);
				
				getChildren().clear();
				getChildren().addAll(label, rect);
				
				Timeline back = new Timeline();
				
				back.getKeyFrames().add(new KeyFrame(Duration.millis(2500), h -> {
					
					getChildren().clear();
					box.getChildren().add(box.getChildren().size() - 1, rect);
					
					getChildren().addAll(box);
					SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
					
					setAlignment(Pos.CENTER);
					StackPane.setAlignment(box, Pos.CENTER);
					
				}));
				
				back.play();
				
			});
			
			gbox.setAlignment(Pos.CENTER);
			gbox.getChildren().addAll(gtitle,
					                  getConfigComponent(partidaNombre, nombreField),
									  rect,
									  getConfigComponent(20, oguardar, volver));
			
			getChildren().clear();
			getChildren().addAll(gbox);
			
			setAlignment(Pos.CENTER);
			StackPane.setAlignment(gbox, Pos.CENTER);
			
		});
		
		continuar.setOnMouseClicked(e -> {
			
			dialog.hide();
			SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
			
		});
		
		setAlignment(Pos.CENTER);
		StackPane.setAlignment(box, Pos.CENTER);
		
		VBox.setMargin(continuar, new Insets(10));
		
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
