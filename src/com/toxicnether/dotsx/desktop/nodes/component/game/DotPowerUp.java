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

package com.toxicnether.dotsx.desktop.nodes.component.game;

import java.awt.MouseInfo;
import java.awt.Point;
import java.io.Serializable;
import java.util.TimerTask;

import com.toxicnether.dotsx.DotsX;
import com.toxicnether.dotsx.cgraph.GraphicAdapter;
import com.toxicnether.dotsx.desktop.bundle.TimerUtility;
import com.toxicnether.dotsx.desktop.nodes.MainPane;
import com.toxicnether.dotsx.desktop.nodes.calc.DotCalculation;
import com.toxicnether.dotsx.desktop.nodes.component.DialogPane;
import com.toxicnether.dotsx.desktop.nodes.component.game.event.PowerUpDragEvent;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * 
 * Esta clase es utilizada como base para
 * los potenciadores de la partida. Contiene
 * atributos que llevan en común y permite
 * seleccionar el componente visual que los
 * diferencia.
 * 
 * @author Abraham Lora
 *
 */
public abstract class DotPowerUp extends StackPane implements Cloneable, Serializable {
	
	private static final long serialVersionUID = -7411509928208834572L;
	
	private static Image onImage, offImage;
	private ColorAdjust blackout = new ColorAdjust();
	
	static {
		
		onImage = new Image(DotsX.getResource("images/component/PowerUpC.png").toExternalForm());
		offImage = new Image(DotsX.getResource("images/component/PowerUpC.png").toExternalForm());
		
	}
	
	private ImageView background;
	private StackPane visual;
	
	private Label quantityLabel;
	private PowerUpDragEvent event;
	
	private int quantity = -1;
	
	private double scaleX = 0;
	private double scaleY = 0;
	
	private boolean drag = false;
	
	/**
	 * 
	 * Este constructor permite inicializar
	 * las variables principales y necesarias
	 * que utiliza cada PowerUp.
	 * 
	 */
	public DotPowerUp() {
		
		this.blackout.setBrightness(-0.93);
		
		this.visual = new StackPane();
		this.background = new ImageView();
		this.quantityLabel = new Label();
		
		this.quantityLabel.setFont(GraphicAdapter.font);
		this.quantityLabel.setText("x0");
		
		this.quantityLabel.setTextFill(Color.DARKRED.brighter());
		
		this.scaleX = this.quantityLabel.getScaleX();
		this.scaleY = this.quantityLabel.getScaleY();
		
		this.background.setFitHeight(130);
		this.background.setFitWidth(130);
		
		this.background.setPreserveRatio(false);
		
		this.getChildren().add(background);
		this.getChildren().add(visual);
		this.getChildren().add(quantityLabel);
		
		StackPane.setAlignment(quantityLabel, Pos.BOTTOM_RIGHT);
		StackPane.setMargin(quantityLabel, new Insets(0, 0, 0, 0));
		
		setQuantity(10);

		this.visual.setOnMousePressed(e -> {
			
			if(DialogPane.openedPanes.size() > 0)
				return;
			
			if(drag || quantity < 1)
				return;
			
			if(getChildren().contains(visual))
				getChildren().remove(visual);
			
			if(!MainPane.getInstance().getChildren().contains(visual))
				MainPane.getInstance().getChildren().add(visual);
			
			this.drag = true;
			
			TimerUtility.getTimer().schedule(new TimerTask() {

				@Override
				public void run() {
					
					if(!drag)
						cancel();
					
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							
							Point point = MouseInfo.getPointerInfo().getLocation();
							visual.relocate(point.getX() - visual.getWidth() / 2, point.getY() - visual.getHeight() / 2);
							
							if(event != null)
								dotPosition(event.fireMove(new Point2D(point.getX(), point.getY())));
							
						}
						
					});
					
				}
				
			}, 0, 1);
			
