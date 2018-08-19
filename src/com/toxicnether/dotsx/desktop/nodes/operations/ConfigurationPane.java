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
import com.toxicnether.dotsx.core.config.Configuration;
import com.toxicnether.dotsx.core.sound.MusicPlay;
import com.toxicnether.dotsx.core.sound.SoundPlay;
import com.toxicnether.dotsx.core.sound.playlist.Playlist;
import com.toxicnether.dotsx.core.sound.type.MusicType;
import com.toxicnether.dotsx.core.sound.type.SoundType;
import com.toxicnether.dotsx.desktop.DesktopAdapter;
import com.toxicnether.dotsx.desktop.nodes.MainPane;
import com.toxicnether.dotsx.desktop.nodes.component.ToggleSwitch;
import com.toxicnether.dotsx.desktop.nodes.type.SceneType;

import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * 
 * Esta clase es el panel de configuración
 * donde se muestran diferentes componentes
 * que son utilizados para manejar opciones
 * primordiales del juego. Entre ellas
 * se tiene:
 * 
 * <br><br>
 * <table border="2">
 * 	<tr>
 * 		<td>Configurar el Volumen de la Música</td>
 *      <td>Configurar el Volumen de los Sonidos</td>
 *      <td>Activar o Desactivar el logo de Inicio</td>
 *  </tr>
 * </table>
 * 
 * @author Abraham Lora
 *
 */
public class ConfigurationPane extends StackPane implements GraphicReferrer {

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
	private ImageView bgImage;
	
	private VBox menuBox;
	private VBox logoBox;
	private VBox optionsBox;
	
	private Label paneName;
	
	private Label musicVolume;
	private Slider mVolume;
	
	private Label soundsVolume;
	private Slider sVolume;
	
	private Label splash;
	private ToggleSwitch splashButton;
	
	private Label back;
	
	/**
	 * 
	 * Este constructor inicializa todos los componentes
	 * que son utilizados durante las opciones, estos
	 * componentes van desde el fondo hasta las cajas de
	 * principal uso para el manejo de las configuraciones.
	 * 
	 */
	public ConfigurationPane() {
		
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
		this.logoBox.setAlignment(Pos.TOP_CENTER);
		this.logoBox.setSpacing(10);
		
		this.pane.setMaxWidth(DesktopAdapter.getDimension().getWidth());
		this.pane.setMaxHeight(DesktopAdapter.getDimension().getHeight());
		
		this.pane.setMinWidth(DesktopAdapter.getDimension().getWidth());
		this.pane.setMinHeight(DesktopAdapter.getDimension().getHeight());
		
		this.pane.setPrefWidth(DesktopAdapter.getDimension().getWidth());
		this.pane.setPrefHeight(DesktopAdapter.getDimension().getHeight());
		
		this.pane.setMaxSize(DesktopAdapter.getDimension().getWidth(), DesktopAdapter.getDimension().getHeight());
		this.pane.getStylesheets().add(DotsX.getResource("css/config.css").toExternalForm());
		
		super.setBackground(new Background(new BackgroundFill(Color.web("black"), CornerRadii.EMPTY, Insets.EMPTY)));
		
	}
	
	/**
	 * 
	 * Este metodo incializa el sistema, aquí se definen
	 * las variables que no hayan sido definidas en el
	 * constructor y se cargan las fuentes para ser atribuidas
	 * a sus respectivas cajas de texto.
	 * 
	 */
	@Override
	public void start() {
		
		if(started == true)
			return;
		
		started = true;
		
		Font titleFont = Font.loadFont(DotsX.getResource("fonts/Lazer84.ttf").toExternalForm(), 40);
		Font menuFont = Font.loadFont(DotsX.getResource("fonts/Less-Bold.otf").toExternalForm(), 40);
		
		Stop[] stops = new Stop[] { new Stop(0, Color.ORANGE), new Stop(1, Color.ORANGERED)};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
		
		this.paneName = new Label("Opciones");
		
		this.musicVolume = new Label("Volumen de la Musica: ");
		this.soundsVolume = new Label("Volumen de los Sonidos: ");
		this.splash = new Label("Logo Inicio: ");
		this.back = new Label("Volver");
		
		this.mVolume = new Slider();
		this.sVolume = new Slider();
		
		this.mVolume.setValue(Double.valueOf(Configuration.get("musicvolume")) * 100);
		this.sVolume.setValue(Double.valueOf(Configuration.get("soundsvolume")) * 100);
		
		this.splashButton = new ToggleSwitch(Boolean.valueOf(Configuration.get("splashlogo")));
		
		this.paneName.setFont(titleFont);
		this.paneName.setTextFill(gradient);
		
		this.musicVolume.setTextFill(Color.WHITESMOKE);
		this.soundsVolume.setTextFill(Color.WHITESMOKE);
		this.splash.setTextFill(Color.WHITESMOKE);
		this.back.setTextFill(Color.WHITESMOKE);
		
		this.musicVolume.setFont(menuFont);
		this.soundsVolume.setFont(menuFont);
		this.splash.setFont(menuFont);
		this.back.setFont(menuFont);
		
		this.optionsBox.getChildren().addAll(getConfigComponent(musicVolume, mVolume),
											 getConfigComponent(soundsVolume, sVolume),
											 getConfigComponent(splash, splashButton));
		
		this.logoBox.getChildren().add(paneName);
		this.menuBox.getChildren().addAll(logoBox, optionsBox, back);
		
		back.setOnMouseClicked(e -> {
			
			MainPane.getInstance().change(SceneType.MENU_SCENE);
			SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
			
		});
		
		splashButton.switchOnProperty().addListener((a, b, c) -> {
			
			if(c) {
        		
				Configuration.set("splashlogo", "true");
				return;
				
	    	}
			
			Configuration.set("splashlogo", "false");
			return;
			
		});
		
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
		
		stylishOption(back);
		bgImage.setOpacity(0.4);
		
		this.pane.getChildren().addAll(background, bgImage, menuBox);
		StackPane.setAlignment(optionsBox, Pos.CENTER);
		
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
	
	/**
	 * 
	 * Este metodo permite convertir una opción
	 * y su respectivo texto en una caja horizontal
	 * que conlleve a ambos.
	 * 
	 * @param cname El nodo que compone el texto.
	 * @param cexe  El nodo que compone a la acción
	 * de la opción.
	 * @return Una caja con ambos nodos incluídos.
	 */
	public HBox getConfigComponent(Node cname, Node cexe) {
		
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(cname, cexe);
		
		return box;
		
	}
	
	@Override
	public StackPane getPane() {
		return pane;
	}
	
}