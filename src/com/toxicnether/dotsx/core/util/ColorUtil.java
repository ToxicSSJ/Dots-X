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

package com.toxicnether.dotsx.core.util;

import java.util.concurrent.ThreadLocalRandom;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

public class ColorUtil {

	public static Timeline getGradientTimeline(Node node, int count, int duration, Color...colors) {

		node.getStyleClass().add("animated");
		
        ObjectProperty<Color> base = new SimpleObjectProperty<>();
        
        KeyValue[] kvalues = new KeyValue[colors.length];
        KeyFrame[] kframes = new KeyFrame[colors.length];
        
        for(int i = 0; i < colors.length; i++) {
        	
        	kvalues[i] = new KeyValue(base, colors[i]);
        	kframes[i] = new KeyFrame(Duration.millis(i * duration), kvalues[i]);
        	
        }

        Timeline timeline = new Timeline(kframes);

        base.addListener((obs, oc, nc) -> {
            node.setStyle(String.format("-gradient-base: #%02x%02x%02x; ", 
		                  (int) (nc.getRed() * 255),
		                  (int) (nc.getGreen() * 255),
		                  (int) (nc.getBlue() * 255)));
        });

        timeline.setAutoReverse(true);
        timeline.setCycleCount(count);
        
        return timeline;
		
	}
	
	public static LinearGradient randomGradient() {
		
		Color[] random = randomCouple();
		Stop[] stops = new Stop[] { new Stop(0, random[0]), new Stop(1, random[1])};
		
        LinearGradient gradient = new LinearGradient(0, 1, 1, 0, true, CycleMethod.NO_CYCLE, stops);
		return gradient;
        
	}
	
	public static LinearGradient getGradient(Color...colors) {
		
		Stop[] stops = new Stop[colors.length];
		
		for(int i = 0; i < stops.length; i++)
			stops[i] = new Stop(i, colors[i]);
		
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
		return gradient;
        
	}
	
	public static Background getBackground(Color color) {
		return new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
	}
	
	public static Color[] randomCouple() {
		
		switch(ThreadLocalRandom.current().nextInt(1, 6)) {
		
		case 1: return new Color[] { Color.BLUE, Color.BLUEVIOLET };
		case 2: return new Color[] { Color.LIMEGREEN, Color.LIME };
		case 3: return new Color[] { Color.HOTPINK, Color.DEEPPINK };
		case 4: return new Color[] { Color.GRAY, Color.SILVER };
		case 5: return new Color[] { Color.MEDIUMVIOLETRED, Color.RED };
		default: return new Color[] { Color.GOLD, Color.ORANGERED };
		
		}
		
	}
	
}