			visual.setEffect(new Glow(.7));
			
		});
		
		this.visual.setOnMouseReleased(e -> {
			
			if(DialogPane.openedPanes.size() > 0)
				return;
			
			if(!drag || quantity < 1)
				return;
			
			this.drag = false;
			Point point = MouseInfo.getPointerInfo().getLocation();
			
			if(event != null)
				if(event.fireDrag(new Point2D(point.getX(), point.getY()))) {
					
					
					
				} else {
					
					TimerUtility.getTimer().schedule(new TimerTask() {

						@Override
						public void run() {
							
							Platform.runLater(new Runnable() {

								@Override
								public void run() {
									
									if(MainPane.getInstance().getChildren().contains(visual))
										MainPane.getInstance().getChildren().remove(MainPane.getInstance().getChildren().indexOf(visual));
									
									if(!getChildren().contains(visual))
										getChildren().add(visual);
									
									dotPosition(null);
									visual.setEffect(null);
									
								}
								
							});
								
						}
						
					}, 5);
					
					return;
					
				}
					
			TimerUtility.getTimer().schedule(new TimerTask() {

				@Override
				public void run() {
					
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							
							if(MainPane.getInstance().getChildren().contains(visual))
								MainPane.getInstance().getChildren().remove(MainPane.getInstance().getChildren().indexOf(visual));
							
							if(!getChildren().contains(visual))
								getChildren().add(visual);
							
							dotPosition(null);
							visual.setEffect(null);
							
						}
						
					});
						
				}
				
			}, 5);
			
		});
		
	}
	
	/**
	 * 
	 * Este metodo permite definir la cantidad del
	 * PowerUp y genera una animación.
	 * 
	 * @param quantity La cantidad que se desea
	 * definir.
	 */
	public void setQuantity(int quantity) {
		
		if(this.quantity == quantity)
			return;
		
		this.quantity = quantity;
		
		Timeline timeline = new Timeline();
		
		this.quantityLabel.setText("x" + quantity);
		
		timeline.getKeyFrames().addAll(
				new KeyFrame(Duration.millis(100), new KeyValue(this.quantityLabel.scaleXProperty(), scaleX + .5)),
				new KeyFrame(Duration.millis(100), new KeyValue(this.quantityLabel.scaleYProperty(), scaleY + .5)),
				new KeyFrame(Duration.millis(200), new KeyValue(this.quantityLabel.scaleXProperty(), scaleX)),
				new KeyFrame(Duration.millis(200), new KeyValue(this.quantityLabel.scaleYProperty(), scaleY)));
		
		if(this.quantity > 0) {
			
			this.background.setImage(onImage);
			
			quantityLabel.setTextFill(Color.DEEPSKYBLUE.brighter().brighter());
			background.setEffect(null);
			visual.setEffect(null);
			
			timeline.play();
			return;
			
		}
		
		this.background.setImage(offImage);
		
		quantityLabel.setTextFill(Color.DARKRED.brighter());
		background.setEffect(blackout);
		background.setCache(true);
		background.setCacheHint(CacheHint.SPEED);
		
		visual.setEffect(blackout);
		visual.setCache(true);
		visual.setCacheHint(CacheHint.SPEED);
		
	}
	
	/**
	 * 
	 * Este metodo permite definir el evento de arrastrado
	 * del PowerUp.
	 * 
	 * @param e El evento a definir.
	 */
	public void setEvent(PowerUpDragEvent e) {
		
		event = e;
		return;
		
	}
	
	/**
	 * @return Devuelve la cantidad actual del PowerUp.
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @return Devuelve el componente visual del PowerUp.
	 */
	public StackPane getVisual() {
		return visual;
	}
	
	/**
	 * @return Retorna la imagen de fondo del PowerUp.
	 */
	public ImageView getBG() {
		return background;
	}
	
	/**
	 * 
	 * Este metodo es llamado cuando se encuentra un
	 * dot en cierta posición. También se llama al momento
	 * de abandonar el drag el PowerUp.
	 * 
	 * @param dot La instancia del dot en la posición o
	 * null si no existe.
	 */
	public abstract void dotPosition(GameDot dot);
	
	/**
	 * 
	 * Este metodo se encargará de generar una animación
	 * dirigida al accionador de cantidad. Con el fin de
	 * que se vea claramente cuando se gane un PowerUp.
	 * 
	 * @param from El punto desde donde se generará la animación.
	 */
	public abstract void addOne(Point2D from);
	
	/**
	 * 
	 * Este metodo debe ser ejecutado cuando se considerá
	 * que el PowerUp está listo para iniciar.
	 * 
	 * @return La instancia de DotCalculation que ejecutará
	 * la respectiva acción del PowerUp.
	 * @see DotCalculation
	 */
	public abstract DotCalculation getCalculation();
	
}
