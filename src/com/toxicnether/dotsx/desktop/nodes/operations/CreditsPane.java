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
 * Esta clase contiene los nodos que componen
 * el texto predefinido de los creditos del juego
 * donde se da a conocer el nombre del creador del
 * juego y algunas atribuciones dadas a qienes se
 * considerá que deben recibirlas debido a que se
 * utilizó material creado por ellos. Cabe aclarar
 * que este material suele ser RoyaltyFree y en
 * cierta mayoría para uso no comercial.
 * 
 * @author Abraham Lora
 *
 */
public class CreditsPane extends StackPane implements GraphicReferrer {

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
	private Label back;
	
	/**
	 * 
	 * Este constructor permite la creación de una pantalla
	 * de creditos predefinida.
	 * 
	 */
	public CreditsPane() {
		
		this.pane = this;
		
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

		start();
		
	}
	
	@Override
	public void start() {
		
		if(started)
			return;
		
		started = true;
		
		Font titleFont = Font.loadFont(DotsX.getResource("fonts/Lazer84.ttf").toExternalForm(), 40);
		Font menuFont = Font.loadFont(DotsX.getResource("fonts/Less-Bold.otf").toExternalForm(), 40);
		
		Stop[] stops = new Stop[] { new Stop(0, Color.ORANGE), new Stop(1, Color.ORANGERED)};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
		
		this.paneName = new Label("Creditos");
		
		this.back = new Label("Ir al Menu");
		
		this.paneName.setFont(titleFont);
		this.paneName.setTextFill(gradient);
		
		this.back.setTextFill(Color.WHITESMOKE);
		this.back.setFont(menuFont);
		
		this.logoBox.getChildren().add(paneName);
		this.menuBox.getChildren().addAll(logoBox, getCreditsComponent());
		
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
	 * diferentes nodos que componen los creditos generales
	 * del juego.
	 * 
	 * @return Una caja vertical con diferentes nodos
	 * que forman los creditos.
	 * @see GamePane
	 */
	public VBox getCreditsComponent() {
		
		VBox box = new VBox(10);
		
		HBox creatorBox = new HBox(5);
		HBox musicBox = new HBox(5);
		HBox fontsBox = new HBox(5);
		
		Label creator = new Label("CREADOR: ");
		Label music = new Label("MUSICA UTILIZADA: ");
		Label usefonts = new Label("FUENTES UTILIZADAS: ");
		
		Label creatorName = new Label("Abraham Miguel Lora Vargas");
		Label musicName = new Label("TeknoAXE's Royalty Free Music");
		Label usefontsReference = new Label("DaFont (Uso no comercial)");
		
		creator.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 55));
		music.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 55));
		usefonts.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 55));
		
		creatorName.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 62));
		musicName.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 62));
		usefontsReference.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 62));
		
		creatorName.setTextFill(ColorUtil.getGradient(Color.ORANGERED.brighter()));
		musicName.setTextFill(ColorUtil.getGradient(Color.CYAN.brighter()));
		usefontsReference.setTextFill(ColorUtil.getGradient(Color.LIME.brighter()));
		
		creatorName.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, .7), CornerRadii.EMPTY, Insets.EMPTY)));
		musicName.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, .7), CornerRadii.EMPTY, Insets.EMPTY)));
		usefontsReference.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, .7), CornerRadii.EMPTY, Insets.EMPTY)));
		
		creator.setTextFill(Color.WHITESMOKE);
		music.setTextFill(Color.WHITESMOKE);
		usefonts.setTextFill(Color.WHITESMOKE);
		
		creatorBox.getChildren().addAll(creator, creatorName);
		musicBox.getChildren().addAll(music, musicName);
		fontsBox.getChildren().addAll(usefonts, usefontsReference);
		
		creatorBox.setAlignment(Pos.CENTER);
		musicBox.setAlignment(Pos.CENTER);
		fontsBox.setAlignment(Pos.CENTER);
		
		box.setBackground(ColorUtil.getBackground(Color.rgb(0, 0, 0, .2)));
		
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(creatorBox, musicBox, fontsBox);
		return box;
		
	}
	
	@Override
	public StackPane getPane() {
		return pane;
	}
	
}