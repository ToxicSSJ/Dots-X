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

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ToggleSwitch extends HBox implements Serializable {

	private static final long serialVersionUID = 1227961691336129135L;
	
	private final Label label = new Label();
	private final Button button = new Button();
	
	private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);
	
	public SimpleBooleanProperty switchOnProperty() { 
		return switchedOn; 
	}
	
	public ToggleSwitch(boolean status) {
		
		init(status);
		
		switchedOn.addListener((a,b,c) -> {
			
				if(c) {
					
		    		label.setText("SI");
		    		setStyle("-fx-background-color: aqua; -fx-text-fill:black; -fx-background-radius: 4;");
		    		label.toFront();
		    		return;
		        		
		    	}
				
				label.setText("NO");
    			setStyle("-fx-background-color: gray; -fx-text-fill:black; -fx-background-radius: 4;");
        		label.toBack();
        		
		});
		
	}
	
	private void init(boolean status) {
		
		button.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		getChildren().addAll(label, button);
		
		button.setOnAction((e) -> {
			switchedOn.set(!switchedOn.get());
		});
		
		label.setOnMouseClicked((e) -> {
			switchedOn.set(!switchedOn.get());
		});
		
		setStyle();
		bindProperties();
		
		if(status) {
			
    		label.setText("SI");
    		setStyle("-fx-background-color: aqua; -fx-text-fill:black; -fx-background-radius: 4;");
    		label.toFront();
    		return;
    		
		}
		
		label.setText("NO");
		setStyle("-fx-background-color: gray; -fx-text-fill:black; -fx-background-radius: 4;");
		label.toBack();
		
	}
	
	private void setStyle() {
		
		setWidth(80);
		label.setAlignment(Pos.CENTER);
		setAlignment(Pos.CENTER_LEFT);
		
	}
	
	private void bindProperties() {
		
		label.prefWidthProperty().bind(widthProperty().divide(2));
		label.prefHeightProperty().bind(heightProperty());
		
		button.prefWidthProperty().bind(widthProperty().divide(2));
		button.prefHeightProperty().bind(heightProperty());
		
	}
	
}
