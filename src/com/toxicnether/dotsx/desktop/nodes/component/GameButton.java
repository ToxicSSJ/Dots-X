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

package com.toxicnether.dotsx.desktop.nodes.component;

import java.io.Serializable;
import java.util.Arrays;

import com.toxicnether.dotsx.DotsX;
import com.toxicnether.dotsx.desktop.nodes.MainPane;
import com.toxicnether.dotsx.desktop.nodes.operations.game.GamePane;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class GameButton extends StackPane implements Serializable {

	private static final long serialVersionUID = 6648446998162671762L;
	
	private Label label = new Label();
	private Rectangle bgrect = new Rectangle();
	
	private Color color = Color.WHITE;
	
	public GameButton(String text, double width, double height) {
		
		this.setHeight(height);
		this.setWidth(width);
		
		this.setPrefHeight(height);
		this.setPrefWidth(width);
		
		this.setMaxHeight(height);
		this.setMaxWidth(width);
		
		this.bgrect.setHeight(height);
		this.bgrect.setWidth(width);
		this.bgrect.setFill(color);
		
		this.bgrect.setStrokeLineJoin(StrokeLineJoin.BEVEL);
		this.bgrect.setStrokeWidth(width * 0.05);
		
		this.label.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 30));
		this.label.setTextFill(color.darker().darker());
		this.label.setText(text);
		
		this.setOnMouseEntered(e -> {
			
			Point2D point = GamePane.getPointAtGrid(bgrect, MainPane.getInstance());
			Rectangle rect = new Rectangle();
			
			rect.setWidth(width);
			rect.setHeight(height);
			
			rect.setFill(color.brighter());
			rect.setManaged(false);
			
			rect.setX(point.getX());
			rect.setY(point.getY());
			
			rect.setMouseTransparent(true);
			
			rect.toBack();
			rect.toBack();
			
			MainPane.getInstance().getChildren().add(rect);
			
			Timeline timeline = new Timeline();
			timeline.setCycleCount(1);
			
			KeyValue cr = new KeyValue(rect.scaleXProperty(), rect.getScaleX() * 1.3), 
					 cs = new KeyValue(rect.scaleYProperty(), rect.getScaleY() * 1.3);
			
			for(KeyValue value : Arrays.asList(cr, cs))
				timeline.getKeyFrames().add(new KeyFrame(Duration.millis(125), value));
			
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(130), new KeyValue(rect.opacityProperty(), 0)));
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(135), k -> MainPane.getInstance().getChildren().remove(rect)));
			timeline.play();
			
			this.setColor(color.brighter());
			
		});
		
		this.setOnMouseExited(e -> {
			
			this.setColor(color.darker());
			
		});
		
		this.getChildren().addAll(bgrect, label);
		
	}
	
	public void setColor(Color color) {
		
		this.color = color;
		
		this.label.setTextFill(color.darker().darker());
		this.bgrect.setFill(color);
		this.bgrect.setStroke(color);
		
	}
	
}
