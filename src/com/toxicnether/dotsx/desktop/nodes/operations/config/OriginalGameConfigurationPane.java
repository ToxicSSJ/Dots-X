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

package com.toxicnether.dotsx.desktop.nodes.operations.config;

import java.util.HashMap;
import java.util.Map;

import com.toxicnether.dotsx.DotsX;
import com.toxicnether.dotsx.cgraph.bundle.GraphicReferrer;
import com.toxicnether.dotsx.core.config.Configuration;
import com.toxicnether.dotsx.core.sound.type.SoundType;
import com.toxicnether.dotsx.desktop.DesktopAdapter;
import com.toxicnether.dotsx.desktop.nodes.MainPane;
import com.toxicnether.dotsx.desktop.nodes.component.GameChoice;
import com.toxicnether.dotsx.desktop.nodes.operations.game.OriginalGamePane.OriginalGameSizes;
import com.toxicnether.dotsx.desktop.nodes.type.SceneType;

import javafx.collections.FXCollections;
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
 * Esta clase esta encargada de manejar
 * la configuración principal para el modo original.
 * 
 * @author Abraham Lora
 *
 */
public class OriginalGameConfigurationPane extends StackPane implements GraphicReferrer {

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
	private VBox optionsBox;
	
	private GameChoice<String> choicesize;
	private Label choiceText;
	
	private Label paneName;
	private Label paneIcon;
	
	private Label back;
	
	/**
	 * 
	 * Este constructor inicializa los atributos
	 * y precarga los tamaños disponibles para
	 * el tablero.
	 * 
	 */
	public OriginalGameConfigurationPane() {
		
		Map<String, OriginalGameSizes> sizes = new HashMap<String, OriginalGameSizes>();
		
		for(OriginalGameSizes size : OriginalGameSizes.values())
			sizes.put(size.getVisual(), size);
		
		this.pane = this;
		this.choiceText = new Label();
		this.background = new Rectangle();
		this.bgImage = new ImageView(new Image(DotsX.getResourceAsStream("images/bg.jpg")));
		
		this.choicesize = new GameChoice<String>();
		this.choicesize.setValue(OriginalGameSizes.getSize(Configuration.get("orsize")).getVisual());
		this.choicesize.setItems(FXCollections.observableArrayList(sizes.keySet()));
		
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
	
	@Override
	public void start() {
		
		if(started == true)
			return;
		
		started = true;
		
		Font titleFont = Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 80);
		Font menuFont = Font.loadFont(DotsX.getResource("fonts/Less-Bold.otf").toExternalForm(), 40);
		
		HBox titlebox = new HBox(10);
		
		this.paneName = new Label("Modo Original");
		this.paneIcon = new Label("C");
		
		this.paneIcon.setFont(Font.loadFont(DotsX.getResource("fonts/Heydings.ttf").toExternalForm(), 50));
		
		this.choiceText = new Label("Proporcion del Tablero: ");
		this.back = new Label("Volver");
		
		this.paneName.setFont(titleFont);
		
		this.paneIcon.setTextFill(Color.CYAN);
		this.paneName.setTextFill(Color.CYAN);
		
		this.back.setTextFill(Color.WHITESMOKE);
		this.back.setFont(menuFont);
		
		this.choiceText.setTextFill(Color.WHITESMOKE);
		this.choiceText.setFont(menuFont);
		
		this.optionsBox.getChildren().addAll(getConfigComponent(choiceText, choicesize));
		
		titlebox.getChildren().addAll(paneName, paneIcon);
		titlebox.setAlignment(Pos.TOP_CENTER);
		
		this.logoBox.getChildren().add(titlebox);
		this.menuBox.getChildren().addAll(logoBox, optionsBox, back);
		
		choicesize.valueProperty().addListener(e -> {
			
			String value = choicesize.getValue();
			OriginalGameSizes size = OriginalGameSizes.getSize(value);
			
			Configuration.set("orsize", size.getVisual());
			
		});
		
		back.setOnMouseClicked(e -> {
			
			MainPane.getInstance().change(SceneType.SELECTOR_SCENE);
			SoundType.CONSOLE_GAME_COUNTDOWN.getPlay().play();
			
		});
		
		stylishOption(back);
		bgImage.setOpacity(0.4);
		
		this.pane.getChildren().addAll(background, bgImage, menuBox);
		
		StackPane.setAlignment(optionsBox, Pos.CENTER);
		StackPane.setAlignment(titlebox, Pos.TOP_CENTER);
		
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