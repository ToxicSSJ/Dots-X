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

import com.toxicnether.dotsx.desktop.DesktopAdapter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * 
 * Esta clase se encarga de manejar de manera
 * abstracta el modo de juego, para ser utilizado
 * durante la pantalla de selección.
 * 
 * @author Abraham Lora
 *
 */
public abstract class GameMode extends StackPane implements Serializable {

	private static final long serialVersionUID = 9024862629365869555L;

	private static ColorAdjust blackout = new ColorAdjust();
	
	private ImageView background;
	private BooleanProperty fade;
	
	private GameButton start = new GameButton("Iniciar", 180, 90);
	private GameButton config = new GameButton("Configurar", 180, 90);
	
	static {
		
		blackout.setBrightness(-0.70);
		
	}
	
	/**
	 * 
	 * Este constructor permite inicializar
	 * los atributos necesarios para el modo
	 * de juego. Se basa en una iamgen de fondo
	 * que se es utilizada durante la división.
	 * 
	 * @param bgImage La imagen de fondo.
	 */
	public GameMode(Image bgImage) {
		
		this.setPrefHeight(DesktopAdapter.getDimension().getHeight());
		this.setPrefWidth(DesktopAdapter.getDimension().getWidth() / 2);
		
		this.setMinHeight(DesktopAdapter.getDimension().getHeight());
		this.setMinWidth(DesktopAdapter.getDimension().getWidth() / 2);
		
		this.setMaxHeight(DesktopAdapter.getDimension().getHeight());
		this.setMaxWidth(DesktopAdapter.getDimension().getWidth() / 2);
		
		this.fade = new SimpleBooleanProperty();
		this.background = new ImageView();
		
		this.background.setSmooth(true);
		this.background.setPreserveRatio(true);
		
		this.background.setImage(bgImage);
		
		this.start.setColor(Color.LIMEGREEN);
		this.config.setColor(Color.BLUEVIOLET);
		
		VBox box = new VBox();
		
		box.setSpacing(30);
		box.setAlignment(Pos.CENTER);
		
		box.getChildren().addAll(start, config);
		this.getChildren().addAll(background, box);
		
		StackPane.setAlignment(box, Pos.BOTTOM_CENTER);
		this.setAlignment(Pos.BOTTOM_CENTER);
		
		this.layoutBoundsProperty().addListener((ob, oldv, newv) -> {
			
	        double w = newv.getWidth();
	        double h = newv.getHeight();
	        
	        background.setFitWidth(w);
	        background.setFitHeight(h);
	        
	        double ratio = h / w;
	        Image image = background.getImage();
	        
	        double ih = image.getHeight();
	        double iw = image.getWidth();
	        double vR = ih / iw;
	        
	        background.setViewport((ratio < vR) ? new Rectangle2D(0, 0, iw, iw * ratio) : new Rectangle2D(0, 0, ih / ratio, ih));
	        
	    });
		
		disappear();
		
		start.setOnMouseClicked(e -> start());
		config.setOnMouseClicked(e -> config());
		
	}
	
	/**
	 * 
	 * Este metodo permite hacer que se produzca
	 * el efecto Fade-In en la selección del
	 * modo.
	 * 
	 */
	public void appear() {
		
		this.setEffect(null);
		this.fade.set(true);
		
	}
	
	/**
	 * 
	 * Este metodo permite hacer que se produzca
	 * el efecto Fade-Out en la selección del
	 * modo.
	 * 
	 */
	public void disappear() {
		
		this.setEffect(blackout);
		this.fade.set(false);
		
	}
	
	/** 
	 * @return Devuelve la propiedad para conocer
	 * el booleano atribuído al Fade.
	 */
	public BooleanProperty getFade() {
		return fade;
	}
	
	/**
	 * 
	 * Este metodo es ejecutado si el usuario
	 * inicia el modo de juego.
	 * 
	 */
	public abstract void start();
	
	/**
	 * 
	 * Este metodo es ejecutado si el usuario
	 * inicia la configuración del modo de
	 * juego.
	 * 
	 */
	public abstract void config();
	
	/**
	 * 
	 * Este metodo funciona como un CallBack
	 * para cuando se esconde el modo de juego.
	 * 
	 */
	public abstract void hide();
	
}
