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

package com.toxicnether.dotsx.desktop.nodes.calc.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.toxicnether.dotsx.core.sound.SoundPlay;
import com.toxicnether.dotsx.core.sound.type.SoundType;
import com.toxicnether.dotsx.desktop.nodes.calc.DotCalculation;
import com.toxicnether.dotsx.desktop.nodes.calc.DotResult;
import com.toxicnether.dotsx.desktop.nodes.component.game.GameDot;
import com.toxicnether.dotsx.desktop.nodes.operations.game.GamePane;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * 
 * Esta clase se encarga de procesar
 * el calculo para la acción de seguir adelante
 * durante el tablero de juego.
 * 
 * @author Abraham Lora
 *
 */
public class NextDotCalculation implements DotCalculation {

	@Override
	public DotResult getResult(StackPane grid, VBox vbox, LinkedList<GameDot> dots, ObservableList<GameDot> selection, GameDot next) {
		
		if(selection.size() < 1)
			return DotResult.CONTINUE;
		
		LinkedList<HBox> boxes = new LinkedList<HBox>();
		Map<HBox, LinkedList<GameDot>> containers = new HashMap<HBox, LinkedList<GameDot>>();
		
		for(Node box : vbox.getChildren()) {
			
			HBox hbox = (HBox) box;
			LinkedList<GameDot> list = new LinkedList<GameDot>();
			
			for(Node dot : hbox.getChildren())
				list.add((GameDot) dot);
			
			boxes.add(hbox);
			containers.put(hbox, list);
			
		}
		
		GameDot last = selection.get(selection.size() - 1);
		
		int nindex = boxes.indexOf(getDotBox(boxes, next));
		int lindex = boxes.indexOf(getDotBox(boxes, last));
		
		int diff = getDifference(nindex, lindex);
		
		if(diff == 0) {
			
			int npos = boxes.get(nindex).getChildren().indexOf(next);
			int lpos = boxes.get(lindex).getChildren().indexOf(last);
			
			if(getDifference(npos, lpos) == 1) {
				
				selection.add(next);
				next.changeAttribute("selected", true);
				
				SoundPlay tick = SoundType.GRAPHIC_DOT_BLIP.getPlay();
				
				tick.setRate(selection.size() * .2);
				tick.play();
				
				Circle circle = new Circle(next.getCircle().getRadius());
				Point2D dotcoords = GamePane.getCenterAtGrid(next, grid);
				
				circle.setFill(next.getColorType().getColor().brighter());
				circle.setManaged(false);
				circle.toFront();
				
				grid.getChildren().add(circle);
				
				circle.setLayoutX(dotcoords.getX());
				circle.setLayoutY(dotcoords.getY());
				
				Timeline selanim = new Timeline();
				
				KeyValue[] values = new KeyValue[] {
						
						new KeyValue(circle.scaleXProperty(), next.getCircle().getScaleX() * 2.2),
						new KeyValue(circle.scaleYProperty(), next.getCircle().getScaleY() * 2.2)
						
				};
				
				for(KeyValue value : values)
					selanim.getKeyFrames().add(new KeyFrame(Duration.millis(425), value));
				
				selanim.getKeyFrames().add(new KeyFrame(Duration.millis(400), new KeyValue(circle.opacityProperty(), .0)));
				selanim.getKeyFrames().add(new KeyFrame(Duration.millis(425), e -> grid.getChildren().remove(circle)));
				selanim.play();
				
				return DotResult.STOP;
				
			}
			
			return DotResult.STOP;
			
		}
		
		if(diff == 1) {
			
			int npos = boxes.get(nindex).getChildren().indexOf(next);
			int lpos = boxes.get(lindex).getChildren().indexOf(last);
			
			if(getDifference(npos, lpos) == 0) {
				
				selection.add(next);
				next.changeAttribute("selected", true);
				
				SoundPlay tick = SoundType.GRAPHIC_DOT_BLIP.getPlay();
				
				tick.setRate(selection.size() * .2);
				tick.play();
				
				Circle circle = new Circle(next.getCircle().getRadius());
				Point2D dotcoords = GamePane.getCenterAtGrid(next, grid);
				
				circle.setFill(next.getColorType().getColor().brighter());
				circle.setManaged(false);
				circle.toFront();
				
				grid.getChildren().add(circle);
				
				circle.setLayoutX(dotcoords.getX());
				circle.setLayoutY(dotcoords.getY());
				
				Timeline selanim = new Timeline();
				
				KeyValue[] values = new KeyValue[] {
						
						new KeyValue(circle.scaleXProperty(), next.getCircle().getScaleX() * 2.2),
						new KeyValue(circle.scaleYProperty(), next.getCircle().getScaleY() * 2.2)
						
				};
				
				for(KeyValue value : values)
					selanim.getKeyFrames().add(new KeyFrame(Duration.millis(425), value));
				
				selanim.getKeyFrames().add(new KeyFrame(Duration.millis(400), new KeyValue(circle.opacityProperty(), .0)));
				selanim.getKeyFrames().add(new KeyFrame(Duration.millis(425), e -> grid.getChildren().remove(circle)));
				selanim.play();
				
				return DotResult.STOP;
				
			}
			
			return DotResult.STOP;
			
		}
		
		if(diff > 1)
			return DotResult.STOP;
		
		return DotResult.CONTINUE;
		
	}
	
	public HBox getDotBox(LinkedList<HBox> boxes, GameDot dot) {
		
		for(HBox box : boxes)
			if(box.getChildren().contains(dot))
				return box;
		
		return null;
		
	}
	
	public String getName() {
		return "NextDotCalculation";
	}
	
	public int getDifference(int a, int b) {
		return a > b ? a - b : b - a;
	}
	
	public static DotCalculation getInstance() {
		return new NextDotCalculation();
	}
	
}
