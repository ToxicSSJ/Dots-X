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
import com.toxicnether.dotsx.core.sound.type.SoundType;
import com.toxicnether.dotsx.core.util.ColorUtil;
import com.toxicnether.dotsx.desktop.DesktopAdapter;
import com.toxicnether.dotsx.desktop.nodes.MainPane;
import com.toxicnether.dotsx.desktop.nodes.operations.game.GamePane;
import com.toxicnether.dotsx.desktop.nodes.type.SceneType;

import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
 * Esta clase contiene las variables necesarias
 * para la finalización de partidas. Aquí se
 * genera un resumen de los puntos y estrellas
 * obtenidos durante esa partida.
 * 
 * @author Abraham Lora
 *
 */
public class FinishGamePane extends StackPane implements GraphicReferrer {

	/**
	 * 
	 * Esta variable permite saber si ya se ha
	 * inicializado el panel con anterioridad.
	 * 
	 */
	protected static boolean started = false;
	
	protected transient final JFXPanel fxPanel = new JFXPanel();
	protected transient StackPane pane;
	
	private Rectangle background;
	private ImageView bgImage;
	
	private VBox menuBox;
	private VBox logoBox;
	
	private Label paneName;
	
	private GamePane game;
	private Label back;
	
	/**
	 * 
	 * Este constructor permite la creación de una pantalla
	 * de finalización de partida en base a un panel de
	 * juego creado e inicializado con anterioridad.
	 * 
	 * @param game El panel de juego.
	 */
	public FinishGamePane(GamePane game) {
		
		this.pane = this;
		this.game = game;
		
		this.background = new Rectangle();
		this.bgImage = new ImageView(new Image(DotsX.getResourceAsStream("images/bg.jpg")));
		
		this.background.setStrokeWidth(0);
		
		this.background.setWidth(DesktopAdapter.getDimension().getWidth());
		this.background.setHeight(DesktopAdapter.getDimension().getHeight());
		
		this.background.setFill(Paint.valueOf("black"));
		
		this.menuBox = new VBox();
		this.menuBox.setAlignment(Pos.CENTER);
		this.menuBox.setSpacing(0);
		
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
		
		MainPane.songBox.getChildren().remove(0);
		start();
		
	}
	
	@Override
	public void start() {
		
		Font titleFont = Font.loadFont(DotsX.getResource("fonts/Lazer84.ttf").toExternalForm(), 40);
		Font menuFont = Font.loadFont(DotsX.getResource("fonts/Less-Bold.otf").toExternalForm(), 40);
		
		Stop[] stops = new Stop[] { new Stop(0, Color.ORANGE), new Stop(1, Color.ORANGERED)};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
		
		this.paneName = new Label("Partida Terminada");
		
		this.back = new Label("Ir al Menu");
		
		this.paneName.setFont(titleFont);
		this.paneName.setTextFill(gradient);
		
		this.back.setTextFill(Color.WHITESMOKE);
		this.back.setFont(menuFont);
		
		this.logoBox.getChildren().add(paneName);
		this.menuBox.getChildren().addAll(logoBox, getResumeComponent(game));
		
		back.setOnMouseClicked(e -> {
			
			MainPane.getInstance().change(SceneType.MENU_SCENE);
			SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
			
		});
		
		stylishOption(back);
		bgImage.setOpacity(0.4);
		
		this.menuBox.getChildren().add(back);
		this.pane.getChildren().addAll(background, bgImage, menuBox);
		menuBox.toFront();
		
		VBox.setMargin(logoBox, new Insets(0, 0, 80, 0));
		VBox.setMargin(back, new Insets(30));
		
		Configuration.removeOriginalGame(game.saveName());
		StackPane.setAlignment(menuBox, Pos.CENTER);
		
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
	
	/**
	 * 
	 * Este metodo permite obtener un caja vertical con
	 * diferentes nodos que componen un resumen general
	 * de la partida, en base a el Score Total y las
	 * Estrellas Totales.
	 * 
	 * @param game La instancia del juego a resumir.
	 * 
	 * @return Una caja vertical con diferentes nodos
	 * que forman el resumen.
	 * @see GamePane
	 */
	public VBox getResumeComponent(GamePane game) {
		
		VBox box = new VBox(10);
		
		HBox starsbox = new HBox(5);
		HBox scorebox = new HBox(5);
		
		Label scoreIcon = new Label("\uea61");
		Label starsIcon = new Label("\ue96a");
		
		Label score = new Label("SCORE TOTAL: ");
		Label stars = new Label("STARS TOTALES: ");
		
		Label scoreInt = new Label("" + game.getScore().get());
		Label starsInt = new Label("" + game.getStars().get());
		
		score.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 55));
		stars.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 55));
		
		scoreInt.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 62));
		starsInt.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 62));
		
		scoreInt.setTextFill(ColorUtil.getGradient(Color.ORANGERED.brighter()));
		starsInt.setTextFill(ColorUtil.getGradient(Color.CYAN.brighter()));
		
		scoreInt.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, .7), CornerRadii.EMPTY, Insets.EMPTY)));
		starsInt.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, .7), CornerRadii.EMPTY, Insets.EMPTY)));
		
		scoreIcon.setFont(Font.loadFont(DotsX.getResource("fonts/FreeGame.ttf").toExternalForm(), 40));
		starsIcon.setFont(Font.loadFont(DotsX.getResource("fonts/FreeGame.ttf").toExternalForm(), 40));
		
		score.setTextFill(Color.WHITESMOKE);
		stars.setTextFill(Color.WHITESMOKE);
		
		scoreIcon.setTextFill(Color.WHITESMOKE);
		starsIcon.setTextFill(Color.WHITESMOKE);
		
		scorebox.getChildren().addAll(scoreIcon, score, scoreInt);
		starsbox.getChildren().addAll(starsIcon, stars, starsInt);
		
		scorebox.setAlignment(Pos.CENTER);
		starsbox.setAlignment(Pos.CENTER);
		
		box.setBackground(ColorUtil.getBackground(Color.rgb(0, 0, 0, .2)));
		
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(scorebox, starsbox);
		return box;
		
	}
	
	@Override
	public StackPane getPane() {
		return pane;
	}
	
}