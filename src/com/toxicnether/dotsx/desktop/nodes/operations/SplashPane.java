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
import com.toxicnether.dotsx.core.sound.type.SoundType;
import com.toxicnether.dotsx.desktop.DesktopAdapter;
import com.toxicnether.dotsx.desktop.bundle.TimerUtility;
import com.toxicnether.dotsx.desktop.nodes.MainPane;
import com.toxicnether.dotsx.desktop.nodes.type.SceneType;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * 
 * Esta clase es encargada de manejar los
 * componentes necesarios para la ejecución del
 * SplashScreen donde se visualiza el Logo del
 * juego.
 * 
 * @author Abraham Lora
 *
 */
@SuppressWarnings("deprecation")
public class SplashPane extends StackPane implements GraphicReferrer {

	protected transient final JFXPanel fxPanel = new JFXPanel();
	protected transient StackPane pane;
	
	private HBox logoBox;
	private Circle circle;
	
	private Label dotslogoLabel;
	private Label xLabel;
	
	/**
	 * 
	 * Este constructor genera los diferentes atributos
	 * del juego aplicandoles valores por defecto.
	 * 
	 */
	public SplashPane() {
		
		this.pane = this;
		
		Color color = Color.rgb(255, 255, 255, 1);
	    BackgroundFill fill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(fill);
		
		this.pane.setMaxWidth(DesktopAdapter.getDimension().getWidth());
		this.pane.setMaxHeight(DesktopAdapter.getDimension().getHeight());
		
		this.pane.setMinWidth(DesktopAdapter.getDimension().getWidth());
		this.pane.setMinHeight(DesktopAdapter.getDimension().getHeight());
		
		this.pane.setPrefWidth(DesktopAdapter.getDimension().getWidth());
		this.pane.setPrefHeight(DesktopAdapter.getDimension().getHeight());
		
		this.pane.setMaxSize(DesktopAdapter.getDimension().getWidth(), DesktopAdapter.getDimension().getHeight());
		
		super.setBackground(background);
		
	}
	
	/**
	 * 
	 * Este metodo produce opciones complementarias para
	 * seguir generando los componentes que serán usados
	 * durante la animación del SplashScreen.
	 * 
	 */
	@Override
	public void start() {
		
		Glow glow = new Glow();
	    glow.setLevel(0.50); 
		
		Font logoFont = Font.loadFont(DotsX.getResource("fonts/Veal.otf").toExternalForm(), 180);
		
		this.circle = new Circle();
		this.logoBox = new HBox();
		
		this.circle.setEffect(glow);
		this.circle.prefWidth(1);
		this.circle.prefHeight(1);
		
		this.circle.setFill(Color.TRANSPARENT);
		
		this.circle.setStroke(Paint.valueOf("blue"));
		this.circle.setStrokeWidth(30);
		
		this.dotslogoLabel = new Label("DOTS");
		this.xLabel = new Label("X");
		
		this.dotslogoLabel.setTextFill(Paint.valueOf("black"));
		this.xLabel.setTextFill(Paint.valueOf("blue"));
		
		this.dotslogoLabel.setFont(logoFont);
		this.xLabel.setFont(logoFont);

		this.xLabel.setEffect(glow);
		this.xLabel.setTextFill(Paint.valueOf("blue"));
		
		this.logoBox.setMaxHeight(dotslogoLabel.getHeight());
		this.logoBox.setMaxWidth(dotslogoLabel.getWidth() + xLabel.getWidth() + 500);
		
		this.logoBox.getChildren().add(dotslogoLabel);
		this.logoBox.getChildren().add(xLabel);
		
		this.logoBox.setMaxSize(dotslogoLabel.getWidth() + xLabel.getWidth() + 500, dotslogoLabel.getHeight());
		this.logoBox.setSpacing(15.5);
		
		this.pane.getChildren().add(circle);
		this.pane.getChildren().add(logoBox);
		
		this.circle.setVisible(false);
		
		this.circle.toBack();
		this.logoBox.toFront();
		
		StackPane.setAlignment(logoBox, Pos.CENTER);
		
		expandEffect();
		
	}
	
	/**
	 * 
	 * Este metodo se encarga de generar las diferentes
	 * animaciones de tiempo para producir el efecto
	 * de las letras.
	 * 
	 */
	public void expandEffect() {
		
		final StringBuilder builder = new StringBuilder(dotslogoLabel.getText());
		
		TimerUtility.getTimer().schedule(new TimerTask() {

			@Override
			public void run() {
				
				for(int i = 4; i >= 1; i--) {
					
					final int pos = i;
					
					TimerUtility.getTimer().schedule(new TimerTask() {
						
						@Override
						public void run() {
							
							Platform.runLater(() -> {
								
								builder.setCharAt(pos - 1, ' ');
								dotslogoLabel.setText(builder.toString() + " ");
								
								SoundType.GRAPHIC_LETTER_HIT.getPlay().play();
								
							});
							
						}
						
					}, i * 200);
					
				}
				
			}
			
		}, 5000);
		
		TimerUtility.getTimer().schedule(new TimerTask() {

			@Override
			public void run() {
				
				Platform.runLater(() -> {
					
					dotslogoLabel.setVisible(false);
					circle.setVisible(true);
					
					StackPane.setAlignment(circle, Pos.CENTER);
					
				});
				
				Timeline bounceline = TimelineBuilder.create().keyFrames(
					new KeyFrame(Duration.millis(0), new KeyValue(circle.radiusProperty(), 0)),
					new KeyFrame(Duration.millis(1850), new KeyValue(circle.radiusProperty(), 1550))).build();
				
				bounceline.setRate(1);
				bounceline.play();
				
				SoundType.GRAPHIC_WOOSH.getPlay().play();
				
			}
			
		}, 5500 + (200 * 4));
		
		TimerUtility.getTimer().schedule(new TimerTask() {

			@Override
			public void run() {
				
				Platform.runLater(() -> {
					
					circle.setVisible(false);
					
				});
				
				Timeline expand = TimelineBuilder.create().keyFrames(
						new KeyFrame(Duration.millis(0), new KeyValue(xLabel.scaleXProperty(), xLabel.getScaleX())),
						new KeyFrame(Duration.millis(0), new KeyValue(xLabel.scaleYProperty(), xLabel.getScaleY())),
						new KeyFrame(Duration.millis(2250), new KeyValue(xLabel.scaleXProperty(), xLabel.getScaleX() + 100)),
						new KeyFrame(Duration.millis(2250), new KeyValue(xLabel.scaleYProperty(), xLabel.getScaleY() + 100))).build();
				
				expand.setRate(1);
				expand.play();
				
			}
			
		}, 6300 + (200 * 4));
		
		TimerUtility.getTimer().schedule(new TimerTask() {

			@Override
			public void run() {
				
				Platform.runLater(() -> {
					
					MainPane.getInstance().change(SceneType.MENU_SCENE);
					
				});
				
			}
			
		}, 6300 + (200 * 4) + 2250);
		
	}
	
	@Override
	public StackPane getPane() {
		return pane;
	}
	
}
