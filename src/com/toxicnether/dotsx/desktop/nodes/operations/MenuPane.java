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

import java.util.TimerTask;

import com.toxicnether.dotsx.DotsX;
import com.toxicnether.dotsx.cgraph.bundle.GraphicReferrer;
import com.toxicnether.dotsx.core.config.Configuration;
import com.toxicnether.dotsx.core.sound.playlist.Playlist;
import com.toxicnether.dotsx.core.sound.type.MusicType;
import com.toxicnether.dotsx.core.sound.type.SoundType;
import com.toxicnether.dotsx.core.util.ColorUtil;
import com.toxicnether.dotsx.desktop.DesktopAdapter;
import com.toxicnether.dotsx.desktop.bundle.TimerUtility;
import com.toxicnether.dotsx.desktop.nodes.MainPane;
import com.toxicnether.dotsx.desktop.nodes.type.SceneType;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * 
 * Esta clase se encarga de manejar los
 * componentes del menú principal del juego,
 * donde se manejan las diferentes opciones
 * que lo componen.
 * 
 * @author Abraham Lora
 *
 */
public class MenuPane extends StackPane implements GraphicReferrer {

	/**
	 * 
	 * Esta variable permite saber si ya se ha
	 * inicializado el panel con anterioridad.
	 * 
	 */
	protected static boolean started = false;
	
	protected transient final JFXPanel fxPanel = new JFXPanel();
	protected transient StackPane pane;
	
	/**
	 * 
	 * Esta variable contiene la Playlist que es
	 * utilizada para reproducir la música del juego
	 * desde el menú hasta el juego en sí. TODO (IWP)
	 * 
	 */
	public static Playlist playlist = new Playlist(MusicType.GRAPHIC_MENU_MUSIC1,
													  MusicType.GRAPHIC_MENU_MUSIC2,
													  MusicType.GRAPHIC_MENU_MUSIC3,
													  MusicType.GRAPHIC_MENU_MUSIC4,
													  MusicType.GRAPHIC_MENU_MUSIC5,
													  MusicType.GRAPHIC_MENU_MUSIC6);
	
	private Rectangle background;
	private ImageView bgImage;
	
	private VBox menuBox;
	private VBox logoBox;
	private VBox optionsBox;
	
	private Label dotslogoLabel;
	private Label subvLabel;
	
	private Label newGame;
	private Label loadGame;
	private Label credits;
	private Label options;
	
	private Label exit;
	
	/**
	 * 
	 * Este constructor hace referencia al
	 * panel principal del juego, aquí se
	 * producen las opciones del menú para poder
	 * ejecutar el juego y se cargan todos los
	 * componentes.
	 * 
	 */
	public MenuPane() {
		
		this.pane = this;
		
		this.background = new Rectangle();
		this.bgImage = new ImageView(new Image(DotsX.getResourceAsStream("images/bg.jpg")));
		
		this.background.setStrokeWidth(0);
		
		this.background.setWidth(DesktopAdapter.getDimension().getWidth());
		this.background.setHeight(DesktopAdapter.getDimension().getHeight());
		this.background.setFill(Paint.valueOf("black"));
		
		this.menuBox = new VBox();
		this.menuBox.setAlignment(Pos.CENTER);
		this.menuBox.setSpacing(70);
		
		this.optionsBox = new VBox();
		this.optionsBox.setAlignment(Pos.CENTER);
		this.optionsBox.setSpacing(20);
		
		this.logoBox = new VBox();
		this.logoBox.setAlignment(Pos.CENTER);
		this.logoBox.setSpacing(10);
		
		this.pane.setMaxWidth(DesktopAdapter.getDimension().getWidth());
		this.pane.setMaxHeight(DesktopAdapter.getDimension().getHeight());
		
		this.pane.setMinWidth(DesktopAdapter.getDimension().getWidth());
		this.pane.setMinHeight(DesktopAdapter.getDimension().getHeight());
		
		this.pane.setPrefWidth(DesktopAdapter.getDimension().getWidth());
		this.pane.setPrefHeight(DesktopAdapter.getDimension().getHeight());
		
		this.pane.setMaxSize(DesktopAdapter.getDimension().getWidth(), DesktopAdapter.getDimension().getHeight());
		this.pane.getStylesheets().add(DotsX.getResource("css/gradient.css").toExternalForm());
		
		super.setBackground(new Background(new BackgroundFill(Color.web(Boolean.valueOf(Configuration.get("splashlogo")) ? "blue" : "black"), CornerRadii.EMPTY, Insets.EMPTY)));
		
	}
	
