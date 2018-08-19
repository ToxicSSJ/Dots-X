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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.toxicnether.dotsx.core.color.DotColorType;
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
 * Esta clase es la encargada de manejar
 * el calculo para la secuencia cerrada.
 * 
 * @author Abraham Lora
 *
 */
public class ClosedSequenceCalculation implements DotCalculation {

	@Override
	public DotResult getResult(StackPane grid, VBox vbox, LinkedList<GameDot> dots, ObservableList<GameDot> selection, GameDot next) {
		
		if(selection.size() < 4)
			return DotResult.CONTINUE;
		
		if(!selection.contains(next))
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
				
				SUSTENTACION : if(selection.get(0).getColorType() == DotColorType.CYAN) {
					
					List<GameDot> inside = getInside(dots, vbox, boxes, containers);
					
					if(inside.isEmpty())
						break SUSTENTACION;
					
					for(GameDot sel : selection) {
						
						sel.setType(DotColorType.RED);
						sel.changeAttribute("selected", false);
						
					}
					
					inside.forEach(dot -> {
						
						dot.changeAttribute("selected", false);
						dot.setType(DotColorType.CYAN);
						
					});
					
					selection.clear();
					return DotResult.STOP;
					
				}
				
				for(GameDot dot : dots) {
					
					if(!dot.getColorType().equals(next.getColorType()))
						continue;
					
					if(selection.contains(dot))
						continue;
					
					dot.changeAttribute("selected", true);
					dot.changeAttribute("closed", true);
					dot.changeAttribute("noline", true);
					
					selection.add(dot);
					
					Circle circle = new Circle(dot.getCircle().getRadius());
					Point2D dotcoords = GamePane.getCenterAtGrid(dot, grid);
					
					circle.setFill(dot.getColorType().getColor().brighter());
					circle.setManaged(false);
					circle.toFront();
					
					grid.getChildren().add(circle);
					
					circle.setLayoutX(dotcoords.getX());
					circle.setLayoutY(dotcoords.getY());
					
					Timeline selanim = new Timeline();
					
					KeyValue[] values = new KeyValue[] {
							
							new KeyValue(circle.scaleXProperty(), dot.getCircle().getScaleX() * 2.2),
							new KeyValue(circle.scaleYProperty(), dot.getCircle().getScaleY() * 2.2)
							
					};
					
					for(KeyValue value : values)
						selanim.getKeyFrames().add(new KeyFrame(Duration.millis(425), value));
					
					selanim.getKeyFrames().add(new KeyFrame(Duration.millis(400), new KeyValue(circle.opacityProperty(), .0)));
					selanim.getKeyFrames().add(new KeyFrame(Duration.millis(425), e -> grid.getChildren().remove(circle)));
					selanim.play();
					
				}
				
				return DotResult.BREAK;
				
			}
			
