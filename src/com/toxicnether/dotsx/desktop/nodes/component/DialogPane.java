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
import java.util.ArrayList;
import java.util.List;

import com.toxicnether.dotsx.desktop.DesktopAdapter;
import com.toxicnether.dotsx.desktop.nodes.MainPane;
import com.toxicnether.dotsx.desktop.nodes.operations.game.GamePane;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;
import javafx.util.Duration;

public class DialogPane extends StackPane implements Serializable {

	private static final long serialVersionUID = 8632984718686043898L;

	public static final List<DialogPane> openedPanes = new ArrayList<DialogPane>();
	
	private Rectangle rect;
	private StackPane content;
	
	public DialogPane() {
		
		this.rect = new Rectangle(750, DesktopAdapter.getDimension().getHeight() / 2.5);
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		this.setOpacity(.95);
		
		this.rect.setStrokeWidth(25);
		this.rect.setStrokeLineJoin(StrokeLineJoin.BEVEL);
		this.rect.setStroke(Color.BLACK);
		this.rect.setFill(Color.BLACK);
		
		this.getChildren().add(rect);
		
	}
	
	public DialogPane(double width, double height) {
		
		this.rect = new Rectangle(width, height);
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		this.setOpacity(.95);
		
		this.rect.setStrokeWidth(25);
		this.rect.setStrokeLineJoin(StrokeLineJoin.BEVEL);
		this.rect.setStroke(Color.BLACK);
		this.rect.setFill(Color.BLACK);
		
		this.getChildren().add(rect);
		
	}

	public DialogPane setItems(StackPane content) {
		
		this.content = content;
		this.getChildren().add(content);
		return this;
		
	}
	
	public DialogPane show() {
		
		if(!openedPanes.contains(this))
			openedPanes.add(this);
		
		Timeline timeline = new Timeline();
		
		Point2D point = GamePane.getCenter(MainPane.getInstance().getBoundsInLocal());
		MainPane.getInstance().getChildren().add(this);
		
		timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(this.layoutXProperty(), -1000)));
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200), new KeyValue(this.layoutXProperty(), point.getX())));
		
		this.setManaged(false);
		this.setLayoutY(point.getY());
		timeline.play();
		
		return this;
		
	}
	
	public void hide() {
		
		if(openedPanes.contains(this))
			openedPanes.remove(this);
		
		Timeline timeline = new Timeline();
		
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200), new KeyValue(this.layoutXProperty(), -1000)));
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200), e -> MainPane.getInstance().getChildren().remove(this)));
		
		this.setManaged(false);
		timeline.play();
		
	}
	
	public Rectangle getRect() {
		return rect;
	}

	public StackPane getContent() {
		return content;
	}
	
}