	/**
	 * 
	 * En este metodo se inicializan los componentes, así como
	 * también las fuentes principales que son utilizadas luego
	 * para estilizar los textos que componen la pantalla del
	 * menú.
	 * 
	 */
	@Override
	public void start() {
		
		if(started == true)
			return;
		
		started = true;
		
		Font logoFont = Font.loadFont(DotsX.getResource("fonts/Alien.ttf").toExternalForm(), 155);
		Font subvFont = Font.loadFont(DotsX.getResource("fonts/Lazer84.ttf").toExternalForm(), 40);
		
		Font menuFont = Font.loadFont(DotsX.getResource("fonts/Less-Bold.otf").toExternalForm(), 40);
		Font soundFont = Font.loadFont(DotsX.getResource("fonts/Less-Bold.otf").toExternalForm(), 30);
		
        MainPane.songName.setFont(soundFont);
        MainPane.songName.setTextFill(ColorUtil.randomGradient());
        
		this.dotslogoLabel = new Label("DOTSX");
		this.subvLabel = new Label("synthversion");
		
		this.newGame = new Label("Nueva Partida");
		this.loadGame = new Label("Partidas Guardadas");
		this.credits = new Label("Creditos");
		this.options = new Label("Opciones");
		
		this.exit = new Label("Salir");
		
		this.subvLabel.setTextFill(ColorUtil.getGradient(Color.ORANGE, Color.ORANGERED));
		
		this.dotslogoLabel.setFont(logoFont);
		this.subvLabel.setFont(subvFont);
		
		this.newGame.setTextFill(Color.WHITESMOKE);
		this.loadGame.setTextFill(Color.WHITESMOKE);
		this.options.setTextFill(Color.WHITESMOKE);
		this.credits.setTextFill(Color.WHITESMOKE);
		this.exit.setTextFill(Color.WHITESMOKE);
		
		this.newGame.setFont(menuFont);
		this.loadGame.setFont(menuFont);
		this.options.setFont(menuFont);
		this.credits.setFont(menuFont);
		this.exit.setFont(menuFont);
		
		Timeline logoanim = ColorUtil.getGradientTimeline(dotslogoLabel, Animation.INDEFINITE, 1600, Color.DARKCYAN, Color.CADETBLUE, Color.BLUEVIOLET, Color.CYAN);
		logoanim.play();
		
		newGame.setOnMouseClicked(e -> {
			
			MainPane.getInstance().change(SceneType.SELECTOR_SCENE);
			SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
			
		});
		
		credits.setOnMouseClicked(e -> {
			
			MainPane.getInstance().change(SceneType.CREDITS_SCENE);
			SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
			
		});
		
		options.setOnMouseClicked(e -> {
			
			MainPane.getInstance().change(SceneType.CONFIG_SCENE);
			SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
			
		});
		
		loadGame.setOnMouseClicked(e -> {
			
			SavedGamesPane pane = new SavedGamesPane();
			MainPane.getInstance().change(SceneType.CUSTOM_SCENE, pane);
			
			SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
			
		});
		
		exit.setOnMouseClicked(e -> {
			
			System.exit(0);
			return;
			
		});
		
		stylishOption(newGame, loadGame, credits, options, exit);
		
		TimerUtility.getTimer().schedule(new TimerTask() {

			@Override
			public void run() {
				
				Platform.runLater(() -> {
					
					pane.getChildren().add(background);
					background.setOpacity(1.0);
					
					final Animation animation = new Transition() {

			            {
			                setCycleDuration(Duration.millis(1000));
			                setInterpolator(Interpolator.EASE_OUT);
			            }

			            @Override
			            protected void interpolate(double frac) {
			            	
			            	background.setOpacity(frac);
			                
			            }
			            
			        };
			        
			        animation.play();
					playlist.play(true);
					
				});
				
			}
			
		}, 150);
		
		TimerUtility.getTimer().schedule(new TimerTask() {

			@SuppressWarnings("static-access")
			@Override
			public void run() {
				
				Platform.runLater(() -> {
					
					MainPane.songBox.getChildren().addAll(MainPane.diskGif, MainPane.songName);
					logoBox.getChildren().addAll(dotslogoLabel, subvLabel);
					
					optionsBox.getChildren().addAll(newGame, loadGame, credits, options);
					menuBox.getChildren().addAll(logoBox, optionsBox, exit);
					
					pane.getChildren().add(MainPane.songBox);
					pane.getChildren().add(menuBox);
					
					pane.setAlignment(MainPane.songBox, Pos.TOP_CENTER);
					pane.setAlignment(menuBox, Pos.CENTER);
					
					MainPane.songBox.setOnMouseClicked(e -> {
					});
					
				});
				
			}
			
		}, 1000);
		
		TimerUtility.getTimer().schedule(new TimerTask() {

			@Override
			public void run() {
				
				Platform.runLater(() -> {
					
					if(Configuration.getBoolean("menuvideo")) {
						
						MainPane.bgVideo.setVisible(false);
						pane.getChildren().add(MainPane.bgVideo);
						MainPane.bgVideo.setOpacity(1.0);
						
						MainPane.bgVideo.toBack();
						
					} else {
						
						bgImage.setVisible(false);
						pane.getChildren().add(bgImage);
						bgImage.setOpacity(1.0);
						
						bgImage.toBack();
						
					}
					
					background.toBack();
					
					final Animation animation = new Transition() {

			            {
			                setCycleDuration(Duration.millis(1000));
			                setInterpolator(Interpolator.EASE_OUT);
			            }

			            @Override
			            protected void interpolate(double frac) {
			            	
			            	if(Configuration.getBoolean("menuvideo"))
			            		MainPane.bgVideo.setVisible(true);
			            	else
			            		bgImage.setVisible(true);
			            	
			            	if(frac < 0.40)
			            		if(Configuration.getBoolean("menuvideo"))
			            			MainPane.bgVideo.setOpacity(frac);
				            	else
				            		bgImage.setOpacity(frac);
			                
			            }
			            
			        };
			        
			        animation.play();
					
				});
				
			}
			
		}, 3150);
		
	}
	
	/**
	 * 
	 * Este metodo permite estilizar una opción del menú,
	 * aplicando los eventos necesarios para darles
	 * un efecto mas comodo a cada una.
	 * 
	 * @param labels Los nodos tipo Label que serán estilizados.
	 */
	public void stylishOption(Label...labels) {
		
		for(Label label : labels) {
			
			label.setOnMouseEntered(e -> {
				
				Stop[] stops = new Stop[] { new Stop(0, Color.ORANGERED), new Stop(1, Color.RED)};
		        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
				
				label.setTextFill(gradient);
				SoundType.CONSOLE_MOVE.getPlay().play();
				
			});
			
			label.setOnMouseExited(e -> label.setTextFill(Color.WHITESMOKE));
			
		}
		
	}
	
	@Override
	public StackPane getPane() {
		return pane;
	}
	
}