			return DotResult.STOP;
			
		}
		
		if(diff == 1) {
			
			int npos = boxes.get(nindex).getChildren().indexOf(next);
			int lpos = boxes.get(lindex).getChildren().indexOf(last);
			
			if(getDifference(npos, lpos) == 0) {
				
				selection.add(next);
				next.changeAttribute("selected", true);
				
				SUSTENTACION : if(selection.get(0).getColorType() == DotColorType.CYAN) {
					
					List<GameDot> inside = getInside(dots, vbox, boxes, containers);
					
					if(inside.isEmpty())
						break SUSTENTACION;
					
					for(GameDot sel : selection) {
						
						sel.setType(DotColorType.RED);
						sel.changeAttribute("selected", false);
						
					}
					
					inside.forEach(dot -> {
						
						dot.changeAttribute("selected", false);
						dot.setType(DotColorType.CYAN);
						
					});
					
					selection.clear();
					return DotResult.STOP;
					
				}
				
				for(GameDot dot : dots) {
					
					if(!dot.getColorType().equals(next.getColorType()))
						continue;
					
					if(selection.contains(dot))
						continue;
					
					dot.changeAttribute("selected", true);
					dot.changeAttribute("closed", true);
					dot.changeAttribute("noline", true);
					
					selection.add(dot);
					
					Circle circle = new Circle(dot.getCircle().getRadius());
					Point2D dotcoords = GamePane.getCenterAtGrid(dot, grid);
					
					circle.setFill(dot.getColorType().getColor().brighter());
					circle.setManaged(false);
					circle.toFront();
					
					grid.getChildren().add(circle);
					
					circle.setLayoutX(dotcoords.getX());
					circle.setLayoutY(dotcoords.getY());
					
					Timeline selanim = new Timeline();
					
					KeyValue[] values = new KeyValue[] {
							
							new KeyValue(circle.scaleXProperty(), dot.getCircle().getScaleX() * 2.2),
							new KeyValue(circle.scaleYProperty(), dot.getCircle().getScaleY() * 2.2)
							
					};
					
					for(KeyValue value : values)
						selanim.getKeyFrames().add(new KeyFrame(Duration.millis(425), value));
					
					selanim.getKeyFrames().add(new KeyFrame(Duration.millis(400), new KeyValue(circle.opacityProperty(), .0)));
					selanim.getKeyFrames().add(new KeyFrame(Duration.millis(425), e -> grid.getChildren().remove(circle)));
					selanim.play();
					
				}
				
				return DotResult.BREAK;
				
			}
			
			return DotResult.STOP;
			
		}
		
		return DotResult.CONTINUE;
		
	}
	
	/**
	 * 
	 * Este metodo permite obtener la caja que contiene a
	 * un dot en base a todas las cajas y el dot que se desea
	 * buscar.
	 * 
	 * @return La caja horizontal con los dots.
	 */
	public HBox getDotBox(LinkedList<HBox> boxes, GameDot dot) {
		
		for(HBox box : boxes)
			if(box.getChildren().contains(dot))
				return box;
		
		return null;
		
	}
	
	public String getName() {
		return "ClosedSequenceCalculation";
	}
	
	public int getDifference(int a, int b) {
		return a > b ? a - b : b - a;
	}
	
	public List<GameDot> getInside(LinkedList<GameDot> dots, VBox vbox, LinkedList<HBox> boxes, Map<HBox, LinkedList<GameDot>> containers) {
		
		List<GameDot> inside = new ArrayList<GameDot>();
		
		for(GameDot dot : dots) {
			
			if(((boolean) dot.getAttribute("selected")) == true)
				continue;
			
			if(isInside(dot, vbox, boxes, containers))
				inside.add(dot);
			
		}
		
		inside.forEach(k -> k.changeAttribute("selected", true));
		return inside;
		
	}
	
	public boolean isInside(GameDot dot, VBox vbox, LinkedList<HBox> boxes, Map<HBox, LinkedList<GameDot>> containers) {
		
		return hasUp(dot, vbox, boxes, containers) &&
				   hasDown(dot, vbox, boxes, containers);
	}
	
	public boolean hasUp(GameDot dot, VBox vbox, LinkedList<HBox> boxes, Map<HBox, LinkedList<GameDot>> containers) {
		
		HBox box;
		int boxindex = 0, dotindex = 0;
		
		for(HBox cbox : boxes)
			if(cbox.getChildren().contains(dot)) {
				
				box = cbox;
				
				boxindex = boxes.indexOf(box);
				dotindex = box.getChildren().indexOf(dot);
				
				break;
				
			}
		
		for(int i = boxindex; i > 0; i--) {
			
			HBox upbox = boxes.get(i);
			GameDot updot = (GameDot) upbox.getChildren().get(dotindex);
			
			if(((boolean) updot.getAttribute("selected")) == true)
				return true;
				
		}
		
		return false;
		
	}
	
	public boolean hasDown(GameDot dot, VBox vbox, LinkedList<HBox> boxes, Map<HBox, LinkedList<GameDot>> containers) {
		
		HBox box;
		int boxindex = 0, dotindex = 0;
		
		for(HBox cbox : boxes)
			if(cbox.getChildren().contains(dot)) {
				
				box = cbox;
				
				boxindex = boxes.indexOf(box);
				dotindex = box.getChildren().indexOf(dot);
				
				break;
				
			}
		
		for(int i = boxindex; i < boxes.size(); i++) {
			
			HBox downbox = boxes.get(i);
			GameDot downdot = (GameDot) downbox.getChildren().get(dotindex);
			
			if(((boolean) downdot.getAttribute("selected")) == true)
				return true;
				
		}
		
		return false;
		
	}
	
	public boolean hasRight(GameDot dot, VBox vbox, Map<HBox, LinkedList<GameDot>> containers) {
		
		HBox container = containers.keySet().stream().filter(hbox -> containers.get(hbox).contains(dot)).findAny().orElse(null);
		LinkedList<GameDot> list = containers.get(container);
		
		int index = containers.get(container).indexOf(dot);
		
		while(index + 1 < list.size())
			if(list.get(++index).hasAttribute("selected"))
				return true;
		
		return false;
		
	}
	
	public boolean hasLeft(GameDot dot, VBox vbox, Map<HBox, LinkedList<GameDot>> containers) {
		
		HBox container = containers.keySet().stream().filter(hbox -> containers.get(hbox).contains(dot)).findAny().orElse(null);
		LinkedList<GameDot> list = containers.get(container);
		
		int index = containers.get(container).indexOf(dot);
		
		while(index - 1 > -1)
			if(list.get(--index).hasAttribute("selected"))
				return true;
		
		return false;
		
	}
	
	public static DotCalculation getInstance() {
		return new ClosedSequenceCalculation();
	}
	
}
