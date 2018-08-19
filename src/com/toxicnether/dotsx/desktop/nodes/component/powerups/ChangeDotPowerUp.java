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

package com.toxicnether.dotsx.desktop.nodes.component.powerups;

import java.util.LinkedList;

import com.toxicnether.dotsx.DotsX;
import com.toxicnether.dotsx.core.sound.type.SoundType;
import com.toxicnether.dotsx.desktop.nodes.MainPane;
import com.toxicnether.dotsx.desktop.nodes.calc.DotCalculation;
import com.toxicnether.dotsx.desktop.nodes.calc.DotResult;
import com.toxicnether.dotsx.desktop.nodes.component.DialogPane;
import com.toxicnether.dotsx.desktop.nodes.component.dialog.SelectDotTypeDialog;
import com.toxicnether.dotsx.desktop.nodes.component.game.DotPowerUp;
import com.toxicnether.dotsx.desktop.nodes.component.game.GameDot;
import com.toxicnether.dotsx.desktop.nodes.operations.game.GamePane;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * 
 * Esta clase es la encargada de manejar
 * los componentes para el PowerUp que intercambia
 * los colores de un Dot.
 * 
 * @author Abraham Lora
 *
 */
public class ChangeDotPowerUp extends DotPowerUp {

	private static final long serialVersionUID = -6750579968378727857L;

	private DotCalculation calculation = new DotCalculation() {

		@Override
		public DotResult getResult(StackPane grid, VBox vbox, LinkedList<GameDot> dots,
				ObservableList<GameDot> selection, GameDot next) {
			
			DialogPane dialog = new DialogPane();
			SelectDotTypeDialog select = new SelectDotTypeDialog(next, instance, dialog);
			
			dialog.setItems(select).show();
			return DotResult.WAIT;
			
		}

		@Override
		public String getName() {
			return "ChangeDotPowerUp";
		}
		
	};
	
	private DotPowerUp instance;
	
	private Label label;
	private Circle dotSimulation;
	
	private boolean usecustom;
	
	/**
	 * 
	 * Este constructor inicializa todos
	 * los componentes del sistema.
	 * 
	 * @param custom Permite saber si
	 * el tipo de dot CUSTOM, está habilitado.
	 * @see DotColorType
	 */
	public ChangeDotPowerUp(boolean custom) {
		super();
		
		Font font = Font.loadFont(DotsX.getResource("fonts/Heydings.ttf").toExternalForm(), 30);
		
		instance = this;
		usecustom = custom;
		label = new Label();
		
		label.setFont(font);
		label.setTextFill(Color.CYAN);
		label.setText("r");
		
		dotSimulation = new Circle(20);
		
		dotSimulation.setFill(Color.TRANSPARENT);
		
		dotSimulation.setStroke(Color.GRAY);
		dotSimulation.setStrokeDashOffset(5);
		dotSimulation.setStrokeWidth(10);
		
		StackPane.setAlignment(dotSimulation, Pos.TOP_CENTER);
		StackPane.setMargin(dotSimulation, new Insets(12, 0, 0, 0));
		
		StackPane.setAlignment(label, Pos.TOP_RIGHT);
		StackPane.setMargin(label, new Insets(-5, 35, 0, 0));
		
		getVisual().getChildren().add(dotSimulation);
		getVisual().getChildren().add(label);
		
		Tooltip tip = new Tooltip("(!) Cambia de color un Dot.");
		tip.setFont(Font.loadFont(DotsX.getResource("fonts/8Bit.ttf").toExternalForm(), 20));
		
        Tooltip.install(this, tip);
		
	}
	
	/**
	 * @return Permite saber si está habilitado el tipo
	 * de DOT custom.
	 */
	public boolean useCustom() {
		return usecustom;
	}
	
	/**
	 * 
	 * Este metodo permite producir una nueva instancia
	 * de 'ChangeDotPowerUp' en base al atributo necesario
	 * para el constructor.
	 * 
	 * @param custom Si se utilizará un color CUSTOM.
	 * @return La instancia.
	 */
	public static DotPowerUp makeInstance(boolean custom) {
		return new ChangeDotPowerUp(custom);
	}

	@Override
	public void addOne(Point2D from) {
		
		StackPane clon = new StackPane();
		clon.setManaged(false);
		
		double scaleX = getVisual().getScaleX();
		double scaleY = getVisual().getScaleY();
		
		Point2D to = GamePane.getCenterAtGrid(getVisual(), MainPane.getInstance());
		Timeline animation = new Timeline();
		
		clon.getChildren().addAll(makeInstance(usecustom).getVisual());
		MainPane.getInstance().getChildren().add(clon);
		
		animation.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(clon.layoutXProperty(), from.getX())));
		animation.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(clon.layoutYProperty(), from.getY())));
		animation.getKeyFrames().add(new KeyFrame(Duration.millis(300), new KeyValue(clon.layoutXProperty(), to.getX())));
		animation.getKeyFrames().add(new KeyFrame(Duration.millis(300), new KeyValue(clon.layoutYProperty(), to.getY() - getVisual().getWidth() / 3)));
		animation.getKeyFrames().add(new KeyFrame(Duration.millis(200), e -> clon.setEffect(new Glow(.6))));
		animation.getKeyFrames().add(new KeyFrame(Duration.millis(200), e -> SoundType.GRAPHIC_DOT_WINLEVEL.getPlay().play()));
		animation.getKeyFrames().add(new KeyFrame(Duration.millis(300), e -> getVisual().setEffect(new Glow(.6))));
		animation.getKeyFrames().add(new KeyFrame(Duration.millis(400), e -> MainPane.getInstance().getChildren().remove(clon)));
		animation.getKeyFrames().add(new KeyFrame(Duration.millis(400), new KeyValue(getVisual().scaleXProperty(), 3)));
		animation.getKeyFrames().add(new KeyFrame(Duration.millis(400), new KeyValue(getVisual().scaleYProperty(), 3)));
		animation.getKeyFrames().add(new KeyFrame(Duration.millis(500), e -> getVisual().setEffect(null)));
		animation.getKeyFrames().add(new KeyFrame(Duration.millis(500), e -> setQuantity(getQuantity() + 1)));
		animation.getKeyFrames().add(new KeyFrame(Duration.millis(500), new KeyValue(getVisual().scaleXProperty(), scaleX)));
		animation.getKeyFrames().add(new KeyFrame(Duration.millis(500), new KeyValue(getVisual().scaleYProperty(), scaleY)));
		
		animation.play();
		return;
		
	}
	
	@Override
	public void dotPosition(GameDot dot) {
		dotSimulation.setStroke(dot == null ? Color.GRAY : dot.getColorType().getColor());
	}

	@Override
	public DotCalculation getCalculation() {
		return calculation;
	}
	
}
