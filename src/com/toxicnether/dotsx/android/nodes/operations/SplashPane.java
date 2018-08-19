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

package com.toxicnether.dotsx.android.nodes.operations;

import java.awt.Dimension;
import java.util.TimerTask;

import com.toxicnether.dotsx.cgraph.bundle.AndroidReferrer;
import com.toxicnether.dotsx.desktop.bundle.TimerUtility;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
 * Esta clase es la encargada de mantener
 * los diferentes componentes que son ejecutados
 * si se a habilitado el logo de inicio en las
 * opciones. Esta desarrollado especificamente
 * como ambiente para Android.
 * 
 * @author Abraham Lora
 *
 */
@SuppressWarnings("deprecation")
public class SplashPane extends StackPane implements AndroidReferrer {

	protected transient final JFXPanel fxPanel = new JFXPanel();
	protected transient StackPane pane;
	
	private HBox logoBox;
	private Circle circle;
	
	private Label dotslogoLabel;
	private Label xLabel;
	
	private Font logoFont;
	private Dimension dimension;

	/**
	 * 
	 * Este constructor inicializa una instancia
	 * predefinida al panel.
	 * 
	 */
	public SplashPane() {
		
		this.pane = this;
		
	}
	
	@Override
	public void setDefaults(Object...res) {
		
		if(res.length > 0)
			if(res[0] instanceof Font)
				logoFont = (Font) res[0];
		
		if(res.length > 1)
			if(res[1] instanceof Dimension)
				dimension = (Dimension) res[1];
		
		Color color = Color.rgb(0, 0, 0, 1);
	    BackgroundFill fill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(fill);
		
		this.pane.setMaxWidth(dimension.getWidth());
		this.pane.setMaxHeight(dimension.getHeight());
		
		this.pane.setMinWidth(dimension.getWidth());
		this.pane.setMinHeight(dimension.getHeight());
		
		this.pane.setPrefWidth(dimension.getWidth());
		this.pane.setPrefHeight(dimension.getHeight());
		
		this.pane.setMaxSize(dimension.getWidth(), dimension.getHeight());
		
		super.setBackground(background);
		
	}
	
	@Override
	public void start() {
		
		this.circle = new Circle();
		this.logoBox = new HBox();
		
		this.circle.setFill(Color.TRANSPARENT);
		
		this.circle.setStroke(Paint.valueOf("blue"));
		this.circle.setStrokeWidth(30);
		
		this.dotslogoLabel = new Label("DOTS");
		this.xLabel = new Label("X");
		
		this.dotslogoLabel.setTextFill(Paint.valueOf("white"));
		this.xLabel.setTextFill(Paint.valueOf("blue"));
		
		this.dotslogoLabel.setFont(logoFont);
		this.xLabel.setFont(logoFont);

		this.xLabel.setTextFill(Paint.valueOf("blue"));
		
		this.logoBox.setMaxHeight(dotslogoLabel.getHeight());
		this.logoBox.setMaxWidth(dotslogoLabel.getWidth() + xLabel.getWidth() + 500);
		
		this.logoBox.getChildren().add(dotslogoLabel);
		this.logoBox.getChildren().add(xLabel);
		
		this.logoBox.setMaxSize(dotslogoLabel.getWidth() + xLabel.getWidth() + 500, dotslogoLabel.getHeight());
		this.logoBox.setSpacing(15.5);
		
		// this.logoBox.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0, 1), CornerRadii.EMPTY, Insets.EMPTY)));
		
		this.pane.getChildren().add(circle);
		this.pane.getChildren().add(logoBox);
		
		this.circle.setVisible(false);
		
		this.circle.toBack();
		this.logoBox.toFront();
		
		StackPane.setAlignment(logoBox, Pos.CENTER);

		expandEffect();
		
	}
	
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
								
							});
							
						}
						
					}, i * 100);
					
				}
				
			}
			
		}, 5000);
		
		TimerUtility.getTimer().schedule(new TimerTask() {

			@Override
			public void run() {
				
				Timeline expand = TimelineBuilder.create().keyFrames(
						new KeyFrame(Duration.millis(0), new KeyValue(xLabel.scaleXProperty(), xLabel.getScaleX())),
						new KeyFrame(Duration.millis(0), new KeyValue(xLabel.scaleYProperty(), xLabel.getScaleY())),
						new KeyFrame(Duration.millis(2250), new KeyValue(xLabel.scaleXProperty(), xLabel.getScaleX() + 100)),
						new KeyFrame(Duration.millis(2250), new KeyValue(xLabel.scaleYProperty(), xLabel.getScaleY() + 100))).build();
				
				expand.setRate(1);
				expand.play();
				
			}
			
		}, 6300 + (60 * 4));
		
	}
	
	@Override
	public StackPane getPane() {
		return pane;
	}
	
}
